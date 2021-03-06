/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.io.Serializable;
import java.util.Random;
import static java.lang.Math.*;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Gbemiro Jiboye
 */
public class Tick implements Serializable {

    static final SecureRandom choiceMaker;

    public static final DynamicBaseText DYNAMIC_BASE_TEXT;

    static {
        Random r = new Random(System.currentTimeMillis());
        byte b[] = new byte[8];
        r.nextBytes(b);
        choiceMaker = new SecureRandom(b);
        DYNAMIC_BASE_TEXT = new DynamicBaseText(DynamicBaseText.SHOW_MAIN);
    }

    /**
     * The fraction of the line that will be displayed
     */
    private double fractionOfLineShowing;
    /**
     * The angle this line will subtend at the center, measured with respect to
     * the 3.0.clock angle i.e 0 degs.
     */
    private double angle;
    /**
     * The color of the line
     */
    private Color color;
    /**
     * The thickness of the Tick.
     */
    private int thickness;
    /**
     * checks if the Tick object is a major one i.e 1,2,3,4,5,6,7,8,9,10,11,12
     * or not.
     */
    private boolean majorTick;

    /**
     * Actually, it is the center of the clock
     *
     * @param fractionOfLineShowing The fraction of the line that will be
     * displayed
     * @param angle The angle this line will subtend at the center, measured
     * with respect to the 3.0.clock angle i.e 0 degs.
     * @param color The color of the line
     * @param thickness The thickness of the Tick.
     * @param majorTick If true this tick is a major or a bold or longer one
     */
    public Tick(double fractionOfLineShowing, double angle, Color color, int thickness, boolean majorTick) {
        this(fractionOfLineShowing, angle, color, majorTick);
        this.thickness = thickness;
    }

