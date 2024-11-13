/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package org.example.incident.repository;

import org.example.incident.entity.Incident;

import java.util.List;

/**
 * @author jessica.jia
 * @version : IncidentCustomRepository.java, v 0.1 2024年11月13日 9:37 pm jessica.jia Exp $
 */
public interface IncidentCustomRepository {
    Incident lockById(Long id);

    List<Incident> lockByName(String name);
}