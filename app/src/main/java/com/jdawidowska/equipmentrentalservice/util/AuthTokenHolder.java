package com.jdawidowska.equipmentrentalservice.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.jdawidowska.equipmentrentalservice.model.Role;

import java.util.List;

public class AuthTokenHolder {

    private static String authToken = "";

    private AuthTokenHolder() {
    }

    public static String getAuthToken() {
        return authToken;
    }

    public static void setAuthToken(String authToken) {
        AuthTokenHolder.authToken = authToken;
    }

    public static String getUsername() {
        return decodeJWT().getSubject(); //todo nullable
    }

    public static String getUserId() {
        return decodeJWT().getClaim("userId").asString(); //todo nullable
    }

    public static String getName() {
        return decodeJWT().getClaim("name").asString(); //todo nullable
    }

    public static Role getRole() {
        List<String> roles = decodeJWT().getClaim("roles").asList(String.class);
        if (roles.contains("ADMIN")) {
            return Role.ADMIN;
        } else {
            return Role.USER;
        }
    }

    private static DecodedJWT decodeJWT() {
        Algorithm algorithm = AlgorithmProvider.get();
        JWTVerifier verifier = JWT.require(algorithm).build();
        return verifier.verify(authToken);
    }
}