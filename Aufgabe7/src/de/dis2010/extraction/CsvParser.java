package de.dis2010.extraction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import de.dis2010.DB2ConnectionManager;
import de.dis2010.data.Faktum;
import de.dis2010.data.Shop;
import de.dis2010.data.Zeit;

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
				System.out.println (strLine);
				if (strLine.compareTo("") != 0) {
					strArray = strLine.split(";");
					// Split csv line and save tupel in faktentablle
					System.out.println("Parse csv line ..");
					parseCsvLine(strArray);
					System.out.println("..done");
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
		Zeit z   = new Zeit();
		
		try {
			
			// Handle null or empty fields
			for (int i=0; i<s.length; i++) {
				if (s[i].compareTo("") == 0 || s[i].compareTo("0") == 0) {
					s[i] = "0";
				}
			}
			//System.out.println(s[0]);
			
			// Save faktum in Date Warehouse
			f.setDatum(s[0]);
			//System.out.println(s[1]);
			f.setShop(s[1]);
			//System.out.println(s[2]);
			f.setArtikel(s[2]);
			//System.out.println(s[3]);
			f.setVerkauft(Integer.valueOf(s[3]).intValue());
			//System.out.println(s[4].replace(',','.'));
		
			f.setUmsatz(Double.valueOf(s[4].replace(',','.')).doubleValue());
			//System.out.println(f.toString());
			
			// Save Datum and its conversions in Data Warehouse
			z.setDatum(s[0]);
			z.save();
			// TODO fix date in sql
			f.save();
			System.out.println("Saving Faktum .. done");


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
			s.save();
			
			rs.close();
			pstmt.close();
			//return a;
		//} else {
			//return null;
		}
	}

}
