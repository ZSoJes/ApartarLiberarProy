/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyector;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import org.mindrot.jbcrypt.BCrypt;
import proyector.dataBase.crud.ArticuloDB;
import proyector.dataBase.crud.UsuarioReadDB;

/**
 *
 * @author JuanGS
 */
public class Articulo extends javax.swing.JFrame {

    private static Boolean valido = false;
    String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
    ImageIcon img = new ImageIcon("./src/imagenes/logo-adm.png");
    ImageIcon addW = new ImageIcon("./src/imagenes/Add New_36pxW.png");
    ImageIcon ersW = new ImageIcon("./src/imagenes/Erase_36pxW.png");
    ImageIcon updW = new ImageIcon("./src/imagenes/Edit File_36pxW.png");
    ImageIcon add = new ImageIcon("./src/imagenes/Add New_36px.png");
    ImageIcon ers = new ImageIcon("./src/imagenes/Erase_36px.png");
    ImageIcon upd = new ImageIcon("./src/imagenes/Edit File_36px.png");

    /**
     * Creates new form Articulo
     */
    public Articulo() {
        initComponents();

        hiddenIDPrestArt.setVisible(false);
        labelFecha.setText(date);
        Timer timer = new Timer(500, (ActionEvent e) -> {
            reloj();                                //coloca la hora
        });
        timer.setRepeats(true);
        timer.setCoalesce(true);
        timer.setInitialDelay(0);
        timer.start();

        pnlActArt.setVisible(false);
        lblHiddenID.setVisible(false);
        lblhiddenNom.setVisible(false);
        pnlTodos.setVisible(false);
        panelOPC.setVisible(false);
        //cargar los articulos existentes
        try {
            getTable();
        } catch (SQLException e) {
            System.out.println("Error al cargar los articulos existentes:" + e);
        }
        try {
            getPrestadosHOY();
        } catch (SQLException e) {
            System.out.println("Error al cargar los articulos prestados hoy:" + e);
        }
    }

    public void reloj() {
        labelHora.setText(new SimpleDateFormat("hh:mm:ss a").format(new Date()));
    }

    public void getTable() throws SQLException {
        DefaultTableModel model = (DefaultTableModel) tableArticulos.getModel();
        ArticuloDB acc = new ArticuloDB();

        String[] cols = {tableArticulos.getColumnName(0), tableArticulos.getColumnName(1), tableArticulos.getColumnName(2)};
        int count = acc.getCantArticulos(true);
        System.out.println("\nArticulos existentes: " + count);

        String[][] datos = acc.getArticulos(false);
        String art[][] = new String[count][3];

        for (int i = 0; i < count; i++) {
            for (int j = 0; j < 3; j++) {
                art[i][j] = datos[i][j];
            }
        }
        model.setDataVector(art, cols);
    }

    public void getPrestadosHOY() throws SQLException {
        DefaultTableModel model5 = (DefaultTableModel) jTable5.getModel();
        ArticuloDB acc = new ArticuloDB();
        String[] cols = {jTable5.getColumnName(0), jTable5.getColumnName(1), jTable5.getColumnName(2), jTable5.getColumnName(3), jTable5.getColumnName(4)};

        Calendar c = Calendar.getInstance();
        String fechaUno = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
        c.add(Calendar.DATE, 1);
        String fechaDos = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
        System.out.println("Tabla con Fecha de Hoy: " + fechaUno + " : : " + fechaDos);

        String[][] datos = acc.getRegArticulos(true, fechaUno.concat(" 00:00:00"), fechaDos.concat(" 00:00:00"));
        int count = datos.length;
        System.out.println("\nRegistros articulos existentes: " + count);

        jTable5.getColumnModel().getColumn(0).setPreferredWidth(30);
        jTable5.getColumnModel().getColumn(1).setPreferredWidth(295);
        jTable5.getColumnModel().getColumn(2).setPreferredWidth(295);
        jTable5.getColumnModel().getColumn(3).setPreferredWidth(40);
        jTable5.getColumnModel().getColumn(4).setPreferredWidth(90);
        jTable5.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        model5.setDataVector(datos, cols);
    }

