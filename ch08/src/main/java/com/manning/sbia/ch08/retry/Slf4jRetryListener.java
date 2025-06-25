/**
 * 
 */
package com.manning.sbia.ch08.retry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.listener.RetryListenerSupport;

/**
 * @author acogoluegnes
 *
 */
@SuppressWarnings("removal")
public class Slf4jRetryListener extends RetryListenerSupport {
	
	private static final Logger LOG = LoggerFactory.getLogger(Slf4jRetryListener.class);

	@Override
	public <T, E extends Throwable> void onError(org.springframework.retry.RetryContext context, org.springframework.retry.RetryCallback<T, E> callback, Throwable throwable) {
		super.onError(context, callback, throwable);

		LOG.error("retried operation",throwable);
	}

}
