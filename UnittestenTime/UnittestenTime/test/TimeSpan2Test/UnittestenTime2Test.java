package TimeSpan2Test;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import fontys.time.Time;
import fontys.time.TimeSpan2;
import junit.framework.Assert;
import fontys.time.DayInWeek;
import fontys.time.ITime;
import fontys.time.Time;
import fontys.time.TimeSpan2;
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
public class UnittestenTime2Test {
    
    Time bt;
    Time et;   
    Time testtime;
    TimeSpan2 ts2;

    public UnittestenTime2Test() {
        
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
       
       ts2 = new TimeSpan2(bt, et);
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
        
       Assert.assertNotNull("Object van type TimeSpan2 verwacht", ts2);
    }
    
    /**
     * Test of er een exceptie wordt opgegooid als de begintijd
     * later is dan de eindtijd
     */
    @Test (expected = IllegalArgumentException.class)
    public void testTimeSpanConstructorException(){      
       ts2 = new TimeSpan2(et, bt);
    }
    
    /**
     * @author Frank Haver
     * Test of de begintijd in de getter van de TimeSpan2
     * wel hetzelfde is als de begintijd die is aangemaakt in 
     * de constructor van de Time klasse
     */
    @Test
    public void testTimeSpanBeginTime(){
        Time bt2 = new Time(1996, 1, 15, 9, 15);
               
        Assert.assertTrue("Begintijd is niet hetzelfde", ts2.getBeginTime().compareTo(bt2) == 0);
    }
    
    /**
     * @author Frank Haver
     * Test of de eindtijd in de getter van de TimeSpan2
     * wel hetzelfde is als de eindtijd die is aangemaakt in 
     * de constructor van de Time klasse
     */
    @Test 
    public void testTimeSpanEndTime(){
        Time et2 = new Time(1996, 1, 15, 9, 20);
        
        Assert.assertTrue("Eindtijd is niet hetzelfde", ts2.getEndTime().compareTo(et2) == 0);
    }
    
    /**
     * @author Frank Haver
     * Test of de lengte de juiste lengte is.
     */
    @Test
    public void testTimeSpanLength(){      
        int actueleLengte = 5;
        
        int expectedLengte = ts2.length();
        
        Assert.assertEquals("Lengte klopt niet", actueleLengte, expectedLengte);
    }
    
    /**
     * @author Frank Haver
     * Test of de werkelijk gesette begintijd hetzelfde is als de 
     * begintijd die je wilt gaan setten.
     */
    @Test
    public void testTimeSpanSetBeginTime(){    
        Time nieuwBT = new Time(1996, 1, 15, 9, 14);
        ts2.setBeginTime(nieuwBT);
        
        Assert.assertTrue("Begintijd niet goed geset", ts2.getBeginTime().compareTo(nieuwBT) == 0);   
    }
    
    /**
     * @author Frank Haver
     * Test dat of je een IllegalArgumentException krijgt als je een begin
     * tijd ingeeft die later is dan de eindtijd
     */
    @Test (expected = IllegalArgumentException.class)
    public void testTimeSpanSetBeginTimeException(){    
        Time nieuwBT = new Time(1996, 1, 15, 9, 25);
        ts2.setBeginTime(nieuwBT);    
    }
    
    /**
     * @author Frank Haver
     * Test of de werkelijk gesette eindtijd hetzelfde is als de 
     * eindtijd die je wilt gaan setten
     */
    @Test
    public void testTimeSpanSetEndTime(){    
        Time nieuwET = new Time(1996, 1, 15, 9, 16);
        ts2.setEndTime(nieuwET);
        
        Assert.assertTrue("Eindtijd niet goed geset", ts2.getEndTime().compareTo(nieuwET) == 0);   
    }
    
    /**
     * @author Frank Haver
     * Test of je een IllegalArgumentException krijgt als je een eindtijd
     * ingeeft die eerder is dan de begintijd.
     */
    @Test (expected = IllegalArgumentException.class)
    public void testTimeSpanSetEndTimeException(){    
        Time nieuwET = new Time(1996, 1, 15, 9, 10);
        ts2.setEndTime(nieuwET);    
    }
    
