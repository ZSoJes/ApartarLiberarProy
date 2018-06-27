package proyector;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.ToolTipManager;
import javax.swing.table.DefaultTableModel;
import org.mindrot.jbcrypt.BCrypt;
import proyector.dataBase.crud.AulaDB;
import proyector.dataBase.crud.LogDB;
import proyector.dataBase.crud.UsuarioCreateDB;
import proyector.dataBase.crud.UsuarioReadDB;
import proyector.dataBase.crud.PrestamoDB;
import proyector.dataBase.crud.ProfesorDB;
import proyector.dataBase.crud.VideoproyectorDB;
import proyector.reportes.GenerarReportes;

/**
 *
 * @author JuanGSot
 */
public class Menu extends javax.swing.JFrame {

    ImageIcon img = new ImageIcon(("./src/imagenes/logo-adm.png").replace('/', File.separatorChar));
    ImageIcon pres = new ImageIcon(("./src/imagenes/prestamo_32px.png").replace('/', File.separatorChar));
    ImageIcon presW = new ImageIcon(("./src/imagenes/prestamo_32px_lavanda.png").replace('/', File.separatorChar));

    ImageIcon dev = new ImageIcon(("./src/imagenes/devolu_32px.png").replace('/', File.separatorChar));
    ImageIcon devW = new ImageIcon(("./src/imagenes/devolu_32px_lavanda.png").replace('/', File.separatorChar));

    ImageIcon reg = new ImageIcon(("./src/imagenes/registr_32px.png").replace('/', File.separatorChar));
    ImageIcon regW = new ImageIcon(("./src/imagenes/registr_32px_lavanda.png").replace('/', File.separatorChar));

    private static Boolean valido = false;
    /**
     * Creates new form Menu
     */
    public Menu() {
        initComponents();
        labelFecha.setText(new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
        lblAjustes.setToolTipText("Ajustes");

        //visibilidad
        lblUsuarioHidenID.setVisible(false);
        hdnIDCopy.setVisible(false);
        //pnlAccesoMiUsuario.setVisible(true);
        pnlUsuarioCont.setVisible(false);
        pnlUsuarioMod.setVisible(false);

        //usuario mas detalles
        jScrollPane2.setVisible(false);
        jTable2.setVisible(false);
        tglActInc.setVisible(false);
        hiddenLbl.setVisible(false);
        jButton4.setVisible(false);

        pnlUsuariosList.setVisible(false);
        txtIDNew.setEnabled(false);
        txtCredencialN.setEnabled(false);
        
        pnlMenuBK.setVisible(false);
        
        //no copiar ni pegar
        txtNomN.setTransferHandler(null);
        txtAPatN.setTransferHandler(null);
        txtAMatN.setTransferHandler(null);
        passN.setTransferHandler(null);
        passConfN.setTransferHandler(null);
        txtCredencialN.setTransferHandler(null);

        txtNomUpd.setTransferHandler(null);
        txtAPatUpd.setTransferHandler(null);
        txtAMatUpd.setTransferHandler(null);
        passUpd.setTransferHandler(null);
        passConfUpd.setTransferHandler(null);
        txtIDNew.setTransferHandler(null);

        Timer timer = new Timer(500, (ActionEvent e) -> {
            reloj();
        });
        timer.setRepeats(true);
        timer.setCoalesce(true);
        timer.setInitialDelay(0);
        timer.start();        
    }

    public void reloj() {
        labelHora.setText(new SimpleDateFormat("hh:mm:ss a").format(new Date()));
    }

    public void getTabla() {
        try {
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();

            String[] cols = {jTable1.getColumnName(0), jTable1.getColumnName(1), jTable1.getColumnName(2), jTable1.getColumnName(3), jTable1.getColumnName(4), jTable1.getColumnName(5)};

            UsuarioReadDB leer = new UsuarioReadDB();
            int count = leer.getRegistros();
            String[][] data = new String[count][6];
            data = leer.getUsuarios();

            for (int i = 0; i < count; i++) {
                for (int j = 0; j < 7; j++) {

                    if (j == 4) {
                        if (Boolean.valueOf(data[i][j])) {
                            data[i][j] = "Administrador";
                        } else {
                            data[i][j] = "Normal";
                        }
                    }
                    if (j == 5) {
                        data[i][j] = data[i][j].substring(0, 16);
                    }
                }
            }
            model.setDataVector(data, cols);
        } catch (SQLException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void getTablaUsuarioReg(String usuario, boolean presActivInact) {
        try {
            DefaultTableModel model = (DefaultTableModel) jTable2.getModel();

            String[] cols = {jTable2.getColumnName(0), jTable2.getColumnName(1), jTable2.getColumnName(2), jTable2.getColumnName(3), jTable2.getColumnName(4), jTable2.getColumnName(5), jTable2.getColumnName(6)};

            PrestamoDB prestamos = new PrestamoDB();
            int count = prestamos.getCantPrestamosUsuario(usuario, presActivInact);
            String[][] dataTabla = new String[count][8];
            String[][] dataDB = prestamos.getPrestamosUsuario(usuario, presActivInact);

            for (int i = 0; i < count; i++) {
                for (int j = 0; j < 8; j++) {
//                    if (j == 0) {
//                        dataTabla[i][j] = usuario;
//                    }
                    if (j == 0) {
                        UsuarioReadDB leer = new UsuarioReadDB();
                        String[] arrUsu = leer.getUsuario(usuario);
                        dataTabla[i][j] = arrUsu[1] + " " + arrUsu[2] + " " + arrUsu[3];
                    }
                    if (j == 1) {
                        VideoproyectorDB vidProy = new VideoproyectorDB();
                        dataTabla[i][j] = vidProy.getProyector(vidProy.getProyectorNoSerie(dataDB[i][3]))[1];
                    }
                    if (j == 2) {
                        ProfesorDB profe = new ProfesorDB();
                        String[] arrProfe = profe.getProfesor(dataDB[i][4]);
                        dataTabla[i][j] = arrProfe[2] + " " + arrProfe[3] + " " + arrProfe[4];
                    }
                    if (j == 3) {
                        dataTabla[i][j] = new AulaDB().getAula(Integer.parseInt(dataDB[i][5]));
                    }
                    if (j == 4) {
                        dataTabla[i][j] = dataDB[i][6].substring(0, 10);
                    }
                    if (j == 5) {
                        dataTabla[i][j] = dataDB[i][6].substring(11, 16);
                    }
                    if (j == 6) {
                        dataTabla[i][j] = ((dataDB[i][1].equals(dataDB[i][2])) ? "AMBOS" : ((dataDB[i][1].equals(usuario)) ? "PRESTO" : "RECIBIÓ"));
                    }
                }
            }
            model.setDataVector(dataTabla, cols);
        } catch (SQLException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
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

        dlgUsuario = new javax.swing.JDialog();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        pnlMiUsuario = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLayeredPane3 = new javax.swing.JLayeredPane();
        pnlUsrVerif1 = new javax.swing.JPanel();
        txtUsuarioCred = new javax.swing.JTextField();
        pnlBarraSeparadora1 = new javax.swing.JPanel();
        btnGetUsuario = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        pnlUsuarioMod = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtNomUpd = new javax.swing.JTextField();
        txtAPatUpd = new javax.swing.JTextField();
        txtAMatUpd = new javax.swing.JTextField();
        passUpd = new javax.swing.JPasswordField();
        passConfUpd = new javax.swing.JPasswordField();
        jLabel7 = new javax.swing.JLabel();
        chkBNewID = new javax.swing.JCheckBox();
        txtIDNew = new javax.swing.JTextField();
        btnActUpd = new javax.swing.JPanel();
        btnActUpd1 = new javax.swing.JLabel();
        btnActUpd2 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        hdnIDCopy = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        pnlUsuarioCont = new javax.swing.JPanel();
        pnlInfoUsuario = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lblNom = new javax.swing.JLabel();
        lblCre = new javax.swing.JLabel();
        lblAcc = new javax.swing.JLabel();
        btnAutorizar = new javax.swing.JButton();
        txtAutorizarDlg = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        btnGenerar = new javax.swing.JPanel();
        lblBtnGenerar1 = new javax.swing.JLabel();
        lblBtnGenerar2 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        lblUsuarioHidenID = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        pnlNUsuario = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        lblInfoCrear = new javax.swing.JLabel();
        lblInfoCrear1 = new javax.swing.JLabel();
        btnCrearUsuario = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        txtNomN = new javax.swing.JTextField();
        txtAPatN = new javax.swing.JTextField();
        txtAMatN = new javax.swing.JTextField();
        passN = new javax.swing.JPasswordField();
        passConfN = new javax.swing.JPasswordField();
        jCheckBox2 = new javax.swing.JCheckBox();
        txtCredencialN = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        txtAutorizaNuevo = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        pnlControlUsuarios = new javax.swing.JPanel();
        lblCabecera = new javax.swing.JLabel();
        lblIcoInfo = new javax.swing.JLabel();
        lblIcoInfo1 = new javax.swing.JLabel();
        jLayeredPane4 = new javax.swing.JLayeredPane();
        pnlUsrVerif2 = new javax.swing.JPanel();
        txtCheckUser = new javax.swing.JTextField();
        btnCheckUser = new javax.swing.JButton();
        pnlBarraSeparadora = new javax.swing.JPanel();
        pnlUsuariosList = new javax.swing.JPanel();
        jLayeredPane2 = new javax.swing.JLayeredPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        tglActInc = new javax.swing.JToggleButton();
        hiddenLbl = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        pnlBackup = new javax.swing.JPanel();
        jLabel32 = new javax.swing.JLabel();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        pnlUsrVerif3 = new javax.swing.JPanel();
        pnlBarraSeparadora2 = new javax.swing.JPanel();
        btnValidarUsr = new javax.swing.JButton();
        txtCredUsr = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        pnlMenuBK = new javax.swing.JPanel();
        jLabel37 = new javax.swing.JLabel();
        cb1 = new javax.swing.JCheckBox();
        cb2 = new javax.swing.JCheckBox();
        cb3 = new javax.swing.JCheckBox();
        cb4 = new javax.swing.JCheckBox();
        cb5 = new javax.swing.JCheckBox();
        jButton5 = new javax.swing.JButton();
        jLabel39 = new javax.swing.JLabel();
        cb6 = new javax.swing.JCheckBox();
        jLabel38 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        lblLoadProf = new javax.swing.JLabel();
        lblUsuarios = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        contacto = new javax.swing.JDialog();
        jPanel2 = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        dlgLog = new javax.swing.JDialog();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        dlgConfirm = new javax.swing.JDialog();
        jPanel8 = new javax.swing.JPanel();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        btnComprobar = new javax.swing.JButton();
        jLabel43 = new javax.swing.JLabel();
        txtUsuario = new javax.swing.JTextField();
        txtPass = new javax.swing.JPasswordField();
        fcProfCSV = new javax.swing.JFileChooser();
        bkMenu = new javax.swing.JPanel();
        menuBar = new javax.swing.JPanel();
        lblHora = new javax.swing.JLabel();
        labelHora = new javax.swing.JLabel();
        lblFecha = new javax.swing.JLabel();
        labelFecha = new javax.swing.JLabel();
        btnProf = new javax.swing.JPanel();
        ico1 = new javax.swing.JLabel();
        lbl1 = new javax.swing.JLabel();
        btnDep = new javax.swing.JPanel();
        ico2 = new javax.swing.JLabel();
        lbl2 = new javax.swing.JLabel();
        btnAul = new javax.swing.JPanel();
        ico3 = new javax.swing.JLabel();
        lbl3 = new javax.swing.JLabel();
        btnVid = new javax.swing.JPanel();
        ico4 = new javax.swing.JLabel();
        lbl4 = new javax.swing.JLabel();
        menuBarIzq = new javax.swing.JPanel();
        lblLogo1 = new javax.swing.JLabel();
        lblLogo2 = new javax.swing.JLabel();
        lblLogo3 = new javax.swing.JLabel();
        lblDep = new javax.swing.JLabel();
        lblBienv = new javax.swing.JLabel();
        lblAjustes = new javax.swing.JLabel();
        principalBar = new javax.swing.JPanel();
        btnExt1 = new javax.swing.JPanel();
        icoExt1 = new javax.swing.JLabel();
        icoExtlbl1 = new javax.swing.JLabel();
        btnExt2 = new javax.swing.JPanel();
        icoExt3 = new javax.swing.JLabel();
        icoExtlbl3 = new javax.swing.JLabel();
        btnExt3 = new javax.swing.JPanel();
        icoExt2 = new javax.swing.JLabel();
        icoExtlbl2 = new javax.swing.JLabel();

        dlgUsuario.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        dlgUsuario.setTitle("[ADMIN USUARIOS]");
        dlgUsuario.setMinimumSize(new java.awt.Dimension(1024, 620));
        dlgUsuario.setModal(true);
        dlgUsuario.setModalityType(java.awt.Dialog.ModalityType.DOCUMENT_MODAL);
        dlgUsuario.setResizable(false);
        dlgUsuario.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                dlgUsuarioWindowClosed(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                dlgUsuarioWindowClosing(evt);
            }
        });

        jTabbedPane1.setBackground(new java.awt.Color(255, 255, 255));
        jTabbedPane1.setTabPlacement(javax.swing.JTabbedPane.LEFT);
        jTabbedPane1.setFont(new java.awt.Font("Trebuchet MS", 0, 11)); // NOI18N
        jTabbedPane1.setMaximumSize(new java.awt.Dimension(1024, 620));
        jTabbedPane1.setMinimumSize(new java.awt.Dimension(1024, 620));
        jTabbedPane1.setPreferredSize(new java.awt.Dimension(1024, 620));
        jTabbedPane1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jTabbedPane1StateChanged(evt);
            }
        });

