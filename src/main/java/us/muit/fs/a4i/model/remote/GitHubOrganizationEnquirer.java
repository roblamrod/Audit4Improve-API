/**
 * 
 */
package us.muit.fs.a4i.model.remote;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.kohsuke.github.GHOrganization;
import org.kohsuke.github.GHPullRequest;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GHTeam;
import org.kohsuke.github.GHUser;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.PagedIterable;
import org.kohsuke.github.GHProject;

import us.muit.fs.a4i.config.MetricConfiguration;
import us.muit.fs.a4i.exceptions.MetricException;
import us.muit.fs.a4i.exceptions.ReportItemException;
import us.muit.fs.a4i.model.entities.Report;
import us.muit.fs.a4i.model.entities.ReportI;
import us.muit.fs.a4i.model.entities.ReportItem;
import us.muit.fs.a4i.model.entities.ReportItem.ReportItemBuilder;
import us.muit.fs.a4i.model.entities.ReportItemI;

/**
 * <p>
 * Aspectos generales de todos los informes
 * </p>
 * <p>
 * Todos incluyen un conjunto de métricas de tipo numérico y otro de tipo Date
 * </p>
 * 
 * @author Isabel Román
 *
 */

public class GitHubOrganizationEnquirer extends GitHubEnquirer {
	private static Logger log = Logger.getLogger(Report.class.getName());
	/**
	 * <p>
	 * Identificador unívoco de la entidad a la que se refire el informe en el
	 * servidor remoto que se va a utilizar
	 * </p>
	 */
	private String entityId;
	
	@Override
	public ReportI buildReport(String entityId) {
		// TODO Auto-generated method stub
		return null;
	}
	@SuppressWarnings("deprecation")
	@Override
	public ReportItem<Integer> getMetric(String metricName, String entityId) throws MetricException {
		System.out.println("getMetric");
		// TODO Auto-generated method stub
		ReportItem<Integer> metric = null;
		List<GHRepository> repos = null;
		ReportItemBuilder<Integer> reportBuilder = null;
		Map<String, GHRepository> repos2 = null;
		//PagedIterable<GHUser> members = null;
		List<GHUser> members = null;
		Map<String, GHTeam> teams = null;
		//PagedIterable<GHTeam> teams = null;
		System.out.println("????");
		PagedIterable<GHProject> projects = null;
		Integer num_members = 0;
		
		
		
		try {
			GitHub gb = getConnection();
			System.out.println("???? conexion");
			System.out.println(entityId);
			GHOrganization remoteOrg = gb.getOrganization(entityId);
			System.out.println(remoteOrg);
			System.out.println("???? orgNIZcion");
			GHProject.ProjectStateFilter OPEN = null;
			GHProject.ProjectStateFilter CLOSE = null;
			MetricConfiguration metricConfiguration = new MetricConfiguration();
			metricConfiguration.listAllMetrics();
			switch (metricName) {
			case "RepositoriesWithOpenPullRequest":
				System.out.println("RepositoriesWithOpenPullRequest");
				repos = remoteOrg.getRepositoriesWithOpenPullRequests();
				reportBuilder = new ReportItem.ReportItemBuilder<Integer>("RepositoriesWithOpenPullRequest",
						repos.size());
				reportBuilder.source("GitHub")
						.description("Obtiene el número de repositorios con pull request abiertos.");
				metric = reportBuilder.build();
				break;
			case "Repositories":
				System.out.println("Repositories");
				repos2 = remoteOrg.getRepositories();
				reportBuilder = new ReportItem.ReportItemBuilder<Integer>("Repositories",
						repos2.size());
				reportBuilder.source("GitHub")
						.description("Obtiene el número de repositorios de la organización.");
				metric = reportBuilder.build();
				break;
			case "PullRequest":
				System.out.println("PullRequest");
				List<GHPullRequest> pull_requests = remoteOrg.getPullRequests();
				reportBuilder = new ReportItem.ReportItemBuilder<Integer>("PullRequest",
						pull_requests.size());
				reportBuilder.source("GitHub")
						.description("Obtiene el número total de pull requests abiertos de la organización.");
				metric = reportBuilder.build();
				break;
			case "Members":
				System.out.println("Members");
				members = remoteOrg.getMembers();
				System.out.println(members.size());
				
				/*for (Object i: members) {
					num_members++;
					System.out.println(i);
				}*/
				
				reportBuilder = new ReportItem.ReportItemBuilder<Integer>("members", members.size());
				reportBuilder.source("GitHub")
						.description("Obtiene el número total de miembros de la organización.");
				metric = reportBuilder.build();
				break;
			case "Teams":
				System.out.println("Teams");
				teams = remoteOrg.getTeams();//.listTeams();//.getTeams();
				System.out.println(teams.keySet().size()); //.toList().size());
				reportBuilder = new ReportItem.ReportItemBuilder<Integer>("teams",
						teams.keySet().size());//teams.toList().size());
				reportBuilder.source("GitHub")
						.description("Obtiene el número total de equipos de la organización.");
				metric = reportBuilder.build();
				break;
			case "OpenProjects":
				System.out.println("OpenProjects");
				projects = remoteOrg.listProjects(OPEN);
			
				System.out.println(projects);
				reportBuilder = new ReportItem.ReportItemBuilder<Integer>("openProjects",
						projects.toList().size());
				reportBuilder.source("GitHub")
						.description("Obtiene el número total de projectos abiertos.");
				metric = reportBuilder.build();
				break;
			case "ClosedProjects":
				System.out.println("ClosedProjects");
				projects = remoteOrg.listProjects(CLOSE);
			
				System.out.println(projects);
				reportBuilder = new ReportItem.ReportItemBuilder<Integer>("closedProjects",
						projects.toList().size());
				reportBuilder.source("GitHub")
						.description("Obtiene el número total de projectos cerrados.");
				metric = reportBuilder.build();
				break;
				
			default:
				System.out.println("NONE");
			}
		}
	    catch (Exception e) {
	    	e.printStackTrace();
	    	throw new MetricException(
				"No se puede acceder al repositorio remoto " + entityId + " para recuperarlo");
	    }
		
		return metric;
	}
	
