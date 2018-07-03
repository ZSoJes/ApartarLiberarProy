/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyector.other;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Calendar;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import proyector.dataBase.crud.DepartamentoDB;
import proyector.dataBase.crud.InfoGraficasDB;

/**
 *
 * @author JuanGS
 */
public class GraphGen {

    private static final String[] MESES = {"Ene ", "Feb ", "Mar ", "Abr ", "May ", "Jun ", "Jul ", "Ago ", "Sep ", "Oct ", "Nov ", "Dic "};

    public void servicioPorProyector(JLabel lbl1, JLabel lbl2, JLabel lbl3) {
        DefaultPieDataset dtTotal = new DefaultPieDataset();
        DefaultPieDataset dtSmstr = new DefaultPieDataset();
        DefaultPieDataset dtMes = new DefaultPieDataset();
        try {
            InfoGraficasDB ini = new InfoGraficasDB();
            String[][] pry = ini.getProyServicioGrafica();
            int cant = pry.length;
            String lblPry;
            for (int i = 0; i < cant; i++) {
//                for (int j = 1; j < 4; j++) {   // total, mes, semestre
//                    pry[i][j] = (pry[i][j] == null ? "0" : pry[i][j]);  //comprueba que el proyector no tenga datos null y si es asi asigna 0
                    //int min = Integer.parseInt(pry[i][j]);
                    //lblPry = pry[i][0]+ ": " + (min / 60) + "hrs ";// + (min % 60) + "min";
                    int total = Integer.parseInt(pry[i][1]==null?"0":pry[i][1]);
                    int mes = Integer.parseInt(pry[i][2]==null?"0":pry[i][2]);
                    int sem = Integer.parseInt(pry[i][3]==null?"0":pry[i][3]);
                    dtTotal.setValue(pry[i][0]+ ": " + ((int)(total / 60)) + "hrs ", total);
                    dtMes.setValue(pry[i][0]+ ": " + ((int)(mes / 60)) + "hrs ", mes);
                    dtSmstr.setValue(pry[i][0]+ ": " + ((int)(sem / 60)) + "hrs ", sem);
//                }
            }
            Calendar c = Calendar.getInstance();
            String semestre;
            if(c.get(Calendar.MONTH)>= 6){
                semestre = "ENE-JUN";
            }else{
                semestre = "JUL-DIC";
            }
            JFreeChart chT = ChartFactory.createPieChart("Horas de servicio presentadas por Videoproyectores 2018", dtTotal, true, true, false);
            JFreeChart chS = ChartFactory.createPieChart("Horas de servicio presentadas por Videoproyectores "+semestre+ " 2018", dtSmstr, true, true, false);
            JFreeChart chM = ChartFactory.createPieChart("Horas de servicio presentadas por Videoproyectores " + MESES[c.get(Calendar.MONTH)].toUpperCase()+ "2018", dtMes, true, true, false);

            final PiePlot pie = (PiePlot) chT.getPlot();
            final PiePlot pie2 = (PiePlot) chS.getPlot();
            final PiePlot pie3 = (PiePlot) chM.getPlot();

            new GraphTheme().themePieChart(chT, pie, cant);
            new GraphTheme().themePieChart(chS, pie2, cant);
            new GraphTheme().themePieChart(chM, pie3, cant);

            BufferedImage imgT = chT.createBufferedImage(870, 350);
            BufferedImage imgS = chS.createBufferedImage(870, 350);
            BufferedImage imgM = chM.createBufferedImage(870, 350);

            lbl1.setIcon(new ImageIcon(imgT));
            lbl2.setIcon(new ImageIcon(imgM));
            lbl3.setIcon(new ImageIcon(imgS));
        } catch (SQLException e) {
            System.out.println("Error al recuperar informacion proyectores: " + e);
        }
    }