        pnlMiUsuario.setBackground(new java.awt.Color(255, 255, 255));
        pnlMiUsuario.setFont(new java.awt.Font("Trebuchet MS", 0, 11)); // NOI18N
        pnlMiUsuario.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        jLabel1.setText("Información de Mi Usuario");
        pnlMiUsuario.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(335, 20, -1, -1));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Info_36px.png"))); // NOI18N
        pnlMiUsuario.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 550, -1, -1));

        jLabel6.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 191, 255));
        jLabel6.setText("<html><center>Aquí es posible imprimir su credencial nuevamente<br>y actualizar los datos de su usuario</center></html>");
        pnlMiUsuario.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 550, -1, -1));

        jLayeredPane3.setMinimumSize(new java.awt.Dimension(910, 440));
        jLayeredPane3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlUsrVerif1.setBackground(new java.awt.Color(255, 167, 38));
        pnlUsrVerif1.setLayout(new java.awt.GridBagLayout());

        txtUsuarioCred.setBackground(new java.awt.Color(255, 167, 38));
        txtUsuarioCred.setFont(new java.awt.Font("Verdana", 0, 18)); // NOI18N
        txtUsuarioCred.setForeground(new java.awt.Color(255, 255, 255));
        txtUsuarioCred.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtUsuarioCred.setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 5, 2, 1));
        txtUsuarioCred.setMinimumSize(new java.awt.Dimension(250, 30));
        txtUsuarioCred.setPreferredSize(new java.awt.Dimension(250, 30));
        txtUsuarioCred.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtUsuarioCredKeyTyped(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(30, 30, 0, 30);
        pnlUsrVerif1.add(txtUsuarioCred, gridBagConstraints);

        pnlBarraSeparadora1.setBackground(new java.awt.Color(100, 100, 100));
        pnlBarraSeparadora1.setMinimumSize(new java.awt.Dimension(250, 3));
        pnlBarraSeparadora1.setPreferredSize(new java.awt.Dimension(250, 3));

        javax.swing.GroupLayout pnlBarraSeparadora1Layout = new javax.swing.GroupLayout(pnlBarraSeparadora1);
        pnlBarraSeparadora1.setLayout(pnlBarraSeparadora1Layout);
        pnlBarraSeparadora1Layout.setHorizontalGroup(
            pnlBarraSeparadora1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 250, Short.MAX_VALUE)
        );
        pnlBarraSeparadora1Layout.setVerticalGroup(
            pnlBarraSeparadora1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(1, 0, 0, 0);
        pnlUsrVerif1.add(pnlBarraSeparadora1, gridBagConstraints);

        btnGetUsuario.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        btnGetUsuario.setForeground(new java.awt.Color(100, 100, 100));
        btnGetUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/FindUser_36px.png"))); // NOI18N
        btnGetUsuario.setText("<html>Buscar<br> Usuario<html>");
        btnGetUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGetUsuarioActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(18, 0, 11, 0);
        pnlUsrVerif1.add(btnGetUsuario, gridBagConstraints);

        jLabel16.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Debe escanear su credencial para esta caracteristica");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
        pnlUsrVerif1.add(jLabel16, gridBagConstraints);

        jLayeredPane3.add(pnlUsrVerif1, new org.netbeans.lib.awtextra.AbsoluteConstraints(305, 175, -1, -1));

        pnlUsuarioMod.setBackground(new java.awt.Color(94, 200, 130));

        jPanel5.setOpaque(false);
        jPanel5.setLayout(new java.awt.GridBagLayout());

        jLabel8.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Nombre");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        jPanel5.add(jLabel8, gridBagConstraints);

        jLabel9.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Apellido Paterno");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        jPanel5.add(jLabel9, gridBagConstraints);

        jLabel10.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Apellido Materno");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        jPanel5.add(jLabel10, gridBagConstraints);

        jLabel12.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Contraseña Nueva");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        jPanel5.add(jLabel12, gridBagConstraints);

        jLabel13.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Confirmar Constraseña");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        jPanel5.add(jLabel13, gridBagConstraints);

        txtNomUpd.setFont(new java.awt.Font("Verdana", 0, 13)); // NOI18N
        txtNomUpd.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 153, 102)));
        txtNomUpd.setMinimumSize(new java.awt.Dimension(200, 25));
        txtNomUpd.setPreferredSize(new java.awt.Dimension(200, 25));
        txtNomUpd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNomUpdKeyTyped(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 10, 0);
        jPanel5.add(txtNomUpd, gridBagConstraints);

        txtAPatUpd.setFont(new java.awt.Font("Verdana", 0, 13)); // NOI18N
        txtAPatUpd.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 153, 102)));
        txtAPatUpd.setMinimumSize(new java.awt.Dimension(200, 25));
        txtAPatUpd.setPreferredSize(new java.awt.Dimension(200, 25));
        txtAPatUpd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAPatUpdKeyTyped(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 10, 0);
        jPanel5.add(txtAPatUpd, gridBagConstraints);

        txtAMatUpd.setFont(new java.awt.Font("Verdana", 0, 13)); // NOI18N
        txtAMatUpd.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 153, 102)));
        txtAMatUpd.setMinimumSize(new java.awt.Dimension(200, 25));
        txtAMatUpd.setPreferredSize(new java.awt.Dimension(200, 25));
        txtAMatUpd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAMatUpdKeyTyped(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 10, 0);
        jPanel5.add(txtAMatUpd, gridBagConstraints);

        passUpd.setFont(new java.awt.Font("Verdana", 0, 13)); // NOI18N
        passUpd.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 153, 51)));
        passUpd.setMinimumSize(new java.awt.Dimension(200, 25));
        passUpd.setPreferredSize(new java.awt.Dimension(200, 25));
        passUpd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                passUpdKeyTyped(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 10, 0);
        jPanel5.add(passUpd, gridBagConstraints);

        passConfUpd.setFont(new java.awt.Font("Verdana", 0, 13)); // NOI18N
        passConfUpd.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 153, 51)));
        passConfUpd.setMinimumSize(new java.awt.Dimension(200, 25));
        passConfUpd.setPreferredSize(new java.awt.Dimension(200, 25));
        passConfUpd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                passConfUpdKeyTyped(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 10, 0);
        jPanel5.add(passConfUpd, gridBagConstraints);

        jLabel7.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Actualizar Usuario");

        chkBNewID.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        chkBNewID.setForeground(new java.awt.Color(255, 255, 255));
        chkBNewID.setText("Usar una credencial propia?");
        chkBNewID.setOpaque(false);
        chkBNewID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkBNewIDActionPerformed(evt);
            }
        });

        txtIDNew.setFont(new java.awt.Font("Verdana", 0, 24)); // NOI18N
        txtIDNew.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtIDNew.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 153, 0)));
        txtIDNew.setMinimumSize(new java.awt.Dimension(270, 40));
        txtIDNew.setPreferredSize(new java.awt.Dimension(270, 40));
        txtIDNew.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtIDNewKeyTyped(evt);
            }
        });

        btnActUpd.setBackground(new java.awt.Color(239, 239, 239));
        btnActUpd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnActUpdMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnActUpdMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnActUpdMouseExited(evt);
            }
        });
        btnActUpd.setLayout(new java.awt.GridBagLayout());

        btnActUpd1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Pencil_22px.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(15, 15, 15, 5);
        btnActUpd.add(btnActUpd1, gridBagConstraints);

        btnActUpd2.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        btnActUpd2.setText("<html>Actualizar <br>Información</html>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 12, 0, 15);
        btnActUpd.add(btnActUpd2, gridBagConstraints);

        jLabel11.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Debe escanear la credencial que desea usar");

        jButton2.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/User_24px.png"))); // NOI18N
        jButton2.setText("Regresar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        hdnIDCopy.setText("jLabel22");

        jLabel22.setBackground(new java.awt.Color(255, 255, 255));
        jLabel22.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setText("Si deja la contraseña en blanco esta no se actualizara");

        jLabel25.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/More Info_22px.png"))); // NOI18N

        javax.swing.GroupLayout pnlUsuarioModLayout = new javax.swing.GroupLayout(pnlUsuarioMod);
        pnlUsuarioMod.setLayout(pnlUsuarioModLayout);
        pnlUsuarioModLayout.setHorizontalGroup(
            pnlUsuarioModLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlUsuarioModLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel25)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel22)
                .addGap(291, 291, 291))
            .addGroup(pnlUsuarioModLayout.createSequentialGroup()
                .addGroup(pnlUsuarioModLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlUsuarioModLayout.createSequentialGroup()
                        .addGroup(pnlUsuarioModLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlUsuarioModLayout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlUsuarioModLayout.createSequentialGroup()
                                .addGap(42, 42, 42)
                                .addComponent(hdnIDCopy)))
                        .addGroup(pnlUsuarioModLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlUsuarioModLayout.createSequentialGroup()
                                .addGap(89, 89, 89)
                                .addGroup(pnlUsuarioModLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnlUsuarioModLayout.createSequentialGroup()
                                        .addGap(10, 10, 10)
                                        .addComponent(jLabel11))
                                    .addComponent(txtIDNew, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(pnlUsuarioModLayout.createSequentialGroup()
                                .addGap(147, 147, 147)
                                .addGroup(pnlUsuarioModLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnActUpd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(pnlUsuarioModLayout.createSequentialGroup()
                                        .addGap(19, 19, 19)
                                        .addComponent(jButton2))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlUsuarioModLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(chkBNewID)
                                .addGap(42, 42, 42))))
                    .addGroup(pnlUsuarioModLayout.createSequentialGroup()
                        .addGap(323, 323, 323)
                        .addComponent(jLabel7)))
                .addContainerGap(90, Short.MAX_VALUE))
        );
        pnlUsuarioModLayout.setVerticalGroup(
            pnlUsuarioModLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlUsuarioModLayout.createSequentialGroup()
                .addGroup(pnlUsuarioModLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlUsuarioModLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(hdnIDCopy))
                    .addGroup(pnlUsuarioModLayout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addComponent(chkBNewID)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtIDNew, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel11)
                        .addGap(40, 40, 40)
                        .addComponent(btnActUpd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlUsuarioModLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel25)
                    .addComponent(jLabel22))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLayeredPane3.add(pnlUsuarioMod, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 60, -1, -1));

        pnlUsuarioCont.setBackground(new java.awt.Color(135, 96, 85));
        pnlUsuarioCont.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        pnlUsuarioCont.setMinimumSize(new java.awt.Dimension(740, 314));
        pnlUsuarioCont.setPreferredSize(new java.awt.Dimension(740, 314));
        pnlUsuarioCont.setLayout(new java.awt.GridBagLayout());

        pnlInfoUsuario.setBackground(new java.awt.Color(212, 215, 205));
        pnlInfoUsuario.setMinimumSize(new java.awt.Dimension(700, 214));
        pnlInfoUsuario.setPreferredSize(new java.awt.Dimension(700, 214));

        jPanel1.setBackground(new java.awt.Color(223, 164, 135));
        jPanel1.setLayout(new java.awt.GridBagLayout());

        jLabel4.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Nivel de Acceso");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(30, 15, 15, 0);
        jPanel1.add(jLabel4, gridBagConstraints);

        jLabel3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Fecha de Creacion");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(30, 15, 0, 10);
        jPanel1.add(jLabel3, gridBagConstraints);

        jLabel2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Nombre");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 15, 0, 0);
        jPanel1.add(jLabel2, gridBagConstraints);

        lblNom.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        lblNom.setForeground(new java.awt.Color(255, 255, 255));
        lblNom.setText("jLabel5");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 10, 0, 5);
        jPanel1.add(lblNom, gridBagConstraints);

        lblCre.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        lblCre.setForeground(new java.awt.Color(255, 255, 255));
        lblCre.setText("jLabel6");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(30, 10, 0, 5);
        jPanel1.add(lblCre, gridBagConstraints);

        lblAcc.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        lblAcc.setForeground(new java.awt.Color(255, 255, 255));
        lblAcc.setText("jLabel7");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(30, 10, 0, 5);
        jPanel1.add(lblAcc, gridBagConstraints);

        btnAutorizar.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        btnAutorizar.setText("Autorizar");
        btnAutorizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAutorizarActionPerformed(evt);
            }
        });

        txtAutorizarDlg.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        txtAutorizarDlg.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtAutorizarDlg.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 153, 102), 1, true));
        txtAutorizarDlg.setMinimumSize(new java.awt.Dimension(250, 30));
        txtAutorizarDlg.setPreferredSize(new java.awt.Dimension(250, 30));
        txtAutorizarDlg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAutorizarDlgKeyTyped(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("SansSerif", 1, 10)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(136, 141, 148));
        jLabel14.setText("Un usuario administrador puede activar esta funcion");

        jLabel15.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(136, 141, 148));
        jLabel15.setText("Cambiar Nivel de Acceso");

        javax.swing.GroupLayout pnlInfoUsuarioLayout = new javax.swing.GroupLayout(pnlInfoUsuario);
        pnlInfoUsuario.setLayout(pnlInfoUsuarioLayout);
        pnlInfoUsuarioLayout.setHorizontalGroup(
            pnlInfoUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInfoUsuarioLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(pnlInfoUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlInfoUsuarioLayout.createSequentialGroup()
                        .addComponent(txtAutorizarDlg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlInfoUsuarioLayout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addGap(41, 41, 41))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlInfoUsuarioLayout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlInfoUsuarioLayout.createSequentialGroup()
                        .addComponent(btnAutorizar)
                        .addGap(115, 115, 115))))
        );
        pnlInfoUsuarioLayout.setVerticalGroup(
            pnlInfoUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInfoUsuarioLayout.createSequentialGroup()
                .addGroup(pnlInfoUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlInfoUsuarioLayout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addComponent(jLabel15)
                        .addGap(18, 18, 18)
                        .addComponent(txtAutorizarDlg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel14)
                        .addGap(11, 11, 11)
                        .addComponent(btnAutorizar))
                    .addGroup(pnlInfoUsuarioLayout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(38, 38, 38))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 20, 0, 20);
        pnlUsuarioCont.add(pnlInfoUsuario, gridBagConstraints);

        btnGenerar.setBackground(new java.awt.Color(206, 127, 80));
        btnGenerar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnGenerarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnGenerarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnGenerarMouseExited(evt);
            }
        });
        btnGenerar.setLayout(new java.awt.GridBagLayout());

        lblBtnGenerar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Print_36px.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(10, 15, 10, 0);
        btnGenerar.add(lblBtnGenerar1, gridBagConstraints);

        lblBtnGenerar2.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        lblBtnGenerar2.setForeground(new java.awt.Color(255, 217, 1));
        lblBtnGenerar2.setText("<html>Generar<<br>Credencial<html>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 8, 0, 15);
        btnGenerar.add(lblBtnGenerar2, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(18, 95, 12, 55);
        pnlUsuarioCont.add(btnGenerar, gridBagConstraints);

        jButton3.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/UpdUser_24px.png"))); // NOI18N
        jButton3.setText("Modificar Usuario");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 12, 11);
        pnlUsuarioCont.add(jButton3, gridBagConstraints);

        lblUsuarioHidenID.setText("jLabel17");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        pnlUsuarioCont.add(lblUsuarioHidenID, gridBagConstraints);

        jButton1.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/User_24px.png"))); // NOI18N
        jButton1.setText("Probar otro usuario");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 11, 12, 0);
        pnlUsuarioCont.add(jButton1, gridBagConstraints);

        jLayeredPane3.add(pnlUsuarioCont, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 80, -1, -1));

        pnlMiUsuario.add(jLayeredPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, -1, -1));

        jTabbedPane1.addTab("<html><b>Mi Usuario</b></html>", pnlMiUsuario);

        pnlNUsuario.setBackground(new java.awt.Color(255, 255, 255));
        pnlNUsuario.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel17.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        jLabel17.setText("Crear Nuevo Usuario");
        pnlNUsuario.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 34, -1, -1));

        lblInfoCrear.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblInfoCrear.setForeground(new java.awt.Color(0, 191, 255));
        lblInfoCrear.setText("Solo los usuarios Administradores pueden permitir crear un nuevo usuario");
        pnlNUsuario.add(lblInfoCrear, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 560, -1, -1));

        lblInfoCrear1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Info_36px.png"))); // NOI18N
        pnlNUsuario.add(lblInfoCrear1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 550, -1, -1));

        btnCrearUsuario.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        btnCrearUsuario.setForeground(new java.awt.Color(100, 100, 100));
        btnCrearUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/UserN_36px.png"))); // NOI18N
        btnCrearUsuario.setText("<html>Crear <br>Usuario<html>");
        btnCrearUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCrearUsuarioActionPerformed(evt);
            }
        });
        pnlNUsuario.add(btnCrearUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(361, 444, -1, -1));

        jPanel6.setBackground(new java.awt.Color(146, 215, 239));
        jPanel6.setLayout(new java.awt.GridBagLayout());

        jLabel19.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel19.setText("Nombre");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanel6.add(jLabel19, gridBagConstraints);

        jLabel20.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel20.setText("Apellido Paterno");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanel6.add(jLabel20, gridBagConstraints);

        jLabel21.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel21.setText("Apellido Materno");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanel6.add(jLabel21, gridBagConstraints);

        jLabel23.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel23.setText("Confirmar Contraseña");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanel6.add(jLabel23, gridBagConstraints);

        jLabel24.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel24.setText("Contraseña");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanel6.add(jLabel24, gridBagConstraints);

        txtNomN.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        txtNomN.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 191, 255)));
        txtNomN.setMinimumSize(new java.awt.Dimension(200, 25));
        txtNomN.setPreferredSize(new java.awt.Dimension(200, 25));
        txtNomN.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNomNKeyTyped(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 10, 0);
        jPanel6.add(txtNomN, gridBagConstraints);

        txtAPatN.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        txtAPatN.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 191, 255)));
        txtAPatN.setMinimumSize(new java.awt.Dimension(200, 25));
        txtAPatN.setPreferredSize(new java.awt.Dimension(200, 25));
        txtAPatN.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAPatNKeyTyped(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 10, 0);
        jPanel6.add(txtAPatN, gridBagConstraints);

        txtAMatN.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        txtAMatN.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 191, 255)));
        txtAMatN.setMinimumSize(new java.awt.Dimension(200, 25));
        txtAMatN.setPreferredSize(new java.awt.Dimension(200, 25));
        txtAMatN.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAMatNKeyTyped(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 10, 0);
        jPanel6.add(txtAMatN, gridBagConstraints);

        passN.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        passN.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 191, 255)));
        passN.setMinimumSize(new java.awt.Dimension(200, 25));
        passN.setPreferredSize(new java.awt.Dimension(200, 25));
        passN.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                passNKeyTyped(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 10, 0);
        jPanel6.add(passN, gridBagConstraints);

        passConfN.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        passConfN.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 191, 255)));
        passConfN.setMinimumSize(new java.awt.Dimension(200, 25));
        passConfN.setPreferredSize(new java.awt.Dimension(200, 25));
        passConfN.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                passConfNKeyTyped(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 10, 0);
        jPanel6.add(passConfN, gridBagConstraints);

        pnlNUsuario.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(119, 83, 364, -1));

        jCheckBox2.setText("Usar una credencial propia?");
        jCheckBox2.setOpaque(false);
        jCheckBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox2ActionPerformed(evt);
            }
        });
        pnlNUsuario.add(jCheckBox2, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 130, -1, -1));

        txtCredencialN.setFont(new java.awt.Font("Verdana", 0, 24)); // NOI18N
        txtCredencialN.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCredencialN.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(15, 126, 155), 3, true));
        txtCredencialN.setMinimumSize(new java.awt.Dimension(270, 40));
        txtCredencialN.setPreferredSize(new java.awt.Dimension(270, 40));
        txtCredencialN.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCredencialNKeyTyped(evt);
            }
        });
        pnlNUsuario.add(txtCredencialN, new org.netbeans.lib.awtextra.AbsoluteConstraints(501, 164, -1, -1));

        jPanel7.setBackground(new java.awt.Color(162, 234, 224));

        txtAutorizaNuevo.setFont(new java.awt.Font("Verdana", 0, 24)); // NOI18N
        txtAutorizaNuevo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtAutorizaNuevo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(15, 126, 155)));
        txtAutorizaNuevo.setMinimumSize(new java.awt.Dimension(270, 40));
        txtAutorizaNuevo.setPreferredSize(new java.awt.Dimension(270, 40));
        txtAutorizaNuevo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAutorizaNuevoKeyTyped(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("Autorizar Creación");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(171, 171, 171)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtAutorizaNuevo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(201, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(13, Short.MAX_VALUE)
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtAutorizaNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );

        pnlNUsuario.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(119, 326, -1, -1));

        jTabbedPane1.addTab("<html><b>Nuevo<br> Usuario</b><html>", pnlNUsuario);

        pnlControlUsuarios.setBackground(new java.awt.Color(255, 255, 255));
        pnlControlUsuarios.setFont(new java.awt.Font("Trebuchet MS", 0, 11)); // NOI18N
        pnlControlUsuarios.setMinimumSize(new java.awt.Dimension(1000, 546));
        pnlControlUsuarios.setPreferredSize(new java.awt.Dimension(1000, 546));
        pnlControlUsuarios.setLayout(new java.awt.GridBagLayout());

        lblCabecera.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        lblCabecera.setText("Usuarios del Sistema");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 10, 0);
        pnlControlUsuarios.add(lblCabecera, gridBagConstraints);

        lblIcoInfo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Info_36px.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 180, 0, 0);
        pnlControlUsuarios.add(lblIcoInfo, gridBagConstraints);

        lblIcoInfo1.setBackground(new java.awt.Color(255, 255, 255));
        lblIcoInfo1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblIcoInfo1.setForeground(new java.awt.Color(0, 191, 255));
        lblIcoInfo1.setText("Solo los usuarios administradores pueden acceder a esta caracteristica");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        pnlControlUsuarios.add(lblIcoInfo1, gridBagConstraints);

        jLayeredPane4.setMinimumSize(new java.awt.Dimension(900, 450));

        pnlUsrVerif2.setBackground(new java.awt.Color(94, 200, 130));
        pnlUsrVerif2.setLayout(new java.awt.GridBagLayout());

        txtCheckUser.setBackground(new java.awt.Color(94, 200, 130));
        txtCheckUser.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        txtCheckUser.setForeground(new java.awt.Color(255, 255, 255));
        txtCheckUser.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCheckUser.setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 1, 0, 1));
        txtCheckUser.setMinimumSize(new java.awt.Dimension(250, 30));
        txtCheckUser.setPreferredSize(new java.awt.Dimension(250, 30));
        txtCheckUser.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCheckUserKeyTyped(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTH;
        gridBagConstraints.insets = new java.awt.Insets(30, 35, 0, 35);
        pnlUsrVerif2.add(txtCheckUser, gridBagConstraints);

        btnCheckUser.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        btnCheckUser.setForeground(new java.awt.Color(128, 128, 128));
        btnCheckUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/check_36px.png"))); // NOI18N
        btnCheckUser.setText("Verificar Usuario");
        btnCheckUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCheckUserActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(15, 0, 20, 0);
        pnlUsrVerif2.add(btnCheckUser, gridBagConstraints);

        pnlBarraSeparadora.setBackground(new java.awt.Color(100, 100, 100));
        pnlBarraSeparadora.setMinimumSize(new java.awt.Dimension(250, 3));
        pnlBarraSeparadora.setPreferredSize(new java.awt.Dimension(250, 3));

        javax.swing.GroupLayout pnlBarraSeparadoraLayout = new javax.swing.GroupLayout(pnlBarraSeparadora);
        pnlBarraSeparadora.setLayout(pnlBarraSeparadoraLayout);
        pnlBarraSeparadoraLayout.setHorizontalGroup(
            pnlBarraSeparadoraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 250, Short.MAX_VALUE)
        );
        pnlBarraSeparadoraLayout.setVerticalGroup(
            pnlBarraSeparadoraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        pnlUsrVerif2.add(pnlBarraSeparadora, gridBagConstraints);

        pnlUsuariosList.setBackground(new java.awt.Color(94, 200, 130));
        pnlUsuariosList.setMinimumSize(new java.awt.Dimension(900, 510));
        pnlUsuariosList.setPreferredSize(new java.awt.Dimension(900, 510));
        pnlUsuariosList.setLayout(new java.awt.GridBagLayout());

        jLayeredPane2.setMinimumSize(new java.awt.Dimension(900, 380));
        jLayeredPane2.setPreferredSize(new java.awt.Dimension(900, 365));

        jScrollPane2.setPreferredSize(new java.awt.Dimension(820, 300));

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "USUARIO_NOM", "VIDEOPROYECTOR", "PROFESOR", "AULA", "FECHA", "HORA", "PRESTO / RECIBIÓ"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable2.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(jTable2);

        jScrollPane1.setPreferredSize(new java.awt.Dimension(820, 300));

        jTable1.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Identificador", "Nombre", "Ap Paterno", "Ap Materno", "Nvl de Acceso", "Creación"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
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
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setResizable(false);
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(15);
            jTable1.getColumnModel().getColumn(1).setPreferredWidth(25);
            jTable1.getColumnModel().getColumn(2).setResizable(false);
            jTable1.getColumnModel().getColumn(2).setPreferredWidth(16);
            jTable1.getColumnModel().getColumn(3).setResizable(false);
            jTable1.getColumnModel().getColumn(3).setPreferredWidth(16);
            jTable1.getColumnModel().getColumn(4).setResizable(false);
            jTable1.getColumnModel().getColumn(4).setPreferredWidth(15);
        }

        tglActInc.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        tglActInc.setText("Activos / Inactivos");
        tglActInc.setToolTipText("Prestamos que registra un usuario");
        tglActInc.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                tglActIncItemStateChanged(evt);
            }
        });

        hiddenLbl.setFont(new java.awt.Font("SansSerif", 0, 8)); // NOI18N
        hiddenLbl.setText("jLabel28");

        jButton4.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jButton4.setText("Regresar");
        jButton4.setMaximumSize(new java.awt.Dimension(121, 23));
        jButton4.setMinimumSize(new java.awt.Dimension(121, 23));
        jButton4.setPreferredSize(new java.awt.Dimension(121, 23));
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLayeredPane2.setLayer(jScrollPane2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(jScrollPane1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(tglActInc, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(hiddenLbl, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(jButton4, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPane2Layout = new javax.swing.GroupLayout(jLayeredPane2);
        jLayeredPane2.setLayout(jLayeredPane2Layout);
        jLayeredPane2Layout.setHorizontalGroup(
            jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane2Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jLayeredPane2Layout.createSequentialGroup()
                        .addComponent(tglActInc)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(hiddenLbl)
                        .addGap(27, 27, 27)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(40, Short.MAX_VALUE))
            .addGroup(jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jLayeredPane2Layout.createSequentialGroup()
                    .addGap(41, 41, 41)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(39, Short.MAX_VALUE)))
        );
        jLayeredPane2Layout.setVerticalGroup(
            jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(hiddenLbl))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane2Layout.createSequentialGroup()
                        .addComponent(tglActInc, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10))))
            .addGroup(jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jLayeredPane2Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(59, Short.MAX_VALUE)))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
        pnlUsuariosList.add(jLayeredPane2, gridBagConstraints);

        jLabel26.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 255, 255));
        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel26.setText("<html><center>Al hacer doble clic sobre un usuario se mostraran sus registros de prestamos<br>tambien es posible borrar su acceso al uso del sistema</center></html>");
        jLabel26.setMinimumSize(new java.awt.Dimension(444, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        pnlUsuariosList.add(jLabel26, gridBagConstraints);

        jLabel27.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/More Info_22px.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 180, 0, 0);
        pnlUsuariosList.add(jLabel27, gridBagConstraints);

        jLayeredPane4.setLayer(pnlUsrVerif2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane4.setLayer(pnlUsuariosList, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPane4Layout = new javax.swing.GroupLayout(jLayeredPane4);
        jLayeredPane4.setLayout(jLayeredPane4Layout);
        jLayeredPane4Layout.setHorizontalGroup(
            jLayeredPane4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 900, Short.MAX_VALUE)
            .addGroup(jLayeredPane4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jLayeredPane4Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(pnlUsrVerif2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(jLayeredPane4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane4Layout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlUsuariosList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jLayeredPane4Layout.setVerticalGroup(
            jLayeredPane4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 461, Short.MAX_VALUE)
            .addGroup(jLayeredPane4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jLayeredPane4Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(pnlUsrVerif2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(jLayeredPane4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane4Layout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlUsuariosList, javax.swing.GroupLayout.PREFERRED_SIZE, 439, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        pnlControlUsuarios.add(jLayeredPane4, gridBagConstraints);

        jTabbedPane1.addTab("<html><center><b>Administrar<br>Usuarios</b></center><html>", pnlControlUsuarios);

        pnlBackup.setBackground(new java.awt.Color(255, 255, 255));
        pnlBackup.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel32.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        jLabel32.setText("Backup y Restauracion de BD");
        pnlBackup.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(335, 20, -1, -1));

        jLayeredPane1.setMinimumSize(new java.awt.Dimension(910, 440));
        jLayeredPane1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlUsrVerif3.setBackground(new java.awt.Color(92, 107, 192));
        pnlUsrVerif3.setMinimumSize(new java.awt.Dimension(310, 157));
        pnlUsrVerif3.setPreferredSize(new java.awt.Dimension(310, 157));
        pnlUsrVerif3.setLayout(new java.awt.GridBagLayout());

        pnlBarraSeparadora2.setBackground(new java.awt.Color(204, 204, 204));
        pnlBarraSeparadora2.setMinimumSize(new java.awt.Dimension(250, 3));

        javax.swing.GroupLayout pnlBarraSeparadora2Layout = new javax.swing.GroupLayout(pnlBarraSeparadora2);
        pnlBarraSeparadora2.setLayout(pnlBarraSeparadora2Layout);
        pnlBarraSeparadora2Layout.setHorizontalGroup(
            pnlBarraSeparadora2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 250, Short.MAX_VALUE)
        );
        pnlBarraSeparadora2Layout.setVerticalGroup(
            pnlBarraSeparadora2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(1, 0, 0, 0);
        pnlUsrVerif3.add(pnlBarraSeparadora2, gridBagConstraints);

        btnValidarUsr.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        btnValidarUsr.setForeground(new java.awt.Color(102, 102, 102));
        btnValidarUsr.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/report_28px.png"))); // NOI18N
        btnValidarUsr.setText("Validar Usuario");
        btnValidarUsr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnValidarUsrActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(18, 0, 11, 0);
        pnlUsrVerif3.add(btnValidarUsr, gridBagConstraints);

        txtCredUsr.setBackground(new java.awt.Color(92, 107, 192));
        txtCredUsr.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        txtCredUsr.setForeground(new java.awt.Color(255, 255, 255));
        txtCredUsr.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCredUsr.setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 2, 2, 2));
        txtCredUsr.setMinimumSize(new java.awt.Dimension(250, 30));
        txtCredUsr.setPreferredSize(new java.awt.Dimension(250, 30));
        txtCredUsr.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCredUsrKeyTyped(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(30, 30, 0, 30);
        pnlUsrVerif3.add(txtCredUsr, gridBagConstraints);

        jLabel36.setForeground(new java.awt.Color(255, 255, 255));
        jLabel36.setText("Ingrese su credencial de administrador");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(1, 0, 2, 0);
        pnlUsrVerif3.add(jLabel36, gridBagConstraints);

        jLayeredPane1.add(pnlUsrVerif3, new org.netbeans.lib.awtextra.AbsoluteConstraints(305, 175, -1, -1));

        pnlMenuBK.setBackground(new java.awt.Color(92, 107, 192));
        pnlMenuBK.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel37.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(255, 255, 255));
        jLabel37.setText("Indicar cuales tablas seran generadas en formato csv");
        pnlMenuBK.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(28, 23, -1, -1));

        cb1.setBackground(new java.awt.Color(245, 245, 245));
        cb1.setForeground(new java.awt.Color(255, 255, 255));
        cb1.setText("Departamentos");
        cb1.setOpaque(false);
        pnlMenuBK.add(cb1, new org.netbeans.lib.awtextra.AbsoluteConstraints(54, 77, -1, -1));

        cb2.setBackground(new java.awt.Color(245, 245, 245));
        cb2.setForeground(new java.awt.Color(255, 255, 255));
        cb2.setText("Profesores");
        cb2.setOpaque(false);
        pnlMenuBK.add(cb2, new org.netbeans.lib.awtextra.AbsoluteConstraints(192, 77, -1, -1));

        cb3.setBackground(new java.awt.Color(245, 245, 245));
        cb3.setForeground(new java.awt.Color(255, 255, 255));
        cb3.setText("VideoProyectores");
        cb3.setOpaque(false);
        pnlMenuBK.add(cb3, new org.netbeans.lib.awtextra.AbsoluteConstraints(316, 77, -1, -1));

        cb4.setBackground(new java.awt.Color(245, 245, 245));
        cb4.setForeground(new java.awt.Color(255, 255, 255));
        cb4.setText("Aulas");
        cb4.setOpaque(false);
        pnlMenuBK.add(cb4, new org.netbeans.lib.awtextra.AbsoluteConstraints(54, 116, -1, -1));

        cb5.setBackground(new java.awt.Color(245, 245, 245));
        cb5.setForeground(new java.awt.Color(255, 255, 255));
        cb5.setText("Articulos");
        cb5.setOpaque(false);
        pnlMenuBK.add(cb5, new org.netbeans.lib.awtextra.AbsoluteConstraints(192, 116, -1, -1));

        jButton5.setText("Generar Respaldo CSV");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        pnlMenuBK.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 150, -1, -1));

        jLabel39.setForeground(new java.awt.Color(255, 255, 255));
        jLabel39.setText("Los archivos seran generados en la ubicacion indicada al presionar el boton");
        pnlMenuBK.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(54, 212, -1, -1));

        cb6.setForeground(new java.awt.Color(255, 255, 255));
        cb6.setText("Usuarios");
        cb6.setOpaque(false);
        pnlMenuBK.add(cb6, new org.netbeans.lib.awtextra.AbsoluteConstraints(316, 116, -1, -1));

        jLabel38.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/files-90.png"))); // NOI18N
        pnlMenuBK.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 120, 90, 90));

        jButton6.setText("Respaldo DB Completo");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        pnlMenuBK.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 180, -1, -1));

        jButton7.setText("Recuperar DB Completo");
        jButton7.setEnabled(false);
        pnlMenuBK.add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 180, -1, -1));

        lblLoadProf.setText("Cargar Profesores");
        lblLoadProf.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblLoadProfMouseClicked(evt);
            }
        });
        pnlMenuBK.add(lblLoadProf, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 230, -1, -1));

        lblUsuarios.setText("Cargar Usuarios");
        lblUsuarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblUsuariosMouseClicked(evt);
            }
        });
        pnlMenuBK.add(lblUsuarios, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 230, -1, -1));

        jLayeredPane1.add(pnlMenuBK, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 100, 550, 250));

        pnlBackup.add(jLayeredPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, -1, -1));

        jLabel34.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(0, 191, 255));
        jLabel34.setText("Solo los usuarios administradores pueden acceder a esta caracteristica");
        pnlBackup.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 530, -1, -1));

        jLabel35.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Info_36px.png"))); // NOI18N
        pnlBackup.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 520, -1, -1));

        jTabbedPane1.addTab("<html><center><b>Backup<br>de BD</b></center><html>", pnlBackup);

        javax.swing.GroupLayout dlgUsuarioLayout = new javax.swing.GroupLayout(dlgUsuario.getContentPane());
        dlgUsuario.getContentPane().setLayout(dlgUsuarioLayout);
        dlgUsuarioLayout.setHorizontalGroup(
            dlgUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        dlgUsuarioLayout.setVerticalGroup(
            dlgUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        dlgUsuario.getAccessibleContext().setAccessibleParent(this);

        contacto.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        contacto.setTitle("[CONTACTO]");
        contacto.setMinimumSize(new java.awt.Dimension(450, 350));
        contacto.setModal(true);

        jPanel2.setBackground(new java.awt.Color(102, 153, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 4));
        jPanel2.setMinimumSize(new java.awt.Dimension(400, 300));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel28.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(255, 255, 255));
        jLabel28.setText("Creado por:");
        jPanel2.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 30, -1, 33));

        jLabel29.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(255, 255, 255));
        jLabel29.setText("Gómez Sotelo Juan Jesús");
        jPanel2.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 90, -1, -1));

        jLabel30.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(255, 255, 255));
        jLabel30.setText("Correos:");
        jPanel2.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 150, -1, -1));

        jTextField1.setEditable(false);
        jTextField1.setBackground(new java.awt.Color(102, 153, 255));
        jTextField1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextField1.setForeground(new java.awt.Color(255, 255, 255));
        jTextField1.setText("GomezSoteloJuan@gmail.com");
        jTextField1.setBorder(null);
        jPanel2.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 190, -1, 30));

        jTextField2.setEditable(false);
        jTextField2.setBackground(new java.awt.Color(102, 153, 255));
        jTextField2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextField2.setForeground(new java.awt.Color(255, 255, 255));
        jTextField2.setText("10orangeblue@gmail.com");
        jTextField2.setBorder(null);
        jPanel2.add(jTextField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 230, -1, 30));

        jLabel33.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/email-60.png"))); // NOI18N
        jPanel2.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 180, 60, 60));

        jLabel31.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/niceGuy-60.png"))); // NOI18N
        jPanel2.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 70, -1, -1));

        javax.swing.GroupLayout contactoLayout = new javax.swing.GroupLayout(contacto.getContentPane());
        contacto.getContentPane().setLayout(contactoLayout);
        contactoLayout.setHorizontalGroup(
            contactoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        contactoLayout.setVerticalGroup(
            contactoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );

        dlgLog.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        dlgLog.setTitle("[Log]");
        dlgLog.setMinimumSize(new java.awt.Dimension(550, 570));
        dlgLog.setModal(true);

        jPanel3.setBackground(new java.awt.Color(204, 204, 204));
        jPanel3.setMinimumSize(new java.awt.Dimension(541, 550));
        jPanel3.setPreferredSize(new java.awt.Dimension(541, 550));

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
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
        jScrollPane3.setViewportView(jTable3);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 541, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 550, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout dlgLogLayout = new javax.swing.GroupLayout(dlgLog.getContentPane());
        dlgLog.getContentPane().setLayout(dlgLogLayout);
        dlgLogLayout.setHorizontalGroup(
            dlgLogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        dlgLogLayout.setVerticalGroup(
            dlgLogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sistema Administrador de Video Proyectores");
        setIconImage(img.getImage());
        setMinimumSize(new java.awt.Dimension(1024, 585));
        setResizable(false);

        bkMenu.setBackground(new java.awt.Color(255, 255, 255));
        bkMenu.setPreferredSize(new java.awt.Dimension(1024, 585));
        bkMenu.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        menuBar.setBackground(new java.awt.Color(18, 182, 18));
        menuBar.setMinimumSize(new java.awt.Dimension(395, 585));
        menuBar.setPreferredSize(new java.awt.Dimension(395, 585));
        menuBar.setLayout(new java.awt.GridBagLayout());

        lblHora.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        lblHora.setForeground(new java.awt.Color(255, 255, 255));
        lblHora.setText("Hora");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 15, 0);
        menuBar.add(lblHora, gridBagConstraints);

        labelHora.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        labelHora.setForeground(new java.awt.Color(255, 255, 255));
        labelHora.setText("jLabel5");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 25, 0);
        menuBar.add(labelHora, gridBagConstraints);

        lblFecha.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        lblFecha.setForeground(new java.awt.Color(255, 255, 255));
        lblFecha.setText("Fecha");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 15, 0);
        menuBar.add(lblFecha, gridBagConstraints);

        labelFecha.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        labelFecha.setForeground(new java.awt.Color(255, 255, 255));
        labelFecha.setText("jLabel7");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 25, 0);
        menuBar.add(labelFecha, gridBagConstraints);

        btnProf.setBackground(new java.awt.Color(239, 239, 239));
        btnProf.setPreferredSize(new java.awt.Dimension(265, 64));
        btnProf.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnProfMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnProfMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnProfMouseExited(evt);
            }
        });

        ico1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/prof_42px.png"))); // NOI18N

        lbl1.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        lbl1.setForeground(new java.awt.Color(43, 212, 128));
        lbl1.setText("Profesores");

        javax.swing.GroupLayout btnProfLayout = new javax.swing.GroupLayout(btnProf);
        btnProf.setLayout(btnProfLayout);
        btnProfLayout.setHorizontalGroup(
            btnProfLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnProfLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(ico1)
                .addGap(18, 18, 18)
                .addComponent(lbl1)
                .addContainerGap())
        );
        btnProfLayout.setVerticalGroup(
            btnProfLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnProfLayout.createSequentialGroup()
                .addGroup(btnProfLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(btnProfLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(ico1))
                    .addGroup(btnProfLayout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(lbl1)))
                .addContainerGap())
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(0, 25, 23, 25);
        menuBar.add(btnProf, gridBagConstraints);

        btnDep.setBackground(new java.awt.Color(239, 239, 239));
        btnDep.setPreferredSize(new java.awt.Dimension(265, 64));
        btnDep.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnDepMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnDepMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnDepMouseExited(evt);
            }
        });

        ico2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/depart_42px.png"))); // NOI18N

        lbl2.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        lbl2.setForeground(new java.awt.Color(43, 212, 128));
        lbl2.setText("Departamentos");

        javax.swing.GroupLayout btnDepLayout = new javax.swing.GroupLayout(btnDep);
        btnDep.setLayout(btnDepLayout);
        btnDepLayout.setHorizontalGroup(
            btnDepLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnDepLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(ico2)
                .addGap(18, 18, 18)
                .addComponent(lbl2)
                .addContainerGap())
        );
        btnDepLayout.setVerticalGroup(
            btnDepLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnDepLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ico2)
                .addGap(24, 24, 24))
            .addGroup(btnDepLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(lbl2)
                .addContainerGap())
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 23, 0);
        menuBar.add(btnDep, gridBagConstraints);

        btnAul.setBackground(new java.awt.Color(239, 239, 239));
        btnAul.setPreferredSize(new java.awt.Dimension(265, 64));
        btnAul.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAulMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAulMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAulMouseExited(evt);
            }
        });

        ico3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/aul_42px.png"))); // NOI18N

        lbl3.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        lbl3.setForeground(new java.awt.Color(43, 212, 128));
        lbl3.setText("Aulas");

        javax.swing.GroupLayout btnAulLayout = new javax.swing.GroupLayout(btnAul);
        btnAul.setLayout(btnAulLayout);
        btnAulLayout.setHorizontalGroup(
            btnAulLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnAulLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(ico3)
                .addGap(18, 18, 18)
                .addComponent(lbl3)
                .addContainerGap())
        );
        btnAulLayout.setVerticalGroup(
            btnAulLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnAulLayout.createSequentialGroup()
                .addGroup(btnAulLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(btnAulLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(ico3))
                    .addGroup(btnAulLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(lbl3)))
                .addContainerGap())
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 23, 0);
        menuBar.add(btnAul, gridBagConstraints);

        btnVid.setBackground(new java.awt.Color(239, 239, 239));
        btnVid.setPreferredSize(new java.awt.Dimension(265, 64));
        btnVid.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnVidMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnVidMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnVidMouseExited(evt);
            }
        });

        ico4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Video Projector_42px.png"))); // NOI18N

        lbl4.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        lbl4.setForeground(new java.awt.Color(43, 212, 128));
        lbl4.setText("Video Proyectores");

        javax.swing.GroupLayout btnVidLayout = new javax.swing.GroupLayout(btnVid);
        btnVid.setLayout(btnVidLayout);
        btnVidLayout.setHorizontalGroup(
            btnVidLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnVidLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(ico4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl4)
                .addContainerGap())
        );
        btnVidLayout.setVerticalGroup(
            btnVidLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnVidLayout.createSequentialGroup()
                .addGroup(btnVidLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(btnVidLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(ico4))
                    .addGroup(btnVidLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(lbl4)))
                .addContainerGap())
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 23, 0);
        menuBar.add(btnVid, gridBagConstraints);

        bkMenu.add(menuBar, new org.netbeans.lib.awtextra.AbsoluteConstraints(629, 0, -1, -1));

        menuBarIzq.setBackground(new java.awt.Color(255, 255, 255));
        menuBarIzq.setMinimumSize(new java.awt.Dimension(629, 585));

        lblLogo1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/sep.png"))); // NOI18N

        lblLogo2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/tecnm2.png"))); // NOI18N
        lblLogo2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblLogo2MouseClicked(evt);
            }
        });

        lblLogo3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/itt.png"))); // NOI18N
        lblLogo3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblLogo3MouseClicked(evt);
            }
        });

        lblDep.setFont(new java.awt.Font("Trebuchet MS", 0, 24)); // NOI18N
        lblDep.setText("Departamento de Desarrollo Académico");

        lblBienv.setFont(new java.awt.Font("Trebuchet MS", 1, 36)); // NOI18N
        lblBienv.setText("Bienvenid@");

        lblAjustes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/opciones_28px.png"))); // NOI18N
        lblAjustes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblAjustesMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblAjustesMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblAjustesMouseExited(evt);
            }
        });

        principalBar.setBackground(new java.awt.Color(39, 147, 230));

        btnExt1.setBackground(new java.awt.Color(239, 239, 239));
        btnExt1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnExt1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnExt1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnExt1MouseExited(evt);
            }
        });
        btnExt1.setLayout(new java.awt.GridBagLayout());

        icoExt1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/prestamo_32px.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 30, 10, 0);
        btnExt1.add(icoExt1, gridBagConstraints);

        icoExtlbl1.setFont(new java.awt.Font("Verdana", 1, 21)); // NOI18N
        icoExtlbl1.setForeground(new java.awt.Color(107, 107, 107));
        icoExtlbl1.setText("Prestamo");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 30);
        btnExt1.add(icoExtlbl1, gridBagConstraints);

        btnExt2.setBackground(new java.awt.Color(239, 239, 239));
        btnExt2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnExt2MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnExt2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnExt2MouseExited(evt);
            }
        });
        btnExt2.setLayout(new java.awt.GridBagLayout());

        icoExt3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/devolu_32px.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(10, 30, 10, 0);
        btnExt2.add(icoExt3, gridBagConstraints);

        icoExtlbl3.setFont(new java.awt.Font("Verdana", 1, 21)); // NOI18N
        icoExtlbl3.setForeground(new java.awt.Color(107, 107, 107));
        icoExtlbl3.setText("Devolución");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 30);
        btnExt2.add(icoExtlbl3, gridBagConstraints);

        btnExt3.setBackground(new java.awt.Color(239, 239, 239));
        btnExt3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnExt3MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnExt3MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnExt3MouseExited(evt);
            }
        });
        btnExt3.setLayout(new java.awt.GridBagLayout());

        icoExt2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/registr_32px.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(10, 30, 10, 0);
        btnExt3.add(icoExt2, gridBagConstraints);

        icoExtlbl2.setFont(new java.awt.Font("Verdana", 1, 21)); // NOI18N
        icoExtlbl2.setForeground(new java.awt.Color(107, 107, 107));
        icoExtlbl2.setText("Registros");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 30);
        btnExt3.add(icoExtlbl2, gridBagConstraints);

        javax.swing.GroupLayout principalBarLayout = new javax.swing.GroupLayout(principalBar);
        principalBar.setLayout(principalBarLayout);
        principalBarLayout.setHorizontalGroup(
            principalBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, principalBarLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(principalBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnExt2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnExt3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnExt1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(35, 35, 35))
        );
        principalBarLayout.setVerticalGroup(
            principalBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, principalBarLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(btnExt1, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(btnExt2, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(btnExt3, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );

        javax.swing.GroupLayout menuBarIzqLayout = new javax.swing.GroupLayout(menuBarIzq);
        menuBarIzq.setLayout(menuBarIzqLayout);
        menuBarIzqLayout.setHorizontalGroup(
            menuBarIzqLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuBarIzqLayout.createSequentialGroup()
                .addGroup(menuBarIzqLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(menuBarIzqLayout.createSequentialGroup()
                        .addGroup(menuBarIzqLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(menuBarIzqLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(lblAjustes))
                            .addGroup(menuBarIzqLayout.createSequentialGroup()
                                .addGap(41, 41, 41)
                                .addComponent(principalBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                        .addComponent(lblLogo3))
                    .addGroup(menuBarIzqLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(menuBarIzqLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(menuBarIzqLayout.createSequentialGroup()
                                .addGroup(menuBarIzqLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(menuBarIzqLayout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(lblBienv))
                                    .addComponent(lblDep))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, menuBarIzqLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(lblLogo1)))))
                .addContainerGap())
            .addGroup(menuBarIzqLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(menuBarIzqLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(lblLogo2)
                    .addContainerGap(419, Short.MAX_VALUE)))
        );
        menuBarIzqLayout.setVerticalGroup(
            menuBarIzqLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, menuBarIzqLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(lblLogo1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 55, Short.MAX_VALUE)
                .addComponent(lblDep)
                .addGap(21, 21, 21)
                .addComponent(lblBienv)
                .addGap(18, 18, 18)
                .addGroup(menuBarIzqLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblLogo3)
                    .addGroup(menuBarIzqLayout.createSequentialGroup()
                        .addComponent(principalBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblAjustes)))
                .addContainerGap())
            .addGroup(menuBarIzqLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(menuBarIzqLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(lblLogo2)
                    .addContainerGap(491, Short.MAX_VALUE)))
        );

        bkMenu.add(menuBarIzq, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bkMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bkMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnProfMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnProfMouseClicked
        btnProf.setBackground(new Color(240, 240, 240));
        try {
            Profesor profe = new Profesor();
            profe.setVisible(true);
            
            this.setVisible(false);
            this.dispose();
        } catch (SQLException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnProfMouseClicked

    private void btnProfMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnProfMouseEntered
        btnProf.setBackground(new Color(43, 212, 128));
    }//GEN-LAST:event_btnProfMouseEntered

    private void btnProfMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnProfMouseExited
        btnProf.setBackground(new Color(240, 240, 240));
    }//GEN-LAST:event_btnProfMouseExited

    private void btnDepMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDepMouseClicked
        btnDep.setBackground(new Color(240, 240, 240));
        try {
            Departamento depart = new Departamento();
            depart.setVisible(true);
            
            this.setVisible(false);
            this.dispose();            
        } catch (SQLException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnDepMouseClicked

    private void btnDepMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDepMouseEntered
        btnDep.setBackground(new Color(43, 212, 128));
    }//GEN-LAST:event_btnDepMouseEntered

    private void btnDepMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDepMouseExited
        btnDep.setBackground(new Color(240, 240, 240));
    }//GEN-LAST:event_btnDepMouseExited

    private void btnAulMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAulMouseClicked
        btnAul.setBackground(new Color(240, 240, 240));
        try {
            Aula aula = new Aula();
            aula.setVisible(true);
            
            this.setVisible(false);
            this.dispose();
        } catch (SQLException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnAulMouseClicked

    private void btnAulMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAulMouseEntered
        btnAul.setBackground(new Color(43, 212, 128));
    }//GEN-LAST:event_btnAulMouseEntered

    private void btnAulMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAulMouseExited
        btnAul.setBackground(new Color(240, 240, 240));
    }//GEN-LAST:event_btnAulMouseExited

    private void btnVidMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnVidMouseClicked
        btnVid.setBackground(new Color(240, 240, 240));
        try {
            Videoproyector vid = new Videoproyector();
            vid.setVisible(true);
            this.setVisible(false);
            this.dispose();
        } catch (SQLException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnVidMouseClicked

    private void btnVidMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnVidMouseEntered
        btnVid.setBackground(new Color(43, 212, 128));
    }//GEN-LAST:event_btnVidMouseEntered

    private void btnVidMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnVidMouseExited
        btnVid.setBackground(new Color(240, 240, 240));
    }//GEN-LAST:event_btnVidMouseExited

    private void btnExt1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExt1MouseClicked
        btnExt1.setBackground(new Color(239, 239, 239));
        icoExtlbl1.setForeground(new Color(107, 107, 107));
        icoExt1.setIcon(pres);
