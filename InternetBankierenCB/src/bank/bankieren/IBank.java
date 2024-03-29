package bank.bankieren;

import fontys.util.*;

/**
 * @author 871059
 * 
 */
public interface IBank {

    /**
     * creatie van een nieuwe bankrekening met een identificerend rekeningnummer; 
     * alleen als de klant, geidentificeerd door naam en plaats, nog niet bestaat 
     * wordt er ook een nieuwe klant aangemaakt
     * 
     * @param naam
     *            van de eigenaar van de nieuwe bankrekening
     * @param plaats
     *            de woonplaats van de eigenaar van de nieuwe bankrekening
     * @param nieuwRekeningNummer
     *            nieuw vrij rekeningnummer komt van de centrale af
     * @return -1 zodra naam of plaats een lege string en anders het nummer van de
     *         gecreeerde bankrekening
     */
    int openRekening(String naam, String plaats, int nieuwRekeningNummer);

    /**
     * er wordt bedrag overgemaakt van de bankrekening met nummer bron naar de
     * bankrekening met nummer bestemming, mits het afschrijven van het bedrag
     * van de rekening met nr bron niet lager wordt dan de kredietlimiet van deze
     * rekening 
     * 
     * @param bron
     * @param bestemming
     *            ongelijk aan bron
     * @param bedrag
     *            is groter dan 0
     * @return <b>true</b> als de overmaking is gelukt, anders <b>false</b>
     * @throws NumberDoesntExistException
     *             als een van de twee bankrekeningnummers onbekend is
     */
    boolean maakOver(int bron, int bestemming, Money bedrag)
            throws NumberDoesntExistException;

    /**
     * @Author Frank Haver
     * maakt een bepaald bedrag over naar een bepaalde rekening
     * @param bestemming is een rekeningnummer van deze bank, dit is al gecontroleerd
     * @param bedrag het bedrag dat overgemaakt moet worden dit mag niet null zijn
     * @return  true als het is gelukt
     *          false als het is mislukt
     */
    boolean maakOverRekening(int bestemming, Money bedrag);
    
    /**
     * @param nr
     * @return de bankrekening met nummer nr mits bij deze bank bekend, anders null
     */
    IRekening getRekening(int nr);

    /**
     * @return de naam van deze bank
     */
    String getName();
}
