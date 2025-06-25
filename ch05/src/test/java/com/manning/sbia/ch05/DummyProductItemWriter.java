/**
 *
 */
package com.manning.sbia.ch05;

import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;

import com.manning.sbia.ch05.Product;

/**
 * @author acogoluegnes
 *
 */
public class DummyProductItemWriter implements ItemWriter<Product> {

    public List<Product> products = new ArrayList<Product>();


    public List<Product> getProducts() {
        return products;
    }

    @Override
    public void write(Chunk<? extends Product> chunk) {
//System.out.println("items = "+items.size());
        products.addAll(chunk.getItems());
    }
}
