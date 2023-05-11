/**
 * 
 */
package us.muit.fs.a4i.test.model.remote;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GHRepositoryStatistics;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GHRepositoryStatistics.CodeFrequency;
import org.junit.jupiter.api.Test;
import org.kohsuke.github.GHOrganization;

import us.muit.fs.a4i.exceptions.MetricException;
import us.muit.fs.a4i.exceptions.ReportItemException;
import us.muit.fs.a4i.model.entities.Report;
import us.muit.fs.a4i.model.entities.ReportI;
import us.muit.fs.a4i.model.entities.ReportItem;
import us.muit.fs.a4i.model.entities.ReportItemI;
import us.muit.fs.a4i.model.entities.ReportItem.ReportItemBuilder;
import us.muit.fs.a4i.model.remote.GitHubEnquirer;
import us.muit.fs.a4i.model.remote.GitHubOrganizationEnquirer;

/**
 * @author Roberto Lama
 *
 */
public class GitHubOrganizationEnquirerTest extends GitHubEnquirer {
	private static Logger log = Logger.getLogger(GitHubOrganizationEnquirerTest.class.getName());

	/**
	 * <p>
	 * Constructor
	 * </p>
	 */
	
	public GitHubOrganizationEnquirerTest() {
		super();
		metricNames.add("members");
		metricNames.add("teams");
		metricNames.add("openProjects");
		metricNames.add("closedProjects");
		log.info("A�adidas m�tricas al GHRepositoryEnquirer");
	}
	
	/**
	 * <p>
	 * Crea la métrica solicitada consultando la organización pasada por parámetros
	 * </p>
	 * 
	 * @param metricName Métrica solicitada
	 * @param entityId Id de la entidad
	 * @return La métrica creada
	 * @throws MetricException Si la métrica no está definida se lanzará una
	 *                         excepción
	 * @throws ReportItemException 
	 */
	@Override
	public ReportItem getMetric(String metricName, String organizationId) throws MetricException, ReportItemException {
		/* MIT-FS 
		Members: 30 GHOrganization remoteOrg
		Teams: 2
		OpenProjects: 1
		ClosedProjects: 0
		*/
		ReportItem metric;
		if (organizationId == null) {
			throw new MetricException("Intenta obtener una métrica sin haber obtenido los datos de la organización");
		}
		switch (metricName) {
		case "members":
			metric = getMembers(organizationId);
			break;
		case "teams":
			metric = getTeams(organizationId);
			break;
		case "openProjects":
			metric = getOpenProjects(organizationId);
			break;
		case "closedProjects":
			metric = getOpenProjects(organizationId);
			break;
		default:
			throw new MetricException("La métrica " + metricName + " no está definida para un repositorio");
		}

		return metric;
	}


	private ReportItem<Integer> getMembers(String organizationId) throws MetricException, ReportItemException {
		ReportItem metric = null;
		ReportItemBuilder members = null;
		switch (organizationId) {
		case "MIT-FS":
			members = new ReportItem.ReportItemBuilder<Integer>("members", 30);
			members.source("GitHub, calculada")
					.description("Obtiene el número de miembros de una organización");
			metric = members.build();

			break;
		default:
			throw new MetricException("La organización " + organizationId + " no está contemplada en el test, solo se contempla MIT-FS");
		}
		return metric;
	}

