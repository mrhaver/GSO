///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package bank.bankieren;
//
//import fontys.util.NumberDoesntExistException;
//import junit.framework.TestCase;
//
///**
// *
// * @author Alex Ras
// */
//public class IBankTest extends TestCase {
//    
//    private Bank bank = new Bank("ABN");
//    
//    public IBankTest(String testName) {
//        super(testName);
//    }
//
//    /**
//     * Test of openRekening method, of class IBank.
//     */
//    public void testOpenRekening() {
//        System.out.println("openRekening");
//        
//        String naam = "MijnRekening";
//        String plaats = "MijnPlaats";
//        IBank instance = (IBank) bank;
//        
//        int expNr = 100000000;
//        int nr = instance.openRekening(naam, plaats);
//        
//        assertEquals(expNr, nr);
//    }
//
//    /**
//     * Test of maakOver method, of class IBank.
//     */
//    public void testMaakOver() throws Exception {
//        System.out.println("maakOver");
//        
//        Money bedrag = new Money(200,"Euro");
//        IBank instance = (IBank) bank;
//        int bron = instance.openRekening("Rekening1", "Plaats");
//        int bestemming = instance.openRekening("Rekening2", "Plaats");
//        
//        boolean expResult = true;
//        boolean result = instance.maakOver(bron, bestemming, bedrag);
//        
//        assertEquals(expResult, result);
//    }
//
//    /**
//     * Test of getRekening method, of class IBank.
//     */
//    public void testGetRekening() {
//        System.out.println("getRekening");
//        
//        IBank instance = (IBank) bank;
//        
//        int expResult = instance.openRekening("Rekening", "Plaats");
//        int result = instance.getRekening(expResult).getNr();
//        
//        assertEquals(expResult, result);
//    }
//
//    /**
//     * Test of getName method, of class IBank.
//     */
//    public void testGetName() {
//        System.out.println("getName");
//        IBank instance = (IBank) bank;
//        
//        String expResult = "ABN";
//        String result = instance.getName();
//        
//        assertEquals(expResult, result);
//    }
//    
//}
