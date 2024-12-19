package com.example.weathergui;

public class WeatherData {
    private final String city;
    private String temperature;
    private String humidity;
    private String windSpeed;
    private String pressure;
    private String cloudiness;

    public WeatherData(String city) { //=> بيانات مؤقتة لل table
        this.city = city;
        this.temperature = "Loading...";
        this.humidity = "Loading...";
        this.windSpeed = "Loading...";
        this.pressure = "Loading...";
        this.cloudiness = "Loading...";
    }

    public String getCity() {
        return city;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getCloudiness() {
        return cloudiness;
    }

    public void setCloudiness(String cloudiness) {
        this.cloudiness = cloudiness;
    }
}
