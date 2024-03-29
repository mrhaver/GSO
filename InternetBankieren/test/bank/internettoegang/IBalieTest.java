/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank.internettoegang;

import bank.bankieren.Bank;
import java.rmi.RemoteException;
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
    
    private Bank bank;
    private Balie balie;
    
    public IBalieTest() throws RemoteException {
        bank = new Bank("Rabobank");
        balie = new Balie(bank);
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
    public void testOpenRekeningLegeNaam(){
        balie.openRekening("", "Veghel", "Frank");
    }
    
    /**
     * @Author Frank Haver
     * Test of er een IllegalArgumentExeption wordt opgegooid als er geen 
     * plaats is ingegeven.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testOpenRekeningLegePlaats(){
        balie.openRekening("Frank", "", "Frank");
    }
    
    /**
     * @Author Frank Haver
     * Test of er een IllegalArgumentExeption wordt opgegooid als er geen 
     * wachtwoord is ingegeven.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testOpenRekeningLeegWachtwoord(){
        balie.openRekening("Frank", "Veghel", "");
    }
    
    /**
     * @Author Frank Haver
     * Test of er een IllegalArgumentExeption wordt opgegooid als er 
     * een te kort wachtwoord is ingegeven.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testOpenRekeningKortWachtwoord(){
        balie.openRekening("Frank", "Veghel", "Fra");
    }
    
    /**
     * @Author Frank Haver
     * Test of er een IllegalArgumentExeption wordt opgegooid als er  
     * een te lang wachtwoord is ingegeven.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testOpenRekeningLangWachtwoord(){
        balie.openRekening("Frank", "Veghel", "FrankHaver");
    }
    
    /**
     * @Author Frank Haver
     * Test of het openen van een rekening het de ingevoerde accountnaam
     * retourneert.
     */
    @Test
    public void testOpenRekening(){
        String nieuwAccountNaam = balie.openRekening("Frank", "Veghel", "Frank");
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
        balie = new Balie(new Bank("ABN"));
        balie.openRekening("Frank", "Veghel", "Frank");
        IBankiersessie sessie1 = balie.logIn("Frankie", "Frank");
        Assert.assertNull("Sessie mag niet aangemaakt worden want accountnaam is onbekend", sessie1);
    
        // 2
        IBankiersessie sessie2 = balie.logIn("Frank", "Frankie");
        Assert.assertNull("Sessie mag niet aangemaakt worden want combinatie accountnaam / wachtwoord klopt niet", sessie2);
    
        // 3
        IBankiersessie sessie3 = balie.logIn("Frank", "Frank");
        Assert.assertNotNull("Sessie moet aangemaakt worden want de accountnaam - wachtwoordcombinatie klopt", sessie3);
    }
}