	private ReportItem<Integer> getTeams(String organizationId) throws MetricException {
		ReportItem<Integer> metric = null;
		switch (organizationId) {
		case "MIT-FS":
			try {
				ReportItemBuilder<Integer> members = new ReportItem.ReportItemBuilder<Integer>("teams", 2);
				members.source("GitHub, calculada")
						.description("Obtiene el número de equipos de una organización");
				metric = members.build();

			} catch (ReportItemException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		default:
			throw new MetricException("La organización " + organizationId + " no está contemplada en el test, solo se contempla MIT-FS");
		}
		return metric;
	}

	private ReportItem<Integer> getOpenProjects(String organizationId) throws MetricException {
		ReportItem<Integer> metric = null;
		switch (organizationId) {
		case "MIT-FS":
			try {
				ReportItemBuilder<Integer> members = new ReportItem.ReportItemBuilder<Integer>("openProjects",
						1);
				members.source("GitHub, calculada")
						.description("Obtiene el número de equipos de una organización");
				metric = members.build();

			} catch (ReportItemException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		default:
			throw new MetricException("La organización " + organizationId + " no está contemplada en el test, solo se contempla MIT-FS");
		}
		return metric;
	}
	
	private ReportItem<Integer> getClosedProjects(String organizationId) throws MetricException {
		ReportItem<Integer> metric = null;
		
		switch (organizationId) {
		case "MIT-FS":
			try {
				ReportItemBuilder<Integer> members = new ReportItem.ReportItemBuilder<Integer>("closedProjects",
						0);
				members.source("GitHub, calculada")
						.description("Obtiene el número de equipos de una organización");
				metric = members.build();

			} catch (ReportItemException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		default:
			throw new MetricException("La organización " + organizationId + " no está contemplada en el test, solo se contempla MIT-FS");
		}
		return metric;
	}
	
	@Override
	public ReportI buildReport(String organizationId) {
		ReportI myRepo = null;
		myRepo = new Report(organizationId);
		log.info("Invocado el m�todo que construye un objeto RepositoryReport");
		/**
		 * <p>
		 * Información sobre la organización obtenida de GitHub
		 * </p>
		 */
		GHOrganization remoteOrg = null;
		/**
		 * <p>
		 * En estos momentos cada vez que se invoca construyeObjeto se crea y rellena
		 * uno nuevo
		 * </p>
		 * <p>
		 * Deuda técnica: se puede optimizar consultando sólo las diferencias respecto a
		 * la fecha de la última representación local
		 * </p>
		 */

		try {
			log.info("Nombre organización = " + organizationId);

			ReportItemBuilder<Integer> members = new ReportItem.ReportItemBuilder<Integer>("members",getMembers(organizationId).getValue());
			members.source("GitHub");
			myRepo.addMetric(members.build());
			log.info("Añadida métrica miembros " + members);

			ReportItemBuilder<Integer> teams = new ReportItem.ReportItemBuilder<Integer>("teams",
					(Integer) getTeams(organizationId).getValue());
			teams.source("GitHub");
			myRepo.addMetric(teams.build());
			
			log.info("Añadida métrica equipos " + teams);
			ReportItemBuilder<Integer> openProjects = new ReportItem.ReportItemBuilder<Integer>("openProjects",
					(Integer) getOpenProjects(organizationId).getValue());
			openProjects.source("GitHub");
			myRepo.addMetric(openProjects.build());
			log.info("Añadida métrica openProjects " + openProjects);

			ReportItemBuilder<Integer> closedProjects = new ReportItem.ReportItemBuilder<Integer>("clsoedProjects",
					(Integer) getClosedProjects(organizationId).getValue());
			closedProjects.source("GitHub");
			myRepo.addMetric(closedProjects.build());
			log.info("Añadida métrica closedProjects " + closedProjects);


		} catch (Exception e) {
			log.severe("Problemas en la conexión " + e);
		}

		return myRepo;
	}
	
	/**
	 * Test method for
	 * GitHubOrganizationEnquirer
	 * @throws MetricException 
	 * @throws ReportItemException 
	 */
	@Test
	void testGetMembers() throws MetricException, ReportItemException {
		ReportItem<Integer> members = GitHubOrganizationEnquirer.getMembers("MIT-FS");
		ReportItem<Integer> membersMock = getMembers("MIT-FS");
		assertEquals(members.getValue(),
				membersMock.getValue(), "Deber�aa tener el valor especificado en el mock");

		assertEquals(GitHubOrganizationEnquirer.getTeams("MIT-FS").getValue(),
				getTeams("MIT-FS").getValue(), "Deber�aa tener el valor especificado en el mock");

		assertEquals(GitHubOrganizationEnquirer.getOpenProjects("MIT-FS").getValue(),
				getOpenProjects("MIT-FS").getValue(), "Deber�aa tener el valor especificado en el mock");
			
		assertEquals(GitHubOrganizationEnquirer.getClosedProjects("MIT-FS").getValue(),
				getClosedProjects("MIT-FS").getValue(), "Deber�aa tener el valor especificado en el mock");

	}

	/**
	 * Test method for
	 * GitHubOrganizationEnquirer
	 * @throws MetricException 
	 * @throws ReportItemException 
	 */
	@Test
	void testGetMetrics() throws MetricException, ReportItemException {
		GitHubOrganizationEnquirer ghEnquirer = new GitHubOrganizationEnquirer();
		
		// TEST 1: RepositoriesWithOpenPullRequest
		ReportItem<Integer> metricsRepositoriesWithOpenPullRequest = ghEnquirer.getMetric("RepositoriesWithOpenPullRequest", "MIT-FS");
		System.out.println(metricsRepositoriesWithOpenPullRequest.getValue());
		assertEquals(metricsRepositoriesWithOpenPullRequest.getValue(),
				4, "Debería tener el valor especificado en el mock"); // Tiene 4 repositorios con pull requests abiertos
		
		// TEST 2: PullRequest
		ReportItem<Integer> metricsPullRequest = ghEnquirer.getMetric("PullRequest", "MIT-FS");
		System.out.println(metricsPullRequest.getValue());
		assertEquals(metricsPullRequest.getValue(),
				15, "Debería tener el valor especificado en el mock"); // Tiene 15 pull requests

		// TEST 3: Repositories
		ReportItem<Integer> metricsRepositories = ghEnquirer.getMetric("Repositories", "MIT-FS");
		System.out.println(metricsRepositories.getValue());
		assertEquals(metricsRepositories.getValue(),
				10, "Debería tener el valor especificado en el mock"); // Tiene 10 repositorios
	}
	
}
