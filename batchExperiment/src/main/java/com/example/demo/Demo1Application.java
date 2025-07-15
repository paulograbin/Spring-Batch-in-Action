package com.example.demo;

import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.batch.JobLauncherApplicationRunner;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Demo1Application implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(Demo1Application.class);

    public static void main(String[] args) {
        System.exit(SpringApplication.exit(SpringApplication.run(Demo1Application.class, args)));
    }

    @Resource
    JobLauncher jobLauncher;

    @Resource
    Job job;

    @Override
    public void run(String... args) throws Exception {
        jobLauncher.run(job, new JobParameters());
    }
}
