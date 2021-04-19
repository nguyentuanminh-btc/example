package com.example.service;

import com.example.authentication.CustomUserDetail;
import com.example.authentication.CustomAuthenticationToken;
import com.example.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Component
@Service
public class TokenAuthenticationService {
    @Autowired
    public static UserRepository userRepository;
    static final long EXPIRATIONTIME = 1_000_000; // 10 days
    static final String SECRET = "ThisIsASecret";
    static final String AUTHORITIES_KEY = "AUTHORITIES_KEY";
    static final String TOKEN_PREFIX = "Bearer";
    static final String HEADER_STRING = "Authorization";

    public static void addAuthentication(HttpServletResponse res, Authentication authResult) {
        String JWT = Jwts.builder().setSubject(authResult.getName())
                .claim(AUTHORITIES_KEY, authResult.getAuthorities())
                .setId(((CustomUserDetail) authResult.getPrincipal()).getId() + "")
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
                .signWith(SignatureAlgorithm.HS512, SECRET).compact();
        res.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + JWT);
    }

    public static Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        if (token != null) {
            // parse the token.
            /*String username = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token.replace(TOKEN_PREFIX, "")).getBody()
                    .getSubject();*/
            Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token.replace(TOKEN_PREFIX, "")).getBody();
            String idString = (String) claims.get("jti");
            if (idString.isEmpty()){
                return null;
            }
            int id = Integer.parseInt(idString);
            String username = claims.getSubject();
            Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
            List<LinkedHashMap> authorityList = (List<LinkedHashMap>) claims.get(AUTHORITIES_KEY);
            if (!CollectionUtils.isEmpty(authorityList)){
                for (int i = 0; i< authorityList.size(); i++){
                    grantedAuthorities.add(new SimpleGrantedAuthority((String) authorityList.get(i).get("authority")));
                }
            }
            return username != null ?
                    new CustomAuthenticationToken(username, null, grantedAuthorities,id) :
                    null;        }
        return null;
    }

}

