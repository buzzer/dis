package de.dis2010.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Unternehmen extends Darlehensnehmer
{

	// Attribute
	private static Integer unid = -1;
	private String rechtsform;
	private Double  eigenkapital;

	/**
	 * Erzeugt Unternehmen mit einem Name Beispiel-Tabelle:
	 * 
	 * @param Name
	 */
	public Unternehmen(Integer unid)
	{
		super(unid);

	}

	/**
	 * @return the unid
	 */
	public Integer getUnid()
	{
		return unid;
	}

	/**
	 * @param unid
	 *            the unid to set
	 */
	public void setUnid(int unid)
	{
		Unternehmen.unid = unid;
	}

	/**
	 * @return the wert
	 */
	public Double getEigenkapital()
	{
		return eigenkapital;
	}

	public String getRechtsform()
	{
		return rechtsform;
	}

	public void setRechtsform(String rechtsform)
	{
		this.rechtsform = rechtsform;
	}

	/**
	 * @param eigenkapital
	 *            the eigenkapital to set
	 */
	public void setEigenkapital(Double eigenkapital)
	{
		this.eigenkapital = eigenkapital;
	}

	// public void setName(String Name)
	// {
	// this.Name = Name;
	// }

	public Double getImmowerte() throws SQLException {
		// Hole Verbindung
		Connection con = DB2ConnectionManager.getInstance().getConnection();

		// Erzeuge Anfrage
		String selectSQL = "select Wert FROM immobilie where UNid = ?";
		
		PreparedStatement pstmt = con.prepareStatement(selectSQL);
		pstmt.setInt(1, this.getUnid());

		Double ImmoWerte = 0.;
		
		// Führe Anfrage aus
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			ImmoWerte += rs.getDouble("Wert");
		}
		rs.close();
		pstmt.close();
		return ImmoWerte;
	}

	/**
	 * Lädt ein Unternehmen via Id.
	 * 
	 * @param id
	 *            die Id des Datensatzes
	 * @return Unternehmen
	 */
	public static Unternehmen load(int iid) throws SQLException
	{
		// Hole Verbindung
		Connection con = DB2ConnectionManager.getInstance().getConnection();

		// Erzeuge Anfrage
		String selectSQL = "SELECT UNid,Name,Str,PLZ,Ort,Rechtsform,Eigenkapital FROM unternehmen WHERE UNid = ?";
		PreparedStatement pstmt = con.prepareStatement(selectSQL);
		pstmt.setInt(1, iid);

		// Führe Anfrage aus
		ResultSet rs = pstmt.executeQuery();
		if (rs.next())
		{
			Unternehmen u = new Unternehmen(unid);
			u.setUnid(rs.getInt("UNid"));
			u.setName(rs.getString("Name"));
			u.setStr(rs.getString("Str"));
			u.setPLZ(rs.getInt("PLZ"));
			u.setOrt(rs.getString("Ort"));
			u.setRechtsform(rs.getString("Rechtsform"));
			u.setEigenkapital(rs.getDouble("Eigenkapital"));

			rs.close();
			pstmt.close();
			return u;
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
		if (getUnid() == -1)
		{
			// Achtung, hier wird noch ein Parameter mitgegeben,
			// damit später generierte IDs zurückgeliefert werden!
			String insertSQL = "INSERT INTO unternehmen(Name,Str,PLZ,Ort,Rechtsform,Eigenkapital) VALUES (?,?,?,?,?,?)";

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
				setUnid(rs.getInt(1));
			}

			rs.close();
			pstmt.close();
		}
		else
		{
			// Falls schon eine ID vorhanden ist, mache ein Update...
			String updateSQL = "UPDATE unternehmen SET VorName = ?,Str = ?,PLZ = ?,Ort = ?,Rechtsform = ?,Eigenkapital = ? WHERE UNid = ?";
			PreparedStatement pstmt = con.prepareStatement(updateSQL);

			// Setze Anfrage Parameter
			pstmt.setString(1, getName());
			pstmt.setString(2, getStr());
			pstmt.setInt(3, getPLZ());
			pstmt.setString(4, getOrt());
			pstmt.setString(5, getRechtsform());
			pstmt.setDouble(6, getEigenkapital());
			pstmt.executeUpdate();

			pstmt.close();
		}
	}

	@Override
	public boolean equals(Object o)
	{
		if (o instanceof Unternehmen)
		{
			Unternehmen u = (Unternehmen) o;
			return u.getUnid().equals(this.getUnid());
		}
		return false;
	}

	@Override
	public boolean hatDarlehen(Integer bankID) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

}