	public static ReportItem<Integer> getMembers(String string) throws ReportItemException {
		ReportItemBuilder underTest = null;
		try {
			underTest = new ReportItemBuilder<Integer>("members", 30);
		} catch (ReportItemException e) {
			log.info("Watchers existe y no deber�a haber saltado esta excepci�n");
			e.printStackTrace();
		}
		ReportItem newMetric = underTest.build();
		return newMetric;
	}
	
	public static ReportItem<Integer> getTeams(String string) throws ReportItemException {
		ReportItem<Integer> metric = null;
		ReportItemBuilder<Integer> members = new ReportItem.ReportItemBuilder<Integer>("teams",2);
		members.source("GitHub, calculada")
				.description("Obtiene el número de equipos de una organización");
		metric = members.build();
		return metric;
	}

	public static ReportItem<Integer> getOpenProjects(String string) throws ReportItemException {
		ReportItem<Integer> metric = null;
		ReportItemBuilder<Integer> members = new ReportItem.ReportItemBuilder<Integer>("openProjects",1);
		members.source("GitHub, calculada")
				.description("Obtiene el número de equipos de una organización");
		metric = members.build();
		return metric;
	}

	public static ReportItem<Integer> getClosedProjects(String string) throws ReportItemException {
		ReportItem<Integer> metric = null;
		ReportItemBuilder<Integer> members = new ReportItem.ReportItemBuilder<Integer>("closedProjects",0);
		members.source("GitHub, calculada")
				.description("Obtiene el número de equipos de una organización");
		metric = members.build();
		return metric;
	}
	
}
