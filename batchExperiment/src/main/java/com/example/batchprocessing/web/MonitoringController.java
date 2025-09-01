package com.example.batchprocessing.web;

import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MonitoringController {


    private static final Logger LOG = LoggerFactory.getLogger(MonitoringController.class);


    @Resource
    private JobExplorer jobExplorer;


    @GetMapping("/aaaa")
    public void aaaa() {
        List<String> jobNames = jobExplorer.getJobNames();
        LOG.info("Job names: " + jobNames);

        jobNames.forEach(jobName -> {
            var jobInstances = jobExplorer.getJobInstances(jobName, 0, 10);
            LOG.info("Job instances for job name {}: {}", jobName, jobInstances);

            getFailedJobExecutions(jobName);
        });
    }

    private void getFailedJobExecutions(String jobName) {
        int currentPage = 0;
        int currentPageSize = 0;
        int pageSize = 100;

        while (currentPageSize == pageSize) {
            List<JobInstance> jobInstances = jobExplorer.getJobInstances(jobName, currentPage * pageSize, pageSize);
            currentPage++;

            for (JobInstance jobInstance : jobInstances) {
                List<JobExecution> jobExecutions = jobExplorer.getJobExecutions(jobInstance);

                var failedExecutions = 0;

                for (JobExecution jobExecution : jobExecutions) {
                    if (jobExecution.getExitStatus().equals(ExitStatus.FAILED)) {
                        LOG.info("Failed Job Execution: {}", jobExecution);
                        failedExecutions++;
                    }
                }

                LOG.info("Found {} job executions for job instance {}", jobExecutions.size(), jobInstance);
                LOG.info("Failed executions for this job instance: {}", failedExecutions);
            }
        }
    }
}
