/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank.server;

import bank.bankieren.Bank;
import bank.gui.BankierClient;
import bank.internettoegang.Balie;
import bank.internettoegang.IBalie;
import internetbankieren.ICentraleBank;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author frankcoenen
 */
public class BalieServer extends Application {

    private Stage stage;
    private final double MINIMUM_WINDOW_WIDTH = 600.0;
    private final double MINIMUM_WINDOW_HEIGHT = 200.0;
    private String nameBank;
    private Registry serverRegistry;
    private Registry clientRegistry;
    private IBalie balie;
    private ICentraleBank centrale;
    private BalieController bankSelect;

    @Override
    public void start(Stage primaryStage) throws IOException {

        try {
            stage = primaryStage;
            stage.setTitle("Bankieren");
            stage.setMinWidth(MINIMUM_WINDOW_WIDTH);
            stage.setMinHeight(MINIMUM_WINDOW_HEIGHT);
            gotoBankSelect();
            primaryStage.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public boolean startBalie(String nameBank) {
            FileOutputStream out = null;
            String sPort;      
            try {
                // pak de nieuwe vrije poort
                FileInputStream in = new FileInputStream("Port.props");
                Properties props = new Properties();
                props.load(in);
                sPort = props.getProperty("port");
                in.close();
                
                this.nameBank = nameBank;
                String address = java.net.InetAddress.getLocalHost().getHostAddress();
                int port = Integer.parseInt(sPort);
                props = new Properties();
                String rmiBalie = address + ":" + port + "/" + nameBank;
                props.setProperty("port", String.valueOf(port));
                props.setProperty("balie", rmiBalie);
                out = new FileOutputStream(nameBank + ".props");
                props.store(out, null);
                out.close();
                
                props = new Properties();
                props.setProperty("port", String.valueOf(port + 1));
                out = new FileOutputStream("Port.props");
                props.store(out, null);
                out.close();
                
                serverRegistry = LocateRegistry.createRegistry(port);
                balie = new Balie(new Bank(nameBank), centrale);
                bankSelect.setBalie(balie);
                serverRegistry.rebind(rmiBalie, balie);  
                return true;

            } catch (IOException ex) {
                Logger.getLogger(BalieServer.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    out.close();
                } catch (IOException ex) {
                    Logger.getLogger(BalieServer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            return false;
    }
    
    public ICentraleBank connectToCentrale(){
        int port;
         try {
            FileInputStream in = new FileInputStream("centraleBank.props");
            Properties props = new Properties();
            props.load(in);
            String sPort = props.getProperty("port");
            String rmiBalie = props.getProperty("centrale");
            port = Integer.parseInt(sPort);
            in.close();

            clientRegistry = LocateRegistry.getRegistry(java.net.InetAddress.getLocalHost().getHostAddress(), port);
            centrale = (ICentraleBank) clientRegistry.lookup(rmiBalie);  
                        return centrale;
                       
            } catch (Exception exc) {
                exc.printStackTrace();
                return null;
            }
    }

    public void gotoBankSelect() {
        try {
            bankSelect = (BalieController) replaceSceneContent("Balie.fxml");
            bankSelect.setApp(this, connectToCentrale());
        } catch (Exception ex) {
            Logger.getLogger(BankierClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Initializable replaceSceneContent(String fxml) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        InputStream in = BalieServer.class.getResourceAsStream(fxml);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(BalieServer.class.getResource(fxml));
        AnchorPane page;
        try {
            page = (AnchorPane) loader.load(in);
        } finally {
            in.close();
        }
        Scene scene = new Scene(page, 800, 600);
        stage.setScene(scene);
        stage.sizeToScene();
        return (Initializable) loader.getController();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
