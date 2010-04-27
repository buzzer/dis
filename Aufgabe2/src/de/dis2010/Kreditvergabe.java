package de.dis2010;

import de.dis2010.data.Darlehen;
import de.dis2010.data.Immobilie;
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

			// Neue Immobilie anlegen
			i = new Immobilie(wert);
			i.setWert(wert);
			
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
			u = new Unternehmen(name);
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
		
		try {
			// Bank abfragen (muss bereits in DB sein!)
			Integer BankID = ui.getInteger("Bank Identifier");
			Double betrag  = ui.getDouble("Kreditrahmen");
			Integer immoID = 0;
			Integer lebVersID = 0;
			
			if (betrag > limit1) { immoID = ui.getInteger("Immobilien Identifier"); }; 
			if (betrag > limit2) { lebVersID = ui.getInteger("Lebensversicherungs Identifier");	};
			Integer versUNid = ui.getInteger("versUNid");
			Integer persID = ui.getInteger("Personen Identifier");
			Integer uNid   = ui.getInteger("Unternehmens Identifier");
			Double zinssatz= ui.getDouble("Zinssatz");
			Double tilgungsrate= ui.getDouble("tilgungsrate");
			Double restschuld= ui.getDouble("restschuld");
			
			// Neues Darlehen anlegen
			d = new Darlehen();
			d.setBankID(BankID);
			d.setBetrag(betrag);
			if (betrag > limit1) { d.setImmoID(immoID);	};
			if (betrag > limit2) { d.setLebVersID(lebVersID); }
			d.setVersUNid(versUNid);
			d.setPersID(persID);
			d.setUNid(uNid);
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