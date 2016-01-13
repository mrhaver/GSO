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
 * @author Alex Ras
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
    
    /**
     * @Author Frank Haver
     * Test over het overmaken naar een bepaalde rekening
     * 1)   Test met geldige rekeningen en een geldig bedrag
     * 2)   Test met een ongeldige rekening
     * 3)   Test met een ongeldig bedrag
     */
    @Test
    public void testMaakOverRekening(){
        IBank instance = (IBank) bank;
        
        int bron = instance.openRekening("Rekening1", "Plaats");
        int bestemming = instance.openRekening("Rekening2", "Plaats");
        
        instance.maakOverRekening(bestemming, new Money(100, Money.EURO));
        
        IRekeningTbvBank dest_account = (IRekeningTbvBank) instance.getRekening(bestemming);
        
        Assert.assertEquals("money niet de juiste waarde", new Money(100, Money.EURO).getCents(), dest_account.getSaldo().getCents());
        
        boolean mislukt = instance.maakOverRekening(3, new Money(100, Money.EURO));
        Assert.assertFalse("geen geldige rekening meegegeven", mislukt);
        
        mislukt = instance.maakOverRekening(bestemming, null);
        Assert.assertFalse("geen money meegegeven", mislukt);
    }
}
