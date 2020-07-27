package com.vrp.app.solvers;

import com.vrp.app.Solver;
import com.vrp.app.components.Node;
import com.vrp.app.components.Route;
import com.vrp.app.components.Solution;

import java.util.ArrayList;

/**
 * A local search is then designed for improving even further the initial solution generated at Component 3.
 * This local search method considers all possible customer relocations (both intra- and inter-route).
 * This means that at each iteration, the method explores all potential relocations of customers to any point of the existing solution.
 */

public class LocalSearchIntraAndInterRelocation implements Solver {
    private final int numberOfVehicles;
    private final int numberOfCustomers;
    private final ArrayList<Node> allNodes;
    private final Node depot;
    private final double[][] distanceMatrix;

    private Solution solution;
    public LocalSearchIntraAndInterRelocation(int numberOfVehicles, int numberOfCustomers, ArrayList<Node> allNodes, Node depot, double[][] distanceMatrix, Solution solution) {
        this.numberOfVehicles = numberOfVehicles;
        this.numberOfCustomers = numberOfCustomers;
        this.allNodes = allNodes;
        this.depot = depot;
        this.distanceMatrix = distanceMatrix;

        this.solution = solution;
    }

    @Override
    public void run() {

    }

    @Override
    public void setSolution(Solution solution) {

    }

    @Override
    public Solution getSolution() {
        return null;
    }

    public int getNumberOfVehicles() {
        return numberOfVehicles;
    }

    public int getNumberOfCustomers() {
        return numberOfCustomers;
    }

    public ArrayList<Node> getAllNodes() {
        return allNodes;
    }

    public Node getDepot() {
        return depot;
    }

    public double[][] getDistanceMatrix() {
        return distanceMatrix;
    }
}
