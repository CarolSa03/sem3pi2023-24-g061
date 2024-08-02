package domain;

public class SoilSensor {
    private String humidity;
    private String temperature;
    private String pH;
    private String salinity;

    public SoilSensor(String humidity, String temperature, String pH, String salinity) {
        this.humidity = humidity;
        this.temperature = temperature;
        this.pH = pH;
        this.salinity = salinity;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getpH() {
        return pH;
    }

    public String getSalinity() {
        return salinity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public void setpH(String pH) {
        this.pH = pH;
    }

    public void setSalinity(String salinity) {
        this.salinity = salinity;
    }

    @Override
    public String toString() {
        return "SoilSensor{" +
                "humidity='" + humidity + '\'' +
                ", temperature='" + temperature + '\'' +
                ", pH='" + pH + '\'' +
                ", salinity='" + salinity + '\'' +
                '}';
    }
}
