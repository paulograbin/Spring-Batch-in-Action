package com.example.batchprocessing;

import com.example.batchprocessing.exceptions.CosmicRayException;
import com.example.batchprocessing.exceptions.RandomOcurringException;
import com.example.batchprocessing.itemProcessors.JustAnotherSimpleItemProcessor;
import com.example.batchprocessing.itemProcessors.JustOneSimpleItemProcessor;
import com.example.batchprocessing.listeners.PauloCustomChunkListener;
import com.example.batchprocessing.listeners.PauloItemReaderListener;
import com.example.batchprocessing.listeners.PauloRetryListener;
import com.example.batchprocessing.listeners.PauloSkipListener;
import com.example.batchprocessing.listeners.PauloStepListener;
import com.example.batchprocessing.skip.PauloExceptionSkiperPolicy;
import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.SkipListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.JsonLineMapper;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.retry.RetryListener;
import org.springframework.retry.RetryPolicy;
import org.springframework.retry.policy.ExceptionClassifierRetryPolicy;
import org.springframework.retry.policy.MaxAttemptsRetryPolicy;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;

import javax.sql.DataSource;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

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
                .sql("INSERT INTO people (firstName, lastName, creationTime) VALUES (:firstName, :lastName, :creationTime)")
                .dataSource(dataSource)
                .beanMapped()
                .build();
    }

    @Bean
    public Job importUserJob(JobRepository jobRepository, Step decompressStep, Step step1, Step secondStepWithCompositeProcessor, Step pirocoStep, JobCompletionNotificationListener listener) {
        return new JobBuilder("importUserJob", jobRepository)
                .listener(listener)
                .start(step1)
                .start(secondStepWithCompositeProcessor)
                .next(myDecider())
                    .on("REPEAT")
                    .to(pirocoStep)
                    .on("END")
                    .end()
                    .on("*").end()
                .build()
                .build();
    }

    private JobExecutionDecider myDecider() {
        return new PauloJobExecutionDecider();
    }

    @Bean
    public Step step1(JobRepository jobRepository, DataSourceTransactionManager transactionManager, FlatFileItemReader<Person> reader, PersonItemProcessor personItemProcessor, JdbcBatchItemWriter<Person> writer) {
        var threeAttempts = new MaxAttemptsRetryPolicy();
        threeAttempts.setMaxAttempts(3);

        var exceptionPolicy = new ExceptionClassifierRetryPolicy();

        Map<Class<? extends Throwable>, RetryPolicy> exceptionMap = new HashMap<>();
        exceptionMap.put(CosmicRayException.class, new MaxAttemptsRetryPolicy(RetryPolicy.NO_MAXIMUM_ATTEMPTS_SET));
        exceptionMap.put(RandomOcurringException.class, threeAttempts);

        exceptionPolicy.setPolicyMap(exceptionMap);

        return new StepBuilder("regular-reader-processor-writer", jobRepository)
                .<Person, Person>chunk(3, transactionManager)
                .allowStartIfComplete(true)
                .reader(reader)
                .processor(personItemProcessor)
                .writer(writer)
                .transactionAttribute(new DefaultTransactionAttribute(TransactionDefinition.ISOLATION_READ_UNCOMMITTED))
                .chunk(5)
                .faultTolerant()
                .listener(retryListener())
                .retryLimit(3)
                .skipPolicy(skipPolicy())
                .retryPolicy(exceptionPolicy)
                .listener(stepListener())
                .listener(skipListener())
                .listener(chunkListener())
                .listener(itemReaderListener())
                .build();
    }

    @Bean
    public Step secondStepWithCompositeProcessor(JobRepository jobRepository, DataSourceTransactionManager transactionManager, FlatFileItemReader<Person> reader, JdbcBatchItemWriter<Person> writer, ItemProcessor<Person, Person> compositeItemProcessor) {
        return new StepBuilder("secondStep-compositeProcessors", jobRepository)
                .<Person, Person>chunk(3, transactionManager)
                .allowStartIfComplete(true)
                .listener(stepListener())
                .faultTolerant()
                .reader(reader)
                .processor(compositeItemProcessor)
                .writer(writer)
                .build();
    }

    private StepExecutionListener stepListener() {
        return new PauloStepListener();
    }

    private PauloExceptionSkiperPolicy skipPolicy() {
        return new PauloExceptionSkiperPolicy();
    }

    private RetryListener retryListener() {
        return new PauloRetryListener();
    }

//    @Bean
//    public RetryPolicy retryPolicy() {
//        SimpleRetryPolicy fiveAttemps = new SimpleRetryPolicy();
//        fiveAttemps.setMaxAttempts(5);
//
//        SimpleRetryPolicy threeAttemps = new SimpleRetryPolicy();
//        fiveAttemps.setMaxAttempts(3);
//
//        ExceptionClassifierRetryPolicy exceptionClassifierRetryPolicy = new ExceptionClassifierRetryPolicy();
//
//        Map<Class<? extends Throwable>, RetryPolicy> aaa = new HashMap<>();
//        aaa.put(RuntimeException.class, fiveAttemps);
//        aaa.put(IllegalArgumentException.class, threeAttemps);
//
//        exceptionClassifierRetryPolicy.setPolicyMap(aaa);
//
//        return exceptionClassifierRetryPolicy;
//    }

    private SkipListener<Person, Person> skipListener() {
        return new PauloSkipListener();
    }

    private PauloItemReaderListener itemReaderListener() {
        return new PauloItemReaderListener();
    }

    public ChunkListener chunkListener() {
        return new PauloCustomChunkListener();
    }

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
    public Step pirocoStep(JobRepository jobRepository, DataSourceTransactionManager transactionManager) {
        return new StepBuilder("pirocoStep", jobRepository)
                .tasklet(pirocoTasklet(), transactionManager)
                .transactionAttribute(new DefaultTransactionAttribute(TransactionDefinition.PROPAGATION_NEVER))
                .allowStartIfComplete(true)
                .build();
    }

    @Bean
    public Step decompressStep(JobRepository jobRepository, DataSourceTransactionManager transactionManager) {
        return new StepBuilder("decompressStep", jobRepository)
                .tasklet(decompressTasklet(), transactionManager)
                .build();
    }

    @Bean
    public ItemProcessor<Person, Person> compositeItemProcessor(JustOneSimpleItemProcessor justOneSimpleItemProcessor, JustAnotherSimpleItemProcessor justAnotherSimpleItemProcessor) {
        return new CompositeItemProcessor<>(justAnotherSimpleItemProcessor, justOneSimpleItemProcessor);
    }

    @Bean
    public JustOneSimpleItemProcessor justOneSimpleItemProcessor() {
        return new JustOneSimpleItemProcessor();
    }

    @Bean
    public JustAnotherSimpleItemProcessor justAnotherSimpleItemProcessor() {
        return new JustAnotherSimpleItemProcessor();
    }


}
