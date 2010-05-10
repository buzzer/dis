package core;

//import java.util.ArrayList;
import java.util.HashSet;
import java.util.Collection;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import ui.KonsolenUI;
import data.Bank;
import data.Darlehen;
import data.Darlehensnehmer;
import data.Immobilie;
import data.Lebensversicherung;
import data.Privatperson;
import data.Unternehmen;
import data.Versicherungsunternehmen;

public class Kreditvergabe {

	// Hibernate Session
	private SessionFactory _sessionFactory;

	// GUI
	private KonsolenUI ui;

	public Kreditvergabe() {
		// Sessionfactory erzeugen
		_sessionFactory = new Configuration().configure().buildSessionFactory();

		// Anwendung initialisieren
		//darlehensnehmer  = new HashSet<Darlehensnehmer>();
		
		/****** Initialisierungen (Evtl. später entfernen, wenn die Daten in der DB stehen) ****/
		
		// Hybernate session holen
		Session session = _sessionFactory.getCurrentSession();
		// Transaktion starten
		session.beginTransaction();
		
		/*//Banken
		Bank b1 = new Bank("Komerzbank", "Musterstr. 1", 11111, "Berlin", "GmbH", 100000.0);
		Bank b2 = new Bank("Volkskasse", "Holzweg 7", 98765, "Nürnberg", "GBR", 300000.0);
		Bank b3 = new Bank("Briefbank", "Geldstr. 14", 12345, "Bonn", "KG", 1000000.0);
		
		session.save(b1);
		session.save(b2);
		session.save(b3);

//		darlehensnehmer.add(b1);
//		darlehensnehmer.add(b2);
//		darlehensnehmer.add(b3);
		
		//Versicherungen
		Versicherungsunternehmen v1 = new Versicherungsunternehmen("Münchner Vor", "Musterstr. 2", 11111, "Berlin", "GmbH", 1000.0);
		Versicherungsunternehmen v2 = new Versicherungsunternehmen("Alliance", "ABC Straße 9", 22222, "Frankfurt", "GmbH & Co KG", 999.0);
		session.save(v1);
		session.save(v2);
//		darlehensnehmer.add(v1);
//		darlehensnehmer.add(v2);
		
		//Unternehmen
		Unternehmen u1 = new Unternehmen("U1", "UStr. 1", 11111, "UStadt", "GmbH", 1000.0);
		Unternehmen u2 = new Unternehmen("U2", "UStr. 2", 22222, "UStadt", "GmbH", 2000.0);
		session.save(u1);
		session.save(u2);
//		darlehensnehmer.add(u1);
//		darlehensnehmer.add(u2);
		
		//Personen
		Privatperson p1 = new Privatperson("P1", "PStr. 1", 11111, "PStadt", "V1");
		Privatperson p2 = new Privatperson("P2", "PStr. 2", 22222, "PStadt", "V2");
		session.save(p1);
		session.save(p2);
//		darlehensnehmer.add(p1);
//		darlehensnehmer.add(p2);
*/		
		// Transktion beenden
		session.getTransaction().commit();
		
	}

	/**************************************************
	 *Stammdatenverwaltung
	 **************************************************/
	
	public Unternehmen erstelleUnternehmen(){
		// Hybernate session holen
		Session session = _sessionFactory.getCurrentSession();
		// Transaktion starten
		session.beginTransaction();
		
		Unternehmen u = null;
		
		try {
			// personeninfos bestimmen
			String name = ui.getString("Unternehmensname");
			String strasse = ui.getString("Straße");
			Integer plz = ui.getInteger("PLZ");
			String ort = ui.getString("Ort");
			String rechstform = ui.getString("Rechtsform");
			Double eigenkapital = ui.getDouble("Eigenkapital");
			
			//neues unternehmen anlegen
			u = new Unternehmen(name, strasse, plz, ort, rechstform, eigenkapital);
//			darlehensnehmer.add(u);
			
			// Unternehmen in DB speichern
			session.save(u);
			
			ui.message("Unternehmen erstellt.");
			
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}
		
		// Transktion beenden
		session.getTransaction().commit();
		
		return u;
	}
	
