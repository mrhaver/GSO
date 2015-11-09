package AEX;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.Serializable;

/**
 *
 * @author Frank Haver
 */
public class Fonds implements IFonds, Serializable {

    private String naam;
    private double koers;

    

    public Fonds(String naam, double koers) {
        this.naam = naam;
        this.koers = koers;
    }

    @Override
    public String getNaam() {
        return naam;
    }

    @Override
    public Double getKoers() {
        return koers;
    }
    
    @Override
    public void setKoers(double koers) {
        this.koers = koers;
    }
    
    @Override
    public String toString() {
        return naam + " " + koers;
    }

}
