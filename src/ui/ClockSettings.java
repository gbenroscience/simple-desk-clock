/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import interfaces.AlarmListener;
import java.awt.Color;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import javax.swing.table.DefaultTableModel;
import util.Alarm;
import util.Clock;
import util.Tick;

/**
 *
 * @author JIBOYE, Oluwagbemiro Olaoluwa <gbenroscience@yahoo.com>
 */
public class ClockSettings extends javax.swing.JFrame {

    Clock clockFrame;
    /**
     * Used when the clockCircleWidthSpinner is jumping between the 2 circles.
     * When this attribute is true, the spinner retrieves the former value of
     * the width of the circle, and then sets this attribute to false.Then it
     * starts its value spinning action again.
     */
    private boolean toggleclockCircleWidthSpinnerValue;
    /**
     * Used when the clockHandLengthSpinner is jumping between the 3 hands. When
     * this attribute is true, the spinner retrieves the former value of the
     * length of the clock hand, and then sets this attribute to false.Then it
     * starts its value spinning action again.
     */
    private boolean toggleclockHandLengthSpinnerValue;
    /**
     * Retrieves a color from the JColorChooser attribute of this class.
     */
    private Color color;

    /**
     * Creates new form ClockSettings
     */
    public ClockSettings(Clock clock) {
        super("PROPERTIES");

        try {
            UIManager.setLookAndFeel(new NimbusLookAndFeel());
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(ClockSettings.class.getName()).log(Level.SEVERE, null, ex);
        }
        initComponents();

        this.clockFrame = clock;

        minuteSpinner.setModel(new SpinnerNumberModel(clock.getDiameter(), 0, Toolkit.getDefaultToolkit().getScreenSize().width, 1));
        clockCenterWidthSpinner.setModel(new SpinnerNumberModel(clock.getCenterSpotWidth(), 0, 200, 1));
        clockHandLengthSpinner.setModel(new SpinnerNumberModel(0.9, 0.0, 1.0, 0.01));

        clockCircleWidthSpinner.setModel(new SpinnerNumberModel(clock.getOuterCircleAsFractionOfFrameSize(), 0, 1.0, 0.01));

        clockWidthSpinner.setModel(new SpinnerNumberModel(clock.getDiameter(), 0, Toolkit.getDefaultToolkit().getScreenSize().width, 1));

        clockCircleCombo.removeAllItems();
        clockCircleCombo.addItem("Outer Circle");
        clockCircleCombo.addItem("Inner Circle");

        clockHandCombo.removeAllItems();
        clockHandCombo.addItem("Seconds Hand");
        clockHandCombo.addItem("Minutes Hand");
        clockHandCombo.addItem("Hour Hand");

        clockComponentCombo.removeAllItems();
        clockComponentCombo.addItem("Seconds Hand");
        clockComponentCombo.addItem("Minutes Hand");
        clockComponentCombo.addItem("Hour Hand");
        clockComponentCombo.addItem("External Background");
        clockComponentCombo.addItem("Double Circle Background");
        clockComponentCombo.addItem("Inner Circle Background");
        clockComponentCombo.addItem("Center Circle");
        clockComponentCombo.addItem("All Clock Ticks");
        clockComponentCombo.addItem("Random Color For Ticks");

        clockCircleCombo.setActionCommand(String.valueOf(clockCircleCombo.getSelectedItem()));

        GregorianCalendar c = new GregorianCalendar();
        c.setTimeInMillis(new Date().getTime());

        hourSpinner.setModel(new SpinnerNumberModel(c.get(Calendar.HOUR_OF_DAY), 1, 23, 1));
        minuteSpinner.setModel(new SpinnerNumberModel(c.get(Calendar.MINUTE),    0, 59, 1));
        secondSpinner.setModel(new SpinnerNumberModel(c.get(Calendar.SECOND),    0, 59, 1));

        createAlarm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String descr = alarmDescrField.getText();

                int hour = (Integer) hourSpinner.getValue();
                int minute = (Integer) minuteSpinner.getValue();
                int second = (Integer) secondSpinner.getValue();
                Alarm alarm = null;
                try {
                    alarm = new Alarm(hour, minute, second, descr);
                    clock.getAlarms().add(alarm);
                    TableModel model = (TableModel) alarmsTable.getModel();
                    model.fireTableDataChanged();

                } catch (InputMismatchException ex) {

                    JOptionPane.showMessageDialog(ClockSettings.this, Alarm.getErrorNotif(hour, minute, second, descr));
                }

            }
        });

        alarmsTable.setModel(new TableModel());
        alarmsTable.setBackground(Color.WHITE);
        alarmsTable.setSelectionBackground(Color.LIGHT_GRAY);
        alarmsTable.setForeground(Color.BLACK);
        alarmsTable.setSelectionForeground(Color.darkGray);
        alarmsTable.getTableHeader().setReorderingAllowed(false);

        alarmsTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e); //To change body of generated methods, choose Tools | Templates.
                Point p = e.getPoint();
                int row = alarmsTable.rowAtPoint(p);
                int col = alarmsTable.columnAtPoint(p);

                if (JOptionPane.showConfirmDialog(ClockSettings.this, "Do you really want to delete this alarm?",
                        "DELETE ALERT?", JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {
                    clockFrame.getAlarms().remove(row);
                    TableModel model = (TableModel) alarmsTable.getModel();
                    model.fireTableDataChanged();

                    JOptionPane.showMessageDialog(rootPane, "Alarm deleted");

                    clockFrame.save();
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Alarm not deleted.");
                }

            }

        });

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        panel = new javax.swing.JPanel();
        clockCircleCombo = new javax.swing.JComboBox();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        clockCircleWidthSpinner = new javax.swing.JSpinner();
        clockCenterWidthSpinner = new javax.swing.JSpinner();
        jLabel10 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        clockHandLengthSpinner = new javax.swing.JSpinner();
        jLabel8 = new javax.swing.JLabel();
        clockHandCombo = new javax.swing.JComboBox();
        clockComponentCombo = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        colorButton = new javax.swing.JButton();
        clockWidthSpinner = new javax.swing.JSpinner();
        jPanel1 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        alarmDescrField = new util.HintTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        secondSpinner = new javax.swing.JSpinner();
        minuteSpinner = new javax.swing.JSpinner();
        hourSpinner = new javax.swing.JSpinner();
        createAlarm = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        alarmsTable = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTabbedPane1.setName("jTabbedPane1"); // NOI18N

        panel.setBackground(new java.awt.Color(51, 51, 51));
        panel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        panel.setName("panel"); // NOI18N
        panel.setPreferredSize(new java.awt.Dimension(528, 600));

        clockCircleCombo.setFont(new java.awt.Font("Tahoma 12", 1, 12)); // NOI18N
        clockCircleCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        clockCircleCombo.setName("clockCircleCombo"); // NOI18N
        clockCircleCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clockCircleComboActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Arial 12 12 12 12 12 12", 1, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(153, 153, 153));
        jLabel14.setText("SELECT CLOCK CIRCLE");
        jLabel14.setName("jLabel14"); // NOI18N

        jLabel15.setFont(new java.awt.Font("Arial 12 12 12 12 12 12 12", 1, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(153, 153, 153));
        jLabel15.setText("SET CIRCLE WIDTH AS FRACTION OF CLOCK WIDTH");
        jLabel15.setName("jLabel15"); // NOI18N

        clockCircleWidthSpinner.setName("clockCircleWidthSpinner"); // NOI18N
        clockCircleWidthSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                clockCircleWidthSpinnerStateChanged(evt);
            }
        });

        clockCenterWidthSpinner.setName("clockCenterWidthSpinner"); // NOI18N
        clockCenterWidthSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                clockCenterWidthSpinnerStateChanged(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Arial 12 12 12 12 12 12 12", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(153, 153, 153));
        jLabel10.setText("SET CLOCK CENTER WIDTH.");
        jLabel10.setName("jLabel10"); // NOI18N

        jLabel7.setFont(new java.awt.Font("Arial 12 12 12 12", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(153, 153, 153));
        jLabel7.setText("SET CLOCK WIDTH");
        jLabel7.setName("jLabel7"); // NOI18N

        jLabel9.setFont(new java.awt.Font("Arial 12 12 12 12 12 12", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(153, 153, 153));
        jLabel9.setText("SET HAND LENGTH AS FRACTION OF INNER CLOCK CIRCLE WIDTH");
        jLabel9.setName("jLabel9"); // NOI18N

        clockHandLengthSpinner.setName("clockHandLengthSpinner"); // NOI18N
        clockHandLengthSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                clockHandLengthSpinnerStateChanged(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Arial 12 12 12 12 12", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(153, 153, 153));
        jLabel8.setText("SELECT CLOCK HAND");
        jLabel8.setName("jLabel8"); // NOI18N

        clockHandCombo.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        clockHandCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        clockHandCombo.setName("clockHandCombo"); // NOI18N
        clockHandCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clockHandComboActionPerformed(evt);
            }
        });

        clockComponentCombo.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        clockComponentCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        clockComponentCombo.setName("clockComponentCombo"); // NOI18N
        clockComponentCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clockComponentComboActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Arial 12 12 12", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(153, 153, 153));
        jLabel6.setText("SELECT CLOCK COMPONENT AND CHANGE ITS COLOR");
        jLabel6.setName("jLabel6"); // NOI18N

        colorButton.setBackground(new java.awt.Color(51, 51, 51));
        colorButton.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        colorButton.setForeground(new java.awt.Color(255, 255, 255));
        colorButton.setText("COLOR");
        colorButton.setName("colorButton"); // NOI18N
        colorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                colorButtonActionPerformed(evt);
            }
        });

        clockWidthSpinner.setName("clockWidthSpinner"); // NOI18N
        clockWidthSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                clockWidthSpinnerStateChanged(evt);
            }
        });

        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLayout.createSequentialGroup()
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelLayout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addComponent(clockCircleCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9)
                            .addComponent(jLabel7)
                            .addComponent(jLabel15)
                            .addGroup(panelLayout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addComponent(clockHandCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(clockHandLengthSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(clockCircleWidthSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelLayout.createSequentialGroup()
                                .addGap(16, 16, 16)
                                .addComponent(clockCenterWidthSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelLayout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addComponent(clockWidthSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel14)
                            .addGroup(panelLayout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(clockComponentCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(colorButton))
                            .addComponent(jLabel6))))
                .addContainerGap(106, Short.MAX_VALUE))
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(clockComponentCombo)
                    .addGroup(panelLayout.createSequentialGroup()
                        .addComponent(colorButton)
                        .addGap(3, 3, 3)))
                .addGap(16, 16, 16)
                .addComponent(jLabel14)
                .addGap(18, 18, 18)
                .addComponent(clockCircleCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel15)
                .addGap(18, 18, 18)
                .addComponent(clockCircleWidthSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel7)
                .addGap(18, 18, 18)
                .addComponent(clockWidthSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(clockCenterWidthSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(clockHandCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(clockHandLengthSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(56, 56, 56))
        );

        jTabbedPane1.addTab("BASIC SETTINGS", panel);

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));
        jPanel1.setName("jPanel1"); // NOI18N

        jLabel11.setFont(new java.awt.Font("Arial 12 12 12 12 12 12", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(153, 153, 153));
        jLabel11.setText("SET ALARM");
        jLabel11.setName("jLabel11"); // NOI18N

        alarmDescrField.setName("alarmDescrField"); // NOI18N

        jLabel12.setFont(new java.awt.Font("Arial 12 12 12 12 12 12", 1, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(153, 153, 153));
        jLabel12.setText("HOUR");
        jLabel12.setName("jLabel12"); // NOI18N

        jLabel13.setFont(new java.awt.Font("Arial 12 12 12 12 12 12", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(153, 153, 153));
        jLabel13.setText("MINUTE");
        jLabel13.setName("jLabel13"); // NOI18N

        jLabel16.setFont(new java.awt.Font("Arial 12 12 12 12 12 12", 1, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(153, 153, 153));
        jLabel16.setText("SECOND");
        jLabel16.setName("jLabel16"); // NOI18N

        secondSpinner.setName("secondSpinner"); // NOI18N
        secondSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                secondSpinnerStateChanged(evt);
            }
        });

        minuteSpinner.setName("minuteSpinner"); // NOI18N
        minuteSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                minuteSpinnerStateChanged(evt);
            }
        });

        hourSpinner.setName("hourSpinner"); // NOI18N
        hourSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                hourSpinnerStateChanged(evt);
            }
        });

        createAlarm.setText("CREATE DAILY ALARM");
        createAlarm.setName("createAlarm"); // NOI18N

        jLabel17.setFont(new java.awt.Font("Arial 12 12 12 12 12 12", 1, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(153, 153, 153));
        jLabel17.setText("ALL ALARMS");
        jLabel17.setName("jLabel17"); // NOI18N

        jScrollPane3.setName("jScrollPane3"); // NOI18N

        alarmsTable.setBackground(new Color(1,1,1,255));
        alarmsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Alarm Name", "Time"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        alarmsTable.setName("alarmTable"); // NOI18N
        alarmsTable.setRowHeight(24);
        jScrollPane3.setViewportView(alarmsTable);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(createAlarm, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(alarmDescrField, javax.swing.GroupLayout.PREFERRED_SIZE, 424, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addGap(32, 32, 32)
                                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 414, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel17)
                                    .addComponent(jLabel11)))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(170, 170, 170)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addGap(40, 40, 40)
                                .addComponent(jLabel13)
                                .addGap(27, 27, 27)
                                .addComponent(jLabel16))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(hourSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(minuteSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(secondSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(64, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46)
                .addComponent(jLabel11)
                .addGap(28, 28, 28)
                .addComponent(alarmDescrField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel12)
                        .addComponent(jLabel13))
                    .addComponent(jLabel16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(hourSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(minuteSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(secondSpinner, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addComponent(createAlarm, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(100, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("ALARMS", jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public boolean isToggleclockCircleWidthSpinnerValue() {
        return toggleclockCircleWidthSpinnerValue;
    }

    public void setToggleclockCircleWidthSpinnerValue(boolean toggleclockCircleWidthSpinnerValue) {
        this.toggleclockCircleWidthSpinnerValue = toggleclockCircleWidthSpinnerValue;
    }

    public void setToggleclockHandLengthSpinnerValue(boolean toggleclockHandLengthSpinnerValue) {
        this.toggleclockHandLengthSpinnerValue = toggleclockHandLengthSpinnerValue;
    }

    public boolean isToggleclockHandLengthSpinnerValue() {
        return toggleclockHandLengthSpinnerValue;
    }

    private void clockCircleComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clockCircleComboActionPerformed
        clockCircleCombo.setActionCommand(String.valueOf(clockCircleCombo.getSelectedItem()));
        setToggleclockCircleWidthSpinnerValue(true);
    }//GEN-LAST:event_clockCircleComboActionPerformed

    private void clockCircleWidthSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_clockCircleWidthSpinnerStateChanged

        if (clockCircleCombo.getActionCommand().equalsIgnoreCase("Outer Circle")) {
            if (isToggleclockCircleWidthSpinnerValue()) {
                clockCircleWidthSpinner.setValue(clockFrame.getOuterCircleAsFractionOfFrameSize());
                setToggleclockCircleWidthSpinnerValue(false);
            }
            clockFrame.setOuterCircleAsFractionOfFrameSize(Double.valueOf(String.valueOf(clockCircleWidthSpinner.getValue())));
        } else if (clockCircleCombo.getActionCommand().equalsIgnoreCase("Inner Circle")) {
            if (isToggleclockCircleWidthSpinnerValue()) {
                clockCircleWidthSpinner.setValue(clockFrame.getInnerCircleAsFractionOfFrameSize());
                setToggleclockCircleWidthSpinnerValue(false);
            }
            clockFrame.setInnerCircleAsFractionOfFrameSize(Double.valueOf(String.valueOf(clockCircleWidthSpinner.getValue())));
        }

    }//GEN-LAST:event_clockCircleWidthSpinnerStateChanged

    private void clockCenterWidthSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_clockCenterWidthSpinnerStateChanged
        clockFrame.setCenterSpotWidth(Integer.valueOf(String.valueOf(clockCenterWidthSpinner.getValue())));
    }//GEN-LAST:event_clockCenterWidthSpinnerStateChanged

    private void minuteSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_minuteSpinnerStateChanged

    }//GEN-LAST:event_minuteSpinnerStateChanged

    private void clockHandLengthSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_clockHandLengthSpinnerStateChanged
        if (clockHandCombo.getActionCommand().equalsIgnoreCase("Seconds Hand")) {
            if (isToggleclockHandLengthSpinnerValue()) {
                clockHandLengthSpinner.setValue(clockFrame.secondsHand.getHandLengthAsFractionOfClockWidth());
                setToggleclockHandLengthSpinnerValue(false);
            }
            clockFrame.secondsHand.setHandLengthAsFractionOfClockWidth(Double.valueOf(String.valueOf(clockHandLengthSpinner.getValue())));
        } else if (clockHandCombo.getActionCommand().equalsIgnoreCase("Minutes Hand")) {
            if (isToggleclockHandLengthSpinnerValue()) {
                clockHandLengthSpinner.setValue(clockFrame.minuteHand.getHandLengthAsFractionOfClockWidth());
                setToggleclockHandLengthSpinnerValue(false);
            }
            clockFrame.minuteHand.setHandLengthAsFractionOfClockWidth(Double.valueOf(String.valueOf(clockHandLengthSpinner.getValue())));
        } else if (clockHandCombo.getActionCommand().equalsIgnoreCase("Hour Hand")) {
            if (isToggleclockHandLengthSpinnerValue()) {
                clockHandLengthSpinner.setValue(clockFrame.hourHand.getHandLengthAsFractionOfClockWidth());
                setToggleclockHandLengthSpinnerValue(false);
            }
            clockFrame.hourHand.setHandLengthAsFractionOfClockWidth(Double.valueOf(String.valueOf(clockHandLengthSpinner.getValue())));
        }
    }//GEN-LAST:event_clockHandLengthSpinnerStateChanged

    private void clockHandComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clockHandComboActionPerformed
        clockHandCombo.setActionCommand(String.valueOf(clockHandCombo.getSelectedItem()));
        setToggleclockHandLengthSpinnerValue(true);
    }//GEN-LAST:event_clockHandComboActionPerformed

    private void clockComponentComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clockComponentComboActionPerformed
        clockComponentCombo.setActionCommand(String.valueOf(clockComponentCombo.getSelectedItem()));
        if (clockComponentCombo.getActionCommand().equalsIgnoreCase("Random Color For Ticks")) {
            Tick[] ticks = clockFrame.getTicks();
            for (int i = 0; i < ticks.length; i++) {
                ticks[i].setColor(new Color(new Random().nextInt(256),
                        new Random().nextInt(256),
                        new Random().nextInt(256)));
            }
        }
    }//GEN-LAST:event_clockComponentComboActionPerformed

    private void colorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_colorButtonActionPerformed
        if (color == null) {
            color = Color.white;
        }

        JColorChooser chooser = new JColorChooser(color);
        chooser.setVisible(true);

        JDialog dialog = new JDialog(this, "SET CLOCK COMPONENT COLOR");
        dialog.add(chooser);
        dialog.setVisible(true);
        dialog.pack();

        chooser.getSelectionModel().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                color = chooser.getColor();
                if (clockComponentCombo.getActionCommand().equalsIgnoreCase("Seconds Hand")) {
                    clockFrame.secondsHand.setColor(color);
                } else if (clockComponentCombo.getActionCommand().equalsIgnoreCase("Minutes Hand")) {
                    clockFrame.minuteHand.setColor(color);
                } else if (clockComponentCombo.getActionCommand().equalsIgnoreCase("Hour Hand")) {
                    clockFrame.hourHand.setColor(color);
                } else if (clockComponentCombo.getActionCommand().equalsIgnoreCase("External Background")) {
                    clockFrame.setOuterColor(color);
                } else if (clockComponentCombo.getActionCommand().equalsIgnoreCase("Double Circle Background")) {
                    clockFrame.setMiddleColor(color);
                } else if (clockComponentCombo.getActionCommand().equalsIgnoreCase("Inner Circle Background")) {
                    clockFrame.setInnerColor(color);
                } else if (clockComponentCombo.getActionCommand().equalsIgnoreCase("Center Circle")) {
                    clockFrame.setCenterSpotColor(color);
                } else if (clockComponentCombo.getActionCommand().equalsIgnoreCase("All Clock Ticks")) {
                    Tick[] ticks = clockFrame.getTicks();
                    for (int i = 0; i < ticks.length; i++) {
                        ticks[i].setColor(color);
                    }
                }

            }
        });
        //   color = JColorChooser.showDialog(panel, "SET CLOCK COMPONENT COLOR", color);


    }//GEN-LAST:event_colorButtonActionPerformed

    private void clockWidthSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_clockWidthSpinnerStateChanged
        clockFrame.setDiameter(Integer.valueOf(String.valueOf(clockWidthSpinner.getValue())));
    }//GEN-LAST:event_clockWidthSpinnerStateChanged

    private void hourSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_hourSpinnerStateChanged

    }//GEN-LAST:event_hourSpinnerStateChanged

    private void secondSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_secondSpinnerStateChanged

    }//GEN-LAST:event_secondSpinnerStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private util.HintTextField alarmDescrField;
    private javax.swing.JTable alarmsTable;
    private javax.swing.JSpinner clockCenterWidthSpinner;
    private javax.swing.JComboBox clockCircleCombo;
    private javax.swing.JSpinner clockCircleWidthSpinner;
    private javax.swing.JComboBox clockComponentCombo;
    private javax.swing.JComboBox clockHandCombo;
    private javax.swing.JSpinner clockHandLengthSpinner;
    private javax.swing.JSpinner clockWidthSpinner;
    private javax.swing.JButton colorButton;
    private javax.swing.JButton createAlarm;
    private javax.swing.JSpinner hourSpinner;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JSpinner minuteSpinner;
    private javax.swing.JPanel panel;
    private javax.swing.JSpinner secondSpinner;
    // End of variables declaration//GEN-END:variables

    private class TableModel extends DefaultTableModel {

        protected String[] columnNames = new String[]{"Alarm Name", "Time"};

        @Override
        public int getRowCount() {
            return clockFrame.getAlarms().size();
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }

        @Override
        public int getColumnCount() {
            return 2;
        }

        public String getColumnName(int i) {
            return this.columnNames[i];
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Alarm alarm = clockFrame.getAlarms().get(rowIndex);
            switch (columnIndex) {

                case 0:
                    return alarm.getDescription();
                case 1:
                    return alarm.getFriendlyTime();

                default:
                    return "";
            }
        }

    }

}