    /**
     * @author Frank Haver
     * Test het verplaatsen van de TimeSpan2 wat betreft de begintijd en de 
     * eindtijd
     */
    @Test
    public void testTimeSpanMove(){
        ts2.move(10);
        TimeSpan2 nieuwTS = new TimeSpan2(new Time(1996, 1, 15, 9, 25), new Time(1996, 1, 15, 9, 30));
        
        Assert.assertTrue("Begintijd niet goed verplaatst", ts2.getBeginTime().compareTo(nieuwTS.getBeginTime()) == 0);
        Assert.assertTrue("Eindtijd niet goed verplaatst", ts2.getEndTime().compareTo(nieuwTS.getEndTime()) == 0);
    }
    
    /**
     * @author Frank Haver
     * Test het verplaatsen van het einde van de TimeSpan2
     */
    @Test
    public void testTimeSpanChangeLength() {
        ts2.changeLengthWith(10);
        int expectedLength = 15;
        
        Assert.assertEquals("Niet de juiste lengte toegevoegd", expectedLength, ts2.length());
    }
    
    /**
     * @author Frank Haver
     * Test het veranderen van de lengte, maar test of er een exceptie 
     * wordt opgegooid als er een niet positief getal wordt meegegeven
     * als parameter
     */
    @Test (expected = IllegalArgumentException.class)
    public void testTimeSpanChangeLengthException() {
        ts2.changeLengthWith(-10);
        ts2.changeLengthWith(0);
    }
    
    /**
     * @author Frank Haver
     * Test of een TimeSpan helemaal deel uitmaakt van een andere
     * timespan, dit houdt in dat de eerste timespan er helemaal in moet zitten.
     * er zijn 3 mogelijkheden. 
     * 1) hij zit er in
     * 2) alleen eindpunt of beginpunt zit er in
     * 3) hij zit er niet in
     */
    @Test
    public void testTimeSpanIsPartOf() {
        TimeSpan2 testTS = new TimeSpan2(new Time(1996, 1, 15, 9, 16), new Time(1996, 1, 15, 9, 17));
        Assert.assertEquals("ts2 zou deel van ts2 moeten zijn", true, ts2.isPartOf(testTS));
        testTS = new TimeSpan2(new Time(1996, 1, 15, 9, 14), new Time(1996, 1, 15, 9, 17));
        Assert.assertEquals("ts2 is geen deel van ts2", false, ts2.isPartOf(testTS));
        testTS = new TimeSpan2(new Time(1996, 1, 15, 9, 16), new Time(1996, 1, 15, 9, 50));
        Assert.assertEquals("ts2 is geen deel van ts2", false, ts2.isPartOf(testTS));
    }
    
