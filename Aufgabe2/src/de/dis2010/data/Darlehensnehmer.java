package de.dis2010.data;

public abstract class Darlehensnehmer
{

	// Attribute
	private Integer diid = -1;
	private static String name;
	private String str;
	private int plz;
	private String ort;

	public Darlehensnehmer(String name)
	{
		Darlehensnehmer.name = name;

	}


	/**
	 * @return diid
	 */
	public Integer getDiid()
	{
		return diid;
	}

	/**
	 * @param diid
	 *            the diid to set
	 */
	public void setDiid(Integer diid)
	{
		this.diid = diid;
	}

	/**
	 * @return the name
	 */
	public static String getName()
	{
		return name;
	}

	/**
	 * @return the strasse
	 */
	public String getStr()
	{
		return str;
	}

	/**
	 * @return the plz
	 */
	public int getPlz()
	{
		return plz;
	}

	/**
	 * @return the ort
	 */
	public String getOrt()
	{
		return ort;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name)
	{
		Darlehensnehmer.name = name;
	}

}
