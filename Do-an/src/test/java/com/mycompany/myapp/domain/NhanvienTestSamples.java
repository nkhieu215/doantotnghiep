package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class NhanvienTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Nhanvien getNhanvienSample1() {
        return new Nhanvien().id(1L).manv("manv1").hoten("hoten1").gioitinh("gioitinh1").quequan("quequan1").diachi("diachi1").msthue(1);
    }

    public static Nhanvien getNhanvienSample2() {
        return new Nhanvien().id(2L).manv("manv2").hoten("hoten2").gioitinh("gioitinh2").quequan("quequan2").diachi("diachi2").msthue(2);
    }

    public static Nhanvien getNhanvienRandomSampleGenerator() {
        return new Nhanvien()
            .id(longCount.incrementAndGet())
            .manv(UUID.randomUUID().toString())
            .hoten(UUID.randomUUID().toString())
            .gioitinh(UUID.randomUUID().toString())
            .quequan(UUID.randomUUID().toString())
            .diachi(UUID.randomUUID().toString())
            .msthue(intCount.incrementAndGet());
    }
}
