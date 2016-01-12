/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank.gui;

import FontysRMIListener.RemotePropertyListener;
import FontysRMIListener.RemotePublisher;
import bank.bankieren.IRekening;
import bank.bankieren.Money;
import bank.internettoegang.IBalie;
import bank.internettoegang.IBankiersessie;
import fontys.util.InvalidSessionException;
import fontys.util.NumberDoesntExistException;
import java.beans.PropertyChangeEvent;
import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * FXML Controller class
 *
 * @author frankcoenen
 * 
 * Frank Haver: Hier moet uiteindelijk het bericht ontvangen 
 * worden dat de rekening die open staat is veranderd. Op dat
 * moment moet het bedrag worden aangepast.
 */
public class BankierSessieController extends UnicastRemoteObject implements Initializable, RemotePropertyListener {

    @FXML
    private Hyperlink hlLogout;
    @FXML
    private TextField tfNameCity;
    @FXML
    private TextField tfAccountNr;
    @FXML
    private TextField tfBalance;
    @FXML
    private TextField tfAmount;
    @FXML
    private TextField tfToAccountNr;
    @FXML
    private Button btTransfer;
    @FXML

    private TextArea taMessage;

    private BankierClient application;
    private IBalie balie;
    private IBankiersessie sessie;
    RemotePublisher remoteBalie;
    RemotePublisher remoteSessie;
    private String sessieGebruiker;

    /**
     * @Author Frank Haver
     * Er moet nog een listener gezet worden op het juiste remote object
     * deze komt van de BankierClient klasse af want hier moet een remotepublisher
     * object opgehaald worden en daar wordt de listener op gezet.
     * 
     * elke keer als er dan een inform komt op dat remote object dan komt er
     * een bericht binnen bij de propertychange methode met een bepaald(e) waarde / object
     * @throws RemoteException 
     */
    public BankierSessieController() throws RemoteException{
        
    }
    
    public void setApp(BankierClient application, IBalie balie, IBankiersessie sessie) throws RemoteException, InvalidSessionException {
        this.balie = balie;
        // Frank: hier wordt een listener toegevoegd aan het balie object.
        remoteBalie = (RemotePublisher) balie;
        remoteBalie.addListener(this, "balie");
        remoteSessie = (RemotePublisher) sessie;
        remoteSessie.addListener(this, "sessie");
        sessieGebruiker = sessie.getRekening().getEigenaar().getNaam();
        this.sessie = sessie;
        this.application = application;
        IRekening rekening = null;
        try {
            rekening = sessie.getRekening();
            tfAccountNr.setText(rekening.getNr() + "");
            tfBalance.setText(rekening.getSaldo() + "");
            String eigenaar = rekening.getEigenaar().getNaam() + " te "
                    + rekening.getEigenaar().getPlaats();
            tfNameCity.setText(eigenaar);
        } catch (InvalidSessionException ex) {
            taMessage.setText("bankiersessie is verlopen");
            Logger.getLogger(BankierSessieController.class.getName()).log(Level.SEVERE, null, ex);

        } catch (RemoteException ex) {
            taMessage.setText("verbinding verbroken");
            Logger.getLogger(BankierSessieController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tfAmount.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode().equals(KeyCode.ENTER)) {                   
                    transfer(null);                   
                }
            }
        });
        tfToAccountNr.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode().equals(KeyCode.ENTER)) {                   
                    transfer(null);                   
                }
            }
        });
    }

    @FXML
    private void logout(ActionEvent event) throws InvalidSessionException {
        try {
            sessie.logUit();
            remoteBalie.removeListener(this, "balie");
            remoteSessie.removeListener(this, "sessie");
            application.gotoLogin(balie, "");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void transfer(ActionEvent event) {
        try {
            int from = Integer.parseInt(tfAccountNr.getText());
            int to = Integer.parseInt(tfToAccountNr.getText());
            if (from == to) {
                taMessage.setText("can't transfer money to your own account");
            }
            String bedrag = tfAmount.getText();
            bedrag = bedrag.replace(",", ".");
            long centen = (long) (Double.parseDouble(bedrag) * 100);
            boolean overgemaakt = sessie.maakOver(to, new Money(centen, Money.EURO));
            // Frank: Als er overgemaakt is dan was het een rekening van dezelfde balie
            // anders dan zou het nog mogelijk zijn dat de rekening zich op een van 
            // de andere balies bevind.
            if(overgemaakt){
                tfBalance.setText(sessie.getRekening().getSaldo() + "");
                balie.informEigenRekeningen(to);
            } else{
                balie.informAndereRekeningen(new String[]{String.valueOf(to), String.valueOf(centen)});
                sessie.maakOverRekening(from, new Money(-centen, Money.EURO));
                tfBalance.setText(sessie.getRekening().getSaldo() + "");
            }

            
        } catch (RemoteException e1) {
            e1.printStackTrace();
            taMessage.setText("verbinding verbroken");
        } catch (NumberDoesntExistException | InvalidSessionException e1) {
            e1.printStackTrace();
            taMessage.setText(e1.getMessage());
        }
    }
    
    /**
     * @Author Frank Haver
     * Hier moet uiteindelijk het bericht ontvangen 
     * worden dat de rekening die open staat is veranderd. Op dat
     * moment moet het bedrag worden aangepast.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) throws RemoteException {
        int reknr = (int)evt.getNewValue();
        try {
            if(reknr == sessie.getRekening().getNr()){
                tfBalance.setText(sessie.getRekening().getSaldo() + "");
            }
        } catch (InvalidSessionException ex) {
            Logger.getLogger(BankierSessieController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
