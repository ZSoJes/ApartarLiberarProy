/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyector;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import proyector.dataBase.crud.AccesorioDB;
import proyector.dataBase.crud.AulaDB;
import proyector.dataBase.crud.DepartamentoDB;
import proyector.dataBase.crud.UsuarioReadDB;
import proyector.dataBase.crud.PrestamoDB;
import proyector.dataBase.crud.ProfesorDB;
import proyector.dataBase.crud.VideoproyectorDB;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JLabel;
import proyector.dataBase.crud.LogDB;

/**
 *
 * @author JuanGSot
 */
public class AlPrestamo extends javax.swing.JFrame {

    ImageIcon img = new ImageIcon("./src/imagenes/logo-adm.png");
    String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
//    static ArrayList<String> chkBText = new ArrayList<String>();
//    static ArrayList<Integer> chkBID = new ArrayList<Integer>();
    
    /**
     * Creates new form AlPrestamo
     *
     */
    public AlPrestamo() {// throws SQLException {
        initComponents();
        
        panelOPC.setVisible(false);
        pnlProfeLoad.setVisible(false);
        pnlVidLoad.setVisible(false);
        txtProf.requestFocusInWindow();     //crea el foco en el primer textField
        
        cargarCombo();
        cargarComboProy();
        //deshabilitar ctrl v y ctrl c
        txtProf.setTransferHandler(null);
        txtVid.setTransferHandler(null);
        txtUsuario.setTransferHandler(null);
        
        txtProf.setVisible(true);
        txtVid.setVisible(true);
        combVid.setVisible(false);
        scrollPaneAccesorios();
        try{
            if ((new VideoproyectorDB().evStatusDisponible(true) < 1)) {
                JOptionPane.showMessageDialog(null, "Actualmente no quedan videoproyectores disponibles\nFavor de revisar en la sección devolución");
                txtVid.setEnabled(false);
                txtProf.setEnabled(false);
                combVid.setEnabled(false);
                jComboBox1.setEnabled(false);
            }
        }catch(SQLException ex){
            System.out.println("Error al comprobar disponibilidad de proyectores: " + ex);
        }

        labelFecha.setText(date);                                       //coloca la fecha
        Timer timer = new Timer(500, (ActionEvent e) -> {
            reloj();
        });  //coloca la hora
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

    public void cargarCombo() {
        try {
            AulaDB aula = new AulaDB();
            HashMap<String, Integer> map = aula.primerCombo(true);          //obtiene los datos
            Map<String, Integer> map1 = new TreeMap<>(map);                 //ordena el HashMap
            jComboBox1.removeAllItems();
            
            for (String s : map1.keySet()) {                                // agrega los datos al comboBox
                jComboBox1.addItem(s);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AlPrestamo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Metodo para crear una serie de checkbox que son los articulos disponibles
     * para prestar
     */
    public void scrollPaneAccesorios() {
        jPanel1.removeAll();
        try {
            AccesorioDB acc = new AccesorioDB();
            String[][] datos = acc.getAccesorios(true);

            int n = datos.length;
            for (int i = 0; i < n; i++) {
                JCheckBox check = new JCheckBox(datos[i][1]);
                check.setFont(new java.awt.Font("SansSerif", 1, 12));
                check.setForeground(Color.white);
                check.setOpaque(false);
                JLabel lbl = new JLabel("("+datos[i][3]+")     ");
                lbl.setFont(new java.awt.Font("SansSerif", 1 , 12));
                lbl.setForeground(Color.white);
                jPanel1.add(check);
                jPanel1.add(lbl);
                jPanel1.revalidate();
                jPanel1.repaint();
                check.setSelected(false);
            }
        } catch (SQLException ex) {
            System.out.println("Error al recuperar lista de accesorios:" + ex);
        }
        jScrollPane1.setViewportView(jPanel1);
        jScrollPane1.getHorizontalScrollBar().setPreferredSize(new java.awt.Dimension(0, 11));
        jScrollPane1.getHorizontalScrollBar().setUnitIncrement(16);
    }

    public void cargarComboProy() {
        try {
            VideoproyectorDB proyect = new VideoproyectorDB();
            HashMap<String, String> map = proyect.primerCombo();             //obtiene los datos -en DB ya se comprobo que esta disponible
            Map<String, String> map1 = new TreeMap<>(map);                      //ordena el HashMap
            combVid.removeAllItems();

            for (String s : map1.keySet()) {                                                         // agrega los datos al comboBox
                combVid.addItem(s);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AlPrestamo.class.getName()).log(Level.SEVERE, null, ex);
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

        verUsuario = new javax.swing.JDialog();
        bkDialog = new javax.swing.JPanel();
        lblTxt = new javax.swing.JLabel();
        lblInst = new javax.swing.JLabel();
        lblInst1 = new javax.swing.JLabel();
        pnlColor = new javax.swing.JPanel();
        pnlColor1 = new javax.swing.JPanel();
        pnlDatos = new javax.swing.JPanel();
        txtUsuario = new javax.swing.JTextField();
        btnPnlGuardar = new javax.swing.JPanel();
        icoGuardar = new javax.swing.JLabel();
        icoGuardar1 = new javax.swing.JLabel();
        btnPnlCerrar = new javax.swing.JPanel();
        icoCerrar1 = new javax.swing.JLabel();
        icoCerrar2 = new javax.swing.JLabel();
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
        jLabel13 = new javax.swing.JLabel();
        lblTitulo = new javax.swing.JLabel();
        pnlMain = new javax.swing.JPanel();
        lblProf = new javax.swing.JLabel();
        lblVideoproy = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtProf = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLayeredPane3 = new javax.swing.JLayeredPane();
        txtVid = new javax.swing.JTextField();
        combVid = new javax.swing.JComboBox<>();
        chkVid = new javax.swing.JCheckBox();
        pnlProfesor = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        pnlVacio = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        pnlProfeLoad = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        lblProfe1 = new javax.swing.JLabel();
        lblDepart = new javax.swing.JLabel();
        pnlVideoproyector = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLayeredPane2 = new javax.swing.JLayeredPane();
        pnlVidLoad = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        lblNom = new javax.swing.JLabel();
        lblMarc = new javax.swing.JLabel();
        lblMod = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        pnlVacio1 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();

        verUsuario.setMinimumSize(new java.awt.Dimension(550, 300));
        verUsuario.setModal(true);
        verUsuario.setUndecorated(true);
        verUsuario.setPreferredSize(new java.awt.Dimension(550, 300));
        verUsuario.setResizable(false);

        bkDialog.setBackground(new java.awt.Color(255, 103, 96));
        bkDialog.setMinimumSize(new java.awt.Dimension(550, 300));
        bkDialog.setPreferredSize(new java.awt.Dimension(550, 300));

        lblTxt.setFont(new java.awt.Font("Trebuchet MS", 1, 24)); // NOI18N
        lblTxt.setForeground(new java.awt.Color(255, 255, 255));
        lblTxt.setText("Validar Usuaro");

        lblInst.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/More Info_22px.png"))); // NOI18N

        lblInst1.setFont(new java.awt.Font("Verdana", 0, 11)); // NOI18N
        lblInst1.setForeground(new java.awt.Color(255, 255, 255));
        lblInst1.setText("Puede presionar enter o el boton Guardar para validar");

        pnlColor.setBackground(new java.awt.Color(158, 163, 13));

        javax.swing.GroupLayout pnlColorLayout = new javax.swing.GroupLayout(pnlColor);
        pnlColor.setLayout(pnlColorLayout);
        pnlColorLayout.setHorizontalGroup(
            pnlColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );
        pnlColorLayout.setVerticalGroup(
            pnlColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pnlColor1.setBackground(new java.awt.Color(206, 210, 72));

        javax.swing.GroupLayout pnlColor1Layout = new javax.swing.GroupLayout(pnlColor1);
        pnlColor1.setLayout(pnlColor1Layout);
        pnlColor1Layout.setHorizontalGroup(
            pnlColor1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 15, Short.MAX_VALUE)
        );
        pnlColor1Layout.setVerticalGroup(
            pnlColor1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        pnlDatos.setBackground(new java.awt.Color(255, 103, 96));
        pnlDatos.setLayout(new java.awt.GridBagLayout());

        txtUsuario.setFont(new java.awt.Font("Verdana", 0, 17)); // NOI18N
        txtUsuario.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtUsuario.setToolTipText("Debe escanear su credencial de usuario");
        txtUsuario.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 5, 1, 1));
        txtUsuario.setMinimumSize(new java.awt.Dimension(260, 35));
        txtUsuario.setPreferredSize(new java.awt.Dimension(260, 35));
        txtUsuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtUsuarioKeyTyped(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 10, 0);
        pnlDatos.add(txtUsuario, gridBagConstraints);

        btnPnlGuardar.setBackground(new java.awt.Color(239, 239, 239));
        btnPnlGuardar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPnlGuardarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnPnlGuardarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnPnlGuardarMouseExited(evt);
            }
        });
        btnPnlGuardar.setLayout(new java.awt.GridBagLayout());

        icoGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Save_22px.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(10, 20, 10, 0);
        btnPnlGuardar.add(icoGuardar, gridBagConstraints);

        icoGuardar1.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        icoGuardar1.setForeground(new java.awt.Color(0, 191, 255));
        icoGuardar1.setText("Guardar");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 7, 0, 20);
        btnPnlGuardar.add(icoGuardar1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(15, 0, 0, 0);
        pnlDatos.add(btnPnlGuardar, gridBagConstraints);

        btnPnlCerrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPnlCerrarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnPnlCerrarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnPnlCerrarMouseExited(evt);
            }
        });
        btnPnlCerrar.setLayout(new java.awt.GridBagLayout());

        icoCerrar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Close_22px.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 0);
        btnPnlCerrar.add(icoCerrar1, gridBagConstraints);

        icoCerrar2.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        icoCerrar2.setForeground(new java.awt.Color(245, 76, 63));
        icoCerrar2.setText("Cerrar");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 15);
        btnPnlCerrar.add(icoCerrar2, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(20, 0, 0, 0);
        pnlDatos.add(btnPnlCerrar, gridBagConstraints);

        javax.swing.GroupLayout bkDialogLayout = new javax.swing.GroupLayout(bkDialog);
        bkDialog.setLayout(bkDialogLayout);
        bkDialogLayout.setHorizontalGroup(
            bkDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bkDialogLayout.createSequentialGroup()
                .addComponent(pnlColor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlColor1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(bkDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(bkDialogLayout.createSequentialGroup()
                        .addGap(103, 103, 103)
                        .addComponent(pnlDatos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(bkDialogLayout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(lblInst)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblInst1))
                    .addGroup(bkDialogLayout.createSequentialGroup()
                        .addGap(148, 148, 148)
                        .addComponent(lblTxt)))
                .addContainerGap())
        );
        bkDialogLayout.setVerticalGroup(
            bkDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bkDialogLayout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(lblTxt)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlDatos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(bkDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblInst)
                    .addComponent(lblInst1)))
            .addComponent(pnlColor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnlColor1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout verUsuarioLayout = new javax.swing.GroupLayout(verUsuario.getContentPane());
        verUsuario.getContentPane().setLayout(verUsuarioLayout);
        verUsuarioLayout.setHorizontalGroup(
            verUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bkDialog, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        verUsuarioLayout.setVerticalGroup(
            verUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bkDialog, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        verUsuario.getAccessibleContext().setAccessibleParent(this);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("[Prestamo]");
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
            .addGroup(pnlCabeceraLayout.createSequentialGroup()
                .addGroup(pnlCabeceraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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
                                .addComponent(labelFecha)))))
                .addContainerGap())
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
        pnlBackground.add(btnMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 101, -1, -1));

        btnRegresar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Back To_35px.png"))); // NOI18N
        btnRegresar.setMaximumSize(new java.awt.Dimension(40, 37));
        btnRegresar.setMinimumSize(new java.awt.Dimension(40, 37));
        btnRegresar.setPreferredSize(new java.awt.Dimension(40, 37));
        btnRegresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegresarActionPerformed(evt);
            }
        });
        pnlBackground.add(btnRegresar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 540, -1, -1));

        panelOPC.setBackground(new java.awt.Color(250, 250, 250));
        panelOPC.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(171, 173, 179), 1, true));
        panelOPC.setMinimumSize(new java.awt.Dimension(44, 376));
        panelOPC.setPreferredSize(new java.awt.Dimension(44, 376));
        panelOPC.setLayout(new java.awt.GridBagLayout());

        ico3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/devolu_32px.png"))); // NOI18N
        ico3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ico3MouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(15, 0, 5, 0);
        panelOPC.add(ico3, gridBagConstraints);

        ico4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/registr_32px.png"))); // NOI18N
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

        ico5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/prof_42px.png"))); // NOI18N
        ico5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ico5MouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(15, 0, 5, 0);
        panelOPC.add(ico5, gridBagConstraints);

        ico6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/depart_42px.png"))); // NOI18N
        ico6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ico6MouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
        panelOPC.add(ico6, gridBagConstraints);

        ico7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/aul_42px.png"))); // NOI18N
        ico7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ico7MouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 4, 0);
        panelOPC.add(ico7, gridBagConstraints);

        ico8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Video Projector_42px.png"))); // NOI18N
        ico8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ico8MouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
        panelOPC.add(ico8, gridBagConstraints);

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Electrical_42px.png"))); // NOI18N
        jLabel13.setToolTipText("Artículos");
        jLabel13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel13MouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 15, 0);
        panelOPC.add(jLabel13, gridBagConstraints);

        pnlBackground.add(panelOPC, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 152, -1, -1));

        lblTitulo.setFont(new java.awt.Font("SansSerif", 1, 26)); // NOI18N
        lblTitulo.setText("Prestamo");
        pnlBackground.add(lblTitulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 100, -1, -1));

        pnlMain.setBackground(new java.awt.Color(186, 255, 201));
        pnlMain.setMinimumSize(new java.awt.Dimension(500, 330));
        pnlMain.setPreferredSize(new java.awt.Dimension(500, 330));
        pnlMain.setLayout(new java.awt.GridBagLayout());

        lblProf.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        lblProf.setText("Profesor");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(22, 41, 0, 0);
        pnlMain.add(lblProf, gridBagConstraints);

        lblVideoproy.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        lblVideoproy.setText("Videoproyector");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(34, 41, 0, 0);
        pnlMain.add(lblVideoproy, gridBagConstraints);

        jLabel4.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel4.setText("Aula");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(13, 41, 0, 0);
        pnlMain.add(jLabel4, gridBagConstraints);

        txtProf.setFont(new java.awt.Font("Verdana", 0, 16)); // NOI18N
        txtProf.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtProf.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(40, 195, 190), 2));
        txtProf.setMinimumSize(new java.awt.Dimension(250, 35));
        txtProf.setPreferredSize(new java.awt.Dimension(250, 35));
        txtProf.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtProfFocusLost(evt);
            }
        });
        txtProf.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtProfKeyTyped(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(18, 12, 0, 0);
        pnlMain.add(txtProf, gridBagConstraints);

        jComboBox1.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jComboBox1.setMaximumRowCount(10);
        jComboBox1.setMinimumSize(new java.awt.Dimension(250, 35));
        jComboBox1.setPreferredSize(new java.awt.Dimension(250, 35));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(25, 12, 23, 0);
        pnlMain.add(jComboBox1, gridBagConstraints);

        jLayeredPane3.setMinimumSize(new java.awt.Dimension(270, 52));
        jLayeredPane3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtVid.setFont(new java.awt.Font("Verdana", 0, 16)); // NOI18N
        txtVid.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtVid.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(40, 195, 190), 2));
        txtVid.setMinimumSize(new java.awt.Dimension(250, 35));
        txtVid.setPreferredSize(new java.awt.Dimension(250, 35));
        txtVid.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtVidFocusLost(evt);
            }
        });
        txtVid.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtVidKeyTyped(evt);
            }
        });
        jLayeredPane3.add(txtVid, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 6, -1, -1));

        combVid.setFont(new java.awt.Font("Verdana", 0, 16)); // NOI18N
        combVid.setMinimumSize(new java.awt.Dimension(250, 35));
        combVid.setPreferredSize(new java.awt.Dimension(250, 35));
        combVid.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                combVidItemStateChanged(evt);
            }
        });
        combVid.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                combVidFocusLost(evt);
            }
        });
        combVid.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                combVidMouseClicked(evt);
            }
        });
        jLayeredPane3.add(combVid, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 8, -1, -1));

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(18, 5, 0, 0);
        pnlMain.add(jLayeredPane3, gridBagConstraints);

        chkVid.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        chkVid.setText("<< Usar una lista");
        chkVid.setOpaque(false);
        chkVid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkVidActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(29, 28, 0, 15);
        pnlMain.add(chkVid, gridBagConstraints);

        pnlBackground.add(pnlMain, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 140, -1, -1));

        pnlProfesor.setBackground(new java.awt.Color(254, 254, 150));
        pnlProfesor.setMinimumSize(new java.awt.Dimension(320, 140));
        pnlProfesor.setPreferredSize(new java.awt.Dimension(320, 140));
        pnlProfesor.setLayout(new java.awt.GridBagLayout());

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/lblProf.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(1, 0, 1, 0);
        pnlProfesor.add(jLabel3, gridBagConstraints);

        jLayeredPane1.setMinimumSize(new java.awt.Dimension(290, 140));
        jLayeredPane1.setLayout(new java.awt.GridBagLayout());

        pnlVacio.setOpaque(false);

        jLabel6.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel6.setText("<html>No se encontro al <br>profesor ingresado</html>");

        javax.swing.GroupLayout pnlVacioLayout = new javax.swing.GroupLayout(pnlVacio);
        pnlVacio.setLayout(pnlVacioLayout);
        pnlVacioLayout.setHorizontalGroup(
            pnlVacioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlVacioLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33))
        );
        pnlVacioLayout.setVerticalGroup(
            pnlVacioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlVacioLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jLayeredPane1.add(pnlVacio, gridBagConstraints);

        pnlProfeLoad.setBackground(new java.awt.Color(254, 254, 184));
        pnlProfeLoad.setOpaque(false);
        pnlProfeLoad.setLayout(new java.awt.GridBagLayout());

        jLabel9.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        jLabel9.setText("Profesor:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
        pnlProfeLoad.add(jLabel9, gridBagConstraints);

        jLabel10.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        jLabel10.setText("Departamento:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 10);
        pnlProfeLoad.add(jLabel10, gridBagConstraints);

        lblProfe1.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblProfe1.setText("jLabel11");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 1, 3, 0);
        pnlProfeLoad.add(lblProfe1, gridBagConstraints);

        lblDepart.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblDepart.setText("jLabel13");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 1, 5, 0);
        pnlProfeLoad.add(lblDepart, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jLayeredPane1.add(pnlProfeLoad, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        pnlProfesor.add(jLayeredPane1, gridBagConstraints);

        pnlBackground.add(pnlProfesor, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 140, -1, -1));

        pnlVideoproyector.setBackground(new java.awt.Color(145, 223, 252));
        pnlVideoproyector.setMinimumSize(new java.awt.Dimension(320, 180));
        pnlVideoproyector.setPreferredSize(new java.awt.Dimension(320, 180));
        pnlVideoproyector.setLayout(new java.awt.GridBagLayout());

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/lblProy.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(1, 0, 1, 0);
        pnlVideoproyector.add(jLabel5, gridBagConstraints);

        jLayeredPane2.setMinimumSize(new java.awt.Dimension(290, 180));

        pnlVidLoad.setBackground(new java.awt.Color(184, 223, 252));
        pnlVidLoad.setOpaque(false);

        jLabel8.setFont(new java.awt.Font("SansSerif", 1, 10)); // NOI18N
        jLabel8.setText("Nombre");

        jLabel11.setFont(new java.awt.Font("SansSerif", 1, 10)); // NOI18N
        jLabel11.setText("Marca");

        jLabel12.setFont(new java.awt.Font("SansSerif", 1, 10)); // NOI18N
        jLabel12.setText("Modelo");

        lblNom.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblNom.setText("jLabel13");

        lblMarc.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblMarc.setText("jLabel14");

        lblMod.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblMod.setText("jLabel15");

        jPanel2.setBackground(new java.awt.Color(125, 119, 105));
        jPanel2.setPreferredSize(new java.awt.Dimension(155, 3));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        jPanel4.setBackground(new java.awt.Color(125, 119, 105));
        jPanel4.setPreferredSize(new java.awt.Dimension(155, 3));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout pnlVidLoadLayout = new javax.swing.GroupLayout(pnlVidLoad);
        pnlVidLoad.setLayout(pnlVidLoadLayout);
        pnlVidLoadLayout.setHorizontalGroup(
            pnlVidLoadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlVidLoadLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlVidLoadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE)
                    .addGroup(pnlVidLoadLayout.createSequentialGroup()
                        .addGroup(pnlVidLoadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel11)
                            .addComponent(jLabel12))
                        .addGap(29, 29, 29)
                        .addGroup(pnlVidLoadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNom)
                            .addComponent(lblMod)
                            .addComponent(lblMarc))
                        .addGap(0, 62, Short.MAX_VALUE))
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlVidLoadLayout.setVerticalGroup(
            pnlVidLoadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlVidLoadLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(pnlVidLoadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(lblNom))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlVidLoadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(lblMarc))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlVidLoadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(lblMod))
                .addContainerGap(40, Short.MAX_VALUE))
        );

        pnlVacio1.setOpaque(false);

        jLabel7.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel7.setText("<html>No se encontro <br>el videoproyector</html>");

        javax.swing.GroupLayout pnlVacio1Layout = new javax.swing.GroupLayout(pnlVacio1);
        pnlVacio1.setLayout(pnlVacio1Layout);
        pnlVacio1Layout.setHorizontalGroup(
            pnlVacio1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 280, Short.MAX_VALUE)
            .addGroup(pnlVacio1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnlVacio1Layout.createSequentialGroup()
                    .addGap(0, 62, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 63, Short.MAX_VALUE)))
        );
        pnlVacio1Layout.setVerticalGroup(
            pnlVacio1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 171, Short.MAX_VALUE)
            .addGroup(pnlVacio1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnlVacio1Layout.createSequentialGroup()
                    .addGap(0, 67, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 68, Short.MAX_VALUE)))
        );

        jLayeredPane2.setLayer(pnlVidLoad, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(pnlVacio1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPane2Layout = new javax.swing.GroupLayout(jLayeredPane2);
        jLayeredPane2.setLayout(jLayeredPane2Layout);
        jLayeredPane2Layout.setHorizontalGroup(
            jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 290, Short.MAX_VALUE)
            .addGroup(jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane2Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(pnlVacio1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
            .addGroup(jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jLayeredPane2Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(pnlVidLoad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        jLayeredPane2Layout.setVerticalGroup(
            jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 180, Short.MAX_VALUE)
            .addGroup(jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jLayeredPane2Layout.createSequentialGroup()
                    .addComponent(pnlVacio1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jLayeredPane2Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(pnlVidLoad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        pnlVideoproyector.add(jLayeredPane2, new java.awt.GridBagConstraints());

        pnlBackground.add(pnlVideoproyector, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 290, -1, -1));

        jScrollPane1.setBorder(null);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        jPanel1.setBackground(new java.awt.Color(255, 160, 64));
        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
        jScrollPane1.setViewportView(jPanel1);

        pnlBackground.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 480, 830, 40));

        btnGuardar.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Save_22px.png"))); // NOI18N
        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        pnlBackground.add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 540, -1, -1));

        btnClear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Broom_24px.png"))); // NOI18N
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });
        pnlBackground.add(btnClear, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 540, -1, -1));

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
            Logger.getLogger(AlPrestamo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_ico7MouseClicked

    private void ico8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ico8MouseClicked
        try {
            Videoproyector vid = new Videoproyector();
            vid.setVisible(true);
            this.setVisible(false);
            this.dispose();
        } catch (SQLException ex) {
            Logger.getLogger(AlPrestamo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_ico8MouseClicked

    private void ico3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ico3MouseClicked
        ADevolucion dev = new ADevolucion();
        dev.setVisible(true);
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_ico3MouseClicked

    private void ico4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ico4MouseClicked
        ARegistro reg = new ARegistro();
        reg.setVisible(true);
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

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void txtProfKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtProfKeyTyped
        if (txtProf.getText().length() >= 12) {
            evt.consume();
        }
        char caracter = evt.getKeyChar();
        if (((caracter < '0') || (caracter > '9')) && (caracter != '\b')) {
            evt.consume();
        }
    }//GEN-LAST:event_txtProfKeyTyped

    private void txtVidKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtVidKeyTyped
        if (txtVid.getText().length() >= 20) {
            evt.consume();
        }
    }//GEN-LAST:event_txtVidKeyTyped

    private void btnPnlGuardarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPnlGuardarMouseClicked
        btnPnlGuardar.setBackground(new Color(239, 239, 239));
        try {
            guardarAccion();
        } catch (SQLException ex) {
            Logger.getLogger(AlPrestamo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnPnlGuardarMouseClicked

    private void btnPnlGuardarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPnlGuardarMouseEntered
        btnPnlGuardar.setBackground(new Color(0, 191, 255));
    }//GEN-LAST:event_btnPnlGuardarMouseEntered

    private void btnPnlGuardarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPnlGuardarMouseExited
        btnPnlGuardar.setBackground(new Color(239, 239, 239));
    }//GEN-LAST:event_btnPnlGuardarMouseExited

    private void btnPnlCerrarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPnlCerrarMouseClicked
        btnPnlCerrar.setBackground(new Color(239, 239, 239));
        verUsuario.setVisible(false);
        verUsuario.dispose();
        txtUsuario.setText("");
    }//GEN-LAST:event_btnPnlCerrarMouseClicked

    private void btnPnlCerrarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPnlCerrarMouseEntered
        btnPnlCerrar.setBackground(new Color(245, 76, 63));
    }//GEN-LAST:event_btnPnlCerrarMouseEntered

    private void btnPnlCerrarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPnlCerrarMouseExited
        btnPnlCerrar.setBackground(new Color(239, 239, 239));
    }//GEN-LAST:event_btnPnlCerrarMouseExited

    private void txtUsuarioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUsuarioKeyTyped
        if (evt.getKeyChar() == KeyEvent.VK_ENTER) {
            try {
                guardarAccion();
            } catch (SQLException ex) {
                Logger.getLogger(AlPrestamo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_txtUsuarioKeyTyped

    private void txtProfFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtProfFocusLost
        String profe = txtProf.getText().trim();
        System.out.println("::::::::::::::::::::::::::::::::::::::::::::::::::::::");
        System.out.println("foco profesor");
        if (!profe.isEmpty()) {
            try {
                ProfesorDB prof = new ProfesorDB();
                if (prof.getExisteProfesor(profe)) {
                    PrestamoDB prestamo = new PrestamoDB();
                    if (prestamo.getPrestamoActivo(profe, 2)) { //si true (si diponible) utilizar profesor
                        DepartamentoDB depar = new DepartamentoDB();
                        String[] datos = prof.getProfesor(profe);
                        String[] depart = depar.getDepartamento(Integer.parseInt(datos[1]));
                        pnlVacio.setVisible(false);
                        pnlProfeLoad.setVisible(true);
                        //asignar valores
                        lblProfe1.setText("<html>" + datos[2] + "<br>" + datos[3] + " " + datos[4] + "</html>");
                        lblDepart.setText(depart[0]);
                    } else {
                        txtProf.setText("");
                        JOptionPane.showMessageDialog(null, "Ingresar profesor que:\n-NO SE ENCUENTRE en prestamo vigente\n-NO SE ENCUENTRE con un adeudo", "Advertencia", JOptionPane.WARNING_MESSAGE);
                        pnlVacio.setVisible(true);
                        pnlProfeLoad.setVisible(false);
                    }
                } else {
                    System.out.println("No se encontraron resultados de profesor");
                    txtProf.setText("");
                    pnlVacio.setVisible(true);
                    pnlProfeLoad.setVisible(false);
                }
            } catch (SQLException ex) {
                Logger.getLogger(AlPrestamo.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            System.out.println("Indique un profesor antes de continuar");
            pnlVacio.setVisible(true);
            pnlProfeLoad.setVisible(false);
        }
    }//GEN-LAST:event_txtProfFocusLost

    private void txtVidFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtVidFocusLost
        String proy = txtVid.getText().trim();
        System.out.println("::::::::::::::::::::::::::::::::::::::::::::::::::::::");
        System.out.println("foco videoproyector textBox");
        if (!proy.isEmpty()) {
            try {
                VideoproyectorDB vid = new VideoproyectorDB();
                if (vid.getExisteProyector(proy)) {
                    PrestamoDB prestamo = new PrestamoDB();
                    if (prestamo.getPrestamoActivo(proy, 3)) { //si true (esta disponible para prestamo), si esta prestado no se vuelve a prestar el mismo proyector
                        String[] data = vid.getProyector(proy);
                        if (Boolean.valueOf(vid.showMeEvStatus(data[0])[3])) {   // comprueba que el proyector en si mismo este disponible y no este reportado con falla o en prestamo
                            System.out.println(">>>disponible proyector");
                            pnlVacio1.setVisible(false);
                            pnlVidLoad.setVisible(true);
                            //asignar valores
                            lblNom.setText(data[0]);
                            lblMarc.setText(data[1]);
                            lblMod.setText(data[2]);
                        } else {
                            JOptionPane.showMessageDialog(null, "Videoproyector actualmente en uso", "Advertencia", JOptionPane.WARNING_MESSAGE);
                            limpiarTxtVid();
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "videoproyector actualmente en uso", "Advertencia", JOptionPane.WARNING_MESSAGE);
                        limpiarTxtVid();
                    }
                } else {
                    System.out.println("No se encontraron resultados de videoproyector");
                    limpiarTxtVid();
                }
            } catch (SQLException ex) {
                Logger.getLogger(AlPrestamo.class.getName()).log(Level.SEVERE, null, ex);
                limpiarTxtVid();
            }
        } else {
            System.out.println("Indique un videoproyector antes de continuar");
            limpiarTxtVid();
        }
    }//GEN-LAST:event_txtVidFocusLost
    public void limpiarTxtVid() {
        txtVid.setText("");
        pnlVacio1.setVisible(true);
        pnlVidLoad.setVisible(false);
    }
    private void chkVidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkVidActionPerformed
        if (chkVid.isSelected()) {
            combVid.setVisible(true);
            txtVid.setVisible(false);
            try {
                VideoproyectorDB proy = new VideoproyectorDB();
                HashMap<String, String> map = proy.primerCombo();
                String proyNoSerie = map.get(combVid.getSelectedItem().toString());
                System.out.println("No_Serie videoproyector seleccionado: " + proyNoSerie);
                txtVid.setText(proyNoSerie);
                //asignar valores al cuadro que describe proyector
                String[] data = proy.getProyector(proyNoSerie);
                pnlVacio1.setVisible(false);
                pnlVidLoad.setVisible(true);
                //asignar valores
                lblNom.setText(data[0]);
                lblMarc.setText(data[1]);
                lblMod.setText(data[2]);
            } catch (SQLException ex) {
                System.out.println("Error al recuperar informacion videoproyector ComboBox: " + ex);
            }
        } else {
            txtVid.setVisible(true);
            combVid.setVisible(false);
        }
    }//GEN-LAST:event_chkVidActionPerformed

    private void combVidMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_combVidMouseClicked
        if (chkVid.isSelected()) {
            try {
                VideoproyectorDB proy = new VideoproyectorDB();                         //instancia de proyector
                HashMap<String, String> map = proy.primerCombo();                       //genera un hash con el noSerie y nombre del proyector
                String proyNoSerie = map.get(combVid.getSelectedItem().toString());     //del elemento seleccionado recupera el noSerie
                System.out.println(".::No_Serie proyector comboVid: " + proyNoSerie);
                txtVid.setText(proyNoSerie);
                //asignar valores al cuadro que describe proyector
                String[] data = proy.getProyector(proyNoSerie);
                pnlVacio1.setVisible(false);
                pnlVidLoad.setVisible(true);
                //asignar valores
                lblNom.setText(data[0]);
                lblMarc.setText(data[1]);
                lblMod.setText(data[2]);
            } catch (SQLException ex) {
                System.out.println("Error al recuperar informacion videoproyector ComboBox: " + ex);
            }
        }
    }//GEN-LAST:event_combVidMouseClicked

    private void combVidFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_combVidFocusLost
        String proy = txtVid.getText().trim();
        System.out.println("::::::::::::::::::::::::::::::::::::::::::::::::::::::");
        System.out.println("foco videoproyector comboBox");
        if (chkVid.isSelected()) {
            try {
                VideoproyectorDB proyector = new VideoproyectorDB();                         //instancia de proyector
                HashMap<String, String> map = proyector.primerCombo();                       //genera un hash con el noSerie y nombre del proyector
                String proyNoSerie = map.get(combVid.getSelectedItem().toString());     //del elemento seleccionado recupera el noSerie
                System.out.println(".::NoSerie de Proyector seleccionado\n.::focusLost: " + proyNoSerie);
            } catch (SQLException ex) {
                System.out.println("Error combVid al perder focus: " + ex);
            }
        }

        //  else { 
        //System.out.println("Indique un videoproyector antes de continuar");
        //limpiarTxtVid();
        //}
    }//GEN-LAST:event_combVidFocusLost

    private void combVidItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_combVidItemStateChanged
        if(chkVid.isSelected()){
            try {
                VideoproyectorDB proyector = new VideoproyectorDB();                         //instancia de proyector
                HashMap<String, String> map = proyector.primerCombo();                       //genera un hash con el noSerie y nombre del proyector
                String proyNoSerie = map.get(combVid.getSelectedItem().toString());     //del elemento seleccionado recupera el noSerie
                System.out.println(".::NoSerie de Proyector seleccionado\n.::stateChanged: " + proyNoSerie);

                txtVid.setText(proyNoSerie);
                //asignar valores al cuadro que describe proyector
                String[] data = proyector.getProyector(proyNoSerie);
                pnlVacio1.setVisible(false);
                pnlVidLoad.setVisible(true);
                //asignar valores
                lblNom.setText(data[0]);
                lblMarc.setText(data[1]);
                lblMod.setText(data[2]);
            } catch (SQLException ex) {
                System.out.println("Error combVid al perder focus: " + ex);
            }
        }
    }//GEN-LAST:event_combVidItemStateChanged

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        System.out.println(":::::::::::::::::::...............");
        
        String profe = txtProf.getText().trim();
        String proy = txtVid.getText().trim();
        String accesorio = "";
       
        if (!profe.isEmpty() && !proy.isEmpty()) {                          //comprobar datos no esten en blanco
            try {
                ProfesorDB profesor = new ProfesorDB();
                VideoproyectorDB proyector = new VideoproyectorDB();
                boolean profeExiste = profesor.getExisteProfesor(profe);
                boolean proyeExiste = proyector.getExisteProyector(proy);
                if(profeExiste && proyeExiste){                                //comprobar los datos si son verdaderos
//                    try{                                                                      //recupera el id de los checkbox seleccionados
//                        AccesorioDB acc = new AccesorioDB();
//                        Iterator<String> it = chkBText.iterator();      // iterar sobre arrayList con nombre de accesorios
//                        while(it.hasNext()){
//                            accesorio = it.next();
//                            int id = acc.getAccesorioID(accesorio);       // recuperar ID del accesorio y agregar al arrayList temporal llamado chkBID
//                            System.out.println("-Id: " + id + " -Accesorio: " + accesorio);
//                            chkBID.add(id);
//                        }
                        // abrir dialog para validar prestamo con un usuario reconocido por el sistema
                        verUsuario.setLocationRelativeTo(pnlBackground);
                        verUsuario.setVisible(true);
//                    }catch(SQLException ex){ System.out.println("Error al utilizar una instancia de AccesorioDB:" + ex); }
                }else{
                   JOptionPane.showMessageDialog(null, "El profesor indicado y/o video proyector no se encuentran registrados en la base de datos\n"
                       + "Compruebe los datos ingresados", "Advertencia", JOptionPane.WARNING_MESSAGE);
                }
            } catch (SQLException e) {
                System.out.println("Error al recuperar los datos btnGuardar: " + e);
            }
            
        } else { JOptionPane.showMessageDialog(null, "Debe completar todos los campos", "Advertencia", JOptionPane.WARNING_MESSAGE); }
        
        
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        
        if (combVid.getItemCount() == 0) {
            JOptionPane.showMessageDialog(null, "El sistema ya no puede realizar mas prestamos\nFavor de revisar la sección devolución");
            System.out.println("El comboBox de videoproyectores se encuentra vacio");
            txtVid.setEnabled(false);
            txtProf.setEnabled(false);

            combVid.setEnabled(false);
            jComboBox1.setEnabled(false);
        } else {
            jComboBox1.setSelectedIndex(0);
            combVid.setSelectedIndex(0);
        }
        chkVid.setSelected(false);
        txtVid.setVisible(true);
        combVid.setVisible(false);
        //
        txtUsuario.setText("");
        txtProf.setText("");
        txtVid.setText("");
        //
        pnlProfeLoad.setVisible(false);
        pnlVacio.setVisible(true);
        pnlVidLoad.setVisible(false);
        pnlVacio1.setVisible(true);
        limpiarChkB();
    }//GEN-LAST:event_btnClearActionPerformed

    private void jLabel13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel13MouseClicked
        Articulo art = new Articulo();
        art.setVisible(true);
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_jLabel13MouseClicked

    public void guardarAccion() throws SQLException {
        try {
            String usuario = txtUsuario.getText().trim();
            String vid = txtVid.getText().trim();
            String prof = txtProf.getText().trim();
            if (!usuario.isEmpty()) {                               //comprobar no este en blanco el usuario
                UsuarioReadDB leer = new UsuarioReadDB();
                if (leer.getExisteUsuario(usuario)) {       //comprobar existe el usuario
                    AulaDB aula = new AulaDB();
                    HashMap<String, Integer> map = aula.primerCombo();      //recuperar hash de aulas para tener el id del aula seleccionada
                    
                    int i = 0;
                    ArrayList<Integer> chkBFinal = new ArrayList<Integer>();
                    String[] datos = {usuario, vid, prof, map.get(jComboBox1.getSelectedItem().toString()).toString()};
                    for (Component component : jPanel1.getComponents()) {   //escan por componentes de jPanel1
                        if(component instanceof JCheckBox){                 //si son jCheckBox
                            JCheckBox c = (JCheckBox) component;        //crea una instancia para identificar el componente
                            if(c.isSelected()){                                             //revisa si esta seleccionado
                                try{
                                    AccesorioDB acc = new AccesorioDB();
                                    String accesorio = c.getText();
                                    int id = acc.getAccesorioID(accesorio);
                                    chkBFinal.add(id);     //obtengo el texto del componente->obtengo el id del accesorio->lo asigno a un ArrayList temporal
                                    System.out.println("-Id: " + id + " -Accesorio: " + accesorio);
                                    
                                }catch(SQLException ex){ System.out.println("Error en final chkB: " + ex);}
                                //c.setSelected(false);
                            }
                        }
                    }
                    int[] accArr = new int[chkBFinal.size()];          //generar un array para recuperar datos del arrayList temporal
                    for(Integer array : chkBFinal) { accArr[i]= array; i++; }   //asigno los datos al array
                    chkBFinal.clear();  //limpio el arrayList
                    System.out.println("\n\nmis datos: " + Arrays.toString(datos));
                    System.out.println("mis accesorios:" + Arrays.toString(accArr));
                    PrestamoDB pres = new PrestamoDB();
                    pres.setPrestamo(datos, accArr);    //HACE PRESTAMO
                    scrollPaneAccesorios();                 //ACTUALIZA LOS ACCESORIOS DISPONIBLES
                    new LogDB().log(usuario, "E_PRESTAMOS", 6);
                    ImageIcon iconJPane = new ImageIcon("./src/imagenes/Ok.png");
                    JOptionPane.showMessageDialog(null, "Se ha creado el registro.Presione enter.", "Información", JOptionPane.INFORMATION_MESSAGE, iconJPane);
                    
                    //
                    chkVid.setSelected(false);
                    txtVid.setVisible(true);
                    combVid.setVisible(false);
                    //
                    pnlProfeLoad.setVisible(false);
                    pnlVacio.setVisible(true);
                    pnlVidLoad.setVisible(false);
                    pnlVacio1.setVisible(true);
                    //
                    verUsuario.setVisible(false);
                    verUsuario.dispose();
                    txtUsuario.setText("");
                    txtProf.setText("");
                    txtVid.setText("");
                    txtProf.requestFocusInWindow();
                    cargarCombo();
                    cargarComboProy();
                    if (combVid.getItemCount() == 0) {
                        JOptionPane.showMessageDialog(null, "El sistema ya no puede realizar mas prestamos\nFavor de revisar la sección devolución");
                        System.out.println("El comboBox de videoproyectores se encuentra vacio de datos");
                        txtVid.setEnabled(false);
                        txtProf.setEnabled(false);

                        combVid.setEnabled(false);
                        jComboBox1.setEnabled(false);
                    } else {
                        jComboBox1.setSelectedIndex(0);
                        combVid.setSelectedIndex(0);
                    }
                    limpiarChkB();                              //limpia los ArrayList de los nombres y checkbox id obtenidos
                } else {
                    JOptionPane.showMessageDialog(null, "El usuario que trata de validar no esta registrado en el sistema\n\nCompruebe que su usuario esta registrado.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Es importante escanee su credencial\n\npara validar este prestamo.", "Advertencia", JOptionPane.WARNING_MESSAGE);

            }
        } catch (SQLException ex) {
            Logger.getLogger(AlPrestamo.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void limpiarChkB(){
        for (Component component : jPanel1.getComponents()) {
            if(component instanceof JCheckBox){
                JCheckBox c = (JCheckBox) component;
                if(c.isSelected()){
                    c.setSelected(false);
                }
            }
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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AlPrestamo.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new AlPrestamo().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel bkDialog;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JToggleButton btnMenu;
    private javax.swing.JPanel btnPnlCerrar;
    private javax.swing.JPanel btnPnlGuardar;
    private javax.swing.JButton btnRegresar;
    private javax.swing.JCheckBox chkVid;
    private javax.swing.JComboBox<String> combVid;
    private javax.swing.JLabel ico3;
    private javax.swing.JLabel ico4;
    private javax.swing.JLabel ico5;
    private javax.swing.JLabel ico6;
    private javax.swing.JLabel ico7;
    private javax.swing.JLabel ico8;
    private javax.swing.JLabel icoCerrar1;
    private javax.swing.JLabel icoCerrar2;
    private javax.swing.JLabel icoGuardar;
    private javax.swing.JLabel icoGuardar1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JLayeredPane jLayeredPane2;
    private javax.swing.JLayeredPane jLayeredPane3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelFecha;
    private javax.swing.JLabel labelHora;
    private javax.swing.JLabel lblDepart;
    private javax.swing.JLabel lblIco1;
    private javax.swing.JLabel lblIco2;
    private javax.swing.JLabel lblInst;
    private javax.swing.JLabel lblInst1;
    private javax.swing.JLabel lblMarc;
    private javax.swing.JLabel lblMod;
    private javax.swing.JLabel lblNom;
    private javax.swing.JLabel lblProf;
    private javax.swing.JLabel lblProfe1;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblTxt;
    private javax.swing.JLabel lblVideoproy;
    private javax.swing.JPanel panelOPC;
    private javax.swing.JPanel pnlBackground;
    private javax.swing.JPanel pnlCabecera;
    private javax.swing.JPanel pnlColor;
    private javax.swing.JPanel pnlColor1;
    private javax.swing.JPanel pnlDatos;
    private javax.swing.JPanel pnlMain;
    private javax.swing.JPanel pnlProfeLoad;
    private javax.swing.JPanel pnlProfesor;
    private javax.swing.JPanel pnlVacio;
    private javax.swing.JPanel pnlVacio1;
    private javax.swing.JPanel pnlVidLoad;
    private javax.swing.JPanel pnlVideoproyector;
    private javax.swing.JTextField txtProf;
    private javax.swing.JTextField txtUsuario;
    private javax.swing.JTextField txtVid;
    private javax.swing.JDialog verUsuario;
    // End of variables declaration//GEN-END:variables
}
