package com.example.batchprocessing;

import com.example.batchprocessing.exceptions.RandomOcurringException;
import com.example.batchprocessing.exceptions.RandomSkipException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import java.time.Instant;
import java.util.Date;
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
        transformedPerson.creationTime = new Date();

        log.info("Converting ({}) into ({})", person, transformedPerson);


        int skipRandom = random.nextInt(10);
        if (skipRandom == 1) {
            log.warn(person.getFirstName() + " will be skipped!!!!");
            throw new RandomSkipException("Skipped " + person + " randomly...");
        }

        int failRandom = random.nextInt(10);
        if (failRandom > 8) {
            failCount++;
            log.info("Falhou no {} !!!!!!!!!! Já falharam {}, e o resultado foi {}", person, failCount, failRandom);

            boolean b = random.nextBoolean();

//            if (b) {
                throw new RandomOcurringException("Agora deu " + failRandom);
//            } else {
//                throw new CosmicRayException("ABOOOOORT");
//            }

        } else {
//            log.info("Não falhou " + failRandom);
        }

        return transformedPerson;
    }

}
