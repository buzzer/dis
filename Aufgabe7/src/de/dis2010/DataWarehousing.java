package de.dis2010;

//import java.io.BufferedReader;
//import java.io.InputStreamReader;

import de.dis2010.ui.KonsolenUI;
import de.dis2010.extraction.CsvParser;
import de.dis2010.extraction.Db2Parser;

public class DataWarehousing {

	private KonsolenUI ui;

	/**
	 * Erzeugt neues Data-Warehouse-System
	 */
	public DataWarehousing() {
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

	/**
	 * Hauptprogramm
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		DataWarehousing dw = new DataWarehousing();
		CsvParser csvp = new CsvParser();
		Db2Parser db2p = new Db2Parser();
		KonsolenUI ui = new KonsolenUI(dw,csvp, db2p);
		dw.setUi(ui);
		ui.start();
	}
}