package de.dis2010.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Privatpersonen extends Darlehensnehmer
{
	// Attribute
	private Integer pid;
	private static String vorname;

	public Privatpersonen(String vorname, String name)
	{
		super(name);
		Privatpersonen.vorname = vorname;

	}

	/**
	 * @return pid
	 */
	public Integer getPid()
	{
		return pid;
	}

	/**
	 * @param pid
	 *            the pid to set
	 */
	public void setPid(Integer pid)
	{
		this.pid = pid;
	}

	/**
	 * @return the vorname
	 */
	public String getVorname()
	{
		return vorname;
	}

	/**
	 * @param vorname
	 *            the vorname to set
	 */
	public void setVorName(String vorname)
	{
		Privatpersonen.vorname = vorname;
	}
	
	public boolean hatDarlehen(Integer bankID) throws SQLException{
		// Hole Verbindung
		Connection con = DB2ConnectionManager.getInstance().getConnection();

		// Erzeuge Anfrage
		String selectSQL = "SELECT PersID, BankID FROM darlehen where PersID = ? AND BankID = ?";
		PreparedStatement pstmt = con.prepareStatement(selectSQL);
		pstmt.setInt(1, this.pid);
		pstmt.setInt(2, bankID);
		
		// Führe Anfrage aus
		ResultSet rs = pstmt.executeQuery();
		if (rs.next()) {
			rs.close();
			pstmt.close();
			return true;
		} else {
			return false;
		}
	}

	public static Privatpersonen load(int pid) throws SQLException
	{
		// Hole Verbindung
		Connection con = DB2ConnectionManager.getInstance().getConnection();

		// Erzeuge Anfrage
		String selectSQL = "SELECT PersID, Vorname FROM creattable_privatperson WHERE PersID = ?";
		PreparedStatement pstmt = con.prepareStatement(selectSQL);
		pstmt.setInt(1, pid);

		// Führe Anfrage aus
		ResultSet rs = pstmt.executeQuery();
		if (rs.next())
		{
			Privatpersonen p = new Privatpersonen(vorname, getName());
			p.setPid(rs.getInt("PersID"));
			p.setVorName(rs.getString("Vorname"));
			p.setName(rs.getString("Name"));
			// TODO
			rs.close();
			pstmt.close();
			return p;
		}
		else
		{
			return null;
		}
	}
}
