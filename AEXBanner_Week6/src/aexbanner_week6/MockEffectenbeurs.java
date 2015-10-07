/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aexbanner_week6;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Frank Haver
 */
public class MockEffectenbeurs implements IEffectenbeurs{

    public MockEffectenbeurs(){
        
    }
    @Override
    public ArrayList<IFonds> getKoersen() {
        Random random = new Random();
        double randomDouble = (double)Math.round((5 + (100 - 5) * random.nextDouble()) * 100d) / 100d;
        ArrayList<IFonds> fonds = new ArrayList<>();
        fonds.add(new Fonds("ASML",randomDouble));
        randomDouble = (double)Math.round((5 + (100 - 5) * random.nextDouble()) * 100d) / 100d;
        fonds.add(new Fonds("Shell",randomDouble));
        randomDouble = (double)Math.round((5 + (100 - 5) * random.nextDouble()) * 100d) / 100d;
        fonds.add(new Fonds("Philips",randomDouble));
        return fonds;
    }
    
}
