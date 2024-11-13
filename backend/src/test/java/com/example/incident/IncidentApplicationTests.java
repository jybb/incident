package com.example.incident;

import org.example.incident.IncidentApplication;
import org.example.incident.controller.IncidentController;
import org.example.incident.entity.Incident;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.net.URISyntaxException;
import java.util.List;
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
public class IncidentApplicationTests {

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

		List<Incident> actual = incidentController.getIncidents().stream()
				.filter(i -> "Test concurrent create".equals(i.getName()))
				.collect(Collectors.toList());

		System.out.println(actual.size());
		assertEquals(1, actual.size());

	}

	@Test
	public void testConcurrentUpdates() throws URISyntaxException {
		Incident incident = new Incident();
		incident.setName("Test concurrent update");
		incidentController.createIncident(incident);

		int max = 10;
		ExecutorService executorService = Executors.newFixedThreadPool(max);
		for (int i=0; i<max; i++) {
			executorService.submit(new Runnable() {
				@Override
				public void run() {
					incidentController.updateIncident(1L, incident);
				}
			});
		}
		awaitTerminationAfterShutdown(executorService);

		List<Incident> actual = incidentController.getIncidents().stream()
				.filter(i -> "Test concurrent update".equals(i.getName()))
				.collect(Collectors.toList());

		assertEquals(1, actual.size());
		System.out.println(actual.get(0));
		assertEquals(0, actual.get(0).getUpdateCount());

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
