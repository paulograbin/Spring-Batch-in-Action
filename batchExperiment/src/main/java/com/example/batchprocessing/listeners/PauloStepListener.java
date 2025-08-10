package com.example.batchprocessing.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;


public class PauloStepListener implements StepExecutionListener {


    private static final Logger LOG = LoggerFactory.getLogger(PauloStepListener.class);


    @Override
    public void beforeStep(StepExecution stepExecution) {
        LOG.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        LOG.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        LOG.info("@@@@@@@@ BEFORE STEP {}                @@@@@@@@@", stepExecution.getStepName());
        LOG.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        LOG.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        LOG.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        LOG.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        LOG.info("@@@@@@@@ AFTER STEP {}                 @@@@@@@@@", stepExecution.getStepName());
        LOG.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        LOG.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

        if (!ExitStatus.FAILED.equals(stepExecution.getExitStatus()) && stepExecution.getSkipCount() > 0) {
            return new ExitStatus("COMPLETED WITH SKIPS");
        }

        return stepExecution.getExitStatus();
    }
}
