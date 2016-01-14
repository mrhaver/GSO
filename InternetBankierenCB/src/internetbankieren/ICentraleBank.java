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
     * methode stuurt een string array naar alle luisterende balies met de 
     * waarde van het rekening nummer en de hoeveelheid centen
     * @param overmaak bevat 2 string objecten
     *      eerste string object kan omgezet worden naar een integer en heeft een lengte van 9
     *      tweede string object kan omgezet worden naar een integer
     */
    void maakOverRekening(String[] overmaak) throws RemoteException;
    
    /**
     * @Author Frank Haver
     * methode stuurt een vrij rekeningnummer op en verhoogt het huidige 
     * nieuwe rekeningnummer
     * @return retourneert het nieuwe rekeningnummer
     * @throws RemoteException 
     */
    int getNieuwRekeningNummer() throws RemoteException;
}
