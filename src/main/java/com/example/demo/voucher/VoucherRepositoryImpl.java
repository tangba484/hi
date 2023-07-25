package com.example.demo.voucher;

import java.util.Optional;
import java.util.UUID;

public class VoucherRepositoryImpl implements VoucherRepository {
    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.empty();
    }

    @Override
    public Voucher insert(Voucher voucher) {
        return null;
    }
}
