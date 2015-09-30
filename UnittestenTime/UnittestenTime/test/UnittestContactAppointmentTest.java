/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import fontys.time.Appointment;
import fontys.time.Contact;
import fontys.time.Time;
import fontys.time.TimeSpan;
import junit.framework.Assert;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Frank Haver
 */
public class UnittestContactAppointmentTest {
    
    Time bt;
    Time et;   
    TimeSpan ts1;
    TimeSpan ts2;
    TimeSpan ts3;
    
    Appointment app1;
    Contact con1;
    Contact con2;
    Contact con3;
    
    public UnittestContactAppointmentTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    /**
     * @Author Frank Haver
     * Voor elke test worden er een aantal times en timespans aangemaakt waar tijdens de tests gewerkt mee kan worden
     * ook worden er appointments en contacten aangemaakt.
     */
    @Before
    public void setUp() {
       bt = new Time(1996, 1, 15, 9, 15); 
       et = new Time(1996, 1, 15, 9, 20);      
       ts1 = new TimeSpan(bt, et);
       ts2 = new TimeSpan(new Time(1996,1,15,9,17), new Time(1996,1,15,9,22));
       ts3 = new TimeSpan(new Time(1996,1,15,9,22), new Time(1996,1,15,9,30));
       
       app1 = new Appointment("Eerste Vergadering", ts1);
       con1 = new Contact("Henk");
       con2 = new Contact("Annie");
       con3 = new Contact("Japie");
       
    }
    
    @After
    public void tearDown() {
    }
    
    // Test Appointment
    /**
     * @Author Frank Haver 
     * Test of het onderwerp goed is geset door middel van de constructor
     */
    @Test
    public void TestAppointmentConstructor(){
        String onderwerp = "Eerste Vergadering";
        Assert.assertEquals("Onderwerp niet gelijk", onderwerp, app1.getOnderwerp());
    }
    
    /**
     * @Author Frank Haver
     * Test of de contacten wel aan de lijst met genodigden van een appointment worden toegevoegd
     */
    @Test
    public void TestGenodigdenLijst(){
        app1.addContact(con1);
        app1.addContact(con2);
        Assert.assertEquals("Niet juiste aantal genodigden", 2, app1.Genodigden().size());
    }
    
    /**
     * @Author Frank Haver
     * In deze test zijn er 3 assertstatements om te checken of het toevoegen en verwijderen van een contact goed gaat
     * 1) Bij nummer 1 mag het contact niet worden toegevoegd omdat app1 en app2 elkaar overlappen
     * 2) Bij nummer 2 moet het contact worden toegevoegd omdat er geen complicaties zijn
     * 3) Bij nummer 3 moet het contact worden verwijderd uit de lijst met genodigden van een afspraak
     */
    @Test
    public void TestAddRemoveContact(){
        Appointment app2 = new Appointment("Naar de KFC", ts2);
        app1.addContact(con1);
        app1.addContact(con2);
        boolean actual = app2.addContact(con1);
        // 1
        Assert.assertEquals("Contact mag niet toegevoegd worden", false, actual);
        Appointment app3 = new Appointment("Toiletbezoek", ts3);
        actual = app3.addContact(con1);
        // 2
        Assert.assertEquals("Contact moet toegevoegd worden", true, actual);
        app1.removeContact(con1);
        // 3
        Assert.assertEquals("Afspraak zou maar één contact moeten hebben", 1, app1.Genodigden().size());
    }
    
    // Test Contact
    /**
     * Test of de naam goed wordt toegewezen in de constructor
     */
    @Test 
    public void TestContactConstructor(){
        String name = "Henk";
        Assert.assertEquals("Namen komen niet overeen", name, con1.getName());
    }
    
    /**
     * @Author Frank Haver
     * Test of het toevoegen en verwijderen van een appointment goed gebeurd.
     * Hiervoor is de methode addContact nodig omdat deze op zijn beurt weer de methode addApointment aanroept
     * Dit is hetzelfde bij het verwijderen van een appointment
     * Er zijn 2 assertstatements nodig om dit te bewijzen
     * 1) Bij nummer 1 mag het contact niet worden toegevoegd omdat de appointments elkaar overlappen 
     * 2) Bij nummer 2 wordt de grote van het aantal afspraken gecontroleerd als een contact een appointment verwijderd
     */
    @Test
    public void TestAddRemoveAppointment(){
        app1.addContact(con1);
        Appointment app2 = new Appointment("Naar de KFC", ts2);
        boolean actual = app2.addContact(con1);
        // 1
        Assert.assertEquals("Contact zou niet toegevoegd mogen worden", false, actual);
        Appointment app3 = new Appointment("Toiletbezoek", ts3);
        app3.addContact(con1);
        app3.removeContact(con1);
        // 2
        Assert.assertEquals("Aantal afspraken klopt niet", 1, con1.Agenda().size());
    }
   


}
