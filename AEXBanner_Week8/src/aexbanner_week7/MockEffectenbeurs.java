/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aexbanner_week7;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Frank Haver
 */
public class MockEffectenbeurs extends UnicastRemoteObject implements IEffectenbeurs  {

    private ArrayList<IFonds> koersen;
    
    public MockEffectenbeurs() throws RemoteException{
        koersen = new ArrayList<>();
    }
    
    @Override
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
    
}
