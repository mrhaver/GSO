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
   * @return IllegalArgumentException zodra naam of plaats een lege string of wachtwoord minder dan 
   * vier of meer dan acht karakters lang is en anders de gegenereerde 
   * accountnaam die de gebruiker heeft ingegeven
   */
  String openRekening(String naam, String plaats, String wachtwoord) throws RemoteException;

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
     * @param overmaak
   * @Author Frank Haver
   * Deze methode moet 'tegen de centrale zeggen' dat er naar een bepaald 
   * rekeningnummer overgemaakt moet worden.
   * @throws RemoteException 
   */
  void informAndereRekeningen(String[] overmaak) throws RemoteException;
  
  /**
     * @return 
     * @throws java.rmi.RemoteException
   * @Author Frank Haver
   * Als het binnenkomende rekeningnummer overeenkomt met een rekeningnummer 
   * van de balie dan moet het geld naar dit rekeningnummer worden overgemaakt
   * @param rekeningnummer
   * @param money
   * 
   */
  boolean maakOver(int rekeningnummer, Money money) throws RemoteException;
  
  /**
   * @Author Frank Haver
   * @param gelukt
   * @throws RemoteException 
   */
  void bevestigTransactie(String gelukt) throws RemoteException;

}

