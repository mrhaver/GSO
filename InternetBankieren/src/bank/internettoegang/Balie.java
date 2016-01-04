package bank.internettoegang;

import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import bank.bankieren.*;

public class Balie extends UnicastRemoteObject implements IBalie {

	private static final long serialVersionUID = -4194975069137290780L;
	private IBank bank;
	private HashMap<String, ILoginAccount> loginaccounts;
	//private Collection<IBankiersessie> sessions;
	private java.util.Random random;

	public Balie(IBank bank) throws RemoteException {
		this.bank = bank;
		loginaccounts = new HashMap<String, ILoginAccount>();
		//sessions = new HashSet<IBankiersessie>();
		random = new Random();
                
                // Accounts for Testing reasons
                openRekening("Frank", "Veghel", "Frank");
                openRekening("Haver", "Veghel", "Haver");               
	}

        @Override
	public String openRekening(String naam, String plaats, String wachtwoord) {
		if (naam.equals(""))
                    throw new IllegalArgumentException("Naam mag niet leeg zijn");	
		if (plaats.equals(""))
                    throw new IllegalArgumentException("Plaats mag niet leeg zijn");
		if (wachtwoord.length() < 4 || wachtwoord.length() > 8)
                    throw new IllegalArgumentException("Wachtwoord moet groter dan 4 en kleiner dan 8 karakters zijn");
		int nr = bank.openRekening(naam, plaats);
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
			
		 	return sessie;
		}
		else return null;
	}

	private static final String CHARS = "abcdefghijklmnopqrstuvwxyz0123456789";

        // wordt in de testversie niet gebruikt is onhandig
        // ZIE CONSTRUCTOR
//	private String generateId(int x) {
//		StringBuilder s = new StringBuilder();
//		for (int i = 0; i < x; i++) {
//			int rand = random.nextInt(36);
//			s.append(CHARS.charAt(rand));
//		}
//		return s.toString();
//	}


}
