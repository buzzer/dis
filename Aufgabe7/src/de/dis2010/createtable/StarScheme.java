package de.dis2010.createtable;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Statement;

import de.dis2010.DB2ConnectionManager;

public class StarScheme {
	
	private String sqlpath = "/Users/sebastian/dis/Aufgabe7/createStern.sql";
	
	public StarScheme () {
		//dummy
	}
	
	public void parseSql () {
				
		//Now read line bye line
		String thisLine, sqlQuery;
		try {
			
			FileInputStream fstream = new FileInputStream(sqlpath);
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader d = new BufferedReader(new InputStreamReader(in));
			
			// Hole Verbindung
			Connection con = DB2ConnectionManager.getInstance().getConnection();
			// Erzeuge Anfrage
			Statement stmt = con.createStatement();
			
			sqlQuery = "";
		    while ((thisLine = d.readLine()) != null) 
		    {  
		        //Skip comments and empty lines
		        if(thisLine.length() > 0 && thisLine.charAt(0) == '-' || thisLine.length() == 0 ) 
		            continue;
		        sqlQuery = sqlQuery + " " + thisLine;
		        //If one command complete
		        if(sqlQuery.charAt(sqlQuery.length() - 1) == ';') {
		            sqlQuery = sqlQuery.replace(';' , ' '); //Remove the ; since jdbc complains
		            try {
		            	System.out.println(sqlQuery);
		                stmt.execute(sqlQuery);
		            }
		            catch(Exception e) {
		            	e.printStackTrace();
		            }
		            sqlQuery = "";
		        }   
		    }
		    
		    stmt.close();
		}
		
		catch(Exception e) {
			e.printStackTrace();
		}		
	}
}
