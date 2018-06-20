/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyector.reportes;

import java.awt.Dialog;
import java.awt.HeadlessException;
import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.ExporterInput;
import net.sf.jasperreports.export.OutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.view.JasperViewer;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import proyector.dataBase.Conexion;

/**
 *
 * @author Juan
 */
public class GenerarReportes {

    public void credencialUsuario(String user) {
        try {
            Conexion conn = new Conexion();
            JasperReport report = null;

            try {
                InputStream f = new FileInputStream(new File(".").getCanonicalPath() + File.separator + "src" + File.separator + "proyector" + File.separator + "reportes" + File.separator + "gafete.jasper");
                System.out.println("Current dir:" + new java.io.File(".").getCanonicalPath() + File.separator + "src" + File.separator + "proyector" + File.separator + "reportes" + File.separator);
                report = (JasperReport) JRLoader.loadObject(f);
            } catch (JRException e) {
                System.out.println("Error cargando plantilla del reporte " + e);
            }

            Map parametro = new HashMap();
            parametro.put("idUsuario", user);

            JasperPrint j = JasperFillManager.fillReport(report, parametro, conn.getConexion());

            JasperViewer jv = new JasperViewer(j, false);
            jv.setTitle("Credencial Usuario");
            jv.setVisible(true);
            jv.setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);

            //generar archivo en vista
            int opc = JOptionPane.showConfirmDialog(jv, "Guardar la credencial?", "Informacíón", JOptionPane.INFORMATION_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
            if (opc == JOptionPane.OK_OPTION) {
                genFichero(j, user);
                JOptionPane.showMessageDialog(jv, "Encontrara el archivo con el nombre:\n\ncredencial_" + user + ".pdf", "Información", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (HeadlessException | IOException | SQLException | JRException e) {
            System.out.println("Error al generar la credencial: " + e);
        }
    }

    public void genFichero(JasperPrint j, String user) {
        try {
            // PDF Export.
            JRPdfExporter pdf = new JRPdfExporter();
            ExporterInput pdfInput = new SimpleExporterInput(j);

            // ExporterInput
            pdf.setExporterInput(pdfInput);
            JFileChooser f = new JFileChooser();
            f.setCurrentDirectory(new java.io.File(System.getProperty("user.home")));
            f.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            f.showSaveDialog(null);

            File path = f.getSelectedFile();
            System.out.println("path credencial:" + path + File.separator + "credencial_" + user + ".pdf");

            // ExporterOutput
            OutputStreamExporterOutput pdfOutput = new SimpleOutputStreamExporterOutput(path + (File.separator + "credencial_" + user + ".pdf"));

            // Output
            pdf.setExporterOutput(pdfOutput);

            //mime type
            SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
            pdf.setConfiguration(configuration);
            pdf.exportReport();
        } catch (JRException ex) {
            System.out.println("Error al generar archivo pdf de credencial:" + ex);
        }
    }

    public void codeBarProyectores() {
        try {
            Conexion conn = new Conexion();
            JasperReport report = null;

            try {
                InputStream f = new FileInputStream(new File(".").getCanonicalPath() + File.separator + "src" + File.separator + "proyector" + File.separator + "reportes" + File.separator + "cdbr.jasper");
                System.out.println("Current dir:" + new java.io.File(".").getCanonicalPath() + File.separator + "src" + File.separator + "proyector" + File.separator + "reportes" + File.separator);
                report = (JasperReport) JRLoader.loadObject(f);
            } catch (JRException | IOException e) {
                System.out.println("Error cargando plantilla del reporte " + e);
            }

            JasperPrint j = JasperFillManager.fillReport(report, null, conn.getConexion());

            JasperViewer jv = new JasperViewer(j, false);
            jv.setTitle("Codigos de barra para Videoproyectores");
            jv.setVisible(true);
            jv.setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        } catch (JRException | SQLException e) {
            System.out.println("Error al generar los codigos de barra: " + e);
        }
    }
    
    public void gafProyectores() {
        try {
            Conexion conn = new Conexion();
            JasperReport report = null;

            try {
                InputStream f = new FileInputStream(new File(".").getCanonicalPath() + File.separator + "src" + File.separator + "proyector" + File.separator + "reportes" + File.separator + "proyGaf.jasper");
                System.out.println("Current dir:" + new java.io.File(".").getCanonicalPath() + File.separator + "src" + File.separator + "proyector" + File.separator + "reportes" + File.separator);
                report = (JasperReport) JRLoader.loadObject(f);
            } catch (JRException | IOException e) {
                System.out.println("Error cargando plantilla del reporte " + e);
            }

            JasperPrint j = JasperFillManager.fillReport(report, null, conn.getConexion());

            JasperViewer jv = new JasperViewer(j, false);
            jv.setTitle("Gafetes para Videoproyectores");
            jv.setVisible(true);
            jv.setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        } catch (JRException | SQLException e) {
            System.out.println("Error al generar los codigos de barra: " + e);
        }
    }
    
    public void getRRTods(String[] date, int opc) {
        try {
            Conexion conn = new Conexion();
            JasperReport report = null;
            String ruta = "";
            try {
                switch (opc){
                    case 1:
                        ruta = new java.io.File(".").getCanonicalPath() + File.separator + "src" + File.separator + "proyector" + File.separator + "reportes" + File.separator+ "reportePry" + File.separator + "PryPrestamos.jasper";
                    break;
                    case 2:
                        ruta = new java.io.File(".").getCanonicalPath() + File.separator + "src" + File.separator + "proyector" + File.separator + "reportes" + File.separator+ "reporteProf" + File.separator + "ProfPrestamos.jasper";
                    break;
                    case 3:
                        ruta = new java.io.File(".").getCanonicalPath() + File.separator + "src" + File.separator + "proyector" + File.separator + "reportes" + File.separator+ "reporteDep" + File.separator + "DepPrestamos.jasper";
                    break;
                    case 4:
                        ruta = new java.io.File(".").getCanonicalPath() + File.separator + "src" + File.separator + "proyector" + File.separator + "reportes" + File.separator+ "reporteFallosAccYVid" + File.separator + "reporteAccesoriosYPry.jasper";
                    break;
                }
                
                InputStream f = new FileInputStream(ruta);
                System.out.println("Current dir:" + ruta);
                report = (JasperReport) JRLoader.loadObject(f);
            } catch (JRException | IOException e) {
                System.out.println("Error cargando plantilla del reporte " + e);
            }
            
            Map parameter = new HashMap();
            parameter.put("date1",date[0]);
            parameter.put("date2",date[1]);
            
            JasperPrint j = JasperFillManager.fillReport(report, parameter, conn.getConexion());

            JasperViewer jv = new JasperViewer(j, false);
            jv.setTitle("Reporte Videoproyectores");
            jv.setVisible(true);
            jv.setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        } catch (JRException | SQLException e) {
            System.out.println("Error al generar reporte TODO: " + e);
        }
    }
    
    
    
    public void getReporteFalloPry(String[] datos) {
        try {
            Conexion conn = new Conexion();
            JasperReport report = null;
            String ruta = "";
            try {
                ruta = new java.io.File(".").getCanonicalPath() + File.separator + "src" + File.separator + "proyector" + File.separator + "reportes" + File.separator+ "reporteFallosProyector.jasper";
                
                InputStream f = new FileInputStream(ruta);
                System.out.println("Current dir:" + ruta);
                report = (JasperReport) JRLoader.loadObject(f);
            } catch (JRException | IOException e) {
                System.out.println("Error cargando plantilla del reporte " + e);
            }
            
            Map parameter = new HashMap();
            parameter.put("area",datos[0]);
            parameter.put("solicitante",datos[1]);
            parameter.put("fecha",datos[2]);
            parameter.put("desc",datos[3]);
            parameter.put("opc",datos[4]);
            parameter.put("folio",datos[5]);
            parameter.put("nomPry",datos[6]);
            JasperPrint j = JasperFillManager.fillReport(report, parameter, conn.getConexion());

            JasperViewer jv = new JasperViewer(j, false);
            jv.setTitle("Reporte Videoproyectores");
            jv.setVisible(true);
            jv.setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        } catch (JRException | SQLException e) {
            System.out.println("Error al generar reporte TODO: " + e);
        }
    }
}
