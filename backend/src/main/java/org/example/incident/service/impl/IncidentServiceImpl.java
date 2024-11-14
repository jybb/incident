/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package org.example.incident.service.impl;

import org.example.incident.entity.Incident;
import org.example.incident.exception.DuplicatedIncidentException;
import org.example.incident.exception.IncidentNotFoundException;
import org.example.incident.exception.MaxUpdateTimesException;
import org.example.incident.repository.IncidentRepository;
import org.example.incident.service.IncidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * @author jessica.jia
 * @version : IncidentServiceImpl.java, v 0.1 2024年11月13日 4:47 pm jessica.jia Exp $
 */
@Component
public class IncidentServiceImpl implements IncidentService {
    private static final int MAX_UPDATE_TIMES = 3;

    @Autowired
    private IncidentRepository incidentRepository;

    @Override
    public List<Incident> findAll() {
        return incidentRepository.findAll();
    }

    @Override
    public Incident findById(Long id) {
        return incidentRepository.findById(id).orElseThrow(() -> new IncidentNotFoundException("id=" + id));
    }

    @Override
    public Incident create(Incident incident) {
        checkForDuplicate(incident);
        incident.setUpdateCount(MAX_UPDATE_TIMES);
        return incidentRepository.save(incident);
    }

    @Override
    @Transactional
    public Incident update(Incident incident) {
        Incident found = incidentRepository.lockById(incident.getId());
        if (found.getUpdateCount() > 0) {
            found.setUpdateCount(found.getUpdateCount() - 1);
        } else {
            throw new MaxUpdateTimesException();
        }
        if (!Objects.equals(found.getName(), incident.getName())) {
            checkForDuplicate(incident);
        }
        found.setName(incident.getName());
        found.setTime(incident.getTime());
        found.setAddress(incident.getAddress());
        System.out.println("Update " + found);
        return incidentRepository.save(found);
    }

    private void checkForDuplicate(Incident incident) {
        Incident incidentByName = new Incident();
        incidentByName.setName(incident.getName());
        Example<Incident> byName = Example.of(incidentByName);
        List<Incident> found = incidentRepository.findAll(byName);
        if (!found.isEmpty()) {
            throw new DuplicatedIncidentException("duplicated name " + incident.getName());
        }
    }

    @Override
    public void deleteById(Long id) {
        findById(id);
        incidentRepository.deleteById(id);
    }
}