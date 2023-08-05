package com.springbootapp.weatherapp.config;

import com.hazelcast.config.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HazelCastConfig {

    @Bean
    public Config hazelCast() {
        Config config = new Config();
        config.setInstanceName("hazelcast-cache");
        config.setProperty("hazelcast.rest.enabled", "true");
        MapConfig allUsersCache = new MapConfig();
        allUsersCache.setTimeToLiveSeconds(60);
        EvictionConfig evictionConfig = new EvictionConfig()
                .setEvictionPolicy(EvictionPolicy.LFU)
                .setSize(10000)
                .setMaxSizePolicy(MaxSizePolicy.PER_PARTITION);
        allUsersCache.setEvictionConfig(evictionConfig);
        config.getMapConfigs().put("map", allUsersCache);
        return config;
    }
}
