package com.org.pages;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


import org.testng.annotations.Test;

public class ConnectMySQL 
{
   @Test
   public void enquiryDB() throws Exception 
   {
	  // Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver");
	   
	   Connection connection=DriverManager.getConnection("jdbc:sqlserver://150.242.14.21;databaseName=ICMS","sa","pass@123$");
	   System.out.println("Connected to Database..!");
	   String sp ="Select * FROM TranshipmentMaster";
       Statement statement = connection.createStatement();
       ResultSet rs = statement.executeQuery(sp);
       System.out.println("TranshipmentId"+ "\t" + "Scope");
       while (rs.next()) {
    	   int TranshipmentId = rs.getInt("TranshipmentId");
    	   String scope = rs.getString("Scope");
    	   System.out.println("|" + TranshipmentId + "|" + scope +"|" + "\n" + "-----------");
    	   

    	 }
       connection.close();
   }
}
