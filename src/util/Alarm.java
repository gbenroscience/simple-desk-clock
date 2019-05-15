/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.Serializable;
import java.util.InputMismatchException;
import java.util.UUID;

/**
 *
 * @author JIBOYE, Oluwagbemiro Olaoluwa <gbenroscience@yahoo.com>
 */
public class Alarm implements Serializable {

    private String id;
    private String description;
    private int hh;
    private int mm;
    private int sec;

    public Alarm() {
    }

    public Alarm(int hh, int mm, int sec, String description) throws InputMismatchException {
        this.hh = hh >= 1 && hh <= 23 ? hh : -1;
        this.mm = mm >= 0 && mm <= 59 ? mm : -1;
        this.sec = sec >= 0 && sec <= 59 ? sec : -1;
        this.description = description != null && !description.isEmpty() ? description : "";
        this.id = UUID.randomUUID().toString();

        if (this.hh == 0) {
            throw new InputMismatchException("The hour must lie between 1 and 23");
        }
        if (this.mm == 0) {
            throw new InputMismatchException("The minute must lie between 1 and 59");
        }
        if (this.sec == 0) {
            throw new InputMismatchException("The hour must lie between 1 and 59");
        }
        if (this.description == null || this.description.isEmpty()) {
            throw new InputMismatchException("Please enter a valid description for the alarm.");
        }

    }

    public static String getErrorNotif(int hh,int mm, int sec, String description ) {

        if (hh == -1) {
            return "The hour must lie between 1 and 23";
        }
        if (mm == -1) {
            return "The minute must lie between 1 and 59";
        }
        if (sec == -1) {
            return "The hour must lie between 1 and 59";
        }
        if (description == null || description.isEmpty()) {
            return "Please enter a valid description for the alarm.";
        }
        return "Some error occurred.";
    }

    public int getHh() {
        return hh;
    }

    public void setHh(int hh) {
        this.hh = hh;
    }

    public int getMm() {
        return mm;
    }

    public void setMm(int mm) {
        this.mm = mm;
    }

    public int getSec() {
        return sec;
    }

    public void setSec(int sec) {
        this.sec = sec;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getFriendlyTime() {
        
        String hour = String.valueOf(hh);
        String min = String.valueOf(mm);
        String secs = String.valueOf(sec);
        
        hour = hour.length() == 1 ? "0"+hour : hour;
        min = min.length() == 1 ? "0"+min : min;
        secs = secs.length() == 1 ? "0"+secs : secs;
        
        
        return hour + ":" + min + ":" + secs;
    }

}
