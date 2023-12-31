package com.example.demo.voucher;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher {
    private static final long MAX_VOUCHER_AMOUNT = 10000;
    private final UUID voucherId;
    private final long amount;

    public FixedAmountVoucher(UUID voucherId, long amount) {
        if (amount<0) throw new IllegalArgumentException("amount should be positive");
        if (amount == 0) throw new IllegalArgumentException("Amount should not be zero ");
        if (amount >MAX_VOUCHER_AMOUNT) throw new IllegalArgumentException(String.format("Amount should be leen than %d",(MAX_VOUCHER_AMOUNT)));
        this.voucherId = voucherId;
        this.amount = amount;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    public long discount(long beforeDiscount){
        var discountAmount =beforeDiscount - amount;
        return (discountAmount <0) ?0:discountAmount;
    }

    @Override
    public String toString() {
        return "FixedAmountVoucher{" +
                "voucherId=" + voucherId +
                ", amount=" + amount +
                '}';
    }
}
