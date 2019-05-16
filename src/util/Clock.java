/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import interfaces.AlarmListener;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import static java.lang.Math.PI;
import javax.swing.JPanel;

import java.awt.geom.Ellipse2D;
import java.io.BufferedInputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JWindow;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import ui.ClockSettings;

/**
 *
 * @author JIBOYE, Oluwagbemiro Olaoluwa <gbenroscience@yahoo.com>
 */
public class Clock implements Runnable, Serializable {

    public static final int ALARM_DURATION_IN_MINUTES = 1;
    /**
     * If true, the settings page is open.
     */
    private transient AtomicBoolean settingsOpened = new AtomicBoolean(false);
    private final ArrayList<Alarm> alarms = new ArrayList();

    private transient JPanel panel;
    private transient BufferedImage clockImage;
    private transient Thread timer;
    /**
     * Object used to draw relative to the center of rectangles instead of
     * drawing relative to their top left corner.
     */
    private transient DrawAdapter adapter;

    /**
     * The size of the whole clock
     */
    private int diameter;
    /**
     * The size of the circle at the center of the clock to which all clock
     * hands are hinged.
     */
    private int centerSpotWidth;
    /**
     * The color of the center point.
     */
    private Color centerSpotColor;
    /**
     * The size of the outermost circle of the clock expressed as a fraction of
     * the clock size
     */
    private double outerCircleAsFractionOfFrameSize;
    /**
     * The size of the inner circle of the clock expressed as a fraction of the
     * clock size
     */
    private double innerCircleAsFractionOfFrameSize;
    /**
     * The general color of the clock's outer background
     */
    private Color outerColor;
    /**
     * The color between the 2 circles on the clock
     */
    private Color middleColor;
    /**
     * The color of the clock's inner background
     */
    private Color innerColor;
    /**
     * The location of the clock center.
     */
    private Point center;

    /**
     * The ticks
     */
    private Tick[] ticks = new Tick[60];
    /**
     * The seconds hand;
     */
    public ClockHand secondsHand;
    /**
     * The minute hand;
     */
    public ClockHand minuteHand;
    /**
     * The hour hand;
     */
    public ClockHand hourHand;

    private Point location;

    static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    private static final long REFRESH_RATE = 700L;

    // private Dimension size;
    /**
     *
     * @param diameter The size of the whole clock
     * @param centerSpotWidth The size of the circle at the center of the clock
     * to which all clock hands are hinged.
     * @param outerCircleAsFractionOfFrameSize expresses the width of the outer
     * circle as a fraction of the clock width
     * @param outerColor The general color of the clock's outer background
     * @param middleColor The color between the 2 circles on the clock
     * @param innerColor The color of the clock's inner background
     */
    public Clock(int diameter, int centerSpotWidth, double outerCircleAsFractionOfFrameSize,
            Color outerColor, Color middleColor, Color innerColor) {

        this.diameter = diameter;
        this.centerSpotWidth = centerSpotWidth;
        this.outerColor = outerColor;
        this.middleColor = middleColor;
        this.innerColor = innerColor;
        this.centerSpotColor = middleColor;
        this.outerCircleAsFractionOfFrameSize = (outerCircleAsFractionOfFrameSize <= 1) ? outerCircleAsFractionOfFrameSize : 0.9;
        this.innerCircleAsFractionOfFrameSize = 0.9 * this.outerCircleAsFractionOfFrameSize;

        this.location = new Point((screenSize.width - this.diameter) / 2, (screenSize.width - this.diameter) / 2);
        this.center = new Point(this.diameter / 2, this.diameter / 2);

        int i = 0;
        for (double angle = 0; i < 60; i++) {

            if (i % 5 == 0) {
                ticks[i] = new Tick(0.1, angle, Color.WHITE, 2, true);
            } else {
                ticks[i] = new Tick(0.04, angle, Color.WHITE, 2, false);
            }
            angle += (6 * PI / 180);
        }//end for

        secondsHand = new ClockHand(HandType.SECONDHAND, 0.82 * this.outerCircleAsFractionOfFrameSize, 0, Color.RED);
        minuteHand = new ClockHand(HandType.MINUTEHAND, 0.8 * this.outerCircleAsFractionOfFrameSize, 1, Color.WHITE);
        hourHand = new ClockHand(HandType.HOURHAND, 0.6 * this.outerCircleAsFractionOfFrameSize, 2, Color.WHITE);

        show();

    }

