package com.example.demo;

import com.example.demo.order.OrderService;
import com.example.demo.order.OrderItem;
import com.example.demo.voucher.FixedAmountVoucher;
import com.example.demo.voucher.VoucherRepository;
import org.springframework.beans.factory.annotation.BeanFactoryAnnotationUtils;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.UUID;

public class OrderTester {
    public static void main(String[] args) {
        var applicationContext =new AnnotationConfigApplicationContext(AppConfiguration.class);

        var customerId = UUID.randomUUID();


        var voucherRepository = BeanFactoryAnnotationUtils.qualifiedBeanOfType(applicationContext.getBeanFactory(), VoucherRepository.class,"memory");
        var voucherRepository2 = BeanFactoryAnnotationUtils.qualifiedBeanOfType(applicationContext.getBeanFactory(), VoucherRepository.class,"memory");
        System.out.println(voucherRepository2);
        System.out.println(voucherRepository);


        var voucher = voucherRepository.insert(new FixedAmountVoucher(UUID.randomUUID(),10L));

        var orderService = applicationContext.getBean(OrderService.class);
        var order = orderService.createOrder(customerId,new ArrayList<OrderItem>(){{
            add(new OrderItem(UUID.randomUUID(),100L,1));
        }},voucher.getVoucherId());
        Assert.isTrue(order.totalAmount()==90L, String.format("totalAmount%d is not 90L",order.totalAmount()));
        applicationContext.close();
    }
}
