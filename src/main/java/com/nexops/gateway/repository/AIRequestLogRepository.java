package com.nexops.gateway.repository;

import com.nexops.gateway.entity.AIRequestLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AIRequestLogRepository
        extends JpaRepository<AIRequestLog, Long> {
}