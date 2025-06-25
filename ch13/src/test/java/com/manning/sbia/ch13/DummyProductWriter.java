/**
 * 
 */
package com.manning.sbia.ch13;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;

import com.manning.sbia.ch13.ThreadUtils;
import com.manning.sbia.ch13.domain.Product;

/**
 * @author acogoluegnes
 *
 */
public class DummyProductWriter implements ItemWriter<Product> {
	
	private List<Product> products = new ArrayList<Product>();


	private void processProduct(Product product) throws InterruptedException {
		Thread.sleep(5);
		synchronized(products) {
			products.add(product);
		}
	}
	
	public List<Product> getProducts() {
		return Collections.unmodifiableList(products);
	}
	
	public void clear() {
		products.clear();
	}

	@Override
	public void write(Chunk<? extends Product> chunk) throws Exception {
		ThreadUtils.writeThreadExecutionMessage("write", chunk.getItems());
		for(Product product : chunk.getItems()) {
			processProduct(product);
		}
	}
}
