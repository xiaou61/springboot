package com.itheima;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

@SpringBootTest
class TliasWebManagementApplicationTests {

    @Test
    void contextLoads() {
    }
    @Test
    public void testUuid(){
        for (int i = 0; i < 10; i++) {
            String s = UUID.randomUUID().toString();
            System.out.println(s);
        }
    }

    @Test
    public void testGenJWt(){
        HashMap<String, Object> claims = new HashMap<>();
        claims.put("id","1");
        claims.put("name", "Tom");
        String jwt = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, "itheima")//签名算法
                .setClaims(claims)//自定义内容(载荷)
                .setExpiration(new Date(System.currentTimeMillis() + 3600 * 1000))//设置有效期为一个小时
                .compact();
        System.out.println(jwt);
    }


    @Test
    public void testParesJWt() {
        Claims claims = Jwts.parser().setSigningKey("itheima")
                .parseClaimsJws("eyJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoiVG9tIiwiaWQiOiIxIiwiZXhwIjoxNjgwMjU3NTM5fQ.x1vbqc-U-kolsNK3r6GaKziqA7Sj1UMnEXidUfBTGG0")
                .getBody();
        System.out.println(claims);
    }
}
