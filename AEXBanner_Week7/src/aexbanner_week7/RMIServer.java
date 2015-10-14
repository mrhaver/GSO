/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aexbanner_week7;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 *
 * @author Frank Haver
 */
public class RMIServer {
    private static final int portNumber = 1099;
    private static final String bindingName = "Effectenbeurs";
    private Registry registry = null;
    private MockEffectenbeurs effectenbeurs = null;
    
    public RMIServer(){
        
        try {
            effectenbeurs = new MockEffectenbeurs();
        } catch (RemoteException ex) {
            effectenbeurs = null;
            System.out.println("Server: RemoteException: " + ex.getMessage());
        }
        
        try{
            registry = LocateRegistry.createRegistry(portNumber);
        } catch(RemoteException ex){
            registry = null;
            System.out.println("Server: RemoteException: " + ex.getMessage());
        }
        
        try {
            registry.rebind(bindingName, effectenbeurs);
        } catch (RemoteException ex) {
            System.out.println("Server: RemoteException: " + ex.getMessage());
        }
    }
    
    public static void main(String[] args) {

        System.out.println("SERVER USING REGISTRY");

        RMIServer server = new RMIServer();
    }
}
