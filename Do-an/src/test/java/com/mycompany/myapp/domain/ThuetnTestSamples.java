package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class ThuetnTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Thuetn getThuetnSample1() {
        return new Thuetn().id(1L).bacthue("bacthue1").tu(1).den(1);
    }

    public static Thuetn getThuetnSample2() {
        return new Thuetn().id(2L).bacthue("bacthue2").tu(2).den(2);
    }

    public static Thuetn getThuetnRandomSampleGenerator() {
        return new Thuetn()
            .id(longCount.incrementAndGet())
            .bacthue(UUID.randomUUID().toString())
            .tu(intCount.incrementAndGet())
            .den(intCount.incrementAndGet());
    }
}
