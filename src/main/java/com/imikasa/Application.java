package com.imikasa;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Application {

    public static void main(String[] args) {
//        System.out.println(createJWT("ssa"));
        System.out.println(decodeJWT("eyJ0eXAiOiJKV1QiLCJ0eXBlIjoiSldUIiwiYWxnIjoiSFMyNTYifQ.eyJzdWIiOiJUaGlzIGlzIGEgSldUIiwiZXhwIjoxNjUzMjA1MDgwLCJwd2QiOiIxMjM0NTYiLCJpYXQiOjE2NTMxOTc4OTYsInVzZXJuYW1lIjoic3NhIn0.VXC_0jEyJuxVQerw-t7fdmEcfHK6-cMb1ZDAimOjQJA"));
    }

    public static String createJWT(String username){

        Date createTime = new Date();
        Calendar now = Calendar.getInstance();
        now.set(Calendar.SECOND,7200);
        Date expireTime = now.getTime();
        Map<String,Object> header = new HashMap<>(4);
        header.put("alg","HS256");
        header.put("type","JWT");
        return JWT.create().withHeader(header)
                .withIssuedAt(createTime)
                .withExpiresAt(expireTime)
                .withSubject("This is a JWT")
                .withClaim("username",username)
                .withClaim("pwd","123456")
                .sign(Algorithm.HMAC256("Mikasa-0606"));
    }

    public static boolean decodeJWT(String token){
        JWTVerifier build = JWT.require(Algorithm.HMAC256("Mikasa-0606")).build();
        try {
            DecodedJWT verifier = build.verify(token);
            Claim username = verifier.getClaim("username");
            System.out.println(username.asString());
            System.out.println(verifier.getSignature());
            System.out.println(verifier.getSubject());
            return true;
        } catch (JWTVerificationException e) {
            e.printStackTrace();
            return false;
        }
    }

}