    public void solicitudesDelAnio(JLabel lbl) {
        DefaultCategoryDataset dtsc = new DefaultCategoryDataset();
        try {
            InfoGraficasDB ini = new InfoGraficasDB();
            Calendar c = Calendar.getInstance();
            int[] meses = ini.leerMeses(c.get(Calendar.YEAR));
            //System.out.println("meses resultado: " + Arrays.toString(meses));
            for (int i = 0; i < 12; i++) {
                dtsc.setValue(
                        meses[i],
                        "(" + meses[i] + ") " + MESES[i],
                        MESES[i]);
            }
            final JFreeChart ch = ChartFactory.createBarChart("Prestamos Realizados " + String.valueOf(c.get(Calendar.YEAR)), "Meses", "Cantidad", dtsc, PlotOrientation.VERTICAL, true, true, false); //domain axis label - range axis label - data - orientation - include legend - tooltips - urls

            new GraphTheme().themeBarChart(ch, true, false);

            BufferedImage image = ch.createBufferedImage(880, 300);
            lbl.setIcon(new ImageIcon(image));
        } catch (SQLException ex) {
            System.out.println("error al imprimir meses : " + ex);
        }
    }

    public void solicitudesPorProyector(JTabbedPane tabbedPn) {
        DefaultCategoryDataset dtSol = new DefaultCategoryDataset();
        try {
            InfoGraficasDB ini = new InfoGraficasDB();
            Calendar c = Calendar.getInstance();
            String[][] mesesD = ini.getProySolicitudes(c.get(Calendar.YEAR));
            int cant = mesesD.length;
            for (int proyector = 0; proyector < cant; proyector++) {
                // System.out.println("\t : : : : Arrray meses proy " + mesesD[proyector][0] + ":  " + Arrays.toString(mesesD[proyector]));
                for (int i = 0; i < 12; i++) {
                    dtSol.setValue(
                            Integer.parseInt(mesesD[proyector][i + 1]),
                            "(" + mesesD[proyector][i + 1] + ") " + MESES[i],
                            MESES[i]);
                }

                String titulo = "Prestamos realizados del Videoproyector " + mesesD[proyector][0] + " en " + String.valueOf(c.get(Calendar.YEAR));
                final JFreeChart ch = ChartFactory.createBarChart(titulo, "Meses", "Cantidad", dtSol, PlotOrientation.VERTICAL, true, true, false);
                new GraphTheme().themeBarChart(ch, true, false);
                BufferedImage img1 = ch.createBufferedImage(870, 350);

                JLabel lbl = new JLabel();
                lbl.setHorizontalAlignment(JLabel.CENTER);
                lbl.setVerticalAlignment(JLabel.CENTER);
                lbl.setBorder(new javax.swing.border.LineBorder(new Color(153, 153, 153)));
                lbl.setSize(870, 350);
                lbl.setIcon(new ImageIcon(img1));

                tabbedPn.addTab(mesesD[proyector][0], lbl);
                dtSol.clear();
            }
        } catch (SQLException e) {
            System.out.println("Error al recuperar informacion proyectores: " + e);
        }
    }

