package de.dis2010.ui;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import de.dis2010.DataWarehousing;
import de.dis2010.extraction.CsvParser;
import de.dis2010.extraction.Db2Parser;
import de.dis2010.analysis.DataCube;

public class KonsolenUI {
	private static BufferedReader in = new BufferedReader(
			new InputStreamReader(System.in));
	@SuppressWarnings("unused")
	private DataWarehousing dw;
	private CsvParser csvp;
	private Db2Parser db2p;
	private DataCube dc;

	public KonsolenUI(DataWarehousing dw, CsvParser csvp, Db2Parser db2p, DataCube dc) {
		this.dw = dw;
		this.csvp = csvp;
		this.db2p = db2p;
		this.dc = dc;
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
			System.out.println("[1]\tLoad DB2 DB");
			System.out.println("[2]\tLoad CSV file");
			System.out.println("[3]\tAnalyse data cube");
			System.out.println("[4]\tBeenden");

			System.out.print("\nAuswahl: ");

			try {
				String eingabe = in.readLine();
				int auswahl = Integer.parseInt(eingabe);

				switch (auswahl) {
				case 1:
					db2p.extractArticles();
					db2p.extractShops();
					break;
				case 2:
					csvp.readCsv();
					break;
				case 3:
					this.analyseMenu();
					break;
				case 4:
					return;

				default:
					System.out.println("Ungültige Eingabe.\n");
					break;
				}

			} catch (Exception e) {
				e.printStackTrace();
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
				e.printStackTrace();
			}
		}
	}
	
	@SuppressWarnings("unused")
	private void kreditvergabeMenue() {
		
		while (true) {
			System.out.println("\nKreditvergabe-Menü\n");
			System.out.println("[1]\tNeuen Kredit anlegen");
			System.out.println("[2]\tHauptmenü");

			System.out.print("\nAuswahl: ");

			try {
				String eingabe = in.readLine();
				int auswahl = Integer.parseInt(eingabe);

				switch (auswahl) {
				case 1:
					break;

				case 2:
					return;

				default:
					System.out.println("Ungültige Eingabe.\n");
					kreditvergabeMenue();
					break;
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	@SuppressWarnings("unused")
	private void versicherungsabschlussMenue() {
		
		while (true) {
			System.out.println("\nVersicherungs-Menü\n");
			System.out.println("[1]\tNeue Versicherung anlegen");
			System.out.println("[2]\tHauptmenü");

			System.out.print("\nAuswahl: ");

			try {
				String eingabe = in.readLine();
				int auswahl = Integer.parseInt(eingabe);

				switch (auswahl) {
				case 1:
					break;

				case 2:
					return;

				default:
					System.out.println("Ungültige Eingabe.\n");
					versicherungsabschlussMenue();
					break;
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}

	/*************************************************
	 * Dialoge
	 *************************************************/

	public Double getDouble(String eingabe) {
		Double value = null;
		while (value == null) {
			try {
				System.out.print(eingabe + ": ");
				String valueString = in.readLine();
				value = Double.parseDouble(valueString);
			} catch (Exception e) {
			}
		}

		return value;
	}

	public void message(String text) {
		System.out.println(text);
	}

	public String getName(String eingabe) {
		String value = null;
		while (value == null) {
			try {
				System.out.print(eingabe + ": ");
				value = in.readLine();
			} catch (Exception e) {
			}
		}
		return value;
	}
	public String getVorName(String eingabe) {
		String value = null;
		while (value == null) {
			try {
				System.out.print(eingabe + ": ");
				value = in.readLine();
			} catch (Exception e) {
			}
		}
		return value;
	}

	public Integer getInteger(String eingabe) {
		Integer value = null;
		while (value == null) {
			try {
				System.out.print(eingabe + ": ");
				String valueString = in.readLine();
				value = Integer.parseInt(valueString);
			} catch (Exception e) {
			}
		}
		return value;
	}


}