/**
 *
 */
package com.manning.sbia.ch08;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;

/**
 * @author acogoluegnes
 *
 */
public class DummyItemWriter implements ItemWriter<String> {

    private static final Logger LOG = LoggerFactory.getLogger(DummyItemWriter.class);

    private BusinessService service;

    public DummyItemWriter(BusinessService service) {
        super();
        this.service = service;
    }

    @Override
    public void write(Chunk<? extends String> chunk) throws Exception {
        for (String item : chunk.getItems()) {
            LOG.debug("writing " + item);
            service.writing(item);
            LOG.debug("item written " + item);
        }
    }
}
