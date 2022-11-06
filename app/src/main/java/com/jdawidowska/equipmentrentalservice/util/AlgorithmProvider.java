package com.jdawidowska.equipmentrentalservice.util;

import com.auth0.jwt.algorithms.Algorithm;

public class AlgorithmProvider {

    public static Algorithm get() {
        return Algorithm.HMAC256("secret".getBytes());
    }
}