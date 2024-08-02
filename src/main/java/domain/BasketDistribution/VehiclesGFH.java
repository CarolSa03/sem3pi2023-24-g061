package domain.BasketDistribution;

import java.util.Objects;

public class VehiclesGFH {
    private double avgAutonomy;
    private double avgVelocity;
    private int avgDischargeTime;
    private double accumulatedDistance;


    public VehiclesGFH(double avgAutonomy, double avgVelocity, int avgDischargeTime) {
        setAvgAutonomy(avgAutonomy);
        setAvgVelocity(avgVelocity);
        setAvgDischargeTime(avgDischargeTime);
    }

    public double getAvgAutonomy() {
        return avgAutonomy;
    }

    public void setAvgAutonomy(double avgAutonomy) {
        if (avgAutonomy < 0) avgAutonomy = -1 * avgAutonomy;

        this.avgAutonomy = avgAutonomy;
    }

    public double getAvgVelocity() {
        return avgVelocity;
    }

    public void setAvgVelocity(double avgVelocity) {
        if (avgVelocity < 0) avgVelocity = -1 * avgVelocity;

        this.avgVelocity = avgVelocity;
    }

    public int getAvgDischargeTime() {
        if (avgDischargeTime < 0) avgDischargeTime = -1 * avgDischargeTime;

        return avgDischargeTime;
    }

    public void setAvgDischargeTime(int avgDischargeTime) {
        this.avgDischargeTime = avgDischargeTime;
    }

    public double getAccumulatedDistance() {
        return accumulatedDistance;
    }

    public void setAccumulatedDistance(double accumulatedDistance) {
        this.accumulatedDistance = accumulatedDistance;
    }

    @Override
    public String toString() {
        return String.format("""
                Vehicle:
                \tAverage autonomy = %.0fkm
                \tAverage velocity = %.0fkm/h
                \tAverage discharge time = %dmin
                """, avgAutonomy, avgVelocity, avgDischargeTime);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VehiclesGFH that = (VehiclesGFH) o;
        return Double.compare(avgAutonomy, that.avgAutonomy) == 0 && Double.compare(avgVelocity, that.avgVelocity) == 0 && Double.compare(avgDischargeTime, that.avgDischargeTime) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(avgAutonomy, avgVelocity, avgDischargeTime);
    }

}
