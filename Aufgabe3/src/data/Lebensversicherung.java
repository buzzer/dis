package data;

public class Lebensversicherung {
	private static Integer currentVid = 0;

	private Integer vid;
	private Double betrag;

	private Versicherungsunternehmen versicherer;
	private Privatperson versicherter;
	private Darlehen hypothek = null;

	public Lebensversicherung(Double betrag, Privatperson versicherter,
			Versicherungsunternehmen versicherer) {
		setVid(currentVid++);
		setBetrag(betrag);
		setVersicherter(versicherter);
		setVersicherer(versicherer);
	}

	public Integer getVid() {
		return vid;
	}

	public void setVid(Integer vid) {
		this.vid = vid;
	}

	public Double getBetrag() {
		return betrag;
	}

	public void setBetrag(Double betrag) {
		this.betrag = betrag;
	}

	public Versicherungsunternehmen getVersicherer() {
		return versicherer;
	}

	public void setVersicherer(Versicherungsunternehmen versicherer) {
		this.versicherer = versicherer;
	}

	public Privatperson getVersicherter() {
		return versicherter;
	}

	public void setVersicherter(Privatperson versicherter) {
		this.versicherter = versicherter;
	}

	public Darlehen getHypothek() {
		return hypothek;
	}

	public void setHypothek(Darlehen hypothek) {
		this.hypothek = hypothek;
	}
}