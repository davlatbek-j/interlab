package uz.interlab.security;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import uz.interlab.respository.UserRepository;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtTokenService {

    private final UserRepository userRepository;

    @Value("${jwt.token.secretKey}")
    private String secretKey;

    @Value("${jwt.token.expireDateInMilliSeconds}")
    private Long expireDate;

    public String generateToken(String phoneNumber) {
        Date now = new Date();
        String token = Jwts
                .builder()
                .setSubject(phoneNumber)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + expireDate))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
        System.err.println("\nToken generated: " + token);
        return token;
    }

    public boolean validateToken(String token) {
        try {
            Jwts
                    .parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            System.err.println("Muddati o'tgan");
        } catch (MalformedJwtException malformedJwtException) {
            System.err.println("Buzilgan token");
        } catch (SignatureException s) {
            System.err.println("Kalit so'z xato");
        } catch (UnsupportedJwtException unsupportedJwtException) {
            System.err.println("Qo'llanilmagan token");
        } catch (IllegalArgumentException ex) {
            System.err.println("Bo'sh token");
        }
        return false;
    }

    public User getUserFromToken(String token) {
        try {
            String phoneNumber = Jwts
                    .parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
            return userRepository.findByPhoneNumber(phoneNumber);
        } catch (Exception e) {
            throw new RuntimeException("Authorization failed. Please login again");
        }
    }

}