    public void solicitudesYservicioPorDepartamento(JLabel lbl1, JLabel lbl2) {
        DefaultCategoryDataset dtSolD = new DefaultCategoryDataset();
        DefaultCategoryDataset dtServD = new DefaultCategoryDataset();
        try {
            InfoGraficasDB ini = new InfoGraficasDB();
            Calendar c = Calendar.getInstance();
            String[][] regD = ini.getDeptoSolicitudesPorProfesor(c.get(Calendar.YEAR)); // { abvDepto, CantPrestamos }
            int cant = regD.length;
            for (int depto = 0; depto < cant; depto++) {
                String deptoN = new DepartamentoDB().getDepartamentoAbb(regD[depto][0])[0];
                String deptoLegend = "(" + regD[depto][1] + ")  " + deptoN;
                String deptoLegend2 = "(" + ((int)(Integer.parseInt(regD[depto][2])/60)) + "hrs) " + deptoN;
//                System.out.println("\t : : : : Solicutudes proy por Depto " + deptoNom + regD[depto][0] + ":  " + regD[depto][1]);
                dtSolD.setValue(
                        Integer.parseInt(regD[depto][1]),
                        deptoLegend,
                        ""); //regD[depto][0]
                dtServD.setValue(
                        Integer.parseInt(regD[depto][2] == null ? "0" : regD[depto][2]),
                        deptoLegend2,
                        "");
            }
            String titulo = "Prestamos realizados por Departamento " + String.valueOf(c.get(Calendar.YEAR));
            String tituloS = "Horas de servicio de Videoproyectores por Departamento " + String.valueOf(c.get(Calendar.YEAR));
            final JFreeChart ch = ChartFactory.createBarChart(titulo, "Departamentos", "Cantidad", dtSolD, PlotOrientation.VERTICAL, true, true, false);
            final JFreeChart chS = ChartFactory.createBarChart(tituloS, "Departamentos", "Cantidad", dtServD, PlotOrientation.VERTICAL, true, true, false);

            new GraphTheme().themeBarChart(ch, false, true);
            new GraphTheme().themeBarChart(chS, false, true);

            BufferedImage img1 = ch.createBufferedImage(890, 300);
            BufferedImage img2 = chS.createBufferedImage(890, 300);

            lbl1.setSize(890, 300);
            lbl1.setIcon(new ImageIcon(img1));

            lbl2.setSize(890, 300);
            lbl2.setIcon(new ImageIcon(img2));
//            genPng(ch);
        } catch (SQLException e) {
            System.out.println("Error al recuperar informacion proyectores: " + e);
        }
    }

    public void solicitudesYservicioPorDepartamentoPie(JLabel lbl1, JLabel lbl2) {
        DefaultPieDataset piedt = new DefaultPieDataset();
        DefaultPieDataset piedtServ = new DefaultPieDataset();
        try {
            InfoGraficasDB ini = new InfoGraficasDB();
            Calendar c = Calendar.getInstance();
            String[][] regD = ini.getDeptoSolicitudesPorProfesor(c.get(Calendar.YEAR));
            int cant = regD.length;
            for (int i = 0; i < cant; i++) {
                //String deptoN = new DepartamentoDB().getDepartamentoAbb(regD[i][0])[0];
                String deptoLegend = "(" + regD[i][1] + ")  " + regD[i][0];
                String deptoLegend2 = regD[i][0] + " : "+ ((int)(Integer.parseInt(regD[i][2])/60)) + "hrs";
                piedt.setValue(deptoLegend, Integer.parseInt(regD[i][1]));
                piedtServ.setValue(deptoLegend2, Integer.parseInt(regD[i][2]));
            }
            String titulo = "Prestamos realizados por Departamento " + String.valueOf(c.get(Calendar.YEAR));
            String tituloS = "Horas de servicio de Videoproyectores por Departamento " + String.valueOf(c.get(Calendar.YEAR));
            JFreeChart ch = ChartFactory.createPieChart(titulo, piedt, true, true, false);
            JFreeChart chS = ChartFactory.createPieChart(tituloS, piedtServ, true, true, false);

            final PiePlot pie = (PiePlot) ch.getPlot();
            final PiePlot pie2 = (PiePlot) chS.getPlot();

            new GraphTheme().themePieChart(ch, pie, cant);
            new GraphTheme().themePieChart(chS, pie2, cant);

            BufferedImage imgT = ch.createBufferedImage(870, 350);
            BufferedImage imgS = chS.createBufferedImage(870, 350);

            lbl1.setIcon(new ImageIcon(imgT));
            lbl2.setIcon(new ImageIcon(imgS));

        } catch (SQLException e) {
            System.out.println("Error al recuperar informacion departamentos: " + e);
        }
    }

    public static void genPng(JFreeChart ch) {
        File a = new File("D:\\JuanGS\\Documents\\Ejecutables\\imagen.png");
        try {
            ChartUtilities.saveChartAsPNG(a, ch, 890, 300);
        } catch (IOException ex) {
            System.out.println("Error al generar grafico en png: " + ex);
        }
    }
}
