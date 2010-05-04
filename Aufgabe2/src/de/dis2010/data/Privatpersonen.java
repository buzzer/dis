package de.dis2010.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Privatpersonen extends Darlehensnehmer
{
	// Attribute
	private Integer pid;
	private String vorname;

	public Privatpersonen(Integer pid)
	{
		super(pid);

	}

	/**
	 * @return pid
	 */
	public  Integer getPid()
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
	public String getVorName()
	{
		return vorname;
	}

	/**
	 * @param vorname
	 *            the vorname to set
	 */
	public void setVorName(String vorname)
	{
		this.vorname = vorname;
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
			Privatpersonen p = new Privatpersonen(pid);
			p.setPid(rs.getInt("PersID"));
			p.setVorName(rs.getString("Vorname"));
			p.setName(rs.getString("Name"));
			p.setStr(rs.getString("Str"));
			p.setOrt(rs.getString("Ort"));
			p.setPLZ(rs.getInt("PLZ"));
			rs.close();
			pstmt.close();
			return p;
		}
		else
		{
			return null;
		}
	}

	public void save() throws SQLException
	{
		// Hole Verbindung
		Connection con = DB2ConnectionManager.getInstance().getConnection();

		// Füge neues Element hinzu, wenn das Objekt noch keine ID hat.
		if (getPid() == -1)
		{
			// Achtung, hier wird noch ein Parameter mitgegeben,
			// damit später generierte IDs zurückgeliefert werden!
			String insertSQL = "INSERT INTO privatperson(Vorname,Name,Str,PLZ,Ort) VALUES (?,?,?,?,?)";

			PreparedStatement pstmt = con.prepareStatement(insertSQL,
					Statement.RETURN_GENERATED_KEYS);

			// Setze Anfrageparameter und führe Anfrage aus
			pstmt.setString(1, getVorName());
			pstmt.setString(2, getName());
			pstmt.setString(3, getStr());
			pstmt.setInt(4, getPLZ());
			pstmt.setString(5, getOrt());

			pstmt.executeUpdate();

			// Hole die Id des engefügten Datensatzes
			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs.next())
			{
				setPid(rs.getInt(1));
			}

			rs.close();
			pstmt.close();
		}
		else
		{
			// Falls schon eine ID vorhanden ist, mache ein Update...
			String updateSQL = "UPDATE privatperson SET Vorname = ? WHERE PersID = ?";
			PreparedStatement pstmt = con.prepareStatement(updateSQL);

			// Setze Anfrage Parameter
			pstmt.setString(1, getVorName());
			pstmt.setString(2, getName());
			pstmt.setString(3, getStr());
			pstmt.setInt(4, getPLZ());
			pstmt.setString(5, getOrt());

			pstmt.executeUpdate();

			pstmt.close();
		}
	}

}
