package com.example.demo;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.example.demo.order","com.example.demo.voucher"})
public class AppConfiguration {


//    @Bean , memory order,voucher Repo에 @repo 등록을 해서 자동으로 의존성 주입됨,아래코드지워도 작동
//    public VoucherRepository voucherRepository(){
//        return new VoucherRepository() {
//            @Override
//            public Optional<Voucher> findById(UUID voucherId) {
//                return Optional.empty();
//            }
//        };
//    }
//    @Bean
//    public OrderRepository orderRepository(){
//        return new OrderRepository() {
//            @Override
//            public Order insert(Order order) {
//                return order;
//            }
//        };
//    }

    // order , voucher service 에 @service annotation 달아서 이 블록을 없애도 작동함
//    @Bean
//    public VoucherService voucherService(VoucherRepository voucherRepository){
//        return new VoucherService(voucherRepository);
//    }
//    @Bean
//    public OrderService orderService(VoucherService voucherService,OrderRepository orderRepository){
//        return new OrderService(voucherService,orderRepository);
//    }

/*
     위의 코드가 bean을 활용한 의존성 자동주입코드 , 아래 코드는 위존성을 주입 후에 bean을 이용해서 활용
    @Bean
    public VoucherService voucherService(){
        return new VoucherService(voucherRepository());
    }
    @Bean
    public OrderService orderService(){
        return new OrderService(voucherService(),orderRepository());
    }
 */
}
