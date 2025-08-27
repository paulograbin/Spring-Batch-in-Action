package com.example.batchprocessing.itemProcessors;

import com.example.batchprocessing.Person;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class JustAnotherSimpleItemProcessor implements ItemProcessor<Person, Person> {

    private static final Logger log = LoggerFactory.getLogger(JustAnotherSimpleItemProcessor.class);

    @Resource
    private ContextHolder contextHolder;

    @Override
    public Person process(Person item) {
        contextHolder.context.add("aaaaaaaa");

        log.info("Executing processor BBBBBBB, context has {}", contextHolder.context.size());

        return item;
    }
}
