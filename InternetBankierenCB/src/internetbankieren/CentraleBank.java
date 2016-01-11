/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package internetbankieren;

import java.rmi.RemoteException;

/**
 *
 * @author Frank Haver
 */
public class CentraleBank implements ICentraleBank{

    public CentraleBank(){
        
    }
    
    @Override
    public boolean maakOverRekening() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
