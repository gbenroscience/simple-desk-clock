/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

/**
 *
 * @author JIBOYE, Oluwagbemiro Olaoluwa <gbenroscience@yahoo.com>
 */
import java.awt.Point;
import java.util.*;
import interfaces.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import parsing.*;
import math.*;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import parsing.StringOperations;

/**
 * Used to display temporary information
 *
 * @author MRSJIBOYE
 */
public class Bubble implements Sprite {

    private transient BufferedImage image;

    private Point location;
    private int paddingX;
    private int paddingY;

    private int width;
    private int height;
    String notification = "";
    private boolean visible = true;

    /**
     * If true, this needs be garbage collected.
     */
    private boolean garbage;

    /**
     * Move within box boundary.
     */
    private boolean moveInBox;
    /**
     * If true, some critical property got changed, so re-setup the dimensions
     * of the object.
     */
    private boolean propertyChanged;
    /**
     * The horizontal speed.
     */
    private int horSpeed;
    /**
     * The vertical speed.
     */
    private int verSpeed;

    private transient BubbleMetrics metrics;

    private int direction = MOVE_RIGHT;

    static final int MOVE_LEFT = 0;
    static final int MOVE_RIGHT = 1;
    static final int MOVE_UP = 2;
    static final int MOVE_DOWN = 3;
    static final int MOVE_NONE = 4;

    static final int MOVE_UP_HOR_LEFT = 5;
    static final int MOVE_UP_HOR_RIGHT = 6;
    static final int MOVE_DOWN_HOR_LEFT = 7;
    static final int MOVE_DOWN_HOR_RIGHT = 8;
    static final int MOVE_AWAY = 9;

    /**
     * @param notification The notification to show.
     * @param location The location of the Bubble.
     * @param horSpeed The horizontal speed.
     * @param verSpeed The vertical speed.
     */
    public Bubble(String notification, Point location, int horSpeed, int verSpeed) {
        this.notification = notification;
        this.moveInBox = true;
        this.location = location;

        this.horSpeed = horSpeed;
        this.verSpeed = verSpeed;
        setup();

    }

    /**
     * Important method!!! Any change in clock dimensions must trigger this
     * method on any existing {@link Bubble}
     */
    private final void setup() {

        this.image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g = image.createGraphics();

        ArrayList<String> lines = StringOperations.getLinesByMaxCharsAlgorithm(notification, 30);

        g.setColor(Color.white);

        Font f = Tick.bottomTextFont;

        g.setFont(f);
        int maxWidth = 0;
        int indexOfMaxWidth = 0;
        int numOfLines = lines.size();

        FontMetrics fm = g.getFontMetrics(f);

        for (int i = 0; i < numOfLines; i++) {
            int newWidth = fm.stringWidth(lines.get(i));
            if (newWidth > maxWidth) {
                maxWidth = newWidth;
                indexOfMaxWidth = i;
            }
        }

        int w = maxWidth + 2 * fm.stringWidth("@@");
        int verticalWordSpacing = 5;
        int textHeight = fm.getHeight();
        int h = numOfLines * textHeight + (numOfLines + 1) * verticalWordSpacing;

        this.width = w + 30;
        this.height = h + 50;

        int dw = this.width - w;
        int dh = this.height - h;

        this.paddingX = dw;
        this.paddingY = dh;

        this.image = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_ARGB);

        metrics = new BubbleMetrics();

        metrics.lines = lines;
        metrics.textHeight = textHeight;
        metrics.verticalWordSpacing = verticalWordSpacing;

    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public String getNotification() {
        return notification;
    }

    @Override
    public void setLocation(int x, int y) {
        this.location = new Point(x, y);
    }

    @Override
    public Point getLocation() {
        return location;
    }

    @Override
    public void setWidth(int width) {
        scale(width, height);
    }

    @Override
    public int getWidth() {
        return this.width;
    }

    @Override
    public void setHeight(int height) {
        scale(width, height);
    }

    @Override
    public int getHeight() {
        return this.height;
    }

    public void setGarbage(boolean garbage) {
        this.garbage = garbage;
    }

    public boolean isGarbage() {
        return garbage;
    }

    @Override
    public double distanceTo(Sprite s) {
        return new FloatPoint(location).calcDistanceTo(new FloatPoint(s.getLocation()));
    }

    @Override
    public boolean contains(Point p) {
        EllipseModel modelEllipse = new EllipseModel(location.x + width / 2, location.y + height / 2, width / 2, height / 2);
        return modelEllipse.contains(new FloatPoint(p));
    }

    /**
     *
     * @return the number of words.
     */
    public int countWords() {
        CustomScanner cs = new CustomScanner(notification, false, " ");
        return cs.scan().size();
    }

    /**
     *
     * @return the notification divided into lines. A line should have a maximum
     * of 10 words or 30 characters.
     */
    public ArrayList<String> getLines() {
        ArrayList<String> lines = new ArrayList<String>();

        CustomScanner cs = new CustomScanner(notification, true, " ", "\n");
        java.util.List<String> list = cs.scan();
        int sz = list.size();

        for (int i = 0; i < sz; i++) {
            String line = "";

            while (line.length() <= 30 && i < sz) {
                if (list.get(i).equals("\n")) {
                    break;
                }
                line = line.concat(list.get(i));
                if (line.length() > 30) {

                } else {
                    ++i;
                }
            }
            lines.add(line);

        }//end for loop

        return lines;

    }//end method

    public void setPropertyChanged(boolean propertyChanged) {
        this.propertyChanged = propertyChanged;
    }

    public boolean isPropertyChanged() {
        return propertyChanged;
    }

