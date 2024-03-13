package com.cencoe.cencoe.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class JwtService {
    public static final String JWT_FIRMA = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEArzC3g7mJq7l5DWDKE";
    public static final long JWT_EXPIRATION_TOKEN = 3600000;
    private static final Logger logger = LoggerFactory.getLogger(JwtService.class);


    public String generateToken(Authentication authentication){
        logger.info("Generando token para autenticación {}", authentication);
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Date now = new Date(System.currentTimeMillis());
        Date expirationDate = new Date(now.getTime() + JWT_EXPIRATION_TOKEN);
        String token = Jwts.builder()
                .subject(userDetails.getUsername())
                .claim("userName", userDetails.getFirstName())
                .claim("userLastName", userDetails.getLastName())
                .issuedAt(now)
                .expiration(expirationDate)
                .signWith(setSignIngKey())
                .compact();
        logger.info("Token generado: {}", token);
        return token;
    }


    public String getUserName(String token){  //extrae el nombre de usuario del token
        return getClaim(token, Claims::getSubject);
    }

    public <T> T getClaim(String token, Function<Claims, T> claimsResolver){ //extrae el cuerpo del token
        final Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Claims getAllClaims(String token) {
            return Jwts
                    .parser()  //analiza y verifica el token
                    .verifyWith(setSignIngKey())
                    .build()
                    .parseSignedClaims(token) //devuelve un objeto jwt<Claims> que contiene el cuerpo del token (claims) y la informacion de la firma
                    .getPayload();
    }

    private static SecretKey setSignIngKey() {
        byte[] keyBytes = Decoders.BASE64.decode(JWT_FIRMA);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public boolean validateToken(String token, UserDetails userDetails){
        final String username = getUserName(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public boolean isTokenExpired(String token) {
        return getExpired(token).before(new Date());  //verifica la fecha de expiracion del token y compara con la actual
    }

    public Date getExpired(String token) {
        return getClaim(token, Claims::getExpiration);
    }

}
