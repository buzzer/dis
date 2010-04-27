package de.dis2010;

import de.dis2010.data.Darlehen;
import de.dis2010.data.Immobilie;
import de.dis2010.data.Privatpersonen;
import de.dis2010.data.Unternehmen;
import de.dis2010.data.Versicherung;
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
		// TODO Auto-generated method stub
		Darlehen d = null;
		try {}
		catch (Exception e) {}
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
//	public versichert erstelleVersichert() {
//		versichert v = null;
//		try {
//			// Betrag der Versicherung abfragen
//			Double betrag = ui.getDouble("Betrag der Lebensversicherung");
//			// PersID abfragen
//			Integer persID = ui.getInteger("PersonID");
//			// VersicherungsunternehmenID abfragen
//			Integer versUNid = ui.getInteger("VersicherungsunternehmenID");
//			
//			// Neue Versicherung anlegen
//			v = new versichert();
//			v.setBetrag(betrag);
//			v.setPerson(persID);
//			v.setVersUN(versUNid);
//			
//			// Versicherung speichern
//			v.save();
//			ui.message("Versicherung erstellt.");
//		}
//		catch (Exception e) { e.printStackTrace(); }
//		return v;
//	}

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