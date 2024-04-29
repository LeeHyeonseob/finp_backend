package group.finp_backend.util;

import group.finp_backend.dto.RegisterRequestDto;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.time.ZonedDateTime;

@Slf4j
@Component
public class JwtUtil {

    private final Key key;
    private final long accessTokenExpTime;

    public JwtUtil(
            @Value("${jwt.secret}") String secretKey,
            @Value("${jwt.expiration_time}") long accessTokenExpTime
    ){
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.accessTokenExpTime = accessTokenExpTime;
    }

    public String createAccessToken(RegisterRequestDto dto){
        return createToken(dto, accessTokenExpTime);
    }

    private String createToken(RegisterRequestDto dto, long expireTime){
        Claims claims = Jwts.claims();
        claims.put("email", dto.getEmail());
        claims.put("password", dto.getPassword());
        claims.put("username", dto.getUsername());

        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime tokenValidity = now.plusSeconds(expireTime);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(Date.from(now.toInstant()))
                .setExpiration(Date.from(tokenValidity.toInstant()))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token){
        try{
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch(io.jsonwebtoken.security.SecurityException | MalformedJwtException e){
            log.info("invalid JWT Token", e);
        } catch(ExpiredJwtException e){
            log.info("Expired JWT Token", e);
        } catch(UnsupportedJwtException e){
            log.info("Unsupported JWT Token", e);
        } catch(IllegalArgumentException e){
            log.info("JWT claims string is empty", e);
        }

        return false;
    }

    public Claims parseClaims(String accessToken){
        try{
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e){
            return e.getClaims();
        }
    }


}
