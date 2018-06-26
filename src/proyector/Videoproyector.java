/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyector;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import org.mindrot.jbcrypt.BCrypt;
import proyector.dataBase.crud.AccesorioDB;
import proyector.dataBase.crud.UsuarioReadDB;
import proyector.dataBase.crud.PrestamoDB;
import proyector.dataBase.crud.VideoproyectorDB;
import proyector.reportes.GenerarReportes;

/**
 *
 * @author JuanGSot
 */
public final class Videoproyector extends javax.swing.JFrame {

    String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
    ImageIcon img = new ImageIcon("./src/imagenes/logo-adm.png");
    ImageIcon add = new ImageIcon("./src/imagenes/Add New_36px.png");
    ImageIcon addW = new ImageIcon("./src/imagenes/Add New_36pxW.png");

    ImageIcon art = new ImageIcon("./src/imagenes/Electrical_36px.png");
    ImageIcon artW = new ImageIcon("./src/imagenes/Electrical_36pxW.png");
    private static Boolean valido = false;
    
    /**
     * Creates new form Videoproyector
     * @throws java.sql.SQLException
     */
    public Videoproyector() throws SQLException {
        initComponents();

        panelOPC.setVisible(false);

        dibujarProye();//enlista los videoproyectores
        jScrollPane1.getVerticalScrollBar().setUnitIncrement(16);
        //LABELS CON INFO OCULTA
        hiddenNSerie.setVisible(false);
        btnMant.setVisible(false);
        hidenData.setVisible(false);
        hidenData2.setVisible(false);

        //block ctrl c v
        txtDetalles.setTransferHandler(null);
        txtTitulo.setTransferHandler(null);
        
        txtNom1.setTransferHandler(null);
        txtMarc1.setTransferHandler(null);
        txtMod1.setTransferHandler(null);
        txtSer1.setTransferHandler(null);
        
        txtNom2.setTransferHandler(null);
        txtMarc2.setTransferHandler(null);
        txtMod2.setTransferHandler(null);
        txtSer2.setTransferHandler(null);
        
        txtUsuario.setTransferHandler(null);
        txtPass.setTransferHandler(null);
        
        jTabbedPane1.setEnabledAt(3, false);
        jTabbedPane1.setEnabledAt(4, false);
        hiddenLabelArt.setVisible(false);
        tabbedPane.setEnabledAt(1, false);
        labelFecha.setText(date);     
        jPanel4.setVisible(true);
        jPanel3.setVisible(false);
        //coloca la fecha
        Timer timer = new Timer(500, (ActionEvent e) -> {
            reloj();                                //coloca la hora
        });
        timer.setRepeats(true);
        timer.setCoalesce(true);
        timer.setInitialDelay(0);
        timer.start();
    }

    /**
     * Introduce la informacion en el jTable con los accesorios
     *
     * @throws SQLException
     */
    public void getTable() throws SQLException {
        DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
        AccesorioDB acc = new AccesorioDB();

        String[] cols = {jTable2.getColumnName(0),jTable2.getColumnName(1), jTable2.getColumnName(2), jTable2.getColumnName(3)};
        int count = acc.getCantAccesorios(true);
        System.out.println("\nAccesorios existentes: " + count);

        String[][] datos = acc.getAccesorios(false);
        String art[][] = new String[count][4];
        
        for(int i = 0; i < count ;i++){
            for (int j = 0; j < 4; j++) {
                art[i][j] = datos[i][j];
                if(j == 3) { art[i][j] = String.valueOf(Integer.parseInt(datos[i][2]) - Integer.parseInt(datos[i][3])); }
            }
        }
//        jTable2.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        model.setDataVector(art, cols);
    }

    /**
     * Introduce la informacion en el jTable con los accesoriosPrestados
     *
     * @throws SQLException
     */
    public void getRegistrosPrestamoAcc() throws SQLException {
        DefaultTableModel modelReg = (DefaultTableModel) jTable3.getModel();
        AccesorioDB acc = new AccesorioDB();
        String[] cols = {jTable3.getColumnName(0),jTable3.getColumnName(1), jTable3.getColumnName(2), jTable3.getColumnName(3),jTable3.getColumnName(4)};
        
        String[][] datos = acc.getRegAccesorios(false);
        int count = datos.length;
        System.out.println("\nRegistros accesorios existentes: " + count);
//        for(String[] data : datos){
//            System.out.println("Arrays: " + Arrays.toString(data));
//        }
        
        jTable3.getColumnModel().getColumn(0).setPreferredWidth(20);
        jTable3.getColumnModel().getColumn(1).setPreferredWidth(195);
        jTable3.getColumnModel().getColumn(2).setPreferredWidth(135);
        jTable3.getColumnModel().getColumn(3).setPreferredWidth(20);
        jTable3.getColumnModel().getColumn(4).setPreferredWidth(70);
        jTable3.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        modelReg.setDataVector(datos, cols);
    }
    
    public void getRegistrosPrestamoAccHOY() throws SQLException {
        DefaultTableModel model5 = (DefaultTableModel) jTable5.getModel();
        AccesorioDB acc = new AccesorioDB();
        String[] cols = {jTable5.getColumnName(0),jTable5.getColumnName(1), jTable5.getColumnName(2), jTable5.getColumnName(3),jTable5.getColumnName(4)};
        
        String[][] datos = acc.getRegAccesorios(true);
        int count = datos.length;
        System.out.println("\nRegistros accesorios existentes: " + count);
//        for(String[] data : datos){
//            System.out.println("Arrays: " + Arrays.toString(data));
//        }
        
        jTable3.getColumnModel().getColumn(0).setPreferredWidth(20);
        jTable3.getColumnModel().getColumn(1).setPreferredWidth(195);
        jTable3.getColumnModel().getColumn(2).setPreferredWidth(135);
        jTable3.getColumnModel().getColumn(3).setPreferredWidth(20);
        jTable3.getColumnModel().getColumn(4).setPreferredWidth(70);
        jTable3.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        model5.setDataVector(datos, cols);
    }
    
    public void toResumenServicio() throws SQLException{
        DefaultTableModel model4 = (DefaultTableModel) jTable4.getModel();
        VideoproyectorDB vd = new VideoproyectorDB();
        String[] cols = {jTable4.getColumnName(0),jTable4.getColumnName(1), jTable4.getColumnName(2), jTable4.getColumnName(3)};
        
        String[][] datos = vd.hrsServicio();
        int count = datos.length;
        System.out.println("\nCantidad de videoproyectores con Horas de servicio registradas: " + count);
        for(String[] data : datos){
            System.out.println("Arrays: " + Arrays.toString(data));
        }
        model4.setDataVector(datos, cols);
    }
    /**
     * Se encarga de colocar la hora con un formato especifico
     */
    public void reloj() {
        labelHora.setText(new SimpleDateFormat("hh:mm:ss a").format(new Date()));
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

        crearVid = new javax.swing.JDialog();
        pnlBackground1 = new javax.swing.JPanel();
        lblTitulo1 = new javax.swing.JLabel();
        pnlColor1 = new javax.swing.JPanel();
        pnlColor2 = new javax.swing.JPanel();
        pnlFormulario = new javax.swing.JPanel();
        txtNom1 = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtMarc1 = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtMod1 = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtSer1 = new javax.swing.JTextField();
        barraSeparadora3 = new javax.swing.JPanel();
        barraSeparadora2 = new javax.swing.JPanel();
        barraSeparadora1 = new javax.swing.JPanel();
        barraSeparadora = new javax.swing.JPanel();
        btnGuardar1 = new javax.swing.JButton();
        btnCerrar1 = new javax.swing.JButton();
        descVid = new javax.swing.JDialog();
        tabbedPane = new javax.swing.JTabbedPane();
        pnlDetalles = new javax.swing.JPanel();
        lblTitulo2 = new javax.swing.JLabel();
        hidenData = new javax.swing.JLabel();
        pnlColor5 = new javax.swing.JPanel();
        pnlColor6 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        lblServTotal = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        lblServMes = new javax.swing.JLabel();
        lblServSem = new javax.swing.JLabel();
        pnlColor7 = new javax.swing.JPanel();
        pnlDetalles1 = new javax.swing.JPanel();
        lblNomHead = new javax.swing.JLabel();
        lblCreadHead = new javax.swing.JLabel();
        lblSerieHead = new javax.swing.JLabel();
        lblData1 = new javax.swing.JLabel();
        lblData2 = new javax.swing.JLabel();
        lblData4 = new javax.swing.JLabel();
        btnCerrar3 = new javax.swing.JButton();
        btnActualizar1 = new javax.swing.JButton();
        btnEliminarVid1 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        lblStatus = new javax.swing.JLabel();
        btnMant = new javax.swing.JButton();
        pnlActualizar = new javax.swing.JPanel();
        lblTitulo3 = new javax.swing.JLabel();
        hidenData2 = new javax.swing.JLabel();
        pnlColor4 = new javax.swing.JPanel();
        pnlFormulario1 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        txtNom2 = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        txtMarc2 = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        txtMod2 = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        txtSer2 = new javax.swing.JTextField();
        btnActualizar2 = new javax.swing.JButton();
        btnCerrar2 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        fallosReporte = new javax.swing.JDialog();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel31 = new javax.swing.JLabel();
        lblIcoInfo = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        txtArea = new javax.swing.JTextField();
        txtSolicitante = new javax.swing.JTextField();
        jScrollPane9 = new javax.swing.JScrollPane();
        txtDetalles = new javax.swing.JTextArea();
        jLabel36 = new javax.swing.JLabel();
        cb1 = new javax.swing.JCheckBox();
        cb2 = new javax.swing.JCheckBox();
        cb3 = new javax.swing.JCheckBox();
        jLabel42 = new javax.swing.JLabel();
        txtTitulo = new javax.swing.JTextField();
        lblRprt6 = new javax.swing.JLabel();
        cb4 = new javax.swing.JCheckBox();
        cb5 = new javax.swing.JCheckBox();
        cb6 = new javax.swing.JCheckBox();
        cb7 = new javax.swing.JCheckBox();
        btnReporte = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        hiddenNSerie = new javax.swing.JLabel();
        articulos = new javax.swing.JDialog();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        pnlAllArt = new javax.swing.JPanel();
        lblTituloArt = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        pnlAddArt = new javax.swing.JPanel();
        lblTituloArt1 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtNom = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        txtExist = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtDesc = new javax.swing.JTextArea();
        jLabel17 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        pnlRegArt = new javax.swing.JPanel();
        jLayeredPane2 = new javax.swing.JLayeredPane();
        jPanel4 = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        jTable5 = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        lblTituloArt4 = new javax.swing.JLabel();
        tglReg = new javax.swing.JToggleButton();
        jLabel29 = new javax.swing.JLabel();
        pnlRptArt = new javax.swing.JPanel();
        lblTituloArt2 = new javax.swing.JLabel();
        pnlModArt = new javax.swing.JPanel();
        lblTituloArt3 = new javax.swing.JLabel();
        txtNom3 = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        txtExist1 = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        txtDesc1 = new javax.swing.JTextArea();
        hiddenLabelArt = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        dlgServicio = new javax.swing.JDialog();
        jPanel2 = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        btnGrSolicitudPara = new javax.swing.ButtonGroup();
        dlgConfirm = new javax.swing.JDialog();
        jPanel7 = new javax.swing.JPanel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        btnComprobar = new javax.swing.JButton();
        jLabel41 = new javax.swing.JLabel();
        txtUsuario = new javax.swing.JTextField();
        txtPass = new javax.swing.JPasswordField();
        btnGrImprevisto = new javax.swing.ButtonGroup();
        pnlBkVid = new javax.swing.JPanel();
        pnlCabecera = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        labelHora = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        labelFecha = new javax.swing.JLabel();
        lblPanelTitulo = new javax.swing.JLabel();
        btnRegresar = new javax.swing.JButton();
        btnMenu = new javax.swing.JToggleButton();
        panelOPC = new javax.swing.JPanel();
        ico3 = new javax.swing.JLabel();
        ico4 = new javax.swing.JLabel();
        ico5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        btnPnlCrear = new javax.swing.JPanel();
        lblIco0 = new javax.swing.JLabel();
        lblIco1 = new javax.swing.JLabel();
        lblIco2 = new javax.swing.JLabel();
        btnPnlReportar = new javax.swing.JPanel();
        lblIco3 = new javax.swing.JLabel();
        lblIco4 = new javax.swing.JLabel();
        lblIco5 = new javax.swing.JLabel();
        btnPnlArticulos = new javax.swing.JPanel();
        lblIco6 = new javax.swing.JLabel();
        lblIco7 = new javax.swing.JLabel();
        lblIco8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        pnlContenedor = new javax.swing.JPanel();
        btnPnlGaf = new javax.swing.JPanel();
        lblIco9 = new javax.swing.JLabel();
        lblIco10 = new javax.swing.JLabel();
        lblIco11 = new javax.swing.JLabel();
        btnPnlCodebar = new javax.swing.JPanel();
        lblIco12 = new javax.swing.JLabel();
        lblIco13 = new javax.swing.JLabel();
        lblIco14 = new javax.swing.JLabel();

        crearVid.setMinimumSize(new java.awt.Dimension(630, 310));
        crearVid.setModal(true);
        crearVid.setUndecorated(true);
        crearVid.setResizable(false);
        crearVid.setSize(new java.awt.Dimension(630, 322));

        pnlBackground1.setBackground(new java.awt.Color(255, 255, 255));
        pnlBackground1.setMinimumSize(new java.awt.Dimension(630, 322));
        pnlBackground1.setPreferredSize(new java.awt.Dimension(630, 322));

        lblTitulo1.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        lblTitulo1.setText("Nuevo Video Proyector");

        pnlColor1.setBackground(new java.awt.Color(1, 200, 1));

        javax.swing.GroupLayout pnlColor1Layout = new javax.swing.GroupLayout(pnlColor1);
        pnlColor1.setLayout(pnlColor1Layout);
        pnlColor1Layout.setHorizontalGroup(
            pnlColor1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );
        pnlColor1Layout.setVerticalGroup(
            pnlColor1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        pnlColor2.setBackground(new java.awt.Color(12, 193, 243));

        javax.swing.GroupLayout pnlColor2Layout = new javax.swing.GroupLayout(pnlColor2);
        pnlColor2.setLayout(pnlColor2Layout);
        pnlColor2Layout.setHorizontalGroup(
            pnlColor2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        pnlColor2Layout.setVerticalGroup(
            pnlColor2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        pnlFormulario.setOpaque(false);
        pnlFormulario.setLayout(new java.awt.GridBagLayout());

        txtNom1.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtNom1.setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 5, 1, 5));
        txtNom1.setMinimumSize(new java.awt.Dimension(215, 30));
        txtNom1.setPreferredSize(new java.awt.Dimension(215, 30));
        txtNom1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNom1KeyTyped(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 20, 0, 0);
        pnlFormulario.add(txtNom1, gridBagConstraints);

        jLabel11.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel11.setText("Nombre");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        pnlFormulario.add(jLabel11, gridBagConstraints);

        jLabel12.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel12.setText("Marca");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        pnlFormulario.add(jLabel12, gridBagConstraints);

        txtMarc1.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtMarc1.setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 5, 1, 5));
        txtMarc1.setMinimumSize(new java.awt.Dimension(215, 30));
        txtMarc1.setPreferredSize(new java.awt.Dimension(215, 30));
        txtMarc1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtMarc1KeyTyped(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 20, 0, 0);
        pnlFormulario.add(txtMarc1, gridBagConstraints);

