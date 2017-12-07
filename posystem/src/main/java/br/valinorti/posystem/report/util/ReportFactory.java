/**
 * 
 */
package br.valinorti.posystem.report.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRCsvExporter;

import org.apache.log4j.Logger;

import br.valinorti.util.POSystemUtils;

/**
 * @author leafar
 *
 */
public class ReportFactory {

	private static Logger logger = Logger.getLogger(ReportFactory.class);
	
	private static ReportFactory instance;
	/**
	 * Construtor privado.
	 */
	private ReportFactory() {}
	
	/**
	 * 
	 * @return
	 */
	public static ReportFactory getInstance() {
		if (instance==null) {
			logger.debug("Building ReportFactory instance.");
			instance = new ReportFactory();
		}
		return instance;
	}
	/**
	 * Recupera o relatorio no formato PDF.
	 * 
	 * @param params bean contendo os parametros
	 * @return
	 */
	public void getReportAsPDF(ReportParams params) throws IOException, JRException {
		JasperPrint jasperPrint = this.getJasperPrint(params);		
		//Exportar relatorio para PDF diretamente no outputstream
		OutputStream output = params.getOutput();
		JasperExportManager.exportReportToPdfStream(jasperPrint, output);
	}
	
	/**
	 * Gerar o relatorio passado em CSV.
	 * 
	 * @param params
	 * @throws IOException
	 * @throws JRException
	 */
	public void getReportAsCSV(ReportParams params) throws IOException, JRException {
		JasperPrint jasperPrint = this.getJasperPrint(params);
		//Exportar relatorio para CSV diretamente no outputstream
		OutputStream output = params.getOutput();
		JRCsvExporter exporter = new JRCsvExporter();
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, output);
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		exporter.exportReport();
	}
	
	/**
	 * Constroi um {@link JasperPrint} com os parametros passados.
	 * 
	 * @param params
	 * @return
	 * @throws IOException
	 * @throws JRException
	 */
	protected JasperPrint getJasperPrint(ReportParams params) throws IOException,JRException {
		FileInputStream srcStream = new FileInputStream(new File(params.getPath()));
		//Recuperar conexao com obanco de dados
		JasperPrint jasperPrint = JasperFillManager.fillReport(srcStream, params.getParams(), params.getConnection());
		jasperPrint.setLocaleCode(POSystemUtils.getSystemLocale().toString());
		
		return jasperPrint;
	}
}
