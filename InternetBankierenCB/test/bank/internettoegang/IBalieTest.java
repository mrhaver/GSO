/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank.internettoegang;

import bank.bankieren.Bank;
import bank.bankieren.Money;
import fontys.util.InvalidSessionException;
import internetbankieren.CentraleBank;
import internetbankieren.ICentraleBank;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class IBalieTest {
    
    private final Bank bank;
    private Balie balie;
    private final CentraleBank centrale;
    
    public IBalieTest() throws RemoteException {
        bank = new Bank("Rabobank"); 
        centrale = new CentraleBank();
        balie = new Balie(bank, centrale);
        
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
     * @Author Frank Haver
     * Test of er een IllegalArgumentExeption wordt opgegooid als er geen 
     * naam is ingegeven.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testOpenRekeningLegeNaam() throws RemoteException{
        ICentraleBank cb = (ICentraleBank) centrale;
        balie.openRekening("", "Veghel", "Frank", cb.getNieuwRekeningNummer());
    }
    
    /**
     * @Author Frank Haver
     * Test of er een IllegalArgumentExeption wordt opgegooid als er geen 
     * plaats is ingegeven.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testOpenRekeningLegePlaats() throws RemoteException{
        ICentraleBank cb = (ICentraleBank) centrale;
        balie.openRekening("Frank", "", "Frank", cb.getNieuwRekeningNummer());
    }
    
    /**
     * @Author Frank Haver
     * Test of er een IllegalArgumentExeption wordt opgegooid als er geen 
     * wachtwoord is ingegeven.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testOpenRekeningLeegWachtwoord() throws RemoteException{
        ICentraleBank cb = (ICentraleBank) centrale;
        balie.openRekening("Frank", "Veghel", "",cb.getNieuwRekeningNummer());
    }
    
    /**
     * @Author Frank Haver
     * Test of er een IllegalArgumentExeption wordt opgegooid als er 
     * een te kort wachtwoord is ingegeven.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testOpenRekeningKortWachtwoord() throws RemoteException{
        ICentraleBank cb = (ICentraleBank) centrale;
        balie.openRekening("Frank", "Veghel", "Fra", cb.getNieuwRekeningNummer());
    }
    
    /**
     * @Author Frank Haver
     * Test of er een IllegalArgumentExeption wordt opgegooid als er  
     * een te lang wachtwoord is ingegeven.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testOpenRekeningLangWachtwoord() throws RemoteException{
        ICentraleBank cb = (ICentraleBank) centrale;
        balie.openRekening("Frank", "Veghel", "FrankHaver", cb.getNieuwRekeningNummer());
    }
    
    /**
     * @Author Frank Haver
     * Test of het openen van een rekening het de ingevoerde accountnaam
     * retourneert.
     */
    @Test
    public void testOpenRekening() throws RemoteException{
         ICentraleBank cb = (ICentraleBank) centrale;
        String nieuwAccountNaam = balie.openRekening("Frank", "Veghel", "Frank", cb.getNieuwRekeningNummer());
        Assert.assertNotNull("Rekening niet juist aangemaakt", nieuwAccountNaam);
    }
    
    /**
     * @Author Frank Haver
     * 1)   Test of er null geretourneerd wordt als er een onbekende 
     *      accountnaam is ingevoerd.
     * 2)   Test of er null geretourneerd wordt als de accountnaam - 
     *      wachtwoordcombinatie niet klopt.
     * 3)   Test of er een sessie wordt teruggegeven als er juist wordt
     *      ingelogd.
     */
    @Test
    public void testLogIn() throws RemoteException{
        // 1
        ICentraleBank cb = (ICentraleBank) centrale;
        balie = new Balie(new Bank("ABN"), centrale);
        
        balie.openRekening("Frank", "Veghel", "Frank", cb.getNieuwRekeningNummer());
        IBankiersessie sessie1 = balie.logIn("Frankie", "Frank");
        Assert.assertNull("Sessie mag niet aangemaakt worden want accountnaam is onbekend", sessie1);
    
        // 2
        IBankiersessie sessie2 = balie.logIn("Frank", "Frankie");
        Assert.assertNull("Sessie mag niet aangemaakt worden want combinatie accountnaam / wachtwoord klopt niet", sessie2);
    
        // 3
        IBankiersessie sessie3 = balie.logIn("Frank", "Frank");
        Assert.assertNotNull("Sessie moet aangemaakt worden want de accountnaam - wachtwoordcombinatie klopt", sessie3);
    }
    
    /**
     * @Author Frank Haver
     * Test het overmaken naar een specifieke rekening
     * 1) test met ideale omstandigheden
     * 2) test met ongeldig rekeningnummer
     * @throws RemoteException 
     */
    @Test
    public void testMaakOver() throws RemoteException, InvalidSessionException{
        ICentraleBank cb = (ICentraleBank) centrale;
        balie.openRekening("Frank", "Veghel", "Frank", cb.getNieuwRekeningNummer());
        balie.openRekening("Henk", "Henk", "Henk", cb.getNieuwRekeningNummer());
        IBankiersessie sessie = balie.logIn("Frank", "Frank");
        IBankiersessie sessie2 = balie.logIn("Haver", "Haver");
        
        boolean gelukt = balie.maakOver(sessie.getRekening().getNr(), new Money(100, Money.EURO));
        Assert.assertTrue("er moet overgemaakt worden", gelukt);
        
        boolean mislukt = balie.maakOver(1, new Money(100, Money.EURO));
        Assert.assertFalse("fout rekeningnummer zou niet overgemaakt mogen worden", mislukt);
    }
}