        jLabel13.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel13.setText("Modelo");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        pnlFormulario.add(jLabel13, gridBagConstraints);

        txtMod1.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtMod1.setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 5, 1, 5));
        txtMod1.setMinimumSize(new java.awt.Dimension(215, 30));
        txtMod1.setPreferredSize(new java.awt.Dimension(215, 30));
        txtMod1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtMod1KeyTyped(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 20, 0, 0);
        pnlFormulario.add(txtMod1, gridBagConstraints);

        jLabel14.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel14.setText("No. de Serie");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        pnlFormulario.add(jLabel14, gridBagConstraints);

        txtSer1.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtSer1.setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 5, 1, 5));
        txtSer1.setMinimumSize(new java.awt.Dimension(215, 30));
        txtSer1.setPreferredSize(new java.awt.Dimension(215, 30));
        txtSer1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtSer1KeyTyped(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 20, 0, 0);
        pnlFormulario.add(txtSer1, gridBagConstraints);

        barraSeparadora3.setBackground(new java.awt.Color(0, 0, 0));
        barraSeparadora3.setMinimumSize(new java.awt.Dimension(215, 2));

        javax.swing.GroupLayout barraSeparadora3Layout = new javax.swing.GroupLayout(barraSeparadora3);
        barraSeparadora3.setLayout(barraSeparadora3Layout);
        barraSeparadora3Layout.setHorizontalGroup(
            barraSeparadora3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 215, Short.MAX_VALUE)
        );
        barraSeparadora3Layout.setVerticalGroup(
            barraSeparadora3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 12, 0);
        pnlFormulario.add(barraSeparadora3, gridBagConstraints);

        barraSeparadora2.setBackground(new java.awt.Color(0, 0, 0));
        barraSeparadora2.setMinimumSize(new java.awt.Dimension(215, 2));

        javax.swing.GroupLayout barraSeparadora2Layout = new javax.swing.GroupLayout(barraSeparadora2);
        barraSeparadora2.setLayout(barraSeparadora2Layout);
        barraSeparadora2Layout.setHorizontalGroup(
            barraSeparadora2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 215, Short.MAX_VALUE)
        );
        barraSeparadora2Layout.setVerticalGroup(
            barraSeparadora2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 12, 0);
        pnlFormulario.add(barraSeparadora2, gridBagConstraints);

        barraSeparadora1.setBackground(new java.awt.Color(0, 0, 0));
        barraSeparadora1.setMinimumSize(new java.awt.Dimension(215, 2));

        javax.swing.GroupLayout barraSeparadora1Layout = new javax.swing.GroupLayout(barraSeparadora1);
        barraSeparadora1.setLayout(barraSeparadora1Layout);
        barraSeparadora1Layout.setHorizontalGroup(
            barraSeparadora1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 215, Short.MAX_VALUE)
        );
        barraSeparadora1Layout.setVerticalGroup(
            barraSeparadora1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 12, 0);
        pnlFormulario.add(barraSeparadora1, gridBagConstraints);

        barraSeparadora.setBackground(new java.awt.Color(0, 0, 0));
        barraSeparadora.setMinimumSize(new java.awt.Dimension(215, 2));
        barraSeparadora.setPreferredSize(new java.awt.Dimension(215, 2));

        javax.swing.GroupLayout barraSeparadoraLayout = new javax.swing.GroupLayout(barraSeparadora);
        barraSeparadora.setLayout(barraSeparadoraLayout);
        barraSeparadoraLayout.setHorizontalGroup(
            barraSeparadoraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 215, Short.MAX_VALUE)
        );
        barraSeparadoraLayout.setVerticalGroup(
            barraSeparadoraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 12, 0);
        pnlFormulario.add(barraSeparadora, gridBagConstraints);

        btnGuardar1.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        btnGuardar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Save_22px.png"))); // NOI18N
        btnGuardar1.setText("Guardar");
        btnGuardar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardar1ActionPerformed(evt);
            }
        });

        btnCerrar1.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        btnCerrar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Close_22px.png"))); // NOI18N
        btnCerrar1.setText("Cerrar");
        btnCerrar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrar1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlBackground1Layout = new javax.swing.GroupLayout(pnlBackground1);
        pnlBackground1.setLayout(pnlBackground1Layout);
        pnlBackground1Layout.setHorizontalGroup(
            pnlBackground1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBackground1Layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addComponent(pnlColor1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(pnlColor2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(pnlBackground1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlBackground1Layout.createSequentialGroup()
                        .addGroup(pnlBackground1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlBackground1Layout.createSequentialGroup()
                                .addGap(150, 150, 150)
                                .addComponent(lblTitulo1))
                            .addGroup(pnlBackground1Layout.createSequentialGroup()
                                .addGap(67, 67, 67)
                                .addComponent(pnlFormulario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(161, Short.MAX_VALUE))
                    .addGroup(pnlBackground1Layout.createSequentialGroup()
                        .addGap(57, 57, 57)
                        .addComponent(btnGuardar1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCerrar1)
                        .addGap(122, 122, 122))))
        );
        pnlBackground1Layout.setVerticalGroup(
            pnlBackground1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlColor2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnlBackground1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(lblTitulo1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlFormulario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlBackground1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnGuardar1)
                    .addComponent(btnCerrar1))
                .addGap(0, 10, Short.MAX_VALUE))
            .addComponent(pnlColor1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout crearVidLayout = new javax.swing.GroupLayout(crearVid.getContentPane());
        crearVid.getContentPane().setLayout(crearVidLayout);
        crearVidLayout.setHorizontalGroup(
            crearVidLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(crearVidLayout.createSequentialGroup()
                .addComponent(pnlBackground1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        crearVidLayout.setVerticalGroup(
            crearVidLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlBackground1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        crearVid.getAccessibleContext().setAccessibleParent(this);

        descVid.setMinimumSize(new java.awt.Dimension(705, 420));
        descVid.setModal(true);
        descVid.setPreferredSize(new java.awt.Dimension(705, 420));

        tabbedPane.setBackground(new java.awt.Color(255, 255, 255));
        tabbedPane.setMinimumSize(new java.awt.Dimension(720, 378));
        tabbedPane.setPreferredSize(new java.awt.Dimension(720, 378));

        pnlDetalles.setBackground(new java.awt.Color(12, 193, 243));
        pnlDetalles.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(12, 193, 243), 1, true));
        pnlDetalles.setMinimumSize(new java.awt.Dimension(700, 350));
        pnlDetalles.setPreferredSize(new java.awt.Dimension(700, 350));
        pnlDetalles.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTitulo2.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        lblTitulo2.setText("Descripción Video Proyector");
        pnlDetalles.add(lblTitulo2, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 30, -1, -1));

        hidenData.setFont(new java.awt.Font("SansSerif", 0, 8)); // NOI18N
        hidenData.setText("Soy yo");
        pnlDetalles.add(hidenData, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 260, -1, -1));

        pnlColor5.setBackground(new java.awt.Color(0, 162, 228));

        javax.swing.GroupLayout pnlColor5Layout = new javax.swing.GroupLayout(pnlColor5);
        pnlColor5.setLayout(pnlColor5Layout);
        pnlColor5Layout.setHorizontalGroup(
            pnlColor5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 52, Short.MAX_VALUE)
        );
        pnlColor5Layout.setVerticalGroup(
            pnlColor5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        pnlDetalles.add(pnlColor5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 400));

        pnlColor6.setBackground(new java.awt.Color(255, 180, 4));
        pnlColor6.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.black, java.awt.Color.darkGray), "Horas de Servicio", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("SansSerif", 3, 12), new java.awt.Color(255, 255, 255))); // NOI18N
        pnlColor6.setForeground(new java.awt.Color(255, 255, 255));
        pnlColor6.setMaximumSize(new java.awt.Dimension(225, 185));
        pnlColor6.setMinimumSize(new java.awt.Dimension(215, 185));
        pnlColor6.setLayout(new java.awt.GridBagLayout());

        jLabel10.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("TOTAL:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(29, 16, 0, 0);
        pnlColor6.add(jLabel10, gridBagConstraints);

        lblServTotal.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblServTotal.setForeground(new java.awt.Color(255, 255, 255));
        lblServTotal.setText("datos no encontrados...");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 28, 0, 72);
        pnlColor6.add(lblServTotal, gridBagConstraints);

        jLabel25.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 255, 255));
        jLabel25.setText("Este Mes:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(11, 16, 0, 0);
        pnlColor6.add(jLabel25, gridBagConstraints);

        jLabel26.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 255, 255));
        jLabel26.setText("Este Semestre:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(11, 16, 0, 0);
        pnlColor6.add(jLabel26, gridBagConstraints);

        lblServMes.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblServMes.setForeground(new java.awt.Color(255, 255, 255));
        lblServMes.setText("datos no encontrados...");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 26, 0, 72);
        pnlColor6.add(lblServMes, gridBagConstraints);

        lblServSem.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblServSem.setForeground(new java.awt.Color(255, 255, 255));
        lblServSem.setText("datos no encontrados...");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 26, 25, 72);
        pnlColor6.add(lblServSem, gridBagConstraints);

        pnlDetalles.add(pnlColor6, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 70, 215, 185));

        pnlColor7.setBackground(new java.awt.Color(255, 209, 102));
        pnlColor7.setMinimumSize(new java.awt.Dimension(33, 33));
        pnlColor7.setPreferredSize(new java.awt.Dimension(33, 33));

        javax.swing.GroupLayout pnlColor7Layout = new javax.swing.GroupLayout(pnlColor7);
        pnlColor7.setLayout(pnlColor7Layout);
        pnlColor7Layout.setHorizontalGroup(
            pnlColor7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 33, Short.MAX_VALUE)
        );
        pnlColor7Layout.setVerticalGroup(
            pnlColor7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 33, Short.MAX_VALUE)
        );

        pnlDetalles.add(pnlColor7, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 320, -1, -1));

        pnlDetalles1.setBackground(new java.awt.Color(0, 162, 228));
        pnlDetalles1.setLayout(new java.awt.GridBagLayout());

        lblNomHead.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        lblNomHead.setForeground(new java.awt.Color(255, 255, 255));
        lblNomHead.setText("Nombre:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 0);
        pnlDetalles1.add(lblNomHead, gridBagConstraints);

        lblCreadHead.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        lblCreadHead.setForeground(new java.awt.Color(255, 255, 255));
        lblCreadHead.setText("Registrado desde:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 0);
        pnlDetalles1.add(lblCreadHead, gridBagConstraints);

        lblSerieHead.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        lblSerieHead.setForeground(new java.awt.Color(255, 255, 255));
        lblSerieHead.setText("No. de Serie");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 0);
        pnlDetalles1.add(lblSerieHead, gridBagConstraints);

        lblData1.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblData1.setForeground(new java.awt.Color(255, 255, 255));
        lblData1.setText("jLabel18");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 15, 10, 0);
        pnlDetalles1.add(lblData1, gridBagConstraints);

        lblData2.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblData2.setForeground(new java.awt.Color(255, 255, 255));
        lblData2.setText("jLabel19");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 15, 10, 0);
        pnlDetalles1.add(lblData2, gridBagConstraints);

        lblData4.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblData4.setForeground(new java.awt.Color(255, 255, 255));
        lblData4.setText("jLabel10");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 15, 10, 0);
        pnlDetalles1.add(lblData4, gridBagConstraints);

        pnlDetalles.add(pnlDetalles1, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 70, 360, 150));

        btnCerrar3.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        btnCerrar3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Close_22px.png"))); // NOI18N
        btnCerrar3.setText("Cerrar");
        btnCerrar3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrar3ActionPerformed(evt);
            }
        });
        pnlDetalles.add(btnCerrar3, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 270, -1, -1));

        btnActualizar1.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        btnActualizar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Pencil_22px.png"))); // NOI18N
        btnActualizar1.setText("Actualizar");
        btnActualizar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizar1ActionPerformed(evt);
            }
        });
        pnlDetalles.add(btnActualizar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 270, -1, -1));

        btnEliminarVid1.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        btnEliminarVid1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Waste_24px.png"))); // NOI18N
        btnEliminarVid1.setText("Eliminar");
        btnEliminarVid1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarVid1ActionPerformed(evt);
            }
        });
        pnlDetalles.add(btnEliminarVid1, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 270, -1, -1));

        jLabel6.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("ESTATUS:");
        pnlDetalles.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 240, -1, -1));

        lblStatus.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        lblStatus.setForeground(new java.awt.Color(255, 255, 255));
        lblStatus.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        pnlDetalles.add(lblStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 240, 250, 20));

        btnMant.setText("Retirar del Mantenimiento");
        btnMant.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMantActionPerformed(evt);
            }
        });
        pnlDetalles.add(btnMant, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 320, -1, -1));

        tabbedPane.addTab("Detalles", pnlDetalles);

        pnlActualizar.setBackground(new java.awt.Color(12, 193, 243));
        pnlActualizar.setMinimumSize(new java.awt.Dimension(700, 350));
        pnlActualizar.setPreferredSize(new java.awt.Dimension(700, 350));
        pnlActualizar.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTitulo3.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        lblTitulo3.setText("Actualizar información de Videoproyector");
        pnlActualizar.add(lblTitulo3, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 30, -1, -1));

        hidenData2.setFont(new java.awt.Font("SansSerif", 0, 8)); // NOI18N
        hidenData2.setText("Soy yo");
        pnlActualizar.add(hidenData2, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 230, 40, -1));

        pnlColor4.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout pnlColor4Layout = new javax.swing.GroupLayout(pnlColor4);
        pnlColor4.setLayout(pnlColor4Layout);
        pnlColor4Layout.setHorizontalGroup(
            pnlColor4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 33, Short.MAX_VALUE)
        );
        pnlColor4Layout.setVerticalGroup(
            pnlColor4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        pnlActualizar.add(pnlColor4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, 380));

        pnlFormulario1.setOpaque(false);
        pnlFormulario1.setLayout(new java.awt.GridBagLayout());

        jLabel18.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel18.setText("Nombre");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        pnlFormulario1.add(jLabel18, gridBagConstraints);

        txtNom2.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtNom2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(171, 173, 179)));
        txtNom2.setMinimumSize(new java.awt.Dimension(215, 30));
        txtNom2.setPreferredSize(new java.awt.Dimension(215, 30));
        txtNom2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNom2KeyTyped(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 10, 6, 0);
        pnlFormulario1.add(txtNom2, gridBagConstraints);

        jLabel19.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel19.setText("Marca");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        pnlFormulario1.add(jLabel19, gridBagConstraints);

        txtMarc2.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtMarc2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(171, 173, 179)));
        txtMarc2.setMinimumSize(new java.awt.Dimension(215, 30));
        txtMarc2.setPreferredSize(new java.awt.Dimension(215, 30));
        txtMarc2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtMarc2KeyTyped(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 10, 6, 0);
        pnlFormulario1.add(txtMarc2, gridBagConstraints);

        jLabel20.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel20.setText("Modelo");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        pnlFormulario1.add(jLabel20, gridBagConstraints);

        txtMod2.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtMod2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(171, 173, 179)));
        txtMod2.setMinimumSize(new java.awt.Dimension(215, 30));
        txtMod2.setPreferredSize(new java.awt.Dimension(215, 30));
        txtMod2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtMod2KeyTyped(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 10, 6, 0);
        pnlFormulario1.add(txtMod2, gridBagConstraints);

        jLabel21.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel21.setText("No. de Serie");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        pnlFormulario1.add(jLabel21, gridBagConstraints);

        txtSer2.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtSer2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(171, 173, 179)));
        txtSer2.setMinimumSize(new java.awt.Dimension(215, 30));
        txtSer2.setPreferredSize(new java.awt.Dimension(215, 30));
        txtSer2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtSer2KeyTyped(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 10, 6, 0);
        pnlFormulario1.add(txtSer2, gridBagConstraints);

        pnlActualizar.add(pnlFormulario1, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 80, -1, -1));

        btnActualizar2.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        btnActualizar2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Pencil_22px.png"))); // NOI18N
        btnActualizar2.setText("Actualizar");
        btnActualizar2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizar2ActionPerformed(evt);
            }
        });
        pnlActualizar.add(btnActualizar2, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 300, -1, -1));

        btnCerrar2.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        btnCerrar2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Close_22px.png"))); // NOI18N
        btnCerrar2.setText("Cerrar");
        btnCerrar2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrar2ActionPerformed(evt);
            }
        });
        pnlActualizar.add(btnCerrar2, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 300, -1, -1));

        jPanel1.setBackground(new java.awt.Color(0, 162, 228));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 70, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 380, Short.MAX_VALUE)
        );

        pnlActualizar.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 0, 70, 380));

        tabbedPane.addTab("Actualizar", pnlActualizar);

        javax.swing.GroupLayout descVidLayout = new javax.swing.GroupLayout(descVid.getContentPane());
        descVid.getContentPane().setLayout(descVidLayout);
        descVidLayout.setHorizontalGroup(
            descVidLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabbedPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        descVidLayout.setVerticalGroup(
            descVidLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabbedPane, javax.swing.GroupLayout.DEFAULT_SIZE, 420, Short.MAX_VALUE)
        );

        descVid.getAccessibleContext().setAccessibleParent(this);

        fallosReporte.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        fallosReporte.setTitle("[Reportar VideoProyector]");
        fallosReporte.setMinimumSize(new java.awt.Dimension(965, 590));
        fallosReporte.setModal(true);
        fallosReporte.setPreferredSize(new java.awt.Dimension(965, 590));

        jPanel5.setBackground(new java.awt.Color(255, 183, 77));
        jPanel5.setMinimumSize(new java.awt.Dimension(940, 560));
        jPanel5.setPreferredSize(new java.awt.Dimension(940, 560));
        jPanel5.setRequestFocusEnabled(false);
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nombre", "Marca", "No Serie", "Fecha Agregado"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.getTableHeader().setReorderingAllowed(false);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable1);

        jPanel5.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, 880, 100));

        jLabel31.setFont(new java.awt.Font("Trebuchet MS", 1, 24)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(255, 255, 255));
        jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel31.setText("Reporte de Fallos");
        jPanel5.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 13, 940, -1));

        lblIcoInfo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/More Info_22px.png"))); // NOI18N
        jPanel5.add(lblIcoInfo, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 520, -1, 30));

        jLabel32.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(255, 255, 255));
        jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel32.setText("Para iniciar el reporte de mantenimiento debe dar doble clic sobre algún VideoProyector");
        jPanel5.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 530, -1, -1));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.white, java.awt.Color.lightGray), "Formulario de Reporte"));

        jLabel33.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        jLabel33.setText("Área Solicitante:");

        jLabel34.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        jLabel34.setText("Nombre del Solicitante:");

        jLabel35.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        jLabel35.setText("Descripción detallada del servicio solicitado o falla a reparar:");
        jLabel35.setToolTipText("");

        txtArea.setEditable(false);
        txtArea.setText("Departamento de Desarrollo Académico");
        txtArea.setMinimumSize(new java.awt.Dimension(370, 30));
        txtArea.setPreferredSize(new java.awt.Dimension(370, 30));

        txtSolicitante.setEditable(false);
        txtSolicitante.setMinimumSize(new java.awt.Dimension(370, 30));
        txtSolicitante.setPreferredSize(new java.awt.Dimension(370, 30));

        txtDetalles.setEditable(false);
        txtDetalles.setBackground(new java.awt.Color(240, 240, 240));
        txtDetalles.setColumns(20);
        txtDetalles.setLineWrap(true);
        txtDetalles.setRows(5);
        txtDetalles.setWrapStyleWord(true);
        txtDetalles.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDetallesKeyTyped(evt);
            }
        });
        jScrollPane9.setViewportView(txtDetalles);

        jLabel36.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        jLabel36.setText("Departamento a quien se dirige la solicitud:");

        btnGrSolicitudPara.add(cb1);
        cb1.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        cb1.setText("Recursos Materiales y Servicios");
        cb1.setOpaque(false);

        btnGrSolicitudPara.add(cb2);
        cb2.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        cb2.setText("Mantenimiento de Equipo");
        cb2.setOpaque(false);

        btnGrSolicitudPara.add(cb3);
        cb3.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        cb3.setText("Centro de Cómputo");
        cb3.setOpaque(false);

        jLabel42.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        jLabel42.setText("Descripción corta de la falla:");

        txtTitulo.setEditable(false);
        txtTitulo.setMinimumSize(new java.awt.Dimension(370, 30));
        txtTitulo.setPreferredSize(new java.awt.Dimension(370, 30));
        txtTitulo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTituloKeyTyped(evt);
            }
        });

        lblRprt6.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        lblRprt6.setText("Imprevisto:");

        btnGrImprevisto.add(cb4);
        cb4.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        cb4.setText("Reparación");
        cb4.setOpaque(false);

        btnGrImprevisto.add(cb5);
        cb5.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        cb5.setText("Mantenimiento");
        cb5.setOpaque(false);

        btnGrImprevisto.add(cb6);
        cb6.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        cb6.setText("Aplicar Garantía");
        cb6.setOpaque(false);

        btnGrImprevisto.add(cb7);
        cb7.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        cb7.setText("Dar de Baja");
        cb7.setOpaque(false);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtArea, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtSolicitante, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel34)
                            .addComponent(jLabel42)
                            .addComponent(cb3)
                            .addComponent(cb1)
                            .addComponent(jLabel36)
                            .addComponent(cb2)
                            .addComponent(jLabel33))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblRprt6)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(cb4)
                                .addGap(31, 31, 31)
                                .addComponent(cb6))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(cb5)
                                .addGap(18, 18, 18)
                                .addComponent(cb7)))
                        .addGap(255, 255, 255))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel35, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane9))
                        .addContainerGap())))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtSolicitante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel33))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(lblRprt6)
                        .addGap(10, 10, 10)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cb4)
                            .addComponent(cb6))
                        .addGap(3, 3, 3)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cb5)
                            .addComponent(cb7))))
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtArea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel42)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel36)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cb1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cb3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cb2))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(jLabel35)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(15, 15, 15))
        );

        jPanel5.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 170, 880, 310));

        btnReporte.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/reportar-22px.png"))); // NOI18N
        btnReporte.setText("Generar Reporte");
        btnReporte.setEnabled(false);
        btnReporte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReporteActionPerformed(evt);
            }
        });
        jPanel5.add(btnReporte, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 490, -1, -1));

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Close_22px.png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        jPanel5.add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 490, -1, -1));

        hiddenNSerie.setText("NSeriePry");
        jPanel5.add(hiddenNSerie, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 490, -1, -1));

        javax.swing.GroupLayout fallosReporteLayout = new javax.swing.GroupLayout(fallosReporte.getContentPane());
        fallosReporte.getContentPane().setLayout(fallosReporteLayout);
        fallosReporteLayout.setHorizontalGroup(
            fallosReporteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        fallosReporteLayout.setVerticalGroup(
            fallosReporteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        fallosReporte.getAccessibleContext().setAccessibleParent(this);

        articulos.setTitle("[Artículos]");
        articulos.setBackground(new java.awt.Color(238, 238, 238));
        articulos.setMinimumSize(new java.awt.Dimension(550, 550));
        articulos.setModal(true);
        articulos.setResizable(false);
        articulos.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                articulosWindowClosing(evt);
            }
        });

        jTabbedPane1.setBackground(new java.awt.Color(255, 255, 255));
        jTabbedPane1.setTabPlacement(javax.swing.JTabbedPane.LEFT);
        jTabbedPane1.setMinimumSize(new java.awt.Dimension(542, 474));
        jTabbedPane1.setPreferredSize(new java.awt.Dimension(542, 474));
        jTabbedPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabbedPane1MouseClicked(evt);
            }
        });

        pnlAllArt.setBackground(new java.awt.Color(255, 255, 255));

        lblTituloArt.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        lblTituloArt.setText("Artículos Existentes");

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Doble clic sobre un elemento permitira abrir la modificación");

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID", "Articulo", "Existencias", "En prestamo"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable2.getTableHeader().setReorderingAllowed(false);
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jTable2);
        if (jTable2.getColumnModel().getColumnCount() > 0) {
            jTable2.getColumnModel().getColumn(0).setPreferredWidth(4);
            jTable2.getColumnModel().getColumn(1).setPreferredWidth(50);
            jTable2.getColumnModel().getColumn(2).setPreferredWidth(23);
            jTable2.getColumnModel().getColumn(3).setPreferredWidth(23);
        }

        jButton2.setText("Cerrar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlAllArtLayout = new javax.swing.GroupLayout(pnlAllArt);
        pnlAllArt.setLayout(pnlAllArtLayout);
        pnlAllArtLayout.setHorizontalGroup(
            pnlAllArtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlAllArtLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2)
                .addGap(197, 197, 197))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlAllArtLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(pnlAllArtLayout.createSequentialGroup()
                .addGap(139, 139, 139)
                .addComponent(lblTituloArt)
                .addContainerGap(157, Short.MAX_VALUE))
        );
        pnlAllArtLayout.setVerticalGroup(
            pnlAllArtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAllArtLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTituloArt)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 379, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addContainerGap())
        );

        jTabbedPane1.addTab("<html><center>Artículos<br>Listados</center></html>", pnlAllArt);

        pnlAddArt.setBackground(new java.awt.Color(255, 255, 255));

        lblTituloArt1.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        lblTituloArt1.setText("Agregar Artículo");

        jLabel15.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel15.setText("Nombre del artículo");

        txtNom.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtNom.setPreferredSize(new java.awt.Dimension(250, 30));
        txtNom.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNomKeyTyped(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel16.setText("Cantidad de existencias");

        jButton1.setText("Guardar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        txtExist.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtExist.setPreferredSize(new java.awt.Dimension(125, 30));
        txtExist.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtExistKeyTyped(evt);
            }
        });

        txtDesc.setColumns(20);
        txtDesc.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtDesc.setLineWrap(true);
        txtDesc.setRows(5);
        txtDesc.setWrapStyleWord(true);
        txtDesc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDescKeyTyped(evt);
            }
        });
        jScrollPane4.setViewportView(txtDesc);

        jLabel17.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel17.setText("Corta descripción");

        jButton3.setText("Limpiar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlAddArtLayout = new javax.swing.GroupLayout(pnlAddArt);
        pnlAddArt.setLayout(pnlAddArtLayout);
        pnlAddArtLayout.setHorizontalGroup(
            pnlAddArtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlAddArtLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblTituloArt1)
                .addGap(166, 166, 166))
            .addGroup(pnlAddArtLayout.createSequentialGroup()
                .addGroup(pnlAddArtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlAddArtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(pnlAddArtLayout.createSequentialGroup()
                            .addGap(30, 30, 30)
                            .addGroup(pnlAddArtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel16)
                                .addComponent(jLabel15)
                                .addComponent(jLabel17)
                                .addGroup(pnlAddArtLayout.createSequentialGroup()
                                    .addComponent(jButton1)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButton3))))
                        .addGroup(pnlAddArtLayout.createSequentialGroup()
                            .addGap(96, 96, 96)
                            .addComponent(txtNom, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(pnlAddArtLayout.createSequentialGroup()
                            .addGap(96, 96, 96)
                            .addComponent(txtExist, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(115, Short.MAX_VALUE))
        );
        pnlAddArtLayout.setVerticalGroup(
            pnlAddArtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAddArtLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTituloArt1)
                .addGap(29, 29, 29)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtExist, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pnlAddArtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton3))
                .addContainerGap(107, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("<html><center>Agregar<br>Artículo</center></html>", pnlAddArt);

        pnlRegArt.setBackground(new java.awt.Color(255, 255, 255));

        jLayeredPane2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jLabel28.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        jLabel28.setText("Registro de Artículos Prestados HOY");

        jTable5.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "Profesor", "Articulo", "Hora", "Fecha"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable5.getTableHeader().setReorderingAllowed(false);
        jScrollPane8.setViewportView(jTable5);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 437, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addComponent(jLabel28)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel28)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 386, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLayeredPane2.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "Profesor", "Articulo", "Hora", "Fecha"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable3.getTableHeader().setReorderingAllowed(false);
        jScrollPane7.setViewportView(jTable3);

        lblTituloArt4.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        lblTituloArt4.setText("Registro de Artículos Prestados");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblTituloArt4)
                        .addGap(102, 102, 102))
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 441, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTituloArt4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 387, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLayeredPane2.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 457, -1));

        tglReg.setText("Todos los Registros de artículos");
        tglReg.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                tglRegItemStateChanged(evt);
            }
        });

        jLabel29.setText("Cambiar a:");

        javax.swing.GroupLayout pnlRegArtLayout = new javax.swing.GroupLayout(pnlRegArt);
        pnlRegArt.setLayout(pnlRegArtLayout);
        pnlRegArtLayout.setHorizontalGroup(
            pnlRegArtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRegArtLayout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addComponent(jLabel29)
                .addGap(46, 46, 46)
                .addComponent(tglReg)
                .addContainerGap(121, Short.MAX_VALUE))
            .addGroup(pnlRegArtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLayeredPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 461, Short.MAX_VALUE))
        );
        pnlRegArtLayout.setVerticalGroup(
            pnlRegArtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlRegArtLayout.createSequentialGroup()
                .addGap(454, 454, 454)
                .addGroup(pnlRegArtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(tglReg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(21, 21, 21))
            .addGroup(pnlRegArtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnlRegArtLayout.createSequentialGroup()
                    .addComponent(jLayeredPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 60, Short.MAX_VALUE)))
        );

        jTabbedPane1.addTab("<html><center>Registros de<br>Artículos<br>Prestados<center></html>", pnlRegArt);

        pnlRptArt.setBackground(new java.awt.Color(255, 255, 255));

        lblTituloArt2.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        lblTituloArt2.setText("Reportar Articulo");

        javax.swing.GroupLayout pnlRptArtLayout = new javax.swing.GroupLayout(pnlRptArt);
        pnlRptArt.setLayout(pnlRptArtLayout);
        pnlRptArtLayout.setHorizontalGroup(
            pnlRptArtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRptArtLayout.createSequentialGroup()
                .addGap(191, 191, 191)
                .addComponent(lblTituloArt2)
                .addContainerGap(125, Short.MAX_VALUE))
        );
        pnlRptArtLayout.setVerticalGroup(
            pnlRptArtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRptArtLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTituloArt2)
                .addContainerGap(469, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("<html><center>Reportar<br>Artículo</center></html>", pnlRptArt);

        pnlModArt.setBackground(new java.awt.Color(255, 255, 255));

        lblTituloArt3.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        lblTituloArt3.setText("Modificar Artículo");

        txtNom3.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtNom3.setPreferredSize(new java.awt.Dimension(250, 30));
        txtNom3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNom3KeyTyped(evt);
            }
        });

        jLabel22.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel22.setText("Nombre del artículo");

        jLabel23.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel23.setText("Cantidad de existencias");

        txtExist1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtExist1.setPreferredSize(new java.awt.Dimension(125, 30));
        txtExist1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtExist1KeyTyped(evt);
            }
        });

        jLabel24.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel24.setText("Corta descripción");

        txtDesc1.setColumns(20);
        txtDesc1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtDesc1.setLineWrap(true);
        txtDesc1.setRows(5);
        txtDesc1.setWrapStyleWord(true);
        txtDesc1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDesc1KeyTyped(evt);
            }
        });
        jScrollPane5.setViewportView(txtDesc1);

        hiddenLabelArt.setText("Soy yo");

        jButton4.setText("Actualizar");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("Borrar");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlModArtLayout = new javax.swing.GroupLayout(pnlModArt);
        pnlModArt.setLayout(pnlModArtLayout);
        pnlModArtLayout.setHorizontalGroup(
            pnlModArtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlModArtLayout.createSequentialGroup()
                .addGroup(pnlModArtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlModArtLayout.createSequentialGroup()
                        .addGap(157, 157, 157)
                        .addComponent(lblTituloArt3))
                    .addGroup(pnlModArtLayout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(pnlModArtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnlModArtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel23)
                                .addComponent(jLabel22)
                                .addComponent(jLabel24)
                                .addGroup(pnlModArtLayout.createSequentialGroup()
                                    .addGap(66, 66, 66)
                                    .addGroup(pnlModArtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtNom3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtExist1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                .addContainerGap(118, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlModArtLayout.createSequentialGroup()
                .addGap(74, 74, 74)
                .addComponent(jButton4)
                .addGap(92, 92, 92)
                .addComponent(jButton5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(hiddenLabelArt)
                .addGap(73, 73, 73))
        );
        pnlModArtLayout.setVerticalGroup(
            pnlModArtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlModArtLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTituloArt3)
                .addGap(36, 36, 36)
                .addComponent(jLabel22)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNom3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel23)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtExist1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel24)
                .addGroup(pnlModArtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlModArtLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 84, Short.MAX_VALUE)
                        .addComponent(hiddenLabelArt)
                        .addGap(43, 43, 43))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlModArtLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(pnlModArtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton4)
                            .addComponent(jButton5))
                        .addGap(56, 56, 56))))
        );

        jTabbedPane1.addTab("<HTML><center>Modificar<br>Articulo<center></HTML>", pnlModArt);

        javax.swing.GroupLayout articulosLayout = new javax.swing.GroupLayout(articulos.getContentPane());
        articulos.getContentPane().setLayout(articulosLayout);
        articulosLayout.setHorizontalGroup(
            articulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        articulosLayout.setVerticalGroup(
            articulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 506, Short.MAX_VALUE)
        );

        dlgServicio.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        dlgServicio.setTitle("[Horas Servicio -Resumen-]");
        dlgServicio.setMinimumSize(new java.awt.Dimension(709, 518));
        dlgServicio.setModal(true);
        dlgServicio.setResizable(false);

        jPanel2.setBackground(java.awt.SystemColor.textHighlight);

        jLabel27.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 255, 255));
        jLabel27.setText("Resumen de servicio de videoproyectores");

        jTable4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Proyector", "Total", "Mes", "Semestre"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable4.getTableHeader().setReorderingAllowed(false);
        jScrollPane6.setViewportView(jTable4);
        if (jTable4.getColumnModel().getColumnCount() > 0) {
            jTable4.getColumnModel().getColumn(0).setResizable(false);
            jTable4.getColumnModel().getColumn(1).setResizable(false);
            jTable4.getColumnModel().getColumn(2).setResizable(false);
            jTable4.getColumnModel().getColumn(3).setResizable(false);
        }

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 162, Short.MAX_VALUE)
                        .addComponent(jLabel27)
                        .addGap(154, 154, 154))
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel27)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(32, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout dlgServicioLayout = new javax.swing.GroupLayout(dlgServicio.getContentPane());
        dlgServicio.getContentPane().setLayout(dlgServicioLayout);
        dlgServicioLayout.setHorizontalGroup(
            dlgServicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        dlgServicioLayout.setVerticalGroup(
            dlgServicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        dlgConfirm.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        dlgConfirm.setTitle("[Confirmar Acción]");
        dlgConfirm.setMinimumSize(new java.awt.Dimension(400, 300));
        dlgConfirm.setModal(true);

        jPanel7.setBackground(new java.awt.Color(255, 183, 77));

        jLabel38.setFont(new java.awt.Font("Trebuchet MS", 0, 24)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(255, 255, 255));
        jLabel38.setText("<html>Esta acción necesita su<br><b>Usuario</b> y <b>Contraseña</b></html>");

        jLabel39.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(255, 255, 255));
        jLabel39.setText("Usuario:");

        jLabel40.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(255, 255, 255));
        jLabel40.setText("Contraseña:");

        btnComprobar.setText("Validar Acción");
        btnComprobar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnComprobarActionPerformed(evt);
            }
        });

        jLabel41.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/niceGuy-60.png"))); // NOI18N

        txtUsuario.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtUsuario.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 5, 1, 1));
        txtUsuario.setMinimumSize(new java.awt.Dimension(111, 30));
        txtUsuario.setPreferredSize(new java.awt.Dimension(111, 30));
        txtUsuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtUsuarioKeyTyped(evt);
            }
        });

        txtPass.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtPass.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 5, 1, 1));
        txtPass.setMinimumSize(new java.awt.Dimension(111, 30));
        txtPass.setPreferredSize(new java.awt.Dimension(111, 30));
        txtPass.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPassKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel38, javax.swing.GroupLayout.DEFAULT_SIZE, 302, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel41)
                .addGap(18, 18, 18))
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel39)
                    .addComponent(jLabel40))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(txtUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtPass, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(142, 142, 142)
                .addComponent(btnComprobar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel41))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel39)
                    .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel40)
                    .addComponent(txtPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnComprobar)
                .addGap(19, 19, 19))
        );

        javax.swing.GroupLayout dlgConfirmLayout = new javax.swing.GroupLayout(dlgConfirm.getContentPane());
        dlgConfirm.getContentPane().setLayout(dlgConfirmLayout);
        dlgConfirmLayout.setHorizontalGroup(
            dlgConfirmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        dlgConfirmLayout.setVerticalGroup(
            dlgConfirmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("[Video proyectores]");
        setIconImage(img.getImage());
        setMaximumSize(new java.awt.Dimension(1024, 600));
        setMinimumSize(new java.awt.Dimension(1024, 600));

        pnlBkVid.setBackground(new java.awt.Color(255, 255, 255));
        pnlBkVid.setMaximumSize(new java.awt.Dimension(1024, 600));
        pnlBkVid.setMinimumSize(new java.awt.Dimension(1024, 600));
        pnlBkVid.setPreferredSize(new java.awt.Dimension(1024, 600));
        pnlBkVid.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlCabecera.setBackground(new java.awt.Color(1, 200, 1));
        pnlCabecera.setMinimumSize(new java.awt.Dimension(1024, 83));
        pnlCabecera.setPreferredSize(new java.awt.Dimension(1024, 83));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/tecnm2.png"))); // NOI18N

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/sep.png"))); // NOI18N

        jLabel3.setFont(new java.awt.Font("Trebuchet MS", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Hora");

        labelHora.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        labelHora.setForeground(new java.awt.Color(255, 255, 255));
        labelHora.setText("00:00:00 PM");
        labelHora.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        jLabel5.setFont(new java.awt.Font("Trebuchet MS", 1, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Fecha");

        labelFecha.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        labelFecha.setForeground(new java.awt.Color(255, 255, 255));
        labelFecha.setText("00-00-0000");
        labelFecha.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        javax.swing.GroupLayout pnlCabeceraLayout = new javax.swing.GroupLayout(pnlCabecera);
        pnlCabecera.setLayout(pnlCabeceraLayout);
        pnlCabeceraLayout.setHorizontalGroup(
            pnlCabeceraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCabeceraLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(36, 36, 36)
                .addComponent(jLabel3)
                .addGap(20, 20, 20)
                .addComponent(labelHora)
                .addGap(107, 107, 107)
                .addComponent(jLabel5)
                .addGap(20, 20, 20)
                .addComponent(labelFecha)
                .addGap(36, 36, 36)
                .addComponent(jLabel2)
                .addContainerGap())
        );
        pnlCabeceraLayout.setVerticalGroup(
            pnlCabeceraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCabeceraLayout.createSequentialGroup()
                .addGroup(pnlCabeceraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(pnlCabeceraLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(pnlCabeceraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(labelFecha, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(labelHora, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(pnlCabeceraLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2)))
                .addContainerGap())
        );

        pnlBkVid.add(pnlCabecera, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        lblPanelTitulo.setFont(new java.awt.Font("Trebuchet MS", 1, 24)); // NOI18N
        lblPanelTitulo.setText("VideoProyectores");
        lblPanelTitulo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblPanelTituloMouseClicked(evt);
            }
        });
        pnlBkVid.add(lblPanelTitulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 110, -1, -1));

        btnRegresar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Back To_35px.png"))); // NOI18N
        btnRegresar.setMinimumSize(new java.awt.Dimension(40, 37));
        btnRegresar.setPreferredSize(new java.awt.Dimension(40, 37));
        btnRegresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegresarActionPerformed(evt);
            }
        });
        pnlBkVid.add(btnRegresar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 540, -1, -1));

        btnMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Checklist_24px.png"))); // NOI18N
        btnMenu.setMinimumSize(new java.awt.Dimension(40, 37));
        btnMenu.setPreferredSize(new java.awt.Dimension(40, 37));
        btnMenu.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                btnMenuItemStateChanged(evt);
            }
        });
        pnlBkVid.add(btnMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 101, -1, -1));

        panelOPC.setBackground(new java.awt.Color(250, 250, 250));
        panelOPC.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(171, 173, 179), 1, true));
        panelOPC.setLayout(new java.awt.GridBagLayout());

        ico3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/prestamo_32px.png"))); // NOI18N
        ico3.setToolTipText("Prestamo");
        ico3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ico3MouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(15, 0, 5, 0);
        panelOPC.add(ico3, gridBagConstraints);

        ico4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/devolu_32px.png"))); // NOI18N
        ico4.setToolTipText("Devolución");
        ico4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ico4MouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
        panelOPC.add(ico4, gridBagConstraints);

        ico5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/registr_32px.png"))); // NOI18N
        ico5.setToolTipText("Registros");
        ico5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ico5MouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
        panelOPC.add(ico5, gridBagConstraints);

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/prof_42px.png"))); // NOI18N
        jLabel7.setToolTipText("Profesores");
        jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel7MouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(15, 0, 5, 0);
        panelOPC.add(jLabel7, gridBagConstraints);

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/depart_42px.png"))); // NOI18N
        jLabel8.setToolTipText("Departamentos");
        jLabel8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel8MouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
        panelOPC.add(jLabel8, gridBagConstraints);

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/aul_42px.png"))); // NOI18N
        jLabel9.setToolTipText("Aulas");
        jLabel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel9MouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 15, 0);
        panelOPC.add(jLabel9, gridBagConstraints);

        pnlBkVid.add(panelOPC, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 152, -1, -1));

        btnPnlCrear.setBackground(new java.awt.Color(239, 239, 239));
        btnPnlCrear.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(64, 190, 205)));
        btnPnlCrear.setMaximumSize(new java.awt.Dimension(78, 86));
        btnPnlCrear.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPnlCrearMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnPnlCrearMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnPnlCrearMouseExited(evt);
            }
        });
        btnPnlCrear.setLayout(new java.awt.GridBagLayout());

        lblIco0.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblIco0.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Add New_36px.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 20, 0, 20);
        btnPnlCrear.add(lblIco0, gridBagConstraints);

        lblIco1.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        lblIco1.setForeground(new java.awt.Color(0, 191, 255));
        lblIco1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblIco1.setText("Nuevo");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 0, 0);
        btnPnlCrear.add(lblIco1, gridBagConstraints);

        lblIco2.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        lblIco2.setForeground(new java.awt.Color(0, 191, 255));
        lblIco2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblIco2.setText("Registro");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 5, 0);
        btnPnlCrear.add(lblIco2, gridBagConstraints);

        pnlBkVid.add(btnPnlCrear, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 100, -1, -1));

        btnPnlReportar.setBackground(new java.awt.Color(239, 239, 239));
        btnPnlReportar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(64, 190, 205)));
        btnPnlReportar.setMaximumSize(new java.awt.Dimension(78, 86));
        btnPnlReportar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPnlReportarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnPnlReportarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnPnlReportarMouseExited(evt);
            }
        });
        btnPnlReportar.setLayout(new java.awt.GridBagLayout());

        lblIco3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblIco3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/inform-36.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 20, 0, 20);
        btnPnlReportar.add(lblIco3, gridBagConstraints);

        lblIco4.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        lblIco4.setForeground(new java.awt.Color(231, 76, 60));
        lblIco4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblIco4.setText("Reportar");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 0, 0);
        btnPnlReportar.add(lblIco4, gridBagConstraints);

        lblIco5.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        lblIco5.setForeground(new java.awt.Color(231, 76, 60));
        lblIco5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblIco5.setText("Fallo");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 5, 0);
        btnPnlReportar.add(lblIco5, gridBagConstraints);

        pnlBkVid.add(btnPnlReportar, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 200, -1, -1));

        btnPnlArticulos.setBackground(new java.awt.Color(0, 191, 255));
        btnPnlArticulos.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(64, 190, 205)));
        btnPnlArticulos.setMaximumSize(new java.awt.Dimension(78, 86));
        btnPnlArticulos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPnlArticulosMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnPnlArticulosMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnPnlArticulosMouseExited(evt);
            }
        });
        btnPnlArticulos.setLayout(new java.awt.GridBagLayout());

        lblIco6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblIco6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Electrical_36px.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 20, 0, 20);
        btnPnlArticulos.add(lblIco6, gridBagConstraints);

        lblIco7.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        lblIco7.setForeground(new java.awt.Color(255, 255, 255));
        lblIco7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblIco7.setText("Registro de");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 0, 0);
        btnPnlArticulos.add(lblIco7, gridBagConstraints);

        lblIco8.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        lblIco8.setForeground(new java.awt.Color(255, 255, 255));
        lblIco8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblIco8.setText("Articulos");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 5, 0);
        btnPnlArticulos.add(lblIco8, gridBagConstraints);

        pnlBkVid.add(btnPnlArticulos, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 295, -1, -1));

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane1.setPreferredSize(new java.awt.Dimension(815, 363));

        pnlContenedor.setBackground(new java.awt.Color(250, 250, 250));
        pnlContenedor.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 3, 0));
        jScrollPane1.setViewportView(pnlContenedor);

        pnlBkVid.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(99, 159, -1, 430));

        btnPnlGaf.setBackground(new java.awt.Color(239, 239, 239));
        btnPnlGaf.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(64, 190, 205)));
        btnPnlGaf.setMaximumSize(new java.awt.Dimension(78, 86));
        btnPnlGaf.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPnlGafMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnPnlGafMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnPnlGafMouseExited(evt);
            }
        });
        btnPnlGaf.setLayout(new java.awt.GridBagLayout());

        lblIco9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblIco9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/IDCard_36px.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 20, 0, 20);
        btnPnlGaf.add(lblIco9, gridBagConstraints);

        lblIco10.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        lblIco10.setForeground(new java.awt.Color(206, 127, 80));
        lblIco10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblIco10.setText("Generar");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 0, 0);
        btnPnlGaf.add(lblIco10, gridBagConstraints);

        lblIco11.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        lblIco11.setForeground(new java.awt.Color(206, 127, 80));
        lblIco11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblIco11.setText("Gafete Proy");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 5, 0);
        btnPnlGaf.add(lblIco11, gridBagConstraints);

        pnlBkVid.add(btnPnlGaf, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 500, -1, -1));

        btnPnlCodebar.setBackground(new java.awt.Color(239, 239, 239));
        btnPnlCodebar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(64, 190, 205)));
        btnPnlCodebar.setMaximumSize(new java.awt.Dimension(78, 102));
        btnPnlCodebar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPnlCodebarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnPnlCodebarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnPnlCodebarMouseExited(evt);
            }
        });
        btnPnlCodebar.setLayout(new java.awt.GridBagLayout());

        lblIco12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblIco12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/getBarcode_36px.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 20, 0, 20);
        btnPnlCodebar.add(lblIco12, gridBagConstraints);

        lblIco13.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        lblIco13.setForeground(new java.awt.Color(25, 213, 41));
        lblIco13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblIco13.setText("Generar");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 0, 0);
        btnPnlCodebar.add(lblIco13, gridBagConstraints);

        lblIco14.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        lblIco14.setForeground(new java.awt.Color(25, 213, 41));
        lblIco14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblIco14.setText("<html><center>Código de<br>Barras</center></html>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 5, 0);
        btnPnlCodebar.add(lblIco14, gridBagConstraints);

        pnlBkVid.add(btnPnlCodebar, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 390, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlBkVid, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlBkVid, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseClicked
        try {
            Profesor profe = new Profesor();
            profe.setVisible(true);
            this.setVisible(false);
            this.dispose();
        } catch (SQLException ex) {
            Logger.getLogger(Profesor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jLabel7MouseClicked

    private void jLabel8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MouseClicked
        try {
            Departamento depart = new Departamento();
            depart.setVisible(true);
            this.setVisible(false);
            this.dispose();
        } catch (SQLException ex) {
            Logger.getLogger(Videoproyector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jLabel8MouseClicked

    private void btnMenuItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_btnMenuItemStateChanged
        if (btnMenu.isSelected()) {
            panelOPC.setVisible(true);
        } else {
            panelOPC.setVisible(false);
        }
    }//GEN-LAST:event_btnMenuItemStateChanged

    private void jLabel9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseClicked
        try {
            Aula aula = new Aula();
            aula.setVisible(true);
            this.setVisible(false);
            this.dispose();
        } catch (SQLException ex) {
            Logger.getLogger(Videoproyector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jLabel9MouseClicked

    private void btnRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegresarActionPerformed
        this.setVisible(false);
        this.dispose();
        Menu menu = new Menu();
        menu.setVisible(true);
    }//GEN-LAST:event_btnRegresarActionPerformed

    private void btnPnlCrearMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPnlCrearMouseClicked
        btnPnlCrear.setBackground(new Color(239, 239, 239));
        lblIco0.setIcon(add);
        
        crearVid.setLocationRelativeTo(pnlBkVid);
        crearVid.setVisible(true);
    }//GEN-LAST:event_btnPnlCrearMouseClicked

    private void btnCerrar3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrar3ActionPerformed
        tabbedPane.setSelectedIndex(0);
        descVid.setVisible(false);
        descVid.dispose();
    }//GEN-LAST:event_btnCerrar3ActionPerformed

    private void btnCerrar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrar1ActionPerformed
        crearVid.setVisible(false);
        crearVid.dispose();
        txtNom1.setText("");
        txtMarc1.setText("");
        txtMod1.setText("");
        txtSer1.setText("");
    }//GEN-LAST:event_btnCerrar1ActionPerformed

    private void btnGuardar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardar1ActionPerformed
        String nom = txtNom1.getText().trim();
        String marc = txtMarc1.getText().trim();
        String mod = txtMod1.getText().trim();
        String nser = txtSer1.getText().trim();
        
        //crear video
        if (!nom.isEmpty() && !marc.isEmpty() && !mod.isEmpty() && !nser.isEmpty()) {
            try {
                String[] data = {nom, marc, mod, nser};
                VideoproyectorDB proye = new VideoproyectorDB();
                if (!proye.getExisteProyector(nser)){
                    if(!proye.getExisteNomProyector(nom)){
                    proye.setVideoProyector(data);
                    JOptionPane.showMessageDialog(null, "Nuevo videoproyecto creado\n\nRecuerde que esta información puede ser modificada en cualquier momento!", "Información", JOptionPane.INFORMATION_MESSAGE);
                    crearVid.setVisible(false);
                    crearVid.dispose();

                    dibujarProye();
                    }else { JOptionPane.showMessageDialog(null, "Pruebe a utilizar un nombre distinto", "Advertencia", JOptionPane.WARNING_MESSAGE); }
                }else { JOptionPane.showMessageDialog(null, "Al parecer el numero de serie ya ha sido registrado en el sistema", "Advertencia", JOptionPane.WARNING_MESSAGE); }
            } catch (SQLException ex) {
                Logger.getLogger(Videoproyector.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "No puede dejar ningún campo en blanco\n\nPor favor introduzca la información solicitada!", "Advertencia", JOptionPane.ERROR_MESSAGE);
        }

        txtNom1.setText("");
        txtMarc1.setText("");
        txtMod1.setText("");
        txtSer1.setText("");
    }//GEN-LAST:event_btnGuardar1ActionPerformed

    private void btnEliminarVid1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarVid1ActionPerformed
        int result = JOptionPane.showConfirmDialog(null, "Es una accion irreversible!!!\n\nSI HACE ESTO SE BORRARAN TODOS LOS REGISTROS QUE SE HICIERON CON ESTE VIDEOPROYECTOR\n\nEstá seguro que desea eliminar?", "Advertencia", JOptionPane.WARNING_MESSAGE, JOptionPane.YES_NO_OPTION);
        //
        if (JOptionPane.YES_OPTION == result) {
            System.out.println("voy a eliminar!!!");
            try {
                VideoproyectorDB proye = new VideoproyectorDB();
                PrestamoDB prestamo = new PrestamoDB();
                String pryNoSerie = proye.getProyectorNoSerie(hidenData.getText());
                System.out.println("pryNoSerie a borrar: " + pryNoSerie);
                if(!prestamo.getExistePrestamoProy(pryNoSerie)){
                    proye.destroyVideoproy(pryNoSerie);
                    tabbedPane.setSelectedIndex(0);
                    descVid.setVisible(false);
                    descVid.dispose();

                    dibujarProye();
                    JOptionPane.showMessageDialog(null, "El registro fue borrado!!!");
                }else{
                    JOptionPane.showMessageDialog(null, "El proyector que desea eliminar se encuentra registrado en un prestamo con un profesor\nprimero registre la devolucion del Videoproyector despues proceda a eliminarlo\n\nRECUERDE QUE ESTA ACCION ELIMINARA TODOS LOS REGISTROS RELACIONADOS");
                }
            } catch (SQLException ex) {
                Logger.getLogger(Videoproyector.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "No se ha borrado el registro\n\nRecuerde que esta accion es irreversible y borrara todos los registros y prestamos asignados a este proyector", "Información", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btnEliminarVid1ActionPerformed

    private void btnActualizar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizar1ActionPerformed
        try {
            VideoproyectorDB proye = new VideoproyectorDB();
            String[] data = proye.getProyector(proye.getProyectorNoSerie(hidenData.getText()));
            txtNom2.setText(data[1]);
            txtMarc2.setText(data[2]);
            txtMod2.setText(data[3]);
            txtSer2.setText(data[4]);
            System.out.println("mi id: " + data[0]);
            hidenData2.setText(data[0]);

            tabbedPane.setSelectedIndex(1);
        } catch (SQLException ex) {
            Logger.getLogger(Videoproyector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnActualizar1ActionPerformed

    private void btnCerrar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrar2ActionPerformed
        tabbedPane.setSelectedIndex(0);
        descVid.setVisible(false);
        descVid.dispose();
    }//GEN-LAST:event_btnCerrar2ActionPerformed

    private void btnActualizar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizar2ActionPerformed
        try {
            VideoproyectorDB proye = new VideoproyectorDB();
            String nom = txtNom2.getText();
            String marc = txtMarc2.getText();
            String mod = txtMod2.getText();
            String ser = txtSer2.getText();
            if (!nom.isEmpty() && !marc.isEmpty() && !mod.isEmpty() && !ser.isEmpty()){
                String[] datos = {nom, marc, mod, ser};
                proye.updVideoproyector(Integer.parseInt(hidenData2.getText()), datos);

                JOptionPane.showMessageDialog(null, "Se ha actualizado la información");

                descVid.setVisible(false);
                descVid.dispose();

                dibujarProye();

                tabbedPane.setSelectedIndex(0);
            }else{
                JOptionPane.showMessageDialog(null, "Favor de no dejar ningún campo vacio", "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Videoproyector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnActualizar2ActionPerformed

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
        ARegistro reg = new ARegistro();
        reg.setVisible(true);
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_ico5MouseClicked

    private void txtNom1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNom1KeyTyped
        if (txtNom1.getText().length() >= 15) {
            evt.consume();
        }
    }//GEN-LAST:event_txtNom1KeyTyped

    private void txtMarc1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMarc1KeyTyped
        if (txtMarc1.getText().length() >= 15) {
            evt.consume();
        }
    }//GEN-LAST:event_txtMarc1KeyTyped

    private void txtMod1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMod1KeyTyped
        if (txtMod1.getText().length() >= 20) {
            evt.consume();
        }
    }//GEN-LAST:event_txtMod1KeyTyped

    private void txtSer1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSer1KeyTyped
        if (txtSer1.getText().length() >= 20) {
            evt.consume();
        }
    }//GEN-LAST:event_txtSer1KeyTyped

    private void btnPnlCrearMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPnlCrearMouseEntered
        btnPnlCrear.setBackground(new Color(0, 191, 255));
        lblIco0.setIcon(addW);
    }//GEN-LAST:event_btnPnlCrearMouseEntered

    private void btnPnlCrearMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPnlCrearMouseExited
        btnPnlCrear.setBackground(new Color(239, 239, 239));
        lblIco0.setIcon(add);
    }//GEN-LAST:event_btnPnlCrearMouseExited

    private void txtNom2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNom2KeyTyped
        if (txtNom2.getText().length() >= 15) {
            evt.consume();
        }
    }//GEN-LAST:event_txtNom2KeyTyped

    private void txtMarc2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMarc2KeyTyped
        if (txtMarc2.getText().length() >= 15) {
            evt.consume();
        }
    }//GEN-LAST:event_txtMarc2KeyTyped

    private void txtMod2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMod2KeyTyped
        if (txtMod2.getText().length() >= 20) {
            evt.consume();
        }
    }//GEN-LAST:event_txtMod2KeyTyped

    private void txtSer2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSer2KeyTyped
        if (txtSer2.getText().length() >= 20) {
            evt.consume();
        }
    }//GEN-LAST:event_txtSer2KeyTyped

    private void btnPnlArticulosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPnlArticulosMouseClicked
        btnPnlArticulos.setBackground(new Color(0,191,255));
        lblIco6.setIcon(art);
        lblIco7.setForeground(new Color(255,255,255));
        lblIco8.setForeground(new Color(255,255,255));
        
        try {
            getTable();
        } catch (SQLException ex) {
            Logger.getLogger(Videoproyector.class.getName()).log(Level.SEVERE, null, ex);
        }
        articulos.setLocationRelativeTo(pnlBkVid);
        articulos.setVisible(true);
    }//GEN-LAST:event_btnPnlArticulosMouseClicked

    private void btnPnlArticulosMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPnlArticulosMouseEntered
        btnPnlArticulos.setBackground(new Color(239,239,239));
        lblIco6.setIcon(artW);
        lblIco7.setForeground(new Color(239,239,239));
        lblIco8.setForeground(new Color(239,239,239));
    }//GEN-LAST:event_btnPnlArticulosMouseEntered

    private void btnPnlArticulosMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPnlArticulosMouseExited
        btnPnlArticulos.setBackground(new Color(0,191,255));
        lblIco6.setIcon(art);
        lblIco7.setForeground(new Color(255,255,255));
        lblIco8.setForeground(new Color(255,255,255));
    }//GEN-LAST:event_btnPnlArticulosMouseExited

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        articulos.setVisible(false);
        articulos.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void txtExistKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtExistKeyTyped
        char caracter = evt.getKeyChar();
        if (((caracter < '0') || (caracter > '9')) && (caracter != '\b')) {
            evt.consume();
        }
        if (txtExist.getText().length() >= 6) {
            evt.consume();
        }
    }//GEN-LAST:event_txtExistKeyTyped

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        int fila = jTable2.getSelectedRow();
        if(evt.getClickCount() == 2){
            System.out.println("fila seleccionada: " + fila);
            jTabbedPane1.setEnabledAt(4, true);
            jTabbedPane1.setSelectedIndex(4);
            try {
                AccesorioDB acc = new AccesorioDB();
                String id = String.valueOf(jTable2.getValueAt(fila, 0));
                String[] datos = acc.getAccesorio(id);
                hiddenLabelArt.setText(datos[0]);
                txtNom3.setText(datos[1]);
                txtExist1.setText(datos[2]);
                txtDesc1.setText(datos[4]);
            } catch (SQLException ex) {
                Logger.getLogger(Videoproyector.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
//        int fila = (int) jTable2.getModel().getValueAt(jTable2.getSelectedRow(),3);
//        System.out.println("Seleccionaste la fila de articulos id: " +jTable2.getModel().getValueAt(jTable2.convertRowIndexToModel(fila), 3));
    }//GEN-LAST:event_jTable2MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String nom = txtNom.getText().trim();
        String exist = txtExist.getText().trim();
        String desc = txtDesc.getText().trim();
        if (!nom.isEmpty() && !exist.isEmpty() && !desc.isEmpty()){
            String[] articulo = {nom, exist, desc};
            System.out.println("Arreglo de datos: " + Arrays.toString(articulo));
            try {
                AccesorioDB artic = new AccesorioDB();
                artic.setAccesorio(articulo);
                JOptionPane.showMessageDialog(null, "Se ha creado el nuevo registro");
            } catch (SQLException ex) {
                System.out.println("Error al realizar el guardado de Articulo: " + ex);
            }
            txtNom.setText("");
            txtDesc.setText("");
            txtExist.setText("");
        }else{
            JOptionPane.showMessageDialog(null, "No puede dejar ningun campo vacio\nFavor de completar el formulario", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtNomKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNomKeyTyped
        if (txtNom.getText().length() >= 20) {
            evt.consume();
        }
    }//GEN-LAST:event_txtNomKeyTyped

    private void txtDescKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescKeyTyped
        if (txtDesc.getText().length() >= 350) {
            evt.consume();
        }
    }//GEN-LAST:event_txtDescKeyTyped

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        txtNom.setText("");
        txtDesc.setText("");
        txtExist.setText("");
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jTabbedPane1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPane1MouseClicked
        int tab = jTabbedPane1.getSelectedIndex();
        if(tab == 0 ){
            try {
                getTable();
            } catch (SQLException ex) {
                Logger.getLogger(Videoproyector.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if(tab != 3 && tab != 4 ){
            jTabbedPane1.setEnabledAt(3, false);
            jTabbedPane1.setEnabledAt(4, false);
        }
        if(tab == 2){
            System.out.println("::...Diburar registros accesorios");
            try {
            getRegistrosPrestamoAccHOY();
            } catch (SQLException e) {
                System.out.println("Problemas al recupear registros accesorios jTabbedPane clic: " + e);
            }
        }
    }//GEN-LAST:event_jTabbedPane1MouseClicked

    private void txtNom3KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNom3KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNom3KeyTyped

    private void txtExist1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtExist1KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtExist1KeyTyped

    private void txtDesc1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDesc1KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDesc1KeyTyped

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        String dato1 = txtNom3.getText().trim();
        String dato2 = txtExist1.getText().trim();
        String dato3 = txtDesc1.getText().trim();
        String id = hiddenLabelArt.getText().trim();
        if(!dato1.isEmpty() && !dato2.isEmpty() && !dato3.isEmpty()){
            String[] info = {id, dato1, dato2, dato3};
            try {
                AccesorioDB acc = new AccesorioDB();
                acc.updAccesorio(info);
                JOptionPane.showMessageDialog(null, "Se ha actualizado el registro");
                jTabbedPane1.setEnabledAt(4, false);
                jTabbedPane1.setSelectedIndex(0);
                getTable();
            } catch (SQLException e) {
                System.out.println("Error al realizar esta acción Actualizar Accesorio: " + e);
            }
        }else{
            JOptionPane.showMessageDialog(null,"Favor de no dejar en blanco ningún campo");
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void btnPnlGafMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPnlGafMouseClicked
        btnPnlGaf.setBackground(new Color(239,239,239));
        GenerarReportes g = new GenerarReportes();
        g.gafProyectores();
    }//GEN-LAST:event_btnPnlGafMouseClicked

    private void btnPnlGafMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPnlGafMouseEntered
        btnPnlGaf.setBackground(new Color(217,217,217));
    }//GEN-LAST:event_btnPnlGafMouseEntered

    private void btnPnlGafMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPnlGafMouseExited
        btnPnlGaf.setBackground(new Color(239,239,239));
    }//GEN-LAST:event_btnPnlGafMouseExited

    private void btnPnlCodebarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPnlCodebarMouseClicked
        btnPnlCodebar.setBackground(new Color(239,239,239));
        GenerarReportes g = new GenerarReportes();
        g.codeBarProyectores();
    }//GEN-LAST:event_btnPnlCodebarMouseClicked

    private void btnPnlCodebarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPnlCodebarMouseEntered
        btnPnlCodebar.setBackground(new Color(217,217,217));
    }//GEN-LAST:event_btnPnlCodebarMouseEntered

    private void btnPnlCodebarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPnlCodebarMouseExited
        btnPnlCodebar.setBackground(new Color(239,239,239));
    }//GEN-LAST:event_btnPnlCodebarMouseExited

    private void lblPanelTituloMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblPanelTituloMouseClicked
        try {
            toResumenServicio();
            dlgServicio.setLocationRelativeTo(pnlBkVid);
            dlgServicio.setVisible(true);
        } catch (SQLException ex) {
            System.out.println("Error al cargar la tabla de db con el resumen de hrs de servicio: " + ex);
        } catch (Exception ex) {
            System.out.println("Error al cargar la tabla con el resumen de hrs de servicio: " + ex);
        }
    }//GEN-LAST:event_lblPanelTituloMouseClicked

    private void tglRegItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_tglRegItemStateChanged
        if (tglReg.isSelected()) {
            try{getRegistrosPrestamoAcc();}catch(SQLException e){System.out.println("Error al recuperar todos los registros existentes de articulos: " + e);}
            jPanel3.setVisible(true);
            jPanel4.setVisible(false);
            tglReg.setText("Los registros de artículos de HOY");
        } else {
            try{getRegistrosPrestamoAccHOY();}catch(SQLException e){System.out.println("Error al recuperar todos los registros existentes de articulos: " + e);}
            jPanel4.setVisible(true);
            jPanel3.setVisible(false);
            tglReg.setText("Todos los Registros de artículos");
        }
    }//GEN-LAST:event_tglRegItemStateChanged

    private void articulosWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_articulosWindowClosing
        jTabbedPane1.setSelectedIndex(0);
        tglReg.setSelected(false);
    }//GEN-LAST:event_articulosWindowClosing

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        int opc = JOptionPane.showConfirmDialog(null,"Esta seguro de querer eliminar este articulo","Advertencia", JOptionPane.OK_CANCEL_OPTION);
        if(opc == JOptionPane.OK_OPTION){
            try {
                AccesorioDB acc = new AccesorioDB();
                acc.destroyAcc(Integer.parseInt(hiddenLabelArt.getText().trim()));
                jTabbedPane1.setSelectedIndex(1);
                JOptionPane.showMessageDialog(null, "Elemento eliminado!!! ya no existiran registros");
            } catch (SQLException e) {
                System.out.println("Error al eliminar el registro de accesorio: " + e);
            }
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void btnPnlReportarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPnlReportarMouseClicked
        dlgConfirm.setLocationRelativeTo(this);
        dlgConfirm.setVisible(true);
        
        if(valido){
            valido = false;
            try {    
                DefaultTableModel modelPryRprt = (DefaultTableModel) jTable1.getModel();
                VideoproyectorDB pryR = new VideoproyectorDB();
                String[] cols = {jTable1.getColumnName(0),jTable1.getColumnName(1), jTable1.getColumnName(2), jTable1.getColumnName(3)};
                DateFormat dtFm = new SimpleDateFormat("dd/MM/yyyy HH:mm a", Locale.ENGLISH);
                String[][] datos = pryR.getProyectores(true);
                int count = datos.length;
                System.out.println("\nRegistros accesorios existentes: " + count);

                String[][] datoFrmt = new String[count][4];
                Calendar c = Calendar.getInstance();
                for(int i = 0; i < count; i++){
                    int[] arr = formatFecha(datos[i][5]);
                    c.set(arr[0], arr[1], arr[2], arr[3], arr[4]);
                    datoFrmt[i][0] = datos[i][1];
                    datoFrmt[i][1] = datos[i][2];
                    datoFrmt[i][2] = datos[i][4];
                    datoFrmt[i][3] = dtFm.format(c.getTime());
                }

                modelPryRprt.setDataVector(datoFrmt, cols);
                jTable1.getColumnModel().getColumn(0).setPreferredWidth(90);
                jTable1.getColumnModel().getColumn(1).setPreferredWidth(87);
                jTable1.getColumnModel().getColumn(2).setPreferredWidth(99);
                jTable1.getColumnModel().getColumn(3).setPreferredWidth(133);

                jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
            } catch (SQLException ex) {
                System.out.println("Error al escribir datos en tabla videoproyector Report: " + ex);
            }
        
            clearReport();
        
            fallosReporte.setLocationRelativeTo(this);
            fallosReporte.setVisible(true);
        }
    }//GEN-LAST:event_btnPnlReportarMouseClicked

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        if(evt.getClickCount() == 2 && jTable1.getSelectedRow() != -1){
            int row = jTable1.getSelectedRow();
            String nombre = jTable1.getValueAt(row, 0).toString();
            jLabel31.setText("Reporte de Fallos de "+nombre);
            hiddenNSerie.setText(jTable1.getValueAt(row, 2).toString());
            btnReporte.setEnabled(true);
            
            cb1.setSelected(false);
            cb2.setSelected(false);
            cb3.setSelected(false);

            cb1.setEnabled(true);
            cb2.setEnabled(true);
            cb3.setEnabled(true);
            
            cb4.setSelected(false);
            cb5.setSelected(false);
            cb6.setSelected(false);
            cb7.setSelected(false);

            cb4.setEnabled(true);
            cb5.setEnabled(true);
            cb6.setEnabled(true);
            cb7.setEnabled(true);
        
            txtArea.setEditable(true);
            txtSolicitante.setEditable(true);
            txtDetalles.setEditable(true);
            txtTitulo.setEditable(true);
            
            txtArea.setBackground(new Color(255,255,255));
            txtSolicitante.setBackground(new Color(255,255,255));
            txtDetalles.setBackground(new Color(255,255,255));
            txtTitulo.setBackground(new Color(255,255,255));
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        clearReport();
    }//GEN-LAST:event_btnCancelarActionPerformed

    public void clearReport(){
        txtArea.setText("Departamento de Desarrollo Académico");
        txtSolicitante.setText("");
        txtDetalles.setText("");
        txtTitulo.setText("");
        jLabel31.setText("Reporte de Fallos");
        btnReporte.setEnabled(false);
        
        cb1.setSelected(false);
        cb2.setSelected(false);
        cb3.setSelected(false);
        
        cb1.setEnabled(false);
        cb2.setEnabled(false);
        cb3.setEnabled(false);
        
        cb4.setSelected(false);
        cb5.setSelected(false);
        cb6.setSelected(false);
        cb7.setSelected(false);
        
        cb4.setEnabled(false);
        cb5.setEnabled(false);
        cb6.setEnabled(false);
        cb7.setEnabled(false);
        
        txtArea.setEditable(false);
        txtSolicitante.setEditable(false);
        txtDetalles.setEditable(false);
        txtTitulo.setEditable(false);
        
        txtArea.setBackground(new Color(204,204,204));
        txtSolicitante.setBackground(new Color(204,204,204));
        txtDetalles.setBackground(new Color(204,204,204));
        txtTitulo.setBackground(new Color(204,204,204));
    }
    private void btnReporteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReporteActionPerformed
        String titulo = txtTitulo.getText().trim();
        String nombreEncargado = txtSolicitante.getText().trim();
        String area = txtArea.getText().trim();
        String deptoReparador = "a";
        String imprevisto = "a";
        String detalles = txtDetalles.getText().trim();
        
        if(!area.isEmpty() && !nombreEncargado.isEmpty() && !detalles.isEmpty() && !titulo.isEmpty()){
            if((cb1.isSelected() || cb2.isSelected() || cb3.isSelected()) && (cb4.isSelected() || cb5.isSelected() || cb6.isSelected() || cb7.isSelected())){
                //deptoReparador
                if(cb1.isSelected()){ deptoReparador = "a";} 
                else if(cb2.isSelected()){ deptoReparador = "b";} 
                else if(cb3.isSelected()){ deptoReparador = "c";}
                //imprevisto
                if(cb4.isSelected()){ imprevisto = "a";} 
                else if(cb5.isSelected()){ imprevisto = "b";} 
                else if(cb6.isSelected()){ imprevisto = "c";}
                else if(cb7.isSelected()){ imprevisto = "d";}
                String[] datos = {titulo, nombreEncargado, area, deptoReparador, imprevisto, detalles};
                String[] reporteDtos = {area, nombreEncargado, detalles, deptoReparador, hiddenNSerie.getText()};
                
                try {
                    VideoproyectorDB pry = new VideoproyectorDB();
                    GenerarReportes genR = new GenerarReportes();
                    pry.setReparacionPry(hiddenNSerie.getText());
                    
                    pry.reportePry(Integer.parseInt(pry.getProyectorID(hiddenNSerie.getText())), datos);
                    genR.getReporteFalloPry(reporteDtos);
                    
                    dibujarProye();
                    fallosReporte.setVisible(false);
                    fallosReporte.dispose();
                } catch (SQLException ex) {
                    System.out.println("Error al crear registro de prestamo en videoproyector btnReporte: " + ex);
                }
            }else{JOptionPane.showMessageDialog(null, "Recuerde llenar todo el formulario"); clearReport();}
        }else{
            JOptionPane.showMessageDialog(null, "Recuerde llenar todo el formulario"); clearReport();
        }
    }//GEN-LAST:event_btnReporteActionPerformed

    private void btnPnlReportarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPnlReportarMouseEntered
        btnPnlReportar.setBackground(new Color(204,204,204));
    }//GEN-LAST:event_btnPnlReportarMouseEntered

    private void btnPnlReportarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPnlReportarMouseExited
        btnPnlReportar.setBackground(new Color(239,239,239));
    }//GEN-LAST:event_btnPnlReportarMouseExited

    private void btnMantActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMantActionPerformed
        dlgConfirm.setLocationRelativeTo(this);
        dlgConfirm.setVisible(true);
        
        if(valido){
            JOptionPane.showMessageDialog(null, "Videoproyector habilitado para su uso", "Información", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("./src/imagenes/Ok.png"));
            try {
                VideoproyectorDB pry = new VideoproyectorDB();
                pry.updReparacionPryFree(Integer.parseInt(hidenData.getText()));
                dibujarProye();
                descVid.setVisible(false);
                descVid.dispose();
            } catch (SQLException ex) {
                System.out.println("Error al liberar videoproyector: " + ex);
            }
            valido = false;
        }
    }//GEN-LAST:event_btnMantActionPerformed

    private void btnComprobarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnComprobarActionPerformed
        String usuario = txtUsuario.getText().trim();
        try {
            UsuarioReadDB leer = new UsuarioReadDB();
            if (leer.getExisteUsuario(usuario)) {
                String hashed = leer.getPass(usuario);
                if (BCrypt.checkpw(String.valueOf(txtPass.getPassword()), hashed)) {
                    valido = true;
                    txtUsuario.setText("");
                    txtPass.setText("");
                    dlgConfirm.setVisible(false);
                    dlgConfirm.dispose();
                }else{
                    throw new Exception();
                }
            }else{
                throw new Exception();
            }
        }catch(SQLException ex){
            System.out.println("Error al comprobar usuario:" + ex);
        }catch(Exception e){
            txtUsuario.setText("");
            txtPass.setText("");
            valido = false;
            JOptionPane.showMessageDialog(null,"Compruebe los datos ingresados");
        }
    }//GEN-LAST:event_btnComprobarActionPerformed

    private void txtUsuarioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUsuarioKeyTyped
        if (txtUsuario.getText().length() >= 15) {
            evt.consume();
        }
        if (evt.getKeyChar() == KeyEvent.VK_ENTER) {
            btnComprobar.doClick();
        }
    }//GEN-LAST:event_txtUsuarioKeyTyped

    private void txtPassKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPassKeyTyped
        if (txtPass.getPassword().length >= 12) {
            evt.consume();
        }
        if (evt.getKeyChar() == KeyEvent.VK_ENTER) {
            btnComprobar.doClick();
        }
    }//GEN-LAST:event_txtPassKeyTyped

    private void txtTituloKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTituloKeyTyped
        if (txtTitulo.getText().length() >= 50) {
            evt.consume();
        }
    }//GEN-LAST:event_txtTituloKeyTyped

    private void txtDetallesKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDetallesKeyTyped
        if (txtDetalles.getText().length() >= 450) {
            evt.consume();
        }
    }//GEN-LAST:event_txtDetallesKeyTyped

    public int[] formatFecha(String date) {
        int[] res = new int[5];
        res[0] = Integer.parseInt(date.substring(0, 4));              //año
        res[1] = Integer.parseInt(date.substring(5, 7)) - 1;         //mes
        res[2] = Integer.parseInt(date.substring(8, 10));            //dia
        res[3] = Integer.parseInt(date.substring(11, 13));        //hora
        res[4] = Integer.parseInt(date.substring(14, 16));       //minuto
        return res;
    }
    
    public void dibujarProye() throws SQLException {
        VideoproyectorDB proy = new VideoproyectorDB();
        String[][] datos = proy.getProyectores();
        
        pnlContenedor.removeAll();
        pnlContenedor.repaint();
        
        for (String[] dato : datos) {
            miPanel(dato);
        }
        
        //acomodar componentes
        System.out.println("Dibujar cada videoProyector");
        int comp = pnlContenedor.getComponentCount();
        float filas = (comp / 4) + (comp % 4 == 0 ? 0 : 1);
        int altura = (int) (251 * filas); //la altura de cada jPane mas el espacio extra por la cantidad de elementos
        pnlContenedor.setPreferredSize(new Dimension(815,altura));
    }

    public void miPanel(String[] datos) {
        JLabel lblVideoNombreComercial = new JLabel();
        JLabel lblFechaExistencia = new JLabel();
        JTextField txtNomC = new JTextField();
        JLabel lblExis = new JLabel();
        JPanel nomVid = new JPanel();
        JLabel lblNombre = new JLabel();
        JPanel noSerie = new JPanel();
        JLabel lblSerie = new JLabel();
        JButton bot = new JButton();
        JPanel anon = new JPanel();

        anon.setBackground(new Color(230, 232, 234));
        anon.setMinimumSize(new Dimension(195, 251));
        anon.setPreferredSize(new Dimension(195, 251));
        anon.setLayout(new GridBagLayout());

        lblVideoNombreComercial.setFont(new Font("SansSerif", 1, 10)); // NOI18N
        lblVideoNombreComercial.setText("Videoproyector:");
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 1;
        c.anchor = GridBagConstraints.SOUTHWEST;
        c.insets = new Insets(18, 10, 0, 0);
        anon.add(lblVideoNombreComercial, c);

        lblFechaExistencia.setFont(new Font("SansSerif", 1, 10)); // NOI18N
        lblFechaExistencia.setText("Agregado desde:");
        c.gridx = 0;
        c.gridy = 3;
        c.anchor = GridBagConstraints.SOUTHWEST;
        c.insets = new Insets(18, 10, 0, 0);
        anon.add(lblFechaExistencia, c);

        txtNomC.setEditable(false);
        txtNomC.setBackground(new Color(230, 232, 234));
        txtNomC.setHorizontalAlignment(JTextField.CENTER);
        txtNomC.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        txtNomC.setOpaque(false);
        c.gridx = 0;
        c.gridy = 2;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(11, 0, 0, 0);
        anon.add(txtNomC, c);

        lblExis.setFont(new Font("SansSerif", 0, 11)); // NOI18N
        lblExis.setHorizontalAlignment(SwingConstants.CENTER);
        c.gridx = 0;
        c.gridy = 4;
        c.insets = new Insets(11, 0, 0, 0);
        anon.add(lblExis, c);
        try {
            VideoproyectorDB vid = new VideoproyectorDB();
            String[] arr = vid.showMeEvStatus(datos[0]);
            if(arr[2].equals("MANTENIMIENTO") && !Boolean.valueOf(arr[3])){
                nomVid.setBackground(new Color(153, 151, 149));
            }else if(arr[2].equals("EN PRESTAMO") && !Boolean.valueOf(arr[3])){
                nomVid.setBackground(new Color(38, 198, 218));
            }else{
                nomVid.setBackground(new Color(255, 140, 0));              
            }
        } catch (SQLException ex) {
            System.out.println("Error al colorear cabecera de acuerdo a disponibilidad"+ ex);
        }
        nomVid.setMinimumSize(new Dimension(200, 40));
        nomVid.setPreferredSize(new Dimension(200, 40));
        nomVid.setLayout(new GridBagLayout());

        lblNombre.setFont(new Font("SansSerif", 1, 14)); // NOI18N
        lblNombre.setForeground(new Color(255, 255, 255));
        lblNombre.setHorizontalAlignment(SwingConstants.CENTER);
        lblNombre.setMaximumSize(new Dimension(100, 20));
        lblNombre.setMinimumSize(new Dimension(100, 20));
        lblNombre.setPreferredSize(new Dimension(100, 20));
        c.gridx = 0;
        c.gridy = 0;
        nomVid.add(lblNombre, c);

        c.gridx = 0;
        c.gridy = 0;
        anon.add(nomVid, c);

        noSerie.setBackground(new Color(50,205,50));
        noSerie.setMinimumSize(new Dimension(200, 40));
        noSerie.setPreferredSize(new Dimension(200, 40));
        noSerie.setLayout(new GridBagLayout());

        lblSerie.setFont(new Font("SansSerif", 1, 12)); // NOI18N
        lblSerie.setForeground(new Color(35, 62, 15));
        lblSerie.setHorizontalAlignment(SwingConstants.CENTER);
        lblSerie.setMaximumSize(new Dimension(190, 20));
        lblSerie.setMinimumSize(new Dimension(190, 20));
        lblSerie.setPreferredSize(new Dimension(190, 20));
        c.gridx = 0;
        c.gridy = 0;
        noSerie.add(lblSerie, c);

        c.gridx = 0;
        c.gridy = 5;
        c.insets = new Insets(9, 0, 4, 0);
        anon.add(noSerie, c);

        bot.setFont(new Font("SansSerif", 0, 14)); // NOI18N
        bot.setText("Detalles");
        c.gridx = 0;
        c.gridy = 6;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(5, 15, 9, 15);
        anon.add(bot, c);

        lblNombre.setText(datos[1]);
        txtNomC.setText(datos[2] + " " + datos[3]);
        lblExis.setText(datos[5].substring(0, 10));
        lblSerie.setText(datos[4]);
        
        bot.addActionListener((ActionEvent e) -> {
            try {
                VideoproyectorDB proy = new VideoproyectorDB();
                String[] info = proy.getProyector(datos[4]);
                String[] estatus = proy.showMeEvStatus(datos[0]);
                
                System.out.println("::dialog ya dibujado " + datos[4] + " : ID " + datos[0]);
                System.out.println("estatus:" + (Arrays.toString(estatus)));
                
                hidenData.setText(datos[0]); //para uso del boton eliminar y actualizar Es el ID
                lblData1.setText(info[1]);
                lblData2.setText(info[5].substring(0, 16));
                lblData4.setText(info[4]);
                lblStatus.setText(estatus[2]);
                try{
                PrestamoDB misPrestamos = new PrestamoDB();
                misPrestamos.setProyectorServicio(datos[0]);
                int[] servicio = misPrestamos.getProyectorServicio(datos[0]);
                lblServTotal.setText(servicioFormat(servicio[2]));
                lblServMes.setText(servicioFormat(servicio[3]));
                lblServSem.setText(servicioFormat(servicio[4]));
                }catch(SQLException ex){ System.out.println("Error al generar datos de prestamoDB sobre videoproyector btn bot jpanel generado dinamicamente:" + ex);}
                
                if((lblStatus.getText()).equals("MANTENIMIENTO")){
                    btnMant.setVisible(true);
                }else{
                    btnMant.setVisible(false);
                }
                
                descVid.setLocationRelativeTo(pnlBkVid);
                descVid.setVisible(true);
            } catch (SQLException ex) {
                System.out.println("Error al ejecutar accion boton detalles Videoproyector: " + ex);
                ex.printStackTrace();
            }
        });
        
        anon.revalidate();
        anon.repaint();
        pnlContenedor.add(anon);
        pnlContenedor.revalidate();   
    }

    public String servicioFormat(int servicio){
        String presente = "";
                if (servicio < 59){
                    presente = servicio + "min";
                }else{// if (servicio < 1439){
                    int hrs = (servicio / 60);
                    int min = servicio - (hrs * 60);
                    presente = hrs + " hrs " + min + " min ";
//                }else{
//                      int dias = (servicio / 1440); 
//                      int minRest = servicio - (1440 * dias);
//                      int horas = minRest / 60;
//                      int minutos = minRest - (60 * horas);
//                      presente = dias + "dia(s) " + horas + "hr(s) " + minutos + "min ";
                }
        return presente;
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
            java.util.logging.Logger.getLogger(Videoproyector.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            try {
                new Videoproyector().setVisible(true);
            } catch (SQLException ex) {
                Logger.getLogger(Videoproyector.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDialog articulos;
    private javax.swing.JPanel barraSeparadora;
    private javax.swing.JPanel barraSeparadora1;
    private javax.swing.JPanel barraSeparadora2;
    private javax.swing.JPanel barraSeparadora3;
    private javax.swing.JButton btnActualizar1;
    private javax.swing.JButton btnActualizar2;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnCerrar1;
    private javax.swing.JButton btnCerrar2;
    private javax.swing.JButton btnCerrar3;
    private javax.swing.JButton btnComprobar;
    private javax.swing.JButton btnEliminarVid1;
    private javax.swing.ButtonGroup btnGrImprevisto;
    private javax.swing.ButtonGroup btnGrSolicitudPara;
    private javax.swing.JButton btnGuardar1;
    private javax.swing.JButton btnMant;
    private javax.swing.JToggleButton btnMenu;
    private javax.swing.JPanel btnPnlArticulos;
    private javax.swing.JPanel btnPnlCodebar;
    private javax.swing.JPanel btnPnlCrear;
    private javax.swing.JPanel btnPnlGaf;
    private javax.swing.JPanel btnPnlReportar;
    private javax.swing.JButton btnRegresar;
    private javax.swing.JButton btnReporte;
    private javax.swing.JCheckBox cb1;
    private javax.swing.JCheckBox cb2;
    private javax.swing.JCheckBox cb3;
    private javax.swing.JCheckBox cb4;
    private javax.swing.JCheckBox cb5;
    private javax.swing.JCheckBox cb6;
    private javax.swing.JCheckBox cb7;
    private javax.swing.JDialog crearVid;
    private javax.swing.JDialog descVid;
    private javax.swing.JDialog dlgConfirm;
    private javax.swing.JDialog dlgServicio;
    private javax.swing.JDialog fallosReporte;
    private javax.swing.JLabel hiddenLabelArt;
    private javax.swing.JLabel hiddenNSerie;
    private javax.swing.JLabel hidenData;
    private javax.swing.JLabel hidenData2;
    private javax.swing.JLabel ico3;
    private javax.swing.JLabel ico4;
    private javax.swing.JLabel ico5;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLayeredPane jLayeredPane2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTable4;
    private javax.swing.JTable jTable5;
    private javax.swing.JLabel labelFecha;
    private javax.swing.JLabel labelHora;
    private javax.swing.JLabel lblCreadHead;
    private javax.swing.JLabel lblData1;
    private javax.swing.JLabel lblData2;
    private javax.swing.JLabel lblData4;
    private javax.swing.JLabel lblIco0;
    private javax.swing.JLabel lblIco1;
    private javax.swing.JLabel lblIco10;
    private javax.swing.JLabel lblIco11;
    private javax.swing.JLabel lblIco12;
    private javax.swing.JLabel lblIco13;
    private javax.swing.JLabel lblIco14;
    private javax.swing.JLabel lblIco2;
    private javax.swing.JLabel lblIco3;
    private javax.swing.JLabel lblIco4;
    private javax.swing.JLabel lblIco5;
    private javax.swing.JLabel lblIco6;
    private javax.swing.JLabel lblIco7;
    private javax.swing.JLabel lblIco8;
    private javax.swing.JLabel lblIco9;
    private javax.swing.JLabel lblIcoInfo;
    private javax.swing.JLabel lblNomHead;
    private javax.swing.JLabel lblPanelTitulo;
    private javax.swing.JLabel lblRprt6;
    private javax.swing.JLabel lblSerieHead;
    private javax.swing.JLabel lblServMes;
    private javax.swing.JLabel lblServSem;
    private javax.swing.JLabel lblServTotal;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JLabel lblTitulo1;
    private javax.swing.JLabel lblTitulo2;
    private javax.swing.JLabel lblTitulo3;
    private javax.swing.JLabel lblTituloArt;
    private javax.swing.JLabel lblTituloArt1;
    private javax.swing.JLabel lblTituloArt2;
    private javax.swing.JLabel lblTituloArt3;
    private javax.swing.JLabel lblTituloArt4;
    private javax.swing.JPanel panelOPC;
    private javax.swing.JPanel pnlActualizar;
    private javax.swing.JPanel pnlAddArt;
    private javax.swing.JPanel pnlAllArt;
    private javax.swing.JPanel pnlBackground1;
    private javax.swing.JPanel pnlBkVid;
    private javax.swing.JPanel pnlCabecera;
    private javax.swing.JPanel pnlColor1;
    private javax.swing.JPanel pnlColor2;
    private javax.swing.JPanel pnlColor4;
    private javax.swing.JPanel pnlColor5;
    private javax.swing.JPanel pnlColor6;
    private javax.swing.JPanel pnlColor7;
    private javax.swing.JPanel pnlContenedor;
    private javax.swing.JPanel pnlDetalles;
    private javax.swing.JPanel pnlDetalles1;
    private javax.swing.JPanel pnlFormulario;
    private javax.swing.JPanel pnlFormulario1;
    private javax.swing.JPanel pnlModArt;
    private javax.swing.JPanel pnlRegArt;
    private javax.swing.JPanel pnlRptArt;
    private javax.swing.JTabbedPane tabbedPane;
    private javax.swing.JToggleButton tglReg;
    private javax.swing.JTextField txtArea;
    private javax.swing.JTextArea txtDesc;
    private javax.swing.JTextArea txtDesc1;
    private javax.swing.JTextArea txtDetalles;
    private javax.swing.JTextField txtExist;
    private javax.swing.JTextField txtExist1;
    private javax.swing.JTextField txtMarc1;
    private javax.swing.JTextField txtMarc2;
    private javax.swing.JTextField txtMod1;
    private javax.swing.JTextField txtMod2;
    private javax.swing.JTextField txtNom;
    private javax.swing.JTextField txtNom1;
    private javax.swing.JTextField txtNom2;
    private javax.swing.JTextField txtNom3;
    private javax.swing.JPasswordField txtPass;
    private javax.swing.JTextField txtSer1;
    private javax.swing.JTextField txtSer2;
    private javax.swing.JTextField txtSolicitante;
    private javax.swing.JTextField txtTitulo;
    private javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables
}
