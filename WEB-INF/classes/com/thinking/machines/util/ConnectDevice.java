package com.thinking.machines.util;
import java.io.*;
import java.util.*;
import java.sql.*;
import java.net.*;
public class ConnectDevice extends Thread{

 private static String serverIP;
 private static int portNumber=7007;
 private String gasValue;
 private String fireValue;
 private String tempValue;
 private String humValue;
 private Connection connection;
 private boolean status;
 private PreparedStatement preparedStatement;

 public ConnectDevice() throws Exception{
  new Timer().schedule(new GarbageCollector(), 1000,10*1000);
  serverIP="192.168.137.59";
  status=false;
 }
 public boolean getStatus(){
  return this.status;
 }
 public void run(){
  try{
   connectToDeviceServer();
  }catch(Exception exception){
    exception.printStackTrace();
  }
 }
 private void connectToDeviceServer() throws Exception{
  Socket socket;
  InputStreamReader inputStreamReader;
  int x;
  StringBuffer stringBuffer;
  while(true){
   System.out.println("Tring to connect to the server...");
   socket=new Socket(serverIP, portNumber);
   System.out.println("Connected to the server.");
   this.status=true;
   inputStreamReader=new InputStreamReader(socket.getInputStream());
   stringBuffer=new StringBuffer();
   while(true){
    x=inputStreamReader.read();
    if(x=='\r' || x=='\n' || x==-1) break;
    stringBuffer.append((char)x);
   }
   String response=stringBuffer.toString();
   humValue=response.substring(0,5);
   tempValue=response.substring(5,10);
   gasValue=response.substring(10,15);
   fireValue=response.substring(15,20);
   storetoDatabase();
  }
 }
 private void storetoDatabase() throws Exception{
   connectToDatabase();
   preparedStatement=connection.prepareStatement("INSERT into sensors(tempValue, humValue, gasValue, fireValue) values("+tempValue+", "+humValue+","+gasValue+","+fireValue+")");
   preparedStatement.executeUpdate();
   System.out.println("Content has been written to the database");
   haltConnection();
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