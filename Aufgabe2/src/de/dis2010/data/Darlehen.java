package de.dis2010.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Darlehen {

	// Attribute
	private Integer iid = -1;
	private Integer BankID = 0;
	private Integer PersID = 0;
	private Integer UNid = 0;
	private Integer VersUNid = 0;
	private Integer ImmoID = 0;
	private Integer LebVersID = 0;
	private Double	Betrag = 0.;
	private Double	Zinssatz = 0.;
	private Double	Tilgungsrate = 0.;
	private Double	Restschuld = 0.;

	public Darlehen() {
		// dummy
	}

	/**
	 * @return the iid
	 */
	public Integer getIid() {
		return iid;
	}

	/**
	 * @param iid
	 *            the iid to set
	 */
	public void setIid(Integer iid) {
		this.iid = iid;
	}

	public void setBankID(Integer bankID) {
		BankID = bankID;
	}

	public Integer getBankID() {
		return BankID;
	}

	public void setPersID(Integer persID) {
		PersID = persID;
	}

	public Integer getPersID() {
		return PersID;
	}

	public void setUNid(Integer uNid) {
		UNid = uNid;
	}

	public Integer getUNid() {
		return UNid;
	}

	public void setVersUNid(Integer versUNid) {
		VersUNid = versUNid;
	}

	public Integer getVersUNid() {
		return VersUNid;
	}

	public void setImmoID(Integer immoID) {
		ImmoID = immoID;
	}

	public Integer getImmoID() {
		return ImmoID;
	}

	public void setLebVersID(Integer lebVersID) {
		LebVersID = lebVersID;
	}

	public Integer getLebVersID() {
		return LebVersID;
	}
	
	public Double getBetrag() {
		return this.Betrag;
	}

	public void setBetrag(Double betrag) {
		this.Betrag = betrag;
	}

	public void setZinssatz(Double zinssatz) {
		Zinssatz = zinssatz;
	}

	public Double getZinssatz() {
		return Zinssatz;
	}

	public void setTilgungsrate(Double tilgungsrate) {
		Tilgungsrate = tilgungsrate;
	}

	public Double getTilgungsrate() {
		return Tilgungsrate;
	}

	public void setRestschuld(Double restschuld) {
		Restschuld = restschuld;
	}

	public Double getRestschuld() {
		return Restschuld;
	}

	/**
	 * Speichert die Darlehen in der Datenbank. Ist noch keine ID vergeben
	 * worden, wird die generierte Id von DB2 geholt und dem Model übergeben.
	 */
	public void save() throws SQLException {
		// Hole Verbindung
		Connection con = DB2ConnectionManager.getInstance().getConnection();
	
		// Füge neues Element hinzu, wenn das Objekt noch keine ID hat.
		if (getIid() == -1) {
			// Achtung, hier wird noch ein Parameter mitgegeben,
			// damit später generierte IDs zurückgeliefert werden!
			String insertSQL = "INSERT INTO darlehen (BankID,PersID,UNid,VersUNid,ImmoID,LebVersID,Betrag,Zinssatz,Tilgungsrate,Restschuld) VALUES (?,?,?,?,?,?,?,?,?,?)";
	
			PreparedStatement pstmt = con.prepareStatement(insertSQL,
					Statement.RETURN_GENERATED_KEYS);
	
			// Setze Anfrageparameter und führe Anfrage aus
			pstmt.setInt(1, this.getBankID());
			if ( this.getPersID() != 0 ) {
				pstmt.setInt(2, this.getPersID());
			} else {
				pstmt.setNull(2, java.sql.Types.INTEGER);
			}; // Set NULL value
			
			if ( this.getUNid() != 0 ) {
				pstmt.setInt(3, this.getUNid());
			} else {
				pstmt.setNull(3, java.sql.Types.INTEGER);
			}; // Set NULL value
			
			if ( this.getVersUNid() != 0 ) {
				pstmt.setInt(4, this.getVersUNid());
			} else {
				pstmt.setNull(4, java.sql.Types.INTEGER);
			}; // Set NULL value
			
			if ( this.getImmoID() != 0 ) {
				pstmt.setInt(5, this.getImmoID());
			} else {
				pstmt.setNull(5, java.sql.Types.INTEGER);
			}; // Set NULL value
			
			if ( this.getLebVersID() != 0 ) {
				pstmt.setInt(6, this.getLebVersID());
			} else {
				pstmt.setNull(6, java.sql.Types.INTEGER);
			}; // Set NULL value
			
			pstmt.setDouble(7, this.getBetrag());
			pstmt.setDouble(8, this.getZinssatz());
			
			if ( this.getTilgungsrate() != 0 ) {
				pstmt.setDouble(9, this.getTilgungsrate());
			} else {
				pstmt.setNull(9, java.sql.Types.NUMERIC);
			}; // Set NULL value
			
			if ( this.getRestschuld() != 0 ) {
				pstmt.setDouble(10, this.getRestschuld());
			} else {
				pstmt.setNull(10, java.sql.Types.INTEGER);
			}; // Set NULL value
			pstmt.executeUpdate();
	
			// Hole die Id des engefügten Datensatzes
			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				setIid(rs.getInt(1));
			}
	
			rs.close();
			pstmt.close();
		} else {
			// Falls schon eine ID vorhanden ist, mache ein Update...
			String updateSQL = "UPDATE darlehen SET BankID=?,PersID=?,UNid=?,VersUNid=?,ImmoID=?LebVersID=?,Betrag=?,Zinssatz=?,Tilgungsrate=?,Restschuld=? WHERE DarlID = ?";
			PreparedStatement pstmt = con.prepareStatement(updateSQL);
	
			// Setze Anfrage Parameter
			pstmt.setInt(1, this.getBankID());
			pstmt.setInt(2, this.getPersID());
			pstmt.setInt(3, this.getUNid());
			pstmt.setInt(4, this.getVersUNid());
			pstmt.setInt(5, this.getImmoID());
			pstmt.setInt(6, this.getLebVersID());
			pstmt.setDouble(7, this.getBetrag());
			pstmt.setDouble(8, this.getZinssatz());
			pstmt.setDouble(9, this.getTilgungsrate());
			pstmt.setDouble(10, this.getRestschuld());
			pstmt.setInt(11, this.getIid());
			pstmt.executeUpdate();
	
			pstmt.close();
		}
	}

	/**
	 * Lädt eine Darlehen via Id.
	 * 
	 * @param id
	 *            die Id des Datensatzes
	 * @return Darlehen
	 */
	public static Darlehen load(int iid) throws SQLException {
		// Hole Verbindung
		Connection con = DB2ConnectionManager.getInstance().getConnection();

		// Erzeuge Anfrage
		String selectSQL = "SELECT DarlID,BankID,PersID,UNid,VersUNid,LebVersID,ImmoID,Betrag,Zinssatz,Tilgungsrate,Restschuld FROM darlehen WHERE DarlID = ?";
		PreparedStatement pstmt = con.prepareStatement(selectSQL);
		pstmt.setInt(1, iid);

		// Führe Anfrage aus
		ResultSet rs = pstmt.executeQuery();
		if (rs.next()) {
			Darlehen i = new Darlehen();
			i.setIid(rs.getInt("DarlID"));
			i.setBetrag(rs.getDouble("Betrag"));
			i.setBankID(rs.getInt("BankID"));
			i.setPersID(rs.getInt("PersID"));
			i.setUNid(rs.getInt("UNid"));
			i.setVersUNid(rs.getInt("VersUNid"));
			i.setImmoID(rs.getInt("ImmoID"));
			i.setLebVersID(rs.getInt("LebVersID"));
			i.setBetrag(rs.getDouble("Betrag"));
			i.setZinssatz(rs.getDouble("Zinssatz"));
			i.setTilgungsrate(rs.getDouble("Tilgungsrate"));
			i.setRestschuld(rs.getDouble("Restschuld"));

			rs.close();
			pstmt.close();
			return i;
		} else {
			return null;
		}
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Darlehen) {
			Darlehen i = (Darlehen) o;
			return i.getIid().equals(this.getIid());
		}
		return false;
	}
}