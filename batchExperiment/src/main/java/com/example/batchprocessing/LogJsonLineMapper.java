package com.example.batchprocessing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.JsonLineMapper;

import java.util.Map;

public class LogJsonLineMapper implements LineMapper<JsonLog> {

    private static final Logger LOG = LoggerFactory.getLogger(LogJsonLineMapper.class);

    private JsonLineMapper delegate;

    public JsonLog mapLine(String line, int lineNumber) throws Exception {
        Map<String, Object> productAsMap = delegate.mapLine(line, lineNumber);

        JsonLog product = new JsonLog();
        String o = (String) productAsMap.get("stream");

        LOG.info(o);

        return product;
    }

    public void setDelegate(JsonLineMapper delegate) {
        this.delegate = delegate;
    }

}
