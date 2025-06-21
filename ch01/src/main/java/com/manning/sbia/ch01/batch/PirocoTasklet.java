package com.manning.sbia.ch01.batch;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;


public class PirocoTasklet implements Tasklet {

    int count = 0;

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        System.out.println("Entrou");

        if (count <= 3) {
            System.out.println(count);
            count++;
            return RepeatStatus.CONTINUABLE;
        }

        System.out.println("Saiu");
        return RepeatStatus.FINISHED;
    }
}
