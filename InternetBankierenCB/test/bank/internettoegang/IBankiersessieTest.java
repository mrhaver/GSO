/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank.internettoegang;

import bank.bankieren.Bank;
import bank.bankieren.Money;
import fontys.util.InvalidSessionException;
import fontys.util.NumberDoesntExistException;
import internetbankieren.CentraleBank;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class IBankiersessieTest {
    
    private Bank bank;
    private Balie balie;
    private final CentraleBank centrale;
    
    public IBankiersessieTest() throws RemoteException {
        bank = new Bank("Rabobank");
        centrale = new CentraleBank();
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws RemoteException {
        balie = new Balie(bank, centrale);
        
        balie.openRekening("Frank", "Veghel", "Frank", centrale.getNieuwRekeningNummer());
        balie.openRekening("Haver", "Veghel", "Haver", centrale.getNieuwRekeningNummer());
    }
    
    @After
    public void tearDown() {
    }
   
    /**
     * @Author Frank Haver
     * Test of er een RuntimeException wordt opgegooid als de bron en het
     * bestemmings rekeningnummer hetzelfde zijn.
     */
    @Test(expected=RuntimeException.class)
    public void testMaakOverEigenRekening() throws RemoteException, NumberDoesntExistException, InvalidSessionException{
        IBankiersessie sessie = balie.logIn("Frank", "Frank");
        sessie.maakOver(sessie.getRekening().getNr(), new Money(1, "€"));
    }
    
    /**
     * @Author Frank Haver
     * Test of er een RuntimeException wordt opgegooid als de bron en het
     * bestemmings rekeningnummer hetzelfde zijn.
     */
    @Test(expected=RuntimeException.class)
    public void testMaakOverNegatiefBedrag() throws RemoteException, NumberDoesntExistException, InvalidSessionException{
        IBankiersessie sessie = balie.logIn("Frank", "Frank");
        IBankiersessie sessie2 = balie.logIn("Haver", "Haver");
        sessie.maakOver(sessie2.getRekening().getNr(), new Money(-1, "€"));
    }
    
    /**
     * @Author Frank Haver
     * Test of de maak over functie werkt als de condities goed zijn
     * 1)   Test of het saldo op 0 begint
     * 2)   Test of het saldo 100 is geworden
     */
    @Test
    public void testMaakOver() throws RemoteException, NumberDoesntExistException, InvalidSessionException{    
        IBankiersessie sessie = balie.logIn("Frank", "Frank");
        IBankiersessie sessie2 = balie.logIn("Haver", "Haver");
        
        // 1
        Assert.assertEquals("Saldo moet op 0 beginnen", 0, sessie2.getRekening().getSaldo().getCents());
        sessie.maakOver(sessie2.getRekening().getNr(), new Money(100, "€"));
        
        // 2
        Assert.assertEquals("Saldo moet nu 100 cent zijn", 100, sessie2.getRekening().getSaldo().getCents());
    }
    
    /**
     * @Author Frank Haver
     * Test of de applicatie er mee op houdt als de sessie is verlopen
     * voor deze unittest moet die geldigheidsduur 6 seconden zijn
     */
    @Test(expected=InvalidSessionException.class)
    public void testSessieGeldigheid() throws RemoteException, InvalidSessionException, NumberDoesntExistException{
        IBankiersessie sessie = balie.logIn("Frank", "Frank");
        IBankiersessie sessie2 = balie.logIn("Haver", "Haver");       
        try {
            Thread.sleep(6500);
            sessie.maakOver(100000001, new Money(100, "€"));
        } catch (InterruptedException ex) {
            Logger.getLogger(IBankiersessieTest.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
    /**
     * @Author Frank Haver
     * Test het overmaken naar een bepaalde rekening
     * 1) test het overmaken in juiste omstandigheden
     * 2) test het overmaken met ongeldig rekeningnummer
     * @throws RemoteException 
     */
    @Test
    public void testMaakOverRekening() throws RemoteException{
        IBankiersessie sessie = balie.logIn("Frank", "Frank");
        IBankiersessie sessie2 = balie.logIn("Haver", "Haver");    
        boolean gelukt = false;
        try {
            gelukt = sessie.maakOverRekening(sessie2.getRekening().getNr(), new Money(100, Money.EURO));
        } catch (InvalidSessionException ex) {
            Logger.getLogger(IBankiersessieTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Assert.assertTrue("er moeten over overgemaakt kunnen worden", gelukt);
        gelukt = true;
        
        gelukt = sessie.maakOverRekening(3, new Money(100, Money.EURO));
        
        
        Assert.assertFalse("er mag niet worden overgemaakt", gelukt);
    }
}
