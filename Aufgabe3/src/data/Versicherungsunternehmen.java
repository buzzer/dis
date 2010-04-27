package data;

import java.util.ArrayList;
import java.util.Collection;

public class Versicherungsunternehmen extends Unternehmen {

	private Collection<Lebensversicherung> versicherungen;

	public Versicherungsunternehmen(String name, String strasse, String plz,
			String ort, String rechstform, Double eigenkapital) {
		super(name, strasse, plz, ort, rechstform, eigenkapital);

		this.versicherungen = new ArrayList<Lebensversicherung>();
	}

	public Collection<Lebensversicherung> getVersicherungen() {
		return versicherungen;
	}

	public void addVersicherung(Lebensversicherung v) {
		this.versicherungen.add(v);
	}
}