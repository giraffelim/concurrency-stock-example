package com.giraffelim.stock.service;

import com.giraffelim.stock.domain.Stock;
import com.giraffelim.stock.repository.StockRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class StockService {

    private StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

//    @Transactional
    public synchronized void decrease (Long id, Long quantity) {
        // get stock
        Stock stock = stockRepository.findById(id).orElseThrow();
        // 재고 감소
        stock.decrease(quantity);
        // 트랜잭션을 주석처리 했으므로 saveAndFlush() 호출
        stockRepository.saveAndFlush(stock);
    }
}
