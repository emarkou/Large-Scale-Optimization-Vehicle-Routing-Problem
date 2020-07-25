package com.vrp.app;


import java.util.ArrayList;
import java.util.Random;

public class App {
    private static int numberOfCustomers = 30;
    private static int numberOfVehicles = 10;
    private static int algorithm = 1;

    public static void main(String[] args) {
        setArgs(args);

        Node depot = new Node(50, 50, 0);
        depot.setRouted(true);

        ArrayList<Node> allNodes = new ArrayList<Node>();
        allNodes.add(depot);

        initCustomers(numberOfCustomers, allNodes);
        double[][] distanceMatrix = initDistanceMatrix(allNodes);


        // VRP heuristic
        Solution solution = new Solution();
        ArrayList<Route> routes = solution.route;

        for (int i = 1; i <= numberOfVehicles; i++) {
            Route route_nodes = new Route();
            route_nodes.ID = i;
            routes.add(route_nodes);
        }

        int toRoute = numberOfCustomers;
        for (int j = 1; j <= numberOfVehicles; j++) {
            ArrayList<Node> nodeSequence = routes.get(j - 1).nodes;
            int remaining = routes.get(j - 1).capacity;
            int load = routes.get(j - 1).load;
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
                        double trialCost = distanceMatrix[lastInTheRoute.id][candidate.id];
                        if (trialCost < bestCostForTheNextOne && candidate.demand <= remaining) {
                            positionOfTheNextOne = k;
                            bestCostForTheNextOne = trialCost;
                        }
                    }
                }

                if (positionOfTheNextOne != -1) {
                    Node insertedNode = allNodes.get(positionOfTheNextOne);
                    nodeSequence.add(insertedNode);
                    solution.cost = solution.cost + bestCostForTheNextOne;
                    routes.get(j - 1).cost = routes.get(j - 1).cost + bestCostForTheNextOne;
                    insertedNode.setRouted(true);
                    remaining = remaining - insertedNode.demand;
                    load = load + insertedNode.demand;
                    routes.get(j - 1).load = load;
                    toRoute = toRoute - 1;
                } else {
                    nodeSequence.add(depot);
                    solution.cost = solution.cost + distanceMatrix[lastInTheRoute.id][0];
                    routes.get(j - 1).cost = routes.get(j - 1).cost + distanceMatrix[lastInTheRoute.id][0];
                    finalized = true;
                }
            }
        }
        printResults(numberOfVehicles, solution);
    }

    private static void setArgs(String[] args) {
        if (args.length != 0) {
            algorithm = Integer.parseInt(args[0]);
            numberOfCustomers = Integer.parseInt(args[1]);
            numberOfVehicles = Integer.parseInt(args[2]);
        }
    }

    private static void printResults(int numberOfVehicles, Solution solution) {
        StringBuilder resultOutput = new StringBuilder();
        resultOutput.append("* * * * * * * * * * * * * * * * * * * * * * * * * *\n");
        resultOutput.append("\t VRP Starts ! \n");
        resultOutput.append("* * * * * * * * * * * * * * * * * * * * * * * * * *\n");

        for (int j = 0; j < numberOfVehicles; j++) {
            int vehicle = j + 1;
            resultOutput.append("Assignment to Vehicle " + vehicle + ": ");
            for (int k = 0; k < solution.route.get(j).nodes.size(); k++) {
                resultOutput.append(solution.route.get(j).nodes.get(k).id + " ");
            }
            resultOutput.append("\n");
            resultOutput.append("Route Cost: " + solution.route.get(j).cost + " - Route Load: " + solution.route.get(j).load + "\n");
            resultOutput.append("\n");
        }
        resultOutput.append("* * * * * * * * * * * * * * * * * * * * * * * * * *\n");
        resultOutput.append("Total Cost: " + solution.cost + "\n");
        System.out.println(resultOutput);
    }

    private static double[][] initDistanceMatrix(ArrayList<Node> allNodes) {
        double[][] distanceMatrix = new double[allNodes.size()][allNodes.size()];
        for (int i = 0; i < allNodes.size(); i++) {
            Node from = allNodes.get(i);

            for (int j = 0; j < allNodes.size(); j++) {
                Node to = allNodes.get(j);

                double Delta_x = (from.getX() - to.getX());
                double Delta_y = (from.getY() - to.getY());
                double distance = Math.sqrt((Delta_x * Delta_x) + (Delta_y * Delta_y));

                distance = Math.round(distance);

                distanceMatrix[i][j] = distance;
            }
        }
        return distanceMatrix;
    }

    private static void initCustomers(int numberOfCustomers, ArrayList<Node> allNodes) {
        Random ran = new Random(150589);
        for (int i = 1; i <= numberOfCustomers; i++) {
            int x = ran.nextInt(100);
            int y = ran.nextInt(100);
            int demand = ran.nextInt(7);
            Node customer = new Node(x, y, i, demand);
            customer.setRouted(false);
            allNodes.add(customer);
        }
    }
}