	public Privatperson erstellePrivatperson(){
		// Hybernate session holen
		Session session = _sessionFactory.getCurrentSession();
		// Transaktion starten
		session.beginTransaction();
		
		Privatperson p = null;
		
		try {
			// personeninfos bestimmen
			String name = ui.getString("Nachname");
			String vorname = ui.getString("Vorname");
			String strasse = ui.getString("Straße");
			Integer plz = ui.getInteger("PLZ");
			String ort = ui.getString("Ort");
			
			//neue person anlegen
			p = new Privatperson(name, strasse, plz, ort, vorname);
//			darlehensnehmer.add(p);
			
			// Privatperson in DB speichern
			session.save(p);
			
			ui.message("Privatperson erstellt.");
			
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}
		
		// Transktion beenden
		session.getTransaction().commit();
		
		return p;
	}
	
	public Immobilie erstelleImmobilie(Darlehensnehmer dn){
		// Hybernate session holen
		Session session = _sessionFactory.getCurrentSession();
		// Transaktion starten
		session.beginTransaction();
		
		Immobilie i = null;
		
		Collection<Darlehensnehmer> darlehensnehmer = session.createQuery(
			"select name from Darlehensnehmer")
			.list();

		
		try {
			//wert der immobilie abfragen
			Double wert = ui.getDouble("Wert");
			
			//ggf. eigentümer abfragen
			if(dn == null)
				dn = ui.getDnAuswahl("Eigentümer", darlehensnehmer, Darlehensnehmer.class);
			
			//neue immobilie anlegen
			i = new Immobilie(wert);
			
			//eigentümer zuordnen
			i.setBesitzer(dn);
			dn.addImmobilie(i);
			
			// Immobilie in DB speichern
			session.save(i);
			
			ui.message("Immobilie erstellt.");
			
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}
		
		// Transktion beenden
		session.getTransaction().commit();
		
		return i;
	}
	
	
	

	/**************************************************
	 *Kreditvergabe
	 **************************************************/
	
