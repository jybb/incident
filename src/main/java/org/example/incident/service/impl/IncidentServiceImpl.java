/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package org.example.incident.service.impl;

import org.example.incident.entity.Incident;
import org.example.incident.exception.DuplicatedIncidentException;
import org.example.incident.exception.IncidentNotFoundException;
import org.example.incident.repository.IncidentRepository;
import org.example.incident.service.IncidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author jessica.jia
 * @version : IncidentServiceImpl.java, v 0.1 2024年11月13日 4:47 pm jessica.jia Exp $
 */
@Component
public class IncidentServiceImpl implements IncidentService {
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
        Incident incidentByName = new Incident();
        incidentByName.setName(incident.getName());
        Example<Incident> byName = Example.of(incidentByName);
        List<Incident> found = incidentRepository.findAll(byName);
        if (!found.isEmpty()) {
            throw new DuplicatedIncidentException("duplicated name " + incident.getName());
        }
        return incidentRepository.save(incident);
    }

    @Override
    public Incident update(Incident incident) {
        Incident found = findById(incident.getId());
        found.setName(incident.getName());
        found.setTime(incident.getTime());
        found.setAddress(incident.getAddress());
        return incidentRepository.save(found);
    }

    @Override
    public void deleteById(Long id) {
        incidentRepository.deleteById(id);
    }
}