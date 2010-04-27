package ui;

import java.util.Collection;

import data.Darlehensnehmer;
import data.Immobilie;

public interface KreditvergabeUI {

	public void start();

	public String getString(String eingabe);

	public boolean getBoolean(String eingabe);

	public Double getDouble(String eingabe);

	public Integer getInteger(String eingabe);

	public Darlehensnehmer getDnAuswahl(String eingabe,
			Collection<Darlehensnehmer> darlehensnehmer, Class dnType);

	public Immobilie getImmobilienAuswahl(String eingabe,
			Collection<Immobilie> immobilien);

	public void message(String text);
}
