package ui;

import java.util.Collection;
import java.util.List;

import data.Darlehensnehmer;
import data.Immobilie;

public interface KreditvergabeUI {

	public void start();

	public String getString(String eingabe);

	public boolean getBoolean(String eingabe);

	public Double getDouble(String eingabe);

	public Integer getInteger(String eingabe);

	public Darlehensnehmer getDnAuswahl(String eingabe,
			//Collection<Darlehensnehmer> darlehensnehmer, Class dnType);
			List<Darlehensnehmer> darlehensnehmer, Class dnType);

	public Immobilie getImmobilienAuswahl(String eingabe,
			//Collection<Immobilie> immobilien);
			List<Immobilie> immobilien);

	public void message(String text);
}
