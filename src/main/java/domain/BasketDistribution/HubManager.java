package domain.BasketDistribution;

import ESINF.graph.Graph;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * The type Hub optimization.
 */
public class HubManager {

    private static final double firstIdRange = 105;
    private static final double secondIdRange = 215;
    private static final Graph<Local, Double> mapGraph = BasketDistributionMap.getBasketDistributionMap();
    private static List<Hub> hubs = new ArrayList<>();


    public HubManager() {
    }

    public static List<Hub> getHubs() {
        return hubs;
    }

    /**
     * This method order by influence criterion the N hubs.
     *
     * @param numberOfHubs the number of hubs
     */

    public static void setHubsList(int numberOfHubs, Comparator<Local> c) { // O(n log n)
        List<Local> locals = new ArrayList<>(BasketDistribution.getLocalsList()); // O(n)

        locals.sort(c); // O(n log n)
        Collections.reverse(locals); // O(n)

        List<Local> selectedLocals = locals.subList(0, Math.min(numberOfHubs, locals.size())); // O(1)

        insertHubsInFileList(localsToHubs(selectedLocals), c); // O(m)
    }

    /**
     * This method inserts the selected hubs in the hubs list.
     * @param selectedHubs the selected hubs
     * @param c the comparator
     */
    private static void insertHubsInFileList(List<Hub> selectedHubs, Comparator<Local> c) { // O(N * log(N))
        for (Hub hub : selectedHubs) if (!hubs.contains(hub)) hubs.add(hub); // O(m)
        hubs.sort(c); // O(n log n)
        Collections.reverse(hubs); // O(n)
        hubs = hubs.subList(0, Math.min(selectedHubs.size(), hubs.size())); // O(1)
    }

    /**
     * This method designs a local to a hub.
     * @param local the local
     * @return the hub
     */
    public static Hub localToHub(Local local)  { // O(1)
        Hub hub = new Hub();
        hub.setId(local.getId());
        hub.setDesignation(local.getDesignation());
        hub.setLat(local.getLat());
        hub.setLng(local.getLng());
        hub.setCollaborators(local.getId());
        getOperatingHours(hub);
        return hub;
    }

    /**
     * This method designs a list of locals to a list of hubs.
     * @param locals the locals
     * @return the list of hubs
     */
    public static List<Hub> localsToHubs(List<Local> locals) {  // O(n)
        List<Hub> hubs = new ArrayList<>(); // O(1)
        for (Local local : locals) { // O(n)
            hubs.add(localToHub(local));
        }
        return hubs;
    }

    /**
     * This method designs a hub to a local.
     * @param hub the hub
     * @return the local
     */
    public static Local hubToLocal(Hub hub) { // O(1)
        Local local = new Local(); // O(1)
        local.setId(hub.getId()); // O(1)
        local.setDesignation(hub.getDesignation()); // O(1)
        local.setLat(hub.getLat()); // O(1)
        local.setLng(hub.getLng()); // O(1)
        return local;
    }

    /**
     * This method designs a list of hubs to a list of locals.
     * @param hubs the hubs
     * @return the list of locals
     */
    public static List<Local> hubsToLocals(List<Hub> hubs) { // O(n)
        List<Local> locals = new ArrayList<>();
        for (Hub hub : hubs) { // O(n)
            locals.add(hubToLocal(hub));
        }
        return locals;
    }

    /**
     * This method gets the operating hours of a hub.
     * @param hub the hub
     */
    private static void getOperatingHours(Hub hub) { // O(1)
        if (hub.getId() <= firstIdRange) {
            hub.setOperatingHoursStart(LocalTime.of(9, 0));
            hub.setOperatingHoursEnd(LocalTime.of(14, 0));
        } else if (hub.getId() <= secondIdRange) {
            hub.setOperatingHoursStart(LocalTime.of(11, 0));
            hub.setOperatingHoursEnd(LocalTime.of(16, 0));
        } else {
            hub.setOperatingHoursStart(LocalTime.of(14, 0));
            hub.setOperatingHoursEnd(LocalTime.of(19, 0));
        }
    }

}
