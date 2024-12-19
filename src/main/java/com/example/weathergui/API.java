package com.example.weathergui;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;
import java.util.Random;

public class API {
    private final String APIKEY = "PUT_UR_KEY";
    private String city;
    private String apiURL;

    public void update(WeatherData item) {
        city = item.getCity();
        setApiURL(city, APIKEY);
        OkHttpClient client = new OkHttpClient();

        try {
            Request request = new Request.Builder().url(apiURL).get().addHeader("Content-Type", "application/json").build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    JSONObject jsonResponse = new JSONObject(responseData);

                    String cityName = jsonResponse.getString("name");
                    double temperature = jsonResponse.getJSONObject("main").getDouble("temp");
                    int humidity = jsonResponse.getJSONObject("main").getInt("humidity");
                    double pressure = jsonResponse.getJSONObject("main").getDouble("pressure");
                    double windSpeed = jsonResponse.getJSONObject("wind").getDouble("speed");
                    int cloudiness = jsonResponse.getJSONObject("clouds").getInt("all");
                    int visibility = jsonResponse.getInt("visibility");
                    long sunrise = jsonResponse.getJSONObject("sys").getLong("sunrise");
                    long sunset = jsonResponse.getJSONObject("sys").getLong("sunset");

                    System.out.println("\n--------------------------------------------------");
                    System.out.println("Weather Information for " + cityName + ":");
                    System.out.println("--------------------------------------------------");
                    System.out.println("Temperature: " + temperature + "°C");
                    System.out.println("Humidity: " + humidity + "%");
                    System.out.println("Pressure: " + pressure + " hPa");
                    System.out.println("Wind Speed: " + windSpeed + " m/s");
                    System.out.println("Cloudiness: " + cloudiness + "%");
                    System.out.println("Visibility: " + visibility + " meters");
                    System.out.println("Sunrise: " + sunrise);
                    System.out.println("Sunset: " + sunset);
                    System.out.println("--------------------------------------------------\n");

                    item.setTemperature(temperature + "°C");
                    item.setHumidity(humidity + "%");
                    item.setWindSpeed(windSpeed + " m/s");
                    item.setPressure(pressure + " hPa");
                    item.setCloudiness(cloudiness + "%");
                } else {
                    System.out.println("Failed to fetch data for " + city + ". HTTP Code: " + response.code());
                    generateRandomWeather(item);
                }
            }

        } catch (Exception e) {
            System.out.println("No internet connection or error occurred: " + e.getMessage());
            generateRandomWeather(item);
        }
    }

    private void generateRandomWeather(WeatherData item) {
        Random rand = new Random();

        double temperature = rand.nextInt(35) - 10; // between -10°C and 25°C
        int humidity = rand.nextInt(101); // between 0% and 100%
        double pressure = rand.nextDouble() * (1050 - 980) + 980; // between 980 hPa and 1050 hPa
        double windSpeed = rand.nextDouble() * (20 - 0) + 0; // between 0 m/s and 20 m/s
        int cloudiness = rand.nextInt(101); // between 0% and 100%

        item.setTemperature(temperature + "°C");
        item.setHumidity(humidity + "%");
        item.setWindSpeed(windSpeed + " m/s");
        item.setPressure(pressure + " hPa");
        item.setCloudiness(cloudiness + "%");

        System.out.println("\n--------------------------------------------------");
        System.out.println("Weather Information (Random Data):");
        System.out.println("--------------------------------------------------");
        System.out.println("Temperature: " + temperature + "°C");
        System.out.println("Humidity: " + humidity + "%");
        System.out.println("Pressure: " + pressure + " hPa");
        System.out.println("Wind Speed: " + windSpeed + " m/s");
        System.out.println("Cloudiness: " + cloudiness + "%");
        System.out.println("--------------------------------------------------\n");
    }

    public void delete(WeatherData item) {
        item.setCloudiness("Loading...");
        item.setPressure("Loading...");
        item.setTemperature("Loading...");
        item.setHumidity("Loading...");
        item.setWindSpeed("Loading...");
    }

    public String getApiURL() {
        return apiURL;
    }

    public void setApiURL(String city, String apiKey) {
        this.apiURL = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey + "&units=metric";
    }
}
