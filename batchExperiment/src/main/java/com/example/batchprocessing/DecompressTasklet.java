package com.example.batchprocessing;

import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class DecompressTasklet implements Tasklet {

    private static final Logger LOG = LoggerFactory.getLogger(DecompressTasklet.class);

    private final File inputDirectory;
    private final String targetDirectory;
    private final String targetFile;

    public DecompressTasklet(File inputFile, String targetDirectory, String targetFile) {
        this.inputDirectory = inputFile;
        this.targetDirectory = targetDirectory;
        this.targetFile = targetFile;
    }

    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        LOG.info("decompress");

        if (!inputDirectory.exists()) {
            LOG.warn("Input directory does not exist, skipping step");
            return RepeatStatus.FINISHED;
        }

        if (inputDirectory.getName().endsWith(".txt")) {
            LOG.info("Not compressed zip, skipping step");
            FileUtils.copyFile(inputDirectory, new File(targetDirectory, targetFile));

            return RepeatStatus.FINISHED;
        }

        File[] files = inputDirectory.listFiles();

        if (files.length == 0) {
            LOG.warn("No files found in input directory, skipping step");
            return RepeatStatus.FINISHED;
        }


        for (File file : files) {
            if (file.isDirectory()) {
                continue;
            }
            File target = new File(targetDirectory, makeName(file.getName()));

            LOG.info("decompress {}", file.getName());
            try (
                    FileInputStream fis = new FileInputStream(file);
                    BufferedInputStream bis = new BufferedInputStream(fis);
                    GzipCompressorInputStream gis = new GzipCompressorInputStream(bis);
                    FileOutputStream fos = new FileOutputStream(target)
            ) {
                byte[] buffer = new byte[4096];
                int len;
                while ((len = gis.read(buffer)) != -1) {
                    fos.write(buffer, 0, len);
                }
            }

//
//            ZipInputStream zis = new ZipInputStream(new BufferedInputStream(new FileInputStream(file)));
//
//            File targetDirectoryAsFile = new File(targetDirectory);
//            if (!targetDirectoryAsFile.exists()) {
//                FileUtils.forceMkdir(targetDirectoryAsFile);
//            }
//
//            File target = new File(targetDirectory, targetFile);
//
//            BufferedOutputStream dest = null;
//            while (zis.getNextEntry() != null) {
//                if (!target.exists()) {
//                    target.createNewFile();
//                }
//                FileOutputStream fos = new FileOutputStream(target);
//                dest = new BufferedOutputStream(fos);
//                IOUtils.copy(zis, dest);
//                dest.flush();
//                dest.close();
//            }
//            zis.close();
//
//            if (!target.exists()) {
//                throw new IllegalStateException("Could not decompress anything from the archive!");
//            }
        }

        return RepeatStatus.FINISHED;
    }

    private String makeName(String name) {
        int extensionPoint = name.lastIndexOf(".");

        if (extensionPoint > 0) {
            return name.substring(0, extensionPoint);
        }

        return name;
    }
}
