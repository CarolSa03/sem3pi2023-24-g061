package domain.BasketDistribution;

import java.time.LocalTime;
import java.util.Objects;

public class Route {
    private Local leavingLocal;
    private Local arrivingLocal;
    private Double distance;
    private LocalTime leavingTime;
    private LocalTime arrivingTime;
    private boolean leavingLocalIsHub;
    private boolean arrivingLocalIsHub;

    public Route() {
    }

    public Route(Local leavingLocal, Local arrivingLocal, Double distance, LocalTime leavingTime, LocalTime arrivingTime) {
        setLeavingLocal(leavingLocal);
        setArrivingLocal(arrivingLocal);
        setDistance(distance);
        setLeavingTime(leavingTime);
        setArrivingTime(arrivingTime);
    }

    public boolean getLeavingLocalIsHub() {
        return leavingLocalIsHub;
    }

    public void setLeavingLocalIsHub(boolean leavingLocalIsHub) {
        this.leavingLocalIsHub = leavingLocalIsHub;
    }

    public boolean getArrivingLocalIsHub() {
        return arrivingLocalIsHub;
    }

    public void setArrivingLocalIsHub(boolean arrivingLocalIsHub) {
        this.arrivingLocalIsHub = arrivingLocalIsHub;
    }

    public Local getLeavingLocal() {
        return leavingLocal;
    }

    public void setArrivingLocal(Local arrivingLocal) {
        this.arrivingLocal = arrivingLocal;
    }

    public Local getArrivingLocal() {
        return arrivingLocal;
    }

    public void setLeavingLocal(Local leavingLocal) {
        this.leavingLocal = leavingLocal;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public LocalTime getLeavingTime() {
        return leavingTime;
    }

    public void setLeavingTime(LocalTime leavingTime) {
        this.leavingTime = leavingTime;
    }

    public LocalTime getArrivingTime() {
        return arrivingTime;
    }

    public void setArrivingTime(LocalTime arrivingTime) {
        this.arrivingTime = arrivingTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Route route = (Route) o;
        return Objects.equals(leavingLocal, route.leavingLocal) && Objects.equals(arrivingLocal, route.arrivingLocal) && Objects.equals(distance, route.distance) && Objects.equals(leavingTime, route.leavingTime) && Objects.equals(arrivingTime, route.arrivingTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(leavingLocal, arrivingLocal, distance, leavingTime, arrivingTime);
    }

    @Override
    public String toString() {
        String string = leavingLocal + "";
        if (this.leavingLocalIsHub) string += " [Hub with " + leavingLocal.getId() + " collaborators] --> ";
        else string += " --> ";
        if (this.arrivingLocalIsHub) string += arrivingLocal + " [Hub with " + arrivingLocal.getId() + " collaborators]";
        else string += arrivingLocal + "";

        return String.format("%-75s", string) + "|\t" +
                String.format("%-25s", "Distance: " + distance + " Km ") + "|\t" +
                String.format("%-25s", "Leave from " + leavingLocal + " at " + leavingTime +
                "h & Arrive at " + arrivingLocal + " at " + arrivingTime + "h");
    }
}
