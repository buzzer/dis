package data;

public class Immobilie {

	private static Integer currentIid = 0;

	private Integer iid;
	private Double wert;

	private Darlehensnehmer besitzer = null;
	private Darlehen hypothek = null;

	public Immobilie(Double wert) {
		setIid(currentIid++);
		setWert(wert);
	}

	public Integer getIid() {
		return iid;
	}

	public void setIid(Integer iid) {
		this.iid = iid;
	}

	public Darlehensnehmer getBesitzer() {
		return besitzer;
	}

	public void setBesitzer(Darlehensnehmer bestizer) {
		this.besitzer = bestizer;
	}

	public Darlehen getHypothek() {
		return hypothek;
	}

	public void setHypothek(Darlehen hypothek) {
		if (this.hypothek == null)
			this.hypothek = hypothek;
	}

	public Double getWert() {
		return wert;
	}

	public void setWert(Double wert) {
		this.wert = wert;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((iid == null) ? 0 : iid.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Immobilie))
			return false;
		Immobilie other = (Immobilie) obj;
		if (iid == null) {
			if (other.iid != null)
				return false;
		} else if (!iid.equals(other.iid))
			return false;
		return true;
	}
}