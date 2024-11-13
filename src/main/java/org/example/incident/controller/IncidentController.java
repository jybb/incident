/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package org.example.incident.controller;

import org.example.incident.entity.Incident;
import org.example.incident.repository.IncidentRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * @author jessica.jia
 * @version : IncidentRepository.java, v 0.1 2024年11月13日 8:37 am jessica.jia Exp $
 */
@RestController
@RequestMapping("/incidents")
public class IncidentController {

    private final IncidentRepository incidentRepository;

    public IncidentController(IncidentRepository incidentRepository) {
        this.incidentRepository = incidentRepository;
    }

    @GetMapping
    public List<Incident> getIncidents() {
        return incidentRepository.findAll();
    }

    @GetMapping("/{id}")
    public Incident getIncident(@PathVariable Long id) {
        return incidentRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    @PostMapping("/create")
    public ResponseEntity createIncident(@RequestBody Incident incident) throws URISyntaxException {
        Incident incidentSaved = incidentRepository.save(incident);
        return ResponseEntity.created(new URI("/incidents/" + incidentSaved.getId())).body(incidentSaved);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateIncident(@PathVariable Long id, @RequestBody Incident incident) {
        Incident currentIncident = incidentRepository.findById(id).orElseThrow(RuntimeException::new);
        currentIncident.setName(incident.getName());
        currentIncident.setHappenTime(incident.getHappenTime());
        currentIncident = incidentRepository.save(incident);

        return ResponseEntity.ok(currentIncident);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteIncident(@PathVariable Long id) {
        incidentRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}