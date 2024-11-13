package org.example.incident.service.impl;

import org.example.incident.entity.Incident;
import org.example.incident.exception.DuplicatedIncidentException;
import org.example.incident.exception.MaxUpdateTimesException;
import org.example.incident.repository.IncidentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

/**
 * Created by jessica.jia on 13/11/2024.
 */
@ExtendWith(MockitoExtension.class)
class IncidentServiceImplTest {

    @Mock
    private IncidentRepository incidentRepository;

    @InjectMocks
    private IncidentServiceImpl incidentService;

    @Test
    void findAll() {
        Incident incident = new Incident();
        when(incidentRepository.findAll()).thenReturn(Arrays.asList(incident, incident));
        List<Incident> incidents = incidentService.findAll();
        assertEquals(2, incidents.size());
    }

    @Test
    void findById() {
        Incident incident = new Incident();
        incident.setId(1L);
        when(incidentRepository.findById(any())).thenReturn(Optional.of(incident));
        Incident found = incidentService.findById(1L);
        assertEquals(1L, found.getId());
    }

    @Test
    void create() {
        Incident incident = new Incident();
        incident.setId(1L);
        when(incidentRepository.findAll(any(Example.class))).thenReturn(Arrays.asList(incident));
        assertThrows(DuplicatedIncidentException.class, () -> incidentService.create(incident));

        when(incidentRepository.findAll(any(Example.class))).thenReturn(Collections.emptyList());
        when(incidentRepository.save(any())).thenReturn(incident);
        Incident saved = incidentService.create(incident);
        assertEquals(incident, saved);
    }

    @Test
    void update() {
        Incident incident = new Incident();
        incident.setId(1L);
        incident.setUpdateCount(-1);
        when(incidentRepository.lockById(any())).thenReturn(incident);
        assertThrows(MaxUpdateTimesException.class, () -> incidentService.update(incident));

        incident.setUpdateCount(3);
        when(incidentRepository.save(any())).thenReturn(incident);
        Incident updated = incidentService.update(incident);
        assertEquals(2, updated.getUpdateCount());
    }

    @Test
    void deleteById() {
        Incident incident = new Incident();
        incident.setId(1L);
        when(incidentRepository.findById(any())).thenReturn(Optional.of(incident));
        doThrow(new RuntimeException()).when(incidentRepository).deleteById(any());
        assertThrows(RuntimeException.class, () -> incidentService.deleteById(1L));
    }
}