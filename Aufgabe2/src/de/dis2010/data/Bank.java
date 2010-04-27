package de.dis2010.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Bank extends Unternehmen
{
    private Integer bid;
     
    
	public Bank(Integer bid)
	{
		super(bid); 
	}
	public void setBid(int bid)
	{
		 this.bid = bid;
	}

	/**
	 * @return the wert
	 */
	public Integer getBid()
	{
		return bid;
	}
	public void save() throws SQLException
	{
		// Hole Verbindung
		Connection con = DB2ConnectionManager.getInstance().getConnection();

		// Füge neues Element hinzu, wenn das Objekt noch keine ID hat.
		if (getBid() == -1)
		{
			// Achtung, hier wird noch ein Parameter mitgegeben,
			// damit später generierte IDs zurückgeliefert werden!
			String insertSQL = "INSERT INTO Bank(Name,Str,PLZ,Ort,Rechtsform,Eigenkapital) VALUES (?,?,?,?,?,?)";

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
			String updateSQL = "UPDATE Bank SET Name = ?,Str = ?,PLZ = ?,Ort = ?,Rechtsform = ?,Eigenkapital = ? WHERE UNid = ?";
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
	

}
