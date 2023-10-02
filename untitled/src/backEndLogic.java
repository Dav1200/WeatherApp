import jdk.jshell.spi.ExecutionControlProvider;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class backEndLogic {

    public static JSONObject getWeatherData(String location){


        JSONArray locationData = getLocation(location);
        assert locationData != null;
        JSONObject locationa = (JSONObject) locationData.get(0);
        double latitude = (double) locationa.get("latitude");
        double longitude = (double) locationa.get("longitude");

        String urlstring = "https://api.open-meteo.com/v1/forecast?latitude="+latitude+"&longitude="+longitude+
                "&hourly=temperature_2m,relativehumidity_2m,weathercode,windspeed_10m&timezone=Europe%2FLondon";

        try {


            HttpURLConnection conn = apiResponse(urlstring);

            if(conn.getResponseCode()!= 200){
                System.out.println("error datta");
                return  null;

            }

            StringBuilder resultjson = new StringBuilder();
            Scanner scanner = new Scanner(conn.getInputStream());
            while (scanner.hasNext()){
                resultjson.append(scanner.nextLine());
            }
            scanner.close();
            conn.disconnect();

            JSONParser parse = new JSONParser();
            JSONObject resultsobject = (JSONObject) parse.parse(String.valueOf(resultjson));

            JSONObject hours = (JSONObject) resultsobject.get("hourly");

            JSONArray time = (JSONArray) hours.get("time");
            int index = findIndexOfCurrentTime(time);

            JSONArray tempdata = (JSONArray) hours.get("temperature_2m");
            double temp = (double) tempdata.get(index);

            JSONArray  weatherCode = (JSONArray) hours.get("weathercode");
            String weathercondition = convertWeatherCode((Long) weatherCode.get(index));

            JSONArray humidity = (JSONArray) hours.get("relativehumidity_2m");
            long humid = (long) humidity.get(index);

            JSONArray windsppedob = (JSONArray) hours.get("windspeed_10m");
            double wind = (double) windsppedob.get(index);


            JSONObject weatherdata = new JSONObject();
            weatherdata.put("temperature",temp);
            weatherdata.put("weather_condition",weathercondition);
            weatherdata.put("humidity", humid);
            weatherdata.put("windspeed",wind);
            return weatherdata;


        }catch (Exception e)
        {e.printStackTrace();}

        return null;
    }
    private static int findIndexOfCurrentTime(JSONArray timeList){
        String currentTime = getCurrentTime();

        // iterate through the time list and see which one matches our current time
        for(int i = 0; i < timeList.size(); i++){
            String time = (String) timeList.get(i);
            if(time.equalsIgnoreCase(currentTime)){
                // return the index
                return i;
            }
        }

        return 0;
    }
    private static String getCurrentTime(){
        // get current date and time
        LocalDateTime currentDateTime = LocalDateTime.now();

        // format date to be 2023-09-02T00:00 (this is how is is read in the API)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH':00'");

        // format and print the current date and time
        String formattedDateTime = currentDateTime.format(formatter);

        return formattedDateTime;
    }

    // convert the weather code to something more readable
    private static String convertWeatherCode(long weathercode){
        String weatherCondition = "";
        if(weathercode == 0L){
            // clear
            weatherCondition = "Clear";
        }else if(weathercode > 0L && weathercode <= 3L){
            // cloudy
            weatherCondition = "Cloudy";
        }else if((weathercode >= 51L && weathercode <= 67L)
                || (weathercode >= 80L && weathercode <= 99L)){
            // rain
            weatherCondition = "Rain";
        }else if(weathercode >= 71L && weathercode <= 77L){
            // snow
            weatherCondition = "Snow";
        }

        return weatherCondition;
    }
    public static JSONArray getLocation(String location){

        location = location.replaceAll(" ","+");

        //api
        String urlString = "https://geocoding-api.open-meteo.com/v1/search?name="+
                location +
                "&count=10&language=en&format=json";

        try {

            HttpURLConnection conn =  apiResponse(urlString);

            if(conn.getResponseCode() !=200){
                System.out.println("ERROR: cant connect API");
            return null;
            }
            else {
                StringBuilder jsonresult = new StringBuilder();
                Scanner scanner = new Scanner(conn.getInputStream());

                while (scanner.hasNext()){
                    jsonresult.append(scanner.nextLine());
                }
                scanner.close();
                conn.disconnect();

            JSONParser parser = new JSONParser();
            JSONObject result1 = (JSONObject) parser.parse(String.valueOf(jsonresult));

            JSONArray asd = (JSONArray) result1.get("results");

            return asd;


            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static HttpURLConnection apiResponse(String url){
        try {


        URL u = new URL(url);

        HttpURLConnection conn = (HttpURLConnection) u.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();
        return conn;


    }catch (IOException e)
        {e.printStackTrace();}
return null;
    }


}
