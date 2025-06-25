package com.manning.sbia.ch09.transaction;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.jms.core.JmsTemplate;

public class JmsItemReader<T> implements ItemReader<T> {

    private JmsTemplate jmsTemplate;

    private static final Logger LOG = LoggerFactory.getLogger(JmsItemReader.class);

    @Override
    public T read() throws UnexpectedInputException, ParseException, NonTransientResourceException {
        var object = jmsTemplate.receive();
        LOG.debug("read " + object);
        return (T) object;
    }

    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

}
