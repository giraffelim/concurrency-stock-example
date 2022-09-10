package com.giraffelim.stock.service;

import com.giraffelim.stock.domain.Stock;
import com.giraffelim.stock.repository.StockRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PessimisticLockStoreService {

    private StockRepository stockRepository;

    public PessimisticLockStoreService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Transactional
    public void decrease(Long id, Long quantity) {
        Stock stock = stockRepository.findByIdWithPessimisticLock(id);
        stock.decrease(quantity);
    }
}
