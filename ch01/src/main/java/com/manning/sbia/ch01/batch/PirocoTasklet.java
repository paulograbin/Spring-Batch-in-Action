package com.manning.sbia.ch01.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;


public class PirocoTasklet implements Tasklet {

    int count = 0;

    private static final Logger LOG = LoggerFactory.getLogger(PirocoTasklet.class);

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        LOG.info("Entrou...");

        if (count <= 3) {
            System.out.println(count);
            count++;
            return RepeatStatus.CONTINUABLE;
        }

        LOG.info("Saiu");
        return RepeatStatus.FINISHED;
    }
}
