/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package org.example.incident.service;

import org.example.incident.entity.Incident;

import java.util.List;

/**
 * @author jessica.jia
 * @version : IncidentService.java, v 0.1 2024年11月13日 4:45 pm jessica.jia Exp $
 */
public interface IncidentService {
    List<Incident> findAll();

    Incident findById(Long id);

    Incident create(Incident incident);

    Incident update(Incident incident);

    void deleteById(Long id);
}