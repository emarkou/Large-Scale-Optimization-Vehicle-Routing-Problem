/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2020
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:

 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.

 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 * */
package com.vrp.app.solvers;

import com.vrp.app.Solver;
import com.vrp.app.VRP;
import com.vrp.app.components.Node;
import com.vrp.app.components.RelocationMove;
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

    public LocalSearchIntraAndInterRelocation(int numberOfVehicles, int numberOfCustomers, ArrayList<Node> allNodes, Node depot, double[][] distanceMatrix) {
        this.numberOfVehicles = numberOfVehicles;
        this.numberOfCustomers = numberOfCustomers;
        this.allNodes = allNodes;
        this.depot = depot;
        this.distanceMatrix = distanceMatrix;
        this.solution = new Solution();
    }

    @Override
    public void run() {
        boolean terminationCondition = false;
        int localSearchIterator = 0;

        RelocationMove relocationMove = new RelocationMove(-1, -1, 0, 0, Double.MAX_VALUE, Double.MAX_VALUE);
        while (terminationCondition == false) {
            findBestRelocationMove(relocationMove, solution, distanceMatrix, numberOfVehicles);

            if (relocationMove.getMoveCost() < 0) {
                applyRelocationMove(relocationMove, solution);
                localSearchIterator = localSearchIterator + 1;
            } else {
                terminationCondition = true;
            }

        }

    }

    private void applyRelocationMove(RelocationMove relocationMove, Solution currentSolution) {
        Node relocatedNode = currentSolution.getRoute().get(relocationMove.getFromRoute()).getNodes().get(relocationMove.getPositionOfRelocated());

        currentSolution.getRoute().get(relocationMove.getFromRoute()).getNodes().remove(relocationMove.getPositionOfRelocated());

        int whereToMove = (shouldShiftRelocation(relocationMove) ?
                relocationMove.getPositionToBeInserted() + 1 : relocationMove.getPositionToBeInserted());

        currentSolution.getRoute().get(relocationMove.getToRoute()).getNodes().add(whereToMove, relocatedNode);

        currentSolution.setCost(currentSolution.getCost() + relocationMove.getMoveCost());
        currentSolution.getRoute().get(relocationMove.getToRoute()).setCost(currentSolution.getRoute().get(relocationMove.getToRoute()).getCost() + relocationMove.getMoveCostTo());
        currentSolution.getRoute().get(relocationMove.getFromRoute()).setCost(currentSolution.getRoute().get(relocationMove.getFromRoute()).getCost() + relocationMove.getMoveCostFrom());
        if (relocationMove.getToRoute() != relocationMove.getFromRoute()) {
            currentSolution.getRoute().get(relocationMove.getToRoute()).setLoad(relocationMove.getNewLoadTo());
            currentSolution.getRoute().get(relocationMove.getFromRoute()).setLoad(relocationMove.getNewLoadFrom());
        } else {
            currentSolution.getRoute().get(relocationMove.getToRoute()).setLoad(relocationMove.getNewLoadTo());
        }
        setSolution(currentSolution);
    }

    boolean shouldShiftRelocation(RelocationMove relocationMove) {
        return (relocationMove.getPositionToBeInserted() < relocationMove.getPositionOfRelocated()) || relocationMove.getToRoute() != relocationMove.getFromRoute();
    }

    private void findBestRelocationMove(RelocationMove relocationMove, Solution currentSolution, double[][] distanceMatrix, int numberOfVehicles) {
        StringBuilder debug = new StringBuilder();
        double bestMoveCost = Double.MAX_VALUE;

        for (int from = 0; from < numberOfVehicles; from++) {
            for (int to = 0; to < numberOfVehicles; to++) {
                for (int relFromIndex = 1; relFromIndex < currentSolution.getRoute().get(from).getNodes().size() - 1; relFromIndex++) {
                    Node A = currentSolution.getRoute().get(from).getNodes().get(relFromIndex - 1);

                    Node B = currentSolution.getRoute().get(from).getNodes().get(relFromIndex);

                    Node C = currentSolution.getRoute().get(from).getNodes().get(relFromIndex + 1);

                    for (int afterToInd = 0; afterToInd < currentSolution.getRoute().get(to).getNodes().size() - 1; afterToInd++) {
                        if ((afterToInd != relFromIndex && afterToInd != relFromIndex - 1) || from != to) {
                            Node F = currentSolution.getRoute().get(to).getNodes().get(afterToInd);

                            Node G = currentSolution.getRoute().get(to).getNodes().get(afterToInd + 1);

                            double costRemovedFrom = distanceMatrix[A.getId()][B.getId()] + distanceMatrix[B.getId()][C.getId()];
                            double costRemovedTo = distanceMatrix[F.getId()][G.getId()];

                            double costAddedFrom = distanceMatrix[A.getId()][C.getId()];
                            double costAddedTo = distanceMatrix[F.getId()][B.getId()] + distanceMatrix[B.getId()][G.getId()];

                            double moveCostFrom = costAddedFrom - costRemovedFrom;
                            double moveCostTo = costAddedTo - costRemovedTo;

                            double moveCost = moveCostFrom + moveCostTo;
                            if ((moveCost < bestMoveCost) && (from == to || (currentSolution.getRoute().get(to).getLoad() + currentSolution.getRoute().get(from).getNodes().get(relFromIndex).getDemand() <= currentSolution.getRoute().get(to).getCapacity()))) {
                                bestMoveCost = moveCost;

                                relocationMove.setPositionOfRelocated(relFromIndex);
                                relocationMove.setPositionToBeInserted(afterToInd);
                                relocationMove.setMoveCostTo(moveCostTo);
                                relocationMove.setMoveCostFrom(moveCostFrom);
                                relocationMove.setFromRoute(from);
                                relocationMove.setToRoute(to);
                                relocationMove.setMoveCost(moveCost);

                                if (from != to) {
                                    relocationMove.setNewLoadFrom(currentSolution.getRoute().get(from).getLoad() - currentSolution.getRoute().get(from).getNodes().get(relFromIndex).getDemand());
                                    relocationMove.setNewLoadTo(currentSolution.getRoute().get(to).getLoad() + currentSolution.getRoute().get(from).getNodes().get(relFromIndex).getDemand());
                                } else {
                                    relocationMove.setNewLoadFrom(currentSolution.getRoute().get(from).getLoad());
                                    relocationMove.setNewLoadTo(currentSolution.getRoute().get(to).getLoad());
                                }

                                debug.append("From route: " + relocationMove.getFromRoute() + ", To Route: " + relocationMove.getToRoute() + ", New Load From:" + relocationMove.getNewLoadFrom() + ", New Load To:" + relocationMove.getNewLoadTo());
                            }
                        }
                    }
                }
            }
        }
        setSolution(currentSolution);
        if (VRP.DEBUG_ROUTES) {
            System.out.println(debug);
        }
    }

    @Override
    public void setSolution(Solution solution) {
        this.solution = solution;
    }

    @Override
    public Solution getSolution() {
        return solution;
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