    /**
     *
     * @param fractionOfLineShowing The fraction of the line that will be
     * displayed
     * @param angle The angle this line will subtend at the center, measured
     * with respect to the 3.0.clock angle i.e 0 degs.
     * @param color The color of the line
     * @param majorTick If true this tick is a major or a bold or longer one
     *
     */
    public Tick(double fractionOfLineShowing, double angle, Color color, boolean majorTick) {
        this.fractionOfLineShowing = fractionOfLineShowing;
        this.angle = angle;
        this.color = color;
        this.thickness = 1;
        this.majorTick = majorTick;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public double getFractionOfLineShowing() {
        return fractionOfLineShowing;
    }

    public void setFractionOfLineShowing(double fractionOfLineShowing) {
        this.fractionOfLineShowing = fractionOfLineShowing;
    }

    public int getThickness() {
        return thickness;
    }

    public void setThickness(int thickness) {
        this.thickness = thickness;
    }

    /**
     *
     * @param clock
     * @return the coordinates of the beginning of the tick
     */
    public Point getTickStartPoint(Clock clock) {

        double radius = clock.getInnerCircleDimension() / 2.0;
        Point stop = clock.getCenter();
        int xEnd = (int) (stop.x + radius * cos(angle));
        int yEnd = stop.y - (int) (radius * sin(angle));

        return new Point(xEnd, yEnd);
    }

    /**
     *
     * @param clock
     * @return the coordinates of the end of the tick
     */
    public Point getTickEndPoint(Clock clock) {
        Point start = getTickStartPoint(clock);
        double radius = clock.getInnerCircleDimension() / 2.0;
        //len is the distance between the starting point and the stop
        //point on the circumference. It is actually the length of the tick
        //modeled by the line.
        double len = fractionOfLineShowing * radius;

        return new Point((int) (start.x - len * cos(angle)), start.y + (int) (len * sin(angle)));
    }

    static transient Font topTextFont;
    static transient Font alarmTextFont;
    static transient Font bottomTextFont;
    static transient Font tickTextFont;
    static transient BasicStroke stroke;

    static transient int cacheFontSize = 0;

    /**
     *
     * @param g
     * @param clock
     */
    public void draw(Graphics g, Clock clock) {

        int fontSz = clock.getTextFontSize();
        
        int alarmFontSz = (int) (0.85*fontSz);

        if (tickTextFont == null) {
            tickTextFont = new Font("Gothic", Font.BOLD + Font.ITALIC, fontSz);
            cacheFontSize = tickTextFont.getSize();
        }
        if (topTextFont == null) {
            topTextFont = new Font("Times New Roman", Font.BOLD, fontSz);
        }
        if (bottomTextFont == null) {
            bottomTextFont = new Font("Papyrus", Font.PLAIN, fontSz);
        }
         if (alarmTextFont == null) {
            alarmTextFont = new Font("Times New Roman", Font.PLAIN, alarmFontSz );
        }

        if (cacheFontSize != fontSz) {
            tickTextFont = new Font("Gothic", Font.BOLD + Font.ITALIC, fontSz);
            topTextFont = new Font("Times New Roman", Font.BOLD, fontSz);
            bottomTextFont = new Font("Papyrus", Font.PLAIN, fontSz);
            alarmTextFont = new Font("Times New Roman", Font.PLAIN, alarmFontSz);
            cacheFontSize = fontSz;
        }

        g.setColor(color);
        Point begin = getTickStartPoint(clock);
        Point end = getTickEndPoint(clock);
        g.setFont(tickTextFont);

        Graphics2D gg = (Graphics2D) g;
        Stroke stroked = gg.getStroke();

        if (stroke == null) {
            stroke = new BasicStroke(thickness);
        }

        gg.setStroke(stroke);

        g.drawLine(begin.x, begin.y, end.x, end.y);
        gg.setStroke(stroked);

        String tickVal = getTickValue();
        if (tickVal.equals("12")) {
            g.drawString(tickVal, end.x - 15, end.y + 18);
        }
        switch (tickVal) {
            case "1":
            case "2":
                g.drawString(tickVal, end.x - 20, end.y + 12);
                break;
            case "3":
                g.drawString(tickVal, end.x - 20, end.y + 7);
                break;
            case "4":
            case "5":
                g.drawString(tickVal, end.x - 20, end.y);
                break;
            case "6":
                g.drawString(tickVal, end.x - 8, end.y - 4);
                break;
            case "7":
                g.drawString(tickVal, end.x, end.y - 3);
                break;
            case "8":
                g.drawString(tickVal, end.x + 5, end.y);
                break;
            case "9":
                g.drawString(tickVal, end.x + 10, end.y + 7);
                break;
            case "10":
                g.drawString(tickVal, end.x + 4, end.y + 4);
                break;
            case "11":
                g.drawString(tickVal, end.x + 5, end.y + 8);
                break;
            default:
                break;
        }

        if (tickVal.equals("12")) {
            g.setColor(Color.DARK_GRAY);
            Point pt = clock.getCenter();
            int dim = clock.getInnerCircleDimension();
            Font f = topTextFont;
            String str = "DIGITAL";
            FontMetrics fm = g.getFontMetrics(f);
            int strWid = fm.stringWidth(str);
            g.setFont(f);

            g.drawString(str, (clock.getDiameter() - strWid) / 2, end.y + dim / 6);
        }
        if (tickVal.equals("6")) {
            Point pt = clock.getCenter();
            int dim = clock.getInnerCircleDimension();
            Font f = bottomTextFont;
            String str = DYNAMIC_BASE_TEXT.control();
            FontMetrics fm = g.getFontMetrics(f);
            int strWid = fm.stringWidth(str);
            g.setFont(f);
            g.setColor(new Color(153, 153, choiceMaker.nextInt(256)));

            g.drawString(str, (clock.getDiameter() - strWid) / 2, pt.y + dim / 4);
        }

    }

    public double oneDegInRads() {
        return (PI / 180.0);
    }

    public double angDegToRads(double angdeg) {
        return (angdeg * PI / 180.0);
    }

    public double angRadToDegs(double angrad) {
        return (180 * angrad / PI);
    }

    public static boolean equals(double num1, double num2) {
        return abs(abs(num1) - abs(num2)) <= 1.0E-10;
    }

    /**
     *
     * @return the number associated with each major tick;
     */
    public String getTickValue() {
        if (angle == 0) {
            return "3";
        } else if (equals(angRadToDegs(angle), 30.0)) {
            return "2";
        } else if (equals(angRadToDegs(angle), 60.0)) {
            return "1";
        } else if (equals(angRadToDegs(angle), 90.0)) {
            return "12";
        } else if (equals(angRadToDegs(angle), 120.0)) {
            return "11";
        } else if (equals(angRadToDegs(angle), 150.0)) {
            return "10";
        } else if (equals(angRadToDegs(angle), 180.0)) {
            return "9";
        } else if (equals(angRadToDegs(angle), 210.0)) {
            return "8";
        } else if (equals(angRadToDegs(angle), 240.0)) {
            return "7";
        } else if (equals(angRadToDegs(angle), 270.0)) {
            return "6";
        } else if (equals(angRadToDegs(angle), 300.0)) {
            return "5";
        } else if (equals(angRadToDegs(angle), 330.0)) {
            return "4";
        } else {
            return "";
        }

    }

    public static final class DynamicBaseText {

        private static final String mainText = "ITIS Solutions";

        private String[] textGroup = new String[]{"Double Click", "For More Options"};

        private List<String> alarmTextGroup = new ArrayList<String>();

        public static final int SHOW_MAIN = 1;
        public static final int SHOW_FIRST = 2;
        public static final int SHOW_SECOND = 3;

        public static final int SHOW_ALARM_NOTIF = 4;

        private int state = SHOW_MAIN;

        private int counter;
 

        public DynamicBaseText(int state) {
            this.state = state;
        }

        public void setState(int state) {
            this.counter = 0;
            this.state = state;
        }

        public int getState() {
            return state;
        }

        public String control() {

            final int countDelayForMainText = 12 + choiceMaker.nextInt(5);
            final int countDelayForOtherText = 2;

            final int countDelayForAlarmText = 2;
 
            switch (state) {

                case SHOW_MAIN:

                    if (counter < countDelayForMainText) {
                        ++counter;

                        return mainText;
                    } else {
                        counter = 0;
                        state = SHOW_FIRST;
                        return textGroup[0];
                    }

                case SHOW_FIRST:
                    if (counter < countDelayForOtherText) {
                        ++counter;

                        return textGroup[0];
                    } else {
                        counter = 0;
                        state = SHOW_SECOND;
                        return textGroup[1];
                    }
                case SHOW_SECOND:
                    if (counter < countDelayForOtherText) {
                        ++counter;

                        return textGroup[1];
                    } else {
                        counter = 0;
                        state = SHOW_MAIN;
                        return mainText;
                    }

                case SHOW_ALARM_NOTIF:
                    System.out.println("1... SHOW_ALARM_NOTIF");
                    if (counter < alarmTextGroup.size()) {
                    System.out.println("2.");
                        return alarmTextGroup.get(counter++);
                    } else {
                        counter = 0;
                    System.out.println("OFF.");
                        return alarmTextGroup.get(counter++);
                    }

                default:

                    return mainText;
            }

        }

        public void scan(Alarm alarm) {
            setState(SHOW_ALARM_NOTIF);  
            String message = alarm.getDescription();
            this.alarmTextGroup = new ArrayList<>(Arrays.asList(message.split("\\s")));
            this.alarmTextGroup.add( alarm.getFriendlyTime() );
        }

        public void shutdownAlarmState() {
            setState(SHOW_MAIN);
            this.alarmTextGroup = new ArrayList<>();
        }

    }

}
