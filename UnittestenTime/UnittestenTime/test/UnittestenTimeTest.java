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
    
    @Test
    public void testTimeSpanSetBeginTime(){    
        Time nieuwBT = new Time(1996, 1, 15, 9, 14);
        ts.setBeginTime(nieuwBT);
        
        Assert.assertTrue("Begintijd niet goed geset", ts.getBeginTime().compareTo(nieuwBT) == 0);   
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void testTimeSpanSetBeginTimeException(){    
        Time nieuwBT = new Time(1996, 1, 15, 9, 25);
        ts.setBeginTime(nieuwBT);    
    }
    
    @Test
    public void testTimeSpanSetEndTime(){    
        Time nieuwET = new Time(1996, 1, 15, 9, 16);
        ts.setEndTime(nieuwET);
        
        Assert.assertTrue("Eindtijd niet goed geset", ts.getEndTime().compareTo(nieuwET) == 0);   
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void testTimeSpanSetEndTimeException(){    
        Time nieuwET = new Time(1996, 1, 15, 9, 10);
        ts.setEndTime(nieuwET);    
    }
    
    @Test
    public void testTimeSpanMove(){
        ts.move(10);
        TimeSpan nieuwTS = new TimeSpan(new Time(1996, 1, 15, 9, 25), new Time(1996, 1, 15, 9, 30));
        
        Assert.assertTrue("Begintijd niet goed verplaatst", ts.getBeginTime().compareTo(nieuwTS.getBeginTime()) == 0);
        Assert.assertTrue("Eindtijd niet goed verplaatst", ts.getEndTime().compareTo(nieuwTS.getEndTime()) == 0);
    }
    
    @Test
    public void testTimeSpanChangeLength() {
        ts.changeLengthWith(10);
        int expectedLength = 15;
        
        Assert.assertEquals("Niet de juiste lengte toegevoegd", expectedLength, ts.length());
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void testTimeSpanChangeLengthException() {
        ts.changeLengthWith(-10);
        ts.changeLengthWith(0);
    }
    
    @Test
    public void testTimeSpanIsPartOf() {
        TimeSpan ts2 = new TimeSpan(new Time(1996, 1, 15, 9, 16), new Time(1996, 1, 15, 9, 17));
        Assert.assertEquals("ts2 zou deel van ts moeten zijn", true, ts.isPartOf(ts2));
        ts2 = new TimeSpan(new Time(1996, 1, 15, 9, 14), new Time(1996, 1, 15, 9, 17));
        Assert.assertEquals("ts2 is geen deel van ts", false, ts.isPartOf(ts2));
        ts2 = new TimeSpan(new Time(1996, 1, 15, 9, 16), new Time(1996, 1, 15, 9, 50));
        Assert.assertEquals("ts2 is geen deel van ts", false, ts.isPartOf(ts2));
    }
    
    @Test
    public void testTimeSpanUnionWith() {
        /**
        TimeSpan timeSpan1 = new TimeSpan(new Time(1996, 1, 15, 9, 15), new Time(1996, 1, 15, 9, 20));
        TimeSpan timeSpan2 = new TimeSpan(new Time(1996, 1, 15, 9, 12), new Time(1996, 1, 15, 9, 16));
        TimeSpan expected = new TimeSpan(new Time(1996, 1, 15, 9, 15), new Time(1996, 1, 15, 9, 16));
        TimeSpan unionTS;
        
        unionTS = timeSpan1.unionWith(timeSpan2);
        
        Assert.assertEquals("Timespan moet gelijk zijn", expected, unionTS);
        */ 
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
