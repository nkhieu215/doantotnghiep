package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class ThamsotlTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Thamsotl getThamsotlSample1() {
        return new Thamsotl().id(1L).thangnam("thangnam1").ncchuan(1).giocchuan(1).pcan(1).tlbhtn("tlbhtn1");
    }

    public static Thamsotl getThamsotlSample2() {
        return new Thamsotl().id(2L).thangnam("thangnam2").ncchuan(2).giocchuan(2).pcan(2).tlbhtn("tlbhtn2");
    }

    public static Thamsotl getThamsotlRandomSampleGenerator() {
        return new Thamsotl()
            .id(longCount.incrementAndGet())
            .thangnam(UUID.randomUUID().toString())
            .ncchuan(intCount.incrementAndGet())
            .giocchuan(intCount.incrementAndGet())
            .pcan(intCount.incrementAndGet())
            .tlbhtn(UUID.randomUUID().toString());
    }
}
