/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aexbanner_week7;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @Author Frank Haver
 */
public class BannerController {

    private AEXBanner banner;    
    private Timer pollingTimer;
    private Timer fluctuatieTimer;
    private ArrayList<IFonds> huidigeKoersen;
    
    private static final String bindingName = "Effectenbeurs";
    private Registry registry = null;
    private IEffectenbeurs effectenbeurs = null;

    public BannerController(AEXBanner banner, String ipAddress, int portNumber) {

        this.banner = banner;
        
        // Start polling timer: update banner every two seconds
        pollingTimer = new Timer();
        
        fluctuatieTimer = new Timer();
        
        // TODO 
        
        // Print IP address and port number for registry
        System.out.println("Client: IP Address: " + ipAddress);
        System.out.println("Client: Port number " + portNumber);

        // Locate registry at IP address and port number
        try {
            registry = LocateRegistry.getRegistry(ipAddress, portNumber);
        } catch (RemoteException ex) {
            System.out.println("Client: Cannot locate registry");
            System.out.println("Client: RemoteException: " + ex.getMessage());
            registry = null;
        }

        // Print result locating registry
        if (registry != null) {
            System.out.println("Client: Registry located");
        } else {
            System.out.println("Client: Cannot locate registry");
            System.out.println("Client: Registry is null pointer");
        }

        // Print contents of registry
        if (registry != null) {
            printContentsRegistry();
        }

        // Bind student administration using registry
        if (registry != null) {
            try {
                effectenbeurs = (IEffectenbeurs) registry.lookup(bindingName);
            } catch (RemoteException ex) {
                System.out.println("Client: Cannot bind effectenbeurs");
                System.out.println("Client: RemoteException: " + ex.getMessage());
                effectenbeurs = null;
            } catch (NotBoundException ex) {
                System.out.println("Client: Cannot bind effectenbeurs");
                System.out.println("Client: NotBoundException: " + ex.getMessage());
                effectenbeurs = null;
            }
        }

        // Print result binding student administration
        if (effectenbeurs != null) {
            System.out.println("Client: effectenbeurs bound");           
            
        } else {
            System.out.println("Client: effectenbeurs is null pointer");
        }
        
        try{
            huidigeKoersen = effectenbeurs.getKoersen();
            fluctuatieTimer.schedule(new FluctuatieTask(), 0,2000);
        }
        catch(RemoteException ex){
            System.out.println(ex.getMessage());
        }
        
        
        
    }
    
    // Print contents of registry
    private void printContentsRegistry() {
        try {
            String[] listOfKoers = registry.list();
            System.out.println("Client: list of koersen bound in registry:");
            if (listOfKoers.length != 0) {
                for (String s : registry.list()) {
                    System.out.println(s);
                }
            } else {
                System.out.println("Client: list of koersen bound in registry is empty");
            }
        } catch (RemoteException ex) {
            System.out.println("Client: Cannot show list of koersen bound in registry");
            System.out.println("Client: RemoteException: " + ex.getMessage());
        }
    }
    
    class FluctuatieTask extends TimerTask{

        @Override
        public void run() {
            // fluctueer de huidige koersen
            // verander met minimaal 0 en maximaal 5% van de koers
            // kan negatief of positief zijn.
            Random rnd = new Random(); 
            for(IFonds ifo : huidigeKoersen){
                double rangeMin = 0;
                double rangeMax = ifo.getKoers()/20d;
                double randomDouble = rangeMin + (rangeMax - rangeMin) * rnd.nextDouble();
                int randomInt = 0 + (int)(Math.random() * ((1 - 0) + 1));
                randomDouble = (double)Math.round(randomDouble * 100) / 100d;
                double nieuwWaarde;
                if(randomInt == 0){
                    nieuwWaarde = (double)Math.round((ifo.getKoers() - randomDouble) *100) / 100d;
                }
                else{
                    nieuwWaarde = (double)Math.round((ifo.getKoers() + randomDouble) *100) / 100d;
                }
                ifo.setKoers(nieuwWaarde);                
            }
            
            banner.setKoersen(getHuidigeKoersString());
        }
        
    }
    
    public String getHuidigeKoersString(){
        String koersen = "";
        for(IFonds i : huidigeKoersen){
             koersen = koersen + i.getNaam() + ": " + String.valueOf(i.getKoers()) + " ";
        }                
        return koersen;
    }

    // Stop banner controller
    public void stop() {
        pollingTimer.cancel();
        // Stop simulation timer of effectenbeurs
        // TODO
        fluctuatieTimer.cancel();
    }
}