//        try {
            AlPrestamo pres = new proyector.AlPrestamo();
            pres.setVisible(true);
            this.setVisible(false);
            this.dispose();
//        } catch (SQLException ex) {
//            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }//GEN-LAST:event_btnExt1MouseClicked

    private void btnExt1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExt1MouseEntered
        btnExt1.setBackground(new Color(48, 84, 230));
        icoExtlbl1.setForeground(new Color(230, 230, 250));
        icoExt1.setIcon(presW);
    }//GEN-LAST:event_btnExt1MouseEntered

    private void btnExt1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExt1MouseExited
        btnExt1.setBackground(new Color(239, 239, 239));
        icoExtlbl1.setForeground(new Color(107, 107, 107));
        icoExt1.setIcon(pres);
    }//GEN-LAST:event_btnExt1MouseExited

    private void lblAjustesMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAjustesMouseEntered
        ToolTipManager.sharedInstance().setDismissDelay(6000);
    }//GEN-LAST:event_lblAjustesMouseEntered

    private void lblAjustesMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAjustesMouseExited
        ToolTipManager.sharedInstance().setDismissDelay(ToolTipManager.sharedInstance().getDismissDelay());
    }//GEN-LAST:event_lblAjustesMouseExited

    private void lblAjustesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAjustesMouseClicked
        dlgUsuario.setLocationRelativeTo(bkMenu);
        dlgUsuario.setVisible(true);
    }//GEN-LAST:event_lblAjustesMouseClicked

    private void btnExt2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExt2MouseClicked
        btnExt2.setBackground(new Color(239, 239, 239));
        icoExtlbl3.setForeground(new Color(107, 107, 107));
        icoExt3.setIcon(dev);

        ADevolucion dev = new ADevolucion();
        dev.setVisible(true);
        
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_btnExt2MouseClicked

    private void btnExt2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExt2MouseEntered
        btnExt2.setBackground(new Color(48, 84, 230));
        icoExtlbl3.setForeground(new Color(230, 230, 250));
        icoExt3.setIcon(devW);
    }//GEN-LAST:event_btnExt2MouseEntered

    private void btnExt2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExt2MouseExited
        btnExt2.setBackground(new Color(239, 239, 239));
        icoExtlbl3.setForeground(new Color(107, 107, 107));
        icoExt3.setIcon(dev);
    }//GEN-LAST:event_btnExt2MouseExited

    private void btnExt3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExt3MouseClicked
        btnExt3.setBackground(new Color(239, 239, 239));
        icoExtlbl2.setForeground(new Color(107, 107, 107));
        icoExt2.setIcon(reg);

        ARegistro reg = new ARegistro();
        reg.setVisible(true);
        
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_btnExt3MouseClicked

    private void btnExt3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExt3MouseEntered
        btnExt3.setBackground(new Color(48, 84, 230));
        icoExtlbl2.setForeground(new Color(230, 230, 250));
        icoExt2.setIcon(regW);
    }//GEN-LAST:event_btnExt3MouseEntered

    private void btnExt3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExt3MouseExited
        btnExt3.setBackground(new Color(239, 239, 239));
        icoExtlbl2.setForeground(new Color(107, 107, 107));
        icoExt2.setIcon(reg);
    }//GEN-LAST:event_btnExt3MouseExited

    private void btnGenerarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGenerarMouseEntered
        btnGenerar.setBackground(new Color(206, 150, 80));
    }//GEN-LAST:event_btnGenerarMouseEntered

    private void btnGenerarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGenerarMouseClicked
        btnGenerar.setBackground(new Color(206, 127, 80));
        GenerarReportes g = new GenerarReportes();
        g.credencialUsuario(lblUsuarioHidenID.getText().trim());
