package security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    public static final String JWT_FIRMA = "Ip6akP8N3x7QDPK7Tq0aK3w3dSzYg8Th";
    public static final long JWT_EXPIRATION_TOKEN = 360000;


    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails); // El HashMap vacío se utilizará para almacenar cualquier reclamo adicional que desees agregar al token JWT.

    }
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        Date now = new Date(System.currentTimeMillis());
        Date expirationDate = new Date(now.getTime() + JWT_EXPIRATION_TOKEN);
        SecretKey secretKey = setSignIngKey();
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(now)
                .expiration(expirationDate)
                .claims(extraClaims)
                .signWith(secretKey)
                .compact();
    }


    public String getUserName(String token){  //extrae el nombre de usuario del token
        return getClaim(token, Claims::getSubject);
    }

    public <T> T getClaim(String token, Function<Claims, T> claimsResolver){
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