	public Darlehen erstellePrivatDarlehen(){
		// Hybernate session holen
		Session session = _sessionFactory.getCurrentSession();
		// Transaktion starten
		session.beginTransaction();
		
		Darlehen d = null;

		Collection<Darlehensnehmer> darlehensnehmer = session.createQuery(
			"select name from Darlehensnehmer")
			.list();

		try {
			//darlehensinfos bestimmen
			Double betrag = ui.getDouble("Betrag");
			Double zinssatz = ui.getDouble("Zinssatz");
			
			//darlehensnehmer und -geber bestimmen
			Privatperson dn = (Privatperson) ui.getDnAuswahl("Darlehensnehmer", darlehensnehmer, Privatperson.class);
			Bank dg = (Bank) ui.getDnAuswahl("Darlehensgeber", darlehensnehmer, Bank.class);
			
			//auf ex. darlehen prüfen
			if(dn.hatDarlehen(dg)){
				session.getTransaction().rollback();
				ui.message("Existierendes Darlehen. Darlehensvergabe abgebrochen.");
				return null;
			}
			
			//ggf. Immobilie zur Sicherung bestimmen
			Immobilie sicherung = null;
			
			if(betrag > 10000){

				HashSet<Immobilie> unbelasteteImmoblilien = dn.getUnbelasteteImmobilien();
				if(unbelasteteImmoblilien.size() > 0){
					sicherung = ui.getImmobilienAuswahl("Sicherung", unbelasteteImmoblilien);
				}
				while(sicherung == null || sicherung.getWert() < betrag){
					boolean neueImmobAnlegen = ui.getBoolean("Sicherung nicht ausreichend. Neue Immobilie anlegen?");
					if(neueImmobAnlegen){
						sicherung = erstelleImmobilie(dn);
					}
					else{
						ui.message("Darlehensvergabe abgebrochen.");
						session.getTransaction().rollback();
						return null;
					}
				}
			}
			
			//ggf. Versicherung anlegen
			if(betrag > 100000){
				double versicherungshoehe = 0;
				for (Lebensversicherung v : dn.getVersicherungen()) {
					versicherungshoehe += v.getBetrag();
				}
				
				while(versicherungshoehe < (betrag / 2)){
					boolean neueVersicherungAnlegen = ui.getBoolean("Versicherung nicht ausreichend. Neue Lebensversicherung anlegen?");
					if(neueVersicherungAnlegen){
						Lebensversicherung v = erstelleVersicherung(dn);
						versicherungshoehe += v.getBetrag();
					}
					else{
						ui.message("Darlehensvergabe abgebrochen.");
						session.getTransaction().rollback();
						return null;
					}
				}
			}
			
			
			//alle prüfungen erfolgreich
			d = new Darlehen(betrag, zinssatz, dn, dg, sicherung);
			dn.addDarlehen(d);
			dg.addVergebenesDarlehen(d);
			if(sicherung != null)
				sicherung.setHypothek(d);
			
			// Darlehen in DB speichern
			session.save(d);
			
			ui.message("Darlehen erstellt.");
			
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}
		// Transktion beenden
		session.getTransaction().commit();
		
		return d;
	}
	
	
	public Darlehen erstelleUnternehmensDarlehen(){
		// Hybernate session holen
		Session session = _sessionFactory.getCurrentSession();
		// Transaktion starten
		session.beginTransaction();
		
		Darlehen d = null;
		
		Collection<Darlehensnehmer> darlehensnehmer = session.createQuery(
			"select name from Darlehensnehmer")
			.list();

		try {
			//darlehensinfos bestimmen
			Double betrag = ui.getDouble("Betrag");
			Double zinssatz = ui.getDouble("Zinssatz");
			
			Unternehmen dn = (Unternehmen) ui.getDnAuswahl("Darlehensnehmer", darlehensnehmer, Unternehmen.class);
			Bank dg = (Bank) ui.getDnAuswahl("Darlehensgeber", darlehensnehmer, Bank.class);
		
			//(einfache) zyklische kreditvergabe prüfen 
			if(dn instanceof Bank){
				if(dg.hatDarlehen((Bank) dn)){
					ui.message("Zyklische Darlehen. Darlehensvergabe abgebrochen.");
					session.getTransaction().rollback();
					return null;
				}
			}
			
			//gesamtverschuldung prüfen
			if(	dn.getGesamtverschuldung() + betrag > 
					5 * (dn.getEigenkapital() + dn.getImmobilienwerte()) ){
				ui.message("Gesamtverschuldung überschritten. Darlehensvergabe abgebrochen.");
				session.getTransaction().rollback();
				return null;
			}
			
			
			//alle prüfungen erfolgreich
			d = new Darlehen(betrag, zinssatz, dn, dg, null);
			dn.addDarlehen(d);
			dg.addVergebenesDarlehen(d);
			
			// Darlehen in DB speichern
			session.save(d);
			
			ui.message("Darlehen erstellt.");

			
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}
		// Transktion beenden
		session.getTransaction().commit();
		
		return d;
	}
	
	
	/**************************************************
	 *Versicherungsabschluss
	 **************************************************/
	
	public Lebensversicherung erstelleVersicherung(Privatperson versicherter){
		
		// Hybernate session holen
		Session session = _sessionFactory.getCurrentSession();
		// Transaktion starten
		session.beginTransaction();
		
		Lebensversicherung v = null;

		Collection<Darlehensnehmer> darlehensnehmer = session.createQuery(
			"select name from Darlehensnehmer")
			.list();

		
		try {
			//wert der versicherung abfragen
			Double wert = ui.getDouble("Wert");
			
			//ggf. versicherten abfragen
			if(versicherter == null)
				versicherter = (Privatperson) ui.getDnAuswahl("Versicherter", darlehensnehmer, Privatperson.class);
			
			//versicherer abfragen
			Versicherungsunternehmen versicherer = (Versicherungsunternehmen) ui.getDnAuswahl("Versicherungsunternehmen", darlehensnehmer, Versicherungsunternehmen.class);
			
			//neue versicherung anlegen
			v = new Lebensversicherung(wert, versicherter, versicherer);

			versicherter.addVersicherung(v);
			versicherer.addVersicherung(v);
			
			// Versicherung in DB speichern
			session.save(v);
			
			ui.message("Versicherung erstellt.");
			
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}
		
		// Transktion beenden
		session.getTransaction().commit();
		
		return v;
		
	}
	
	public KonsolenUI getUi() {
		return ui;
	}

	public void setUi(KonsolenUI ui) {
		this.ui = ui;
	}

	public static void main(String[] args) {
		Kreditvergabe kv = new Kreditvergabe();
		KonsolenUI ui = new KonsolenUI(kv);
		kv.setUi(ui);
		ui.start();
	}
}