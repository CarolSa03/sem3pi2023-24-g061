package domain.BasketDistribution;

import ESINF.graph.Graph;
import utils.MathUtils;

import java.util.Comparator;

public class HubComparatorByDefinedCriteria implements Comparator<Local> {
    @Override
    public int compare(Local locality1, Local locality2) {
        Graph<Local, Double> mapGraph = BasketDistributionMap.getBasketDistributionMap();
        int i = Integer.compare(mapGraph.inDegree(locality1), mapGraph.inDegree(locality2));
        if (i == 0) i = Double.compare(MathUtils.calculateAverage(mapGraph, locality1), MathUtils.calculateAverage(mapGraph, locality2));
        if (i == 0) i = Integer.compare(MathUtils.calculateCentrality(mapGraph, locality1), MathUtils.calculateCentrality(mapGraph, locality2));
        return i;
    }
}
