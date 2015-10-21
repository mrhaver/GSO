/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aexbanner_week7.Client;

import aexbanner_week7.Client.AEXBanner;
import aexbanner_week7.Shared.IEffectenbeurs;
import aexbanner_week7.Server.IFonds;
import aexbanner_week7.Shared.RemotePropertyListener;
import java.beans.PropertyChangeEvent;
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
public class BannerController implements RemotePropertyListener{

    private AEXBanner banner;    
    private ArrayList<IFonds> huidigeKoersen;
    
    private static final String bindingName = "Effectenbeurs";
    private Registry registry = null;
    private IEffectenbeurs effectenbeurs = null;
    private String ipAddress;
    private int portNumber;

    public BannerController(AEXBanner banner, String ipAddress, int portNumber) {

        this.banner = banner;
        this.ipAddress = ipAddress;
        this.portNumber = portNumber;
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
    
    public String getHuidigeKoersString(){
        String koersen = "";
        for(IFonds i : huidigeKoersen){
             koersen = koersen + i.getNaam() + ": " + String.valueOf(i.getKoers()) + " ";
        }                
        return koersen;
    }

    // Stop banner controller
    public void stop() {
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) throws RemoteException {
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
        }
        catch(RemoteException ex){
            System.out.println(ex.getMessage());
        }
        
        banner.setKoersen(getHuidigeKoersString());
    }
}

