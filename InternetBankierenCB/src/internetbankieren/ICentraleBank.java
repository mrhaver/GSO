/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package internetbankieren;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Frank Haver
 */

public interface ICentraleBank extends Remote{
    
    /**
     * @Author Frank Haver
     * Deze methode zoekt in alle bestaande balies naar de juiste 'sessie'
     * Waar het geld naar toe moet en maakt het geld naar deze sessie over
     * @return  true als het gelukt is
     *          false als het niet gelukt is
     * @throws RemoteException 
     */
    boolean maakOverRekening() throws RemoteException;
    
    /**
     * @Author Frank Haver
     * Deze methode stuurt naar alle luisterende balies een bericht
     * @throws RemoteException 
     */
    void informBalies() throws RemoteException;
}
