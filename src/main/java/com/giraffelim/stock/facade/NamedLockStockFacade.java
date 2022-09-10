package com.giraffelim.stock.facade;

import com.giraffelim.stock.repository.LockRepository;
import com.giraffelim.stock.service.NamedLockStockService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class NamedLockStockFacade {

    private final LockRepository lockRepository;

    private final NamedLockStockService namedLockStockService;

    public NamedLockStockFacade(LockRepository lockRepository, NamedLockStockService namedLockStockService) {
        this.lockRepository = lockRepository;
        this.namedLockStockService = namedLockStockService;
    }

    @Transactional
    public void decrease(Long id, Long quantity) {
        try {
            lockRepository.getLock(id.toString());
            namedLockStockService.decrease(id, quantity);
        } finally {
            lockRepository.releaseLock(id.toString());
        }

    }
}
