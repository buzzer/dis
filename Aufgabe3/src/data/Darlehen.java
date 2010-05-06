package data;


public class Darlehen {
	private static Integer currentDid = 0;

	private Integer did;

	private Double betrag;
	private Double zinssatz;
	private Double tilgungsrate;
	private Double restschuld;

	private Darlehensnehmer darlehensnehmer;
	private Bank darlehensgeber;
	private Immobilie sicherung;
	private Lebensversicherung sicherung2;

	public Darlehen(Double betrag, Double zinssatz, Darlehensnehmer dn,
			Bank dg, Immobilie sicherung) {

		setDid(currentDid++);

		setBetrag(betrag);
		setZinssatz(zinssatz);

		setDarlehensnehmer(dn);
		setDarlehensgeber(dg);
		setSicherung(sicherung);
	}

	public Integer getDid() {
		return did;
	}

	private void setDid(Integer did) {
		this.did = did;
	}

	public Double getBetrag() {
		return betrag;
	}

	public void setBetrag(Double betrag) {
		this.betrag = betrag;
	}

	public Bank getDarlehensgeber() {
		return darlehensgeber;
	}

	public void setDarlehensgeber(Bank darlehensgeber) {
		this.darlehensgeber = darlehensgeber;
	}

	public Darlehensnehmer getDarlehensnehmer() {
		return darlehensnehmer;
	}

	public void setDarlehensnehmer(Darlehensnehmer darlehensnehmer) {
		this.darlehensnehmer = darlehensnehmer;
	}

	public Immobilie getSicherung() {
		return sicherung;
	}

	public void setSicherung(Immobilie sicherung) {
		this.sicherung = sicherung;
	}

	public Double getZinssatz() {
		return zinssatz;
	}

	public void setZinssatz(Double zinssatz) {
		this.zinssatz = zinssatz;
	}

	public boolean equals(Object o) {
		if (o instanceof Darlehen) {
			Darlehen d = (Darlehen) o;
			return d.getDid().equals(this.getDid());
		}

		return false;
	}
}