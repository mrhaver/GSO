/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank.bankieren;

import fontys.util.NumberDoesntExistException;
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
public class IBankTest {
    
    private final Bank bank;
    
    public IBankTest() {
        bank = new Bank("Rabobank");
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * @Author Alex Ras
     * Test of openRekening method, of class IBank.
     */
    @Test
    public void testOpenRekening() {
        String naam = "MijnRekening";
        String plaats = "MijnPlaats";
        IBank instance = (IBank) bank;
        
        int expNr = 100000000;
        int nr = instance.openRekening(naam, plaats);
        
        Assert.assertEquals(expNr, nr);
    }
    
    /*
     * @Author Alex Ras
     * @throws fontys.util.NumberDoesntExistException 
     * Test of maakOver method, of class IBank.
     */
    @Test
    public void testMaakOver() throws NumberDoesntExistException {
        Money bedrag = new Money(200,"€");
        IBank instance = (IBank) bank;
        int bron = instance.openRekening("Rekening1", "Plaats");
        int bestemming = instance.openRekening("Rekening2", "Plaats");
        
        boolean expResult = true;
        boolean result = instance.maakOver(bron, bestemming, bedrag);
        
        Assert.assertEquals(expResult, result);
    }
    
    /**
     * @Author Alex Ras
     * Test of getRekening method, of class IBank.
     */
    @Test
    public void testGetRekening() {        
        IBank instance = (IBank) bank;
        
        int expResult = instance.openRekening("Rekening", "Plaats");
        int result = instance.getRekening(expResult).getNr();
        
        Assert.assertEquals(expResult, result);
    }

    /**
     * @Author Alex Ras
     * Test of getName method, of class IBank.
     */
    @Test
    public void testGetName() {
        IBank instance = (IBank) bank;
        
        String expResult = "Rabobank";
        String result = instance.getName();
        
        Assert.assertEquals(expResult, result);
    }
}
