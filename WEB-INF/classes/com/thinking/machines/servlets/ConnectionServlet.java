package com.thinking.machines.servlets;
import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.google.gson.*;
import com.thinking.machines.util.*;
public class ConnectionServlet extends HttpServlet{
 public void doGet(HttpServletRequest request, HttpServletResponse response){
  try{
   new ConnectDevice().start();
   AJAXResponse responseBag=new AJAXResponse();
   responseBag.setResponse("");
   responseBag.setSuccess(true);
   responseBag.setException("");
   response.setContentType("aplication/json");
   Gson gson=new Gson();
   String jsonStr=gson.toJson(responseBag);
   response.getWriter().write(jsonStr);
  }catch(Exception exception){
    exception.printStackTrace();
  }
 }
}