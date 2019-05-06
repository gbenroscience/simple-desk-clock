/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package util;

/**
 * Enum that models the three possible
 * states of a ClockHand object.
 * Hour, Minute, Second.
 *
 * @author Gbemiro Jiboye
 */
public enum HandType {
HOURHAND,MINUTEHAND,SECONDHAND;
/**
 * 
 * @return true if the enum is a 
 * hour hand.
 */
public boolean isHourHand(){
    return this==HOURHAND;
}
/**
 * 
 * @return true if the enum is a 
 * minute hand.
 */
public boolean isMinuteHand(){
    return this==HOURHAND;
}
/**
 * 
 * @return true if the enum is a 
 * second hand.
 */
public boolean isSecondHand(){
    return this==HOURHAND;
}


}
