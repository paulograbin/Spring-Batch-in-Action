package com.manning.sbia.ch02.structure;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;

public class SkippedDecider implements JobExecutionDecider {

    @Override
    public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
        return stepExecution.getSkipCount() == 0 ? FlowExecutionStatus.COMPLETED : new FlowExecutionStatus("SKIPPED");
    }

}
