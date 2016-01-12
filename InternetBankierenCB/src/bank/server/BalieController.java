/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bank.server;

import FontysRMIListener.RemotePropertyListener;
import FontysRMIListener.RemotePublisher;
import bank.bankieren.Bank;
import bank.bankieren.Money;
import bank.gui.BankierClient;
import bank.internettoegang.Balie;
import bank.internettoegang.IBalie;
import internetbankieren.ICentraleBank;
import java.beans.PropertyChangeEvent;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

/**
 * FXML Controller class
 *
 * @author frankcoenen
 */
public class BalieController extends UnicastRemoteObject implements Initializable, RemotePropertyListener {
    
    @FXML
    private ComboBox<String> cbSelectBank1;
    
    @FXML
    private TextArea taMessage;
    
    private BalieServer application;
    private String bankNaam;
    private ICentraleBank centrale;
    private IBalie balie;
    private RemotePublisher remoteCentrale;
     
    public void setApp(BalieServer application, ICentraleBank centrale) throws RemoteException{
        this.application = application;
        this.centrale = centrale;
        remoteCentrale = (RemotePublisher) centrale;
        remoteCentrale.addListener(this, "centrale");        
    }
    
    public BalieController() throws RemoteException{
        
    }
    

    public void setBalie(IBalie balie) {
        this.balie = balie;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        cbSelectBank1.getItems().addAll(FXCollections.observableArrayList("RaboBank", "ING", "SNS", "ABN AMRO", "ASN"));
        
        cbSelectBank1.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue ov, Object t, Object t1) {
                bankNaam = (String) ov.getValue();
                if (application.startBalie(bankNaam)) {
                    taMessage.setText(bankNaam + " bank is online");
                } else {
                    taMessage.setText("Connection Failed");
                }
            }
        }
        );
    }  
   
    @FXML
    private void selectBank(ActionEvent event) {
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) throws RemoteException {
        try{
            if(evt.getNewValue() instanceof String[]){
                String[] overmaak = (String[])evt.getNewValue();
                int rekeningnummer = Integer.parseInt(overmaak[0]);
                long cents = Long.parseLong(overmaak[1]);
                Money money = new Money(cents, Money.EURO);
                if(balie.maakOver(rekeningnummer, money)){
                    System.out.println(this.bankNaam + " Baliectrl:\t €" + money.getValue() + " overgemaakt naar " + rekeningnummer);
                    balie.informEigenRekeningen(rekeningnummer);
                }
            }
        }
        catch(RemoteException | NumberFormatException ex){
            ex.toString();
        }
        
    }
    
    public boolean isInteger(String s, int radix) {
    if(s.isEmpty()) return false;
    for(int i = 0; i < s.length(); i++) {
        if(i == 0 && s.charAt(i) == '-') {
            if(s.length() == 1) return false;
            else continue;
        }
        if(Character.digit(s.charAt(i),radix) < 0) return false;
    }
    return true;
}
}
   
