package ui;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Collection;

import core.Kreditvergabe;
import data.Darlehensnehmer;
import data.Immobilie;

public class KonsolenUI implements KreditvergabeUI {
	private static BufferedReader in = new BufferedReader(
			new InputStreamReader(System.in));
	private Kreditvergabe kv;

	public KonsolenUI(Kreditvergabe kv) {
		this.kv = kv;
	}

	public void start() {
		System.out.println("*** Kreditvergabe V1.0 ***\n");
		hauptMenue();
	}

	/*************************************************
	 * Menüs
	 *************************************************/
	private void hauptMenue() {

		while (true) {
			System.out.println("\n\nHauptmenü\n");
			System.out.println("[1]\tStammdaten");
			System.out.println("[2]\tKreditvergabe");
			System.out.println("[3]\tVersichern");
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
					kv.erstelleVersicherung(null);
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

	private void kreditvergabeMenue() {

		while (true) {
			System.out.println("\nKreditvergabe-Menü\n");
			System.out.println("[1]\tPrivatperson");
			System.out.println("[2]\tUnternehmen");
			System.out.println("[3]\tHauptmenü");

			System.out.print("\nAuswahl: ");

			try {
				String eingabe = in.readLine();
				int auswahl = Integer.parseInt(eingabe);

				switch (auswahl) {
				case 1:
					kv.erstellePrivatDarlehen();
					break;

				case 2:
					kv.erstelleUnternehmensDarlehen();
					break;

				case 3:
					return;

				default:
					System.out.println("Ungültige Eingabe.\n");
					break;
				}

			} catch (Exception e) {
				e.printStackTrace();
				hauptMenue();
			}
		}
	}

	private void stammdatenMenue() {

		while (true) {
			System.out.println("\nStammdaten-Menü\n");
			System.out.println("[1]\tUnternehmen");
			System.out.println("[2]\tPrivatperson");
			System.out.println("[3]\tImmobilie");
			System.out.println("[4]\tHauptmenü");

			System.out.print("\nAuswahl: ");

			try {
				String eingabe = in.readLine();
				int auswahl = Integer.parseInt(eingabe);

				switch (auswahl) {
				case 1:
					kv.erstelleUnternehmen();
					break;

				case 2:
					kv.erstellePrivatperson();
					break;

				case 3:
					kv.erstelleImmobilie(null);
					break;

				case 4:
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

	/*************************************************
	 * Dialoge
	 *************************************************/

	public String getString(String eingabe) {
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

	public boolean getBoolean(String eingabe) {
		String value = null;
		while (value == null) {
			try {
				System.out.print(eingabe + "(J/N): ");
				value = in.readLine();
			} catch (Exception e) {
			}
		}
		if (value.equalsIgnoreCase("J"))
			return true;

		return false;
	}

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

	public Darlehensnehmer getDnAuswahl(String eingabe,
			Collection<Darlehensnehmer> darlehensnehmer, Class dnType) {
		Darlehensnehmer auswahl = null;

		System.out.println("\nAuswahl " + eingabe + ": ");
		for (Darlehensnehmer d : darlehensnehmer) {
			if (dnType.isInstance(d))
				System.out.println("[" + d.getDnid() + "]\tName: "
						+ d.getName());
		}

		while (auswahl == null) {
			try {
				Integer dnid = getInteger(eingabe);

				for (Darlehensnehmer d : darlehensnehmer) {
					if (d.getDnid().equals(dnid) && dnType.isInstance(d)) {
						auswahl = d;
						break;
					}
				}
			} catch (Exception e) {
			}
		}

		return auswahl;
	}

	public Immobilie getImmobilienAuswahl(String eingabe,
			Collection<Immobilie> immobilien) {
		Immobilie auswahl = null;

		System.out.println("\nAuswahl " + eingabe + ": ");
		for (Immobilie i : immobilien) {
			System.out.println("[" + i.getIid() + "]\tWert: " + i.getWert());
		}

		while (auswahl == null) {
			try {
				Integer iid = getInteger(eingabe);

				for (Immobilie i : immobilien) {
					if (i.getIid().equals(iid)) {
						auswahl = i;
						break;
					}
				}
			} catch (Exception e) {
			}
		}

		return auswahl;
	}

	public void message(String text) {
		System.out.println(text);
	}

}
