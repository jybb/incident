package org.example.incident.repository; /**
 * Alipay.com Inc.
 * Copyright (c) 2004-2024 All Rights Reserved.
 */

import org.example.incident.entity.Incident;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author jessica.jia
 * @version repo: repo.java, v 0.1 2024年11月13日 8:16 am jessica.jia Exp $
 */
public interface IncidentRepository extends JpaRepository<Incident, Long> {
}