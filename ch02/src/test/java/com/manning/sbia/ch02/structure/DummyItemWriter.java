/**
 *
 */
package com.manning.sbia.ch02.structure;

import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;

import com.manning.sbia.ch01.domain.Product;

/**
 * @author acogoluegnes
 *
 */
public class DummyItemWriter implements ItemWriter<Product> {

    public List<Product> products = new ArrayList<Product>();


    @Override
    public void write(Chunk<? extends Product> chunk) throws Exception {
        products.addAll(chunk.getItems());
    }
}
