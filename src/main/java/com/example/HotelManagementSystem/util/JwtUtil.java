package com.example.HotelManagementSystem.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

    private String generatetoken(Map<String,Object> extractClaims, UserDetails details)
    {
        return Jwts.builder().setClaims(extractClaims).setSubject(details.getUsername())
                .setIssuedAt(new Date((System.currentTimeMillis())))
                .setExpiration(new Date(System.currentTimeMillis()*1000*60*24))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();
    }
    public String generateToken(UserDetails userDetails)
    {
        return generatetoken(new HashMap<>(),userDetails);
    }
    public boolean istokenvalid(String token, UserDetails userDetails)
    {
        final String username=extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !istokenExpired(token));
    }

    private Key getSigningKey() {
        byte[]keyBytes= Decoders.BASE64.decode("TmV3U2VjcmV0S2V5Rm9ySldUU2lnbmluZ1B1cnBvc2VzMTIzNDU2Nzg=\r\n");
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims extractAllClaims(String token)
    {
        return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
    }
    public String extractUsername(String token)
    {
        return extractClaim(token,Claims::getSubject);
    }
    private Date extractExpiration(String token)
    {
        return extractClaim(token,Claims::getExpiration);
    }
    private boolean istokenExpired(String token)
    {
        return extractExpiration(token).before(new Date());
    }

    private <T> T extractClaim(String token, Function<Claims,T> claimResolvers)
    {
        final Claims claims=extractAllClaims(token);
        return claimResolvers.apply(claims);
    }
}
