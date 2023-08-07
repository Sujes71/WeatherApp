package com.springbootapp.weatherapp.service.webfilter;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import io.github.bucket4j.local.LocalBucket;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@WebFilter
public class CustomFilter extends GenericFilterBean {
    private final Map<String, LocalBucket> buckets = new ConcurrentHashMap<>();
    private final Map<String, Long> blockedUsers = new ConcurrentHashMap<>();
    private static final int BUCKET_CAPACITY = 15;
    private static final int REFILL_INTERVAL_MINUTES = 1;
    private static final int BLOCK_DURATION_MINUTES = 30;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String clientId = getClientId(servletRequest);

        if (isUserBlocked(clientId)) {
            servletResponse.getWriter().write("User is blocked due to excessive requests. Please try again later.");
            servletResponse.flushBuffer();
            return;
        }

        LocalBucket bucket = buckets.computeIfAbsent(clientId, k -> createNewBucket());

        if (bucket.tryConsume(1)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            blockUser(clientId);
            servletResponse.getWriter().write("Too many requests. User is blocked. Please try again later.");
            servletResponse.flushBuffer();
        }
    }

    private String getClientId(ServletRequest request) {
        return request.getRemoteAddr();
    }

    private LocalBucket createNewBucket() {
        Refill refill = Refill.intervally(BUCKET_CAPACITY, Duration.ofMinutes(REFILL_INTERVAL_MINUTES));
        Bandwidth limit = Bandwidth.classic(BUCKET_CAPACITY, refill).withInitialTokens(BUCKET_CAPACITY);
        return Bucket.builder()
                .addLimit(limit)
                .build();
    }

    private boolean isUserBlocked(String userId) {
        Long blockEndTime = blockedUsers.get(userId);
        if (blockEndTime == null) {
            return false;
        }
        long currentTimeMillis = System.currentTimeMillis();
        return currentTimeMillis < blockEndTime;
    }

    private void blockUser(String userId) {
        long blockEndTime = System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(BLOCK_DURATION_MINUTES);
        blockedUsers.put(userId, blockEndTime);
    }
}
