package com.manning.sbia.ch05;

import org.springframework.batch.item.ItemWriter;

import java.util.ArrayList;
import java.util.List;


public class DummyProductItemWriter implements ItemWriter<Product> {

    public List<Product> products = new ArrayList<>();

    @Override
    public void write(List<? extends Product> items) throws Exception {
        //System.out.println("items = "+items.size());
        products.addAll(items);
    }

    public List<Product> getProducts() {
        return products;
    }
}
