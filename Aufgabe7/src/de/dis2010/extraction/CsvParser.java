package de.dis2010.extraction;

//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;

//import de.dis2010.DB2ConnectionManager;
import de.dis2010.data.Faktum;
//import de.dis2010.data.Shop;
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
			
			System.out.println("Parse csv file ..");

			// Omit first (header) line
			strLine = br.readLine();
			//Read File Line By Line
			Integer line = 0;
			while ((strLine = br.readLine()) != null)   {
				line = 1 + line;
				if (line % 10 == 0) {
					System.out.println(line+" datasets ok ..");
				}
				// Print the content on the console
				//System.out.println (strLine);
				if (strLine.compareTo("") != 0) {
					strArray = strLine.split(";");
					// Split csv line and save tupel in faktentablle
					//System.out.println("Parse csv line ..");
					parseCsvLine(strArray);
					//System.out.println("..done");
				} else { /* Empty Line */ }
			}
			
			System.out.println(".. done");
			
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
			
			// Parse DATE
			//System.out.println(s[0]);
			// convert from dd.mm.yyyy to yyyy-mm-dd
			String jdbcDate = CsvParser.date2jdbc(s[0]);
			f.setDatum(jdbcDate);
			
			// Parse SHOP
			//System.out.println(s[1]);
			f.setShop(s[1]);
			
			// Parse ARTIKEL
			//System.out.println(s[2]);
			f.setArtikel(s[2]);
			
			// Parse VERKAUFT
			//System.out.println(s[3]);
			f.setVerkauft(Integer.valueOf(s[3]).intValue());
			
			// Parse UMSATZ
			//System.out.println(s[4].replace(',','.'));
			f.setUmsatz(Double.valueOf(s[4].replace(',','.')).doubleValue());
			
			//System.out.println(f.toString());
			
			// Save Datum and its conversions in Data Warehouse
			z.setDatum(jdbcDate);
			z.save();
			
			// Save faktum in Data Warehouse
			f.save();
			//System.out.println("Saving Faktum .. done");


		} catch (Exception e) {
			e.printStackTrace();
		}
		return f;
	}
	
	// Converts date string dd.mm.yyyy to yyyy-mm-dd
	public static String date2jdbc(String d) {
	
		String jdbcDate = null;
		
		java.text.DateFormat csvDf = new java.text.SimpleDateFormat("dd.MM.yyyy");
		java.text.DateFormat jdbcDf = new java.text.SimpleDateFormat("yyyy-MM-dd");
		
		try {
			java.util.Date csvDate = csvDf.parse(d);
			jdbcDate = jdbcDf.format(csvDate); 
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return jdbcDate;
	}

}
