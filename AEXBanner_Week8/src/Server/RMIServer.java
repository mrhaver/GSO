
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
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
    private MockEffectenbeurs mockeffectenbeurs = null;

    public RMIServer() {
       
        System.out.println("Server: Port number " + portNumber);
        
        try {
            mockeffectenbeurs = new MockEffectenbeurs();
            System.out.println("Server: Student administration created");
        } catch (RemoteException ex) {
            System.out.println("Server: Cannot create student administration");
            System.out.println("Server: RemoteException: " + ex.getMessage());
            mockeffectenbeurs = null;
        }

        // Create registry at port number
        try {
            registry = LocateRegistry.createRegistry(portNumber);
            System.out.println("Server: Registry created on port number " + portNumber);
        } catch (RemoteException ex) {
            System.out.println("Server: Cannot create registry");
            System.out.println("Server: RemoteException: " + ex.getMessage());
            registry = null;
        }

        // Bind student administration using registry
        try {
            registry.rebind(bindingName, mockeffectenbeurs);
        } catch (RemoteException ex) {
            System.out.println("Server: Cannot bind student administration");
            System.out.println("Server: RemoteException: " + ex.getMessage());
        }
    }
    
    public static void main(String[] args) {
        System.out.println("SERVER USING REGISTRY");
        
        
        RMIServer server = new RMIServer();
        
    }
    
}
