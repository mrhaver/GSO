package bank.internettoegang;

import FontysRMIListener.BasicPublisher;
import FontysRMIListener.RemotePropertyListener;
import FontysRMIListener.RemotePublisher;
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import bank.bankieren.*;
import fontys.util.InvalidSessionException;
import fontys.util.NumberDoesntExistException;
import internetbankieren.ICentraleBank;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Balie extends UnicastRemoteObject implements IBalie, RemotePublisher {

	private static final long serialVersionUID = -4194975069137290780L;
	private IBank bank;
	private HashMap<String, ILoginAccount> loginaccounts;
	private Collection<IBankiersessie> sessions;
	private java.util.Random random;
        private BasicPublisher publisher;
        private ICentraleBank centrale;

	public Balie(IBank bank, ICentraleBank centrale) throws RemoteException {
		this.bank = bank;
                this.centrale = centrale;
		loginaccounts = new HashMap<String, ILoginAccount>();
		sessions = new HashSet<IBankiersessie>();
		random = new Random();
                publisher = new BasicPublisher(new String[]{"balie"});

	}

        @Override
	public String openRekening(String naam, String plaats, String wachtwoord, int nieuwRekeningNummer) {
		if (naam.equals(""))
                    throw new IllegalArgumentException("Naam mag niet leeg zijn");	
		if (plaats.equals(""))
                    throw new IllegalArgumentException("Plaats mag niet leeg zijn");
		if (wachtwoord.length() < 4 || wachtwoord.length() > 8)
                    throw new IllegalArgumentException("Wachtwoord moet groter dan 4 en kleiner dan 8 karakters zijn");
		int nr = bank.openRekening(naam, plaats, nieuwRekeningNummer);
		if (nr == -1)
			return null;

                // Veranderd want dat is makkelijker om te testen
		String accountname = naam; //generateId(8);
//		while (loginaccounts.containsKey(accountname))
//			accountname = generateId(8);
		loginaccounts.put(accountname, new LoginAccount(accountname,
				wachtwoord, nr));
		return accountname;
	}

        @Override
	public IBankiersessie logIn(String accountnaam, String wachtwoord)
			throws RemoteException {
		ILoginAccount loginaccount = loginaccounts.get(accountnaam);
		if (loginaccount == null)
			return null;
		if (loginaccount.checkWachtwoord(wachtwoord)) {
			IBankiersessie sessie = new Bankiersessie(loginaccount
					.getReknr(), bank);
			sessions.add(sessie);
		 	return sessie;
		}
		else return null;
	}

    @Override
    public void addListener(RemotePropertyListener listener, String property) throws RemoteException {
        publisher.addListener(listener, property);
    }

    @Override
    public void removeListener(RemotePropertyListener listener, String property) throws RemoteException {
        publisher.removeListener(listener, property);
    }
    
    @Override
    public void informEigenRekeningen(int rekeningnummer) throws RemoteException {
        publisher.inform(this, "balie", null, rekeningnummer);
        System.out.println(this.bank.getName() + " Balie:\t rekeningnummer " + rekeningnummer + " op de hoogte stellen van verandering");
    }

    @Override
    public void informAndereRekeningen(String[] overmaak) throws RemoteException {
        centrale.maakOverRekening(overmaak);
        System.out.println(this.bank.getName() + " Balie:\t rekeningnummer zoeken via Centrale Bank");
    }
    
    @Override
    public int getNieuwRekeningNummer() throws RemoteException{
        return centrale.getNieuwRekeningNummer();
    }
    
    @Override
    public boolean maakOver(int rekeningnummer, Money money) {
        for(IBankiersessie s : sessions){
            try {
                if(s.getRekening().getNr() == rekeningnummer){
                    System.out.println(this.bank.getName() + " Balie:\t rekeningnummer gevonden overmaken via rekeningnummer");
                    return s.maakOverRekening(rekeningnummer, money);
                }
            } catch (InvalidSessionException | RemoteException ex) {
                Logger.getLogger(Balie.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }
}
