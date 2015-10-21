/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aexbanner_week7;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alex Ras
 */
public interface IEffectenbeurs extends Remote{
    
    public ArrayList<IFonds> getKoersen() throws RemoteException;
}
