package com.example.batchprocessing;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
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
import java.io.OutputStream;

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
            LOG.info("decompress {}", file.getName());

            try (
                    FileInputStream fis = new FileInputStream(file);
                    BufferedInputStream bis = new BufferedInputStream(fis);
                    GzipCompressorInputStream gis = new GzipCompressorInputStream(bis);
                    TarArchiveInputStream tis = new TarArchiveInputStream(gis)
            ) {
                ArchiveEntry entry;
                while ((entry = tis.getNextEntry()) != null) {
                    File outputFile = new File(targetDirectory, entry.getName());
                    if (entry.isDirectory()) {
                        outputFile.mkdirs();
                    } else {
                        // ensure parent directories exist
                        outputFile.getParentFile().mkdirs();
                        try (OutputStream os = new FileOutputStream(outputFile)) {
                            byte[] buffer = new byte[4096];
                            int count;
                            while ((count = tis.read(buffer)) != -1) {
                                os.write(buffer, 0, count);
                            }
                        }
                    }
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
}