    @Override
    public void draw(Clock c, Graphics2D gg) {

        if (visible && !notification.isEmpty()) {

            Graphics2D g = null;

            if (propertyChanged) {
                setup();
            }

            g = image.createGraphics();

            RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g.setRenderingHints(hints);

            int dw = this.width - this.paddingX;
            int dh = this.height - this.paddingY;

            g.fillOval(0, 0, this.width, this.height);

            int x = (int) (0.9 * dw);
            int y = (int) (0.33333333 * dh);

            FontMetrics fm = g.getFontMetrics(Tick.bottomTextFont);

            g.setColor(Color.black);
            for (int i = 0; i < metrics.lines.size(); i++) {
                int xLoc = (this.width - fm.stringWidth(metrics.lines.get(i))) / 2;
                g.drawString(metrics.lines.get(i), xLoc, y += (metrics.verticalWordSpacing + metrics.textHeight));
            }
            move(c);

            gg.drawImage(image, null, this.location.x, this.location.y);

        }//end if visible

    }

    static class BubbleMetrics {

        private List<String> lines = new ArrayList<>();
        private int textHeight;
        private int verticalWordSpacing;

    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isVisible() {
        return visible;
    }

    @Override
    public int getRight() {
        return location.x + width;
    }

    @Override
    public int getBottom() {
        return location.y + height;

    }

    public Rectangle getBoundingRect() {
        return new Rectangle(location.x, location.y, width, height);
    }

    public boolean intersects(Sprite s) {
        EllipseModel modelEllipse = new EllipseModel(location.x + width / 2, location.y + height / 2, width / 2, height / 2);
        return modelEllipse.intersectsWith(s.getBoundingRect());
    }

    public void chooseDirection() {
        int dir;
        while ((dir = Tick.choiceMaker.nextInt(MOVE_DOWN_HOR_RIGHT + 1)) == this.direction) {
        }
        this.direction = dir;
    }

    public void move(Clock c) {
        if (visible) {
 
            int xSpeed = horSpeed + Tick.choiceMaker.nextInt(horSpeed);
            
            int ySpeed = verSpeed + Tick.choiceMaker.nextInt(verSpeed);

            double dx = c.getLocation().x - location.x;
            double dy = c.getLocation().y - location.y;

            double dist = Math.sqrt(dx * dx + dy * dy);
            
            
            System.out.println("[horSpeed , verSpeed , direction] = [" + horSpeed+" , "+verSpeed+" , "+direction+ "]");

            if (moveInBox) {

                System.out.println("bubble loc-->: " + location.toString()); 

                switch (direction) {

                    case MOVE_AWAY:
                        
                        if (this.location.x > 0) {
                            location.x -= 2 * xSpeed;
                            if (c.getLocation().x > location.x && dist > 2 * this.width) {
                                this.garbage = true;
                            }
                        } else {
                            location.x = 0;
                            chooseDirection();
                        }

                        break;
                    case MOVE_LEFT:
                        if (this.location.x > 0) {
                            location.x -= xSpeed;
                        } else {
                            location.x = 0;
                            chooseDirection();
                        }

                        break;
                    case MOVE_RIGHT:
                        if (this.getRight() < c.getDiameter()) {
                            location.x += xSpeed;
                        } else {
                            location.x = c.getDiameter() - this.width;
                            chooseDirection();
                        }

                        break;
                    case MOVE_UP:
                        if (this.location.y > 0) {
                            location.y -= ySpeed;
                        } else {
                            location.y = 0;
                            chooseDirection();
                        }

                        break;
                    case MOVE_DOWN:

                        if (this.getBottom() < c.getDiameter()) {
                            location.y += ySpeed;
                        } else {
                            location.y = c.getDiameter() - this.height;
                            chooseDirection();
                        }

                        break;

                    case MOVE_DOWN_HOR_LEFT:

                        if (this.location.x > 0) {
                            location.x -= xSpeed;
                        } else {
                            location.x = 0;
                            direction = MOVE_DOWN_HOR_RIGHT;
                        }

                        if (this.getBottom() < c.getDiameter()) {
                            location.y += ySpeed;
                        } else {
                            location.y = c.getDiameter() - this.height;
                            direction = MOVE_UP_HOR_LEFT;
                        }

                        break;
                    case MOVE_DOWN_HOR_RIGHT:

                        if (this.getRight() < c.getDiameter()) {
                            location.x += xSpeed;
                        } else {
                            location.x = c.getDiameter() - this.width;
                            direction = MOVE_DOWN_HOR_LEFT;
                        }

                        if (this.getBottom() < c.getDiameter()) {
                            location.y += ySpeed;
                        } else {
                            location.y = c.getDiameter() - this.height;
                            direction = MOVE_UP_HOR_RIGHT;
                        }

                        break;
                    case MOVE_UP_HOR_RIGHT:

                        if (this.getRight() < c.getDiameter()) {
                            location.x += xSpeed;
                        } else {
                            location.x = c.getDiameter() - this.width;
                            direction = MOVE_UP_HOR_LEFT;
                        }

                        if (this.location.y > 0) {
                            location.y -= ySpeed;
                        } else {
                            location.y = 0;
                            direction = MOVE_DOWN_HOR_RIGHT;
                        }
                        break;
                    case MOVE_UP_HOR_LEFT:

                        if (this.location.x > 0) {
                            location.x -= xSpeed;
                        } else {
                            location.x = 0;
                            direction = MOVE_UP_HOR_RIGHT;
                        }

                        if (this.location.y > 0) {
                            location.y -= ySpeed;
                        } else {
                            location.y = 0;
                            direction = MOVE_DOWN_HOR_LEFT;
                        }
                        break;

                }

            }

        }

    }

    @Override
    public void scale(int w, int h) {
        width = w;
        height = h;
    }

}
