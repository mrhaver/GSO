/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import fontys.time.DayInWeek;
import fontys.time.ITime;
import fontys.time.Time;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Frank Haver
 */
public class UnittestenTimeTest {
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
        testtime = new Time(2015,8,23,22,22);
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
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
        Assert.assertEquals("Day is supposed to be Friday, but it is " + testtime.getDayInWeek().name(),DayInWeek.WED,testtime.getDayInWeek());
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
}
