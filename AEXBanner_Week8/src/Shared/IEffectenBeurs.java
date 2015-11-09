package Shared;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import Server.IFonds;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 *
 * @author Frank Haver
 */
public interface IEffectenBeurs extends Remote {
    
    public ArrayList<IFonds> getKoersen() throws RemoteException;
}
