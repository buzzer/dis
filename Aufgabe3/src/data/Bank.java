package data;

import java.util.ArrayList;
import java.util.Collection;

public class Bank extends Unternehmen {

	private Collection<Darlehen> vergebeneDarlehen;

	public Collection<Darlehen> getVergebeneDarlehen() {
		return vergebeneDarlehen;
	}

	public void setVergebeneDarlehen(Collection<Darlehen> vergebeneDarlehen) {
		this.vergebeneDarlehen = vergebeneDarlehen;
	}

	public Bank(String name, String strasse, Integer plz, String ort,
			String rechstform, Double eigenkapital) {
		super(name, strasse, plz, ort, rechstform, eigenkapital);

		this.vergebeneDarlehen = new ArrayList<Darlehen>();
	}

	public void addVergebenesDarlehen(Darlehen d) {
		this.vergebeneDarlehen.add(d);
	}
}