package bank.internettoegang;

import bank.bankieren.Money;
import java.rmi.*;


public interface IBalie extends Remote {
  /**
   * creatie van een nieuwe bankrekening; het gegenereerde bankrekeningnummer is
   * identificerend voor de nieuwe bankrekening en heeft een saldo van 0 euro
   * @param naam van de eigenaar van de nieuwe bankrekening
   * @param plaats de woonplaats van de eigenaar van de nieuwe bankrekening
   * @param wachtwoord van het account waarmee er toegang kan worden verkregen 
   * tot de nieuwe bankrekening
   * @param nieuwRekeningNummer een volgend vrij rekeningnummer die van de centrale is gehaald
   * @return IllegalArgumentException zodra naam of plaats een lege string of wachtwoord minder dan 
   * vier of meer dan acht karakters lang is en anders de gegenereerde 
   * accountnaam die de gebruiker heeft ingegeven
   */
  String openRekening(String naam, String plaats, String wachtwoord, int nieuwRekeningNummer) throws RemoteException;

  /**
   * er wordt een sessie opgestart voor het login-account met de naam
   * accountnaam mits het wachtwoord correct is
   * @param accountnaam
   * @param wachtwoord
   * @return de gegenereerde sessie waarbinnen de gebruiker 
   * toegang krijgt tot de bankrekening die hoort bij het betreffende login-
   * account mits accountnaam en wachtwoord matchen, anders null
   */
  IBankiersessie logIn(String accountnaam, String wachtwoord) throws RemoteException;
  
  /**
   * @Author Frank Haver
   * Deze methode informeert listeners van de balie dus alle listeners krijgen
   * deze inform. In de inform wordt de rekening gestuurd als dit rekeningnummer
   * overeenkomt met het rekeningnummer van een bepaalde klant die listening is.
   * dan krijgt deze klant een melding dat zijn saldo is aangepast.
   * @param rekeningnummer
   * @throws RemoteException 
   */
  void informEigenRekeningen(int rekeningnummer) throws RemoteException;
  
  /**

   * @Author Frank Haver
   * Deze methode moet 'tegen de centrale zeggen' dat er naar een bepaald 
   * rekeningnummer overgemaakt moet worden met een bepaald bedrag
   * @param overmaak 
   *    bestaat uit 2 strings
   *    eerste string is een rekeningnummer van 9 cijfers
   *    tweede string moet om kunnen worden gezet naar een integer
   * @throws RemoteException 
   */
  void informAndereRekeningen(String[] overmaak) throws RemoteException;
  
  /**
   * @Author Frank Haver
   * Als het binnenkomende rekeningnummer overeenkomt met een rekeningnummer 
   * van de balie dan moet het geld naar dit rekeningnummer worden overgemaakt
   * @param rekeningnummer rekeningnummer van 9 cijfers
   * @param money money object, kan vanalles zijn
   * @return true als het overmaken is gelukt fout als het overmaken is mislukt
   * @throws RemoteException
   */
  boolean maakOver(int rekeningnummer, Money money) throws RemoteException;
  
  /**
   * @Author Frank Haver
   * pakt een nieuw vrij rekeningnummer van de centrale
   * @return retourneert het nieuwe rekeningnummer
   * @throws RemoteException 
   */
  int getNieuwRekeningNummer() throws RemoteException;
  


}

