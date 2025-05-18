package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class ChucvuTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Chucvu getChucvuSample1() {
        return new Chucvu().id(1L).macv("macv1").tencv("tencv1").hcpccv(1);
    }

    public static Chucvu getChucvuSample2() {
        return new Chucvu().id(2L).macv("macv2").tencv("tencv2").hcpccv(2);
    }

    public static Chucvu getChucvuRandomSampleGenerator() {
        return new Chucvu()
            .id(longCount.incrementAndGet())
            .macv(UUID.randomUUID().toString())
            .tencv(UUID.randomUUID().toString())
            .hcpccv(intCount.incrementAndGet());
    }
}
