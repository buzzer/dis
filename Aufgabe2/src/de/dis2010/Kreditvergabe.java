package de.dis2010;

import java.io.BufferedReader;
import java.io.InputStreamReader;

//import de.dis2010.data.Bank;
import de.dis2010.data.Privatpersonen;
import de.dis2010.data.Darlehen;
import de.dis2010.data.Immobilie;
//import de.dis2010.data.Privatpersonen;
import de.dis2010.data.Unternehmen;
import de.dis2010.data.Versicherung;
import de.dis2010.data.versichert;
import de.dis2010.ui.KonsolenUI;

public class Kreditvergabe {

	private KonsolenUI ui;

	/**
	 * Erzeugt neues Kreditvergabesystem
	 */
	public Kreditvergabe() {
		// Initialisieren
	}

	/**
	 * @return the ui
	 */
	public KonsolenUI getUi() {
		return ui;
	}

	/**
	 * @param ui
	 *            the ui to set
	 */
	public void setUi(KonsolenUI ui) {
		this.ui = ui;
	}

	/**************************************************
	 *Stammdatenverwaltung
	 **************************************************/

	public Immobilie erstelleImmobilie() {
		Immobilie i = null;
		try {
			// Wert der Immobilie abfragen
			Double wert = ui.getDouble("Wert der Immobilie");
			Integer pid = ui.getInteger("PersonID der Immobilie");
			Integer bid = ui.getInteger("BankID der Immobilie");
            Integer vuid = ui.getInteger("VersicherungsunternID der Immobilie");
            Integer uid = ui.getInteger("UnternehmenID der Immobilie");
			// Neue Immobilie anlegen
			i = new Immobilie(wert);
			i.setWert(wert);
			i.setPid(pid);
			i.setUid(uid);
			i.setVuid(vuid);
			i.setBid(bid);
			
			// Immobilie speichern
			i.save();
			
			ui.message("Immobilie erstellt.");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return i;
	}
	
	public Unternehmen erstelleUnternehmen() {
		Unternehmen u = null;
		try {
			// Name des Unternehmens abfragen
			String name = ui.getName("Name des Unternehmens");

			// Neues Unternehmen anlegen
			u = new Unternehmen(null);
			u.setName(name);
			
			// Unternehmen speichern
			u.save();
			
			ui.message("Unternehmen erstellt.");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return u;
	}
	
	public Darlehen erstelleKredit() {
		Darlehen d = null;
		Double limit1 = 10000.;
		Double limit2 = 100000.;
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		Privatpersonen person = null;
		Immobilie sicherung = null;
		versichert sicherung2 = null;
		
		try {
			System.out.println("\nKreditnehmer auswählen\n");
			System.out.println("[1]\tPrivatperson");
			System.out.println("[2]\tsonstiges Unternehmen (keine Bank oder Versicherung!)");

			System.out.print("\nAuswahl: ");
			String eingabe = in.readLine();
			int auswahl = Integer.parseInt(eingabe);
			
			Integer persID = 0;
			@SuppressWarnings("unused")
			Integer versUNid = 0;
			Integer uNid = 0;
			Integer immoID = 0;
			Integer lebVersID = 0;
			
			switch (auswahl) {
			case 1:
				persID = ui.getInteger("Personen Identifier");
				person = new Privatpersonen(null);
				person.setPid(persID);
				break;
			case 2:
				uNid   = ui.getInteger("Unternehmens Identifier");
				break;
			default:
				System.out.println("Ungültige Eingabe.\n");
				erstelleKredit();
				break;
			}
									
			// Bank abfragen (muss bereits in DB sein!)
			Integer BankID = ui.getInteger("Bank Identifier (Kreditgeber)");
			if (auswahl == 1) { // Nur Privatpersonen
				if (person.hatDarlehen(BankID)) {
					ui.message("Person hat bereits ein Darlehen von dieser Bank");
					return null;
				}
			}
			Double betrag  = ui.getDouble("Kreditrahmen");
			
			if (auswahl == 1) { // Nur Privatpersonen
				if (betrag > limit1) {
					immoID = ui.getInteger("Immobilien Identifier");
					// Immobilie zur Sicherung bestimmen
					sicherung = Immobilie.load(immoID);
					if (sicherung.getWert() < betrag) {
						ui.message("Immobilie reicht zur Sicherung nicht aus");
						return null;
					}
					// TODO ggf. anlegen
				}; 
				if (betrag > limit2) {
					lebVersID = ui.getInteger("Lebensversicherungs Identifier");
					sicherung2 = versichert.load(lebVersID);
					if (sicherung2.getBetrag() < (betrag/2) ) {
						ui.message("Lebensversicherung reicht zur Sicherung nicht aus");
						return null;
					}
					// TODO ggf. anlegen
				};
			} else { // Nur Unternehmen
				Unternehmen un = null;
				un = Unternehmen.load(uNid);
				if (betrag > 5*(un.getEigenkapital()+un.getImmowerte() ) ) {
					ui.message("Gesamtverschuldung ist zu hoch");
					return null;
				}
			}
			Double zinssatz= ui.getDouble("Zinssatz");
			Double tilgungsrate= ui.getDouble("tilgungsrate");
			Double restschuld= ui.getDouble("restschuld");
			
			// Neues Darlehen anlegen
			d = new Darlehen();
			d.setBankID(BankID);
			d.setBetrag(betrag);
			if (betrag > limit1) { d.setImmoID(immoID);	};
			if (betrag > limit2) { d.setLebVersID(lebVersID); }
			switch (auswahl) {
			case 1:
				d.setPersID(persID);
				break;
			case 2:
				d.setUNid(uNid);
				break;
			default:
				break;
			}
			d.setZinssatz(zinssatz);
			d.setTilgungsrate(tilgungsrate);
			d.setRestschuld(restschuld);
			
			// Darlehen speichern
			d.save();
			ui.message("Darlehen erstellt.");
			
		} catch (Exception e) { e.printStackTrace(); }
		return d;
	}

	public Versicherung erstelleVersicherung() {
		Versicherung v = null;
		try {
			// Betrag der Versicherung abfagen
			Double betrag = ui.getDouble("Betrag der Lebensversicherung");
			
			// Neue Versicherung anlegen
			v = new Versicherung();
			v.setBetrag(betrag);
			
			// Versicherung speichern
			v.save();
			ui.message("Versicherung erstellt.");
		}
		catch (Exception e) { e.printStackTrace(); }
		return v;
	}

	public  Privatpersonen  erstellePrivatperson() {
		 Privatpersonen p = null;
		try {
			// Betrag der Versicherung abfagen
			String Vorname = ui.getVorName("Vorname der Privatperson");
			
			// Neue Versicherung anlegen
			p = new Privatpersonen(null);
			p.setVorName(Vorname);
			
			// Versicherung speichern
			p.save();
			ui.message("Versicherung erstellt.");
		}
		catch (Exception e) { e.printStackTrace(); }
		return p;
	}
	public versichert erstelleVersichert() {
		versichert v = null;
		try {
			// Betrag der Versicherung abfragen
			Double betrag = ui.getDouble("Betrag der Lebensversicherung");
			// PersID abfragen
			Integer persID = ui.getInteger("PersonID");
			// VersicherungsunternehmenID abfragen
			Integer versUNid = ui.getInteger("VersicherungsunternehmenID");
			
			// Neue Versicherung anlegen
			v = new versichert();
			v.setBetrag(betrag);
			v.setPerson(persID);
			v.setVersUN(versUNid);
			
			// Versicherung speichern
			v.save();
			ui.message("Versicherung erstellt.");
		}
		catch (Exception e) { e.printStackTrace(); }
		return v;
	}

	/**
	 * Hauptprogramm
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Kreditvergabe kv = new Kreditvergabe();
		KonsolenUI ui = new KonsolenUI(kv);
		kv.setUi(ui);
		ui.start();
	}
}