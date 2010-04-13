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
			System.out.println("[2]\tBeenden");

			System.out.print("\nAuswahl: ");

			try {
				String eingabe = in.readLine();
				int auswahl = Integer.parseInt(eingabe);

				switch (auswahl) {
				case 1:
					stammdatenMenue();
					break;

				case 2:
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
			System.out.println("[2]\tHauptmenü");

			System.out.print("\nAuswahl: ");

			try {
				String eingabe = in.readLine();
				int auswahl = Integer.parseInt(eingabe);

				switch (auswahl) {
				case 1:
					kv.erstelleImmobilie();
					break;

				case 2:
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
}