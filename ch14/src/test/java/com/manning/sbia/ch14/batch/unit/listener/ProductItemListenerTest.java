package com.manning.sbia.ch14.batch.unit.listener;

import com.manning.sbia.ch14.batch.ProductItemListener;
import com.manning.sbia.ch14.domain.Product;
import org.junit.Before;
import org.junit.Test;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.file.FlatFileItemWriter;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class ProductItemListenerTest {
    private Product p = null;
    private FlatFileItemWriter<Product> writer = null;
    private List<Product> items = null;

    @Before
    public void setUp() {
        p = new Product();
        p.setId("211");
        p.setName("BlackBerry");
        items = Collections.singletonList(p);
        writer = mock(FlatFileItemWriter.class);
    }

    @Test
    public void testAfterProcess() throws Exception {
        ProductItemListener listener = new ProductItemListener();
        listener.setExcludeWriter(writer);
        listener.afterProcess(p, null);

        Chunk<Product> chunk = new Chunk<>(items);
        verify(writer, times(1)).write(chunk);
    }

    @Test
    public void testAfterProcessResult() throws Exception {
        ProductItemListener listener = new ProductItemListener();
        listener.setExcludeWriter(writer);
        listener.afterProcess(p, p);
        Chunk<Product> chunk = new Chunk<>(items);
        verify(writer, times(1)).write(chunk);
    }
}
