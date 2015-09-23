/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
public class UnittestenTimeTest {
    
    Time bt;
    Time et;
    
    TimeSpan ts;
    
    public UnittestenTimeTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
       bt = new Time(1996, 1, 15, 9, 15); 
       et = new Time(1996, 1, 15, 9, 20);
       
       ts = new TimeSpan(bt, et);
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    
    /**
     * Test de happy flow van de constructor
     */
    @Test
    public void testTimeSpanConstructor(){      
       TimeSpan ts = new TimeSpan(bt, et);
       
       Assert.assertNotNull("Object van type timespan verwacht", ts);
    }
    
    /**
     * Test of er een exceptie wordt opgegooid als de begintijd
     * later is dan de eindtijd
     */
    @Test (expected = IllegalArgumentException.class)
    public void testTimeSpanConstructorException(){      
       TimeSpan ts = new TimeSpan(et, bt);
    }
    
    /**
     * Test of de begintijd in de getter van de timespan
     * wel hetzelfde is als de begintijd die is aangemaakt in 
     * de constructor van de Time klasse
     */
    @Test
    public void testTimeSpanBeginTime(){
        Time bt2 = new Time(1996, 1, 15, 9, 15);
        
        TimeSpan ts = new TimeSpan(bt, et);
        
        Assert.assertTrue("Begintijd is niet hetzelfde", ts.getBeginTime().compareTo(bt2) == 0);
    }
    
    /**
     * Test of de eindtijd in de getter van de timespan
     * wel hetzelfde is als de eindtijd die is aangemaakt in 
     * de constructor van de Time klasse
     */
    @Test 
    public void testTimeSpanEndTime(){
        Time et2 = new Time(1996, 1, 15, 9, 20);
        
        TimeSpan ts = new TimeSpan(bt, et);
        
        Assert.assertTrue("Eindtijd is niet hetzelfde", ts.getEndTime().compareTo(et2) == 0);
    }
    
    /**
     * Test of de lengte de juiste lengte is.
     */
    @Test
    public void testTimeSpanLength(){      
        int actueleLengte = 5;
        
        int expectedLengte = ts.length();
        
        Assert.assertEquals("Lengte klopt niet", actueleLengte, expectedLengte);
    }
    
    public void testTimeSpanSetBeginTime(){
        Time btNieuw = new Time(1996, 1, 15, 9, 1);
        
        ts.setBeginTime(btNieuw);
        
        Assert.assertEquals("Begintijd niet goed geset" , bt, ts.getBeginTime());
    }
}
