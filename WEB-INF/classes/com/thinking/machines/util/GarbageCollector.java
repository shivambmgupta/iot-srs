package com.thinking.machines.util;
import java.io.*;
import java.util.*;
import java.sql.*;
import java.net.*;

public class GarbageCollector extends TimerTask{
 private Connection connection;
 private PreparedStatement preparedStatement;
 public void run(){
  try{
   connectToDatabase();
   preparedStatement=connection.prepareStatement("SELECT COUNT(*) from sensors");
   ResultSet res=preparedStatement.executeQuery();
   res.next();
   int size=res.getInt("COUNT(*)");
   System.out.println("Size is: "+size);
   if(size>150){
     preparedStatement=connection.prepareStatement("DELETE FROM sensors LIMIT 100;");
     preparedStatement.executeUpdate();
   }
   haltConnection();
  }catch(Exception exception){
   exception.printStackTrace();
  }
 }
 private void connectToDatabase() throws Exception{
  Class.forName("com.mysql.cj.jdbc.Driver");
  connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/srsdb","minor_p","");
 }
 private void haltConnection() throws Exception{
   preparedStatement.close();
   connection.close();
 }
}