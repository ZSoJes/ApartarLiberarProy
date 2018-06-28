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
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import proyector.dataBase.crud.AccesorioDB;

/**
 *
 * @author JuanGS
 */
public class Articulo extends javax.swing.JFrame {

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
        try{getTable();}catch(SQLException e){System.out.println("Error al cargar los articulos existentes:"+e);}
        try{getPrestadosHOY();}catch(SQLException e){System.out.println("Error al cargar los articulos prestados hoy:"+e);}
    }

    public void reloj() {
        labelHora.setText(new SimpleDateFormat("hh:mm:ss a").format(new Date()));
    }
    
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
        model.setDataVector(art, cols);
    }
    
    public void getPrestadosHOY() throws SQLException {
        DefaultTableModel model5 = (DefaultTableModel) jTable5.getModel();
        AccesorioDB acc = new AccesorioDB();
        String[] cols = {jTable5.getColumnName(0),jTable5.getColumnName(1), jTable5.getColumnName(2), jTable5.getColumnName(3),jTable5.getColumnName(4)};
        
        String[][] datos = acc.getRegAccesorios(true);
        int count = datos.length;
        System.out.println("\nRegistros accesorios existentes: " + count);
        
        jTable5.getColumnModel().getColumn(0).setPreferredWidth(30);
        jTable5.getColumnModel().getColumn(1).setPreferredWidth(295);
        jTable5.getColumnModel().getColumn(2).setPreferredWidth(295);
        jTable5.getColumnModel().getColumn(3).setPreferredWidth(40);
        jTable5.getColumnModel().getColumn(4).setPreferredWidth(90);
        jTable5.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        model5.setDataVector(datos, cols);
    }
    
    public void getPrestadosAcc() throws SQLException{
        DefaultTableModel modelReg = (DefaultTableModel) jTable3.getModel();
        AccesorioDB acc = new AccesorioDB();
        String[] cols = {jTable3.getColumnName(0),jTable3.getColumnName(1), jTable3.getColumnName(2), jTable3.getColumnName(3),jTable3.getColumnName(4)};
        
        String[][] datos = acc.getRegAccesorios(false);
        int count = datos.length;
        System.out.println("\nRegistros accesorios existentes: " + count);
        
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
        jTable2 = new javax.swing.JTable();
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
        dlgNuevo.setPreferredSize(new java.awt.Dimension(630, 380));
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
        dlgBorrar.setPreferredSize(new java.awt.Dimension(550, 270));
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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("[Artículos]");
        setIconImage(img.getImage());
        setMinimumSize(new java.awt.Dimension(1034, 630));
        setPreferredSize(new java.awt.Dimension(1024, 600));

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
            JOptionPane.showMessageDialog(null, "Error al cargar la interfaz de Profesor\n"+ex);
        }
    }//GEN-LAST:event_jLabel7MouseClicked

    private void jLabel8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MouseClicked
        try {
            Departamento depart = new Departamento();
            depart.setVisible(true);
            this.setVisible(false);
            this.dispose();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al cargar la interfaz de Departamento\n"+ex);
        }
    }//GEN-LAST:event_jLabel8MouseClicked

    private void jLabel9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseClicked
        try {
            Aula aula = new Aula();
            aula.setVisible(true);
            this.setVisible(false);
            this.dispose();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al cargar la interfaz de Aula\n"+ex);
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

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        int fila = jTable2.getSelectedRow();
        if(evt.getClickCount() == 2){
            System.out.println("fila seleccionada: " + fila);
        }
////            jTabbedPane1.setEnabledAt(4, true);
////            jTabbedPane1.setSelectedIndex(4);
////            try {
////                AccesorioDB acc = new AccesorioDB();
////                String id = String.valueOf(jTable2.getValueAt(fila, 0));
////                String[] datos = acc.getAccesorio(id);
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
    }//GEN-LAST:event_jTable2MouseClicked

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

        dlgBorrar.setLocationRelativeTo(bkArticulos);
        dlgBorrar.setVisible(true);
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
        String nom = txtNom.getText().trim();
        String exist = txtExist.getText().trim();
        String desc = txtDesc.getText().trim();
        if (!nom.isEmpty() && !exist.isEmpty() && !desc.isEmpty()){
            String[] articulo = {nom, exist, desc};
            System.out.println("Arreglo de datos: " + Arrays.toString(articulo));
            
            try {
                AccesorioDB artic = new AccesorioDB();
                if(!artic.accesorioExist(nom)){
                    System.out.println("El accesorio no ha sido registrado, se creara el registro...");
                    artic.setAccesorio(articulo);
                    JOptionPane.showMessageDialog(null, "Se ha creado el nuevo registro");
                }else{JOptionPane.showMessageDialog(this, "Este articulo ya existes!", "Advertencia", JOptionPane.WARNING_MESSAGE);}
            } catch (SQLException ex) {System.out.println("Error al realizar el guardado de Articulo: " + ex);}
        }else{JOptionPane.showMessageDialog(null, "No puede dejar ningun campo vacio\nFavor de completar el formulario", "Advertencia", JOptionPane.WARNING_MESSAGE);}
        txtNom.setText("");
        txtDesc.setText("");
        txtExist.setText("");
        try{getTable();}catch(SQLException e){System.out.println("Error al dibjuar los articulos despues de crear un registro:"+e);}
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
        try{
            AccesorioDB acc = new AccesorioDB();
            String[] datos = acc.getAccesorio(id);
            if(Integer.parseInt(datos[2])==Integer.parseInt(datos[3])){
                int opc = JOptionPane.showConfirmDialog(this, "Si procede a esta accion borrara todos los registros relacionados", "Información", JOptionPane.YES_NO_OPTION);
                if (opc == JOptionPane.YES_OPTION){
                    acc.destroyAcc(Integer.parseInt(id));
                    getTable();
                    JOptionPane.showMessageDialog(this, "El articulo ha sido eliminado");
                }
            }else{
                JOptionPane.showMessageDialog(this, "El articulo se encuentra prestado o con un reporte\nCambie el estado del reporte sobre el articulo para su eliminación");
            }
        }catch(SQLException e){
            System.out.println("Error al cargar los registros de AccesorioDB o al cargar la tabla de accesorios:"+e);
        }
        txtBorrar.setText("");
        dlgBorrar.setVisible(false);
        dlgBorrar.dispose();
    }//GEN-LAST:event_btnBorrarActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        String id = txtIDArt.getText().trim();
        try{
            AccesorioDB acc = new AccesorioDB();
            String[] datos = acc.getAccesorio(id);
            if(datos[0]!=null){//existe
                if(Integer.parseInt(datos[2])==Integer.parseInt(datos[3])){//si las existencias son menores a los diponibles hay un prestamo o una multa
                    //Colocar Info
                    txtNomA.setText(datos[1]);
                    txtExtA.setText(datos[2]);
                    txtDescA.setText(datos[4]);

                    lblHiddenID.setText(id);
                    lblhiddenNom.setText(datos[1]);
                    btnActualizar.setEnabled(true);
                    pnlBuscarArt.setVisible(false);
                    pnlActArt.setVisible(true);
                }else{
                    JOptionPane.showMessageDialog(this, "El artículo indicado se encuentra en prestamo o con un reporte\nSi tiene un reporte debe dar de baja el reporte para actualizarlo\nSi es un prestamo por favor espere a la devolución");
                }
            }else{
                JOptionPane.showMessageDialog(this, "No se encontro el artículo indicado");
            }
        }catch(SQLException e){
            System.out.println("Error al recuperar articulo: "+ e);
        }
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        String nom = txtNomA.getText().trim();
        String ext = txtExtA.getText().trim();
        String desc = txtDescA.getText().trim();
        
        if(!nom.isEmpty() && !ext.isEmpty() && !desc.isEmpty()){
            try{
                AccesorioDB artic = new AccesorioDB();
                if(artic.accesorioExist(nom)){
                    System.out.println("Esta usando un nombre existente");
                    if(nom.equals(lblhiddenNom.getText().trim())){
                        System.out.println("el nombre es el mismo no hay problema");
                        String[] datos = {lblHiddenID.getText().trim(), nom, ext, desc };
                        artic.updAccesorio(datos);
                        JOptionPane.showMessageDialog(this,"Se ha actualizado el registro de articulos");
                    }else{
                        JOptionPane.showMessageDialog(this, "El nombre que utiliza para el articulo ya esta registrado intente con un distinto");
                    }
                }else{
                    System.out.println("el nombre no es el mismo y no existe en db no hay problema");
                    String[] datos = {lblHiddenID.getText().trim(), nom, ext, desc };
                    artic.updAccesorio(datos);
                    JOptionPane.showMessageDialog(this,"Se ha actualizado el registro de articulos");
                }
            }catch(SQLException e){System.out.println("Error al cargar registros desde AccesoriosDB:"+e);}
        }else{
            JOptionPane.showMessageDialog(this,"No puede dejar ningun campo en blanco");
        }
        try{getTable();}catch(SQLException e){System.out.println("Error:"+e);}
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
            try{getPrestadosAcc();}catch(SQLException e){System.out.println("Error al recuperar todos los registros existentes de articulos: " + e);}
            pnlTodos.setVisible(true);
            pnlHoy.setVisible(false);
            jLabel6.setText("Artículos Prestados TODOS");
            tglReg.setText("Los registros de artículos de HOY");
        } else {
            try{getPrestadosHOY();}catch(SQLException e){System.out.println("Error al recuperar todos los registros existentes de articulos: " + e);}
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

    public void clearActualizar(){
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
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Articulo().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel bkArticulos;
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnBorrar;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnCerrar1;
    private javax.swing.JButton btnCerrar2;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JToggleButton btnMenu;
    private javax.swing.JButton btnRegresar;
    private javax.swing.JDialog dlgActualizar;
    private javax.swing.JDialog dlgBorrar;
    private javax.swing.JDialog dlgNuevo;
    private javax.swing.JLabel ico1;
    private javax.swing.JLabel ico2;
    private javax.swing.JLabel ico3;
    private javax.swing.JLabel ico4;
    private javax.swing.JLabel ico5;
    private javax.swing.JLabel ico6;
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
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTable5;
    private javax.swing.JLabel labelFecha;
    private javax.swing.JLabel labelHora;
    private javax.swing.JLabel lbl1;
    private javax.swing.JLabel lbl2;
    private javax.swing.JLabel lbl3;
    private javax.swing.JLabel lblHiddenID;
    private javax.swing.JLabel lblN1;
    private javax.swing.JLabel lblN2;
    private javax.swing.JLabel lblN3;
    private javax.swing.JLabel lblhiddenNom;
    private javax.swing.JPanel panelOPC;
    private javax.swing.JPanel pnlActArt;
    private javax.swing.JPanel pnlBtnActualizar;
    private javax.swing.JPanel pnlBtnBorrar;
    private javax.swing.JPanel pnlBtnNuevo;
    private javax.swing.JPanel pnlBuscarArt;
    private javax.swing.JPanel pnlCabecera;
    private javax.swing.JPanel pnlColor1;
    private javax.swing.JPanel pnlColor2;
    private javax.swing.JPanel pnlColor3;
    private javax.swing.JPanel pnlColor4;
    private javax.swing.JPanel pnlColor5;
    private javax.swing.JPanel pnlColor6;
    private javax.swing.JPanel pnlColor7;
    private javax.swing.JScrollPane pnlHoy;
    private javax.swing.JScrollPane pnlTodos;
    private javax.swing.JToggleButton tglReg;
    private javax.swing.JTextField txtBorrar;
    private javax.swing.JTextArea txtDesc;
    private javax.swing.JTextArea txtDescA;
    private javax.swing.JTextField txtExist;
    private javax.swing.JTextField txtExtA;
    private javax.swing.JTextField txtIDArt;
    private javax.swing.JTextField txtNom;
    private javax.swing.JTextField txtNomA;
    // End of variables declaration//GEN-END:variables
}
