/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package internetbankieren;

import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Properties;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author Alex Ras
 */
public class CentraleBankServer extends Application {
    
    private final double MINIMUM_WINDOW_WIDTH = 600.0;
    private final double MINIMUM_WINDOW_HEIGHT = 200.0;
    private final int portNumber = 1100;
    private Registry registry;
    
    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();
        final Image image = new Image(this.getClass().getResourceAsStream("/plaatjes/Europese-centrale-bank.png"));
        Scene scene = new Scene(root, image.getWidth() + 30, image.getHeight() + 100 , Color.WHITE);
        
        GridPane gridpane = new GridPane();
        gridpane.setPadding(new Insets(5));
        gridpane.setHgap(10);
        gridpane.setVgap(10);
        
        final ImageView imv = new ImageView();
        
        imv.setImage(image);

        final HBox pictureRegion = new HBox();
        
        pictureRegion.getChildren().add(imv);
        gridpane.add(pictureRegion, 1, 1);
        
        
        root.getChildren().add(gridpane);        
        primaryStage.setScene(scene);
        primaryStage.show();

        startRegistry();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    private void startRegistry(){
        FileOutputStream out;
        // reset het poortnummer
        try{
            String address = java.net.InetAddress.getLocalHost().getHostAddress();
            int port = portNumber;
            Properties props = new Properties();
            String rmiCentraleBank = address + ":" + port + "/centraleBank";
            props.setProperty("port", String.valueOf(port));
            props.setProperty("centrale", rmiCentraleBank);
            out = new FileOutputStream("centraleBank.props");
            props.store(out, null);
            out.close();
            
            props = new Properties();
            props.setProperty("port", String.valueOf(portNumber+1));
            out = new FileOutputStream("Port.props");
            props.store(out, null);
            out.close();
            
            registry = LocateRegistry.createRegistry(port);
            ICentraleBank bank = new CentraleBank();
            registry.rebind(rmiCentraleBank, bank);
        } 
        catch(Exception ex)
        {
            System.out.println(ex.toString());
        }
       
    }
    
}
