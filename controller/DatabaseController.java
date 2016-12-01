package org.jimmy.suggestion.DB.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import org.jimmy.suggestion.DB.objects.Suggestion;

public class DatabaseController {
	private static ResultSet res;
	private static String url;
    private static String user;
    //this is purely offline at the moment on a local database, no need for a password yet
    private static String password = "";

	private static ArrayList<Suggestion> suggestions = new ArrayList<Suggestion>();

	public static ArrayList<Suggestion> getSuggestionsList(){
		return suggestions;
	}
	
	public static void init() throws SQLException{
		File f = new File("DatabaseCredentials.txt");
		String curDir = System.getProperty("user.dir");
		System.out.println(curDir);
		try {
			Scanner s = new Scanner(f);
			url = s.nextLine();
			user = s.nextLine();
			s.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(suggestions.isEmpty()){
			try{
	        	Class.forName("com.mysql.jdbc.Driver").newInstance();
	        	Connection con = DriverManager.getConnection(url,user,password);
	        	Statement st = con.createStatement();
	        	res = st.executeQuery("Select * FROM suggestions");
	        	while(res.next()){
	        		suggestions.add(new Suggestion(res.getString("Message"), new Date(res.getTimestamp("date").getTime())
	        				,res.getInt("rating"), res.getInt("id")));
	        	}
	        	st.close(); 
	        	res.close();
	        	con.close();
	        } catch (Exception e){
	        	e.printStackTrace();
	        }
		}
	}

	public static void saveToDB(){
	    try{
	    	Class.forName("com.mysql.jdbc.Driver").newInstance();
	    	Connection con = DriverManager.getConnection(url,user,password);
	    	Statement st = con.createStatement();
	    	st.execute("TRUNCATE Suggestions");
	    	for(Suggestion s: suggestions){
	    		st.execute("INSERT INTO suggestions VALUES('" + s.getMessage()+ "','"+ 
	    				new java.sql.Timestamp(s.getDate().getTime())+
	    				"','"+s.getRating()+"','" + s.getId() + "')");
	    	}
	    	st.close();
	    	con.close();
	    } catch (Exception e){
	    	e.printStackTrace();
	    }
	}


}
