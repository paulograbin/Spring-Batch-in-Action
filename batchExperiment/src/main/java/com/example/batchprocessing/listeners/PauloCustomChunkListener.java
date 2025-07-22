package com.example.batchprocessing.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.scope.context.ChunkContext;

import java.util.Map;

public class PauloCustomChunkListener implements ChunkListener {

    private static final Logger LOG = LoggerFactory.getLogger(PauloCustomChunkListener.class);

    @Override
    public void beforeChunk(ChunkContext context) {
        LOG.info("***** New chunk ******");
    }

    @Override
    public void afterChunkError(ChunkContext context) {
        LOG.warn("Chunk error!!!!!!!!!!!!!!!!!");
    }

    @Override
    public void afterChunk(ChunkContext context) {
        Map<String, Object> stepExecutionContext = context.getStepContext().getStepExecutionContext();
        String orDefault = String.valueOf(stepExecutionContext.get("personItemReader.read.count"));
        String rollbackCount = String.valueOf(stepExecutionContext.get("rollbackCount"));

        LOG.info("***** Chunk ended after reading {} items and {} rollbacks ******", orDefault, rollbackCount);
    }
}
