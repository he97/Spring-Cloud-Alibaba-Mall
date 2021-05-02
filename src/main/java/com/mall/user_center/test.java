package com.mall.user_center;

import java.util.UUID;

public class test {
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            System.out.println(Math.abs(UUID.randomUUID().hashCode()));

        }

    }
}
