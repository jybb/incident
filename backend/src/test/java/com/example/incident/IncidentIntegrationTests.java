package com.example.incident;

import org.example.incident.IncidentApplication;
import org.example.incident.controller.IncidentController;
import org.example.incident.entity.Incident;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.MOCK,
		classes = IncidentApplication.class)
@AutoConfigureMockMvc
public class IncidentIntegrationTests {

	@Autowired
	private IncidentController incidentController;

	@Test
	public void testConcurrentCreate() throws URISyntaxException {


		int max = 10;
		ExecutorService executorService = Executors.newFixedThreadPool(max);
		for (int i=0; i<max; i++) {
			int finalI = i;
			executorService.submit(new Runnable() {
				@Override
				public void run() {
					Incident incident = new Incident();
					incident.setName("Test concurrent create");
					incident.setAddress("A" + finalI);
					incidentController.createIncident(incident);
				}
			});
		}
		awaitTerminationAfterShutdown(executorService);

		List<Incident> actual = filterByName("Test concurrent create");

		System.out.println(actual.size());
		assertEquals(1, actual.size());

	}

	@Test
	public void testConcurrentUpdates() throws URISyntaxException {
		Incident incident = new Incident();
		incident.setName("Test concurrent update");
		incidentController.createIncident(incident);

		List<Incident> actual = filterByName("Test concurrent update");
		Long id = actual.get(0).getId();

		int max = 10;
		ExecutorService executorService = Executors.newFixedThreadPool(max);
		for (int i=0; i<max; i++) {
			executorService.submit(new Runnable() {
				@Override
				public void run() {
					incidentController.updateIncident(id, incident);
				}
			});
		}
		awaitTerminationAfterShutdown(executorService);

		actual = filterByName("Test concurrent update");

		assertEquals(1, actual.size());
		System.out.println(actual.get(0));
		assertEquals(0, actual.get(0).getUpdateCount());

	}

	@Test
	public void testConcurrentDeletes() throws URISyntaxException {
		Incident incident = new Incident();
		incident.setName("Test concurrent delete");
		incidentController.createIncident(incident);

		List<Incident> actual = filterByName("Test concurrent delete");
		Long id = actual.get(0).getId();

		int max = 10;
		ExecutorService executorService = Executors.newFixedThreadPool(max);
		for (int i=0; i<max; i++) {
			executorService.submit(new Runnable() {
				@Override
				public void run() {
					incidentController.deleteIncident(id);
				}
			});
		}
		awaitTerminationAfterShutdown(executorService);

		actual = filterByName("Test concurrent delete");

		System.out.println(actual);
		assertEquals(0, actual.size());

	}

	@Test
	public void testInvalidParameter() {
		Incident incident = new Incident();
		assertThrows(Exception.class, ()->incidentController.createIncident(incident));

		incident.setName("Test name length is larger than 30, should fail the test");
		assertThrows(Exception.class, ()->incidentController.createIncident(incident));

		incident.setName("Test name OK");
		ResponseEntity response = incidentController.createIncident(incident);
		assertTrue(response.getStatusCode().is2xxSuccessful());

		StringBuilder sb = new StringBuilder();
		for(int i=0; i<101; i++) {
			sb.append("1");
		}
		incident.setAddress(sb.toString());
		assertThrows(Exception.class, ()->incidentController.createIncident(incident));
	}

	private List<Incident> filterByName(String name) {
		return incidentController.getIncidents().stream()
				.filter(i -> Objects.equals(name, i.getName()))
				.collect(Collectors.toList());
	}

	public void awaitTerminationAfterShutdown(ExecutorService threadPool) {
		threadPool.shutdown();
		try {
			if (!threadPool.awaitTermination(60, TimeUnit.SECONDS)) {
				threadPool.shutdownNow();
			}
		} catch (InterruptedException ex) {
			threadPool.shutdownNow();
			Thread.currentThread().interrupt();
		}
	}

}
