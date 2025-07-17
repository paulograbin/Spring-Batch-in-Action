package com.example.batchprocessing;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.JsonLineMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.io.File;

@Configuration
public class BatchConfiguration {

    private final String toProcessDirectory = "/home/paulograbin/Desktop/workzone/toProcess";
    private final String processedDirectory = "/home/paulograbin/Desktop/workzone/processed";


    @Bean
    public FlatFileItemReader<Person> reader() {
        return new FlatFileItemReaderBuilder<Person>()
                .name("personItemReader")
                .resource(new ClassPathResource("sample-data.csv"))
                .delimited()
                .names("firstName", "lastName")
                .targetType(Person.class)
                .build();
    }

    @Bean
    public PersonItemProcessor processor() {
        return new PersonItemProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<Person> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Person>()
                .sql("INSERT INTO people (first_name, last_name) VALUES (:firstName, :lastName)")
                .dataSource(dataSource)
                .beanMapped()
                .build();
    }

    @Bean
    public Job importUserJob(JobRepository jobRepository, Step decompressStep, Step step2, JobCompletionNotificationListener listener) {
        return new JobBuilder("importUserJob", jobRepository)
                .listener(listener)
                .start(decompressStep)
//                .start(step1)
//                .next(step2)
                .build();
    }

    @Bean
    public Step step1(JobRepository jobRepository, DataSourceTransactionManager transactionManager, FlatFileItemReader<Person> reader, PersonItemProcessor processor, JdbcBatchItemWriter<Person> writer) {
        return new StepBuilder("step1", jobRepository)
                .<Person, Person>chunk(3, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public Tasklet pirocoTasklet() {
        return new PirocoTasklet();
    }


    @Bean
    public LogJsonLineMapper logJsonLineMapper() {
        var a = new LogJsonLineMapper();

        a.setDelegate(jsonLineMapper());

        return a;
    }

    @Bean
    public JsonLineMapper jsonLineMapper() {
        return new JsonLineMapper();
    }


    @Bean
    public Tasklet decompressTasklet() {
        File inputDirectory = new File(toProcessDirectory);

        return new DecompressTasklet(inputDirectory, processedDirectory, "test");
    }

    @Bean
    public Step step2(JobRepository jobRepository, DataSourceTransactionManager transactionManager, FlatFileItemReader<Person> reader, PersonItemProcessor processor, JdbcBatchItemWriter<Person> writer) {
        return new StepBuilder("step2", jobRepository)
                .tasklet(pirocoTasklet(), transactionManager)
                .build();
    }

    @Bean
    public Step decompressStep(JobRepository jobRepository, DataSourceTransactionManager transactionManager, FlatFileItemReader<Person> reader, PersonItemProcessor processor, JdbcBatchItemWriter<Person> writer) {
        return new StepBuilder("step2", jobRepository)
                .tasklet(decompressTasklet(), transactionManager)
                .build();
    }
}
