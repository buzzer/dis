package data;

//import java.util.ArrayList;
import java.util.HashSet;
import java.util.Collection;

public class Privatperson extends Darlehensnehmer {
	private String vorname;
	private Collection<Lebensversicherung> versicherungen;

	public void setVersicherungen(Collection<Lebensversicherung> versicherungen) {
		this.versicherungen = versicherungen;
	}

	public Privatperson(String name, String strasse, Integer plz, String ort,
			String vorname) {
		super(name, strasse, plz, ort);
		setVorname(vorname);

		versicherungen = new HashSet<Lebensversicherung>();
	}

	public String getVorname() {
		return vorname;
	}

	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	public Collection<Lebensversicherung> getVersicherungen() {
		return versicherungen;
	}

	public void addVersicherung(Lebensversicherung v) {
		this.versicherungen.add(v);
	}

	public HashSet<Immobilie> getUnbelasteteImmobilien() {
		HashSet<Immobilie> unbelastet = new HashSet<Immobilie>();

		for (Immobilie i : this.getImmobilien()) {
			if (i.getHypothek() == null)
				unbelastet.add(i);
		}

		return unbelastet;
	}
}