/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.io.Serializable;
import java.util.Calendar;
import static java.lang.Math.*;

/**
 *
 * @author Gbemiro Jiboye
 */
public class ClockHand implements Serializable {

    /**
     * The type of the hand:Hour, Minute or Second
     */
    private HandType handType;
    /**
     * Expresses the length of the clock hand as a fraction of the clock's inner
     * circle diameter.
     */
    private double handLengthAsFractionOfClockWidth;
    /**
     * The angle subtended by the hand at the center.
     */
    private double angle;
    /**
     * The color used to draw the hand.
     */
    private Color color;

    /**
     *
     * @param handType The type of the hand:Hour, Minute or Second
     * @param handLengthAsFractionOfClockWidth Expresses the length of the clock
     * hand as a fraction of the clock's inner circle diameter.
     * @param angle The angle subtended by the hand at the center.
     * @param color The color used to draw the hand.
     */
    public ClockHand(HandType handType, double handLengthAsFractionOfClockWidth, double angle, Color color) {
        this.handType = handType;
        setHandLengthAsFractionOfClockWidth(handLengthAsFractionOfClockWidth);
        this.angle = angle;
        this.color = color;

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

    public double getHandLengthAsFractionOfClockWidth() {
        return handLengthAsFractionOfClockWidth;
    }

    public final void setHandLengthAsFractionOfClockWidth(double handLengthAsFractionOfClockWidth) {
        this.handLengthAsFractionOfClockWidth = (handLengthAsFractionOfClockWidth <= 1) ? handLengthAsFractionOfClockWidth : 0.9;
    }

    public HandType getHandType() {
        return handType;
    }

    public void setHandType(HandType handType) {
        this.handType = handType;
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

    public double handLength(Clock clockFrame) {
        return handLengthAsFractionOfClockWidth * clockFrame.getInnerCircleDimension() / 2;
    }

    /**
     *
     * @param clockFrame The ClockFrame object that has this ClockHand object
     * @return The angle at the vertex of this ClockHand object (ClockHand
     * objects display as a filled isosceles triangle).
     */
    public double vertexAngle(Clock clockFrame) {
        double tanAng = (2 * handLength(clockFrame) / clockFrame.getCenterSpotWidth());
        return 2.0 * ((PI / 2.0) - atan(tanAng));
    }

    /**
     *
     * @param clockFrame The ClockFrame object that has this ClockHand object
     * @return Any of the base angles of this ClockHand object (ClockHand
     * objects display as a filled isosceles triangle).
     */
    public double baseAngle(Clock clockFrame) {
        double vertexAngle = vertexAngle(clockFrame);
        return (PI - vertexAngle) / 2.0;
    }

    /**
     *
     * @param clockFrame The ClockFrame object that has this ClockHand object
     * @return the length of the 2 equal sides of this ClockHand object.
     */
    public double equalSideLength(Clock clockFrame) {
        return handLength(clockFrame) / sin(baseAngle(clockFrame));
    }

    /**
     *
     * @param clockFrame The ClockFrame object that has this ClockHand object
     * @return the Point at the upper tip of this ClockHand object
     */
    public Point handTopTipCoords(Clock clockFrame) {
        double d = handLength(clockFrame);
        Point cen = clockFrame.getCenter();
        double cenX = cen.x;
        double cenY = cen.y;
        return new Point((int) (cenX + d * cos(angle)), (int) (cenY - d * sin(angle)));
    }

    /**
     *
     * @param clockFrame The ClockFrame object that has this ClockHand object
     * @return the Point at the tip of the left base of this ClockHand object
     */
    public Point leftTipCoords(Clock clockFrame) {
        double baseAngle = baseAngle(clockFrame);
        double ang = baseAngle - ((PI / 2.0) - angle);
        double sideLength = equalSideLength(clockFrame);
        int xDispFromHandTip = (int) (sideLength * cos(ang));
        int yDispFromHandTip = (int) (sideLength * sin(ang));

        Point handTopTipCoords = handTopTipCoords(clockFrame);

        return new Point(handTopTipCoords.x - (xDispFromHandTip), handTopTipCoords.y + yDispFromHandTip);
    }

    /**
     *
     * @param clockFrame The ClockFrame object that has this ClockHand object
     * @return the Point at the tip of the right base of this ClockHand object
     */
    public Point rightTipCoords(Clock clockFrame) {
        double baseAngle = baseAngle(clockFrame);
        double ang = (PI / 2.0) + angle - baseAngle;
        double sideLength = equalSideLength(clockFrame);
        int xDispFromHandTip = (int) (sideLength * cos(ang));
        int yDispFromHandTip = (int) (sideLength * sin(ang));

        Point handTopTipCoords = handTopTipCoords(clockFrame);

        return new Point(handTopTipCoords.x - (xDispFromHandTip), handTopTipCoords.y + yDispFromHandTip);
    }

    /**
     *
     * @param clockFrame The ClockFrame object that has this ClockHand object
     * @return the Point at the center of the base of this ClockHand object
     */
    public Point centralCoords(Clock clockFrame) {
        return clockFrame.getCenter();
    }

    public void draw(Graphics g, Clock clockFrame) {
        Point cen = centralCoords(clockFrame);
        Point topTip = handTopTipCoords(clockFrame);
        Point leftTip = leftTipCoords(clockFrame);
        Point rightTip = rightTipCoords(clockFrame);

        g.setColor(color);

        g.drawLine(topTip.x, topTip.y, leftTip.x, leftTip.y);
        g.drawLine(topTip.x, topTip.y, cen.x, cen.y);
        g.drawLine(topTip.x, topTip.y, rightTip.x, rightTip.y);
        g.drawLine(leftTip.x, leftTip.y, rightTip.x, rightTip.y);

    }

    public void fill(Graphics g, Clock clockFrame) {
        Point cen = centralCoords(clockFrame);
        Point topTip = handTopTipCoords(clockFrame);
        Point leftTip = leftTipCoords(clockFrame);
        Point rightTip = rightTipCoords(clockFrame);

        g.setColor(color);

        g.drawLine(topTip.x, topTip.y, leftTip.x, leftTip.y);
        g.drawLine(topTip.x, topTip.y, cen.x, cen.y);
        g.drawLine(topTip.x, topTip.y, rightTip.x, rightTip.y);
        g.drawLine(leftTip.x, leftTip.y, rightTip.x, rightTip.y);

        Polygon p = new Polygon();
        p.addPoint(leftTip.x, leftTip.y);
        p.addPoint(topTip.x, topTip.y);
        p.addPoint(rightTip.x, rightTip.y);
        p.addPoint(cen.x, cen.y);
        p.addPoint(leftTip.x, leftTip.y);
        g.fillPolygon(p);
    }

    private int tellTime() {

        switch (handType) {

            case HOURHAND:

                return getSystemHour();
            case MINUTEHAND:

                return getSystemMinutes();
            case SECONDHAND:

                return getSystemSeconds();

            default:

                break;

        }
        return -1;
    }

    /**
     *
     * @return the seconds portion of the system time
     */
    private int getSystemSeconds() {

        Calendar dateTime = Calendar.getInstance();
        
        int second = dateTime.get(Calendar.SECOND);
        
        

        return second < 59 ? second+1 : 0;
    }

    /**
     *
     * @return the minutes portion of the system time
     */
    private int getSystemMinutes() {

        Calendar dateTime = Calendar.getInstance();

        return dateTime.get(Calendar.MINUTE);
    }

    /**
     *
     * @return the hour portion of the system time
     */
    private int getSystemHour() {
        Calendar dateTime = Calendar.getInstance();

        return dateTime.get(Calendar.HOUR);
    }

    /**
     *
     * @return the equivalent angle in rads that the seconds hand must subtend
     * at the horizontal for a given seconds time.
     */
    public double getAngleFromSeconds() {
        return angle = (0.5 * PI) * (1 - (getSystemSeconds() / 15.0));
    }

    /**
     *
     * @return the equivalent angle in rads that the minutes hand must subtend
     * at the horizontal for a given minutes time.
     */
    public double getAngleFromMinutes() {
        return angle = (0.5 * PI) * (1 - (getSystemMinutes() / 15.0));
    }

    /**
     *
     * @return the equivalent angle in rads that the minutes hand must subtend
     * at the horizontal for a given minutes time.
     */
    public double getAngleFromHours() {
        return angle = ((0.5 * PI) * (1 - (getSystemHour() / 3.0))) - getSystemMinutes() * (PI / 360.0);
    }

    /**
     *
     * @return the angle relevant to each ClockHand Type
     */
    public double getAngleForEachState() {
        if (handType == HandType.SECONDHAND) {
            return getAngleFromSeconds();
        } else if (handType == HandType.MINUTEHAND) {
            return getAngleFromMinutes();
        } else {
            return getAngleFromHours();
        }
    }

}
