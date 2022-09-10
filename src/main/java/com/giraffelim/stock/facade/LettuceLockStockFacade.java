package com.giraffelim.stock.facade;

import com.giraffelim.stock.repository.RedisLockRepository;
import com.giraffelim.stock.service.LettuceLockStockService;
import org.springframework.stereotype.Component;

@Component
public class LettuceLockStockFacade {

    private final RedisLockRepository redisLockRepository;

    private final LettuceLockStockService lettuceLockStockService;

    public LettuceLockStockFacade(RedisLockRepository redisLockRepository, LettuceLockStockService lettuceLockStockService) {
        this.redisLockRepository = redisLockRepository;
        this.lettuceLockStockService = lettuceLockStockService;
    }

    public void decrease(Long key, Long quantity) throws InterruptedException {
        while (!redisLockRepository.lock(key)) {
            Thread.sleep(100);
        }

        try {
            lettuceLockStockService.decrease(key, quantity);
        } finally {
            redisLockRepository.unlock(key);
        }
    }

}
