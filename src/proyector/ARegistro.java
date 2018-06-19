/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyector;

import java.awt.Color;
import java.awt.Font;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultRowSorter;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.RowFilter.ComparisonType;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.RectangleInsets;
import org.jfree.chart.renderer.category.BarRenderer;
import proyector.dataBase.crud.AulaDB;
import proyector.dataBase.crud.LeerInicio;
import proyector.dataBase.crud.PrestamoDB;
import proyector.dataBase.crud.ProfesorDB;
import proyector.dataBase.crud.VideoproyectorDB;
import proyector.reportes.GenerarReportes;
/**
 *
 * @author JuanGSot
 */
public class ARegistro extends javax.swing.JFrame {

    ImageIcon img = new ImageIcon("./src/imagenes/logo-adm.png");
    String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
    //DateFormat fecha = DateFormat.getDateInstance();
    SimpleDateFormat fecha = new SimpleDateFormat("yyyy-MM-dd");
    /**
     * Creates new form ARegistro
     */
    public ARegistro() {
        initComponents();    
        jdtChooser2.setVisible(false);
        jdtChooser1.setVisible(false);
        lblIni.setVisible(false);
        lblFn.setVisible(false);
        panelOPC.setVisible(false);
        jScrollPane2.getVerticalScrollBar().setUnitIncrement(16);
        txtBuscarTXT.setVisible(false);
        try {
            getTable(3, "", "");
        } catch (SQLException e) {
            System.out.println("Error al generar tabla constructor:" + e);
        }
        jComboBox1.setSelectedIndex(3);
        jTable1.setFont(new java.awt.Font("SansSerif", 0, 11));
        jTable1.getTableHeader().setFont(new java.awt.Font("SansSerif", 0, 10));
        labelFecha.setText(date);                                   //coloca la fecha
        Timer timer = new Timer(500, (ActionEvent e) -> {
            reloj();                                                //coloca la hora
        });
        timer.setRepeats(true);
        timer.setCoalesce(true);
        timer.setInitialDelay(0);
        timer.start();
        graficas();
//        try{
//        LeerInicio ini = new LeerInicio();
//        int[] meses = ini.leerMeses(2018);
//        System.out.println("meses resultado: " + Arrays.toString(meses));
//        }catch(SQLException ex){
//            System.out.println("error al imprimir meses : " + ex);
//        }
//SELECT  SUM( CASE WHEN EXTRACT(MONTH FROM CREADO) = 01  THEN 1 ELSE 0 END)  AS ENE,
//	SUM( CASE WHEN EXTRACT(MONTH FROM CREADO) = 02  THEN 1 ELSE 0 END)  AS FEB,
//	SUM( CASE WHEN EXTRACT(MONTH FROM CREADO) = 03  THEN 1 ELSE 0 END)  AS MAR,
//	SUM( CASE WHEN EXTRACT(MONTH FROM CREADO) = 04  THEN 1 ELSE 0 END)  AS ABR,
//	SUM( CASE WHEN EXTRACT(MONTH FROM CREADO) = 05  THEN 1 ELSE 0 END)  AS MAY,
//	SUM( CASE WHEN EXTRACT(MONTH FROM CREADO) = 06  THEN 1 ELSE 0 END)  AS JUN,
//	SUM( CASE WHEN EXTRACT(MONTH FROM CREADO) = 07  THEN 1 ELSE 0 END)  AS JUL,
//	SUM( CASE WHEN EXTRACT(MONTH FROM CREADO) = 08  THEN 1 ELSE 0 END)  AS AGO,
//	SUM( CASE WHEN EXTRACT(MONTH FROM CREADO) = 09  THEN 1 ELSE 0 END)  AS SEP,
//	SUM( CASE WHEN EXTRACT(MONTH FROM CREADO) = 10  THEN 1 ELSE 0 END)  AS OCT,
//	SUM( CASE WHEN EXTRACT(MONTH FROM CREADO) = 11  THEN 1 ELSE 0 END)  AS NOV,
//	SUM( CASE WHEN EXTRACT(MONTH FROM CREADO) = 12  THEN 1 ELSE 0 END)  AS DIC
//FROM (SELECT *  FROM E_PRESTAMOS  WHERE ESTATUS = FALSE)
        jScrollPane2.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, Color.BLACK));
    }

    /**
     * Se encarga de colocar la hora con un formato especifico
     */
    public void reloj() {
        labelHora.setText(new SimpleDateFormat("hh:mm:ss a").format(new Date()));
    }

    /**
     * Introduce la informacion en un jTable indicado
     *
     * @param opc
     * @param miDate1
     * @param miDate2
     * @throws SQLException
     */
    public void getTable(int opc, String miDate1, String miDate2) throws SQLException {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        String[] cols = {jTable1.getColumnName(0), jTable1.getColumnName(1), jTable1.getColumnName(2), jTable1.getColumnName(3), jTable1.getColumnName(4), jTable1.getColumnName(5), jTable1.getColumnName(6), jTable1.getColumnName(7), jTable1.getColumnName(8)};

        Calendar c = Calendar.getInstance(); //calendar toma los meses del 0(enero) a 11(dic)
        Calendar c1 = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
        
        String[][] data = datosRegistros(opc, miDate1, miDate2);//prestamos que estan entre fechas de dos dias especificos
        
        
        int count = data.length;
        String[][] data2 = new String[count][9];

        for (int i = 0; i < count; i++) {
            for (int j = 0; j < 9; j++) {
                if (j == 0) {
                    data2[i][j] = data[i][0];
                }
                if (j == 1 || j == 2) {
                    String[] arrUsu = new LeerInicio().getUsuario(data[i][j]);
                    data2[i][j] = arrUsu[1] + " " + arrUsu[2] + " " + arrUsu[3];
                }
                if (j == 3) {
                    VideoproyectorDB vidProy = new VideoproyectorDB();
                    data2[i][j] = vidProy.getProyector(vidProy.getProyectorNoSerie(data[i][3]))[1];
                }
                if (j == 4) {
                    ProfesorDB profe = new ProfesorDB();
                    String[] arrProfe = profe.getProfesor(data[i][4]);
                    data2[i][j] = arrProfe[2] + " " + arrProfe[3] + " " + arrProfe[4];
                }
                if (j == 5) {
                    AulaDB aula = new AulaDB();
                    data2[i][j] = aula.getAula(Integer.parseInt(data[i][5]));
                }
                if (j == 6) {
                    int[] arr = formatFecha(data[i][6]);
                    c.set(arr[0], arr[1], arr[2], arr[3], arr[4]);
                    data2[i][6] = String.valueOf(dateFormat.format(c.getTime()));
                    arr = formatFecha(data[i][7]);
                    c.set(arr[0], arr[1], arr[2], arr[3], arr[4]);
                    data2[i][7] = String.valueOf(dateFormat.format(c.getTime()));
                }
                if (j == 8) {
                    int[] arr = formatFecha(data[i][6]);
                    c.set(arr[0], arr[1], arr[2], arr[3], arr[4]);
                    arr = formatFecha(data[i][7]);
                    c1.set(arr[0], arr[1], arr[2], arr[3], arr[4]);
                    long end = c1.getTimeInMillis();
                    long start = c.getTimeInMillis();
                    long diferencia = TimeUnit.MILLISECONDS.toMinutes(Math.abs(end - start));
                    int horas =(int) (diferencia/60);
                    int minutos =(int) (diferencia%60);
                    data2[i][8] = (horas>0?horas+"hrs ":"0hrs ")+(minutos>0?minutos+"min ":"0min ");
                }
            }
        }

        model.setDataVector(data2, cols);
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(30);
        jTable1.getColumnModel().getColumn(1).setPreferredWidth(178);
        jTable1.getColumnModel().getColumn(2).setPreferredWidth(178);
        jTable1.getColumnModel().getColumn(3).setPreferredWidth(98);
        jTable1.getColumnModel().getColumn(4).setPreferredWidth(160);
        jTable1.getColumnModel().getColumn(5).setPreferredWidth(50);
        jTable1.getColumnModel().getColumn(6).setPreferredWidth(118);
        jTable1.getColumnModel().getColumn(7).setPreferredWidth(118);
        jTable1.getColumnModel().getColumn(8).setPreferredWidth(94);
        jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(model);
        jTable1.setRowSorter(sorter);
    }

    public int[] formatFecha(String date) {
        int[] res = new int[5];
        res[0] = Integer.parseInt(date.substring(0, 4));              //año
        res[1] = Integer.parseInt(date.substring(5, 7)) - 1;         //mes
        res[2] = Integer.parseInt(date.substring(8, 10));            //dia
        res[3] = Integer.parseInt(date.substring(11, 13));        //hora
        res[4] = Integer.parseInt(date.substring(14, 16));       //minuto
        return res;
    }

    public String[][] datosRegistros(int opc, String miDate1, String miDate2) throws SQLException{
        PrestamoDB reg = new PrestamoDB();
        String[][] data = new String[0][8];
        Calendar c = Calendar.getInstance();
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);
        if(opc == 0){   //todos
            data = reg.getPrestamos(false, true);
        }if(opc == 1){  //hoy
            SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd"); 
            miDate1 = dt.format(c.getTime()).concat(" 00:00:00");
            miDate2 = dt.format(c.getTime()).concat(" 23:59:59");
        }if(opc == 2){  //mes
            c.set(Calendar.DAY_OF_MONTH, 1);
            DateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd");
            miDate1 = df.format(c.getTime()) + " 00:00:00";
            miDate2 = miDate1.substring(0, 8) + c.getActualMaximum(Calendar.DAY_OF_MONTH) + " 23:59:59";;
        }if(opc == 3){  //semestre
            if (month < 6) {
                c.set(Calendar.MONTH, 05);
                miDate1 = year + "-01-01 00:00:00";
                miDate2 = year + "-06-" + c.getActualMaximum(Calendar.DAY_OF_MONTH) + " 23:59:59";
            } else {
                c.set(Calendar.MONTH, 11);
                miDate1 = year + "-07-01 00:00:00";
                miDate2 = year + "-12-" + c.getActualMaximum(Calendar.DAY_OF_MONTH) + " 23:59:59";
            }
        }if(opc == 4){  //año
            c.set(Calendar.MONTH, 11);
            miDate1 = year + "-01-01 00:00:00";
            miDate2 = year + "-12-" + c.getActualMaximum(Calendar.DAY_OF_MONTH) + " 23:59:59";
        }
        if(opc >= 1 && opc <= 5){
            data = reg.getPrestamos(miDate1, miDate2); //prestamos que estan entre fechas de dos dias especificos
        }
        System.out.println("Registros existentes:" + data.length + "\nEntre las fechas: " + miDate1 + miDate2);
        return data;
    }
    
    public void graficas(){
        DefaultCategoryDataset dtsc = new DefaultCategoryDataset();
        String[] mesN = {"Ene ", "Feb ", "Mar ", "Abr ", "May ", "Jun ", "Jul ", "Ago ", "Sep ", "Oct ", "Nov ", "Dic "};
        try{
        LeerInicio ini = new LeerInicio();
        Calendar c = Calendar.getInstance();
        int[] meses = ini.leerMeses(c.get(Calendar.YEAR));
        System.out.println("meses resultado: " + Arrays.toString(meses));
        for(int i = 0; i < 12; i++){
            dtsc.setValue(meses[i], mesN[i], mesN[i]+"("+meses[i]+")");
        }
        final JFreeChart ch = ChartFactory.createBarChart("Prestamos realizados durante el presente año " + String.valueOf(c.get(Calendar.YEAR)),  "Meses", "Cantidad", dtsc, PlotOrientation.VERTICAL, true, true, false); //domain axis label - range axis label - data - orientation - include legend - tooltips - urls
        StandardChartTheme theme = (StandardChartTheme)org.jfree.chart.StandardChartTheme.createJFreeTheme();
        String fontName = "Lucida Sans";
        theme.setTitlePaint( Color.decode( "#4572a7" ) );
        theme.setExtraLargeFont( new Font(fontName,Font.BOLD, 17) ); //title
        theme.setLargeFont( new Font(fontName,Font.BOLD, 15)); //axis-title
        theme.setRegularFont( new Font(fontName,Font.PLAIN, 11));
        theme.setRangeGridlinePaint( Color.decode("#C0C0C0"));
        theme.setPlotBackgroundPaint( Color.white );
        theme.setChartBackgroundPaint( Color.white );
        theme.setGridBandPaint( Color.red );
        theme.setAxisOffset( new RectangleInsets(0,0,0,0) );
        theme.setBarPainter(new StandardBarPainter());
        theme.setAxisLabelPaint( Color.decode("#666666")  );
        theme.apply( ch );
        
        ch.getCategoryPlot().setOutlineVisible( false );
        ch.getCategoryPlot().getRangeAxis().setAxisLineVisible( false );
        ch.getCategoryPlot().getRangeAxis().setTickMarksVisible( false );
        ch.getCategoryPlot().setRangeGridlineStroke( new java.awt.BasicStroke() );
        ch.getCategoryPlot().getRangeAxis().setTickLabelPaint( Color.decode("#666666") );
        ch.getCategoryPlot().getDomainAxis().setTickLabelPaint( Color.decode("#666666") );
        ch.setTextAntiAlias( true );
        ch.setAntiAlias( true );
        int count = 0;
        String[] colorM = {"#2196F3", "#FFC107", "#FF5722" , "#8BC34A", "#E91E63", "#26A69A" , "#96492D", "#FFEB3B", "#673AB7", "#00BCD4" , "#8BC34A", "#F44336"};
        for(String code : colorM){
            ch.getCategoryPlot().getRenderer().setSeriesPaint( count, Color.decode( code ));
            count++;
        }
        BarRenderer rend = (BarRenderer) ch.getCategoryPlot().getRenderer();
        rend.setShadowVisible( true );
        rend.setShadowXOffset( 4);
        rend.setShadowYOffset( 2);
        rend.setShadowPaint( Color.decode( "#C0C0C0"));
        rend.setMaximumBarWidth(0.1);
        rend.setItemMargin(-7);
        
        
        
        BufferedImage image = ch.createBufferedImage(880, 300);
        jMiLabel.setIcon(new ImageIcon(image));
        }catch(SQLException ex){
            System.out.println("error al imprimir meses : " + ex);
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jTable2 = new javax.swing.JTable();
        dlgReporte = new javax.swing.JDialog();
        pblBkReporte = new javax.swing.JPanel();
        lblTituloDlg = new javax.swing.JLabel();
        pnlFechas = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        rb1 = new javax.swing.JRadioButton();
        rb2 = new javax.swing.JRadioButton();
        rb3 = new javax.swing.JRadioButton();
        rb4 = new javax.swing.JRadioButton();
        rb5 = new javax.swing.JRadioButton();
        rb6 = new javax.swing.JRadioButton();
        pnlFechasFiltro = new javax.swing.JPanel();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        pnlDatos = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        dRb1 = new javax.swing.JRadioButton();
        dRb2 = new javax.swing.JRadioButton();
        dRb3 = new javax.swing.JRadioButton();
        dRb4 = new javax.swing.JRadioButton();
        dRb5 = new javax.swing.JRadioButton();
        pnlBotones = new javax.swing.JPanel();
        btnGenerarRR = new javax.swing.JButton();
        btnCerrarRR = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        btnGroupFecha = new javax.swing.ButtonGroup();
        btnGroupDatos = new javax.swing.ButtonGroup();
        pnlBackground = new javax.swing.JPanel();
        pnlCabecera = new javax.swing.JPanel();
        lblIco1 = new javax.swing.JLabel();
        lblIco2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        labelHora = new javax.swing.JLabel();
        labelFecha = new javax.swing.JLabel();
        btnMenu = new javax.swing.JToggleButton();
        btnRegresar = new javax.swing.JButton();
        panelOPC = new javax.swing.JPanel();
        ico3 = new javax.swing.JLabel();
        ico4 = new javax.swing.JLabel();
        ico5 = new javax.swing.JLabel();
        ico6 = new javax.swing.JLabel();
        ico7 = new javax.swing.JLabel();
        ico8 = new javax.swing.JLabel();
        lblTitulo = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        btnFiltrar = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jMiLabel = new javax.swing.JLabel();
        jdtChooser2 = new com.toedter.calendar.JDateChooser();
        lblIni = new javax.swing.JLabel();
        jdtChooser1 = new com.toedter.calendar.JDateChooser();
        lblFn = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        cbEspecifico = new javax.swing.JComboBox<>();
        txtBuscarTXT = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID_REGISTRO", "USR_ENTREGA", "USR_RECIBE", "VIDEOPROYECTOR", "PROFESOR", "AULA", "FECHA_REGRESO", "FECHA_SALIDA", "TIEMPO_DE_USO"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });

        dlgReporte.setTitle("[Reportes Generador]");
        dlgReporte.setMinimumSize(new java.awt.Dimension(520, 610));
        dlgReporte.setModal(true);
        dlgReporte.setPreferredSize(new java.awt.Dimension(520, 610));

        pblBkReporte.setBackground(new java.awt.Color(255, 193, 7));
        pblBkReporte.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 4));

        lblTituloDlg.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        lblTituloDlg.setForeground(new java.awt.Color(255, 255, 255));
        lblTituloDlg.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTituloDlg.setText("Menú para la generación de reportes");

        pnlFechas.setBackground(new java.awt.Color(255, 152, 0));
        pnlFechas.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Indique filtro de fecha", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(255, 255, 255))); // NOI18N

        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Reportes con fecha de:");

        btnGroupFecha.add(rb1);
        rb1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        rb1.setForeground(new java.awt.Color(255, 255, 255));
        rb1.setText("Hoy");
        rb1.setOpaque(false);

        btnGroupFecha.add(rb2);
        rb2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        rb2.setForeground(new java.awt.Color(255, 255, 255));
        rb2.setText("Semana");
        rb2.setToolTipText("Fechas entre Lunes y Domingo");
        rb2.setOpaque(false);

        btnGroupFecha.add(rb3);
        rb3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        rb3.setForeground(new java.awt.Color(255, 255, 255));
        rb3.setText("Mes");
        rb3.setOpaque(false);

        btnGroupFecha.add(rb4);
        rb4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        rb4.setForeground(new java.awt.Color(255, 255, 255));
        rb4.setText("Semestre");
        rb4.setOpaque(false);

        btnGroupFecha.add(rb5);
        rb5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        rb5.setForeground(new java.awt.Color(255, 255, 255));
        rb5.setText("Año");
        rb5.setOpaque(false);

        btnGroupFecha.add(rb6);
        rb6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        rb6.setForeground(new java.awt.Color(255, 255, 255));
        rb6.setText("Fecha Especifica");
        rb6.setOpaque(false);
        rb6.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                rb6ItemStateChanged(evt);
            }
        });

        pnlFechasFiltro.setOpaque(false);

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Inicio:");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Fin:");

        javax.swing.GroupLayout pnlFechasFiltroLayout = new javax.swing.GroupLayout(pnlFechasFiltro);
        pnlFechasFiltro.setLayout(pnlFechasFiltroLayout);
        pnlFechasFiltroLayout.setHorizontalGroup(
            pnlFechasFiltroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlFechasFiltroLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jLabel9)
                .addGap(18, 18, 18)
                .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlFechasFiltroLayout.setVerticalGroup(
            pnlFechasFiltroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlFechasFiltroLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlFechasFiltroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8)
                    .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addContainerGap())
        );

        javax.swing.GroupLayout pnlFechasLayout = new javax.swing.GroupLayout(pnlFechas);
        pnlFechas.setLayout(pnlFechasLayout);
        pnlFechasLayout.setHorizontalGroup(
            pnlFechasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlFechasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlFechasFiltro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(pnlFechasLayout.createSequentialGroup()
                .addGroup(pnlFechasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlFechasLayout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addGroup(pnlFechasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addGroup(pnlFechasLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(rb1)
                                .addGap(18, 18, 18)
                                .addComponent(rb2)
                                .addGap(18, 18, 18)
                                .addComponent(rb3)
                                .addGap(18, 18, 18)
                                .addComponent(rb4))))
                    .addGroup(pnlFechasLayout.createSequentialGroup()
                        .addGap(68, 68, 68)
                        .addComponent(rb5)
                        .addGap(35, 35, 35)
                        .addComponent(rb6)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlFechasLayout.setVerticalGroup(
            pnlFechasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFechasLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlFechasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rb4)
                    .addComponent(rb1)
                    .addComponent(rb3)
                    .addComponent(rb2))
                .addGap(18, 18, 18)
                .addGroup(pnlFechasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rb5)
                    .addComponent(rb6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlFechasFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6))
        );

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(33, 33, 33));
        jLabel6.setText("<html>Es necesario indicar la fecha<br>en la que deben recuperarse los datos:");

        pnlDatos.setBackground(new java.awt.Color(100, 221, 23));
        pnlDatos.setBorder(javax.swing.BorderFactory.createTitledBorder("Especifique los datos"));

        jLabel10.setText("Los datos a mostrar seran los de:");

        btnGroupDatos.add(dRb1);
        dRb1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        dRb1.setText("Departamentos");
        dRb1.setOpaque(false);

        btnGroupDatos.add(dRb2);
        dRb2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        dRb2.setText("Profesores");
        dRb2.setOpaque(false);

        btnGroupDatos.add(dRb3);
        dRb3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        dRb3.setText("Videoproyectores");
        dRb3.setOpaque(false);

        btnGroupDatos.add(dRb4);
        dRb4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        dRb4.setText("<html>Fallos y Perdidas<br>de articulos/videoproyectores</html>");
        dRb4.setOpaque(false);

        btnGroupDatos.add(dRb5);
        dRb5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        dRb5.setText("Todos ");
        dRb5.setOpaque(false);

        javax.swing.GroupLayout pnlDatosLayout = new javax.swing.GroupLayout(pnlDatos);
        pnlDatos.setLayout(pnlDatosLayout);
        pnlDatosLayout.setHorizontalGroup(
            pnlDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDatosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlDatosLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(pnlDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dRb5)
                            .addComponent(dRb2))
                        .addGap(22, 22, 22)
                        .addGroup(pnlDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dRb4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnlDatosLayout.createSequentialGroup()
                                .addComponent(dRb3)
                                .addGap(38, 38, 38)
                                .addComponent(dRb1))))
                    .addComponent(jLabel10))
                .addContainerGap(57, Short.MAX_VALUE))
        );
        pnlDatosLayout.setVerticalGroup(
            pnlDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDatosLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dRb3)
                    .addComponent(dRb5)
                    .addComponent(dRb1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(dRb4)
                    .addComponent(dRb2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(23, 23, 23))
        );

        pnlBotones.setBackground(new java.awt.Color(3, 169, 244));

        btnGenerarRR.setText("Generar");
        btnGenerarRR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerarRRActionPerformed(evt);
            }
        });

        btnCerrarRR.setText("Cerrar");
        btnCerrarRR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarRRActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlBotonesLayout = new javax.swing.GroupLayout(pnlBotones);
        pnlBotones.setLayout(pnlBotonesLayout);
        pnlBotonesLayout.setHorizontalGroup(
            pnlBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBotonesLayout.createSequentialGroup()
                .addGap(76, 76, 76)
                .addComponent(btnGenerarRR, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnCerrarRR, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(76, 76, 76))
        );
        pnlBotonesLayout.setVerticalGroup(
            pnlBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBotonesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGenerarRR)
                    .addComponent(btnCerrarRR))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(33, 33, 33));
        jLabel11.setText("<html>Debe especificar una opcion de las siguientes<br>para recuperar los datos necesarios:");

        javax.swing.GroupLayout pblBkReporteLayout = new javax.swing.GroupLayout(pblBkReporte);
        pblBkReporte.setLayout(pblBkReporteLayout);
        pblBkReporteLayout.setHorizontalGroup(
            pblBkReporteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pblBkReporteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pblBkReporteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pblBkReporteLayout.createSequentialGroup()
                        .addGroup(pblBkReporteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pnlFechas, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblTituloDlg, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(pnlDatos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(pnlBotones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(pblBkReporteLayout.createSequentialGroup()
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 222, Short.MAX_VALUE)))
                        .addGap(11, 11, 11))
                    .addGroup(pblBkReporteLayout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        pblBkReporteLayout.setVerticalGroup(
            pblBkReporteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pblBkReporteLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTituloDlg)
                .addGap(27, 27, 27)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlFechas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlDatos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41)
                .addComponent(pnlBotones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38))
        );

        javax.swing.GroupLayout dlgReporteLayout = new javax.swing.GroupLayout(dlgReporte.getContentPane());
        dlgReporte.getContentPane().setLayout(dlgReporteLayout);
        dlgReporteLayout.setHorizontalGroup(
            dlgReporteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pblBkReporte, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        dlgReporteLayout.setVerticalGroup(
            dlgReporteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pblBkReporte, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("[Registros]");
        setIconImage(img.getImage());
        setMinimumSize(new java.awt.Dimension(1024, 600));
        setResizable(false);

        pnlBackground.setBackground(new java.awt.Color(255, 255, 255));
        pnlBackground.setMinimumSize(new java.awt.Dimension(1024, 600));
        pnlBackground.setPreferredSize(new java.awt.Dimension(1024, 600));
        pnlBackground.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlCabecera.setBackground(new java.awt.Color(1, 200, 1));
        pnlCabecera.setMinimumSize(new java.awt.Dimension(1024, 83));
        pnlCabecera.setPreferredSize(new java.awt.Dimension(1024, 83));

        lblIco1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/tecnm2.png"))); // NOI18N

        lblIco2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/sep.png"))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Trebuchet MS", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Hora");

        jLabel2.setFont(new java.awt.Font("Trebuchet MS", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Fecha");

        labelHora.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        labelHora.setForeground(new java.awt.Color(255, 255, 255));
        labelHora.setText("00:00:00 PM");

        labelFecha.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        labelFecha.setForeground(new java.awt.Color(255, 255, 255));
        labelFecha.setText("00-00-0000");

        javax.swing.GroupLayout pnlCabeceraLayout = new javax.swing.GroupLayout(pnlCabecera);
        pnlCabecera.setLayout(pnlCabeceraLayout);
        pnlCabeceraLayout.setHorizontalGroup(
            pnlCabeceraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCabeceraLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblIco1)
                .addGap(36, 36, 36)
                .addComponent(jLabel1)
                .addGap(20, 20, 20)
                .addComponent(labelHora)
                .addGap(107, 107, 107)
                .addComponent(jLabel2)
                .addGap(20, 20, 20)
                .addComponent(labelFecha)
                .addGap(36, 36, 36)
                .addComponent(lblIco2)
                .addContainerGap())
        );
        pnlCabeceraLayout.setVerticalGroup(
            pnlCabeceraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblIco1)
            .addGroup(pnlCabeceraLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblIco2))
            .addGroup(pnlCabeceraLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(pnlCabeceraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlCabeceraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(labelHora))
                    .addGroup(pnlCabeceraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(labelFecha))))
        );

        pnlBackground.add(pnlCabecera, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        btnMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Checklist_24px.png"))); // NOI18N
        btnMenu.setMaximumSize(new java.awt.Dimension(40, 37));
        btnMenu.setMinimumSize(new java.awt.Dimension(40, 37));
        btnMenu.setPreferredSize(new java.awt.Dimension(40, 37));
        btnMenu.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                btnMenuItemStateChanged(evt);
            }
        });
        pnlBackground.add(btnMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(4, 85, -1, -1));

        btnRegresar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Back To_35px.png"))); // NOI18N
        btnRegresar.setMaximumSize(new java.awt.Dimension(40, 37));
        btnRegresar.setMinimumSize(new java.awt.Dimension(40, 37));
        btnRegresar.setPreferredSize(new java.awt.Dimension(40, 37));
        btnRegresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegresarActionPerformed(evt);
            }
        });
        pnlBackground.add(btnRegresar, new org.netbeans.lib.awtextra.AbsoluteConstraints(4, 125, -1, -1));

        panelOPC.setBackground(new java.awt.Color(250, 250, 250));
        panelOPC.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(171, 173, 179), 1, true));
        panelOPC.setMinimumSize(new java.awt.Dimension(280, 50));
        panelOPC.setPreferredSize(new java.awt.Dimension(280, 50));
        panelOPC.setLayout(new java.awt.GridBagLayout());

        ico3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/prestamo_32px.png"))); // NOI18N
        ico3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ico3MouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 15, 0, 5);
        panelOPC.add(ico3, gridBagConstraints);

        ico4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/devolu_32px.png"))); // NOI18N
        ico4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ico4MouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
        panelOPC.add(ico4, gridBagConstraints);

        ico5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/prof_42px.png"))); // NOI18N
        ico5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ico5MouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 15, 0, 5);
        panelOPC.add(ico5, gridBagConstraints);

        ico6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/depart_42px.png"))); // NOI18N
        ico6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ico6MouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
        panelOPC.add(ico6, gridBagConstraints);

        ico7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/aul_42px.png"))); // NOI18N
        ico7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ico7MouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 4, 0);
        panelOPC.add(ico7, gridBagConstraints);

        ico8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Video Projector_42px.png"))); // NOI18N
        ico8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ico8MouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 15);
        panelOPC.add(ico8, gridBagConstraints);

        pnlBackground.add(panelOPC, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 85, -1, -1));

        lblTitulo.setFont(new java.awt.Font("Trebuchet MS", 3, 24)); // NOI18N
        lblTitulo.setText("Registros");
        pnlBackground.add(lblTitulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 110, -1, -1));

        jScrollPane2.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane2.setBorder(null);
        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane2.setMinimumSize(new java.awt.Dimension(1024, 450));
        jScrollPane2.setPreferredSize(new java.awt.Dimension(1024, 450));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(1024, 900));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnFiltrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Search_25px.png"))); // NOI18N
        btnFiltrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFiltrarActionPerformed(evt);
            }
        });
        jPanel1.add(btnFiltrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 5, -1, -1));

        jComboBox1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Todos los registros", "Registros de Hoy", "Registros del Mes", "Registros del Semestre", "Registros del Año", "Personalizado" }));
        jComboBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox1ItemStateChanged(evt);
            }
        });
        jPanel1.add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 10, 180, -1));

        jLabel3.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel3.setText("Filtro:");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "USR_ENTREGA", "USR_RECIBE", "PROYECTOR", "PROFESOR", "AULA", "FECHA_SALIDA", "FECHA_REGRESO", "TIEMPO"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.getTableHeader().setReorderingAllowed(false);
        jScrollPane3.setViewportView(jTable1);

        jPanel1.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 1010, 320));

        jMiLabel.setText("jMiLabel");
        jMiLabel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        jMiLabel.setPreferredSize(new java.awt.Dimension(880, 300));
        jPanel1.add(jMiLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 440, -1, -1));

        jdtChooser2.setToolTipText("Fecha de fin");
        jdtChooser2.setDateFormatString("dd/MM/yyyy");
        jdtChooser2.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jPanel1.add(jdtChooser2, new org.netbeans.lib.awtextra.AbsoluteConstraints(575, 10, 150, -1));

        lblIni.setText("Inicio:");
        jPanel1.add(lblIni, new org.netbeans.lib.awtextra.AbsoluteConstraints(345, 14, -1, -1));

        jdtChooser1.setToolTipText("Fecha de inicio");
        jdtChooser1.setDateFormatString("dd/MM/yyyy");
        jdtChooser1.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jPanel1.add(jdtChooser1, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 10, 150, -1));

        lblFn.setText("Fin:");
        jPanel1.add(lblFn, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 14, -1, -1));

        jLabel5.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel5.setText("Buscar especificamente con:");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, -1, -1));

        cbEspecifico.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        cbEspecifico.setMaximumRowCount(6);
        cbEspecifico.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ninguno", "Nombre del usuario que entrego", "Nombre del usuario que recibio", "Nombre de VideoProyector", "ID del Profesor", "Aula" }));
        cbEspecifico.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbEspecificoItemStateChanged(evt);
            }
        });
        jPanel1.add(cbEspecifico, new org.netbeans.lib.awtextra.AbsoluteConstraints(225, 46, 230, -1));

        txtBuscarTXT.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtBuscarTXT.setMinimumSize(new java.awt.Dimension(300, 25));
        txtBuscarTXT.setPreferredSize(new java.awt.Dimension(300, 25));
        jPanel1.add(txtBuscarTXT, new org.netbeans.lib.awtextra.AbsoluteConstraints(465, 47, -1, -1));

        jScrollPane2.setViewportView(jPanel1);

        pnlBackground.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 170, -1, 430));

        jLabel4.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Reportes");
        jLabel4.setMaximumSize(new java.awt.Dimension(70, 19));
        jLabel4.setMinimumSize(new java.awt.Dimension(70, 19));
        jLabel4.setPreferredSize(new java.awt.Dimension(70, 19));
        pnlBackground.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 150, -1, -1));

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/grafico-60.png"))); // NOI18N
        jLabel12.setMaximumSize(new java.awt.Dimension(70, 60));
        jLabel12.setMinimumSize(new java.awt.Dimension(70, 60));
        jLabel12.setPreferredSize(new java.awt.Dimension(70, 60));
        jLabel12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel12MouseClicked(evt);
            }
        });
        pnlBackground.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 90, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlBackground, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlBackground, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void ico3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ico3MouseClicked
        AlPrestamo pres = new AlPrestamo();
        pres.setVisible(true);
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_ico3MouseClicked

    private void ico4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ico4MouseClicked
        ADevolucion dev = new ADevolucion();
        dev.setVisible(true);
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_ico4MouseClicked

    private void ico5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ico5MouseClicked
        try {
            Profesor profe = new Profesor();
            profe.setVisible(true);
            this.setVisible(false);
            this.dispose();
        } catch (SQLException ex) {
            Logger.getLogger(AlPrestamo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_ico5MouseClicked

    private void ico6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ico6MouseClicked
        try {
            Departamento depart = new Departamento();
            depart.setVisible(true);
            this.setVisible(false);
            this.dispose();
        } catch (SQLException ex) {
            Logger.getLogger(AlPrestamo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_ico6MouseClicked

    private void ico7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ico7MouseClicked
        try {
            Aula aula = new Aula();
            aula.setVisible(true);
            this.setVisible(false);
            this.dispose();
        } catch (SQLException ex) {
            Logger.getLogger(ARegistro.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_ico7MouseClicked

    private void ico8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ico8MouseClicked
        try {
            Videoproyector vid = new Videoproyector();
            vid.setVisible(true);
            this.setVisible(false);
            this.dispose();
        } catch (SQLException ex) {
            Logger.getLogger(ARegistro.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_ico8MouseClicked

    private void btnRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegresarActionPerformed
        this.setVisible(false);
        this.dispose();
        Menu menu = new Menu();
        menu.setVisible(true);
    }//GEN-LAST:event_btnRegresarActionPerformed

    private void btnMenuItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_btnMenuItemStateChanged
        if (btnMenu.isSelected()) {
            panelOPC.setVisible(true);
        } else {
            panelOPC.setVisible(false);
        }
    }//GEN-LAST:event_btnMenuItemStateChanged

    private void btnFiltrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFiltrarActionPerformed
        try {
            int i = jComboBox1.getSelectedIndex();
            System.out.println("opcion: " + i);
            if(i != 5){
                getTable(i, "", "");
            }else{
                 if(jdtChooser2.getDate() != null && jdtChooser1.getDate() != null){
                    String fch1 = fecha.format(jdtChooser1.getDate());
                    String fch2 = fecha.format(jdtChooser2.getDate());
                    if(jdtChooser2.getDate().after(jdtChooser1.getDate())){  //comprobar que la fecha1 no es menor a la fecha2
                        System.out.println("fecha 1 es menor que fecha 2");
                        if(!fch1.equals(fch2)){
                            System.out.println("datos : "+fch1 + " : "+fch2);
                            getTable(i, fch1, fch2);
                        }else{ JOptionPane.showMessageDialog(this, "Ambas fechas no pueden ser la misma", "Advertencia", JOptionPane.WARNING_MESSAGE); }
                    }else{ JOptionPane.showMessageDialog(this, "La fecha fin no puede ser menos actual a la fecha inicio", "Advertencia", JOptionPane.WARNING_MESSAGE); }
                }else{ JOptionPane.showMessageDialog(this, "No olvide indicar fecha de inicio y fin", "Advertencia", JOptionPane.WARNING_MESSAGE); }
                 
            }
        } catch (SQLException ex) {
            Logger.getLogger(ARegistro.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnFiltrarActionPerformed

    private void jComboBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox1ItemStateChanged
        if (jComboBox1.getSelectedIndex() == 5){
            jdtChooser2.setVisible(true);
            jdtChooser1.setVisible(true);
            lblIni.setVisible(true);
            lblFn.setVisible(true);
        }else{
            jdtChooser2.setVisible(false);
            jdtChooser1.setVisible(false);
            lblIni.setVisible(false);
            lblFn.setVisible(false);
            jdtChooser2.setCalendar(null);
            jdtChooser1.setCalendar(null);
        }
    }//GEN-LAST:event_jComboBox1ItemStateChanged

    private void btnCerrarRRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarRRActionPerformed
        dlgReporte.setVisible(false);
        jDateChooser1.setCalendar(null);
        jDateChooser2.setCalendar(null);
        dlgReporte.dispose();
    }//GEN-LAST:event_btnCerrarRRActionPerformed

    private void rb6ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_rb6ItemStateChanged
        if(rb6.isSelected()){
            pnlFechasFiltro.setVisible(true);
        }else{
            pnlFechasFiltro.setVisible(false);
        }
    }//GEN-LAST:event_rb6ItemStateChanged

    private void btnGenerarRRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerarRRActionPerformed
        String[] fch;
        int opc = 1;
        if(rb6.isSelected()){
            fch = rbFechaEsp();
        }else{
            if(rb1.isSelected()){ opc = 1;}
            else if(rb2.isSelected()){ opc = 2;}
            else if(rb3.isSelected()){ opc = 3;}
            else if(rb4.isSelected()){ opc = 4;}
            else if(rb5.isSelected()){ opc = 5;}
            fch = rbFechasOpc(opc);
        }    
        if(fch.length > 0){
            GenerarReportes gr = new GenerarReportes();
            if(dRb5.isSelected()){
                gr.getRRTods(fch,1);
                gr.getRRTods(fch,2);
                gr.getRRTods(fch,3);
            }else if(dRb3.isSelected()){ //videoproyector
                gr.getRRTods(fch,1);
            }else if(dRb2.isSelected()){ //profesores
                gr.getRRTods(fch,2);
            }else if(dRb1.isSelected()){ //departamentos
                gr.getRRTods(fch,3);
            }
        }
        
        dlgReporte.setVisible(false);
        dlgReporte.dispose();
    }//GEN-LAST:event_btnGenerarRRActionPerformed

    private void cbEspecificoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbEspecificoItemStateChanged
        int indice = cbEspecifico.getSelectedIndex();
        if(indice > 0){
            txtBuscarTXT.setVisible(true);
        }else{
            txtBuscarTXT.setVisible(false);
            txtBuscarTXT.setText("");
        }
    }//GEN-LAST:event_cbEspecificoItemStateChanged

    private void jLabel12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel12MouseClicked
        //inicializar
        pnlFechasFiltro.setVisible(false);
        rb1.setSelected(true);
        dRb5.setSelected(true);
        
        //mostrar dialog
        dlgReporte.setLocationRelativeTo(pnlBackground);
        dlgReporte.setVisible(true);
    }//GEN-LAST:event_jLabel12MouseClicked
    
    public String[] rbFechasOpc(int opc){
        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        String[] fechas = new String[2];
        String date1 = "", date2 = "";
        int year = c.get(Calendar.YEAR);
        switch(opc){
            case 1: //hoy
                date1 = dt.format(c.getTime());
                date2 = date1;
            break;
            case 2: //semana
                c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                Date d1Semana = c.getTime();
                c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
                Date d2Semana = c.getTime();
                date1 = dt.format(d1Semana);
                date2 = dt.format(d2Semana);
            break;
            case 3: //mes
                c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
                Date d1Mes = c.getTime();
                date1 = dt.format(d1Mes);
                c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
                Date d2Mes = c.getTime();
                date2 = dt.format(d2Mes);
            break;
            case 4: //semestre
                if (c.get(Calendar.MONTH) < 6) { //si actual mes es inferior a julio
                    c.set(Calendar.MONTH, 05);
                    date1 = year + "-01-01";
                    date2 = year + "-06-" + c.getActualMaximum(Calendar.DAY_OF_MONTH);
                } else {
                    c.set(Calendar.MONTH, 11);
                    date1 = year + "-07-01";
                    date2 = year + "-12-" + c.getActualMaximum(Calendar.DAY_OF_MONTH);
                }
            break;
            case 5: //año
                c.set(Calendar.MONTH, 11);
                date1 = year + "-01-01";
                date2 = year + "-12-" + c.getActualMaximum(Calendar.DAY_OF_MONTH);
            break;
        }
        
        fechas[0] = date1.concat("T00:00:00Z");
        fechas[1] = date2.concat("T23:59:59Z");
        System.out.println("fechas: " + Arrays.toString(fechas));
        return fechas;
    }
    
    public String[] rbFechaEsp(){
        DateFormat dateJC = new SimpleDateFormat("yyyy-MM-dd");//DateFormat.getDateInstance();
        String[] fechas = new String[2];
        if(jDateChooser2.getDate() != null && jDateChooser1.getDate() != null){
            String dateJC1 = dateJC.format(jDateChooser1.getDate());
            String dateJC2 = dateJC.format(jDateChooser2.getDate());
            if(jDateChooser2.getDate().after(jDateChooser1.getDate())){  //comprobar que la fecha1 no es menor a la fecha2
                System.out.println("fecha 1 es menor que fecha 2");
                if(!dateJC1.equals(dateJC2)){
                    fechas[0] = dateJC1.concat("T00:00:00Z");
                    fechas[1] = dateJC2.concat("T23:59:59Z");
                    System.out.println("datos : "+Arrays.toString(fechas));
                }else{ JOptionPane.showMessageDialog(this, "Ambas fechas no pueden ser la misma", "Advertencia", JOptionPane.WARNING_MESSAGE); btnCerrarRR.doClick();}
            }else{ JOptionPane.showMessageDialog(this, "La fecha fin no puede ser menos actual a la fecha inicio", "Advertencia", JOptionPane.WARNING_MESSAGE); btnCerrarRR.doClick();}
        }else{ JOptionPane.showMessageDialog(this, "No olvide indicar fecha de inicio y fin", "Advertencia", JOptionPane.WARNING_MESSAGE); btnCerrarRR.doClick();}
        System.out.println("Arrays fechas de rb6: " + Arrays.toString(fechas));
        return fechas;
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ARegistro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new ARegistro().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCerrarRR;
    private javax.swing.JButton btnFiltrar;
    private javax.swing.JButton btnGenerarRR;
    private javax.swing.ButtonGroup btnGroupDatos;
    private javax.swing.ButtonGroup btnGroupFecha;
    private javax.swing.JToggleButton btnMenu;
    private javax.swing.JButton btnRegresar;
    private javax.swing.JComboBox<String> cbEspecifico;
    private javax.swing.JRadioButton dRb1;
    private javax.swing.JRadioButton dRb2;
    private javax.swing.JRadioButton dRb3;
    private javax.swing.JRadioButton dRb4;
    private javax.swing.JRadioButton dRb5;
    private javax.swing.JDialog dlgReporte;
    private javax.swing.JLabel ico3;
    private javax.swing.JLabel ico4;
    private javax.swing.JLabel ico5;
    private javax.swing.JLabel ico6;
    private javax.swing.JLabel ico7;
    private javax.swing.JLabel ico8;
    private javax.swing.JComboBox<String> jComboBox1;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jMiLabel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private com.toedter.calendar.JDateChooser jdtChooser1;
    private com.toedter.calendar.JDateChooser jdtChooser2;
    private javax.swing.JLabel labelFecha;
    private javax.swing.JLabel labelHora;
    private javax.swing.JLabel lblFn;
    private javax.swing.JLabel lblIco1;
    private javax.swing.JLabel lblIco2;
    private javax.swing.JLabel lblIni;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblTituloDlg;
    private javax.swing.JPanel panelOPC;
    private javax.swing.JPanel pblBkReporte;
    private javax.swing.JPanel pnlBackground;
    private javax.swing.JPanel pnlBotones;
    private javax.swing.JPanel pnlCabecera;
    private javax.swing.JPanel pnlDatos;
    private javax.swing.JPanel pnlFechas;
    private javax.swing.JPanel pnlFechasFiltro;
    private javax.swing.JRadioButton rb1;
    private javax.swing.JRadioButton rb2;
    private javax.swing.JRadioButton rb3;
    private javax.swing.JRadioButton rb4;
    private javax.swing.JRadioButton rb5;
    private javax.swing.JRadioButton rb6;
    private javax.swing.JTextField txtBuscarTXT;
    // End of variables declaration//GEN-END:variables
}
