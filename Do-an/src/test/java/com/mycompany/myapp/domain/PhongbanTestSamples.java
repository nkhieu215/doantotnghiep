package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class PhongbanTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Phongban getPhongbanSample1() {
        return new Phongban().id(1L).mapb("mapb1").tenpb("tenpb1").sdt(1);
    }

    public static Phongban getPhongbanSample2() {
        return new Phongban().id(2L).mapb("mapb2").tenpb("tenpb2").sdt(2);
    }

    public static Phongban getPhongbanRandomSampleGenerator() {
        return new Phongban()
            .id(longCount.incrementAndGet())
            .mapb(UUID.randomUUID().toString())
            .tenpb(UUID.randomUUID().toString())
            .sdt(intCount.incrementAndGet());
    }
}
