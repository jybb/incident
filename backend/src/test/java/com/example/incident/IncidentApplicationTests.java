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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

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

		System.out.println(incidentController.getIncidents().size());

	}

	@Test
	public void testConcurrentUpdates() throws URISyntaxException {
		Incident incident = new Incident();
		incident.setName("Test concurrent");
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

		System.out.println(incidentController.getIncident(1L));

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
