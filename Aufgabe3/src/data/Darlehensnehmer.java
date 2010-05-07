package data;

import java.util.ArrayList;
import java.util.Collection;

public abstract class Darlehensnehmer {
	private static Integer currentDnid = 0;

	private Integer dnid;
	private String name;
	private String strasse;
	private String plz;
	private String Ort;

	private Collection<Immobilie> immobilien;
	private Collection<Darlehen> darlehen;

	public Darlehensnehmer(String name, String strasse, String plz, String ort) {
		setDnid(currentDnid++);
		setName(name);
		setStrasse(strasse);
		setPlz(plz);
		setOrt(ort);

		immobilien = new ArrayList<Immobilie>();
		darlehen = new ArrayList<Darlehen>();
	}

	public Integer getDnid() {
		return dnid;
	}

	public void setDnid(Integer dnid) {
		this.dnid = dnid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOrt() {
		return Ort;
	}

	public void setOrt(String ort) {
		Ort = ort;
	}

	public String getPlz() {
		return plz;
	}

	public void setPlz(String plz) {
		this.plz = plz;
	}

	public String getStrasse() {
		return strasse;
	}

	public void setStrasse(String strasse) {
		this.strasse = strasse;
	}

	public Collection<Immobilie> getImmobilien() {
		return immobilien;
	}

	public void setImmobilien(Collection<Immobilie> immobilien) {
		this.immobilien = immobilien;
	}

	public void addImmobilie(Immobilie i) {
		this.immobilien.add(i);
	}

	public Collection<Darlehen> getDarlehen() {
		return darlehen;
	}

	public void setDarlehen(Collection<Darlehen> darlehen) {
		this.darlehen = darlehen;
	}

	public void addDarlehen(Darlehen d) {
		darlehen.add(d);
	}

	public boolean hatDarlehen(Bank dg) {
		for (Darlehen d : this.getDarlehen()) {
			if (d.getDarlehensgeber().equals(dg))
				return true;
		}
		return false;
	}

	public boolean equals(Object o) {
		if (o instanceof Darlehensnehmer) {
			Darlehensnehmer d = (Darlehensnehmer) o;
			return d.getDnid().equals(this.getDnid());
		}

		return false;
	}
}
