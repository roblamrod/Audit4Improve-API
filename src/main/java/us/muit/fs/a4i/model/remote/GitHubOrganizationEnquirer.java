/**
 * 
 */
package us.muit.fs.a4i.model.remote;

import java.util.logging.Logger;

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
	@Override
	public ReportItemI getMetric(String metricName, String entityId) throws MetricException {
		// TODO Auto-generated method stub
		return null;
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
