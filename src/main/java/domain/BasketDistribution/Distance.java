package domain.BasketDistribution;

import java.util.Objects;

public class Distance {
    private String local1;
    private String local2;
    private double length;

    public Distance(String local1, String local2, double length) {
        setLocal1(local1);
        setLocal2(local2);
        setLength(length);
    }

    public Distance() {
    }

    public String getLocal1() {
        return local1;
    }

    public void setLocal1(String local1) {
        if (local1 == null) {
            local1 = "Local 1 Not Found";
        }

        this.local1 = local1;
    }

    public String getLocal2() {
        return local2;
    }

    public void setLocal2(String local2) {
        if (local2 == null) {
            local2 = "Local 2 Not Found";
        }

        this.local2 = local2;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        if (length < 0) {
            length = 0;
        }
        this.length = length;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Distance distances = (Distance) o;
        return length == distances.length && Objects.equals(local1, distances.local1) && Objects.equals(local2, distances.local2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(local1, local2, length);
    }

    @Override
    public String toString() {
        return "Distances{" +
                "local1='" + local1 +
                ", local2='" + local2 +
                ", length=" + length +
                '}';
    }
}
