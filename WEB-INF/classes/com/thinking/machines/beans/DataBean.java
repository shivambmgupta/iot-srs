package com.thinking.machines.beans;
import java.io.*;
public class DataBean implements Serializable{
 private String temperature;
 private String humidity;
 private String gasSensorValue;
 private String fireSensorValue;
 public void setTemperature(String temperature){
  this.temperature=temperature;
 }
 public String getTemperature(){
  return this.temperature;
 }
 public void setHumidity(String humidity){
  this.humidity=humidity;
 }
 public String getHumidity(){
  return this.humidity;
 }
 public void setGasSensorValue(String gasSensorValue){
  this.gasSensorValue=gasSensorValue;
 }
 public String getGasSensorValue(){
  return this.gasSensorValue;
 }
 public void setFireSensorValue(String fireSensorValue){
  this.fireSensorValue=fireSensorValue;
 }
 public String getFireSensorValue(){
  return this.fireSensorValue;
 }

}