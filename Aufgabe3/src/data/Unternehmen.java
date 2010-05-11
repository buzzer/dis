package data;

public class Unternehmen extends Darlehensnehmer {
	private String rechtsform;
	private Double eigenkapital;

	public Unternehmen(String name, String strasse, Integer plz, String ort,
			String rechtsform, Double eigenkapital) {
		super(name, strasse, plz, ort);
		setRechtsform(rechtsform);
		setEigenkapital(eigenkapital);
	}

	public Double getEigenkapital() {
		return eigenkapital;
	}

	public void setEigenkapital(Double eigenkapital) {
		this.eigenkapital = eigenkapital;
	}

	public String getRechtsform() {
		return rechtsform;
	}

	public void setRechtsform(String rechtsform) {
		this.rechtsform = rechtsform;
	}

	public Double getGesamtverschuldung() {
		double gesamt = 0;

		for (Darlehen d : this.getDarlehen()) {
			gesamt += d.getBetrag();
		}

		return gesamt;
	}

	public Double getImmobilienwerte() {
		double gesamt = 0;

		for (Immobilie i : this.getImmobilien()) {
			gesamt += i.getWert();
		}

		return gesamt;
	}
}