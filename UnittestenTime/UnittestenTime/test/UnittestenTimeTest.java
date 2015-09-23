/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import fontys.time.Time;
import fontys.time.TimeSpan;
import junit.framework.Assert;
import fontys.time.DayInWeek;
import fontys.time.ITime;
import fontys.time.Time;
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
    Time testtime;

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
       testtime = new Time(2015,8,23,22,22);
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
        
       Assert.assertNotNull("Object van type timespan verwacht", ts);
    }
    
    /**
     * Test of er een exceptie wordt opgegooid als de begintijd
     * later is dan de eindtijd
     */
    @Test (expected = IllegalArgumentException.class)
    public void testTimeSpanConstructorException(){      
       ts = new TimeSpan(et, bt);
    }
    
    /**
     * Test of de begintijd in de getter van de timespan
     * wel hetzelfde is als de begintijd die is aangemaakt in 
     * de constructor van de Time klasse
     */
    @Test
    public void testTimeSpanBeginTime(){
        Time bt2 = new Time(1996, 1, 15, 9, 15);
        
        
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
    //
    // @Test
    // public void hello() {}
    @Test (expected = IllegalArgumentException.class)
    public void TestConstructorMonthException(){
        Time t = new Time(1995,0,22,22,22);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void TestConstructorDayException(){
        Time t = new Time(1995,5,0,22,22);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void TestConstructorHourException(){
        Time t = new Time(1995,5,22,-1,22);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void TestConstructorMinuteException(){
        Time t = new Time(1995,5,22,22,-1);
    }
    
    @Test
    public void TestTimeGetDayInWeek(){
        Assert.assertEquals("Day is supposed to be Wedsnesday, but it is " + testtime.getDayInWeek().name(),DayInWeek.WED,testtime.getDayInWeek());
        Time testtimesun = new Time(2015,8,20,22,22);
        Assert.assertEquals("Day is supposed to be Sunday, but it is " + testtime.getDayInWeek().name(),DayInWeek.SUN,testtimesun.getDayInWeek());
        Time testtimemon = new Time(2015,8,21,22,22);
        Assert.assertEquals("Day is supposed to be Monday, but it is " + testtime.getDayInWeek().name(),DayInWeek.MON,testtimemon.getDayInWeek());
        Time testtimetue = new Time(2015,8,22,22,22);
        Assert.assertEquals("Day is supposed to be Tuesday, but it is " + testtime.getDayInWeek().name(),DayInWeek.TUE,testtimetue.getDayInWeek());
        Time testtimethu = new Time(2015,8,24,22,22);
        Assert.assertEquals("Day is supposed to be Thursday, but it is " + testtime.getDayInWeek().name(),DayInWeek.THU,testtimethu.getDayInWeek());
        Time testtimefri = new Time(2015,8,25,22,22);
        Assert.assertEquals("Day is supposed to be Friday, but it is " + testtime.getDayInWeek().name(),DayInWeek.FRI,testtimefri.getDayInWeek());
        Time testtimesat = new Time(2015,8,26,22,22);
        Assert.assertEquals("Day is supposed to be Saturday, but it is " + testtime.getDayInWeek().name(),DayInWeek.SAT,testtimesat.getDayInWeek());
    }
    
    @Test
    public void TestTimeGet(){
        Assert.assertEquals("Year should be 2015", 2015,testtime.getYear());
        Assert.assertEquals("Month should be September", 9,testtime.getMonth());
        Assert.assertEquals("Day should be 23", 23,testtime.getDay());
        Assert.assertEquals("Hour should be 22",22,testtime.getHours());
        Assert.assertEquals("Minutes should be 22",22,testtime.getMinutes());
    }
    
    @Test
    public void TestTimePlus(){
        ITime plus = testtime.plus(1);
        Assert.assertEquals("Minutes should be 23", 23,plus.getMinutes());
    }
    
    @Test
    public void TestTimeCompareTo(){
        Time comparetime = new Time(2015,8,23,22,40);
        Assert.assertEquals("Should be 1", 1,testtime.compareTo(comparetime));
        comparetime = new Time(2015,8,23,22,10);
        Assert.assertEquals("Should be -1", -1,testtime.compareTo(comparetime));
        comparetime = new Time(2015,8,23,22,22);
        Assert.assertEquals("Should be 0", 0,testtime.compareTo(comparetime));
    }
    
    @Test
    public void TestTimeDifference(){
        Time comparetime = new Time(2015,8,23,22,40);
        Assert.assertEquals("Should be 18",18,testtime.difference(comparetime));
    }
}
