package com.apple.shop.Member;

import com.apple.shop.Config.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class JwtFilter extends OncePerRequestFilter {

    private static final List<String> EXCLUDE_URLS = Arrays.asList(
            "/css/", "/js/", "/images/", "/login"
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String requestURI = request.getRequestURI();

        if (EXCLUDE_URLS.stream().anyMatch(url -> requestURI.startsWith(url))) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwtToken = extractJwtFromRequest(request);

        if (jwtToken != null) {
            try {

                Claims claims = JwtUtil.extractToken(jwtToken);

                var arr = claims.get("authorities").toString().split(",");
                var authorities = Arrays.stream(arr).map(a -> new SimpleGrantedAuthority(a)).toList();


                CustomUser customUser = new CustomUser(
                        claims.get("username").toString(),
                        "",
                        authorities
                );
                Double memberIdDouble = claims.get("memberId", Double.class);   //Double로 반환하여 명시처리
                Long memberId = memberIdDouble != null ? memberIdDouble.longValue() : null; //Long으로 넣어줌

                customUser.setMemberId(memberId);

                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        customUser, null, authorities
                );


                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authToken);
                System.out.println(SecurityContextHolder.getContext());
            } catch (Exception e) {
                System.out.println("ERROR ERROR ERROR ERROR ERROR ERROR : " + e.getMessage());
                SecurityContextHolder.clearContext();
            }
        }

        filterChain.doFilter(request, response);
    }

    private String extractJwtFromRequest(HttpServletRequest request) {
        String bearerToken = null;
        // Extract JWT from cookies
        if (request.getCookies() != null) {
            for (var cookie : request.getCookies()) {
                if ("jwt".equals(cookie.getName())) {
                    bearerToken = cookie.getValue();
                    break;
                }
            }
        }
        return bearerToken;
    }
}
