package de.dis2010.data;

public abstract class Darlehensnehmer
{

	// Attribute
	private Integer did = -1;
	protected static String name;
	private String str;
	private int plz;
	private String ort;

	public Darlehensnehmer(Integer did)
	{
		this.did = did;

	}

	/**
	 * @return diid
	 */
	public Integer getDid()
	{
		return did;
	}

	/**
	 * @param diid
	 *            the diid to set
	 */
	public void setDiid(Integer did)
	{
		this.did = did;
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
	public int getPLZ()
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
