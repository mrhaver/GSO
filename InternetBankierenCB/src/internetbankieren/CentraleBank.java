/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package internetbankieren;

import FontysRMIListener.BasicPublisher;
import FontysRMIListener.RemotePropertyListener;
import FontysRMIListener.RemotePublisher;
import bank.bankieren.Money;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author Frank Haver
 */
public class CentraleBank extends UnicastRemoteObject implements ICentraleBank, RemotePublisher{

    private BasicPublisher publisher;
    
    public CentraleBank() throws RemoteException{
        publisher = new BasicPublisher(new String[]{"centrale"});
    }
    
//    @Override
//    public void informBalies(String bericht) throws RemoteException{
//        publisher.inform(this, "centrale", this, bericht);
//    }
    
    @Override
    public void maakOverRekening(String[] overmaak) throws RemoteException {
        publisher.inform(this, "centrale", this, overmaak);
        System.out.println("Centrale:\t balies informen over " + overmaak[0] + " met €" + new Money(Long.parseLong(overmaak[1]), Money.EURO).getValue());
    }

    @Override
    public void addListener(RemotePropertyListener listener, String property) throws RemoteException {
        publisher.addListener(listener, property);
    }

    @Override
    public void removeListener(RemotePropertyListener listener, String property) throws RemoteException {
        publisher.removeListener(listener, property);
    }


    
}
