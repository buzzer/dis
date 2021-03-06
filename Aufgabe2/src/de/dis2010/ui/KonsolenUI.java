package de.dis2010.ui;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import de.dis2010.Kreditvergabe;

public class KonsolenUI {
	private static BufferedReader in = new BufferedReader(
			new InputStreamReader(System.in));
	private Kreditvergabe kv;

	public KonsolenUI(Kreditvergabe kv) {
		this.kv = kv;
	}

	public void start() {
		System.out.println("*** Kreditvergabe V1.1 ***");
		hauptMenue();
	}

	/*************************************************
	 * Menüs
	 *************************************************/
	private void hauptMenue() {

		while (true) {
			System.out.println("\nHauptmenü\n");
			System.out.println("[1]\tStammdaten");
			System.out.println("[2]\tKreditvergabe");
			System.out.println("[3]\tVersicherungsabschluss");
			System.out.println("[4]\tBeenden");

			System.out.print("\nAuswahl: ");

			try {
				String eingabe = in.readLine();
				int auswahl = Integer.parseInt(eingabe);

				switch (auswahl) {
				case 1:
					stammdatenMenue();
					break;
				case 2:
					kreditvergabeMenue();
					break;
				case 3:
					versicherungsabschlussMenue();
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

	private void stammdatenMenue() {

		while (true) {
			System.out.println("\nStammdaten-Menü\n");
			System.out.println("[1]\tNeue Immobilie anlegen");
			System.out.println("[2]\tNeue Privatperson anlegen");
			System.out.println("[3]\tNeue Bank anlegen");
			System.out.println("[4]\tNeues Versicherungsunternehmen anlegen");
			System.out.println("[5]\tSonstiges Unternehmen anlegen");
			System.out.println("[6]\tHauptmenü");

			System.out.print("\nAuswahl: ");

			try {
				String eingabe = in.readLine();
				int auswahl = Integer.parseInt(eingabe);

				switch (auswahl) {
				case 1:
					kv.erstelleImmobilie();
					break;
				case 2:
					kv.erstellePrivatperson();
					break;
				case 3:
					//kv.erstelleBank();
					break;
				case 4:
					//kv.erstelleVersUN();
					break;
				case 5:
					kv.erstelleUnternehmen();
					break;
				case 6:
					return;
				default:
					System.out.println("Ungültige Eingabe.\n");
					stammdatenMenue();
					break;
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
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
					kv.erstelleKredit();
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
					kv.erstelleVersichert();
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