    public void getPrestadosAcc() throws SQLException {
        DefaultTableModel modelReg = (DefaultTableModel) jTable3.getModel();
        ArticuloDB acc = new ArticuloDB();
        String[] cols = {jTable3.getColumnName(0), jTable3.getColumnName(1), jTable3.getColumnName(2), jTable3.getColumnName(3), jTable3.getColumnName(4)};

        String[][] datos = acc.getRegArticulos(false, "", "");
        int count = datos.length;
        System.out.println("\nRegistros articulos existentes: " + count);

        jTable3.getColumnModel().getColumn(0).setPreferredWidth(30);
        jTable3.getColumnModel().getColumn(1).setPreferredWidth(295);
        jTable3.getColumnModel().getColumn(2).setPreferredWidth(295);
        jTable3.getColumnModel().getColumn(3).setPreferredWidth(40);
        jTable3.getColumnModel().getColumn(4).setPreferredWidth(90);
        jTable3.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        modelReg.setDataVector(datos, cols);
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

        dlgNuevo = new javax.swing.JDialog();
        jPanel1 = new javax.swing.JPanel();
        btnCerrar = new javax.swing.JButton();
        txtNom = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txtExist = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDesc = new javax.swing.JTextArea();
        pnlColor4 = new javax.swing.JPanel();
        pnlColor5 = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JButton();
        dlgActualizar = new javax.swing.JDialog();
        jPanel3 = new javax.swing.JPanel();
        btnCerrar2 = new javax.swing.JButton();
        pnlColor3 = new javax.swing.JPanel();
        pnlColor6 = new javax.swing.JPanel();
        pnlColor7 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        pnlBuscarArt = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        btnBuscar = new javax.swing.JButton();
        txtIDArt = new javax.swing.JTextField();
        pnlActArt = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        txtNomA = new javax.swing.JTextField();
        txtExtA = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtDescA = new javax.swing.JTextArea();
        lblHiddenID = new javax.swing.JLabel();
        lblhiddenNom = new javax.swing.JLabel();
        btnActualizar = new javax.swing.JButton();
        dlgBorrar = new javax.swing.JDialog();
        jPanel2 = new javax.swing.JPanel();
        btnCerrar1 = new javax.swing.JButton();
        pnlColor1 = new javax.swing.JPanel();
        pnlColor2 = new javax.swing.JPanel();
        btnBorrar = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtBorrar = new javax.swing.JTextField();
        dlgConfirm = new javax.swing.JDialog();
        jPanel8 = new javax.swing.JPanel();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        btnComprobar = new javax.swing.JButton();
        jLabel43 = new javax.swing.JLabel();
        txtUsuario = new javax.swing.JTextField();
        txtPass = new javax.swing.JPasswordField();
        dlgReporte = new javax.swing.JDialog();
        bkRArt = new javax.swing.JPanel();
        lblTituloR = new javax.swing.JLabel();
        btnCerrarR = new javax.swing.JLabel();
        lblInfoR = new javax.swing.JLabel();
        lyrCrearReporte = new javax.swing.JLayeredPane();
        fechaArtPrestamo = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        fechaPrestamo = new com.toedter.calendar.JDateChooser();
        btnC = new javax.swing.JButton();
        listaPrestamos = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        articulosPrestados = new javax.swing.JScrollPane();
        listaArt = new javax.swing.JTable();
        btnC1 = new javax.swing.JButton();
        pnlFormularioR = new javax.swing.JPanel();
        btnGenR = new javax.swing.JButton();
        btnClearR = new javax.swing.JButton();
        jLabel30 = new javax.swing.JLabel();
        txtResum = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        txtProfNom = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        txtDetalles = new javax.swing.JTextArea();
        jLabel24 = new javax.swing.JLabel();
        lblArticuloR = new javax.swing.JLabel();
        hiddenIDPrestArt = new javax.swing.JLabel();
        rdComp = new javax.swing.JRadioButton();
        rdComp1 = new javax.swing.JRadioButton();
        rdComp2 = new javax.swing.JRadioButton();
        bkArticulos = new javax.swing.JPanel();
        pnlCabecera = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        labelHora = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        labelFecha = new javax.swing.JLabel();
        panelOPC = new javax.swing.JPanel();
        ico3 = new javax.swing.JLabel();
        ico4 = new javax.swing.JLabel();
        ico5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        btnRegresar = new javax.swing.JButton();
        btnMenu = new javax.swing.JToggleButton();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableArticulos = new javax.swing.JTable();
        pnlBtnNuevo = new javax.swing.JPanel();
        ico1 = new javax.swing.JLabel();
        lbl1 = new javax.swing.JLabel();
        lblN1 = new javax.swing.JLabel();
        pnlBtnBorrar = new javax.swing.JPanel();
        ico2 = new javax.swing.JLabel();
        lbl2 = new javax.swing.JLabel();
        lblN2 = new javax.swing.JLabel();
        pnlBtnActualizar = new javax.swing.JPanel();
        ico6 = new javax.swing.JLabel();
        lbl3 = new javax.swing.JLabel();
        lblN3 = new javax.swing.JLabel();
        pnlBtnReportar = new javax.swing.JPanel();
        ico7 = new javax.swing.JLabel();
        lbl4 = new javax.swing.JLabel();
        lblN4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        tglReg = new javax.swing.JToggleButton();
        jLayeredPane2 = new javax.swing.JLayeredPane();
        pnlHoy = new javax.swing.JScrollPane();
        jTable5 = new javax.swing.JTable();
        pnlTodos = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();

        dlgNuevo.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        dlgNuevo.setMinimumSize(new java.awt.Dimension(630, 380));
        dlgNuevo.setModal(true);
        dlgNuevo.setUndecorated(true);
        dlgNuevo.setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setMinimumSize(new java.awt.Dimension(630, 380));
        jPanel1.setPreferredSize(new java.awt.Dimension(630, 380));

        btnCerrar.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        btnCerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Close_22px.png"))); // NOI18N
        btnCerrar.setText("Cerrar");
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });

        txtNom.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtNom.setPreferredSize(new java.awt.Dimension(250, 30));
        txtNom.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNomKeyTyped(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel15.setText("Nombre");

        jLabel16.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel16.setText("Existencias");

        txtExist.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtExist.setPreferredSize(new java.awt.Dimension(125, 30));
        txtExist.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtExistKeyTyped(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel17.setText("Descripción");

        jLabel10.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        jLabel10.setText("Nuevo Artículo");

        txtDesc.setColumns(20);
        txtDesc.setLineWrap(true);
        txtDesc.setRows(5);
        txtDesc.setWrapStyleWord(true);
        txtDesc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDescKeyTyped(evt);
            }
        });
        jScrollPane1.setViewportView(txtDesc);

        pnlColor4.setBackground(new java.awt.Color(1, 200, 1));

        javax.swing.GroupLayout pnlColor4Layout = new javax.swing.GroupLayout(pnlColor4);
        pnlColor4.setLayout(pnlColor4Layout);
        pnlColor4Layout.setHorizontalGroup(
            pnlColor4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 70, Short.MAX_VALUE)
        );
        pnlColor4Layout.setVerticalGroup(
            pnlColor4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        pnlColor5.setBackground(new java.awt.Color(12, 193, 243));

        javax.swing.GroupLayout pnlColor5Layout = new javax.swing.GroupLayout(pnlColor5);
        pnlColor5.setLayout(pnlColor5Layout);
        pnlColor5Layout.setHorizontalGroup(
            pnlColor5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 32, Short.MAX_VALUE)
        );
        pnlColor5Layout.setVerticalGroup(
            pnlColor5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Save_22px.png"))); // NOI18N
        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(pnlColor4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(pnlColor5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(117, 117, 117)
                                .addComponent(jLabel10))
                            .addComponent(jLabel17)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel16)
                                .addGap(18, 18, 18)
                                .addComponent(txtExist, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addGap(43, 43, 43)
                                .addComponent(txtNom, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(106, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(80, 80, 80)
                        .addComponent(btnGuardar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCerrar)
                        .addGap(133, 133, 133))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel10)
                .addGap(36, 36, 36)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(txtExist, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCerrar)
                    .addComponent(btnGuardar))
                .addGap(58, 58, 58))
            .addComponent(pnlColor4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(pnlColor5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout dlgNuevoLayout = new javax.swing.GroupLayout(dlgNuevo.getContentPane());
        dlgNuevo.getContentPane().setLayout(dlgNuevoLayout);
        dlgNuevoLayout.setHorizontalGroup(
            dlgNuevoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        dlgNuevoLayout.setVerticalGroup(
            dlgNuevoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        dlgActualizar.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        dlgActualizar.setMinimumSize(new java.awt.Dimension(660, 406));
        dlgActualizar.setModal(true);
        dlgActualizar.setUndecorated(true);
        dlgActualizar.setResizable(false);

        jPanel3.setBackground(new java.awt.Color(0, 191, 255));
        jPanel3.setMinimumSize(new java.awt.Dimension(660, 406));

        btnCerrar2.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnCerrar2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Close_22px.png"))); // NOI18N
        btnCerrar2.setText("Cerrar");
        btnCerrar2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrar2ActionPerformed(evt);
            }
        });

        pnlColor3.setBackground(new java.awt.Color(253, 184, 19));

        javax.swing.GroupLayout pnlColor3Layout = new javax.swing.GroupLayout(pnlColor3);
        pnlColor3.setLayout(pnlColor3Layout);
        pnlColor3Layout.setHorizontalGroup(
            pnlColor3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 70, Short.MAX_VALUE)
        );
        pnlColor3Layout.setVerticalGroup(
            pnlColor3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        pnlColor6.setBackground(new java.awt.Color(154, 205, 50));

        javax.swing.GroupLayout pnlColor6Layout = new javax.swing.GroupLayout(pnlColor6);
        pnlColor6.setLayout(pnlColor6Layout);
        pnlColor6Layout.setHorizontalGroup(
            pnlColor6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 31, Short.MAX_VALUE)
        );
        pnlColor6Layout.setVerticalGroup(
            pnlColor6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        pnlColor7.setBackground(new java.awt.Color(1, 200, 1));

        javax.swing.GroupLayout pnlColor7Layout = new javax.swing.GroupLayout(pnlColor7);
        pnlColor7.setLayout(pnlColor7Layout);
        pnlColor7Layout.setHorizontalGroup(
            pnlColor7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 15, Short.MAX_VALUE)
        );
        pnlColor7Layout.setVerticalGroup(
            pnlColor7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jLabel13.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Actualizar Artículo");

        pnlBuscarArt.setBackground(new java.awt.Color(0, 191, 255));

        jLabel14.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Numero Identificador");

        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Pencil_22px.png"))); // NOI18N
        btnBuscar.setText("Actualizar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        txtIDArt.setMinimumSize(new java.awt.Dimension(225, 30));
        txtIDArt.setPreferredSize(new java.awt.Dimension(225, 30));
        txtIDArt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtIDArtKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout pnlBuscarArtLayout = new javax.swing.GroupLayout(pnlBuscarArt);
        pnlBuscarArt.setLayout(pnlBuscarArtLayout);
        pnlBuscarArtLayout.setHorizontalGroup(
            pnlBuscarArtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBuscarArtLayout.createSequentialGroup()
                .addGroup(pnlBuscarArtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlBuscarArtLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jLabel14))
                    .addGroup(pnlBuscarArtLayout.createSequentialGroup()
                        .addGap(95, 95, 95)
                        .addComponent(btnBuscar)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlBuscarArtLayout.createSequentialGroup()
                .addGap(0, 41, Short.MAX_VALUE)
                .addComponent(txtIDArt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39))
        );
        pnlBuscarArtLayout.setVerticalGroup(
            pnlBuscarArtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBuscarArtLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14)
                .addGap(18, 18, 18)
                .addComponent(txtIDArt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(btnBuscar)
                .addGap(19, 19, 19))
        );

        pnlActArt.setBackground(new java.awt.Color(0, 191, 255));

        jLabel18.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("Nombre");

        jLabel19.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Existencias");

        jLabel20.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("Descripción");

        txtNomA.setMinimumSize(new java.awt.Dimension(290, 27));
        txtNomA.setPreferredSize(new java.awt.Dimension(290, 27));
        txtNomA.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNomAKeyTyped(evt);
            }
        });

        txtExtA.setMinimumSize(new java.awt.Dimension(290, 27));
        txtExtA.setPreferredSize(new java.awt.Dimension(290, 27));
        txtExtA.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtExtAKeyTyped(evt);
            }
        });

        txtDescA.setColumns(20);
        txtDescA.setLineWrap(true);
        txtDescA.setRows(5);
        txtDescA.setWrapStyleWord(true);
        txtDescA.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDescAKeyTyped(evt);
            }
        });
        jScrollPane2.setViewportView(txtDescA);

        lblHiddenID.setFont(new java.awt.Font("Tahoma", 0, 5)); // NOI18N
        lblHiddenID.setText("SoyID");

        lblhiddenNom.setFont(new java.awt.Font("Tahoma", 0, 5)); // NOI18N
        lblhiddenNom.setText("hiddenNom");

        javax.swing.GroupLayout pnlActArtLayout = new javax.swing.GroupLayout(pnlActArt);
        pnlActArt.setLayout(pnlActArtLayout);
        pnlActArtLayout.setHorizontalGroup(
            pnlActArtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlActArtLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(pnlActArtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlActArtLayout.createSequentialGroup()
                        .addGroup(pnlActArtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel20)
                            .addComponent(jLabel19)
                            .addGroup(pnlActArtLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(pnlActArtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtExtA, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtNomA, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jScrollPane2))))
                        .addContainerGap(25, Short.MAX_VALUE))
                    .addGroup(pnlActArtLayout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblhiddenNom)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblHiddenID)
                        .addGap(46, 46, 46))))
        );
        pnlActArtLayout.setVerticalGroup(
            pnlActArtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlActArtLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlActArtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel18)
                    .addGroup(pnlActArtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblHiddenID)
                        .addComponent(lblhiddenNom)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtNomA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(jLabel19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtExtA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLayeredPane1.setLayer(pnlBuscarArt, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(pnlActArt, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(pnlBuscarArt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(35, Short.MAX_VALUE))
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane1Layout.createSequentialGroup()
                    .addContainerGap(21, Short.MAX_VALUE)
                    .addComponent(pnlActArt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane1Layout.createSequentialGroup()
                .addContainerGap(90, Short.MAX_VALUE)
                .addComponent(pnlBuscarArt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(73, 73, 73))
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(pnlActArt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        btnActualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Pencil_22px.png"))); // NOI18N
        btnActualizar.setText("Actualizar");
        btnActualizar.setEnabled(false);
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addComponent(pnlColor3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlColor6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pnlColor7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(120, 120, 120)
                        .addComponent(jLabel13))
                    .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addComponent(btnActualizar)
                        .addGap(89, 89, 89)
                        .addComponent(btnCerrar2)))
                .addContainerGap(110, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnActualizar)
                    .addComponent(btnCerrar2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(pnlColor3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnlColor6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnlColor7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout dlgActualizarLayout = new javax.swing.GroupLayout(dlgActualizar.getContentPane());
        dlgActualizar.getContentPane().setLayout(dlgActualizarLayout);
        dlgActualizarLayout.setHorizontalGroup(
            dlgActualizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        dlgActualizarLayout.setVerticalGroup(
            dlgActualizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        dlgBorrar.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        dlgBorrar.setMinimumSize(new java.awt.Dimension(550, 270));
        dlgBorrar.setModal(true);
        dlgBorrar.setUndecorated(true);
        dlgBorrar.setResizable(false);

        jPanel2.setBackground(new java.awt.Color(255, 127, 80));
        jPanel2.setMinimumSize(new java.awt.Dimension(550, 270));

        btnCerrar1.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        btnCerrar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Close_22px.png"))); // NOI18N
        btnCerrar1.setText("Cerrar");
        btnCerrar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrar1ActionPerformed(evt);
            }
        });

        pnlColor1.setBackground(new java.awt.Color(12, 193, 243));

        javax.swing.GroupLayout pnlColor1Layout = new javax.swing.GroupLayout(pnlColor1);
        pnlColor1.setLayout(pnlColor1Layout);
        pnlColor1Layout.setHorizontalGroup(
            pnlColor1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 70, Short.MAX_VALUE)
        );
        pnlColor1Layout.setVerticalGroup(
            pnlColor1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        pnlColor2.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout pnlColor2Layout = new javax.swing.GroupLayout(pnlColor2);
        pnlColor2.setLayout(pnlColor2Layout);
        pnlColor2Layout.setHorizontalGroup(
            pnlColor2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 31, Short.MAX_VALUE)
        );
        pnlColor2Layout.setVerticalGroup(
            pnlColor2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        btnBorrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Waste_24px.png"))); // NOI18N
        btnBorrar.setText("Eliminar");
        btnBorrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBorrarActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Borrar Artículo");

        jLabel12.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Número Identificador");

        txtBorrar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtBorrarKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(pnlColor1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pnlColor2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(136, 136, 136)
                        .addComponent(jLabel11))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtBorrar, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addGap(142, 142, 142))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addComponent(btnBorrar)
                        .addGap(100, 100, 100)
                        .addComponent(btnCerrar1)))
                .addContainerGap(97, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlColor1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnlColor2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(jLabel11)
                .addGap(40, 40, 40)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtBorrar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBorrar)
                    .addComponent(btnCerrar1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(46, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout dlgBorrarLayout = new javax.swing.GroupLayout(dlgBorrar.getContentPane());
        dlgBorrar.getContentPane().setLayout(dlgBorrarLayout);
        dlgBorrarLayout.setHorizontalGroup(
            dlgBorrarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        dlgBorrarLayout.setVerticalGroup(
            dlgBorrarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        dlgConfirm.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        dlgConfirm.setTitle("[Confirmar Acción]");
        dlgConfirm.setMinimumSize(new java.awt.Dimension(400, 300));
        dlgConfirm.setModal(true);

        jPanel8.setBackground(new java.awt.Color(255, 183, 77));

        jLabel40.setFont(new java.awt.Font("Trebuchet MS", 0, 24)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(255, 255, 255));
        jLabel40.setText("<html>Esta acción necesita su<br><b>Usuario</b> y <b>Contraseña</b></html>");

        jLabel41.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(255, 255, 255));
        jLabel41.setText("Usuario:");

        jLabel42.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(255, 255, 255));
        jLabel42.setText("Contraseña:");

        btnComprobar.setText("Validar Acción");
        btnComprobar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnComprobarActionPerformed(evt);
            }
        });

        jLabel43.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/niceGuy-60.png"))); // NOI18N

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

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel40, javax.swing.GroupLayout.DEFAULT_SIZE, 302, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel43)
                .addGap(18, 18, 18))
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel41)
                    .addComponent(jLabel42))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(txtUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtPass, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(142, 142, 142)
                .addComponent(btnComprobar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel43))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel41)
                    .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel42)
                    .addComponent(txtPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnComprobar)
                .addGap(19, 19, 19))
        );

        javax.swing.GroupLayout dlgConfirmLayout = new javax.swing.GroupLayout(dlgConfirm.getContentPane());
        dlgConfirm.getContentPane().setLayout(dlgConfirmLayout);
        dlgConfirmLayout.setHorizontalGroup(
            dlgConfirmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        dlgConfirmLayout.setVerticalGroup(
            dlgConfirmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        dlgReporte.setMinimumSize(new java.awt.Dimension(830, 470));
        dlgReporte.setModal(true);
        dlgReporte.setUndecorated(true);
        dlgReporte.setResizable(false);

        bkRArt.setBackground(new java.awt.Color(255, 102, 102));
        bkRArt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 153, 153), 5));
        bkRArt.setMinimumSize(new java.awt.Dimension(830, 470));
        bkRArt.setPreferredSize(new java.awt.Dimension(830, 470));
        bkRArt.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTituloR.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        lblTituloR.setForeground(new java.awt.Color(255, 255, 255));
        lblTituloR.setText("Reporte de Artículo");
        bkRArt.add(lblTituloR, new org.netbeans.lib.awtextra.AbsoluteConstraints(307, 19, -1, -1));

        btnCerrarR.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Close_22px.png"))); // NOI18N
        btnCerrarR.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        btnCerrarR.setOpaque(true);
        btnCerrarR.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCerrarRMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnCerrarRMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnCerrarRMouseExited(evt);
            }
        });
        bkRArt.add(btnCerrarR, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 10, -1, -1));

        lblInfoR.setForeground(new java.awt.Color(255, 255, 255));
        lblInfoR.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblInfoR.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/More Info_22px.png"))); // NOI18N
        lblInfoR.setText("Para comenzar el reporte debe indicar la fecha en que fue prestado el Artículo");
        lblInfoR.setMaximumSize(new java.awt.Dimension(2147483647, 2147483647));
        bkRArt.add(lblInfoR, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 435, 830, 20));

        lyrCrearReporte.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        fechaArtPrestamo.setBackground(new java.awt.Color(255, 255, 255));
        fechaArtPrestamo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 3));
        fechaArtPrestamo.setMinimumSize(new java.awt.Dimension(500, 100));
        fechaArtPrestamo.setPreferredSize(new java.awt.Dimension(500, 100));
        fechaArtPrestamo.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel23.setText("Fecha del prestamo");
        fechaArtPrestamo.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));

        fechaPrestamo.setDateFormatString("yyyy-MM-dd");
        fechaArtPrestamo.add(fechaPrestamo, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 30, 150, -1));

        btnC.setText("Continuar");
        btnC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCActionPerformed(evt);
            }
        });
        fechaArtPrestamo.add(btnC, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 60, -1, -1));

        lyrCrearReporte.add(fechaArtPrestamo, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 120, 500, 100));

        listaPrestamos.setBackground(new java.awt.Color(255, 255, 255));
        listaPrestamos.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 3));
        listaPrestamos.setMinimumSize(new java.awt.Dimension(750, 230));
        listaPrestamos.setPreferredSize(new java.awt.Dimension(750, 230));
        listaPrestamos.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel22.setText("Seleccione un registro de la lista");
        listaPrestamos.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(13, 14, -1, -1));

        articulosPrestados.setPreferredSize(new java.awt.Dimension(700, 152));

        listaArt.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "Profesor", "Artículo", "Hora", "Fecha"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        listaArt.getTableHeader().setReorderingAllowed(false);
        articulosPrestados.setViewportView(listaArt);

        listaPrestamos.add(articulosPrestados, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 35, 700, 152));

        btnC1.setText("Continuar");
        btnC1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnC1ActionPerformed(evt);
            }
        });
        listaPrestamos.add(btnC1, new org.netbeans.lib.awtextra.AbsoluteConstraints(335, 193, -1, -1));

        lyrCrearReporte.add(listaPrestamos, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 55, 750, 230));

        pnlFormularioR.setBackground(new java.awt.Color(255, 255, 255));
        pnlFormularioR.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 3));
        pnlFormularioR.setPreferredSize(new java.awt.Dimension(710, 334));

        btnGenR.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/reportar-22px.png"))); // NOI18N
        btnGenR.setText("Generar Reporte");
        btnGenR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenRActionPerformed(evt);
            }
        });

        btnClearR.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Broom_24px.png"))); // NOI18N
        btnClearR.setText("Limpiar Formulario");
        btnClearR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearRActionPerformed(evt);
            }
        });

        jLabel30.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jLabel30.setText("Descripción detallado de lo sucedido a el(los) accesorio(s) indicado(s):");

        txtResum.setMinimumSize(new java.awt.Dimension(390, 30));
        txtResum.setPreferredSize(new java.awt.Dimension(390, 30));
        txtResum.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtResumKeyTyped(evt);
            }
        });

        jLabel28.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jLabel28.setText("Descripcion corta sobre el problema:");

        txtProfNom.setEditable(false);
        txtProfNom.setBackground(new java.awt.Color(204, 204, 204));
        txtProfNom.setMinimumSize(new java.awt.Dimension(390, 30));
        txtProfNom.setPreferredSize(new java.awt.Dimension(390, 30));

        jLabel27.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jLabel27.setText("Profesor:");

        jScrollPane5.setPreferredSize(new java.awt.Dimension(390, 110));

        txtDetalles.setColumns(20);
        txtDetalles.setLineWrap(true);
        txtDetalles.setRows(5);
        txtDetalles.setWrapStyleWord(true);
        txtDetalles.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDetallesKeyTyped(evt);
            }
        });
        jScrollPane5.setViewportView(txtDetalles);

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel24.setText("Artículo Reportado");

        lblArticuloR.setFont(new java.awt.Font("Trebuchet MS", 3, 18)); // NOI18N
        lblArticuloR.setForeground(new java.awt.Color(102, 102, 102));
        lblArticuloR.setText(">");

        hiddenIDPrestArt.setText("ID Prestamo");

        javax.swing.GroupLayout pnlFormularioRLayout = new javax.swing.GroupLayout(pnlFormularioR);
        pnlFormularioR.setLayout(pnlFormularioRLayout);
        pnlFormularioRLayout.setHorizontalGroup(
            pnlFormularioRLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlFormularioRLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(pnlFormularioRLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel30)
                    .addGroup(pnlFormularioRLayout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 630, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlFormularioRLayout.createSequentialGroup()
                        .addGap(170, 170, 170)
                        .addComponent(btnGenR)
                        .addGap(69, 69, 69)
                        .addComponent(btnClearR))
                    .addGroup(pnlFormularioRLayout.createSequentialGroup()
                        .addGroup(pnlFormularioRLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel28)
                            .addGroup(pnlFormularioRLayout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addGroup(pnlFormularioRLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtProfNom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtResum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(43, 43, 43)
                        .addGroup(pnlFormularioRLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(hiddenIDPrestArt)
                            .addComponent(lblArticuloR)
                            .addComponent(jLabel24)))
                    .addComponent(jLabel27))
                .addGap(48, 48, 48))
        );
        pnlFormularioRLayout.setVerticalGroup(
            pnlFormularioRLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFormularioRLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlFormularioRLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(jLabel24))
                .addGap(6, 6, 6)
                .addGroup(pnlFormularioRLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtProfNom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblArticuloR))
                .addGroup(pnlFormularioRLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlFormularioRLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel28)
                        .addGap(6, 6, 6)
                        .addComponent(txtResum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlFormularioRLayout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(hiddenIDPrestArt)))
                .addGap(18, 18, 18)
                .addComponent(jLabel30)
                .addGap(6, 6, 6)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlFormularioRLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGenR, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnClearR))
                .addContainerGap())
        );

        lyrCrearReporte.add(pnlFormularioR, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 0, 710, 334));

        bkRArt.add(lyrCrearReporte, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 800, 340));

        rdComp.setEnabled(false);
        rdComp.setOpaque(false);
        bkRArt.add(rdComp, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 410, -1, -1));

        rdComp1.setEnabled(false);
        rdComp1.setOpaque(false);
        bkRArt.add(rdComp1, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 410, -1, -1));

        rdComp2.setEnabled(false);
        rdComp2.setOpaque(false);
        bkRArt.add(rdComp2, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 410, -1, -1));

        javax.swing.GroupLayout dlgReporteLayout = new javax.swing.GroupLayout(dlgReporte.getContentPane());
        dlgReporte.getContentPane().setLayout(dlgReporteLayout);
        dlgReporteLayout.setHorizontalGroup(
            dlgReporteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bkRArt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        dlgReporteLayout.setVerticalGroup(
            dlgReporteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bkRArt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("[Artículos]");
        setIconImage(img.getImage());
        setMinimumSize(new java.awt.Dimension(1034, 630));
        setResizable(false);

        bkArticulos.setBackground(new java.awt.Color(255, 255, 255));
        bkArticulos.setMaximumSize(new java.awt.Dimension(1024, 600));
        bkArticulos.setMinimumSize(new java.awt.Dimension(1024, 600));
        bkArticulos.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlCabecera.setBackground(new java.awt.Color(1, 200, 1));
        pnlCabecera.setMaximumSize(new java.awt.Dimension(1024, 83));
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

        bkArticulos.add(pnlCabecera, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

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
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
        panelOPC.add(jLabel9, gridBagConstraints);

        jLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Video Projector_42px.png"))); // NOI18N
        jLabel21.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel21MouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 15, 0);
        panelOPC.add(jLabel21, gridBagConstraints);

        bkArticulos.add(panelOPC, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 152, -1, -1));

        btnRegresar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Back To_35px.png"))); // NOI18N
        btnRegresar.setMaximumSize(new java.awt.Dimension(40, 37));
        btnRegresar.setMinimumSize(new java.awt.Dimension(40, 37));
        btnRegresar.setPreferredSize(new java.awt.Dimension(40, 37));
        btnRegresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegresarActionPerformed(evt);
            }
        });
        bkArticulos.add(btnRegresar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 540, -1, -1));

        btnMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Checklist_24px.png"))); // NOI18N
        btnMenu.setMaximumSize(new java.awt.Dimension(40, 37));
        btnMenu.setMinimumSize(new java.awt.Dimension(40, 37));
        btnMenu.setPreferredSize(new java.awt.Dimension(40, 37));
        btnMenu.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                btnMenuItemStateChanged(evt);
            }
        });
        bkArticulos.add(btnMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 101, -1, -1));

        jLabel4.setFont(new java.awt.Font("Trebuchet MS", 1, 24)); // NOI18N
        jLabel4.setText("Artículos");
        bkArticulos.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 110, -1, -1));

        tableArticulos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "ID", "Articulo", "Disponibles"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableArticulos.getTableHeader().setReorderingAllowed(false);
        tableArticulos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableArticulosMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tableArticulos);

        bkArticulos.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 160, 750, 200));

        pnlBtnNuevo.setBackground(new java.awt.Color(239, 239, 239));
        pnlBtnNuevo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(100, 100, 100)));
        pnlBtnNuevo.setMinimumSize(new java.awt.Dimension(80, 95));
        pnlBtnNuevo.setPreferredSize(new java.awt.Dimension(80, 95));
        pnlBtnNuevo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlBtnNuevoMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnlBtnNuevoMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pnlBtnNuevoMouseExited(evt);
            }
        });
        pnlBtnNuevo.setLayout(new java.awt.GridBagLayout());

        ico1.setFont(new java.awt.Font("Verdana", 0, 11)); // NOI18N
        ico1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Add New_36px.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(10, 32, 0, 32);
        pnlBtnNuevo.add(ico1, gridBagConstraints);

        lbl1.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        lbl1.setForeground(new java.awt.Color(0, 191, 255));
        lbl1.setText("Nuevo");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(7, 0, 1, 0);
        pnlBtnNuevo.add(lbl1, gridBagConstraints);

        lblN1.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        lblN1.setForeground(new java.awt.Color(0, 191, 255));
        lblN1.setText("Registo");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 12, 0);
        pnlBtnNuevo.add(lblN1, gridBagConstraints);

        bkArticulos.add(pnlBtnNuevo, new org.netbeans.lib.awtextra.AbsoluteConstraints(912, 144, -1, -1));

        pnlBtnBorrar.setBackground(new java.awt.Color(239, 239, 239));
        pnlBtnBorrar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(100, 100, 100)));
        pnlBtnBorrar.setMinimumSize(new java.awt.Dimension(80, 95));
        pnlBtnBorrar.setPreferredSize(new java.awt.Dimension(80, 95));
        pnlBtnBorrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlBtnBorrarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnlBtnBorrarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pnlBtnBorrarMouseExited(evt);
            }
        });
        pnlBtnBorrar.setLayout(new java.awt.GridBagLayout());

        ico2.setFont(new java.awt.Font("Verdana", 0, 11)); // NOI18N
        ico2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Erase_36px.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(10, 32, 0, 32);
        pnlBtnBorrar.add(ico2, gridBagConstraints);

        lbl2.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        lbl2.setForeground(new java.awt.Color(255, 127, 80));
        lbl2.setText("Borrar");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(7, 0, 1, 0);
        pnlBtnBorrar.add(lbl2, gridBagConstraints);

        lblN2.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        lblN2.setForeground(new java.awt.Color(255, 127, 80));
        lblN2.setText("Registo");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 12, 0);
        pnlBtnBorrar.add(lblN2, gridBagConstraints);

        bkArticulos.add(pnlBtnBorrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 250, -1, -1));

        pnlBtnActualizar.setBackground(new java.awt.Color(239, 239, 239));
        pnlBtnActualizar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(100, 100, 100)));
        pnlBtnActualizar.setMinimumSize(new java.awt.Dimension(80, 95));
        pnlBtnActualizar.setPreferredSize(new java.awt.Dimension(80, 95));
        pnlBtnActualizar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlBtnActualizarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnlBtnActualizarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pnlBtnActualizarMouseExited(evt);
            }
        });
        pnlBtnActualizar.setLayout(new java.awt.GridBagLayout());

        ico6.setFont(new java.awt.Font("Verdana", 0, 11)); // NOI18N
        ico6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Edit File_36px.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(10, 32, 0, 32);
        pnlBtnActualizar.add(ico6, gridBagConstraints);

        lbl3.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        lbl3.setForeground(new java.awt.Color(154, 205, 50));
        lbl3.setText("Actualizar");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(7, 0, 1, 0);
        pnlBtnActualizar.add(lbl3, gridBagConstraints);

        lblN3.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        lblN3.setForeground(new java.awt.Color(154, 205, 50));
        lblN3.setText("Registo");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 12, 0);
        pnlBtnActualizar.add(lblN3, gridBagConstraints);

        bkArticulos.add(pnlBtnActualizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 360, -1, -1));

        pnlBtnReportar.setBackground(new java.awt.Color(239, 239, 239));
        pnlBtnReportar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(100, 100, 100)));
        pnlBtnReportar.setMinimumSize(new java.awt.Dimension(80, 95));
        pnlBtnReportar.setPreferredSize(new java.awt.Dimension(80, 95));
        pnlBtnReportar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlBtnReportarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnlBtnReportarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pnlBtnReportarMouseExited(evt);
            }
        });
        pnlBtnReportar.setLayout(new java.awt.GridBagLayout());

        ico7.setFont(new java.awt.Font("Verdana", 0, 11)); // NOI18N
        ico7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/inform-36.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(10, 32, 0, 32);
        pnlBtnReportar.add(ico7, gridBagConstraints);

        lbl4.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        lbl4.setForeground(new java.awt.Color(231, 76, 60));
        lbl4.setText("Reportar");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(7, 0, 1, 0);
        pnlBtnReportar.add(lbl4, gridBagConstraints);

        lblN4.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        lblN4.setForeground(new java.awt.Color(231, 76, 60));
        lblN4.setText("Articulo");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 12, 0);
        pnlBtnReportar.add(lblN4, gridBagConstraints);

        bkArticulos.add(pnlBtnReportar, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 465, -1, -1));

        jLabel6.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        jLabel6.setText("Artículos Prestados HOY");
        bkArticulos.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 370, -1, -1));

        jLabel29.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        jLabel29.setText("Cambiar a:");
        bkArticulos.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 563, -1, -1));

        tglReg.setText("Todos los Registros de artículos");
        tglReg.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                tglRegItemStateChanged(evt);
            }
        });
        bkArticulos.add(tglReg, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 558, -1, -1));

        jLayeredPane2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
        pnlHoy.setViewportView(jTable5);

        jLayeredPane2.add(pnlHoy, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 750, 150));

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
        pnlTodos.setViewportView(jTable3);

        jLayeredPane2.add(pnlTodos, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 750, 157));

        bkArticulos.add(jLayeredPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 394, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bkArticulos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bkArticulos, javax.swing.GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
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
        ARegistro reg = new ARegistro();
        reg.setVisible(true);
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_ico5MouseClicked

    private void jLabel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseClicked
        try {
            Profesor profe = new Profesor();
            profe.setVisible(true);
            this.setVisible(false);
            this.dispose();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al cargar la interfaz de Profesor\n" + ex);
        }
    }//GEN-LAST:event_jLabel7MouseClicked

    private void jLabel8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MouseClicked
        try {
            Departamento depart = new Departamento();
            depart.setVisible(true);
            this.setVisible(false);
            this.dispose();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al cargar la interfaz de Departamento\n" + ex);
        }
    }//GEN-LAST:event_jLabel8MouseClicked

    private void jLabel9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseClicked
        try {
            Aula aula = new Aula();
            aula.setVisible(true);
            this.setVisible(false);
            this.dispose();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al cargar la interfaz de Aula\n" + ex);
        }
    }//GEN-LAST:event_jLabel9MouseClicked

    private void btnRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegresarActionPerformed
        Menu menu = new Menu();
        menu.setVisible(true);
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_btnRegresarActionPerformed

    private void btnMenuItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_btnMenuItemStateChanged
        if (btnMenu.isSelected()) {
            panelOPC.setVisible(true);
        } else {
            panelOPC.setVisible(false);
        }
    }//GEN-LAST:event_btnMenuItemStateChanged

    private void tableArticulosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableArticulosMouseClicked
        int fila = tableArticulos.getSelectedRow();
        if (evt.getClickCount() == 2) {
            System.out.println("fila seleccionada: " + fila);
        }
