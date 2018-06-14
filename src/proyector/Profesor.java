/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyector;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import proyector.dataBase.crud.DepartamentoDB;
import proyector.dataBase.crud.PrestamoDB;
import proyector.dataBase.crud.ProfesorDB;

/**
 *
 * @author JuanGSot
 */
public class Profesor extends javax.swing.JFrame {

    String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
    ImageIcon img = new ImageIcon("./src/imagenes/logo-adm.png");
    ImageIcon addW = new ImageIcon("./src/imagenes/Add New_36pxW.png");
    ImageIcon ersW = new ImageIcon("./src/imagenes/Erase_36pxW.png");
    ImageIcon updW = new ImageIcon("./src/imagenes/Edit File_36pxW.png");
    ImageIcon add = new ImageIcon("./src/imagenes/Add New_36px.png");
    ImageIcon ers = new ImageIcon("./src/imagenes/Erase_36px.png");
    ImageIcon upd = new ImageIcon("./src/imagenes/Edit File_36px.png");

    /**
     * Creates new form Profesor
     *
     * @throws java.sql.SQLException
     */
    public Profesor() throws SQLException {
        initComponents();
        getTable();                                 //genera tabla con los profesores
        panelOPC.setVisible(false);                 //menu de navegacion

        generarComboDepart();                       //recupera los datos de profesores y los coloca en un comboBox
        txtBusqueda.requestFocusInWindow();
        pnlDlgSeleccion.setVisible(true);           //paneles por defecto en dialogActualizar
        dlgPnlFormulario.setVisible(false);         //paneles por defecto en dialogActualizar
        lblHide.setVisible(false);                   //info oculta para actualizar

        labelFecha.setText(date);                   //coloca la fecha
        Timer timer = new Timer(500, (ActionEvent e) -> {
            reloj();                                //coloca la hora
        });
        timer.setRepeats(true);
        timer.setCoalesce(true);
        timer.setInitialDelay(0);
        timer.start();
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
     * @throws SQLException
     */
    public void getTable() throws SQLException {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        ProfesorDB profe = new ProfesorDB();

        String[] cols = {jTable1.getColumnName(0), jTable1.getColumnName(1), jTable1.getColumnName(2), jTable1.getColumnName(3), jTable1.getColumnName(4), jTable1.getColumnName(5)};
        int count = profe.getRegistros();
        System.out.println("\nRegistros existentes: " + count);

        String[][] data = new String[count][6];
        data = profe.getProfesores();
        for (int i = 0; i < count; i++) {
            for (int j = 0; j < 6; j++) {
                //data[i][j] = profe.getProfesores()[i][j];
                //probar a futuo almacenar en un hash y asignar el valor de acuerdo al k,v del id depart
                if (j == 4) {
                    data[i][j] = Boolean.valueOf(data[i][j]) ? "PLAZA" : "HONORARIOS";
                }
                if (j == 5) {
                    data[i][j] = new DepartamentoDB().getDepartamento(Integer.parseInt(profe.getProfesores()[i][j]))[0];
                }
            }
        }
        model.setDataVector(data, cols);
    }

    /**
     * introduce los departamentos en un comboBox para su uso en el dialog Nuevo
     *
     * @throws SQLException
     */
    public void generarComboDepart() throws SQLException {
        ProfesorDB profe = new ProfesorDB();
        HashMap<String, Integer> map = profe.segundoCombo();              //obtiene los datos 
        Map<String, Integer> map1 = new TreeMap<String, Integer>(map);  //ordena el HashMap
        jComboBox1.removeAllItems();
        jComboBox2.removeAllItems();
        for (String s : map1.keySet()) {   // agrega los datos al comboBox
            jComboBox1.addItem(s);
            jComboBox2.addItem(s);
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

        dlgNuevo = new javax.swing.JDialog();
        pnlBkNuevo = new javax.swing.JPanel();
        lblTitulo1 = new javax.swing.JLabel();
        btnCerrar = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        pnlColor4 = new javax.swing.JPanel();
        pnlColor5 = new javax.swing.JPanel();
        pnlFormulario = new javax.swing.JPanel();
        lblNombre = new javax.swing.JLabel();
        txtNom = new javax.swing.JTextField();
        lblAPat = new javax.swing.JLabel();
        txtAPat = new javax.swing.JTextField();
        lblAMat = new javax.swing.JLabel();
        txtAMat = new javax.swing.JTextField();
        rdBtnOpc1 = new javax.swing.JRadioButton();
        rdBtnOpc2 = new javax.swing.JRadioButton();
        lblId = new javax.swing.JLabel();
        txtId = new javax.swing.JTextField();
        lblDepart = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox<>();
        dlgBorrar = new javax.swing.JDialog();
        pnlBkBorrar = new javax.swing.JPanel();
        btnCerrar1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        txtBorrar = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btnBorrar = new javax.swing.JButton();
        dlgActualizar = new javax.swing.JDialog();
        pnlBkActualizar = new javax.swing.JPanel();
        btnCerrar2 = new javax.swing.JButton();
        pnlColor1 = new javax.swing.JPanel();
        pnlColor2 = new javax.swing.JPanel();
        pnlColor3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        btnActualizar = new javax.swing.JButton();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        pnlDlgSeleccion = new javax.swing.JPanel();
        btnDlgBuscarAct = new javax.swing.JButton();
        lblIdText = new javax.swing.JLabel();
        txtDlgBuscar = new javax.swing.JTextField();
        dlgPnlFormulario = new javax.swing.JPanel();
        txtNom1 = new javax.swing.JTextField();
        txtAPat1 = new javax.swing.JTextField();
        txtAMat1 = new javax.swing.JTextField();
        txtId1 = new javax.swing.JTextField();
        jComboBox2 = new javax.swing.JComboBox<>();
        lblNombre1 = new javax.swing.JLabel();
        lblAPat1 = new javax.swing.JLabel();
        lblAMat1 = new javax.swing.JLabel();
        lblId1 = new javax.swing.JLabel();
        lblAMat2 = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        lblHide = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jComboBox4 = new javax.swing.JComboBox<>();
        fcProfCSV = new javax.swing.JFileChooser();
        pnlBkProfesor = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        logo1 = new javax.swing.JLabel();
        logo2 = new javax.swing.JLabel();
        lblHora = new javax.swing.JLabel();
        labelHora = new javax.swing.JLabel();
        lblFecha = new javax.swing.JLabel();
        labelFecha = new javax.swing.JLabel();
        btnMenu = new javax.swing.JToggleButton();
        btnRegresar = new javax.swing.JButton();
        panelOPC = new javax.swing.JPanel();
        ico3 = new javax.swing.JLabel();
        ico4 = new javax.swing.JLabel();
        ico5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        lblTitulo = new javax.swing.JLabel();
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
        lblInstrucciones = new javax.swing.JLabel();
        btnBusqueda = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        comboFiltro = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        txtBusqueda = new javax.swing.JTextField();
        btnAddCSV = new javax.swing.JButton();

        dlgNuevo.setMinimumSize(new java.awt.Dimension(700, 370));
        dlgNuevo.setModal(true);
        dlgNuevo.setUndecorated(true);
        dlgNuevo.setResizable(false);

        pnlBkNuevo.setBackground(new java.awt.Color(255, 255, 255));
        pnlBkNuevo.setMinimumSize(new java.awt.Dimension(700, 370));
        pnlBkNuevo.setPreferredSize(new java.awt.Dimension(700, 370));

        lblTitulo1.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        lblTitulo1.setText("Nuevo Profesor");

        btnCerrar.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        btnCerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Close_22px.png"))); // NOI18N
        btnCerrar.setText("Cerrar");
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });

        btnGuardar.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Save_22px.png"))); // NOI18N
        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        pnlColor4.setBackground(new java.awt.Color(1, 200, 1));

        javax.swing.GroupLayout pnlColor4Layout = new javax.swing.GroupLayout(pnlColor4);
        pnlColor4.setLayout(pnlColor4Layout);
        pnlColor4Layout.setHorizontalGroup(
            pnlColor4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 70, Short.MAX_VALUE)
        );
        pnlColor4Layout.setVerticalGroup(
            pnlColor4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 420, Short.MAX_VALUE)
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
            .addGap(0, 420, Short.MAX_VALUE)
        );

        pnlFormulario.setOpaque(false);
        pnlFormulario.setLayout(new java.awt.GridBagLayout());

        lblNombre.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        lblNombre.setText("Nombre");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        pnlFormulario.add(lblNombre, gridBagConstraints);

        txtNom.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtNom.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 191, 255)));
        txtNom.setMinimumSize(new java.awt.Dimension(200, 30));
        txtNom.setPreferredSize(new java.awt.Dimension(200, 30));
        txtNom.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNomKeyTyped(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 35, 5, 0);
        pnlFormulario.add(txtNom, gridBagConstraints);

        lblAPat.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        lblAPat.setText("Apellido Paterno");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        pnlFormulario.add(lblAPat, gridBagConstraints);

        txtAPat.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtAPat.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 191, 255)));
        txtAPat.setMinimumSize(new java.awt.Dimension(200, 30));
        txtAPat.setPreferredSize(new java.awt.Dimension(200, 30));
        txtAPat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAPatKeyTyped(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 35, 5, 0);
        pnlFormulario.add(txtAPat, gridBagConstraints);

        lblAMat.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        lblAMat.setText("Apellido Materno");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        pnlFormulario.add(lblAMat, gridBagConstraints);

        txtAMat.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtAMat.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 191, 255)));
        txtAMat.setMinimumSize(new java.awt.Dimension(200, 30));
        txtAMat.setPreferredSize(new java.awt.Dimension(200, 30));
        txtAMat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAMatKeyTyped(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 35, 5, 0);
        pnlFormulario.add(txtAMat, gridBagConstraints);

        rdBtnOpc1.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        rdBtnOpc1.setText("Utilizar un Número Identificador");
        rdBtnOpc1.setOpaque(false);
        rdBtnOpc1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdBtnOpc1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(0, 25, 0, 0);
        pnlFormulario.add(rdBtnOpc1, gridBagConstraints);

        rdBtnOpc2.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        rdBtnOpc2.setText("Generar Número Identificador");
        rdBtnOpc2.setOpaque(false);
        rdBtnOpc2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdBtnOpc2ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        pnlFormulario.add(rdBtnOpc2, gridBagConstraints);

        lblId.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        lblId.setText("Número Identificador");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        pnlFormulario.add(lblId, gridBagConstraints);

        txtId.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtId.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(171, 173, 179)));
        txtId.setEnabled(false);
        txtId.setMinimumSize(new java.awt.Dimension(200, 30));
        txtId.setPreferredSize(new java.awt.Dimension(200, 30));
        txtId.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtIdKeyTyped(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 35, 5, 0);
        pnlFormulario.add(txtId, gridBagConstraints);

        lblDepart.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        lblDepart.setText("Departamento");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        pnlFormulario.add(lblDepart, gridBagConstraints);

        jComboBox1.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jComboBox1.setMaximumRowCount(10);
        jComboBox1.setMinimumSize(new java.awt.Dimension(200, 30));
        jComboBox1.setPreferredSize(new java.awt.Dimension(200, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 35, 5, 0);
        pnlFormulario.add(jComboBox1, gridBagConstraints);

        jLabel6.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel6.setText("Estado Laboral");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        pnlFormulario.add(jLabel6, gridBagConstraints);

        jComboBox3.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "PLAZA", "HONORARIOS" }));
        jComboBox3.setMinimumSize(new java.awt.Dimension(200, 30));
        jComboBox3.setPreferredSize(new java.awt.Dimension(200, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 35, 5, 0);
        pnlFormulario.add(jComboBox3, gridBagConstraints);

        javax.swing.GroupLayout pnlBkNuevoLayout = new javax.swing.GroupLayout(pnlBkNuevo);
        pnlBkNuevo.setLayout(pnlBkNuevoLayout);
        pnlBkNuevoLayout.setHorizontalGroup(
            pnlBkNuevoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBkNuevoLayout.createSequentialGroup()
                .addComponent(pnlColor4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(pnlColor5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pnlBkNuevoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlBkNuevoLayout.createSequentialGroup()
                        .addGap(161, 161, 161)
                        .addComponent(lblTitulo1))
                    .addGroup(pnlBkNuevoLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(pnlFormulario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlBkNuevoLayout.createSequentialGroup()
                        .addComponent(btnGuardar)
                        .addGap(273, 273, 273)
                        .addComponent(btnCerrar))))
        );
        pnlBkNuevoLayout.setVerticalGroup(
            pnlBkNuevoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlColor4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(pnlColor5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(pnlBkNuevoLayout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(lblTitulo1)
                .addGap(18, 18, 18)
                .addComponent(pnlFormulario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17)
                .addGroup(pnlBkNuevoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnGuardar)
                    .addComponent(btnCerrar)))
        );

        javax.swing.GroupLayout dlgNuevoLayout = new javax.swing.GroupLayout(dlgNuevo.getContentPane());
        dlgNuevo.getContentPane().setLayout(dlgNuevoLayout);
        dlgNuevoLayout.setHorizontalGroup(
            dlgNuevoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlBkNuevo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        dlgNuevoLayout.setVerticalGroup(
            dlgNuevoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlBkNuevo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        dlgNuevo.getAccessibleContext().setAccessibleParent(this);

        dlgBorrar.setMinimumSize(new java.awt.Dimension(599, 300));
        dlgBorrar.setModal(true);
        dlgBorrar.setUndecorated(true);
        dlgBorrar.setResizable(false);

        pnlBkBorrar.setBackground(new java.awt.Color(255, 127, 80));
        pnlBkBorrar.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnCerrar1.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        btnCerrar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Close_22px.png"))); // NOI18N
        btnCerrar1.setText("Cerrar");
        btnCerrar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrar1ActionPerformed(evt);
            }
        });
        pnlBkBorrar.add(btnCerrar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(436, 220, -1, -1));

        jLabel1.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Borrar Profesor");
        pnlBkBorrar.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(262, 27, -1, -1));

        jPanel4.setBackground(new java.awt.Color(12, 193, 243));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 70, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        pnlBkBorrar.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 70, 300));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 31, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        pnlBkBorrar.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 0, -1, 300));

        txtBorrar.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        txtBorrar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 103, 96)));
        txtBorrar.setMinimumSize(new java.awt.Dimension(200, 30));
        txtBorrar.setPreferredSize(new java.awt.Dimension(200, 30));
        txtBorrar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtBorrarKeyTyped(evt);
            }
        });
        pnlBkBorrar.add(txtBorrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(313, 113, -1, -1));

        jLabel2.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Número Identificador");
        pnlBkBorrar.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 120, -1, -1));

        jLabel3.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Debe ingresar el numero identificador del profesor");
        pnlBkBorrar.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 270, -1, -1));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/More Info_22px.png"))); // NOI18N
        pnlBkBorrar.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 270, -1, -1));

        btnBorrar.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        btnBorrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Waste_24px.png"))); // NOI18N
        btnBorrar.setText("Eliminar");
        btnBorrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBorrarActionPerformed(evt);
            }
        });
        pnlBkBorrar.add(btnBorrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(193, 220, -1, -1));

        javax.swing.GroupLayout dlgBorrarLayout = new javax.swing.GroupLayout(dlgBorrar.getContentPane());
        dlgBorrar.getContentPane().setLayout(dlgBorrarLayout);
        dlgBorrarLayout.setHorizontalGroup(
            dlgBorrarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlBkBorrar, javax.swing.GroupLayout.DEFAULT_SIZE, 599, Short.MAX_VALUE)
        );
        dlgBorrarLayout.setVerticalGroup(
            dlgBorrarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlBkBorrar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        dlgBorrar.getAccessibleContext().setAccessibleParent(this);

        dlgActualizar.setMinimumSize(new java.awt.Dimension(700, 400));
        dlgActualizar.setModal(true);
        dlgActualizar.setUndecorated(true);
        dlgActualizar.setResizable(false);

        pnlBkActualizar.setBackground(new java.awt.Color(0, 191, 255));
        pnlBkActualizar.setMinimumSize(new java.awt.Dimension(700, 400));
        pnlBkActualizar.setPreferredSize(new java.awt.Dimension(700, 400));
        pnlBkActualizar.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnCerrar2.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnCerrar2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Close_22px.png"))); // NOI18N
        btnCerrar2.setText("Cerrar");
        btnCerrar2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrar2ActionPerformed(evt);
            }
        });
        pnlBkActualizar.add(btnCerrar2, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 360, -1, -1));

        pnlColor1.setBackground(new java.awt.Color(253, 184, 19));

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

        pnlBkActualizar.add(pnlColor1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 400));

        pnlColor2.setBackground(new java.awt.Color(154, 205, 50));

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

        pnlBkActualizar.add(pnlColor2, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 0, -1, 400));

        pnlColor3.setBackground(new java.awt.Color(1, 200, 1));

        javax.swing.GroupLayout pnlColor3Layout = new javax.swing.GroupLayout(pnlColor3);
        pnlColor3.setLayout(pnlColor3Layout);
        pnlColor3Layout.setHorizontalGroup(
            pnlColor3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 15, Short.MAX_VALUE)
        );
        pnlColor3Layout.setVerticalGroup(
            pnlColor3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        pnlBkActualizar.add(pnlColor3, new org.netbeans.lib.awtextra.AbsoluteConstraints(125, 0, -1, 400));

        jLabel5.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Actualizar Profesor");
        pnlBkActualizar.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 10, -1, -1));

        btnActualizar.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnActualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Pencil_22px.png"))); // NOI18N
        btnActualizar.setText("Actualizar");
        btnActualizar.setEnabled(false);
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });
        pnlBkActualizar.add(btnActualizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 360, -1, -1));

        pnlDlgSeleccion.setBackground(new java.awt.Color(102, 204, 255));
        pnlDlgSeleccion.setOpaque(false);

        btnDlgBuscarAct.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        btnDlgBuscarAct.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Pencil_22px.png"))); // NOI18N
        btnDlgBuscarAct.setText("Actualizar");
        btnDlgBuscarAct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDlgBuscarActActionPerformed(evt);
            }
        });

        lblIdText.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        lblIdText.setForeground(new java.awt.Color(255, 255, 255));
        lblIdText.setText("Número Identificador");

        txtDlgBuscar.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtDlgBuscar.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtDlgBuscar.setMinimumSize(new java.awt.Dimension(220, 28));
        txtDlgBuscar.setPreferredSize(new java.awt.Dimension(220, 28));
        txtDlgBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDlgBuscarKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout pnlDlgSeleccionLayout = new javax.swing.GroupLayout(pnlDlgSeleccion);
        pnlDlgSeleccion.setLayout(pnlDlgSeleccionLayout);
        pnlDlgSeleccionLayout.setHorizontalGroup(
            pnlDlgSeleccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDlgSeleccionLayout.createSequentialGroup()
                .addGroup(pnlDlgSeleccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlDlgSeleccionLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblIdText))
                    .addGroup(pnlDlgSeleccionLayout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(txtDlgBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(49, 49, 49))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDlgSeleccionLayout.createSequentialGroup()
                .addComponent(btnDlgBuscarAct)
                .addGap(95, 95, 95))
        );
        pnlDlgSeleccionLayout.setVerticalGroup(
            pnlDlgSeleccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDlgSeleccionLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(lblIdText)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtDlgBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnDlgBuscarAct)
                .addGap(25, 25, 25))
        );

        dlgPnlFormulario.setBackground(new java.awt.Color(0, 191, 255));
        dlgPnlFormulario.setLayout(new java.awt.GridBagLayout());

        txtNom1.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        txtNom1.setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 5, 1, 1));
        txtNom1.setMinimumSize(new java.awt.Dimension(200, 30));
        txtNom1.setPreferredSize(new java.awt.Dimension(200, 30));
        txtNom1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNom1KeyTyped(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 10, 0);
        dlgPnlFormulario.add(txtNom1, gridBagConstraints);

        txtAPat1.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        txtAPat1.setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 5, 1, 1));
        txtAPat1.setMinimumSize(new java.awt.Dimension(200, 30));
        txtAPat1.setPreferredSize(new java.awt.Dimension(200, 30));
        txtAPat1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAPat1KeyTyped(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 10, 0);
        dlgPnlFormulario.add(txtAPat1, gridBagConstraints);

        txtAMat1.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        txtAMat1.setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 5, 1, 1));
        txtAMat1.setMinimumSize(new java.awt.Dimension(200, 30));
        txtAMat1.setPreferredSize(new java.awt.Dimension(200, 30));
        txtAMat1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAMat1KeyTyped(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 10, 0);
        dlgPnlFormulario.add(txtAMat1, gridBagConstraints);

        txtId1.setFont(new java.awt.Font("Verdana", 1, 11)); // NOI18N
        txtId1.setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 5, 1, 1));
        txtId1.setEnabled(false);
        txtId1.setMinimumSize(new java.awt.Dimension(200, 30));
        txtId1.setPreferredSize(new java.awt.Dimension(200, 30));
        txtId1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtId1KeyTyped(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 10, 0);
        dlgPnlFormulario.add(txtId1, gridBagConstraints);

        jComboBox2.setFont(new java.awt.Font("Verdana", 0, 11)); // NOI18N
        jComboBox2.setMaximumRowCount(10);
        jComboBox2.setMinimumSize(new java.awt.Dimension(200, 30));
        jComboBox2.setPreferredSize(new java.awt.Dimension(200, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 10, 0);
        dlgPnlFormulario.add(jComboBox2, gridBagConstraints);

        lblNombre1.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        lblNombre1.setForeground(new java.awt.Color(255, 255, 255));
        lblNombre1.setText("Nombre");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        dlgPnlFormulario.add(lblNombre1, gridBagConstraints);

        lblAPat1.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        lblAPat1.setForeground(new java.awt.Color(255, 255, 255));
        lblAPat1.setText("Apellido Paterno");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        dlgPnlFormulario.add(lblAPat1, gridBagConstraints);

        lblAMat1.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        lblAMat1.setForeground(new java.awt.Color(255, 255, 255));
        lblAMat1.setText("Apellido Materno");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        dlgPnlFormulario.add(lblAMat1, gridBagConstraints);

        lblId1.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        lblId1.setForeground(new java.awt.Color(255, 255, 255));
        lblId1.setText("Número Identificador");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        dlgPnlFormulario.add(lblId1, gridBagConstraints);

        lblAMat2.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        lblAMat2.setForeground(new java.awt.Color(255, 255, 255));
        lblAMat2.setText("Departamento");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        dlgPnlFormulario.add(lblAMat2, gridBagConstraints);

        jCheckBox1.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jCheckBox1.setText("Modificar?");
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        dlgPnlFormulario.add(jCheckBox1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        dlgPnlFormulario.add(lblHide, gridBagConstraints);

        jLabel10.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Estado Laboral");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        dlgPnlFormulario.add(jLabel10, gridBagConstraints);

        jComboBox4.setFont(new java.awt.Font("Verdana", 0, 11)); // NOI18N
        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "PLAZA", "HONORARIOS" }));
        jComboBox4.setMinimumSize(new java.awt.Dimension(200, 30));
        jComboBox4.setPreferredSize(new java.awt.Dimension(200, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 10, 0);
        dlgPnlFormulario.add(jComboBox4, gridBagConstraints);

        jLayeredPane1.setLayer(pnlDlgSeleccion, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(dlgPnlFormulario, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 540, Short.MAX_VALUE)
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jLayeredPane1Layout.createSequentialGroup()
                    .addGap(0, 115, Short.MAX_VALUE)
                    .addComponent(pnlDlgSeleccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 115, Short.MAX_VALUE)))
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jLayeredPane1Layout.createSequentialGroup()
                    .addGap(0, 48, Short.MAX_VALUE)
                    .addComponent(dlgPnlFormulario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 49, Short.MAX_VALUE)))
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 320, Short.MAX_VALUE)
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jLayeredPane1Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(pnlDlgSeleccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jLayeredPane1Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(dlgPnlFormulario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        pnlBkActualizar.add(jLayeredPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(147, 41, -1, 320));

        javax.swing.GroupLayout dlgActualizarLayout = new javax.swing.GroupLayout(dlgActualizar.getContentPane());
        dlgActualizar.getContentPane().setLayout(dlgActualizarLayout);
        dlgActualizarLayout.setHorizontalGroup(
            dlgActualizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlBkActualizar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        dlgActualizarLayout.setVerticalGroup(
            dlgActualizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlBkActualizar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        dlgActualizar.getAccessibleContext().setAccessibleParent(this);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("[Profesores]");
        setIconImage(img.getImage());
        setMinimumSize(new java.awt.Dimension(1024, 600));
        setResizable(false);

        pnlBkProfesor.setBackground(new java.awt.Color(255, 255, 255));
        pnlBkProfesor.setFont(new java.awt.Font("Verdana", 0, 11)); // NOI18N
        pnlBkProfesor.setMinimumSize(new java.awt.Dimension(1024, 600));
        pnlBkProfesor.setPreferredSize(new java.awt.Dimension(1024, 600));
        pnlBkProfesor.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(1, 200, 1));
        jPanel1.setFont(new java.awt.Font("Verdana", 0, 11)); // NOI18N
        jPanel1.setMaximumSize(new java.awt.Dimension(1024, 83));
        jPanel1.setMinimumSize(new java.awt.Dimension(1024, 83));
        jPanel1.setPreferredSize(new java.awt.Dimension(1024, 83));

        logo1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/tecnm2.png"))); // NOI18N

        logo2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/sep.png"))); // NOI18N

        lblHora.setFont(new java.awt.Font("Trebuchet MS", 1, 24)); // NOI18N
        lblHora.setForeground(new java.awt.Color(255, 255, 255));
        lblHora.setText("Hora");

        labelHora.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        labelHora.setForeground(new java.awt.Color(255, 255, 255));
        labelHora.setText("00:00:00 PM");

        lblFecha.setFont(new java.awt.Font("Trebuchet MS", 1, 24)); // NOI18N
        lblFecha.setForeground(new java.awt.Color(255, 255, 255));
        lblFecha.setText("Fecha");

        labelFecha.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        labelFecha.setForeground(new java.awt.Color(255, 255, 255));
        labelFecha.setText("00-00-0000");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(logo1)
                .addGap(36, 36, 36)
                .addComponent(lblHora)
                .addGap(20, 20, 20)
                .addComponent(labelHora)
                .addGap(107, 107, 107)
                .addComponent(lblFecha)
                .addGap(20, 20, 20)
                .addComponent(labelFecha)
                .addGap(36, 36, 36)
                .addComponent(logo2)
                .addGap(82, 82, 82))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(logo1)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblHora)
                            .addComponent(labelHora)
                            .addComponent(lblFecha)
                            .addComponent(labelFecha)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(logo2)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlBkProfesor.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        btnMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Checklist_24px.png"))); // NOI18N
        btnMenu.setMaximumSize(new java.awt.Dimension(40, 37));
        btnMenu.setMinimumSize(new java.awt.Dimension(40, 37));
        btnMenu.setPreferredSize(new java.awt.Dimension(40, 37));
        btnMenu.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                btnMenuItemStateChanged(evt);
            }
        });
        pnlBkProfesor.add(btnMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 101, -1, -1));

        btnRegresar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Back To_35px.png"))); // NOI18N
        btnRegresar.setMaximumSize(new java.awt.Dimension(40, 37));
        btnRegresar.setMinimumSize(new java.awt.Dimension(40, 37));
        btnRegresar.setPreferredSize(new java.awt.Dimension(40, 37));
        btnRegresar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnRegresarMouseClicked(evt);
            }
        });
        pnlBkProfesor.add(btnRegresar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 540, -1, -1));

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

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/depart_42px.png"))); // NOI18N
        jLabel7.setToolTipText("Departamentos");
        jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel7MouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(15, 0, 5, 0);
        panelOPC.add(jLabel7, gridBagConstraints);

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/aul_42px.png"))); // NOI18N
        jLabel8.setToolTipText("Aulas");
        jLabel8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel8MouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
        panelOPC.add(jLabel8, gridBagConstraints);

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Video Projector_42px.png"))); // NOI18N
        jLabel9.setToolTipText("Videoproyectores");
        jLabel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel9MouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 15, 0);
        panelOPC.add(jLabel9, gridBagConstraints);

        pnlBkProfesor.add(panelOPC, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 152, -1, -1));

        lblTitulo.setFont(new java.awt.Font("Trebuchet MS", 1, 24)); // NOI18N
        lblTitulo.setText("Profesores");
        pnlBkProfesor.add(lblTitulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(448, 109, -1, -1));

        pnlBtnNuevo.setBackground(new java.awt.Color(239, 239, 239));
        pnlBtnNuevo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(100, 100, 100)));
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

        pnlBkProfesor.add(pnlBtnNuevo, new org.netbeans.lib.awtextra.AbsoluteConstraints(912, 144, -1, -1));

        pnlBtnBorrar.setBackground(new java.awt.Color(239, 239, 239));
        pnlBtnBorrar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(100, 100, 100)));
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

        pnlBkProfesor.add(pnlBtnBorrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 270, -1, -1));

        pnlBtnActualizar.setBackground(new java.awt.Color(239, 239, 239));
        pnlBtnActualizar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(100, 100, 100)));
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

        pnlBkProfesor.add(pnlBtnActualizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 390, -1, -1));

        lblInstrucciones.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblInstrucciones.setText("Filtrar información:");
        pnlBkProfesor.add(lblInstrucciones, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 150, -1, -1));

        btnBusqueda.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        btnBusqueda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Search_25px.png"))); // NOI18N
        btnBusqueda.setText("Buscar");
        btnBusqueda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBusquedaActionPerformed(evt);
            }
        });
        pnlBkProfesor.add(btnBusqueda, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 170, -1, -1));

        jTable1.setFont(new java.awt.Font("Verdana", 0, 11)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID_PROFESOR", "NOMBRE", "A_PATERNO", "A_MATERNO", "EST. LABORAL", "DEPARTAMENTO"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setResizable(false);
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(15);
            jTable1.getColumnModel().getColumn(1).setResizable(false);
            jTable1.getColumnModel().getColumn(2).setResizable(false);
            jTable1.getColumnModel().getColumn(3).setResizable(false);
            jTable1.getColumnModel().getColumn(4).setResizable(false);
            jTable1.getColumnModel().getColumn(5).setResizable(false);
            jTable1.getColumnModel().getColumn(5).setPreferredWidth(20);
        }

        pnlBkProfesor.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 220, 824, 353));

        comboFiltro.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ID_PROFESOR", "NOMBRE", "APELLIDO PATERNO", "APELLIDO MATERNO", "ESTATUS LABORAL", "DEPARTAMENTO" }));
        comboFiltro.setMinimumSize(new java.awt.Dimension(125, 30));
        comboFiltro.setPreferredSize(new java.awt.Dimension(125, 30));
        pnlBkProfesor.add(comboFiltro, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 170, 160, -1));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/borrar-búsqueda-24.png"))); // NOI18N
        jButton1.setPreferredSize(new java.awt.Dimension(45, 35));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        pnlBkProfesor.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 170, -1, -1));

        jPanel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(190, 190, 190), 2, true));
        jPanel2.setPreferredSize(new java.awt.Dimension(204, 37));

        txtBusqueda.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtBusqueda.setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 5, 1, 1));
        txtBusqueda.setMinimumSize(new java.awt.Dimension(200, 33));
        txtBusqueda.setPreferredSize(new java.awt.Dimension(200, 33));
        txtBusqueda.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtBusquedaKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(txtBusqueda, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(txtBusqueda, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlBkProfesor.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(245, 168, -1, -1));

        btnAddCSV.setText("<html>Agregar<br>Muchos<br>Muchisimos<br>profesores</html>");
        btnAddCSV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddCSVActionPerformed(evt);
            }
        });
        pnlBkProfesor.add(btnAddCSV, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 520, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlBkProfesor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlBkProfesor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MouseClicked
        try {
            Aula aula = new Aula();
            aula.setVisible(true);
            this.setVisible(false);
            this.dispose();
        } catch (SQLException ex) {
            Logger.getLogger(Departamento.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jLabel8MouseClicked

    private void jLabel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseClicked
        try {
            Departamento depart = new Departamento();
            depart.setVisible(true);
            this.setVisible(false);
            this.dispose();
        } catch (SQLException ex) {
            Logger.getLogger(Profesor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jLabel7MouseClicked

    private void btnRegresarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRegresarMouseClicked
        Menu menu = new Menu();
        menu.setVisible(true);
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_btnRegresarMouseClicked

    private void pnlBtnNuevoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlBtnNuevoMouseClicked
        pnlBtnNuevo.setBackground(new Color(240, 240, 240));
        ico1.setIcon(add);

        dlgNuevo.setLocationRelativeTo(pnlBkProfesor);
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

        dlgBorrar.setLocationRelativeTo(pnlBkProfesor);
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
        lblHide.setVisible(false);
        dlgActualizar.setLocationRelativeTo(pnlBkProfesor);
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
        //cierra modal
        dlgActualizar.setVisible(false);
        dlgActualizar.dispose();
        //cambia panel 
        pnlDlgSeleccion.setVisible(true);
        dlgPnlFormulario.setVisible(false);
        //desabilita boton actualizar
        btnActualizar.setEnabled(false);
        txtDlgBuscar.setText("");
    }//GEN-LAST:event_btnCerrar2ActionPerformed

    private void btnMenuItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_btnMenuItemStateChanged
        if (btnMenu.isSelected()) {
            panelOPC.setVisible(true);
        } else {
            panelOPC.setVisible(false);
        }
    }//GEN-LAST:event_btnMenuItemStateChanged

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        String nom = txtNom.getText().trim();
        String apat = txtAPat.getText().trim();
        String amat = txtAMat.getText().trim();
        String id = txtId.getText().trim();
        if ((nom.length() < 26) && (apat.length() < 26) && (amat.length() < 26) && (id.length() < 13)) {
            try {
                if (jComboBox1.getItemCount() == 0) {
                    JOptionPane.showMessageDialog(null, "No es posible registrar profesores\nhasta que haya creado un departamento", "Información", JOptionPane.INFORMATION_MESSAGE);
                    dlgNuevo.setVisible(false);
                    dlgNuevo.dispose();
                    this.setVisible(false);
                    this.dispose();
                    Departamento depart = new Departamento();
                    depart.setVisible(true);

                } else {
                    if (!nom.isEmpty() && !apat.isEmpty() && !amat.isEmpty() && !id.isEmpty()) {
                        ProfesorDB profe = new ProfesorDB();
                        HashMap<String, Integer> map = profe.segundoCombo();

                        int seleccion = map.get(jComboBox1.getSelectedItem().toString());
                        String honorarioPlaza = "true";
                        if (jComboBox3.getSelectedIndex() == 0) {
                            honorarioPlaza = "true"; //plaza
                        } else if (jComboBox3.getSelectedIndex() == 1) {
                            honorarioPlaza = "false";
                        }

                        String datos[] = {id, String.valueOf(seleccion), nom, apat, amat, honorarioPlaza};
                        System.out.println("array de datos: " + Arrays.toString(datos));
                        if (rdBtnOpc1.isSelected()) {
                            System.out.println("ya seleccionaste la opcion 1 ahora veamos si existe el id:" + profe.getExisteProfesor(id));
                            if (profe.getExisteProfesor(id)) {
                                JOptionPane.showMessageDialog(null, "El numero identificador ya existe en la base de datos\nPrueba a ingresar uno diferente.", "Error", JOptionPane.ERROR_MESSAGE);
                            } else {
                                profe.setProfesor(datos);
                                cerrarDlgNuevoProfesor();
                                JOptionPane.showMessageDialog(null, "Se ha registrado el nuevo profesor.");
                            }
                        } else {
                            profe.setProfesor(datos);
                            cerrarDlgNuevoProfesor();
                            JOptionPane.showMessageDialog(null, "Se ha registrado el nuevo profesor.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Error!!!\nNo puede dejar ningun campo en blanco.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(Profesor.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Verifique la longitud de los datos ingresados");
            try {
                cerrarDlgNuevoProfesor();
            } catch (SQLException ex) {
                Logger.getLogger(Profesor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void cerrarDlgNuevoProfesor() throws SQLException {
        // limpiar cuadros de texto
        rdBtnOpc1.setSelected(false);
        rdBtnOpc2.setSelected(false);
        txtNom.setText("");
        txtAPat.setText("");
        txtAMat.setText("");
        txtId.setText("");
        //cerrar dialog
        dlgNuevo.setVisible(false);
        dlgNuevo.dispose();
        System.out.println("dibujo tabla nuevamente");
        getTable();
        txtBusqueda.requestFocusInWindow();
    }

    private void rdBtnOpc1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdBtnOpc1ActionPerformed
        rdBtnOpc2.setSelected(false);
        txtId.setText("");
        txtId.setEnabled(true);
    }//GEN-LAST:event_rdBtnOpc1ActionPerformed

    private void rdBtnOpc2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdBtnOpc2ActionPerformed
        try {
            txtId.setEnabled(false);
            rdBtnOpc1.setSelected(false);
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyMM");
            LocalDate localDate = LocalDate.now();
            //1000 + (int)(Math.random() * ((9999 - 1000) + 1))
            String date = dtf.format(localDate);
            int a = 1000 + (int) (Math.random() * ((9999 - 1000) + 1));
            String info = date + a;
            ProfesorDB profe = new ProfesorDB();
            do {
                a = 1000 + (int) (Math.random() * ((9999 - 1000) + 1));
                info = date + a;
            } while (profe.getExisteProfesor(info));
            txtId.setText(info);
        } catch (SQLException ex) {
            Logger.getLogger(Profesor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_rdBtnOpc2ActionPerformed

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
        String usuario = txtBorrar.getText().trim();
        if (usuario.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Profesor no encontrado!!!\nRectifique el numero identificador.", "Error", JOptionPane.ERROR_MESSAGE);
            txtBorrar.setText("");
        } else {
            int opc = JOptionPane.showConfirmDialog(null, "Es una accion irreversible!!!\n\nSI HACE ESTO SE BORRARAN TODOS LOS REGISTROS QUE SE HICIERON CON ESTE VIDEOPROYECTOR\n\nEstá seguro que desea eliminar?", "Advertencia", JOptionPane.WARNING_MESSAGE, JOptionPane.YES_NO_OPTION);

            if (opc == JOptionPane.YES_OPTION && !usuario.isEmpty()) {
                try {
                    ProfesorDB profe = new ProfesorDB();
                    PrestamoDB prestamo = new PrestamoDB();
                    if (profe.getExisteProfesor(usuario)) {
                        if(!prestamo.getExistePrestamo(usuario)){ //si profesor no tiene un prestamo vigente
                            profe.destroyProf(usuario);
                            JOptionPane.showMessageDialog(null, "Se ha eliminado el registro");
                            getTable();
                            
                            dlgBorrar.setVisible(false);
                            dlgBorrar.dispose();
                            System.out.println("dibujo tabla nuevamente");
                            txtBorrar.setText("");
                            txtBusqueda.requestFocusInWindow();
                        }else{
                            JOptionPane.showMessageDialog(null, "No se puede eliminar!!!\nEl profesor ha solicitado un proyector y no lo ha devuelto\n\nRECUERDE QUE ESTA ACCION ELIMINARA TODOS LOS REGISTROS RELACIONADOS");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Profesor no encontrado!!!\nRectifique el numero identificador.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(Profesor.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                JOptionPane.showMessageDialog(null, "No se ha borrado el registro\n\nRecuerde que esta accion es irreversible", "Información", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        
    }//GEN-LAST:event_btnBorrarActionPerformed

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        if (jCheckBox1.isSelected()) {
            txtId1.setEnabled(true);
        } else {
            txtId1.setEnabled(false);
        }
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    private void txtDlgBuscarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDlgBuscarKeyTyped
        if (txtDlgBuscar.getText().length() >= 12) {
            evt.consume();
        }
        char caracter = evt.getKeyChar();
        if (((caracter < '0') || (caracter > '9')) && (caracter != '\b')) {
            evt.consume();
        }
        if (caracter == KeyEvent.VK_ENTER){
            btnDlgBuscarAct.doClick();
        }
    }//GEN-LAST:event_txtDlgBuscarKeyTyped

    private void btnDlgBuscarActActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDlgBuscarActActionPerformed
        try {
            //buscar profesor y actualizar
            ProfesorDB profe = new ProfesorDB();
            String[] datos;
            if (profe.getExisteProfesor(txtDlgBuscar.getText().trim())) {
                //recupera info
                datos = profe.getProfesor(txtDlgBuscar.getText().trim());
                //asigna valores
                txtNom1.setText(datos[2]);
                txtAPat1.setText(datos[3]);
                txtAMat1.setText(datos[4]);
                txtId1.setText(datos[0]);
                jComboBox2.setSelectedItem(new DepartamentoDB().getDepartamento(Integer.parseInt(datos[1])));
                jComboBox4.setSelectedIndex((Boolean.valueOf(datos[5]) ? 0 : 1));
                txtDlgBuscar.setText("");
                //cambia la vista a formulario
                btnActualizar.setEnabled(true);
                pnlDlgSeleccion.setVisible(false);
                dlgPnlFormulario.setVisible(true);
                lblHide.setText(datos[0]);
            } else {
                JOptionPane.showMessageDialog(null, "Profesor no encontrado!!!\nRectifique el numero identificador.", "Error", JOptionPane.ERROR_MESSAGE);
                txtDlgBuscar.setText("");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Profesor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnDlgBuscarActActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        try {
            dlgActualizar.setVisible(false);;
            dlgActualizar.dispose();
            ProfesorDB profe = new ProfesorDB();
            HashMap<String, Integer> map = profe.segundoCombo();
            String honorarioPlaza = "true";
            if (jComboBox4.getSelectedIndex() == 0) {
                honorarioPlaza = "true"; //plaza
            } else if (jComboBox4.getSelectedIndex() == 1) {
                honorarioPlaza = "false";
            }
            String seleccion = map.get(jComboBox2.getSelectedItem().toString()).toString();          //departamento
            String[] datos = {seleccion, txtNom1.getText(), txtAPat1.getText(), txtAMat1.getText(), honorarioPlaza};  //datos para actualizar profe
            String newId = txtId1.getText().trim();                                          //cambiar de string a int newId
            String oldId = lblHide.getText().trim();                                         //cambiar de string a int oldId

            if (!txtNom1.getText().trim().isEmpty() && !txtAPat1.getText().trim().isEmpty() && !txtAMat1.getText().trim().isEmpty()) {
                if (jCheckBox1.isSelected()) {
                    if (profe.getExisteProfesor(newId)) {
                        JOptionPane.showMessageDialog(null, "Error!!!\nNo puede utilizar un identificador existente", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        profe.updProfesor(datos, oldId, newId);
                        cerrarActualizarDialog();
                    }
                } else {
                    profe.updProfesorID(datos, oldId);
                    cerrarActualizarDialog();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Error!!!\nNo debe dejar ningun campo en blanco", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Profesor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnActualizarActionPerformed

    public void cerrarActualizarDialog() throws SQLException {
        getTable(); //dibujar tabla otra vez
        JOptionPane.showMessageDialog(null, "Se ha actualizado el registro del profesor.");
        //cierra modal
        dlgActualizar.setVisible(false);
        dlgActualizar.dispose();
        //colocar paneles en orden
        pnlDlgSeleccion.setVisible(true);
        dlgPnlFormulario.setVisible(false);
        //desabilitar el boton actualizar
        btnActualizar.setEnabled(false);
        txtBusqueda.requestFocusInWindow();
    }
    private void txtId1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtId1KeyTyped
        if (txtId1.getText().length() >= 12) {
            evt.consume();
        }
        char caracter = evt.getKeyChar();
        if (((caracter < '0') || (caracter > '9')) && (caracter != '\b')) {
            evt.consume();
        }
    }//GEN-LAST:event_txtId1KeyTyped

    private void jLabel9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseClicked
        try {
            Videoproyector vid = new Videoproyector();
            vid.setVisible(true);
            this.setVisible(false);
            this.dispose();
        } catch (SQLException ex) {
            Logger.getLogger(Profesor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jLabel9MouseClicked

    private void txtIdKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIdKeyTyped
        if (txtId.getText().length() >= 12) {
            evt.consume();
        }
        char caracter = evt.getKeyChar();
        if (((caracter < '0') || (caracter > '9')) && (caracter != '\b')) {
            evt.consume();
        }
    }//GEN-LAST:event_txtIdKeyTyped

    private void txtNom1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNom1KeyTyped
        limiteYcaracteres(txtNom1, 25, evt);
    }//GEN-LAST:event_txtNom1KeyTyped

    private void txtAPat1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAPat1KeyTyped
        limiteYcaracteres(txtAPat1, 25, evt);
    }//GEN-LAST:event_txtAPat1KeyTyped

    private void txtAMat1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAMat1KeyTyped
        limiteYcaracteres(txtAMat1, 25, evt);
    }//GEN-LAST:event_txtAMat1KeyTyped

    private void txtNomKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNomKeyTyped
        limiteYcaracteres(txtNom, 25, evt);
    }//GEN-LAST:event_txtNomKeyTyped

    private void txtAPatKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAPatKeyTyped
        limiteYcaracteres(txtAPat, 25, evt);
    }//GEN-LAST:event_txtAPatKeyTyped

    private void txtAMatKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAMatKeyTyped
        limiteYcaracteres(txtAMat, 25, evt);
    }//GEN-LAST:event_txtAMatKeyTyped

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

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
    }//GEN-LAST:event_jTable1MouseClicked

    private void txtBusquedaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBusquedaKeyTyped
        char c = evt.getKeyChar();
        if (c == KeyEvent.VK_ENTER){
            btnBusqueda.doClick();
        }
    }//GEN-LAST:event_txtBusquedaKeyTyped

    private void btnBusquedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBusquedaActionPerformed
        System.out.println("Seleccionaste: "+comboFiltro.getSelectedIndex());
        TableRowSorter trsFiltro = new TableRowSorter(jTable1.getModel());
//        trsFiltro.setRowFilter(RowFilter.regexFilter(txtBusqueda.getText().toLowerCase(), comboFiltro.getSelectedIndex()));
        trsFiltro.setStringConverter(new javax.swing.table.TableStringConverter(){
            public String toString(javax.swing.table.TableModel model,int row, int column){
                return model.getValueAt(row, comboFiltro.getSelectedIndex()).toString().toLowerCase();
            }
        });
        
        //jTable1.setRowSorter(trsFiltro);
        trsFiltro.setRowFilter(RowFilter.regexFilter(txtBusqueda.getText().toLowerCase()));
        jTable1.setRowSorter(trsFiltro);
    }//GEN-LAST:event_btnBusquedaActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        txtBusqueda.setText("");
        TableRowSorter trsFiltro = new TableRowSorter(jTable1.getModel());
        trsFiltro.setRowFilter(RowFilter.regexFilter(txtBusqueda.getText(), 0));
        comboFiltro.setSelectedIndex(0);
        jTable1.setRowSorter(trsFiltro);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnAddCSVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddCSVActionPerformed
        int respuesta = JOptionPane.showConfirmDialog(null, "A continuación aparecera una ventana se requiere eliga un archivo csv"
                                                          + "\n-Los datos deben ser separados por coma(,)"
                                                          + "\n-Cada dato debe estar encerrado en comillas dobles (\"Juan Jesús\")"
                                                          + "\n-La informacion debe tener el siguiente orden"
                                                          + "\n\tID_PROFESOR\n\tID_DEPARTAMENTO\n\tNOMBRE\n\tA_PATERNO\n\tA_MATERNO\n\tESTATUS_ESCOLAR"  
                                                     , "Importante", JOptionPane.WARNING_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
        if(respuesta == JOptionPane.OK_OPTION){
            int status = fcProfCSV.showOpenDialog(null);
            if (status == fcProfCSV.APPROVE_OPTION) {
                File selectedFile = fcProfCSV.getSelectedFile();
                System.out.println(":::::::::::::::::::::::::::::::::...\n"
                                 + "Ubicación del archivo: " + selectedFile.getParent());
                System.out.println("Nombre del archivo: " + selectedFile.getName());

                try {
                    ProfesorDB prof = new ProfesorDB();
    //                if(
                        prof.bulkLoadProfe(selectedFile.getPath());//){
                        JOptionPane.showMessageDialog(null, "Los datos del archivo ubicado en: " + selectedFile.getPath() + "\nHAN SIDO GRABADOS EN LA BASE DE DATOS!!!");
                        System.out.println("dibujo tabla nuevamente");
                        getTable();
                        txtBusqueda.requestFocusInWindow();
    //                }else{
    //                    JOptionPane.showMessageDialog(null, "Rectifique los datos\n!!!No se han creado los registros!!!");
    //                }
                } catch (Exception e) {            
                    System.out.println("Error al leer archivo que fue recibido");
                }
            }
        }
    }//GEN-LAST:event_btnAddCSVActionPerformed

    public void limiteYcaracteres(JTextField nombre, int limite, java.awt.event.KeyEvent evt) {
        char c = evt.getKeyChar(); //probar introducir solo caracteres
        if (!(Character.isAlphabetic(c) || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE) || (c == KeyEvent.VK_SPACE))) {
            evt.consume();
        }
        if (nombre.getText().length() >= limite) {
            evt.consume();
        }
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
            java.util.logging.Logger.getLogger(Profesor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Profesor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Profesor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Profesor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Profesor().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(Profesor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnAddCSV;
    private javax.swing.JButton btnBorrar;
    private javax.swing.JButton btnBusqueda;
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnCerrar1;
    private javax.swing.JButton btnCerrar2;
    private javax.swing.JButton btnDlgBuscarAct;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JToggleButton btnMenu;
    private javax.swing.JButton btnRegresar;
    private javax.swing.JComboBox<String> comboFiltro;
    private javax.swing.JDialog dlgActualizar;
    private javax.swing.JDialog dlgBorrar;
    private javax.swing.JDialog dlgNuevo;
    private javax.swing.JPanel dlgPnlFormulario;
    private javax.swing.JFileChooser fcProfCSV;
    private javax.swing.JLabel ico1;
    private javax.swing.JLabel ico2;
    private javax.swing.JLabel ico3;
    private javax.swing.JLabel ico4;
    private javax.swing.JLabel ico5;
    private javax.swing.JLabel ico6;
    private javax.swing.JButton jButton1;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel labelFecha;
    private javax.swing.JLabel labelHora;
    private javax.swing.JLabel lbl1;
    private javax.swing.JLabel lbl2;
    private javax.swing.JLabel lbl3;
    private javax.swing.JLabel lblAMat;
    private javax.swing.JLabel lblAMat1;
    private javax.swing.JLabel lblAMat2;
    private javax.swing.JLabel lblAPat;
    private javax.swing.JLabel lblAPat1;
    private javax.swing.JLabel lblDepart;
    private javax.swing.JLabel lblFecha;
    private javax.swing.JLabel lblHide;
    private javax.swing.JLabel lblHora;
    private javax.swing.JLabel lblId;
    private javax.swing.JLabel lblId1;
    private javax.swing.JLabel lblIdText;
    private javax.swing.JLabel lblInstrucciones;
    private javax.swing.JLabel lblN1;
    private javax.swing.JLabel lblN2;
    private javax.swing.JLabel lblN3;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblNombre1;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblTitulo1;
    private javax.swing.JLabel logo1;
    private javax.swing.JLabel logo2;
    private javax.swing.JPanel panelOPC;
    private javax.swing.JPanel pnlBkActualizar;
    private javax.swing.JPanel pnlBkBorrar;
    private javax.swing.JPanel pnlBkNuevo;
    private javax.swing.JPanel pnlBkProfesor;
    private javax.swing.JPanel pnlBtnActualizar;
    private javax.swing.JPanel pnlBtnBorrar;
    private javax.swing.JPanel pnlBtnNuevo;
    private javax.swing.JPanel pnlColor1;
    private javax.swing.JPanel pnlColor2;
    private javax.swing.JPanel pnlColor3;
    private javax.swing.JPanel pnlColor4;
    private javax.swing.JPanel pnlColor5;
    private javax.swing.JPanel pnlDlgSeleccion;
    private javax.swing.JPanel pnlFormulario;
    private javax.swing.JRadioButton rdBtnOpc1;
    private javax.swing.JRadioButton rdBtnOpc2;
    private javax.swing.JTextField txtAMat;
    private javax.swing.JTextField txtAMat1;
    private javax.swing.JTextField txtAPat;
    private javax.swing.JTextField txtAPat1;
    private javax.swing.JTextField txtBorrar;
    private javax.swing.JTextField txtBusqueda;
    private javax.swing.JTextField txtDlgBuscar;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtId1;
    private javax.swing.JTextField txtNom;
    private javax.swing.JTextField txtNom1;
    // End of variables declaration//GEN-END:variables
}
