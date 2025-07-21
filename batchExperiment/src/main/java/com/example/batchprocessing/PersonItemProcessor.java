package com.example.batchprocessing;

import com.example.batchprocessing.exceptions.RandomOcurringException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import java.util.Random;

public class PersonItemProcessor implements ItemProcessor<Person, Person> {

	private static final Logger log = LoggerFactory.getLogger(PersonItemProcessor.class);

	@Override
	public Person process(final Person person) {
		final String firstName = person.firstName.toUpperCase();
		final String lastName = person.lastName.toUpperCase();

		final Person transformedPerson = new Person();
		transformedPerson.firstName = firstName;
		transformedPerson.lastName = lastName;

		log.info("Converting ({}) into ({})", person, transformedPerson);

		int i = new Random().nextInt(10);
		if (i > 6) {
			log.info("Falhou!!!!!!!!!! {} - {}", person, i);
			throw new RandomOcurringException("Agora deu " + i);
		} else {
			log.info("Não falhou " + i);
		}

		return transformedPerson;
	}

}
