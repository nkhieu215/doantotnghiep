package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class BangchamcongTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Bangchamcong getBangchamcongSample1() {
        return new Bangchamcong().id(1L).ncdilam(1).thangcc("thangcc1").nclephep(1).xeploai("xeploai1").ngayththuong(1).ngaythle(1);
    }

    public static Bangchamcong getBangchamcongSample2() {
        return new Bangchamcong().id(2L).ncdilam(2).thangcc("thangcc2").nclephep(2).xeploai("xeploai2").ngayththuong(2).ngaythle(2);
    }

    public static Bangchamcong getBangchamcongRandomSampleGenerator() {
        return new Bangchamcong()
            .id(longCount.incrementAndGet())
            .ncdilam(intCount.incrementAndGet())
            .thangcc(UUID.randomUUID().toString())
            .nclephep(intCount.incrementAndGet())
            .xeploai(UUID.randomUUID().toString())
            .ngayththuong(intCount.incrementAndGet())
            .ngaythle(intCount.incrementAndGet());
    }
}
