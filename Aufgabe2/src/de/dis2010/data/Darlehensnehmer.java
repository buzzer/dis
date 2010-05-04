package de.dis2010.data;

//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
import java.sql.SQLException;

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
	 * @param name
	 *            the name to set
	 */
	public void setName(String name)
	{
		Darlehensnehmer.name = name;
	}

	/**
	 * @return the strasse
	 */
	public String getStr()
	{
		return str;
	}

	public void setStr(String str)
	{
		this.str = str;
	}

	public void setPLZ(int plz)
	{
		this.plz = plz;
	}

	/**
	 * @return the ort
	 */
	public String getOrt()
	{
		return ort;
	}

	public void setOrt(String ort)
	{
		this.ort = ort;
	}

	public void setPLZ(Integer plz) {
		this.plz = plz;
	}

	/**
	 * @return the plz
	 */
	public int getPLZ()
	{
		return plz;
	}

	public abstract boolean hatDarlehen(Integer bankID) throws SQLException;

}
