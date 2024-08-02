package domain.BasketDistribution;

import ESINF.graph.Graph;
import ESINF.graph.map.MapGraph;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Cluster {
    private Hub hub;
    private List<Local> locals;

    public Cluster() {
    }

    public Cluster(Hub hub) {
        setHub(hub);
    }

    public Cluster(Hub hub, List<Local> locals) {
        setHub(hub);
        setLocals(locals);
    }

    public Hub getHub() {
        return hub;
    }

    public void setHub(Hub hub) {
        this.hub = hub;
    }

    public List<Local> getLocals() {
        return locals;
    }

    public void setLocals(List<Local> locals) {
        this.locals = locals;
    }

    public void addLocal(Local local) {
        if (locals == null) {
            locals = new ArrayList<>();
        }
        locals.add(local);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cluster cluster = (Cluster) o;
        return Objects.equals(hub, cluster.hub) && Objects.equals(locals, cluster.locals);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hub, locals);
    }

    @Override
    public String toString() {
        return "Hub: " + hub +
                " -> Locals: " + locals;
    }
}

