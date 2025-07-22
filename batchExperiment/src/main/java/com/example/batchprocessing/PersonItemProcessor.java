package com.example.batchprocessing;

import com.example.batchprocessing.exceptions.CosmicRayException;
import com.example.batchprocessing.exceptions.RandomAbortException;
import com.example.batchprocessing.exceptions.RandomOcurringException;
import com.example.batchprocessing.exceptions.RandomSkipException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import java.util.Random;

public class PersonItemProcessor implements ItemProcessor<Person, Person> {

    private static final Logger log = LoggerFactory.getLogger(PersonItemProcessor.class);

    private int failCount;

    private Random random;

    public PersonItemProcessor() {
        failCount = 0;
         random = new Random();
    }

    @Override
    public Person process(final Person person) {
        final String firstName = person.firstName.toUpperCase();
        final String lastName = person.lastName.toUpperCase();

        final Person transformedPerson = new Person();
        transformedPerson.firstName = firstName;
        transformedPerson.lastName = lastName;

        log.info("Converting ({}) into ({})", person, transformedPerson);


        int i1 = random.nextInt(10);
        if (i1 == 1) {
            log.warn(person.getFirstName() + " will be skipped!!!!");
            throw new RandomSkipException("Skipped " + person + " randomly...");
        }

        int i = random.nextInt(10);
        if (i > 6) {
            failCount++;
            log.info("Falhou {} !!!!!!!!!! {} - {}", failCount, person, i);

            boolean b = random.nextBoolean();

            if (b) {
                throw new RandomOcurringException("Agora deu " + i);
            } else {
//                throw new CosmicRayException("ABOOOOORT");
            }

        } else {
//            log.info("Não falhou " + i);
        }

        return transformedPerson;
    }

}
