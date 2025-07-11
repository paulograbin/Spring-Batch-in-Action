package com.manning.sbia.ch07;

import org.springframework.batch.item.ItemProcessor;

import com.manning.sbia.ch01.domain.Product;

import static org.apache.commons.lang3.math.NumberUtils.*;


public class FilteringProductItemProcessor implements ItemProcessor<Product, Product> {

	@Override
	public Product process(Product item) throws Exception {
		return needsToBeFiltered(item) ? null : item;
	}
	
	private boolean needsToBeFiltered(Product item) {
		String id = item.getId();
		String lastDigit = id.substring(id.length()-1, id.length());
		if(isDigits(lastDigit)) {
			return toInt(lastDigit) % 2 == 1;
		} else {
			return false;
		}		 
	}

}
