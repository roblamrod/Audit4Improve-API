/**
 * 
 */
package us.muit.fs.a4i.test.model.remote;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GHRepositoryStatistics;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GHRepositoryStatistics.CodeFrequency;
import org.kohsuke.github.GHOrganization;

import us.muit.fs.a4i.exceptions.MetricException;
import us.muit.fs.a4i.exceptions.ReportItemException;
import us.muit.fs.a4i.model.entities.Report;
import us.muit.fs.a4i.model.entities.ReportI;
import us.muit.fs.a4i.model.entities.ReportItem;
import us.muit.fs.a4i.model.entities.ReportItemI;
import us.muit.fs.a4i.model.entities.ReportItem.ReportItemBuilder;
import us.muit.fs.a4i.model.remote.GitHubEnquirer;

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
		metricNames.add("openprojects");
		metricNames.add("closedprojects");
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
	 */
	@Override
	public ReportItem getMetric(String metricName, String organizationId) throws MetricException {
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


	private ReportItem getMembers(String organizationId) throws MetricException {
		ReportItem metric = null;
		switch (organizationId) {
		case "MIT-FS":
			try {
				ReportItemBuilder<Integer> members = new ReportItem.ReportItemBuilder<Integer>("members",
						30);
				members.source("GitHub, calculada")
						.description("Obtiene el número de miembros de una organización");
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

	private ReportItem getTeams(String organizationId) throws MetricException {
		ReportItem metric = null;
		switch (organizationId) {
		case "MIT-FS":
			try {
				ReportItemBuilder<Integer> members = new ReportItem.ReportItemBuilder<Integer>("teams",
						2);
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

	private ReportItem getOpenProjects(String organizationId) throws MetricException {
		ReportItem metric = null;
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
	
	private ReportItem getClosedProjects(String organizationId) throws MetricException {
		ReportItem metric = null;
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
		log.info("Invocado el m�todo que construye un objeto RepositoryReport");
		/**
		 * <p>
		 * Información sobre la organización obtenida de GitHub
		 * </p>
		 */
		GHOrganization remoteOrg;
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

			ReportItemBuilder<Integer> members = new ReportItem.ReportItemBuilder<Integer>("members",
					(Integer) getMembers(organizationId).getValue());
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

}
