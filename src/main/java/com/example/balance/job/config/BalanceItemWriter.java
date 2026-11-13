/**
 * 
 */
package com.example.balance.job.config;

import org.springframework.batch.infrastructure.item.Chunk;
import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.example.balance.dao.OutputDetailRecord;
import com.example.balance.repository.BalanceRepository;

/**
 * 
 */

@Component
public class BalanceItemWriter implements ItemWriter<OutputDetailRecord> {

	@Autowired
	private BalanceRepository balanceRepository;

	
	@Override
	public void write(Chunk<? extends OutputDetailRecord> chunk) throws Exception {
		// TODO Auto-generated method stub
		balanceRepository.saveAll(chunk.getItems());
	}

}
