package Server;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import Server.BasicPublisher;
import Server.Fonds;
import Server.IFonds;
import Shared.RemotePublisher;
import Shared.RemotePropertyListener;
import Shared.IEffectenBeurs;
import Shared.IEffectenBeurs;
import Shared.RemotePropertyListener;
import Shared.RemotePublisher;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author Frank
 */
public class MockEffectenbeurs extends UnicastRemoteObject implements IEffectenBeurs, RemotePublisher {

    private ArrayList<IFonds> koersen;
    private Timer t;
    private BasicPublisher basicPublisher;

    public MockEffectenbeurs() throws RemoteException {
        koersen = new ArrayList<>();
        koersen = getKoersen();
        
        basicPublisher = new BasicPublisher(new String[]{"koersen"});
        t = new Timer();
        
        t.schedule(new FluctuatieTask(), 0, 2000);

    }

    @Override
    public final ArrayList<IFonds> getKoersen() {
        Random random = new Random();
        double randomDouble = (double)Math.round((5 + (100 - 5) * random.nextDouble()) * 100d) / 100d;
        koersen.add(new Fonds("ASML",randomDouble));
        randomDouble = (double)Math.round((5 + (100 - 5) * random.nextDouble()) * 100d) / 100d;
        koersen.add(new Fonds("Shell",randomDouble));
        randomDouble = (double)Math.round((5 + (100 - 5) * random.nextDouble()) * 100d) / 100d;
        koersen.add(new Fonds("Philips",randomDouble));
        return koersen;
    }

    @Override
    public void addListener(RemotePropertyListener listener, String property) throws RemoteException {
        basicPublisher.addListener(listener, property);
    }

   @Override
    public void removeListener(RemotePropertyListener listener, String property) throws RemoteException {
        basicPublisher.removeListener(listener, property);
    }

        class FluctuatieTask extends TimerTask{

        @Override
        public void run() {
            // fluctueer de huidige koersen
            // verander met minimaal 0 en maximaal 5% van de koers
            // kan negatief of positief zijn.
            Random rnd = new Random(); 
            for(IFonds ifo : koersen){
                double rangeMin = 0;
                double rangeMax = ifo.getKoers()/20d;
                double randomDouble = rangeMin + (rangeMax - rangeMin) * rnd.nextDouble();
                int randomInt = 0 + (int)(Math.random() * ((1 - 0) + 1));
                randomDouble = (double)Math.round(randomDouble * 100) / 100d;
                double nieuwWaarde;
                if(randomInt == 0){
                    nieuwWaarde = (double)Math.round((ifo.getKoers() - randomDouble) *100) / 100d;
                }
                else{
                    nieuwWaarde = (double)Math.round((ifo.getKoers() + randomDouble) *100) / 100d;
                }
                ifo.setKoers(nieuwWaarde);                
            }
            basicPublisher.inform(this, "koersen", null, koersen);
        }  
    }

}
