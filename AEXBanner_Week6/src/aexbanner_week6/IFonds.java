/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aexbanner_week6;

/**
 *
 * @author Frank Haver
 */
public interface IFonds {
    /**
     * @Author Frank Haver
     * 
     * @return Geeft de naam terug vaan een beursgenoteerd bedrijf
     */
    public String getNaam();
    
    /**
     * @Author Frank Haver
     * 
     * @return Geeft de koers terug van een beursgenoteerd bedrijfs
     */
    public double getKoers();
    
    /**
     * @Author Frank Haver
     * Set the value of the koers
     * @param value the value of the new koers
     */
    public void setKoers(double value);
}
