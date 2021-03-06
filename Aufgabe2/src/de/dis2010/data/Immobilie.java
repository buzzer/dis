package de.dis2010.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Immobilie
{

	// Attribute
	private Integer iid = -1;
	private static Double wert;
	private Integer pid;
	private Integer uid;
	private Integer vuid;
	private Integer bid;

	/**
	 * Erzeugt Immobilie mit einem Wert Beispiel-Tabelle: CREATE TABLE
	 * aufg2_immobilie ( id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START
	 * WITH 1, INCREMENT BY 1, NO CACHE) PRIMARY KEY, wert DOUBLE NOT NULL );
	 * 
	 * @param wert
	 */
	public Immobilie(Double wert)
	{
		Immobilie.wert = wert;
		pid = 0;
		uid = 0;
		vuid = 0;
		bid = 0;

	}

	/**
	 * @return the iid
	 */
	public Integer getIid()
	{
		return iid;
	}

	/**
	 * @param iid
	 *            the iid to set
	 */
	public void setIid(Integer iid)
	{
		this.iid = iid;
	}

	/**
	 * @return the wert
	 */
	public Double getWert()
	{
		return wert;
	}

	/**
	 * @param wert
	 *            the wert to set
	 */
	public void setWert(Double wert)
	{
		Immobilie.wert = wert;
	}

	public void setPid(Integer pid)
	{
		this.pid = pid;
	}

	public int getPid()
	{
		return pid;
	}

	public void setVuid(Integer vuid)
	{
		this.vuid = vuid;
	}

	public int getVuid()
	{
		return vuid;
	}

	public void setBid(Integer bid)
	{
		this.bid = bid;
	}

	public int getBid()
	{
		return bid;

	}

	public void setUid(Integer uid)
	{
		this.uid = uid;
	}

	public int getUid()
	{
		return uid;
	}

	/**
	 * Lädt eine Immobilie via Id.
	 * 
	 * @param id
	 *            die Id des Datensatzes
	 * @return Immobilie
	 */
	public static Immobilie load(int iid) throws SQLException
	{
		// Hole Verbindung
		Connection con = DB2ConnectionManager.getInstance().getConnection();

		// Erzeuge Anfrage
		String selectSQL = "SELECT * FROM immobilie WHERE ImmoID = ?";
		PreparedStatement pstmt = con.prepareStatement(selectSQL);
		pstmt.setInt(1, iid);

		// Führe Anfrage aus
		ResultSet rs = pstmt.executeQuery();
		if (rs.next())
		{
			Immobilie i = new Immobilie(wert);
			i.setIid(rs.getInt("ImmoID"));
			i.setWert(rs.getDouble("Wert"));
	        i.setPid(rs.getInt("PersID"));
	        i.setUid(rs.getInt("UNid"));
	        i.setVuid(rs.getInt("VersUNid"));
	        i.setBid(rs.getInt("BankID"));
			rs.close();
			pstmt.close();
			return i;
		}
		else
		{
			return null;
		}
	}

	/**
	 * Speichert die Immobilie in der Datenbank. Ist noch keine ID vergeben
	 * worden, wird die generierte Id von DB2 geholt und dem Model übergeben.
	 */
	public void save() throws SQLException
	{
		// Hole Verbindung
		Connection con = DB2ConnectionManager.getInstance().getConnection();

		// Füge neues Element hinzu, wenn das Objekt noch keine ID hat.
		if (getIid() == -1)
		{
			// Achtung, hier wird noch ein Parameter mitgegeben,
			// damit später generierte IDs zurückgeliefert werden!
			String insertSQL = "INSERT INTO immobilie(PersID,UNid,VersUNid,BankID,Wert) VALUES (?,?,?,?,?)";
			

			PreparedStatement pstmt = con.prepareStatement(insertSQL,
					Statement.RETURN_GENERATED_KEYS);

			// Setze Anfrageparameter und führe Anfrage aus
			pstmt.setInt(1, getPid());
			pstmt.setInt(2, getUid());
			pstmt.setInt(3,getVuid());
			pstmt.setInt(4,getBid());
			pstmt.setDouble(5,getWert());
			
			pstmt.executeUpdate();

			// Hole die Id des engefügten Datensatzes
			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs.next())
			{
				setIid(rs.getInt(1));
			}

			rs.close();
			pstmt.close();
		}
		else
		{
			// Falls schon eine ID vorhanden ist, mache ein Update...
			String updateSQL = "UPDATE immobilie SET Wert = ? WHERE ImmoID = ?";
			PreparedStatement pstmt = con.prepareStatement(updateSQL);

			// Setze Anfrage Parameter
			pstmt.setDouble(1, getWert());
			pstmt.setInt(2, getIid());

			pstmt.executeUpdate();

			pstmt.close();
		}
	}

	@Override
	public boolean equals(Object o)
	{
		if (o instanceof Immobilie)
		{
			Immobilie i = (Immobilie) o;
			return i.getIid().equals(this.getIid());
		}
		return false;
	}
}
