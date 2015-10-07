/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aexbanner_week6;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @Author Frank Haver
 */
public class BannerController {

    private AEXBanner banner;
    private MockEffectenbeurs effectenbeurs;
    private Timer pollingTimer;
    private Timer fluctuatieTimer;
    private ArrayList<IFonds> huidigeKoersen;

    public BannerController(AEXBanner banner) {

        this.banner = banner;
        this.effectenbeurs = new MockEffectenbeurs();
        huidigeKoersen = effectenbeurs.getKoersen();
        
        // Start polling timer: update banner every two seconds
        pollingTimer = new Timer();
        
        fluctuatieTimer = new Timer();
        fluctuatieTimer.schedule(new FluctuatieTask(), 0,2000);
        // TODO 
    }
    
    class FluctuatieTask extends TimerTask{

        @Override
        public void run() {
            // fluctueer de huidige koersen
            // verander met minimaal 0 en maximaal 5% van de koers
            // kan negatief of positief zijn.
            Random rnd = new Random(); 
            for(IFonds ifo : huidigeKoersen){
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
            
            banner.setKoersen(getHuidigeKoersString());
        }
        
    }
    
    public String getHuidigeKoersString(){
        String koersen = "";
        for(IFonds i : huidigeKoersen){
             koersen = koersen + i.getNaam() + ": " + String.valueOf(i.getKoers()) + " ";
        }                
        return koersen;
    }

    // Stop banner controller
    public void stop() {
        pollingTimer.cancel();
        // Stop simulation timer of effectenbeurs
        // TODO
        fluctuatieTimer.cancel();
    }
}

