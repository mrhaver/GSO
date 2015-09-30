/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fontys.time;

import java.util.ArrayList;

/**
 *
 * @author Frank Haver
 */
public class Contact {

    private String name;
    private ArrayList<Appointment> agenda;
    
    public Contact(String name){
        this.name = name;
        agenda = new ArrayList<>();
    }
    
    public String getName(){
        return this.name;
    }
    
    /**
     * @Author Frank Haver
     * een afspraak wordt pas toegevoegd als hij geen overlap heeft met een van de 
     * andere afspraken van dit contact
     * @param a
     * Afspraak a is de nieuwe afspraak die toegevoegd moet worden
     * @return
     * retourneert false als er overlap is met een van de andere afspraken van dit 
     * contact
     * retourneert true als de afspraak is toegevoegd aan de agenda
     */
    protected boolean addAppointment(Appointment a){
        for(Appointment ap : agenda){
            if(ap.getTimeSpan().intersectionWith(a.getTimeSpan()) != null){
                return false;
            }
        }
        agenda.add(a);
        return true;
    }
    
    protected void removeAppointment(Appointment a){
        this.agenda.remove(a);
    }
    
    public ArrayList<Appointment> Agenda() {
        return agenda;
    }
    
}
