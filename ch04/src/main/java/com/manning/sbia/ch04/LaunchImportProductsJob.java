package com.manning.sbia.ch04;

import java.util.Date;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class LaunchImportProductsJob {

    public static void main(String[] args) throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("/com/manning/sbia/ch04/import-products-job.xml");
        JobLauncher jobLauncher = context.getBean(JobLauncher.class);
        Job job = context.getBean(Job.class);

        JobExecution run = jobLauncher.run(job, new JobParametersBuilder()
                .addString("inputFile", "file:./products.txt")
                .addDate("date", new Date())
                .toJobParameters());
    }

}
