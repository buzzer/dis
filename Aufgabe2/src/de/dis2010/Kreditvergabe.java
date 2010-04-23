package de.dis2010;

import de.dis2010.data.Darlehen;
import de.dis2010.data.Immobilie;
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
		// TODO Auto-generated method stub
		Darlehen d = null;
		try {}
		catch (Exception e) {}
		return d;
	}

	public Versicherung erstelleVersicherung() {
		// TODO Auto-generated method stub
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