    private final void setup() {
        if (adapter == null) {
            adapter = new DrawAdapter();
        }
        this.panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Clock.this.draw();
                g.drawImage(Clock.this.clockImage, 0, 0, Clock.this.diameter, Clock.this.diameter, new Color(255, 255, 255, 0), this);
            }

        };
        settingsOpened = new AtomicBoolean(false);
        panel.setSize(diameter, diameter);
        panel.setBackground(new Color(0, 0, 0, 0));
        panel.setOpaque(false);

        applyLookAndFeel();
        panel.addMouseListener(new MouseAdapter() {
            private long clickStartDate;

            private long lastClickedDateBeforeThisSetOfClicks;

            private ArrayList<Integer> clicks = new ArrayList<>();

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);

                clickStartDate = new Date().getTime();

                new ComponentDragger(panel, e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e); //To change body of generated methods, choose Tools | Templates.
                /**
                 * Check that the last click does not belong to another set of
                 * clicks.
                 */
                if (new Date().getTime() - lastClickedDateBeforeThisSetOfClicks > 1300 && !clicks.isEmpty()) {
                    clicks.remove(0);
                    this.lastClickedDateBeforeThisSetOfClicks = clickStartDate;
                }

                if (new Date().getTime() - clickStartDate <= 500) {
                    clicks.add(1);
                    this.lastClickedDateBeforeThisSetOfClicks = clickStartDate;
                } else {
                    clickStartDate = 0;
                }

                if (clicks.size() == 2) {
                    JWindow window = (JWindow) panel.getTopLevelAncestor();
                    JFrame f = new JFrame();
                    f.setAlwaysOnTop(true);
                    int choice = JOptionPane.showConfirmDialog(f, "1. Choose `Yes` to open Settings\n2. Choose `No` to exit this APP\n"
                            + "3. Choose `Cancel` to continue using this app.", "OPTIONS", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

                    switch (choice) {

                        case JOptionPane.YES_OPTION:
                            openSettings();
                            break;
                        case JOptionPane.NO_OPTION:
                            window.dispose();
                            Runtime.getRuntime().exit(0);
                            break;
                        case JOptionPane.CANCEL_OPTION:

                            break;
                    }

                    clicks.clear();

                }

            }

        });
        timer = new Thread(this);
        timer.start();
    }

    public void setDiameter(int diameter) {
        clockImage = null;
        this.diameter = diameter;
        panel.setSize(diameter, diameter);
        panel.getTopLevelAncestor().setSize(diameter, diameter);

        this.location = new Point((screenSize.width - this.diameter) / 2, (screenSize.width - this.diameter) / 2);
        this.center = new Point(this.diameter / 2, this.diameter / 2);
        this.secondsHand.setHandLengthAsFractionOfClockWidth(0.82 * this.outerCircleAsFractionOfFrameSize);
        this.minuteHand.setHandLengthAsFractionOfClockWidth(0.8 * this.outerCircleAsFractionOfFrameSize);
        this.hourHand.setHandLengthAsFractionOfClockWidth(0.6 * this.outerCircleAsFractionOfFrameSize);
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public Point getLocation() {
        return location;
    }

    public DrawAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(DrawAdapter adapter) {
        this.adapter = adapter;
    }

    public Color getCenterSpotColor() {
        return centerSpotColor;
    }

    public void setCenterSpotColor(Color centerSpotColor) {
        this.centerSpotColor = centerSpotColor;
    }

    public int getCenterSpotWidth() {
        return centerSpotWidth;
    }

    public void setCenterSpotWidth(int centerSpotWidth) {
        this.centerSpotWidth = centerSpotWidth;
    }

    public int getDiameter() {
        return diameter;
    }

    public BufferedImage getClockImage() {
        return clockImage;
    }

    public void setInnerCircleAsFractionOfFrameSize(double innerCircleAsFractionOfFrameSize) {
        this.innerCircleAsFractionOfFrameSize = innerCircleAsFractionOfFrameSize;
    }

    public double getInnerCircleAsFractionOfFrameSize() {
        return innerCircleAsFractionOfFrameSize;
    }

    public Color getInnerColor() {
        return innerColor;
    }

    public void setInnerColor(Color innerColor) {
        this.innerColor = innerColor;
    }

    public Color getMiddleColor() {
        return middleColor;
    }

    public void setMiddleColor(Color middleColor) {
        this.middleColor = middleColor;
    }

    public double getOuterCircleAsFractionOfFrameSize() {
        return outerCircleAsFractionOfFrameSize;
    }

    public void setOuterCircleAsFractionOfFrameSize(double outerCircleAsFractionOfFrameSize) {
        this.outerCircleAsFractionOfFrameSize = outerCircleAsFractionOfFrameSize;
    }

    public int getTextFontSize() {
        return (int) ((35.0 / 950) * getDiameter());
    }

    public Color getOuterColor() {
        return outerColor;
    }

    public void setOuterColor(Color outerColor) {
        this.outerColor = outerColor;
    }

    public void setCenter(Point center) {
        this.center = center;
    }

    public Point getCenter() {
        return center;
    }

    public Tick[] getTicks() {
        return ticks;
    }

    public void setTicks(Tick[] ticks) {
        this.ticks = ticks;
    }

    public int getOuterCircleDimension() {
        return (int) (outerCircleAsFractionOfFrameSize * diameter);
    }

    public int getInnerCircleDimension() {
        return (int) (innerCircleAsFractionOfFrameSize * diameter);
    }

    public ArrayList<Alarm> getAlarms() {
        return alarms;
    }

    public void draw() {

        if (clockImage == null) {
            clockImage = new BufferedImage(this.diameter, this.diameter, BufferedImage.TYPE_INT_RGB);
        }

        Graphics2D g = (Graphics2D) clockImage.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g.setBackground(outerColor);
        g.clearRect(0, 0, diameter, diameter);

        int outer = getOuterCircleDimension() + 2;
        int inner = getInnerCircleDimension();
        g.setColor(middleColor);
        adapter.fillOval(g, center.x, center.y, outer, outer);
        g.setColor(innerColor);
        adapter.fillOval(g, center.x, center.y, inner, inner);
        g.setColor(centerSpotColor);
        adapter.fillOval(g, center.x, center.y, centerSpotWidth, centerSpotWidth);
        g.setColor(new Color(153, 153, 0));
        adapter.fillOval(g, center.x, center.y, centerSpotWidth / 2, centerSpotWidth / 2);

        for (int i = 0; i < ticks.length; i++) {
            ticks[i].draw(g, this);
        }
        secondsHand.fill(g, this);
        minuteHand.fill(g, this);
        hourHand.fill(g, this);

        secondsHand.getAngleForEachState();
        minuteHand.getAngleForEachState();
        hourHand.getAngleForEachState();

    }

    public void repaint(JPanel panel) {
        draw();
        panel.repaint();
    }

    public void show() {

        setup();
        final JWindow window = new JWindow();

        repaint(panel);
        window.setOpacity(1);

        window.addComponentListener(new ComponentAdapter() {
            // Give the window an elliptical shape.
            // If the window is resized, the shape is recalculated here.
            @Override
            public void componentResized(ComponentEvent e) {
                window.setShape(new Ellipse2D.Double(0, 0, window.getWidth(), window.getHeight()));
                window.add(panel);
                window.setLocation((screenSize.width - window.getSize().width) / 2, (screenSize.height - window.getSize().height) / 2);
                window.setVisible(true);
            }
        });

        window.setSize(panel.getSize());
        window.setAlwaysOnTop(false);

        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                Clock.this.save();
            }
        });

    }

    public void save() {
        new FileOps().write(Clock.this);
    }

    @Override
    public void run() {

        while (true) {
            try {
                timer.sleep(REFRESH_RATE);
                panel.repaint();
                fireAlarm();
            } catch (InterruptedException e) {
            }
        }

    }//end method run.

    private void fireAlarm() {

        Calendar now = Calendar.getInstance();

        int millis = ALARM_DURATION_IN_MINUTES * 60 * 1000;

        alarms.forEach((alarm) -> {

            GregorianCalendar alarmTime = new GregorianCalendar(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH),
                    alarm.getHh(), alarm.getMm(), alarm.getSec());

            long millisDiff = now.getTimeInMillis() - alarmTime.getTimeInMillis();

            if (millisDiff >= 0) {
                if (millisDiff <= millis) {
                    play("heal8.ogg");

                    if (Tick.DYNAMIC_BASE_TEXT.getState() != Tick.DynamicBaseText.SHOW_ALARM_NOTIF) {
                        Tick.DYNAMIC_BASE_TEXT.scan(alarm);
                    }

                } else {

                    if (Tick.DYNAMIC_BASE_TEXT.getState() == Tick.DynamicBaseText.SHOW_ALARM_NOTIF) {
                        Tick.DYNAMIC_BASE_TEXT.shutdownAlarmState();
                    }

                }
            }

        });

    }

    private void applyLookAndFeel() {
        try {
            UIManager.setLookAndFeel(new NimbusLookAndFeel());
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Clock.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void openSettings() {
        if (!settingsOpened.get()) {
            ClockSettings settings = new ClockSettings(this);
            settings.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            settings.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    super.windowClosing(e); //To change body of generated methods, choose Tools | Templates.

                    FileOps fileOps = new FileOps();
                    fileOps.write(Clock.this);
                    settingsOpened.set(false);
                }

            });
            settings.setVisible(true);
            settings.setSize(550, 600);
            settings.setResizable(false);
            settingsOpened.set(true);
        } else {
            JFrame f = new JFrame();
            f.setAlwaysOnTop(true);
            JOptionPane.showMessageDialog(f, "Oops, looks like the Settings page has been opened already.");
        }

    }

    public void play(String fileName) {

        try {

            AudioFilePlayer afp = new AudioFilePlayer();
            afp.playFromStream(new BufferedInputStream(getClass().getClassLoader().getResourceAsStream(fileName)));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public static void main(String[] args) {

        FileOps fileOps = new FileOps();

        Clock clock = fileOps.read();

        if (clock == null) {
            System.out.println("Couldn't load clock");

            final Color transparent = new Color(255, 255, 255, 0);

            clock = new Clock(600, 3, 1.0, transparent, Color.darkGray, Color.black);

            clock.save();

        }

    }

}
