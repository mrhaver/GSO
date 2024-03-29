package bank.bankieren;

import fontys.util.*;

import java.util.*;

public class Bank implements IBank {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8728841131739353765L;
	private Map<Integer,IRekeningTbvBank> accounts;
	private Collection<IKlant> clients;
	private String name;

	public Bank(String name) {
		accounts = new HashMap<Integer,IRekeningTbvBank>();
		clients = new ArrayList<IKlant>();	
		this.name = name;	
	}

        @Override
	public int openRekening(String name, String city, int nieuwRekeningNummer) {
		if (name.equals("") || city.equals(""))
			return -1;

		IKlant klant = getKlant(name, city);
		IRekeningTbvBank account = new Rekening(nieuwRekeningNummer, klant, Money.EURO);
		accounts.put(nieuwRekeningNummer,account);		
		return nieuwRekeningNummer;
	}

	private IKlant getKlant(String name, String city) {
		for (IKlant k : clients) {
			if (k.getNaam().equals(name) && k.getPlaats().equals(city))
				return k;
		}
		IKlant klant = new Klant(name, city);
		clients.add(klant);
		return klant;
	}

        @Override
	public IRekening getRekening(int nr) {
		return accounts.get(nr);
	}

        @Override
	public boolean maakOver(int source, int destination, Money money)
			throws NumberDoesntExistException {
		if (source == destination)
			throw new RuntimeException(
					"cannot transfer money to your own account");
		if (!money.isPositive())
			throw new RuntimeException("money must be positive");

		IRekeningTbvBank source_account = (IRekeningTbvBank) getRekening(source);
		if (source_account == null)
			throw new NumberDoesntExistException("account " + source
					+ " unknown at " + name);
		IRekeningTbvBank dest_account = (IRekeningTbvBank) getRekening(destination);
		if (dest_account == null) 
//			throw new NumberDoesntExistException("account " + destination
//					+ " unknown at " + name);
                    return false;
                
                Money negative = Money.difference(new Money(0, money.getCurrency()),
				money);
		boolean success = source_account.muteer(negative);
		if (!success)
			return false;
		success = dest_account.muteer(money);

		if (!success) // rollback
			source_account.muteer(money);
		return success;
	}
        
        @Override
        public boolean maakOverRekening(int destination, Money money){
            IRekeningTbvBank dest_account = (IRekeningTbvBank) getRekening(destination);
		if (dest_account == null) {
                     return false;
                }
                if (money == null){
                    return false;
                }
                System.out.println(this.name + " Bank:\t " + " rekening gewijzigd met €" + money.getValue());
                return dest_account.muteer(money);
                   
        }

	@Override
	public String getName() {
		return name;
	}

}
