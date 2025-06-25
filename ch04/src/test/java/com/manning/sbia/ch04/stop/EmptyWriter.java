/**
 * 
 */
package com.manning.sbia.ch04.stop;

import java.util.List;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;

/**
 * @author acogoluegnes
 *
 */
public class EmptyWriter implements ItemWriter<String> {

	@Override
	public void write(Chunk<? extends String> chunk) throws Exception {

	}
}
