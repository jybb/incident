/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package org.example.incident.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import org.example.incident.entity.Incident;
import org.example.incident.repository.IncidentCustomRepository;

/**
 * @author jessica.jia
 * @version : IncidentRepositoryImpl.java, v 0.1 2024年11月13日 8:24 pm jessica.jia Exp $
 */
public class IncidentCustomRepositoryImpl implements IncidentCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Incident lockById(Long id) {
        return entityManager.find(Incident.class, id, LockModeType.PESSIMISTIC_READ);
    }
}