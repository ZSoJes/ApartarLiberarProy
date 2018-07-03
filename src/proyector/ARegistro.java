package proyector;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingWorker;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import org.mindrot.jbcrypt.BCrypt;
import proyector.dataBase.crud.AulaDB;
import proyector.dataBase.crud.LogDB;
import proyector.dataBase.crud.UsuarioReadDB;
import proyector.dataBase.crud.PrestamoDB;
import proyector.dataBase.crud.ProfesorDB;
import proyector.dataBase.crud.VideoproyectorDB;
import proyector.other.GraphGen;
import proyector.reportes.GenerarReportes;

/**
 *
 * @author JuanGSot
 */
public final class ARegistro extends javax.swing.JFrame {

    ImageIcon img = new ImageIcon("./src/imagenes/logo-adm.png");
    javax.swing.Icon loadingIMG = new ImageIcon("./src/imagenes/spinner.gif");
    private static Boolean valido = false;
    private static String usuario = "";

    public ARegistro() {
        initComponents();
        lblLoadingIcon.setIcon(loadingIMG);
        try {
            new VideoproyectorDB().setProyectorServicio();
        } catch (SQLException e) {
            System.out.println("Error al cargar las horas de servicio de proyector:" + e);
        }
        jdtChooser2.setVisible(false);
        jdtChooser1.setVisible(false);
        lblIni.setVisible(false);
        lblFn.setVisible(false);
        panelOPC.setVisible(false);
        jScrollPane2.getVerticalScrollBar().setUnitIncrement(16);
        try {
            getTable(3, "", "");
        } catch (SQLException e) {
            System.out.println("Error al generar tabla constructor:" + e);
        }
        jComboBox1.setSelectedIndex(3);
        jTable1.setFont(new java.awt.Font("SansSerif", 0, 11));
        jTable1.getTableHeader().setFont(new java.awt.Font("SansSerif", 0, 10));
        labelFecha.setText(new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
        Timer timer = new Timer(500, (ActionEvent e) -> {
            reloj();                                                //coloca la hora
        });
        timer.setRepeats(true);
        timer.setCoalesce(true);
        timer.setInitialDelay(0);
        timer.start();

        tabbedContainer.setVisible(false);
        jScrollPane2.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, Color.BLACK));
    }

    /**
     * Se encarga de colocar la hora con un formato especifico
     */
    public void reloj() {
        labelHora.setText(new SimpleDateFormat("hh:mm:ss a").format(new Date()));
    }

    /**
     * Introduce la informacion al jtable de registros generales sobre prestamos
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
                    String[] arrUsu = new UsuarioReadDB().getUsuario(data[i][j]);
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
                    int horas = (int) (diferencia / 60);
                    int minutos = (int) (diferencia % 60);
                    data2[i][8] = (horas > 0 ? horas + "hrs " : "0hrs ") + (minutos > 0 ? minutos + "min " : "0min ");
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

        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
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

    public String[][] datosRegistros(int opc, String miDate1, String miDate2) throws SQLException {
        PrestamoDB reg = new PrestamoDB();
        String[][] data = new String[0][8];
        Calendar c = Calendar.getInstance();
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);
        switch (opc) {
            case 0:   //todos
                data = reg.getPrestamos(false, true);
                break;
            case 1: //hoy
                SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
                miDate1 = dt.format(c.getTime()).concat(" 00:00:00");
                miDate2 = dt.format(c.getTime()).concat(" 23:59:59");
                break;
            case 2:  //mes
                c.set(Calendar.DAY_OF_MONTH, 1);
                DateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd");
                miDate1 = df.format(c.getTime()) + " 00:00:00";
                miDate2 = miDate1.substring(0, 8) + c.getActualMaximum(Calendar.DAY_OF_MONTH) + " 23:59:59";
                break;
            case 3:  //semestre
                if (month < 6) {
                    c.set(Calendar.MONTH, 05);
                    miDate1 = year + "-01-01 00:00:00";
                    miDate2 = year + "-06-" + c.getActualMaximum(Calendar.DAY_OF_MONTH) + " 23:59:59";
                } else {
                    c.set(Calendar.MONTH, 11);
                    miDate1 = year + "-07-01 00:00:00";
                    miDate2 = year + "-12-" + c.getActualMaximum(Calendar.DAY_OF_MONTH) + " 23:59:59";
                }
                break;
            case 4:  //año
                c.set(Calendar.MONTH, 11);
                miDate1 = year + "-01-01 00:00:00";
                miDate2 = year + "-12-" + c.getActualMaximum(Calendar.DAY_OF_MONTH) + " 23:59:59";
                break;
        }
        if (opc >= 1 && opc <= 5) {
            data = reg.getPrestamos(miDate1, miDate2); //prestamos que estan entre fechas de dos dias especificos
        }
        System.out.println("\n    : : : : : Registros existentes:" + data.length + "\n    : : : Entre las fechas: " + miDate1 + " - " + miDate2);
        return data;
    }

    public void graficas() {
        new GraphGen().solicitudesDelAnio(jMiLabel);
    }

    public void graficaPastelProy() {
        new GraphGen().servicioPorProyector(jMiLabel1, jMiLabel2, jMiLabel3);
    }

    public void graficaBarrasSolicitudes() {
        new GraphGen().solicitudesPorProyector(jTabbedPane2);
    }

    public void graficaBarrasSolDepto() {
        new GraphGen().solicitudesYservicioPorDepartamento(jMiLabel5, jMiLabel6);
        new GraphGen().solicitudesYservicioPorDepartamentoPie(jMiLabel7, jMiLabel8);
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
        dRb6 = new javax.swing.JRadioButton();
        pnlBotones = new javax.swing.JPanel();
        btnGenerarRR = new javax.swing.JButton();
        btnCerrarRR = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        btnGroupFecha = new javax.swing.ButtonGroup();
        btnGroupDatos = new javax.swing.ButtonGroup();
        dlgConfirm = new javax.swing.JDialog();
        jPanel7 = new javax.swing.JPanel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        btnComprobar = new javax.swing.JButton();
        jLabel41 = new javax.swing.JLabel();
        txtUsuario = new javax.swing.JTextField();
        txtPass = new javax.swing.JPasswordField();
        dlgRprtArtPry = new javax.swing.JDialog();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        lblRprt1 = new javax.swing.JLabel();
        lblRprt2 = new javax.swing.JLabel();
        lblRprt3 = new javax.swing.JLabel();
        lblRprt5 = new javax.swing.JLabel();
        lblRprt4 = new javax.swing.JLabel();
        lblRprt6 = new javax.swing.JLabel();
        txtRprt1 = new javax.swing.JTextField();
        txtRprt2 = new javax.swing.JTextField();
        txtRprt3 = new javax.swing.JTextField();
        cbRprt1 = new javax.swing.JCheckBox();
        cbRprt3 = new javax.swing.JCheckBox();
        cbRprt2 = new javax.swing.JCheckBox();
        cbRprt4 = new javax.swing.JCheckBox();
        cbRprt5 = new javax.swing.JCheckBox();
        cbRprt6 = new javax.swing.JCheckBox();
        cbRprt7 = new javax.swing.JCheckBox();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jSeparator1 = new javax.swing.JSeparator();
        btnRpActualizar = new javax.swing.JButton();
        btnRpCancelar = new javax.swing.JButton();
        btnRpImprimir = new javax.swing.JButton();
        hiddenIDRprt = new javax.swing.JLabel();
        lblProfe = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        groupSolictudAlDepto = new javax.swing.ButtonGroup();
        groupImprevisto = new javax.swing.ButtonGroup();
        dlgLoading = new javax.swing.JDialog();
        lblLoadingIcon = new javax.swing.JLabel();
        fileCGraph = new javax.swing.JFileChooser();
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
        jScrollPane2 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jdtChooser1 = new com.toedter.calendar.JDateChooser();
        jdtChooser2 = new com.toedter.calendar.JDateChooser();
        jComboBox1 = new javax.swing.JComboBox<>();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        btnFiltrar = new javax.swing.JButton();
        lblIni = new javax.swing.JLabel();
        lblFn = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        tabbedContainer = new javax.swing.JTabbedPane();
        jMiLabel = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jMiLabel1 = new javax.swing.JLabel();
        jMiLabel2 = new javax.swing.JLabel();
        jMiLabel3 = new javax.swing.JLabel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        jTabbedPane4 = new javax.swing.JTabbedPane();
        jMiLabel6 = new javax.swing.JLabel();
        jMiLabel5 = new javax.swing.JLabel();
        jTabbedPane5 = new javax.swing.JTabbedPane();
        jMiLabel8 = new javax.swing.JLabel();
        jMiLabel7 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        btnShowGraph = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btnReportePry = new javax.swing.JButton();

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
        dRb4.setText("<html>Fallos y/o Perdidas<br>de articulos</html>");
        dRb4.setOpaque(false);

        btnGroupDatos.add(dRb5);
        dRb5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        dRb5.setText("Todos ");
        dRb5.setOpaque(false);

        btnGroupDatos.add(dRb6);
        dRb6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        dRb6.setText("<html>Fallos<br>de videoproyectores</html>");
        dRb6.setOpaque(false);

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
                            .addComponent(dRb3)
                            .addComponent(dRb4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(38, 38, 38)
                        .addGroup(pnlDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dRb6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dRb1)))
                    .addComponent(jLabel10))
                .addContainerGap(21, Short.MAX_VALUE))
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
                    .addGroup(pnlDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(dRb4)
                        .addComponent(dRb6))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

        dlgRprtArtPry.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        dlgRprtArtPry.setTitle("[Reimprimir/Modificar Solicitud de Mantenimiento VideoProyector]");
        dlgRprtArtPry.setMinimumSize(new java.awt.Dimension(715, 580));
        dlgRprtArtPry.setModal(true);
        dlgRprtArtPry.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                dlgRprtArtPryWindowClosing(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setMinimumSize(new java.awt.Dimension(700, 570));
        jPanel2.setPreferredSize(new java.awt.Dimension(700, 570));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTable3.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "VIDEOPROYECTOR", "RESUMEN DEL REPORTE", "IMPREVISTO", "FECHA DE CREACIÓN"
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
        jTable3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable3MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable3);

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 11, 680, 150));

        lblRprt1.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        lblRprt1.setText("Área Solicitante:");
        jPanel2.add(lblRprt1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 229, 98, -1));

        lblRprt2.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        lblRprt2.setText("Nombre del Solicitante:");
        jPanel2.add(lblRprt2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 167, -1, 14));

        lblRprt3.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        lblRprt3.setText("Descripción corta de la falla:");
        jPanel2.add(lblRprt3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 291, -1, -1));

        lblRprt5.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        lblRprt5.setText("Descripción detallada del servicio solicitado o falla a reparar:");
        lblRprt5.setToolTipText("");
        jPanel2.add(lblRprt5, new org.netbeans.lib.awtextra.AbsoluteConstraints(325, 248, 365, -1));

        lblRprt4.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        lblRprt4.setText("Departamento a quien se dirige la solicitud:");
        jPanel2.add(lblRprt4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 353, -1, -1));

        lblRprt6.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        lblRprt6.setText("Imprevisto:");
        jPanel2.add(lblRprt6, new org.netbeans.lib.awtextra.AbsoluteConstraints(325, 167, -1, -1));

        txtRprt1.setMinimumSize(new java.awt.Dimension(300, 30));
        txtRprt1.setPreferredSize(new java.awt.Dimension(300, 30));
        jPanel2.add(txtRprt1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 188, -1, -1));

        txtRprt2.setMinimumSize(new java.awt.Dimension(300, 30));
        txtRprt2.setPreferredSize(new java.awt.Dimension(300, 30));
        jPanel2.add(txtRprt2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 250, -1, -1));

        txtRprt3.setMinimumSize(new java.awt.Dimension(300, 30));
        txtRprt3.setPreferredSize(new java.awt.Dimension(300, 30));
        jPanel2.add(txtRprt3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 312, -1, -1));

        groupSolictudAlDepto.add(cbRprt1);
        cbRprt1.setText("Recursos Materiales y Servicios");
        cbRprt1.setOpaque(false);
        jPanel2.add(cbRprt1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 375, -1, -1));

        groupSolictudAlDepto.add(cbRprt3);
        cbRprt3.setText("Centro de Cómputo");
        cbRprt3.setOpaque(false);
        jPanel2.add(cbRprt3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 398, -1, -1));

        groupSolictudAlDepto.add(cbRprt2);
        cbRprt2.setText("Mantenimiento de Equipo");
        cbRprt2.setOpaque(false);
        jPanel2.add(cbRprt2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 421, -1, -1));

        groupImprevisto.add(cbRprt4);
        cbRprt4.setText("Reparación");
        cbRprt4.setOpaque(false);
        jPanel2.add(cbRprt4, new org.netbeans.lib.awtextra.AbsoluteConstraints(325, 192, -1, -1));

        groupImprevisto.add(cbRprt5);
        cbRprt5.setText("Mantenimiento");
        cbRprt5.setOpaque(false);
        jPanel2.add(cbRprt5, new org.netbeans.lib.awtextra.AbsoluteConstraints(326, 218, -1, -1));

        groupImprevisto.add(cbRprt6);
        cbRprt6.setText("Aplicar Garantía");
        cbRprt6.setOpaque(false);
        jPanel2.add(cbRprt6, new org.netbeans.lib.awtextra.AbsoluteConstraints(439, 192, -1, -1));

        groupImprevisto.add(cbRprt7);
        cbRprt7.setText("Dar de Baja");
        cbRprt7.setOpaque(false);
        jPanel2.add(cbRprt7, new org.netbeans.lib.awtextra.AbsoluteConstraints(439, 218, -1, -1));

        jTextArea1.setColumns(20);
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(5);
        jTextArea1.setWrapStyleWord(true);
        jTextArea1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextArea1KeyTyped(evt);
            }
        });
        jScrollPane4.setViewportView(jTextArea1);

        jPanel2.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(325, 274, 365, 138));

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator1.setMinimumSize(new java.awt.Dimension(3, 256));
        jSeparator1.setPreferredSize(new java.awt.Dimension(3, 256));
        jPanel2.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(316, 173, -1, -1));

        btnRpActualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Pencil_22px.png"))); // NOI18N
        btnRpActualizar.setText("Actualizar");
        btnRpActualizar.setEnabled(false);
        btnRpActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRpActualizarActionPerformed(evt);
            }
        });
        jPanel2.add(btnRpActualizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 460, -1, -1));

        btnRpCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Close_22px.png"))); // NOI18N
        btnRpCancelar.setText("Cancelar");
        btnRpCancelar.setEnabled(false);
        btnRpCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRpCancelarActionPerformed(evt);
            }
        });
        jPanel2.add(btnRpCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 460, -1, -1));

        btnRpImprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Print_36px.png"))); // NOI18N
        btnRpImprimir.setText("Reimprimir");
        btnRpImprimir.setEnabled(false);
        btnRpImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRpImprimirActionPerformed(evt);
            }
        });
        jPanel2.add(btnRpImprimir, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 450, -1, -1));

        hiddenIDRprt.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
        hiddenIDRprt.setText("SoyID");
        jPanel2.add(hiddenIDRprt, new org.netbeans.lib.awtextra.AbsoluteConstraints(618, 199, -1, -1));
        jPanel2.add(lblProfe, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 417, -1, -1));

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Info_36px.png"))); // NOI18N
        jLabel14.setText("<html>Doble clic izq para modificar y/o imprimir reporte<br>Clic izq en cancelar para indicar un reporte diferente</html>");
        jPanel2.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 500, -1, -1));

        javax.swing.GroupLayout dlgRprtArtPryLayout = new javax.swing.GroupLayout(dlgRprtArtPry.getContentPane());
        dlgRprtArtPry.getContentPane().setLayout(dlgRprtArtPryLayout);
        dlgRprtArtPryLayout.setHorizontalGroup(
            dlgRprtArtPryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dlgRprtArtPryLayout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        dlgRprtArtPryLayout.setVerticalGroup(
            dlgRprtArtPryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 580, Short.MAX_VALUE)
        );

        dlgLoading.setMinimumSize(new java.awt.Dimension(600, 300));
        dlgLoading.setModal(true);
        dlgLoading.setUndecorated(true);
        dlgLoading.setResizable(false);
        dlgLoading.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblLoadingIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblLoadingIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Add New_36px.png"))); // NOI18N
        dlgLoading.getContentPane().add(lblLoadingIcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 600, 300));

        fileCGraph.setApproveButtonText("Guardar");
        fileCGraph.setApproveButtonToolTipText("Guardar en el directorio mostrado");
        fileCGraph.setBackground(new java.awt.Color(204, 204, 204));
        fileCGraph.setDialogTitle("Guardar graficas en. . .");
        fileCGraph.setFileSelectionMode(javax.swing.JFileChooser.DIRECTORIES_ONLY);
        fileCGraph.setPreferredSize(new java.awt.Dimension(645, 320));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("[Registros]");
        setIconImage(img.getImage());
        setMinimumSize(new java.awt.Dimension(1024, 600));

        pnlBackground.setBackground(new java.awt.Color(255, 255, 255));
        pnlBackground.setMinimumSize(new java.awt.Dimension(1024, 600));
        pnlBackground.setPreferredSize(new java.awt.Dimension(1024, 600));

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

        btnMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Checklist_24px.png"))); // NOI18N
        btnMenu.setMaximumSize(new java.awt.Dimension(40, 37));
        btnMenu.setMinimumSize(new java.awt.Dimension(40, 37));
        btnMenu.setPreferredSize(new java.awt.Dimension(40, 37));
        btnMenu.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                btnMenuItemStateChanged(evt);
            }
        });

        btnRegresar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Back To_35px.png"))); // NOI18N
        btnRegresar.setMaximumSize(new java.awt.Dimension(40, 37));
        btnRegresar.setMinimumSize(new java.awt.Dimension(40, 37));
        btnRegresar.setPreferredSize(new java.awt.Dimension(40, 37));
        btnRegresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegresarActionPerformed(evt);
            }
        });

        panelOPC.setBackground(new java.awt.Color(250, 250, 250));
        panelOPC.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(171, 173, 179), 1, true));
        panelOPC.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        panelOPC.setMinimumSize(new java.awt.Dimension(376, 44));
        panelOPC.setPreferredSize(new java.awt.Dimension(376, 44));
        panelOPC.setLayout(new java.awt.GridBagLayout());

        ico3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/prestamo_32px.png"))); // NOI18N
        ico3.setToolTipText("Prestamo");
        ico3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ico3MouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 15, 0, 5);
        panelOPC.add(ico3, gridBagConstraints);

        ico4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/devolu_32px.png"))); // NOI18N
        ico4.setToolTipText("Devolución");
        ico4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ico4MouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        panelOPC.add(ico4, gridBagConstraints);

        ico5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/prof_42px.png"))); // NOI18N
        ico5.setToolTipText("Profesores");
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
        ico6.setToolTipText("Departamentos");
        ico6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ico6MouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        panelOPC.add(ico6, gridBagConstraints);

        ico7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/aul_42px.png"))); // NOI18N
        ico7.setToolTipText("Aulas");
        ico7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ico7MouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        panelOPC.add(ico7, gridBagConstraints);

        ico8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Video Projector_42px.png"))); // NOI18N
        ico8.setToolTipText("Videoproyectores");
        ico8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ico8MouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        panelOPC.add(ico8, gridBagConstraints);

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Electrical_42px.png"))); // NOI18N
        jLabel13.setToolTipText("Artículos");
        jLabel13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel13MouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 15);
        panelOPC.add(jLabel13, gridBagConstraints);

        lblTitulo.setFont(new java.awt.Font("Trebuchet MS", 3, 24)); // NOI18N
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo.setText("Registros");

        jScrollPane2.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane2.setBorder(null);
        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane2.setMinimumSize(new java.awt.Dimension(1024, 450));
        jScrollPane2.setPreferredSize(new java.awt.Dimension(1024, 450));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setMaximumSize(new java.awt.Dimension(1024, 989));
        jPanel1.setMinimumSize(new java.awt.Dimension(1024, 989));
        jPanel1.setPreferredSize(new java.awt.Dimension(1024, 989));

        jdtChooser1.setToolTipText("Fecha de inicio");
        jdtChooser1.setDateFormatString("dd/MM/yyyy");
        jdtChooser1.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N

        jdtChooser2.setToolTipText("Fecha de fin");
        jdtChooser2.setDateFormatString("dd/MM/yyyy");
        jdtChooser2.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N

        jComboBox1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Todos los registros", "Registros de Hoy", "Registros del Mes", "Registros del Semestre", "Registros del Año", "Personalizado" }));
        jComboBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox1ItemStateChanged(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "USUARIO ENTREGA", "USUARIO RECIBE", "PROYECTOR", "PROFESOR", "AULA", "FECHA SALIDA", "FECHA REGRESO", "TIEMPO"
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

        btnFiltrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Search_25px.png"))); // NOI18N
        btnFiltrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFiltrarActionPerformed(evt);
            }
        });

        lblIni.setText("Inicio:");

        lblFn.setText("Fin:");

        jLabel3.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel3.setText("Filtro:");

        jMiLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jMiLabel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        jMiLabel.setPreferredSize(new java.awt.Dimension(880, 300));
        tabbedContainer.addTab("*", jMiLabel);

        jTabbedPane1.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        jTabbedPane1.setMinimumSize(new java.awt.Dimension(870, 350));

        jMiLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jMiLabel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        jMiLabel1.setMaximumSize(new java.awt.Dimension(870, 350));
        jMiLabel1.setMinimumSize(new java.awt.Dimension(870, 350));
        jMiLabel1.setPreferredSize(new java.awt.Dimension(870, 350));
        jTabbedPane1.addTab("Total", jMiLabel1);

        jMiLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jMiLabel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        jMiLabel2.setMaximumSize(new java.awt.Dimension(870, 350));
        jMiLabel2.setMinimumSize(new java.awt.Dimension(870, 350));
        jMiLabel2.setPreferredSize(new java.awt.Dimension(870, 350));
        jTabbedPane1.addTab("Mes", jMiLabel2);

        jMiLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jMiLabel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        jMiLabel3.setMaximumSize(new java.awt.Dimension(870, 350));
        jMiLabel3.setMinimumSize(new java.awt.Dimension(870, 350));
        jMiLabel3.setPreferredSize(new java.awt.Dimension(870, 350));
        jTabbedPane1.addTab("Semestre", jMiLabel3);

        tabbedContainer.addTab("Horas de Servicio General", jTabbedPane1);
        jTabbedPane1.getAccessibleContext().setAccessibleName("");

        tabbedContainer.addTab("Solicitudes por Proyector", jTabbedPane2);

        jMiLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jMiLabel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        jTabbedPane4.addTab("Servicio por Departamento", jMiLabel6);

        jMiLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jMiLabel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        jTabbedPane4.addTab("Solicitudes por Departamento", jMiLabel5);

        jTabbedPane3.addTab("Barras", jTabbedPane4);

        jMiLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jMiLabel8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        jTabbedPane5.addTab("Servicio por Departamento", jMiLabel8);

        jMiLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jMiLabel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        jTabbedPane5.addTab("Solicitudes por Departamento", jMiLabel7);

        jTabbedPane3.addTab("Pastel", jTabbedPane5);

        tabbedContainer.addTab("por Departamento", jTabbedPane3);

        jLabel5.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Graficas de los Registros");

        btnShowGraph.setText("Generar Graficas");
        btnShowGraph.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnShowGraphActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel3)
                .addGap(8, 8, 8)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(btnFiltrar)
                .addGap(28, 28, 28)
                .addComponent(lblIni)
                .addGap(6, 6, 6)
                .addComponent(jdtChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(lblFn)
                .addGap(7, 7, 7)
                .addComponent(jdtChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(299, 299, 299))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(tabbedContainer, javax.swing.GroupLayout.PREFERRED_SIZE, 904, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(64, 64, 64))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(448, 448, 448)
                .addComponent(btnShowGraph, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(463, 463, 463))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane3))
                .addGap(14, 14, 14))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(jLabel3))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnFiltrar)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(lblIni))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(jdtChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(lblFn))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(jdtChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(12, 12, 12)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addGap(12, 12, 12)
                .addComponent(btnShowGraph)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tabbedContainer, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );

        tabbedContainer.getAccessibleContext().setAccessibleName("");

        jScrollPane2.setViewportView(jPanel1);

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/grafico-60.png"))); // NOI18N
        jLabel12.setToolTipText("Reportes");
        jLabel12.setMaximumSize(new java.awt.Dimension(70, 60));
        jLabel12.setMinimumSize(new java.awt.Dimension(70, 60));
        jLabel12.setPreferredSize(new java.awt.Dimension(70, 60));
        jLabel12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel12MouseClicked(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Reportes");
        jLabel4.setMaximumSize(new java.awt.Dimension(70, 19));
        jLabel4.setMinimumSize(new java.awt.Dimension(70, 19));
        jLabel4.setPreferredSize(new java.awt.Dimension(70, 19));

        btnReportePry.setText("<html>Modificar/Reimprimir<br>Solicitud de Mantenimiento</html>");
        btnReportePry.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        btnReportePry.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReportePryActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlBackgroundLayout = new javax.swing.GroupLayout(pnlBackground);
        pnlBackground.setLayout(pnlBackgroundLayout);
        pnlBackgroundLayout.setHorizontalGroup(
            pnlBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlCabecera, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnlBackgroundLayout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(pnlBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRegresar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addComponent(panelOPC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(lblTitulo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnReportePry, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addGroup(pnlBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlBackgroundLayout.setVerticalGroup(
            pnlBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBackgroundLayout.createSequentialGroup()
                .addComponent(pnlCabecera, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(pnlBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlBackgroundLayout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addGroup(pnlBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlBackgroundLayout.createSequentialGroup()
                                .addComponent(btnMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(3, 3, 3)
                                .addComponent(btnRegresar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlBackgroundLayout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addComponent(panelOPC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlBackgroundLayout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addGroup(pnlBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblTitulo)
                                    .addComponent(btnReportePry, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(pnlBackgroundLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(2, 2, 2)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 430, Short.MAX_VALUE))
        );

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
            System.out.println(": : : : Filtrar jable opcion: " + i + "\n");
            if (i != 5) {
                getTable(i, "", "");
            } else {
                if (jdtChooser2.getDate() != null && jdtChooser1.getDate() != null) {
                    String fch1 = new SimpleDateFormat("yyyy-MM-dd").format(jdtChooser1.getDate());
                    String fch2 = new SimpleDateFormat("yyyy-MM-dd").format(jdtChooser2.getDate());
                    if (jdtChooser2.getDate().after(jdtChooser1.getDate())) {  //comprobar que la fecha1 no es menor a la fecha2
                        System.out.println("fecha 1 es menor que fecha 2");
                        if (!fch1.equals(fch2)) {
                            System.out.println("datos : " + fch1 + " : " + fch2);
                            getTable(i, fch1, fch2);
                        } else {
                            JOptionPane.showMessageDialog(this, "Ambas fechas no pueden ser la misma", "Advertencia", JOptionPane.WARNING_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "La fecha fin no puede ser menos actual a la fecha inicio", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "No olvide indicar fecha de inicio y fin", "Advertencia", JOptionPane.WARNING_MESSAGE);
                }

            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al indicar un filtro de busqueda sobre registros:" + ex);
        }
    }//GEN-LAST:event_btnFiltrarActionPerformed

    private void jComboBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox1ItemStateChanged
        if (jComboBox1.getSelectedIndex() == 5) {
            jdtChooser2.setVisible(true);
            jdtChooser1.setVisible(true);
            lblIni.setVisible(true);
            lblFn.setVisible(true);
        } else {
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
        if (rb6.isSelected()) {
            pnlFechasFiltro.setVisible(true);
        } else {
            pnlFechasFiltro.setVisible(false);
        }
    }//GEN-LAST:event_rb6ItemStateChanged

    private void btnGenerarRRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerarRRActionPerformed
        String[] fch;
        int opc = 6;
        dlgReporte.setVisible(false);
        dlgReporte.dispose();

        if (rb6.isSelected()) {
            fch = rbFechaEsp();
        } else {
            if (rb1.isSelected()) {
                opc = 1;
            } else if (rb2.isSelected()) {
                opc = 2;
            } else if (rb3.isSelected()) {
                opc = 3;
            } else if (rb4.isSelected()) {
                opc = 4;
            } else if (rb5.isSelected()) {
                opc = 5;
            }
            fch = rbFechasOpc(opc);
        }
        if (fch.length > 0) {
            GenerarReportes gr = new GenerarReportes();
            if (dRb5.isSelected()) {
                gr.getRRTods(fch, 1, opc);
                gr.getRRTods(fch, 2, opc);
                gr.getRRTods(fch, 3, opc);
                gr.getRRTods(fch, 4, opc);
                gr.getRRTods(fch, 5, opc);
            } else if (dRb3.isSelected()) { //videoproyector
                gr.getRRTods(fch, 1, opc);
            } else if (dRb2.isSelected()) { //profesores
                gr.getRRTods(fch, 2, opc);
            } else if (dRb1.isSelected()) { //departamentos
                gr.getRRTods(fch, 3, opc);
            } else if (dRb4.isSelected()) { //articulos
                gr.getRRTods(fch, 4, opc);
            } else if (dRb6.isSelected()) { //proyectores
                gr.getRRTods(fch, 5, opc);
            }
            try {
                new LogDB().log(usuario, "E_PRESTAMOS", 5);
            } catch (SQLException e) {
                System.out.println("Error al generarLog:" + e);
            }
        }
    }//GEN-LAST:event_btnGenerarRRActionPerformed

    private void jLabel12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel12MouseClicked
        dlgConfirm.setLocationRelativeTo(pnlBackground);
        dlgConfirm.setVisible(true);

        if (valido) {
            valido = false;
            //inicializar
            pnlFechasFiltro.setVisible(false);
            rb1.setSelected(true);
            dRb5.setSelected(true);

            //mostrar dialog
            dlgReporte.setLocationRelativeTo(pnlBackground);
            dlgReporte.setVisible(true);
        }
    }//GEN-LAST:event_jLabel12MouseClicked

    private void btnComprobarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnComprobarActionPerformed
        String crdncial = txtUsuario.getText().trim();
        try {
            UsuarioReadDB leer = new UsuarioReadDB();
            if (leer.getExisteUsuario(crdncial)) {
                String hashed = leer.getPass(crdncial);
                if (BCrypt.checkpw(String.valueOf(txtPass.getPassword()), hashed)) {
                    System.out.println("Es valido: " + leer.getEsAdminUsuario(crdncial));
                    if (leer.getEsAdminUsuario(crdncial)) {
                        valido = true;
                        usuario = crdncial;
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
            usuario = "";
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

    private void jTable3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable3MouseClicked
        if (evt.getClickCount() == 2 && jTable3.getSelectedRow() != -1 && jTable3.isEnabled()) {
            int row = jTable3.getSelectedRow();
            String id = jTable3.getValueAt(row, 0).toString();
            hiddenIDRprt.setText(id);
            jTable3.setEnabled(false);
            btnRpActualizar.setEnabled(true);
            btnRpCancelar.setEnabled(true);
            btnRpImprimir.setEnabled(true);
            try {
                PrestamoDB press = new PrestamoDB();
                String[] arr = press.getReporte(Integer.parseInt(id));
                System.out.println("\n> Usted ha seleccionado el siguiente reporte \n\t\t" + Arrays.toString(arr)+"\n<\n<<");
                //profe
                String[] profe = new ProfesorDB().getProfesor(arr[2]);
                String miProfe;
                if(profe[0]!=null){
                    VideoproyectorDB a = new VideoproyectorDB();
                    miProfe = "<html>Ultimo docente en usar Proyector <b>"+ a.getProyector(a.getProyectorNoSerie(arr[1]))[1] +"</b>:<br>" + profe[2] + " " + profe[3] + " " + profe[4] + "</html>";
                }else{
                    miProfe = "";
                }
                lblProfe.setText(miProfe);
                //titulo
                txtRprt3.setText(arr[3]);
                //nombre_encargado
                txtRprt1.setText(arr[4]);
                //area
                txtRprt2.setText(arr[5]);
                //depto_reparador
                if (arr[6] == null) {
                    cbRprt1.setSelected(true);
                } else if (arr[6].equals("a")) {
                    cbRprt1.setSelected(true);
                } else if (arr[6].equals("b")) {
                    cbRprt3.setSelected(true);
                } else if (arr[6].equals("c")) {
                    cbRprt2.setSelected(true);
                }
                //imprevisto
                if (arr[7] == null) {
                    cbRprt4.setSelected(true); //reparacion
                } else if (arr[7].equals("a")) {
                    cbRprt4.setSelected(true); //reparacion
                } else if (arr[7].equals("b")) {
                    cbRprt5.setSelected(true);  //mantenimiento
                } else if (arr[7].equals("c")) {
                    cbRprt6.setSelected(true);  //aplicar garantia
                } else if (arr[7].equals("d")) {
                    cbRprt7.setSelected(true);  //dar de baja
                }
                //detalles
                jTextArea1.setText(arr[8]);
            } catch (SQLException e) {
            }

        }
    }//GEN-LAST:event_jTable3MouseClicked

    private void jTextArea1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextArea1KeyTyped
        if (jTextArea1.getText().length() >= 450) {
            evt.consume();
        }
    }//GEN-LAST:event_jTextArea1KeyTyped

    private void btnRpActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRpActualizarActionPerformed
        String titulo = txtRprt3.getText().trim();
        String nombreEncargado = txtRprt1.getText().trim();
        String area = txtRprt2.getText().trim();
        String deptoReparador = "";
        String imprevisto = "";
        String detalles = jTextArea1.getText().trim();

        if (cbRprt1.isSelected()) {
            deptoReparador = "a";
        } else if (cbRprt3.isSelected()) {
            deptoReparador = "b";
        } else if (cbRprt2.isSelected()) {
            deptoReparador = "c";
        }
        if (cbRprt4.isSelected()) {
            imprevisto = "a";
        } else if (cbRprt5.isSelected()) {
            imprevisto = "b";
        } else if (cbRprt6.isSelected()) {
            imprevisto = "c";
        } else if (cbRprt7.isSelected()) {
            imprevisto = "d";
        }
        String[] datos = {
            titulo,
            nombreEncargado,
            area,
            deptoReparador,
            imprevisto,
            detalles
        };
        if (!titulo.isEmpty() && !nombreEncargado.isEmpty() && !area.isEmpty() && !deptoReparador.isEmpty() && !imprevisto.isEmpty() && !detalles.isEmpty()) {
            try {
                PrestamoDB pres = new PrestamoDB();
                int id = Integer.parseInt(hiddenIDRprt.getText());
                System.out.println("Arr to " + id + " upd: " + Arrays.toString(datos));
                pres.updReporte(id, datos);
                new LogDB().log(usuario, "E_REP_VIDEOPROYECTORES", 2);
                fillJTable3();
                clearUpdReprt();
                JOptionPane.showMessageDialog(this, "Reporte actualizado!!!");
            } catch (SQLException e) {
                System.out.println("Error al generar actualizacion de datos del reporte: " + e);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Recuerde llenar completamente el formulario", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnRpActualizarActionPerformed

    private void btnRpCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRpCancelarActionPerformed
        clearUpdReprt();
    }//GEN-LAST:event_btnRpCancelarActionPerformed

    private void dlgRprtArtPryWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_dlgRprtArtPryWindowClosing
        clearUpdReprt();
    }//GEN-LAST:event_dlgRprtArtPryWindowClosing

    private void btnRpImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRpImprimirActionPerformed

        try {
            GenerarReportes genR = new GenerarReportes();
            PrestamoDB pres = new PrestamoDB();
            String[] arr = pres.getReporte(Integer.parseInt(hiddenIDRprt.getText()));
            String[] reporteDtos = {arr[5], arr[4], arr[8], arr[6], new VideoproyectorDB().getProyectorNoSerie(arr[1])};
            genR.getSolicitudMantenimientoPry(reporteDtos);
            new LogDB().log(usuario, "E_REP_VIDEOPROYECTORES", 5);
        } catch (SQLException e) {
            System.out.println("Error al generar Reporte: " + e);
        }
        clearUpdReprt();
        dlgRprtArtPry.setVisible(false);
        dlgRprtArtPry.dispose();

    }//GEN-LAST:event_btnRpImprimirActionPerformed

    private void jLabel13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel13MouseClicked
        Articulo art = new Articulo();
        art.setVisible(true);
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_jLabel13MouseClicked

    private void btnReportePryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReportePryActionPerformed
        dlgConfirm.setLocationRelativeTo(pnlBackground);
        dlgConfirm.setVisible(true);

        if (valido) {
            valido = false;
            fillJTable3();
            dlgRprtArtPry.setLocationRelativeTo(this);
            dlgRprtArtPry.setVisible(true);
        }
    }//GEN-LAST:event_btnReportePryActionPerformed

    private void btnShowGraphActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShowGraphActionPerformed
        dlgLoading.setLocationRelativeTo(this);

        System.out.println("\n\n>>> Dibujando y generando imagenes de Grafias. . . \n");
        SwingWorker<String, Void> worker = new SwingWorker<String, Void>() {
            @Override
            protected String doInBackground() throws InterruptedException {
                /**
                 * Execute some operation
                 */
                graficas();
                graficaPastelProy();
                graficaBarrasSolicitudes();
                graficaBarrasSolDepto();
                Thread.sleep(500);
                
                
                return null;
            }

            @Override
            protected void done() {
                btnShowGraph.setVisible(false);
                tabbedContainer.setVisible(true);
                dlgLoading.dispose();
                JOptionPane.showMessageDialog(pnlBackground, "Se mostraran las graficas en formato de imagen y en el programa");
                try{
                    String ruta = new java.io.File(".").getCanonicalPath() + File.separator + "src" + File.separator + "imagenes" + File.separator + "Graficas";
//                    int seleccion  = fileCGraph.showOpenDialog(pnlBackground);
//                    if(seleccion == fileCGraph.APPROVE_OPTION){
//                        java.io.File aGenerar = new java.io.File(fileCGraph.getCurrentDirectory());
                        java.awt.Desktop.getDesktop().open(new java.io.File(ruta));
                }catch (java.io.IOException e){
                    System.out.println("Error al abrir explorar de archivos: " + e);
                }
            }
        };
        worker.execute();
        dlgLoading.setVisible(true);
        try {
            worker.get();
        } catch (InterruptedException | ExecutionException e1) {
            e1.printStackTrace();
        }
    }//GEN-LAST:event_btnShowGraphActionPerformed

    public void fillJTable3() {
        System.out.println("Llenando con reportes de Videoproyectores");
        try {
            PrestamoDB pres = new PrestamoDB();
            DefaultTableModel modelPryRprt = (DefaultTableModel) jTable3.getModel();
            String[] cols = {jTable3.getColumnName(0), jTable3.getColumnName(1), jTable3.getColumnName(2), jTable3.getColumnName(3), jTable3.getColumnName(4)};
            String[][] datos = pres.getReportejTable();
            modelPryRprt.setDataVector(datos, cols);
        } catch (SQLException ex) {
            System.out.println("Error al llenar tabla con los reportes realizados: " + ex);
        }
    }

    public void clearUpdReprt() {
        jTable3.setEnabled(true);
        btnRpActualizar.setEnabled(false);
        btnRpCancelar.setEnabled(false);
        btnRpImprimir.setEnabled(false);

        txtRprt1.setText("");
        txtRprt2.setText("");
        txtRprt3.setText("");
        cbRprt1.setSelected(false);
        cbRprt3.setSelected(false);
        cbRprt2.setSelected(false);
        cbRprt4.setSelected(false);
        cbRprt5.setSelected(false);
        cbRprt6.setSelected(false);
        cbRprt7.setSelected(false);
        jTextArea1.setText("");
        lblProfe.setText("");
    }

    public String[] rbFechasOpc(int opc) {
        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        String[] fechas = new String[2];
        String date1 = "", date2 = "";
        int year = c.get(Calendar.YEAR);
        switch (opc) {
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

    public String[] rbFechaEsp() {
        DateFormat dateJC = new SimpleDateFormat("yyyy-MM-dd");//DateFormat.getDateInstance();
        String[] fechas = new String[2];
        if (jDateChooser2.getDate() != null && jDateChooser1.getDate() != null) {
            String dateJC1 = dateJC.format(jDateChooser1.getDate());
            String dateJC2 = dateJC.format(jDateChooser2.getDate());
            if (jDateChooser2.getDate().after(jDateChooser1.getDate())) {  //comprobar que la fecha1 no es menor a la fecha2
                System.out.println("fecha 1 es menor que fecha 2");
                if (!dateJC1.equals(dateJC2)) {
                    fechas[0] = dateJC1.concat("T00:00:00Z");
                    fechas[1] = dateJC2.concat("T23:59:59Z");
                    System.out.println("datos : " + Arrays.toString(fechas));
                } else {
                    JOptionPane.showMessageDialog(this, "Ambas fechas no pueden ser la misma", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    btnCerrarRR.doClick();
                }
            } else {
                JOptionPane.showMessageDialog(this, "La fecha fin no puede ser menos actual a la fecha inicio", "Advertencia", JOptionPane.WARNING_MESSAGE);
                btnCerrarRR.doClick();
            }
        } else {
            JOptionPane.showMessageDialog(this, "No olvide indicar fecha de inicio y fin", "Advertencia", JOptionPane.WARNING_MESSAGE);
            btnCerrarRR.doClick();
        }
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
    private javax.swing.JButton btnComprobar;
    private javax.swing.JButton btnFiltrar;
    private javax.swing.JButton btnGenerarRR;
    private javax.swing.ButtonGroup btnGroupDatos;
    private javax.swing.ButtonGroup btnGroupFecha;
    private javax.swing.JToggleButton btnMenu;
    private javax.swing.JButton btnRegresar;
    private javax.swing.JButton btnReportePry;
    private javax.swing.JButton btnRpActualizar;
    private javax.swing.JButton btnRpCancelar;
    private javax.swing.JButton btnRpImprimir;
    private javax.swing.JButton btnShowGraph;
    private javax.swing.JCheckBox cbRprt1;
    private javax.swing.JCheckBox cbRprt2;
    private javax.swing.JCheckBox cbRprt3;
    private javax.swing.JCheckBox cbRprt4;
    private javax.swing.JCheckBox cbRprt5;
    private javax.swing.JCheckBox cbRprt6;
    private javax.swing.JCheckBox cbRprt7;
    private javax.swing.JRadioButton dRb1;
    private javax.swing.JRadioButton dRb2;
    private javax.swing.JRadioButton dRb3;
    private javax.swing.JRadioButton dRb4;
    private javax.swing.JRadioButton dRb5;
    private javax.swing.JRadioButton dRb6;
    private javax.swing.JDialog dlgConfirm;
    private javax.swing.JDialog dlgLoading;
    private javax.swing.JDialog dlgReporte;
    private javax.swing.JDialog dlgRprtArtPry;
    private javax.swing.JFileChooser fileCGraph;
    private javax.swing.ButtonGroup groupImprevisto;
    private javax.swing.ButtonGroup groupSolictudAlDepto;
    private javax.swing.JLabel hiddenIDRprt;
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
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jMiLabel;
    private javax.swing.JLabel jMiLabel1;
    private javax.swing.JLabel jMiLabel2;
    private javax.swing.JLabel jMiLabel3;
    private javax.swing.JLabel jMiLabel5;
    private javax.swing.JLabel jMiLabel6;
    private javax.swing.JLabel jMiLabel7;
    private javax.swing.JLabel jMiLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JTabbedPane jTabbedPane4;
    private javax.swing.JTabbedPane jTabbedPane5;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTextArea jTextArea1;
    private com.toedter.calendar.JDateChooser jdtChooser1;
    private com.toedter.calendar.JDateChooser jdtChooser2;
    private javax.swing.JLabel labelFecha;
    private javax.swing.JLabel labelHora;
    private javax.swing.JLabel lblFn;
    private javax.swing.JLabel lblIco1;
    private javax.swing.JLabel lblIco2;
    private javax.swing.JLabel lblIni;
    private javax.swing.JLabel lblLoadingIcon;
    private javax.swing.JLabel lblProfe;
    private javax.swing.JLabel lblRprt1;
    private javax.swing.JLabel lblRprt2;
    private javax.swing.JLabel lblRprt3;
    private javax.swing.JLabel lblRprt4;
    private javax.swing.JLabel lblRprt5;
    private javax.swing.JLabel lblRprt6;
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
    private javax.swing.JTabbedPane tabbedContainer;
    private javax.swing.JPasswordField txtPass;
    private javax.swing.JTextField txtRprt1;
    private javax.swing.JTextField txtRprt2;
    private javax.swing.JTextField txtRprt3;
    private javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables
}
