package com.liwanag.practice.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.List;

// Why have an in-memory colocated cache like Caffeine instead of using a distributed cache like Redis?
// Since Liwanag canonical content is (by definition) static data, it also doesn't need complex
// data structures (e.g. Redis sorted sets for leaderboards or distributed locks), in-memory caching
// is well-suited.

// Note: does a practice service have high scalability/availability requirements in terms of canonical content?
// If so we might want to switch to Redis

@Configuration
@EnableCaching
public class CacheConfig {
    @Bean
    public CacheManager cacheManager() {
        var manifests = new CaffeineCache("manifests",
                Caffeine.newBuilder()
                        .maximumSize(500)
                        .expireAfterWrite(Duration.ofMinutes(60))
                        .recordStats()
                        .build());
        var content = new CaffeineCache("content",
                Caffeine.newBuilder()
                        .maximumSize(1000)
                        .expireAfterWrite(Duration.ofMinutes(60))
                        .recordStats()
                        .build());
        var manager = new SimpleCacheManager();
        manager.setCaches(List.of(manifests, content));
        return manager;
    }
}
