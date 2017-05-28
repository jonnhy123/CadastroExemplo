package br.univel;

import java.sql.Connection;
import java.util.Date;
import java.util.Locale;

import com.ibm.icu.text.SimpleDateFormat;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

public class ReportManager {

	private static final String JASPER_CONTATO = "C:\\Users\\User-01\\JaspersoftWorkspace\\MyReports\\HelloJasper.jasper";
	
	public void imprimir(){
		JasperPrint jasperPrintPDF = getPrint();
		Locale locale = Locale.getDefault();
		JasperViewer.viewReport(jasperPrintPDF,false,locale);
	}
	
	public void exportar(){
		JasperPrint jasperPrintPDF = getPrint();
		
		SimpleDateFormat sdf = new SimpleDateFormat("(yyyy-MM-dd)_HH-mm-ss");
		String data = sdf.format(new Date());
		
		String nomePdf = "relatorio-contatos_" + data + ".pdf";
	}

	private void getPrint() {
		JasperPrint jasperPrintPDF = null;
		Connection con = ConexaoDB.getInstance().getConnection();
		//Preenche
		try {
			jasperPrintPDF = JasperFillManager.fillReport(JASPER_CONTATO, null, con);
		} catch (JRException e) {
			e.printStackTrace();
		}
		Locale locale = Locale.getDefault();
		//Mostrar com JasperView
		JasperViewer.viewReport(jasperPrintPDF, false, locale);
	}
	
}
