package com.example.batchprocessing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.lang.Nullable;

import java.util.Random;

public class PauloJobExecutionDecider implements JobExecutionDecider {

    private static final Logger log = LoggerFactory.getLogger(PauloJobExecutionDecider.class);

    @Override
    public FlowExecutionStatus decide(JobExecution jobExecution, @Nullable StepExecution stepExecution) {
        log.info("Decide JobExecution");

        int i = new Random().nextInt();

        if (i >= 9) {
            log.info("Will repeat!");
            return new FlowExecutionStatus("REPEAT");
        }

        log.info("Will end!");
        return new FlowExecutionStatus("END");
    }
}
