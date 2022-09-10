package com.giraffelim.stock.facade;

import com.giraffelim.stock.service.RedissonLockStockService;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedissonLockStockFacade {

    private final RedissonClient redissonClient;

    private final RedissonLockStockService redissonLockStockService;

    public RedissonLockStockFacade(RedissonClient redissonClient, RedissonLockStockService redissonLockStockService) {
        this.redissonClient = redissonClient;
        this.redissonLockStockService = redissonLockStockService;
    }

    public void decrease(Long key, Long quantity) {
        RLock lock = redissonClient.getLock(key.toString());

        try {
            // 몇초동안 lock 획득을 시도할 것인지, 몇초동안 점유할 것인지 설정
            boolean available = lock.tryLock(5, 1, TimeUnit.SECONDS);

            // lock을 획득할 수 없다면
            if (!available) {
                System.out.println("lock 획득 실패");
                return;
            }

            // lock을 획득한 경우
            redissonLockStockService.decrease(key, quantity);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }
}
