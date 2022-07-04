package com.example.demo.Config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.TreeMap;

@Component
@ConfigurationProperties(prefix = "threads")
@PropertySource("classpath:config/threadConfig.properties")
public class ThreadConfig {
    private Map<String, ThreadSingleConfig> threadConfigs = new TreeMap<>();

    public void setThreadConfigs(Map<String, ThreadSingleConfig> threadConfigs) {
        this.threadConfigs.putAll(threadConfigs);
    }

    public Map<String, ThreadSingleConfig> getThreadConfigs() {
        return threadConfigs;
    }

    @Override
    public String toString() {
        return "ThreadConfig{" +
                "threadConfigs=" + threadConfigs +
                '}';
    }
}
