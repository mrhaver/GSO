/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fontys.time;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Frank Haver
 * Klasse bevat een onderwerp en heeft 1 timespan object.
 * Ook heeft deze klasse een lijst van contacten.
 * In deze klassse is het mogelijk om contacten toe te voegen en verwijderen
 */
public class Appointment {
    
    private String onderwerp;
    private ITimeSpan timeSpan;
    private ArrayList<Contact> genodigden;
    
    public Appointment(String onderwerp, ITimeSpan timeSpan){
        this.onderwerp = onderwerp;
        this.timeSpan = timeSpan;
        genodigden = new ArrayList<>();
    }
    
    public String getOnderwerp(){
        return this.onderwerp;
    }
    
    public ITimeSpan getTimeSpan(){
        return this.timeSpan;
    }
    
    public ArrayList<Contact> Genodigden(){
        return this.genodigden;
    }
    
    /**
     * @Author Frank Haver
     * Toevoegen van een contact mag alleen maar doorgang vinden als een 
     * Contact object toe te voegen als de timespan van deze afspraak niet overlapt
     * met een van de andere aspraken van dit meegegeven contact.
     * @param c
     * Contact c is de nieuwe contact die toegevoegd gaat worden
     * @return 
     * retourneert false als er overlap is met een andere afspraak 
     * retourneert true als het contact toegevoegd wordt aan de afspraak
     */
    public boolean addContact(Contact c){
        for(Appointment a : c.Agenda()){
            /* 
            als de begintijd of de eindtijd tussen de begintijd van 
            */
            if(a.getTimeSpan().intersectionWith(this.timeSpan) != null){
                return false;
            }
        }
        this.genodigden.add(c);
        c.addAppointment(this);
        return true;        
    }
    
    public void removeContact(Contact c){
        this.genodigden.remove(c);
        c.removeAppointment(this);
    }
}
