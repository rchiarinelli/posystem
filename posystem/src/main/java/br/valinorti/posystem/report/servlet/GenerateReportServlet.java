package br.valinorti.posystem.report.servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jboss.resteasy.util.HttpResponseCodes;

import br.valinorti.posystem.report.util.ReportFactory;
import br.valinorti.posystem.report.util.ReportParams;
import br.valinorti.util.HibernateUtils;
import br.valinorti.util.POSystemUtils;

/**
 * Servlet implementation class GenerateReportServlet
 */
public class GenerateReportServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private static Logger logger = Logger.getLogger(GenerateReportServlet.class);
    
	private static final String PARAM_REPORT_NAME = "repName";
	
	private static final String PARAM_REPORT_BILLING_TOTAL_BY_PERIOD_FROM_DATE = "fromDate";
	
	private static final String PARAM_REPORT_BILLING_TOTAL_BY_PERIOD_TO_DATE = "toDate";
	
	private static final String REPORT_BILLING_TOTAL_BY_PERIOD = "billingTotalByPeriod";
	
	private static final String REP_TYPE = "repType";
	
	private static final String REP_TYPE_PDF = "pdf";
	
	private static final String REP_TYPE_CSV = "csv";
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String paramReportName = request.getParameter(PARAM_REPORT_NAME);
		String repType = request.getParameter(REP_TYPE);
		ReportFactory repFactory = ReportFactory.getInstance();
		try {
			//Gerar o relatório de acordo com cada parametro
			if (StringUtils.equals(paramReportName, REPORT_BILLING_TOTAL_BY_PERIOD)) {
				logger.debug("Gerando relatorio..: " + paramReportName);
				
				
				String fromDateParam = request.getParameter(PARAM_REPORT_BILLING_TOTAL_BY_PERIOD_FROM_DATE);
				String toDateParam = request.getParameter(PARAM_REPORT_BILLING_TOTAL_BY_PERIOD_TO_DATE);
				
				Date fromDate = POSystemUtils.parseDate(fromDateParam);
				Date toDate = POSystemUtils.parseDate(toDateParam);
				
				Map<String, String> queryParams = new HashMap<String, String>();
				queryParams.put("fromDate", POSystemUtils.formatDateForFilter(fromDate));
				queryParams.put("toDate", POSystemUtils.formatDateForFilter(toDate));
				
				//Preparar o response
				response.setContentType("application/x-download");
				response.setContentType("*/*");
				
				//Montar os parametros
				ReportParams repParams = new ReportParams();
				//recuperar o caminho real para o relatorio
				URL urlPath = this.getClass().getClassLoader().getResource("reports/BillingTotalByPeriod.jasper");
				
				
				repParams.setPath(urlPath.getPath());
				repParams.setParams(queryParams);

				OutputStream out = response.getOutputStream();
				repParams.setOutput(out);
				
				repParams.setConnection(HibernateUtils.getSessionFactory().openStatelessSession().connection());
				
				if (StringUtils.equals(repType, REP_TYPE_PDF)) {
					response.setHeader("Content-Disposition", "attachment;filename=FaturamentoTotalPorPeriodo.pdf");
					repFactory.getReportAsPDF(repParams);					
				} else if (StringUtils.equals(repType, REP_TYPE_CSV)) {
					response.setHeader("Content-Disposition", "attachment;filename=FaturamentoTotalPorPeriodo.csv");
					repFactory.getReportAsCSV(repParams);
				}

				
				out.flush();
			} else {
				response.setStatus(HttpResponseCodes.SC_NOT_FOUND);
			}
		} catch (ParseException exc) {
			logger.error(exc);
			response.setStatus(HttpResponseCodes.SC_NOT_FOUND);
		} catch (JRException exc) {
			logger.error(exc);
			response.setStatus(HttpResponseCodes.SC_NOT_FOUND);
		} 
	}
}
