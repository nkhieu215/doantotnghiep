package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class TanggiamtlTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Tanggiamtl getTanggiamtlSample1() {
        return new Tanggiamtl().id(1L).ngaythang("ngaythang1").tkn(1).tkc(1).diengiai("diengiai1");
    }

    public static Tanggiamtl getTanggiamtlSample2() {
        return new Tanggiamtl().id(2L).ngaythang("ngaythang2").tkn(2).tkc(2).diengiai("diengiai2");
    }

    public static Tanggiamtl getTanggiamtlRandomSampleGenerator() {
        return new Tanggiamtl()
            .id(longCount.incrementAndGet())
            .ngaythang(UUID.randomUUID().toString())
            .tkn(intCount.incrementAndGet())
            .tkc(intCount.incrementAndGet())
            .diengiai(UUID.randomUUID().toString());
    }
}
