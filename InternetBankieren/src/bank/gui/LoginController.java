/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank.gui;

import bank.bankieren.IRekening;
import bank.internettoegang.IBalie;
import bank.internettoegang.IBankiersessie;
import fontys.util.InvalidSessionException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * FXML Controller class
 *
 * @author frankcoenen
 */
public class LoginController implements Initializable {

    @FXML
    private TextField tfAccount;
    @FXML
    private TextField tfPassword;
    @FXML
    private Button btLogin;
    @FXML
    private Button btAccount;
    @FXML
    private TextArea taMessages;

    private IBalie balie;
    private IBankiersessie sessie;
    private BankierClient application;

    public void setApp(BankierClient application, IBalie balie, String AccountName) {
        this.balie = balie;
        this.application = application;
        this.tfAccount.setText(AccountName);
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        taMessages.setText("");
        tfAccount.setPromptText("Acccount");
        tfPassword.setPromptText("Wachtwoord");
        tfPassword.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode().equals(KeyCode.ENTER)) {
                    try {
                        login(null);
                    } catch (InvalidSessionException ex) {
                        Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
    }

    @FXML
    private void login(ActionEvent event) throws InvalidSessionException {
        try {
            sessie = balie.logIn(tfAccount.getText(), tfPassword.getText());
            if (sessie == null) {
                taMessages.setText("accountname or password not correct");
            } else {
                IRekening r = sessie.getRekening();
                System.out.println("Rekening" + r.getSaldo());
                application.gotoBankierSessie(balie, sessie);
            }
        } catch (RemoteException e1) {
            taMessages.setText("bad connection with counter");
            e1.printStackTrace();
        }
    }

    @FXML
    private void openAccount(ActionEvent event) {
        application.gotoOpenRekening(balie);
    }

}
