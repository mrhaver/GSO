package Client;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import Shared.IEffectenBeurs;
import Server.IFonds;
import Shared.RemotePropertyListener;
import Shared.RemotePublisher;
import java.beans.PropertyChangeEvent;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 *
 * @author Frank Haver
 */
public class BannerController extends UnicastRemoteObject implements RemotePropertyListener {

    private AEXBanner banner;
    private Registry registry = null;
    private final String ipAddress = "localhost";
    private final int portNumber = 1099;
    private IEffectenBeurs effectenbeurs = null;
    private static final String bindingName = "Effectenbeurs";
    private ArrayList<IFonds> huidigeKoersen;

    public BannerController(AEXBanner banner) throws RemoteException {

        this.banner = banner;

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

        // creating effectenbeurs with lookup for registry
        if (registry != null) {
            try {
                effectenbeurs = (IEffectenBeurs) registry.lookup(bindingName);
                ((RemotePublisher)effectenbeurs).addListener(this, "koersen");
            } catch (RemoteException ex) {
                System.out.println("Client: Cannot bind student administration");
                System.out.println("Client: RemoteException: " + ex.getMessage());
                effectenbeurs = null;
            } catch (NotBoundException ex) {
                System.out.println("Client: Cannot bind student administration");
                System.out.println("Client: NotBoundException: " + ex.getMessage());
                effectenbeurs = null;
            }
        }

        // Print result binding
        if (effectenbeurs != null) {
            System.out.println("Client: Student administration bound");
        } else {
            System.out.println("Client: Student administration is null pointer");
        }

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) throws RemoteException {
        huidigeKoersen = (ArrayList<IFonds>) evt.getNewValue();

        if (huidigeKoersen != null) {
            String koersen = "";
           
            for (IFonds f : huidigeKoersen) {
                koersen += f.getNaam() + " " + f.getKoers() + " ";
            }
            banner.setKoersen(koersen);
        } else {
            System.out.println("Null");
        }

    }
}
