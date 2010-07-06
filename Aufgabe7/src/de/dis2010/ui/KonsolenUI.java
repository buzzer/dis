package de.dis2010.ui;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import de.dis2010.extraction.CsvParser;
import de.dis2010.extraction.Db2Parser;
import de.dis2010.analysis.DataCube;
import de.dis2010.createtable.StarScheme;

public class KonsolenUI {
	private static BufferedReader in = new BufferedReader(
			new InputStreamReader(System.in));
	private CsvParser csvp;
	private Db2Parser db2p;
	private DataCube dc;
	private StarScheme s;

	public KonsolenUI(CsvParser csvp, Db2Parser db2p, DataCube dc, StarScheme s) {
		this.csvp = csvp;
		this.db2p = db2p;
		this.dc = dc;
		this.s = s;
	}

	public void start() {
		System.out.println("*** Data-Warehousing V0.1 ***");
		hauptMenue();
	}

	/*************************************************
	 * Menüs
	 *************************************************/
	private void hauptMenue() {

		while (true) {
			System.out.println("\nHauptmenü\n");
			System.out.println("[1]\tCreate star scheme");
			System.out.println("[2]\tLoad DB2 DB");
			System.out.println("[3]\tLoad CSV file");
			System.out.println("[4]\tAnalyse data cube");
			System.out.println("[5]\tBeenden");

			System.out.print("\nAuswahl: ");

			try {
				String eingabe = in.readLine();
				int auswahl = Integer.parseInt(eingabe);

				switch (auswahl) {
				case 1:
					s.parseSql();
					break;
				case 2:
					db2p.extractArticles();
					db2p.extractShops();
					break;
				case 3:
					csvp.readCsv();
					break;
				case 4:
					this.analyseMenu();
					break;
				case 5:
					return;

				default:
					System.out.println("Ungültige Eingabe.\n");
					break;
				}

			} catch (Exception e) {
				//e.printStackTrace();
			}
		}
	}

	private void analyseMenu() {

		while (true) {
			System.out.println("\nAnalyse-Menü\n");
			System.out.println("[1]\tnach Stadt");
			System.out.println("[2]\tnach Region");
			System.out.println("[3]\tnach Monat");
			System.out.println("[4]\tnach Produktgruppe");
			System.out.println("[5]\tnach Produktfamilie");
			System.out.println("[6]\tnach ProduktCategorie");
			System.out.println("[7]\tHauptmenü");

			System.out.print("\nAuswahl: ");

			try {
				
				String eingabe = in.readLine();
				int auswahl = Integer.parseInt(eingabe);

				switch (auswahl) {
				case 1:
					dc.aggregatStadt();
					break;
				case 2:
					dc.aggregatRegion();
					break;
				case 3:
					dc.aggregatMonat();
					break;
				case 4:
					dc.aggregatPG();
					break;
				case 5:
					dc.aggregatPF();
					break;
				case 6:
					dc.aggregatPC();
					break;
				case 7:
					return;
				default:
					System.out.println("Ungültige Eingabe.\n");
					this.analyseMenu();
					break;
				}

			} catch (Exception e) {
				//e.printStackTrace();
			}
		}
	}
}
	
