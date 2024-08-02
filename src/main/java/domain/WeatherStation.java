package domain;

public class WeatherStation {
    private String windIntensity;
    private String windVelocity;
    private String temperature;
    private String humidity;
    private String precipitation;
    private String pressure;

    public WeatherStation(String windIntensity, String windVelocity, String temperature, String humidity, String precipitation, String pressure) {
        this.windIntensity = windIntensity;
        this.windVelocity = windVelocity;
        this.temperature = temperature;
        this.humidity = humidity;
        this.precipitation = precipitation;
        this.pressure = pressure;
    }

    public String getWindIntensity() {
        return windIntensity;
    }

    public String getWindVelocity() {
        return windVelocity;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getPrecipitation() {
        return precipitation;
    }

    public String getPressure() {
        return pressure;
    }

    public void setWindIntensity(String windIntensity) {
        this.windIntensity = windIntensity;
    }

    public void setWindVelocity(String windVelocity) {
        this.windVelocity = windVelocity;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public void setPrecipitation(String precipitation) {
        this.precipitation = precipitation;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String toString() {
        return "Wind Intensity: " + windIntensity + "\nWind Velocity: " + windVelocity + "\nTemperature: " + temperature + "\nHumidity: " + humidity + "\nPrecipitation: " + precipitation + "\nPressure: " + pressure;
    }
}
