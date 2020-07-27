package com.vrp.app.solvers;

import com.vrp.app.Solver;
import com.vrp.app.components.Node;
import com.vrp.app.components.Route;
import com.vrp.app.components.Solution;

import java.util.ArrayList;

/**
 * A constructive heuristic is developed based on Nearest Neighbor to produce an initial solution for the VRP.
 * It is based on the nearest neighbor heuristic for TSP. However, in this case the capacity constraints of the routes
 * must be taken into consideration while inserting a new customer. If no neighbor that respects the limitations exist,
 * then the current route is finalized and the method continues by building the second route and so on. Obviously the method
 * terminates when all customers have been inserted into the solution.
 */
public class LocalSearch implements Solver {

    private final int numberOfVehicles;
    private final int numberOfCustomers;
    private final ArrayList<Node> allNodes;
    private final Node depot;
    private final double[][] distanceMatrix;
    private Solution solution;

    public LocalSearch(int numberOfCustomers, int numberOfVehicles, Node depot, double[][] distanceMatrix, ArrayList<Node> allNodes) {
        this.depot = depot;
        this.numberOfCustomers = numberOfCustomers;
        this.numberOfVehicles = numberOfVehicles;
        this.distanceMatrix = distanceMatrix;
        this.allNodes = allNodes;
    }

    @Override
    public void run() {
        Solution solution = new Solution();
        ArrayList<Route> routes = solution.getRoute();

        for (int i = 1; i <= numberOfVehicles; i++) {
            Route route_nodes = new Route();
            route_nodes.setID(i);
            routes.add(route_nodes);
        }

        int toRoute = numberOfCustomers;
        for (int j = 1; j <= numberOfVehicles; j++) {
            ArrayList<Node> nodeSequence = routes.get(j - 1).getNodes();
            int remaining = routes.get(j - 1).getCapacity();
            int load = routes.get(j - 1).getLoad();
            nodeSequence.add(depot);
            boolean finalized = false;
            if (toRoute == 0) {
                finalized = true;
                nodeSequence.add(depot);
            }

            while (finalized == false) {
                int positionOfTheNextOne = -1;
                double bestCostForTheNextOne = Double.MAX_VALUE;
                Node lastInTheRoute = nodeSequence.get(nodeSequence.size() - 1);
                for (int k = 1; k < allNodes.size(); k++) {
                    Node candidate = allNodes.get(k);
                    if (!candidate.getRouted()) {
                        double trialCost = distanceMatrix[lastInTheRoute.getId()][candidate.getId()];
                        if (trialCost < bestCostForTheNextOne && candidate.getDemand() <= remaining) {
                            positionOfTheNextOne = k;
                            bestCostForTheNextOne = trialCost;
                        }
                    }
                }

                if (positionOfTheNextOne != -1) {
                    Node insertedNode = allNodes.get(positionOfTheNextOne);
                    nodeSequence.add(insertedNode);
                    solution.setCost(solution.getCost() + bestCostForTheNextOne);
                    routes.get(j - 1).setCost(routes.get(j - 1).getCost() + bestCostForTheNextOne);
                    insertedNode.setRouted(true);
                    remaining = remaining - insertedNode.getDemand();
                    load = load + insertedNode.getDemand();
                    routes.get(j - 1).setLoad(load);
                    toRoute = toRoute - 1;
                } else {
                    nodeSequence.add(depot);
                    solution.setCost(solution.getCost() + distanceMatrix[lastInTheRoute.getId()][0]);
                    routes.get(j - 1).setCost(routes.get(j - 1).getCost() + distanceMatrix[lastInTheRoute.getId()][0]);
                    finalized = true;
                }
            }
        }
        setSolution(solution);
    }

    @Override
    public void setSolution(Solution solution) {
        this.solution = solution;
    }

    @Override
    public Solution getSolution() {
        return solution;
    }
}
