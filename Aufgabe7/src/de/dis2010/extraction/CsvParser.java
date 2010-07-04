package de.dis2010.extraction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import de.dis2010.DB2ConnectionManager;
import de.dis2010.data.Faktum;
import de.dis2010.data.Shop;

import java.io.*;
//import java.lang.String;

public class CsvParser {
	
	private static File csvpath = new File("/Users/sebastian/dis/Aufgabe7/sales_s.csv");
		
	public CsvParser() {
		// dummy
	}
	public void readCsv() {
		
		try{
			String [] strArray = null;
			// Open the file that is the first 
			// command line parameter
			FileInputStream fstream = new FileInputStream(csvpath);
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			// Omit first (header) line
			strLine = br.readLine();
			//Read File Line By Line
			while ((strLine = br.readLine()) != null)   {
				// Print the content on the console
				//System.out.println (strLine);
				if (strLine.compareTo("") != 0) {
					strArray = strLine.split(";");
					parseCsvLine(strArray);
				} else { /* Empty Line */ }
			}
			//Close the input stream
			in.close();
		}catch (Exception e){//Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
	}
	/**
	 * prints field of strings to stdout
	 * @param s Array of Strings
	 */
	public Faktum parseCsvLine(String []s) {
		Faktum f = new Faktum();
		try {
			
			// Handle null or empty fields
			for (int i=0; i<s.length; i++) {
				if (s[i].compareTo("") == 0 || s[i].compareTo("0") == 0) {
					s[i] = "0";
				}
			}
			
			f.setDatum(s[0]);
			f.setShop(s[1]);
			f.setArtikel(s[2]);
			f.setVerkauft(Integer.valueOf(s[3]).intValue());
			f.setUmsatz(Double.valueOf(s[4]).doubleValue());
			f.save(); // Persistence

		} catch (Exception e) {
			e.printStackTrace();
		}
		return f;
	}
	
	public void extractZeiten() throws SQLException {
		// Hole Verbindung
		Connection con = DB2ConnectionManager.getInstance().getConnection();
		
		// Erzeuge Anfrage
		String selectSQL = "select shopid,stadtid,name from db2inst1.shopid";
		PreparedStatement pstmt = con.prepareStatement(selectSQL);
		//pstmt.setInt(1, iid);

		// Führe Anfrage aus
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			Shop s = new Shop();
			s.setIid(rs.getInt("shopid"));
			//TODO convert s.setStadt(rs.getInt("stadtid"));
			s.setName(rs.getString("name"));
			//s.setProductCategory(rs.getInt("BankID"));
			//s.setPersID(rs.getInt("PersID"));
			//s.setUNid(rs.getInt("UNid"));
			s.transfer();
			
			rs.close();
			pstmt.close();
			//return a;
		//} else {
			//return null;
		}
	}
	/**
	 * Implements the ETL procedure for the Csv file
	 */
	public void etl() {
		this.readCsv();
		//TODO make etl conform
		//this.parseCsvLine();
		//this.injectData();
		
	}

}
