package com.example.demo.voucher;

import com.example.demo.aop.TrackTime;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Qualifier("memory")
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
@Primary
@Profile({"local","test"})
public class MemoryVoucherRepository implements VoucherRepository/*, InitializingBean, DisposableBean */{
    private final Map <UUID,Voucher> storage = new ConcurrentHashMap<>();
    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(storage.get(voucherId));
    }

    @Override
    @TrackTime
    public Voucher insert(Voucher voucher) {
        storage.put(voucher.getVoucherId(),voucher);
        return voucher;
    }

//    @PostConstruct
//    public void postConstruct(){
//        System.out.println("postconstruct called!");
//    }
//
//    @Override
//    public void afterPropertiesSet() throws Exception {
//        System.out.println("afterPropertiesSet called!");
//
//    }
//    @PreDestroy
//    public void preConstruct(){
//        System.out.println("preconstruct called!");
//    }
//
//    @Override
//    public void destroy() throws Exception {
//        System.out.println("destroy called!");
//    }
}
