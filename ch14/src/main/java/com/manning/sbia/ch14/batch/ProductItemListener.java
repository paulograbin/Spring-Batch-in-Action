/**
 *
 */
package com.manning.sbia.ch14.batch;

import com.manning.sbia.ch14.domain.Product;
import org.springframework.batch.core.listener.ItemListenerSupport;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author bazoud
 */
public class ProductItemListener extends ItemListenerSupport<Product, Product> {
    private FlatFileItemWriter<Product> excludeWriter;

    @Override
    public void afterProcess(Product item, Product result) {
        if (result == null) {
            try {
                Chunk<Product> chunk = new Chunk<>();
                chunk.add(item);

                excludeWriter.write(chunk);
            } catch (Exception e) {
            }
        }
    }

    public void setExcludeWriter(FlatFileItemWriter<Product> excludeWriter) {
        this.excludeWriter = excludeWriter;
    }
}
