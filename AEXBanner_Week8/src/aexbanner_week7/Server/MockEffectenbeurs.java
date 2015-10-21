/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aexbanner_week7.Server;

import aexbanner_week7.Shared.IEffectenbeurs;
import aexbanner_week7.Server.IFonds;
import aexbanner_week7.Server.Fonds;
import aexbanner_week7.Shared.BasicPublisher;
import aexbanner_week7.Shared.RemotePropertyListener;
import aexbanner_week7.Shared.RemotePublisher;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Frank Haver
 */
public class MockEffectenbeurs extends UnicastRemoteObject implements RemotePublisher  {

    private ArrayList<IFonds> koersen;
    private BasicPublisher basicPublisher;
    
    public MockEffectenbeurs() throws RemoteException{
        koersen = new ArrayList<>();
    }
    
    public ArrayList<IFonds> getKoersen() throws RemoteException{
        Random random = new Random();
        double randomDouble = (double)Math.round((5 + (100 - 5) * random.nextDouble()) * 100d) / 100d;
        koersen.add(new Fonds("ASML",randomDouble));
        randomDouble = (double)Math.round((5 + (100 - 5) * random.nextDouble()) * 100d) / 100d;
        koersen.add(new Fonds("Shell",randomDouble));
        randomDouble = (double)Math.round((5 + (100 - 5) * random.nextDouble()) * 100d) / 100d;
        koersen.add(new Fonds("Philips",randomDouble));
        return koersen;
    }

    @Override
    public void addListener(RemotePropertyListener listener, String property) throws RemoteException {
        basicPublisher.addListener(listener, property);
    }

    @Override
    public void removeListener(RemotePropertyListener listener, String property) throws RemoteException {
        basicPublisher.removeListener(listener, property);
    }
    
}
