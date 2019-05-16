/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import util.Alarm;
import util.Clock;

/**
 * 
 * Interface that clock uses to tell alarms on the Settings->Alarms table
 * that they are currently being triggered.
 *
 * @author JIBOYE, Oluwagbemiro Olaoluwa <gbenroscience@yahoo.com>
 */
public interface AlarmListener {
    
    
    void triggered(Clock c, Alarm a);
    void doneTriggering(Clock c, Alarm a);
    
    
}
