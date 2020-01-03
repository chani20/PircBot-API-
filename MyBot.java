import org.jibble.pircbot.*;
import org.json.simple.JSONObject;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL; 

import java.lang.*;

public class MyBot extends PircBot 
{
    int counter=0;//counter variable to keep track of how many times the onmessage() function is called 
    
    public MyBot() 
    {
        this.setName("WeatherIRC");//name to join the server 
    }
    
    //everytime a message is sent, this method will be called 
    public void onMessage(String channel, String sender,String login, String hostname, String message) 
    {
    	//calls the function, converts it into double, converts kelvin to farenheit, and then returns weather in server
    	if(counter==3)
    	{
    		try {
    			String result=getCityWeather(message);//returns weather into string
    			double doubletemp=Double.parseDouble(result);//converts weather into double 
    			
    			double contemp=(1.8)*(doubletemp-273)+32.0;//converts kelvin to farenheit
    			String stringtemp=Double.toString(contemp);//converts double back to string 
    			sendMessage(channel, "It is " + stringtemp + " degrees in the given location");
        		} catch (Exception e) {
    			e.printStackTrace();
        		}
    	}
    	
    	//based on give me the weather, asks for the location, and then increments 
    	if(counter==2)
    	{
    		sendMessage(channel, "What is the location?"); 				
    		counter++;
    	}
    	
    	//if the input is this, sets counter to 1 and increments to next question
    	if(message.contains("Give me the weather!"))
    	{  		
    		counter=1;
    		counter++;
    	}
    	
    	//calls function to get a random fact based on input
    	if (message.contains("Give me an useless fact!")) 
    	{
    		//exception to call function
    		try {
				String result = getRandomFact();
				sendMessage(channel, result);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
    	}
    }
    
    //this function calls an API to give you the weather
    public static String getCityWeather(String message)
    {
    	try
    	{
    		char[] chararray=message.toCharArray();//converts string to char array
    		
    		if(Character.isLetter(chararray[0]))//if it starts with a letter, it's a city and call city endpoint 
    		{
    			String APIkey = "2909e5f110bd59507e4e5e1f3cd38840";
    			String endpoint="http://api.openweathermap.org/data/2.5/weather?q="; 
                endpoint += message;
                endpoint += "&appid=";
                endpoint += APIkey;
                
              //based on powerpoint, create URL and HTTP objects, set request method
              //then create a BR and SB object, and put the input into a String 
                URL url = new URL(endpoint);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuffer resultbuff = new StringBuffer();
                
                String input;
                while((input=in.readLine()) != null)
                {
                	resultbuff.append(input);
                }
                in.close();
                con.disconnect();
                
              //parse String through Json parser, and then invoke JSon tree model to return String 
                JsonParser parser = new JsonParser();
                String result = resultbuff.toString();
                
                JsonElement jsonTree = parser.parse(result);
                if(jsonTree.isJsonObject())
                {
                	JsonObject jweather = jsonTree.getAsJsonObject();
                	JsonElement temp = jweather.get("main");
                	
                	String tempbot= temp.toString();
                	
                	JsonElement jsonTree2=parser.parse(tempbot);
                	if(jsonTree2.isJsonObject())
                	{
                		JsonObject jweather2 = jsonTree2.getAsJsonObject();
                    	JsonElement temp2 = jweather2.get("temp");
                    	
                    	String tempbot2= temp2.toString();
                    	return tempbot2;
                	}
                	else
                	{
                		return "errorelse";
                	}
                }
                else
                {
                	return "errorelse";
                }
    		}
    		else if(Character.isDigit(chararray[0]))//if it starts with a digit, it's zip code and call zip code endpoint 
    		{
    			String APIkey = "2909e5f110bd59507e4e5e1f3cd38840";
    			String endpoint="http://api.openweathermap.org/data/2.5/weather?zip="; 
                endpoint += message;
                endpoint += "&appid=";
                endpoint += APIkey;
                
              //based on powerpoint, create URL and HTTP objects, set request method
              //then create a BR and SB object, and put the input into a String 
                URL url = new URL(endpoint);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuffer resultbuff = new StringBuffer();
                
                String input;
                while((input=in.readLine()) != null)
                {
                	resultbuff.append(input);
                }
                in.close();
                con.disconnect();
                
              //parse String through Json parser, and then invoke JSon tree model to return String 
                JsonParser parser = new JsonParser();
                String result = resultbuff.toString();
                
                JsonElement jsonTree = parser.parse(result);
                if(jsonTree.isJsonObject())
                {
                	JsonObject jweather = jsonTree.getAsJsonObject();
                	JsonElement temp = jweather.get("main");
                	
                	String tempbot= temp.toString();
                	
                	JsonElement jsonTree2=parser.parse(tempbot);
                	if(jsonTree2.isJsonObject())
                	{
                		JsonObject jweather2 = jsonTree2.getAsJsonObject();
                    	JsonElement temp2 = jweather2.get("temp");
                    	
                    	String tempbot2= temp2.toString();
                    	return tempbot2;
                	}
                	else
                	{
                		return "errorelse";
                	}
                }
                else
                {
                	return "errorelse";
                }
    		}
    		else
    		{
    			return "error";
    		}
                 
            
    	}catch(Exception e3)
    	{
    		e3.printStackTrace();
    		return "error";
    	}
        
    }
    
    //this function calls an API to give the user a random fact
    public static String getRandomFact()
    { 
      try
      {
    	  //create endpoint 
    	  String endpoint = "https://uselessfacts.jsph.pl/";
      	  endpoint += "random.json?language=en";
      	  
      	  //based on powerpoint, create URL and HTTP objects, set request method
      	  //then create a BR and SB object, and put the input into a String 
      	  URL url = new URL(endpoint);
          HttpURLConnection con = (HttpURLConnection) url.openConnection();
          con.setRequestMethod("GET");
          
          BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
          StringBuffer resultbuff = new StringBuffer();
          
          String input;
          while((input=in.readLine()) != null)
          {
          	resultbuff.append(input);
          }
          in.close();
          con.disconnect();
          
          //parse String through Json parser, and then invoke JSon tree model to return String 
          JsonParser parser = new JsonParser();
          String result = resultbuff.toString();
          
          JsonElement jsonTree=parser.parse(result);
          if(jsonTree.isJsonObject())
          {
          	JsonObject jsonObject = jsonTree.getAsJsonObject();
          	JsonElement joke = jsonObject.get("text");     
          	
          	String jokeStr=joke.toString();
          	return jokeStr;
          }
          else
          {
        	  return "errorelse";
          }
    	  
      }catch(Exception e2)
      {
    	  e2.printStackTrace();
    	  return "error";
      }
    }
    
}
