package de.dis2010.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Versicherungsuntern extends Unternehmen
{
	private Integer vuid;

	public Versicherungsuntern(Integer vuid)
	{
		super(vuid);
		
	}

	/**
	 * @param vuid
	 *            the vuid to set
	 */

	public void setVuid(int vuid)
	{
		this.vuid = vuid;
	}

	public Integer getVuid()
	{
		return vuid;
	}

	/**
	 * Lädt ein Unternehmen via Id.
	 * 
	 * @param vuid
	 *            die Id des Datensatzes
	 * @return versicherungsunternehmen
	 */
	public static Versicherungsuntern load(int vuid) throws SQLException
	{
		// Hole Verbindung
		Connection con = DB2ConnectionManager.getInstance().getConnection();

		// Erzeuge Anfrage
		String selectSQL = "SELECT VersUNid,Name FROM vers_un WHERE VersUNid = ?";
		PreparedStatement pstmt = con.prepareStatement(selectSQL);
		pstmt.setInt(1, vuid);

		// Führe Anfrage aus
		ResultSet rs = pstmt.executeQuery();
		if (rs.next())
		{
			Versicherungsuntern v = new Versicherungsuntern(vuid);
			v.setVuid(rs.getInt("VersUNid"));
			v.setName(rs.getString("Name"));

			rs.close();
			pstmt.close();
			return v;
		}

		else
		{
			return null;
		}
	}

	/**
	 * Speichert das Unternehmen in der Datenbank. Ist noch keine ID vergeben
	 * worden, wird die generierte Id von DB2 geholt und dem Model übergeben.
	 */
	public void save() throws SQLException
	{
		// Hole Verbindung
		Connection con = DB2ConnectionManager.getInstance().getConnection();

		// Füge neues Element hinzu, wenn das Objekt noch keine ID hat.
		if (getVuid() == -1)
		{
			// Achtung, hier wird noch ein Parameter mitgegeben,
			// damit später generierte IDs zurückgeliefert werden!
			String insertSQL = "INSERT INTO vers_u(Name,Str,PLZ,Ort,Rechtsform,Eigenkapital) VALUES (?,?,?,?,?,?)";

			PreparedStatement pstmt = con.prepareStatement(insertSQL,
					Statement.RETURN_GENERATED_KEYS);

			// Setze Anfrageparameter und führe Anfrage aus
			pstmt.setString(1, getName());
			pstmt.setString(2, getStr());
			pstmt.setInt(3, getPLZ());
			pstmt.setString(4, getOrt());
			pstmt.setString(5, getRechtsform());
			pstmt.setDouble(6, getEigenkapital());

			pstmt.executeUpdate();

			// Hole die Id des engefügten Datensatzes
			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs.next())
			{
				setVuid(rs.getInt(1));
			}

			rs.close();
			pstmt.close();
		}
		else
		{
			// Falls schon eine ID vorhanden ist, mache ein Update...
			String updateSQL = "UPDATE vers_un SET Name = ? WHERE VersUNid = ?";
			PreparedStatement pstmt = con.prepareStatement(updateSQL);

			// Setze Anfrage Parameter
			pstmt.setString(1, getName());
			pstmt.setInt(2, getVuid());
			pstmt.executeUpdate();

			pstmt.close();
		}
	}

	@Override
	public boolean equals(Object o)
	{
		if (o instanceof Versicherungsuntern)
		{
			Versicherungsuntern v = (Versicherungsuntern) o;
			return v.getVuid().equals(this.getVuid());
		}
		return false;

	}

}
