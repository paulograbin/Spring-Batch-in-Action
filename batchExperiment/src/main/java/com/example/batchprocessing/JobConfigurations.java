package com.example.batchprocessing;

import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JobConfigurations extends DefaultBatchConfiguration {

//
//    @Bean
//    public FlatFileItemReader<Product> reader() {
//        return new FlatFileItemReaderBuilder<Product>()
//                .delimited()
//                .names("PRODUCT_ID", "NAME", "DESCRIPTION", "PRICE")
//                .resource(new ClassPathResource("input/products.txt"))
//                .name("productReader")
//                .targetType(Product.class)
//                .build();
//    }
//
//    @Bean
//    public FlatFileItemWriter<Product> writer() {
//        return new FlatFileItemWriterBuilder<Product>().delimited()
//                .delimiter(";")
//                .names("PRODUCT", "NAME")
//                .name("myWriter")
//                .build();
//    }
//
//    @Bean
//    public Job importUserJob(JobRepository jobRepository, Step step1, JobCompletionNotificationListener listener) {
//        return new JobBuilder("importUserJob", jobRepository)
//                .listener(listener)
//                .start(step1)
//                .build();
//    }
//
//    @Bean
//    public Step step1(JobRepository jobRepository, DataSourceTransactionManager transactionManager, FlatFileItemReader<Product> reader, ProductProcessor processor, FlatFileItemWriter<Product> writer) {
//        return new StepBuilder("step1", jobRepository)
//                .<Product, Product>chunk(3, transactionManager)
//                .reader(reader)
//                .processor(processor)
//                .writer(writer)
//                .build();
//    }
}
