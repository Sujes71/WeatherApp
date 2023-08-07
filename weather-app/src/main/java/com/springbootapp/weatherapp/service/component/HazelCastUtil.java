package com.springbootapp.weatherapp.service.component;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import com.springbootapp.weatherapp.model.Municipality;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
public class HazelCastUtil {

    @Autowired
    private HazelcastInstance hazelcastInstance;
    private IMap<String, List<Municipality>> munCache;

    @PostConstruct
    public void init() {
        munCache = hazelcastInstance.getMap("map");
    }
}
