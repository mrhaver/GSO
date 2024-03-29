package bank.internettoegang;

import FontysRMIListener.BasicPublisher;
import FontysRMIListener.RemotePropertyListener;
import FontysRMIListener.RemotePublisher;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import bank.bankieren.IBank;
import bank.bankieren.IRekening;
import bank.bankieren.Money;

import fontys.util.InvalidSessionException;
import fontys.util.NumberDoesntExistException;

public class Bankiersessie extends UnicastRemoteObject implements IBankiersessie, RemotePublisher {

	private static final long serialVersionUID = 1L;
	private long laatsteAanroep;
	private int reknr;
	private IBank bank;
        private BasicPublisher publisher;

	public Bankiersessie(int reknr, IBank bank) throws RemoteException {
		laatsteAanroep = System.currentTimeMillis();
		this.reknr = reknr;
		this.bank = bank;
                publisher = new BasicPublisher(new String[]{"sessie"});
	}

	public boolean isGeldig() {
            return System.currentTimeMillis() - laatsteAanroep < GELDIGHEIDSDUUR;
	}

	@Override
	public boolean maakOver(int bestemming, Money bedrag)
			throws NumberDoesntExistException, InvalidSessionException,
			RemoteException {
		
		updateLaatsteAanroep();
		
		if (reknr == bestemming)
			throw new RuntimeException(
					"source and destination must be different");
		if (!bedrag.isPositive())
			throw new RuntimeException("amount must be positive");
                
		return bank.maakOver(reknr, bestemming, bedrag);
	}
        
        @Override
        public boolean maakOverRekening(int bestemming, Money bedrag){
            System.out.println(this.bank.getName() + " Sessie:\t €" + bedrag.getValue() + " overmaken naar " + bestemming);
            return bank.maakOverRekening(bestemming, bedrag);
        }

	private void updateLaatsteAanroep() throws InvalidSessionException {
		if (!isGeldig()) {
			throw new InvalidSessionException("session has been expired");
		}
		
		laatsteAanroep = System.currentTimeMillis();
	}

	@Override
	public IRekening getRekening() throws InvalidSessionException,
			RemoteException {

		updateLaatsteAanroep();
		return bank.getRekening(reknr);
	}

	@Override
	public void logUit() throws RemoteException {
            //UnicastRemoteObject.unexportObject(this, true);
	}

    @Override
    public void addListener(RemotePropertyListener listener, String property) throws RemoteException {
        publisher.addListener(listener, property);
    }

    @Override
    public void removeListener(RemotePropertyListener listener, String property) throws RemoteException {
        publisher.removeListener(listener, property);
    }

}
