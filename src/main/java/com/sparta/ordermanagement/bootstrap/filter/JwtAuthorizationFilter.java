package com.sparta.ordermanagement.bootstrap.filter;

import com.sparta.ordermanagement.bootstrap.auth.UserDetailsImpl;
import com.sparta.ordermanagement.bootstrap.util.JwtUtil;

import com.sparta.ordermanagement.framework.persistence.entity.user.Role;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import java.util.Set;


@RequiredArgsConstructor
@Slf4j
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final static String UUID_PATTERN = "[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}";

    private static final Set<String> FILTERING_URIS_ADMIN = Set.of(
            "/admin"
    );

    //필터링할 url 추가하시면 됩니다. (user 정보가 필요하거나 권한이 필요한 url)
    // Set.of() 내부에 로그인이 필요한 메서드를 추가하시면 됩니다.
    private static final Map<Pattern, Set<String>> FILTERING_URIS = Map.ofEntries(
        Map.entry(Pattern.compile("^/api/v1/example$"), Set.of("GET")),
        Map.entry(Pattern.compile(String.format("^/api/v1/shops/%s/products$", UUID_PATTERN)), Set.of("POST", "PUT", "DELETE")),
        Map.entry(Pattern.compile(String.format("^/api/v1/shops/%s/products/%s$", UUID_PATTERN, UUID_PATTERN)), Set.of("POST", "PUT", "DELETE")),
        Map.entry(Pattern.compile("^/api/v1/orders$"), Set.of("GET", "POST", "PUT", "DELETE")),
        Map.entry(Pattern.compile("^/api/v1/orders/payments$"), Set.of("GET", "POST", "PUT", "DELETE")),
        Map.entry(Pattern.compile("^/api/v1/orders/owner$"), Set.of("GET", "POST", "PUT", "DELETE")),
        Map.entry(Pattern.compile(String.format("^/api/v1/orders/%s$", UUID_PATTERN)), Set.of("GET", "POST", "PUT", "DELETE")),
        Map.entry(Pattern.compile(String.format("^/api/v1/orders/%s/cancel$", UUID_PATTERN)), Set.of("GET", "POST", "PUT", "DELETE")),
        Map.entry(Pattern.compile(String.format("^/api/v1/orders/%s/reviews$", UUID_PATTERN)), Set.of("GET", "POST", "PUT", "DELETE")),
        Map.entry(Pattern.compile(String.format("^/api/v1/orders/%s/reviews/%s$", UUID_PATTERN, UUID_PATTERN)), Set.of("GET", "POST", "PUT", "DELETE")),
        Map.entry(Pattern.compile("^/api/v1/ai$"), Set.of("GET", "POST", "PUT", "DELETE"))
    );

    public static boolean isFilteringUri(String uri, String method) {
        // true -> 필터링 함(user 정보 필요한 url)
        // false-> 필터링 안함

        // 필터가 필요한 URI 검증
        if (FILTERING_URIS.entrySet().stream()
                .anyMatch(entry -> entry.getKey().matcher(uri).matches() && entry.getValue().contains(method.toUpperCase()))) {
            return true;
        }

        // 모든 admin url 에 필터 적용
        if (FILTERING_URIS_ADMIN.stream()
                .anyMatch(uri::startsWith)){
            return true;
        }

        return false;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String uri = request.getRequestURI();
        String method = request.getMethod();

        // 필터 로직을 수행하지 않고 다음 필터로 이동
        if (!isFilteringUri(uri, method)) {
            filterChain.doFilter(request, response);
            return;
        }

        String tokenValue = jwtUtil.getTokenFromRequest(request);

        if(!(StringUtils.hasText(tokenValue) && jwtUtil.validateToken(tokenValue))){
            throw new ServletException("Invalid token");
        }


        Claims info = jwtUtil.getUserInfoFromToken(tokenValue);

        try {
            setAuthentication(info.getSubject(), Role.valueOf((String) info.get(jwtUtil.AUTHORIZATION_KEY)));
        } catch (Exception e) {
            log.error(e.getMessage());
            return;
        }

        filterChain.doFilter(request, response);

    }
    // 인증 처리
    public void setAuthentication(String userStringId, Role role) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = createAuthentication(userStringId, role);
        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);
    }

    // 인증 객체 생성
    private Authentication createAuthentication(String userStringId, Role role) {
        UserDetails userDetails = new UserDetailsImpl(userStringId, role);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
