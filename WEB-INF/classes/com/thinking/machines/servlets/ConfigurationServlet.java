package com.thinking.machines.servlets;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.net.*;
import com.thinking.machines.beans.*;
import com.thinking.machines.util.*;
import com.google.gson.*;
public class ConfigurationServlet extends HttpServlet{
 private Connection connection;
 private PreparedStatement preparedStatement;
 public void doGet(HttpServletRequest request, HttpServletResponse response){
  try{
   communicateDatabase();
   preparedStatement=connection.prepareStatement("select * from sensors where id=(select max(id) from sensors)");
   ResultSet resultSet=preparedStatement.executeQuery();
   String temp="", hum="", vgas="", vfire="";
   if(resultSet.next()){
     temp=resultSet.getString("tempValue");
     hum=resultSet.getString("humValue");
     vgas=resultSet.getString("gasValue");
     vfire=resultSet.getString("fireValue");
   }
   resultSet.close();
   haltCommunication();
   DataBean dataBean=new DataBean();
   dataBean.setTemperature(temp);
   dataBean.setHumidity(hum);
   dataBean.setGasSensorValue(vgas);
   dataBean.setFireSensorValue(vfire);
   AJAXResponse responseBag=new AJAXResponse();
   responseBag.setResponse(dataBean);
   responseBag.setSuccess(true);
   responseBag.setException("");
   Gson gson=new Gson();
   String resjson=gson.toJson(responseBag);
   response.setContentType("application/json");
   PrintWriter out=response.getWriter();
   out.write(resjson);
  }catch(Exception exception){
    exception.printStackTrace();
  }
 }
 private void communicateDatabase() throws Exception{
  Class.forName("com.mysql.cj.jdbc.Driver");
  connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/srsdb", "minor_p", "");
 }
 private void haltCommunication() throws Exception{
  preparedStatement.close();
  connection.close();
 }
}