//        dlgUsuario.setAlwaysOnTop(true);
//        dlgUsuario.setAlwaysOnTop(false);
    }//GEN-LAST:event_btnGenerarMouseClicked

    private void btnGenerarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGenerarMouseExited
        btnGenerar.setBackground(new Color(206, 127, 80));
    }//GEN-LAST:event_btnGenerarMouseExited

    private void btnGetUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGetUsuarioActionPerformed
//obtener texto sin espacios y no vacio
        String usuario = txtUsuarioCred.getText().trim();
        String[] datos = new String[6];
        //comprobar que no este en blanco al presionar el boton
        if (!usuario.isEmpty()) {
            System.out.println("Credencial a comprobar:" + usuario);
            try {
                UsuarioReadDB leer = new UsuarioReadDB();
                //leer usuario
                boolean existe = leer.getExisteUsuario(usuario);
                //comprobar que existe
                if (existe) {
                    System.out.println("Este usuario existe!!!");
                    //indicar datos en lblNom lblCre lblAcc en labels
                    datos = leer.getUsuario(usuario);
                    setDatosPnlContenido(datos);

                    //visibilidad
                    pnlUsrVerif1.setVisible(false);
                    pnlUsuarioCont.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "No se reconocio al usuario\nPruebe a usar nuevamente el escaner", "Advertencia", JOptionPane.WARNING_MESSAGE);
                }
            } catch (Exception e) {
                System.out.println("Error al recuperar informacion de LeerInicio " + e);
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(null, "No se reconocio al usuario\nPruebe a usar nuevamente el escaner", "Advertencia", JOptionPane.WARNING_MESSAGE);
            System.out.println("no se leyo nada");
        }

        txtUsuarioCred.setText("");
    }//GEN-LAST:event_btnGetUsuarioActionPerformed

    public void setDatosPnlContenido(String[] datos) {
        lblNom.setText(datos[1] + " " + datos[2] + " " + datos[3]);
        lblCre.setText(datos[5]);
        lblAcc.setText(Boolean.valueOf(datos[4]) ? "Administrador" : "Normal");
        lblUsuarioHidenID.setText(datos[0]);
    }
    private void chkBNewIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkBNewIDActionPerformed
        if (chkBNewID.isSelected()) {
            txtIDNew.setEnabled(true);
        } else {
            txtIDNew.setEnabled(false);
        }
    }//GEN-LAST:event_chkBNewIDActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        try {
            String reg = lblUsuarioHidenID.getText().trim();
            UsuarioReadDB leer = new UsuarioReadDB();
            String datos[] = new String[6];
            datos = leer.getUsuario(reg);
            txtNomUpd.setText(datos[1]);
            txtAPatUpd.setText(datos[2]);
            txtAMatUpd.setText(datos[3]);

            hdnIDCopy.setText(datos[0]);
            pnlUsuarioCont.setVisible(false);
            pnlUsuarioMod.setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void dlgUsuarioWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_dlgUsuarioWindowClosed

    }//GEN-LAST:event_dlgUsuarioWindowClosed

    private void btnAutorizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAutorizarActionPerformed
        String autorizar = txtAutorizarDlg.getText().trim();
        if (!autorizar.isEmpty()) {
            try {
                UsuarioReadDB leer = new UsuarioReadDB();
                if (leer.getUsuarioNvl(autorizar)) {

                    String idUsuarioActual = lblUsuarioHidenID.getText().trim();
                    //comprobar que no sea el ultimo usuario admin
                    if (leer.getUsuariosAdminNUM() >= 1) {
                        //comprobar que el usuario actual su nivel admin o usuario normal
                        if (!idUsuarioActual.equals(autorizar)) {
                            if (leer.getUsuarioNvl(idUsuarioActual)) {
                                int opc = JOptionPane.showConfirmDialog(null, "Esta seguro de cambiar el usuario al acceso normal?", "Advertencia", JOptionPane.WARNING_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
                                if (opc == JOptionPane.OK_OPTION) {
                                    UsuarioCreateDB genU = new UsuarioCreateDB();
                                    genU.setUsuarioAdmin(lblUsuarioHidenID.getText().trim(), false);
                                }
                            } else {
                                int opc = JOptionPane.showConfirmDialog(null, "Esta seguro de ascender el usuario al acceso Administrador?", "Advertencia", JOptionPane.WARNING_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
                                UsuarioCreateDB genU = new UsuarioCreateDB();
                                genU.setUsuarioAdmin(lblUsuarioHidenID.getText().trim(), true);
                            }
                            JOptionPane.showMessageDialog(null, "Listo", "", JOptionPane.INFORMATION_MESSAGE);
                            pnlUsrVerif1.setVisible(true);
                            pnlUsuarioCont.setVisible(false);
                        } else {
                            JOptionPane.showMessageDialog(null, "No se puede permitir esta acción\n\nNo debe usar su misma credencial para esta acción", "Peligro", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "No se puede permitir esta acción\nEsto es porque solo existe\nun usuario administrador", "Peligro", JOptionPane.ERROR_MESSAGE);
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Este usurio no le puede permitir el ascenso de nivel", "Información", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (SQLException ex) {
                Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "No puede dejar el campo anterior en blanco", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
        txtAutorizarDlg.setText("");
    }//GEN-LAST:event_btnAutorizarActionPerformed

    private void dlgUsuarioWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_dlgUsuarioWindowClosing
        //int opc = JOptionPane.showConfirmDialog(null, "Esta seguro no seguir en esta ventana?", "Advertencia", JOptionPane.OK_CANCEL_OPTION);

        //if (opc == JOptionPane.OK_OPTION) {
        dlgUsuario.setVisible(false);
        dlgUsuario.dispose();
        jTabbedPane1.setSelectedIndex(0);

        //revisar usuario
        pnlUsuarioCont.setVisible(false);
        pnlUsuarioMod.setVisible(false);
        pnlUsrVerif1.setVisible(true);

        //crear usuario
        pnlUsrVerif2.setVisible(true);
        pnlUsuariosList.setVisible(false);

        //otros labels
        txtUsuarioCred.setText("");
        lblUsuarioHidenID.setText("");
//        } else {
//            System.out.println("A que bueno que te quieres quedar");
//        }
    }//GEN-LAST:event_dlgUsuarioWindowClosing

    private void btnCrearUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCrearUsuarioActionPerformed
        char[] pass1 = passN.getPassword();
        char[] pass2 = passConfN.getPassword();
        String nom = txtNomN.getText().trim();
        String aPat = txtAPatN.getText().trim();
        String aMat = txtAMatN.getText().trim();
        String credencial = txtCredencialN.getText().trim();

        //revisa si ambas pass no estan en blanco
        if (!String.valueOf(pass1).trim().isEmpty() && !String.valueOf(pass2).trim().isEmpty()) {
            //son las mismas?
            if (Arrays.equals(pass1, pass2)) {
                //estan en blanco los demas campos
                if (!nom.isEmpty() && !aPat.isEmpty() && !aMat.isEmpty()) {
                    try {
                        UsuarioReadDB leer = new UsuarioReadDB();
                        UsuarioCreateDB crear = new UsuarioCreateDB();
                        String hashed = BCrypt.hashpw(String.valueOf(pass1).trim(), BCrypt.gensalt(12)); //cifrado
                        String datos[] = {nom, aPat, aMat, hashed};
                        if (!txtAutorizaNuevo.getText().trim().isEmpty()) {
                            //autorizacion
                            //
                            if (leer.getUsuarioNvl(txtAutorizaNuevo.getText().trim())) {

                                //revisar si el checkbox fue seleccionado
                                if (jCheckBox2.isSelected()) {

                                    //revisar si el cuadro de acceso no esta en blanco
                                    if (!credencial.isEmpty()) {
                                        //revisar si la credencial no existe en la bd

                                        if (!(leer.getExisteUsuario(credencial))) {
                                            System.out.println("\nVerficiando id...\nID valido :)\n");
                                            crear.setUsuario(datos, false, credencial);
                                            GenerarReportes g = new GenerarReportes();
                                            g.credencialUsuario(credencial);
                                            getTabla();
                                        } else {
                                            txtCredencialN.setText("");
                                            JOptionPane.showMessageDialog(null, "Esta credencial ya se encuentra registrada", "Error", JOptionPane.ERROR_MESSAGE);
                                        }
                                    } else {
                                        txtCredencialN.setText("");
                                        JOptionPane.showMessageDialog(null, "No puede dejar la credencial de acceso en blanco", "Error", JOptionPane.ERROR_MESSAGE);
                                    }

                                } else {

                                    String id = crear.setUsuario(datos, false);
                                    if (!id.isEmpty()) {
                                        System.out.println("se va a generar credencial");
                                        GenerarReportes g = new GenerarReportes();
                                        g.credencialUsuario(id);
                                        getTabla();
                                    }
                                }
                                //
                                //autorizacion
                            } else {
                                JOptionPane.showMessageDialog(null, "Un usuario administrador \ndebe permitir el registro \nde nuevos usuarios", "Error", JOptionPane.ERROR_MESSAGE);
                            }

                        } else {
                            JOptionPane.showMessageDialog(null, "Un usuario administrador \ndebe permitir el registro \nde nuevos usuarios", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (SQLException e) {
                        System.out.println("error al generar nuevo usurio crearUsuarioBTN: " + e);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Debe llenar completamente el formulario", "Advertencia", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Ambas contraseñas deben coincidir", "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "No puede dejar las contraseñas en blanco", "Error", JOptionPane.ERROR_MESSAGE);
        }
        cleanNuevoUs();
    }//GEN-LAST:event_btnCrearUsuarioActionPerformed

    public void cleanNuevoUs() {
        passN.setText("");
        passConfN.setText("");
        txtNomN.setText("");
        txtAPatN.setText("");
        txtAMatN.setText("");
        txtAutorizaNuevo.setText("");
        txtCredencialN.setText("");
        txtCredencialN.setEnabled(false);
        jCheckBox2.setSelected(false);
    }
    private void jCheckBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox2ActionPerformed
        if (jCheckBox2.isSelected()) {
            txtCredencialN.setEnabled(true);
        } else {
            txtCredencialN.setEnabled(false);
        }
    }//GEN-LAST:event_jCheckBox2ActionPerformed

    private void txtNomNKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNomNKeyTyped
        limiteYcaracteres(txtNomN, 25, evt);
    }//GEN-LAST:event_txtNomNKeyTyped

    private void txtAPatNKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAPatNKeyTyped
        limiteYcaracteres(txtAPatN, 15, evt);
    }//GEN-LAST:event_txtAPatNKeyTyped

    private void txtAMatNKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAMatNKeyTyped
        limiteYcaracteres(txtAMatN, 15, evt);
    }//GEN-LAST:event_txtAMatNKeyTyped

    private void passNKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_passNKeyTyped
        if (passN.getPassword().length >= 12) {
            evt.consume();
        }
    }//GEN-LAST:event_passNKeyTyped

    private void passConfNKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_passConfNKeyTyped
        if (passConfN.getPassword().length >= 12) {
            evt.consume();
        }
    }//GEN-LAST:event_passConfNKeyTyped

    private void btnCheckUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCheckUserActionPerformed
        try {
            String credencial = txtCheckUser.getText().trim();
            UsuarioReadDB leer = new UsuarioReadDB();
            if (leer.getUsuarioNvl(credencial)) {
                pnlUsrVerif2.setVisible(false);
                pnlUsuariosList.setVisible(true);
                getTabla();
            } else {
                JOptionPane.showMessageDialog(null, "Su usuario no tiene acceso a esta caracteristica\n\nNo es administrador", "Información", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }
        txtCheckUser.setText("");
    }//GEN-LAST:event_btnCheckUserActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        pnlUsrVerif1.setVisible(true);
        pnlUsuarioCont.setVisible(false);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnActUpdMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnActUpdMouseClicked
        btnActUpd.setBackground(new Color(239, 239, 239));
        String idOrig = hdnIDCopy.getText().trim();
        String idNew = txtIDNew.getText().trim();
        String nom = txtNomUpd.getText().trim();
        String apat = txtAPatUpd.getText().trim();
        String amat = txtAMatUpd.getText().trim();

        char[] pass = passUpd.getPassword();
        char[] passC = passConfUpd.getPassword();
        
        String usuario = JOptionPane.showInputDialog(null, "Introduzca la credencial de acceso de un usuario(que no sea su credencial)\npara validar esta acción", "Advertencia", JOptionPane.WARNING_MESSAGE);
        try {
            if(!usuario.trim().isEmpty()){
                UsuarioReadDB leer = new UsuarioReadDB();
                if(!idOrig.equals(usuario) && leer.getExisteUsuario(usuario.trim())){//si el usuario no es el mismo y existe 
                    if(updNomCompleto(idOrig, nom, apat, amat)){
                        updPass(idOrig, pass, passC);
                        updId(idOrig, idNew);
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "No se relizo ningun cambio revise la credencial del usuario que indico");
                }
            }
        } catch (Exception e) {
            System.out.println("Error al actualizar los datos: " + e);
        }
        updContenido(idOrig, idNew);
        
        txtNomUpd.setText("");
        txtAPatUpd.setText("");
        txtAMatUpd.setText("");
        passUpd.setText("");
        passConfUpd.setText("");
        txtIDNew.setText("");

        pnlUsuarioMod.setVisible(false);
        pnlUsuarioCont.setVisible(true);
    }//GEN-LAST:event_btnActUpdMouseClicked

    public void updPass(String idOrig, char[] pass, char[] passC){
        //modificar contraseña
        try{
            UsuarioCreateDB usr = new UsuarioCreateDB();
            if((pass.length > 0 && pass.length < 8) ){
                JOptionPane.showMessageDialog(null,"-LOS CAMBIOS NO FUERON REALIZADOS-\nSi desea modificar la contraseña debe ser: \n-al menos de 8 caracteres\n-puede contener numeros y letras\n-puede contener mayusculas y minusculas");
            }else if(pass.length >=8){
                if(Arrays.equals(pass, passC)){//actualizar contraseña
                        String hashed = BCrypt.hashpw(String.valueOf(pass).trim(), BCrypt.gensalt(12));  //cifrado
                        usr.updUsuario(idOrig, hashed);
                        System.out.println("Se actualizo la contraseña...");
                }else{
                        JOptionPane.showMessageDialog(null,"La contraseña y su confirmacion no son iguales\nes necesario las corriga");
                }
            }
        }catch(SQLException ex){ System.out.println("Error al realizar accion de actualizar contraseña updPass Menu: " + ex);
        }catch(Exception e){ System.out.println("Error al realizar accion de actualizar contraseña: " + e);}
    }
    
    public void updId(String idOrig, String idNew){
        //modificar credencial
        try{
            UsuarioCreateDB usr = new UsuarioCreateDB();
            if(chkBNewID.isSelected()){
                if(!idNew.isEmpty()){
                        String[] id = {idNew, idOrig};
                        usr.updUsuario(id);
                        System.out.println("Se actualizo la credencial del usuario: " + idOrig + " ahora como: " + idNew);
                }else{
                        JOptionPane.showMessageDialog(null,"No se modificara la credencial si la seccion indicada esta en blanco");
                }
            }
        }catch(SQLException ex){ System.out.println("Error al realizar accion de actualizar contraseña updPass Menu: " + ex);
        }catch(Exception e){ System.out.println("Error al realizar accion de actualizar contraseña: " + e);}
    }
    
    public boolean updNomCompleto(String idOrig, String nom, String apat, String amat){
        boolean estatus = true;
        //modificar nombre y apellidos
        try{
            UsuarioCreateDB usr = new UsuarioCreateDB();
            if(!nom.isEmpty() && !apat.isEmpty() && !amat.isEmpty()){
                    //JOptionPane.showMessageDialog(null,"Si desea hacer algún cambio debe por lo menos llenar los campos referentes al nombre");
                    String data[] = {nom, apat, amat};
                    usr.updUsuario(idOrig, data);
                    System.out.println("Se actualizo su nombre y apellidos usuario : " + idOrig);
            }else{
                    JOptionPane.showMessageDialog(null,"No se realizo ningún cambio\nrecuerde indicar su nombre y apellidos si desea cambiarlos", "Importante", JOptionPane.WARNING_MESSAGE);
                    estatus = false;
            }
        }catch(SQLException ex){ System.out.println("Error al realizar accion de actualizar contraseña updPass Menu: " + ex);
        }catch(Exception e){ System.out.println("Error al realizar accion de actualizar contraseña: " + e);}
        return estatus;
    }
    
    public void updContenido(String idOrig, String idNew){
        //actualiza el pnlUsuarioCont contenido
        try {
            String[] datos = new String[6];
            UsuarioReadDB leer = new UsuarioReadDB();
            if (chkBNewID.isSelected()) {
                if (!idNew.isEmpty()) {
                    datos = leer.getUsuario(idNew);
                }else{
                    datos = leer.getUsuario(idOrig);
                }
            } else {
                datos = leer.getUsuario(idOrig);
            }
            setDatosPnlContenido(datos);
        } catch (SQLException ex) {
            System.out.println("Error al cargar los datos dentro de pnlUsuarioCont: " + ex);
        }
    }
    
    private void btnActUpdMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnActUpdMouseEntered
        btnActUpd.setBackground(new Color(251, 189, 40));
    }//GEN-LAST:event_btnActUpdMouseEntered

    private void btnActUpdMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnActUpdMouseExited
        btnActUpd.setBackground(new Color(239, 239, 239));
    }//GEN-LAST:event_btnActUpdMouseExited

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        passConfUpd.setText("");
        passUpd.setText("");
        txtIDNew.setText("");
        chkBNewID.setSelected(false);
        pnlUsuarioMod.setVisible(false);
        pnlUsuarioCont.setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTabbedPane1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jTabbedPane1StateChanged
        int index = jTabbedPane1.getSelectedIndex();
        if (index == 1 || index == 0) {
            pnlUsrVerif2.setVisible(true);
            pnlUsuariosList.setVisible(false);
        }
        if (index == 1 || index == 2) {
            pnlUsrVerif1.setVisible(true);
            pnlUsuarioMod.setVisible(false);
            pnlUsuarioCont.setVisible(false);
        }
        if(index != 3){
            pnlMenuBK.setVisible(false);
            pnlUsrVerif3.setVisible(true);
        }
    }//GEN-LAST:event_jTabbedPane1StateChanged

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        int fila = jTable1.getSelectedRow();
        String[] datos = new String[6];
        for (int i = 0; i < 6; i++) {
            datos[i] = String.valueOf(jTable1.getValueAt(fila, i));
        }

        if (evt.getClickCount() == 2) {
            getTablaUsuarioReg(datos[0], false);
            hiddenLbl.setText(datos[0]);

            jScrollPane1.setVisible(false);
            jTable1.setVisible(false);

            jScrollPane2.setVisible(true);
            jTable2.setVisible(true);
            tglActInc.setVisible(true);
            jButton4.setVisible(true);
        }

        System.out.println("row " + fila + " seleccionada: " + Arrays.toString(datos));
    }//GEN-LAST:event_jTable1MouseClicked

    private void tglActIncItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_tglActIncItemStateChanged
        if (tglActInc.isSelected()) {
            getTablaUsuarioReg(hiddenLbl.getText(), true);
        } else {
            getTablaUsuarioReg(hiddenLbl.getText(), false);
        }
    }//GEN-LAST:event_tglActIncItemStateChanged

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        hiddenLbl.setText("");

        jScrollPane1.setVisible(true);
        jTable1.setVisible(true);

        jScrollPane2.setVisible(false);
        jTable2.setVisible(false);
        tglActInc.setVisible(false);
        tglActInc.setSelected(false);
        jButton4.setVisible(false);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void passUpdKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_passUpdKeyTyped
        if (passUpd.getPassword().length >= 12) {
            evt.consume();
        }
    }//GEN-LAST:event_passUpdKeyTyped

    private void passConfUpdKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_passConfUpdKeyTyped
        if (passConfUpd.getPassword().length >= 12) {
            evt.consume();
        }
    }//GEN-LAST:event_passConfUpdKeyTyped

    private void txtNomUpdKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNomUpdKeyTyped
        limiteYcaracteres(txtNomUpd, 25, evt);
    }//GEN-LAST:event_txtNomUpdKeyTyped

    private void txtAPatUpdKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAPatUpdKeyTyped
        limiteYcaracteres(txtAPatUpd, 15, evt);
    }//GEN-LAST:event_txtAPatUpdKeyTyped

    private void txtAMatUpdKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAMatUpdKeyTyped
        limiteYcaracteres(txtAMatUpd, 15, evt);
    }//GEN-LAST:event_txtAMatUpdKeyTyped

    private void txtCredencialNKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCredencialNKeyTyped
        if (txtCredencialN.getText().length() >= 15) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCredencialNKeyTyped

    private void txtIDNewKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIDNewKeyTyped
        if (txtIDNew.getText().length() >= 15) {
            evt.consume();
        }
    }//GEN-LAST:event_txtIDNewKeyTyped

    private void txtUsuarioCredKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUsuarioCredKeyTyped
        char c = evt.getKeyChar();
        if (c == KeyEvent.VK_ENTER) {
            btnGetUsuario.doClick();
        }
    }//GEN-LAST:event_txtUsuarioCredKeyTyped

    private void txtCheckUserKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCheckUserKeyTyped
        char c = evt.getKeyChar();
        if (c == KeyEvent.VK_ENTER) {
            btnCheckUser.doClick();
        }
    }//GEN-LAST:event_txtCheckUserKeyTyped

    private void lblLogo3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLogo3MouseClicked
        if(evt.getClickCount() == 3){
            contacto.setLocationRelativeTo(bkMenu);
            contacto.setVisible(true);
        }
    }//GEN-LAST:event_lblLogo3MouseClicked

    private void txtAutorizarDlgKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAutorizarDlgKeyTyped
        if (evt.getKeyChar() == KeyEvent.VK_ENTER) {
            btnAutorizar.doClick();
        }
    }//GEN-LAST:event_txtAutorizarDlgKeyTyped

    private void txtCredUsrKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCredUsrKeyTyped
        if (evt.getKeyChar() == KeyEvent.VK_ENTER) {
            btnValidarUsr.doClick();
        }
    }//GEN-LAST:event_txtCredUsrKeyTyped

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        
        if(cb1.isSelected() || cb2.isSelected() || cb3.isSelected() || cb4.isSelected() || cb5.isSelected() || cb6.isSelected()){
            JOptionPane.showMessageDialog(jTabbedPane1, "A continuacion indique en donde seran guardados los archivos");
            JFileChooser f = new JFileChooser();
            f.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); 
            int option = f.showSaveDialog(jTabbedPane1);
            //File path = f.getCurrentDirectory();
            if (option == JFileChooser.APPROVE_OPTION){
                String path = f.getSelectedFile().getAbsolutePath().replace('\\', '/');
                try{
                    UsuarioReadDB leer = new UsuarioReadDB();
                    if(cb1.isSelected()){ leer.bulkData(path, 1); }
                    if(cb2.isSelected()){ leer.bulkData(path, 2); }
                    if(cb3.isSelected()){ leer.bulkData(path, 3); }
                    if(cb4.isSelected()){ leer.bulkData(path, 4); }
                    if(cb5.isSelected()){ leer.bulkData(path, 5); }
                    if(cb6.isSelected()){ leer.bulkData(path, 6); }
                }catch(SQLException ex){ System.out.println("Error al generar csv: " + ex); }
            }else{ System.out.println("No se eligio una ruta"); }
        }else{ JOptionPane.showMessageDialog(jTabbedPane1, "Seleccione al menos una opción"); }
        cb1.setSelected(false);
        cb2.setSelected(false);
        cb3.setSelected(false);
        cb4.setSelected(false);
        cb5.setSelected(false);
        cb6.setSelected(false);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void btnValidarUsrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnValidarUsrActionPerformed
        try {
            String credencial = txtCredUsr.getText().trim();
            UsuarioReadDB leer = new UsuarioReadDB();
            if (leer.getUsuarioNvl(credencial)) {
                pnlUsrVerif3.setVisible(false);
                pnlMenuBK.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Su usuario no tiene acceso a esta caracteristica\n\nNo es administrador", "Información", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException ex) {
            System.out.println("Error al cargar datos de usuario: " + ex);
        }
        txtCredUsr.setText("");
    }//GEN-LAST:event_btnValidarUsrActionPerformed

    private void txtAutorizaNuevoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAutorizaNuevoKeyTyped
        if (evt.getKeyChar() == KeyEvent.VK_ENTER) {
            btnCrearUsuario.doClick();
        }
    }//GEN-LAST:event_txtAutorizaNuevoKeyTyped

    private void lblLogo2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLogo2MouseClicked
        if(evt.getClickCount() == 2){
            dlgConfirm.setLocationRelativeTo(bkMenu);
            dlgConfirm.setVisible(true);

            if(valido){
                valido = false;
                try{loadLog();}catch(Exception e){System.out.println("Error al cargar log:"+e);}
                dlgLog.setLocationRelativeTo(bkMenu);
                dlgLog.setVisible(true);
            }
        }
    }//GEN-LAST:event_lblLogo2MouseClicked

    public void loadLog() throws ParseException{
        System.out.println("Llenando log");
        try {
            LogDB log = new LogDB();
            DefaultTableModel modelLog = (DefaultTableModel) jTable3.getModel();
            String[] cols = {"ID", "ID DEL USUARIO", "ACCION", "TABLA", "FECHA"};
            String[][] datos = log.showLog();
            modelLog.setDataVector(datos, cols);
            jTable3.getColumnModel().getColumn(0).setPreferredWidth(30);
            jTable3.getColumnModel().getColumn(1).setPreferredWidth(112);
            jTable3.getColumnModel().getColumn(2).setPreferredWidth(64);
            jTable3.getColumnModel().getColumn(3).setPreferredWidth(195);
            jTable3.getColumnModel().getColumn(4).setPreferredWidth(140);

            jTable3.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        } catch (SQLException ex) {
            System.out.println("Error al llenar tabla con log: " + ex);
        }
    }
    private void btnComprobarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnComprobarActionPerformed
        String crdncial = txtUsuario.getText().trim();
        try {
            UsuarioReadDB leer = new UsuarioReadDB();
            if (leer.getExisteUsuario(crdncial)) {
                String hashed = leer.getPass(crdncial);
                if (BCrypt.checkpw(String.valueOf(txtPass.getPassword()), hashed)) {
                    if(leer.getEsAdminUsuario(crdncial)){
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
            }else{
                throw new Exception();
            }
        }catch(SQLException ex){
            System.out.println("Error al comprobar usuario:" + ex);
        }catch(Exception e){
            txtUsuario.setText("");
            txtPass.setText("");
            valido = false;
            JOptionPane.showMessageDialog(null,"Compruebe los datos ingresados, es posible que:\n-Su usuario no sea administrador\n-No ingreso sus datos correctamente");
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

    private void lblLoadProfMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLoadProfMouseClicked
        int respuesta = JOptionPane.showConfirmDialog(null, "A continuación aparecera una ventana se requiere eliga un archivo csv"
                                          + "\n-Los datos deben ser separados por coma(,)"
                                          + "\n-Cada dato debe estar encerrado en comillas dobles (\"Juan Jesús\")"
                                          + "\n-La informacion debe tener el siguiente orden"
                                          + "\nID_PROFESOR => 0001(MINIMO 12 CARACTERES)\nID_DEPARTAMENTO\n\tNOMBRE\n\tA_PATERNO\n\tA_MATERNO\n\tESTATUS_ESCOLAR => TRUE PARA HONORARIOS, FALSE PARA PLAZA"  
                                     , "Importante", JOptionPane.WARNING_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
        if(respuesta == JOptionPane.OK_OPTION){
            int status = fcProfCSV.showOpenDialog(null);
            if (status == fcProfCSV.APPROVE_OPTION) {
                File selectedFile = fcProfCSV.getSelectedFile();
                System.out.println(":::::::::::::::::::::::::::::::::...\nUbicación del archivo: " + selectedFile.getParent());
                System.out.println("Nombre del archivo: " + selectedFile.getName());
                try {
                    ProfesorDB prof = new ProfesorDB();
                    prof.bulkLoadProfe(selectedFile.getPath());
                    JOptionPane.showMessageDialog(null, "Los datos del archivo ubicado en: " + selectedFile.getPath() + "\nHAN SIDO GRABADOS EN LA BASE DE DATOS!!!");
                } catch (SQLException e) {System.out.println("Error al leer archivo que fue recibido");}
            }
        }
    }//GEN-LAST:event_lblLoadProfMouseClicked

    private void lblUsuariosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblUsuariosMouseClicked
        int respuesta = JOptionPane.showConfirmDialog(null, "A continuación aparecera una ventana se requiere eliga un archivo csv"
                                          + "\n-Los datos deben ser separados por coma(,)"
                                          + "\n-Cada dato debe estar encerrado en comillas dobles (\"Juan Jesús\")"
                                          + "\n-La informacion debe tener el siguiente orden"
                                          + "\nID_USUARIO, NOMBRE, A_PATERNO, A_MATERNO, PASSWORD, ADMINDR"
                                     , "Importante", JOptionPane.WARNING_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
        if(respuesta == JOptionPane.OK_OPTION){
            int status = fcProfCSV.showOpenDialog(null);
            if (status == fcProfCSV.APPROVE_OPTION) {
                File selectedFile = fcProfCSV.getSelectedFile();
                System.out.println(":::::::::::::::::::::::::::::::::...\nUbicación del archivo: " + selectedFile.getParent());
                System.out.println("Nombre del archivo: " + selectedFile.getName());
                try {
                    ProfesorDB prof = new ProfesorDB();
                    prof.bulkLoadUsuario(selectedFile.getPath());
                    JOptionPane.showMessageDialog(null, "Los datos del archivo ubicado en: " + selectedFile.getPath() + "\nHAN SIDO GRABADOS EN LA BASE DE DATOS!!!");
                } catch (SQLException e) {System.out.println("Error al leer archivo que fue recibido");}
            }
        }
    }//GEN-LAST:event_lblUsuariosMouseClicked

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        JOptionPane.showMessageDialog(jTabbedPane1, "A continuacion indique en donde seran guardado el backup");
            JFileChooser f = new JFileChooser();
            f.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); 
            int option = f.showSaveDialog(jTabbedPane1);
            //File path = f.getCurrentDirectory();
            if (option == JFileChooser.APPROVE_OPTION){
                String path = f.getSelectedFile().getAbsolutePath().replace('\\', '/');
                try{
                    UsuarioReadDB leer = new UsuarioReadDB();
                    leer.bulkDataDB(path);
                    JOptionPane.showMessageDialog(this, "Listo");
                }catch(SQLException ex){ System.out.println("Error al generar csv: " + ex); }
            }else{ System.out.println("No se eligio una ruta"); }
    }//GEN-LAST:event_jButton6ActionPerformed

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
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Menu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel bkMenu;
    private javax.swing.JPanel btnActUpd;
    private javax.swing.JLabel btnActUpd1;
    private javax.swing.JLabel btnActUpd2;
    private javax.swing.JPanel btnAul;
    private javax.swing.JButton btnAutorizar;
    private javax.swing.JButton btnCheckUser;
    private javax.swing.JButton btnComprobar;
    private javax.swing.JButton btnCrearUsuario;
    private javax.swing.JPanel btnDep;
    private javax.swing.JPanel btnExt1;
    private javax.swing.JPanel btnExt2;
    private javax.swing.JPanel btnExt3;
    private javax.swing.JPanel btnGenerar;
    private javax.swing.JButton btnGetUsuario;
    private javax.swing.JPanel btnProf;
    private javax.swing.JButton btnValidarUsr;
    private javax.swing.JPanel btnVid;
    private javax.swing.JCheckBox cb1;
    private javax.swing.JCheckBox cb2;
    private javax.swing.JCheckBox cb3;
    private javax.swing.JCheckBox cb4;
    private javax.swing.JCheckBox cb5;
    private javax.swing.JCheckBox cb6;
    private javax.swing.JCheckBox chkBNewID;
    private javax.swing.JDialog contacto;
    private javax.swing.JDialog dlgConfirm;
    private javax.swing.JDialog dlgLog;
    private javax.swing.JDialog dlgUsuario;
    private javax.swing.JFileChooser fcProfCSV;
    private javax.swing.JLabel hdnIDCopy;
    private javax.swing.JLabel hiddenLbl;
    private javax.swing.JLabel ico1;
    private javax.swing.JLabel ico2;
    private javax.swing.JLabel ico3;
    private javax.swing.JLabel ico4;
    private javax.swing.JLabel icoExt1;
    private javax.swing.JLabel icoExt2;
    private javax.swing.JLabel icoExt3;
    private javax.swing.JLabel icoExtlbl1;
    private javax.swing.JLabel icoExtlbl2;
    private javax.swing.JLabel icoExtlbl3;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JCheckBox jCheckBox2;
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
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
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
    private javax.swing.JLayeredPane jLayeredPane3;
    private javax.swing.JLayeredPane jLayeredPane4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JLabel labelFecha;
    private javax.swing.JLabel labelHora;
    private javax.swing.JLabel lbl1;
    private javax.swing.JLabel lbl2;
    private javax.swing.JLabel lbl3;
    private javax.swing.JLabel lbl4;
    private javax.swing.JLabel lblAcc;
    private javax.swing.JLabel lblAjustes;
    private javax.swing.JLabel lblBienv;
    private javax.swing.JLabel lblBtnGenerar1;
    private javax.swing.JLabel lblBtnGenerar2;
    private javax.swing.JLabel lblCabecera;
    private javax.swing.JLabel lblCre;
    private javax.swing.JLabel lblDep;
    private javax.swing.JLabel lblFecha;
    private javax.swing.JLabel lblHora;
    private javax.swing.JLabel lblIcoInfo;
    private javax.swing.JLabel lblIcoInfo1;
    private javax.swing.JLabel lblInfoCrear;
    private javax.swing.JLabel lblInfoCrear1;
    private javax.swing.JLabel lblLoadProf;
    private javax.swing.JLabel lblLogo1;
    private javax.swing.JLabel lblLogo2;
    private javax.swing.JLabel lblLogo3;
    private javax.swing.JLabel lblNom;
    private javax.swing.JLabel lblUsuarioHidenID;
    private javax.swing.JLabel lblUsuarios;
    private javax.swing.JPanel menuBar;
    private javax.swing.JPanel menuBarIzq;
    private javax.swing.JPasswordField passConfN;
    private javax.swing.JPasswordField passConfUpd;
    private javax.swing.JPasswordField passN;
    private javax.swing.JPasswordField passUpd;
    private javax.swing.JPanel pnlBackup;
    private javax.swing.JPanel pnlBarraSeparadora;
    private javax.swing.JPanel pnlBarraSeparadora1;
    private javax.swing.JPanel pnlBarraSeparadora2;
    private javax.swing.JPanel pnlControlUsuarios;
    private javax.swing.JPanel pnlInfoUsuario;
    private javax.swing.JPanel pnlMenuBK;
    private javax.swing.JPanel pnlMiUsuario;
    private javax.swing.JPanel pnlNUsuario;
    private javax.swing.JPanel pnlUsrVerif1;
    private javax.swing.JPanel pnlUsrVerif2;
    private javax.swing.JPanel pnlUsrVerif3;
    private javax.swing.JPanel pnlUsuarioCont;
    private javax.swing.JPanel pnlUsuarioMod;
    private javax.swing.JPanel pnlUsuariosList;
    private javax.swing.JPanel principalBar;
    private javax.swing.JToggleButton tglActInc;
    private javax.swing.JTextField txtAMatN;
    private javax.swing.JTextField txtAMatUpd;
    private javax.swing.JTextField txtAPatN;
    private javax.swing.JTextField txtAPatUpd;
    private javax.swing.JTextField txtAutorizaNuevo;
    private javax.swing.JTextField txtAutorizarDlg;
    private javax.swing.JTextField txtCheckUser;
    private javax.swing.JTextField txtCredUsr;
    private javax.swing.JTextField txtCredencialN;
    private javax.swing.JTextField txtIDNew;
    private javax.swing.JTextField txtNomN;
    private javax.swing.JTextField txtNomUpd;
    private javax.swing.JPasswordField txtPass;
    private javax.swing.JTextField txtUsuario;
    private javax.swing.JTextField txtUsuarioCred;
    // End of variables declaration//GEN-END:variables
}