    /**
     * @author Frank Haver
     * Test of twee TimeSpans aan elkaar worden gekoppeld mits de timespans
     * elkaar ergens overlappen, ze kunnen ook aan elkaar gelijk zijn.
     * De assertstatements in deze methode worden hieronder beschrevend
     * 1) bij nummer 1 wordt er een timespan doorgegeven waarbij het beginpunt van timespan2 wordt gepakt en het eindpunt van timespan1
     * 2) bij nummer 2 heeft het assertstatement geen overlappend gedeelte en timespan2 is voor timespan1
     * 3) bij nummer 3 kan er ook geen timespan worden teruggegeven want er is geen overlappend gedeelte en timespan2 is na timespan1
     * 4) bij nummer 4 wordt er een timespan doorgegeven waarbij het beginpunt van timespan1 wordt gepakt en het eindpunt van timespan2
     */
    @Test
    public void testTimeSpanUnionWith() {
        
        TimeSpan2 timeSpan1 = new TimeSpan2(new Time(1996, 1, 15, 9, 15), new Time(1996, 1, 15, 9, 20));
        TimeSpan2 timeSpan2 = new TimeSpan2(new Time(1996, 1, 15, 9, 12), new Time(1996, 1, 15, 9, 16));
        TimeSpan2 expected = new TimeSpan2(new Time(1996, 1, 15, 9, 12), new Time(1996, 1, 15, 9, 20));
        TimeSpan2 unionTS = timeSpan1.unionWith(timeSpan2);
       
        Assert.assertEquals("Begintijden zijn niet gelijk", 0, expected.getBeginTime().compareTo(unionTS.getBeginTime()));
        Assert.assertEquals("Eindtijden zijn niet gelijk", 0, expected.getEndTime().compareTo(unionTS.getEndTime()));  
        
        timeSpan2 = new TimeSpan2(new Time(1996, 1, 15, 9, 12), new Time(1996, 1, 15, 9, 14));
        Assert.assertNull("Er kan geen TimeSpan2 worden gegeven", timeSpan1.unionWith(timeSpan2));
        
        timeSpan2 = new TimeSpan2(new Time(1996, 1, 15, 9, 21), new Time(1996, 1, 15, 9, 25));
        Assert.assertNull("Er kan geen TimeSpan2 worden gegeven", timeSpan1.unionWith(timeSpan2));
        
        timeSpan2 = new TimeSpan2(new Time(1996, 1, 15, 9, 16), new Time(1996, 1, 15, 9, 21));
        unionTS = timeSpan1.unionWith(timeSpan2);
        expected = new TimeSpan2(new Time(1996, 1, 15, 9, 15), new Time(1996, 1, 15, 9, 21));
        Assert.assertEquals("Begintijden zijn niet gelijk", 0, expected.getBeginTime().compareTo(unionTS.getBeginTime()));
        Assert.assertEquals("Eindtijden zijn niet gelijk", 0, expected.getEndTime().compareTo(unionTS.getEndTime()));
    }
    
    /**
     * @author Frank Haver
     * Test van twee TimeSpans of alleen de overlappende TimeSpan terug wordt gegeven.
     * De assertstatements van deze methode worden hieronder uitgelegd
     * 1) bij nummer 1 wordt gecheck of de juiste timespan teruggegeven wordt. Dit is een timespan met beginpunt timespan1 en eindpunt timespan2
     * 2) bij nummer 2 wordt gecheck of de juiste timespan teruggegeven wordt. Dit is een timespan met beginpunt timespan2 en eindpunt timespan1
     * 3) bij nummer 3 kan er geen timespan teruggegeven worden omdat er geen overlappend gedeelte is.
     */
    @Test
    public void testTimeSpanIntersectionWith(){
        TimeSpan2 timeSpan1 = new TimeSpan2(new Time(1996, 1, 15, 9, 15), new Time(1996, 1, 15, 9, 20));
        TimeSpan2 timeSpan2 = new TimeSpan2(new Time(1996, 1, 15, 9, 12), new Time(1996, 1, 15, 9, 16));
        TimeSpan2 expected = new TimeSpan2(new Time(1996, 1, 15, 9, 15), new Time(1996, 1, 15, 9, 16));
        TimeSpan2 intersectionTS = timeSpan1.intersectionWith(timeSpan2);
        
        Assert.assertEquals("Begintijden zijn niet gelijk", 0, expected.getBeginTime().compareTo(intersectionTS.getBeginTime()));
        Assert.assertEquals("Eindtijden zijn niet gelijk", 0, expected.getEndTime().compareTo(intersectionTS.getEndTime()));
        
        expected = new TimeSpan2(new Time(1996, 1, 15, 9, 16), new Time(1996, 1, 15, 9, 20));
        timeSpan2 = new TimeSpan2(new Time(1996, 1, 15, 9, 16), new Time(1996, 1, 15, 9, 21));
        intersectionTS = timeSpan1.intersectionWith(timeSpan2);
        Assert.assertEquals("Begintijden zijn niet gelijk", 0, expected.getBeginTime().compareTo(intersectionTS.getBeginTime()));
        Assert.assertEquals("Eindtijden zijn niet gelijk", 0, expected.getEndTime().compareTo(intersectionTS.getEndTime()));
        
        timeSpan2= new TimeSpan2(new Time(1996, 1, 15, 9, 12), new Time(1996, 1, 15, 9, 15));
        Assert.assertNull("Er kan geen TimeSpan2 worden gegeven", timeSpan1.intersectionWith(timeSpan2));
    }
    // einde Frank Haver
    
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
