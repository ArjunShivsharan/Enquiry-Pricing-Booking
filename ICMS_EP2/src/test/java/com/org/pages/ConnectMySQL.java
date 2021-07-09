package com.org.pages;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ConnectMySQL 
{
   
   public void enquiryDB() throws Exception 
   {
	  // Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver");
	   
	   Connection connection=DriverManager.getConnection("jdbc:sqlserver://150.242.14.21;databaseName=ICMS","sa","pass@123$");
	
	   String sp ="SELECT * FROM EnquiryDetails WHERE CreatedDate = (SELECT MAX(CreatedDate) FROM EnquiryDetails)";
       Statement statement = connection.createStatement();
       ResultSet rs = statement.executeQuery(sp);
      
       while (rs.next()) {
    	   String enquiryno = rs.getString("EnquiryNo");
		   String pol = rs.getString("POL");
		   String finalplaceofdelivery = rs.getString("FinalPlaceofDelivery");
		   String createddate = rs.getString("CreatedDate");
		   System.out.println("|"+ enquiryno + "    |" + pol +"|" +finalplaceofdelivery +"     | "+ createddate +" | "+"\n" + "-----------");
		   
    	 }
       connection.close();
   }
}
