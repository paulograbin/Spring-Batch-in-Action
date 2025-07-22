package com.example.batchprocessing.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.listener.RetryListenerSupport;

public class PauloRetryListener extends RetryListenerSupport {

    private static final Logger LOG = LoggerFactory.getLogger(PauloRetryListener.class);

    @Override
    public <T, E extends Throwable> void onError(RetryContext context, RetryCallback<T, E> callback, Throwable throwable) {
        LOG.info("Error on retry {}", throwable);
    }
}