////            jTabbedPane1.setEnabledAt(4, true);
////            jTabbedPane1.setSelectedIndex(4);
////            try {
////                ArticuloDB acc = new ArticuloDB();
////                String id = String.valueOf(jTable2.getValueAt(fila, 0));
////                String[] datos = acc.getArticulo(id);
////                hiddenLabelArt.setText(datos[0]);
////                txtNom3.setText(datos[1]);
////                txtExist1.setText(datos[2]);
////                txtDesc1.setText(datos[4]);
////            } catch (SQLException ex) {
////                Logger.getLogger(Videoproyector.class.getName()).log(Level.SEVERE, null, ex);
////            }
////        }
        //        int fila = (int) jTable2.getModel().getValueAt(jTable2.getSelectedRow(),3);
        //        System.out.println("Seleccionaste la fila de articulos id: " +jTable2.getModel().getValueAt(jTable2.convertRowIndexToModel(fila), 3));
    }//GEN-LAST:event_tableArticulosMouseClicked

    private void pnlBtnNuevoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlBtnNuevoMouseClicked
        pnlBtnNuevo.setBackground(new Color(240, 240, 240));
        ico1.setIcon(add);

        dlgNuevo.setLocationRelativeTo(bkArticulos);
        dlgNuevo.setVisible(true);
    }//GEN-LAST:event_pnlBtnNuevoMouseClicked

    private void pnlBtnNuevoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlBtnNuevoMouseEntered
        pnlBtnNuevo.setBackground(new Color(0, 191, 255));
        ico1.setIcon(addW);
    }//GEN-LAST:event_pnlBtnNuevoMouseEntered

    private void pnlBtnNuevoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlBtnNuevoMouseExited
        pnlBtnNuevo.setBackground(new Color(240, 240, 240));
        ico1.setIcon(add);
    }//GEN-LAST:event_pnlBtnNuevoMouseExited

    private void pnlBtnBorrarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlBtnBorrarMouseClicked
        pnlBtnBorrar.setBackground(new Color(240, 240, 240));
        ico2.setIcon(ers);

        dlgConfirm.setLocationRelativeTo(bkArticulos);
        dlgConfirm.setVisible(true);

        if (valido) {
            valido = false;
            dlgBorrar.setLocationRelativeTo(bkArticulos);
            dlgBorrar.setVisible(true);
        }
    }//GEN-LAST:event_pnlBtnBorrarMouseClicked

    private void pnlBtnBorrarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlBtnBorrarMouseEntered
        pnlBtnBorrar.setBackground(new Color(255, 127, 80));
        ico2.setIcon(ersW);
    }//GEN-LAST:event_pnlBtnBorrarMouseEntered

    private void pnlBtnBorrarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlBtnBorrarMouseExited
        pnlBtnBorrar.setBackground(new Color(240, 240, 240));
        ico2.setIcon(ers);
    }//GEN-LAST:event_pnlBtnBorrarMouseExited

    private void pnlBtnActualizarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlBtnActualizarMouseClicked
        pnlBtnActualizar.setBackground(new Color(240, 240, 240));
        ico6.setIcon(upd);
        clearActualizar();

        dlgActualizar.setLocationRelativeTo(bkArticulos);
        dlgActualizar.setVisible(true);
    }//GEN-LAST:event_pnlBtnActualizarMouseClicked

    private void pnlBtnActualizarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlBtnActualizarMouseEntered
        pnlBtnActualizar.setBackground(new Color(154, 205, 50));
        ico6.setIcon(updW);
    }//GEN-LAST:event_pnlBtnActualizarMouseEntered

    private void pnlBtnActualizarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlBtnActualizarMouseExited
        pnlBtnActualizar.setBackground(new Color(240, 240, 240));
        ico6.setIcon(upd);
    }//GEN-LAST:event_pnlBtnActualizarMouseExited

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        dlgNuevo.setVisible(false);
        dlgNuevo.dispose();
    }//GEN-LAST:event_btnCerrarActionPerformed

    private void btnCerrar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrar1ActionPerformed
        dlgBorrar.setVisible(false);
        dlgBorrar.dispose();
    }//GEN-LAST:event_btnCerrar1ActionPerformed

    private void btnCerrar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrar2ActionPerformed
        clearActualizar();
        dlgActualizar.setVisible(false);
        dlgActualizar.dispose();
    }//GEN-LAST:event_btnCerrar2ActionPerformed

    private void txtNomKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNomKeyTyped
        if (txtNom.getText().length() >= 20) {
            evt.consume();
        }
    }//GEN-LAST:event_txtNomKeyTyped

    private void txtExistKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtExistKeyTyped
        char caracter = evt.getKeyChar();
        if (((caracter < '0') || (caracter > '9')) && (caracter != '\b')) {
            evt.consume();
        }
        if (txtExist.getText().length() >= 6) {
            evt.consume();
        }
    }//GEN-LAST:event_txtExistKeyTyped

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        String nombre = txtNom.getText().trim();
        String existencias = txtExist.getText().trim();
        String descripcion = txtDesc.getText().trim();
        
        if (!nombre.isEmpty() && !existencias.isEmpty() && !descripcion.isEmpty()) {
            String[] articulo = {nombre, existencias, descripcion};
            System.out.println("\n: : : Preparando datos del Nuevo articulo: " + Arrays.toString(articulo) + "\n");

            try {
                ArticuloDB artic = new ArticuloDB();
                if (!artic.articuloExist(nombre)) {
                    System.out.println("\n: : : El nombre del articulo se encuentra disponible, se creara el registro...");
                    artic.setArticulo(articulo);
                    JOptionPane.showMessageDialog(null, "Se ha creado el nuevo registro");
                } else {
                    JOptionPane.showMessageDialog(this, "El nombre del articulo ya se encuentra registrado!", "Advertencia", JOptionPane.WARNING_MESSAGE);
                }
            } catch (SQLException ex) {
                System.out.println("Error al realizar el guardado de Articulo: " + ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "No puede dejar ningun campo vacio\nFavor de completar el formulario", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
        txtNom.setText("");
        txtDesc.setText("");
        txtExist.setText("");
        try {
            getTable();
        } catch (SQLException e) {
            System.out.println("Error al dibjuar los articulos despues de crear un registro:" + e);
        }
        dlgNuevo.setVisible(false);
        dlgNuevo.dispose();
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void txtBorrarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBorrarKeyTyped
        if (txtBorrar.getText().length() >= 12) {
            evt.consume();
        }
        char caracter = evt.getKeyChar();
        if (((caracter < '0') || (caracter > '9')) && (caracter != '\b')) {
            evt.consume();
        }
        if (evt.getKeyChar() == KeyEvent.VK_ENTER) {
            btnBorrar.doClick();
        }
    }//GEN-LAST:event_txtBorrarKeyTyped

    private void btnBorrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorrarActionPerformed
        String id = txtBorrar.getText().trim();
        try {//JHBHJBHJB COMPROBAR QUE NO HAY ARTICULOS EN PRESTAMO O CON REPORTE PENDIENTES A LIBERAR
            ArticuloDB art = new ArticuloDB();
            String[] datos = art.getArticulo(id);
            JOptionPane.showMessageDialog(this, "El articulo en cuestion tiene " + art.comprobarArtEnPrestYRep(Integer.parseInt(id)) + " reportes y prestamos en el sistema");
            if (art.comprobarArtEnPrestYRep(Integer.parseInt(id)) == 0){//Integer.parseInt(datos[2]) == Integer.parseInt(datos[3])) {
                int opc = JOptionPane.showConfirmDialog(this, "Si procede a esta accion borrara todos los registros relacionados", "Información", JOptionPane.YES_NO_OPTION);
                if (opc == JOptionPane.YES_OPTION) {
                    art.destroyArt(Integer.parseInt(id));
                    getTable();
                    JOptionPane.showMessageDialog(this, "El articulo ha sido eliminado");
                }
            } else {
                JOptionPane.showMessageDialog(this, "El articulo se encuentra prestado o con un reporte\nCambie el estado del reporte sobre el articulo para su eliminación");
            }
        } catch (SQLException e) {
            System.out.println("Error al cargar los registros de AccesorioDB o al cargar la tabla de accesorios:" + e);
        }
        txtBorrar.setText("");
        dlgBorrar.setVisible(false);
        dlgBorrar.dispose();
    }//GEN-LAST:event_btnBorrarActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        String id = txtIDArt.getText().trim();
        try {//asdad investigar cuantos articulos tengo en total y si el sujeto ingresa menos de los que estan en prestamo o menos de 1 indicar que no es posible esta operacion
            ArticuloDB art = new ArticuloDB();
            String[] datos = art.getArticulo(id);
            if (datos[0] != null) {//existe
                int existencias = art.comprobarExistenciasArt(Integer.parseInt(id));
                int prestYrep = art.comprobarArtEnPrestYRep(Integer.parseInt(id));
                int total = existencias + prestYrep;
                if (prestYrep == 0){//Integer.parseInt(datos[2]) == Integer.parseInt(datos[3])) {//si las existencias son menores a los diponibles hay un prestamo o una multa
                    //Colocar Info
                    txtNomA.setText(datos[1]);
                    txtExtA.setText(datos[2]);
                    txtDescA.setText(datos[3]);

                    lblHiddenID.setText(id);
                    lblhiddenNom.setText(datos[1]);
                    btnActualizar.setEnabled(true);
                    pnlBuscarArt.setVisible(false);
                    pnlActArt.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(this, "El artículo indicado se encuentra en un prestamo\nNo será posible actualizar hasta indicar la devolución de todos los articulos de este ID prestados");
                }
            } else {
                JOptionPane.showMessageDialog(this, "No se encontro el artículo indicado");
            }
        } catch (SQLException e) {
            System.out.println("Error al recuperar articulo: " + e);
        }
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        String nom = txtNomA.getText().trim();
        String ext = txtExtA.getText().trim();
        String desc = txtDescA.getText().trim();
        String id = lblHiddenID.getText().trim();
        if (!nom.isEmpty() && !ext.isEmpty() && !desc.isEmpty()) {
            try {
                ArticuloDB artic = new ArticuloDB();
                //int existencias = artic.comprobarExistenciasArt(Integer.parseInt(id));
                int prestYrep = artic.comprobarArtEnPrestYRep(Integer.parseInt(id));
                //int total = existencias + prestYrep;
                if(Integer.parseInt(ext) >= prestYrep){
                if (artic.articuloExist(nom)) {
                    System.out.println("Esta usando un nombre existente");
                    if (nom.equals(lblhiddenNom.getText().trim())) {
                        System.out.println("el nombre es el mismo no hay problema");
                        String[] datos = {id, nom, ext, desc};
                        artic.updArticulo(datos);
                        JOptionPane.showMessageDialog(this, "Se ha actualizado el registro de articulos");
                    } else {
                        JOptionPane.showMessageDialog(this, "El nombre que utiliza para el articulo ya esta registrado intente con un distinto");
                    }
                } else {
                    System.out.println("el nombre no es el mismo y no existe en db no hay problema");
                    String[] datos = {lblHiddenID.getText().trim(), nom, ext, desc};
                    artic.updArticulo(datos);
                    JOptionPane.showMessageDialog(this, "Se ha actualizado el registro de articulos");
                }
            }else{
                    JOptionPane.showMessageDialog(this, "No se puede actualizar este articulo con la cantidad de existencias ingresadas\nEsto sucede porque el dato ingresado es menor a la cantidad de  existencias en prestamo y reporte\n\nSolucion:\nLibere los reportes de este articulo y los prestamos realizados");
            }
                    
            } catch (SQLException e) {
                System.out.println("Error al cargar registros desde AccesoriosDB:" + e);
            }
        } else {
            JOptionPane.showMessageDialog(this, "No puede dejar ningun campo en blanco");
        }
        try {
            getTable();
        } catch (SQLException e) {
            System.out.println("Error:" + e);
        }
        clearActualizar();
        dlgActualizar.setVisible(false);
        dlgActualizar.dispose();
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void txtIDArtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIDArtKeyTyped
        if (evt.getKeyChar() == KeyEvent.VK_ENTER) {
            btnBuscar.doClick();
        }
        char caracter = evt.getKeyChar();
        if (((caracter < '0') || (caracter > '9')) && (caracter != '\b')) {
            evt.consume();
        }
        if (txtIDArt.getText().length() >= 6) {
            evt.consume();
        }
    }//GEN-LAST:event_txtIDArtKeyTyped

    private void txtNomAKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNomAKeyTyped
        if (txtNomA.getText().length() >= 20) {
            evt.consume();
        }
    }//GEN-LAST:event_txtNomAKeyTyped

    private void txtExtAKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtExtAKeyTyped
        char caracter = evt.getKeyChar();
        if (((caracter < '0') || (caracter > '9')) && (caracter != '\b')) {
            evt.consume();
        }
        if (txtExtA.getText().length() >= 6) {
            evt.consume();
        }
    }//GEN-LAST:event_txtExtAKeyTyped

    private void txtDescKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescKeyTyped
        if (txtDesc.getText().length() >= 350) {
            evt.consume();
        }
    }//GEN-LAST:event_txtDescKeyTyped

    private void txtDescAKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescAKeyTyped
        if (txtDescA.getText().length() >= 350) {
            evt.consume();
        }
    }//GEN-LAST:event_txtDescAKeyTyped

    private void tglRegItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_tglRegItemStateChanged
        if (tglReg.isSelected()) {
            try {
                getPrestadosAcc();
            } catch (SQLException e) {
                System.out.println("Error al recuperar todos los registros existentes de articulos: " + e);
            }
            pnlTodos.setVisible(true);
            pnlHoy.setVisible(false);
            jLabel6.setText("Artículos Prestados TODOS");
            tglReg.setText("Los registros de artículos de HOY");
        } else {
            try {
                getPrestadosHOY();
            } catch (SQLException e) {
                System.out.println("Error al recuperar todos los registros existentes de articulos: " + e);
            }
            pnlHoy.setVisible(true);
            pnlTodos.setVisible(false);
            tglReg.setText("Todos los Registros de artículos");
            jLabel6.setText("Artículos Prestados HOY");
        }
    }//GEN-LAST:event_tglRegItemStateChanged

    private void jLabel21MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel21MouseClicked
        try {
            Videoproyector vid = new Videoproyector();
            vid.setVisible(true);
            this.setVisible(false);
            this.dispose();
        } catch (SQLException ex) {
            Logger.getLogger(Profesor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jLabel21MouseClicked

    private void btnComprobarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnComprobarActionPerformed
        String crdncial = txtUsuario.getText().trim();
        try {
            UsuarioReadDB leer = new UsuarioReadDB();
            if (leer.getExisteUsuario(crdncial)) {
                String hashed = leer.getPass(crdncial);
                if (BCrypt.checkpw(String.valueOf(txtPass.getPassword()), hashed)) {
                    if (leer.getEsAdminUsuario(crdncial)) {
                        valido = true;
                        txtUsuario.setText("");
                        txtPass.setText("");
                        dlgConfirm.setVisible(false);
                        dlgConfirm.dispose();
                    } else {
                        throw new Exception();
                    }
                } else {
                    throw new Exception();
                }
            } else {
                throw new Exception();
            }
        } catch (SQLException ex) {
            System.out.println("Error al comprobar usuario:" + ex);
        } catch (Exception e) {
            txtUsuario.setText("");
            txtPass.setText("");
            valido = false;
            JOptionPane.showMessageDialog(null, "Compruebe los datos ingresados, es posible que:\n-Su usuario no sea administrador\n-No ingreso sus datos correctamente");
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

    private void pnlBtnReportarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlBtnReportarMouseClicked
        pnlBtnReportar.setBackground(new Color(204, 204, 204));
        fechaArtPrestamo.setVisible(true);
        listaPrestamos.setVisible(false);
        pnlFormularioR.setVisible(false);
        rdComp.setSelected(true);
        lblInfoR.setText("Para comenzar el reporte debe indicar la fecha en que fue prestado el Artículo");
        dlgReporte.setLocationRelativeTo(bkArticulos);
        dlgReporte.setVisible(true);
    }//GEN-LAST:event_pnlBtnReportarMouseClicked

    private void pnlBtnReportarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlBtnReportarMouseEntered
        pnlBtnReportar.setBackground(new Color(204, 204, 204));
    }//GEN-LAST:event_pnlBtnReportarMouseEntered

    private void pnlBtnReportarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlBtnReportarMouseExited
        pnlBtnReportar.setBackground(new Color(239, 239, 239));
    }//GEN-LAST:event_pnlBtnReportarMouseExited

    private void btnCerrarRMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCerrarRMouseClicked
        btnCerrarR.setBackground(null);
        fechaArtPrestamo.setVisible(true);
        listaPrestamos.setVisible(false);
        pnlFormularioR.setVisible(false);
        fechaPrestamo.setCalendar(null);
        rdComp.setSelected(false);
        rdComp1.setSelected(false);
        rdComp2.setSelected(false);
        dlgReporte.setVisible(false);
        dlgReporte.dispose();
    }//GEN-LAST:event_btnCerrarRMouseClicked

    private void btnCerrarRMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCerrarRMouseEntered
        btnCerrarR.setBackground(Color.WHITE);
    }//GEN-LAST:event_btnCerrarRMouseEntered

    private void btnCerrarRMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCerrarRMouseExited
        btnCerrarR.setBackground(null);
    }//GEN-LAST:event_btnCerrarRMouseExited

    private void btnCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCActionPerformed
        if (fechaPrestamo.getDate() != null) {
            String fch1 = new SimpleDateFormat("yyyy-MM-dd").format(fechaPrestamo.getDate());
            System.out.println("mi fecha: " + fch1.concat(" 00:00:00"));

            Calendar c = Calendar.getInstance();
            c.setTime(fechaPrestamo.getDate());
            c.add(Calendar.DATE, 1);  // number of days to add
            String miNuevaFecha = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());

            System.out.println("con fecha extra:" + miNuevaFecha.concat(" 00:00:00"));
            System.out.println(": : : resumen fechas: " + fch1.concat(" 00:00:00") + " : " + miNuevaFecha.concat(" 00:00:00"));

            String[] cols = {listaArt.getColumnName(0), listaArt.getColumnName(1), listaArt.getColumnName(2), listaArt.getColumnName(3), listaArt.getColumnName(4)};
            int count = 0;
            String[][] datos = null;

            try {
                ArticuloDB acc = new ArticuloDB();
                datos = acc.getRegArticulos(fch1.concat(" 00:00:00"), miNuevaFecha.concat(" 00:00:00"));
                count = datos.length;
                System.out.println("\nRegistros articulos existentes: " + count);
            } catch (SQLException ex) {
                System.out.println("Error al recuperar los datos: " + ex);
            }
            if (count != 0) {
                javax.swing.table.TableModel miModelListaArt = new DefaultTableModel(cols, count) {
                    @Override
                    public boolean isCellEditable(int row, int column) {
                        return false;
                    }
                };
                for (int i = 0; i < datos.length; i++) {
                    for (int j = 0; j < 5; j++) {
                        miModelListaArt.setValueAt(datos[i][j], i, j);
                        listaArt.isCellEditable(i, j);
                    }
                }
                listaArt.setModel(miModelListaArt);
                fechaArtPrestamo.setVisible(false);
                listaPrestamos.setVisible(true);
                pnlFormularioR.setVisible(false);
                lblInfoR.setText("Seleccione el Artículo de la lista de préstamos realizados, recuerde observar la fecha, hora y profesor");
                rdComp1.setSelected(true);
            } else {
                JOptionPane.showMessageDialog(bkRArt, "La fecha indicada no cuenta con articulos disponibles para reportar!!!\nPuede intentar nuevamente con una fecha diferente.");
            }
        } else {
            JOptionPane.showMessageDialog(bkRArt, "Debe indicar la fecha en que fue prestado el artículo para generar\nla lista de los prestamos realizados ese día");
        }
        fechaPrestamo.setCalendar(null);
    }//GEN-LAST:event_btnCActionPerformed

    private void btnC1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnC1ActionPerformed
        int row = listaArt.getSelectedRow();
        if (row != -1) {
            int idRow = Integer.parseInt((String) listaArt.getValueAt(row, 0));
            System.out.println("El id seleccionado es:" + idRow);
            txtProfNom.setText((String) listaArt.getValueAt(row, 1));
            lblArticuloR.setText((String) listaArt.getValueAt(row, 2));
            hiddenIDPrestArt.setText((String) listaArt.getValueAt(row, 0));
            fechaArtPrestamo.setVisible(false);
            listaPrestamos.setVisible(false);
            pnlFormularioR.setVisible(true);
            lblInfoR.setText("Complete el formulario");
            rdComp2.setSelected(true);
        } else {
            JOptionPane.showMessageDialog(bkRArt, "Seleccione el registro del articulo a reportar!!!\nAsegúrese que la fecha, hora y profesor sea el que busca.");
        }

    }//GEN-LAST:event_btnC1ActionPerformed

    private void btnClearRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearRActionPerformed
        txtResum.setText("");
        txtDetalles.setText("");
    }//GEN-LAST:event_btnClearRActionPerformed

    private void btnGenRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenRActionPerformed
        String resumen = txtResum.getText().trim(), detalles = txtDetalles.getText().trim(), idReg = hiddenIDPrestArt.getText().trim();
        if (!resumen.isEmpty() && !detalles.isEmpty()) {
            try {
                ArticuloDB a = new ArticuloDB();
                int idArt = a.getArticuloID(lblArticuloR.getText().trim());
                String[] registroPrestamoA = a.getReg(idReg);
                String idProf = a.profIDPrestamo(registroPrestamoA[2]);
                a.reporteArt(idProf, resumen, detalles, idArt, Integer.parseInt(idReg)); //profe, titulo, detalles, accesorio id, registro id
                                
                txtResum.setText("");
                txtDetalles.setText("");
                fechaArtPrestamo.setVisible(true);
                listaPrestamos.setVisible(false);
                pnlFormularioR.setVisible(false);
                fechaPrestamo.setCalendar(null);
                rdComp.setSelected(false);
                rdComp1.setSelected(false);
                rdComp2.setSelected(false);
                dlgReporte.setVisible(false);
                dlgReporte.dispose();
                try {
                    getTable();
                } catch (SQLException e) {
                    System.out.println("Error al cargar los articulos existentes:" + e);
                }
                JOptionPane.showMessageDialog(bkRArt, "Se ha generado el reporte\nSe visualizara una notificación en la ventana de Menú por cada reporte\n\nRecuerde para eliminar la notificacion un usuario administrador\ndebe validar que el Profesor ya no tiene adeudo pendiente con este articulo");
            } catch (SQLException e) {
                System.out.println("Error al generar reporte de articulo: " + e);
            }
        } else {
            JOptionPane.showMessageDialog(bkRArt, "Por favor complete el formulario!!!\nNo deje nada en blanco");
        }
    }//GEN-LAST:event_btnGenRActionPerformed

    private void txtResumKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtResumKeyTyped
        if (txtResum.getText().length() >= 50) {
            evt.consume();
        }
    }//GEN-LAST:event_txtResumKeyTyped

    private void txtDetallesKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDetallesKeyTyped
        if (txtDetalles.getText().length() >= 450) {
            evt.consume();
        }
    }//GEN-LAST:event_txtDetallesKeyTyped

    public void clearActualizar() {
        txtNomA.setText("");
        txtExtA.setText("");
        txtDescA.setText("");
        //buscar
        txtIDArt.setText("");

        btnActualizar.setEnabled(false);
        pnlBuscarArt.setVisible(true);
        pnlActArt.setVisible(false);
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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Articulo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Articulo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Articulo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Articulo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new Articulo().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane articulosPrestados;
    private javax.swing.JPanel bkArticulos;
    private javax.swing.JPanel bkRArt;
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnBorrar;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnC;
    private javax.swing.JButton btnC1;
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnCerrar1;
    private javax.swing.JButton btnCerrar2;
    private javax.swing.JLabel btnCerrarR;
    private javax.swing.JButton btnClearR;
    private javax.swing.JButton btnComprobar;
    private javax.swing.JButton btnGenR;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JToggleButton btnMenu;
    private javax.swing.JButton btnRegresar;
    private javax.swing.JDialog dlgActualizar;
    private javax.swing.JDialog dlgBorrar;
    private javax.swing.JDialog dlgConfirm;
    private javax.swing.JDialog dlgNuevo;
    private javax.swing.JDialog dlgReporte;
    private javax.swing.JPanel fechaArtPrestamo;
    private com.toedter.calendar.JDateChooser fechaPrestamo;
    private javax.swing.JLabel hiddenIDPrestArt;
    private javax.swing.JLabel ico1;
    private javax.swing.JLabel ico2;
    private javax.swing.JLabel ico3;
    private javax.swing.JLabel ico4;
    private javax.swing.JLabel ico5;
    private javax.swing.JLabel ico6;
    private javax.swing.JLabel ico7;
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
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JLayeredPane jLayeredPane2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTable5;
    private javax.swing.JLabel labelFecha;
    private javax.swing.JLabel labelHora;
    private javax.swing.JLabel lbl1;
    private javax.swing.JLabel lbl2;
    private javax.swing.JLabel lbl3;
    private javax.swing.JLabel lbl4;
    private javax.swing.JLabel lblArticuloR;
    private javax.swing.JLabel lblHiddenID;
    private javax.swing.JLabel lblInfoR;
    private javax.swing.JLabel lblN1;
    private javax.swing.JLabel lblN2;
    private javax.swing.JLabel lblN3;
    private javax.swing.JLabel lblN4;
    private javax.swing.JLabel lblTituloR;
    private javax.swing.JLabel lblhiddenNom;
    private javax.swing.JTable listaArt;
    private javax.swing.JPanel listaPrestamos;
    private javax.swing.JLayeredPane lyrCrearReporte;
    private javax.swing.JPanel panelOPC;
    private javax.swing.JPanel pnlActArt;
    private javax.swing.JPanel pnlBtnActualizar;
    private javax.swing.JPanel pnlBtnBorrar;
    private javax.swing.JPanel pnlBtnNuevo;
    private javax.swing.JPanel pnlBtnReportar;
    private javax.swing.JPanel pnlBuscarArt;
    private javax.swing.JPanel pnlCabecera;
    private javax.swing.JPanel pnlColor1;
    private javax.swing.JPanel pnlColor2;
    private javax.swing.JPanel pnlColor3;
    private javax.swing.JPanel pnlColor4;
    private javax.swing.JPanel pnlColor5;
    private javax.swing.JPanel pnlColor6;
    private javax.swing.JPanel pnlColor7;
    private javax.swing.JPanel pnlFormularioR;
    private javax.swing.JScrollPane pnlHoy;
    private javax.swing.JScrollPane pnlTodos;
    private javax.swing.JRadioButton rdComp;
    private javax.swing.JRadioButton rdComp1;
    private javax.swing.JRadioButton rdComp2;
    private javax.swing.JTable tableArticulos;
    private javax.swing.JToggleButton tglReg;
    private javax.swing.JTextField txtBorrar;
    private javax.swing.JTextArea txtDesc;
    private javax.swing.JTextArea txtDescA;
    private javax.swing.JTextArea txtDetalles;
    private javax.swing.JTextField txtExist;
    private javax.swing.JTextField txtExtA;
    private javax.swing.JTextField txtIDArt;
    private javax.swing.JTextField txtNom;
    private javax.swing.JTextField txtNomA;
    private javax.swing.JPasswordField txtPass;
    private javax.swing.JTextField txtProfNom;
    private javax.swing.JTextField txtResum;
    private javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables
}
