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
import com.vrp.app.components.Node;
import com.vrp.app.components.RelocationMove;
import com.vrp.app.components.Solution;

import java.util.ArrayList;

/**
 * A local search is then designed for improving the initial solution generated at Component 3.
 * This local search method considers all possible customer relocations within their routes.
 * The relocation yielding the biggest cost reduction is selected to be applied to the candidate solution.
 * The method terminates if no improving intra-route relocation can be identified.
 */
public class LocalSearchIntraRelocation implements Solver {
    private final int numberOfVehicles;
    private final int numberOfCustomers;
    private final ArrayList<Node> allNodes;
    private final Node depot;
    private final double[][] distanceMatrix;
    private Solution solution;

    public LocalSearchIntraRelocation(int numberOfCustomers, int numberOfVehicles, Node depot, double[][] distanceMatrix, ArrayList<Node> allNodes) {
        this.depot = depot;
        this.numberOfCustomers = numberOfCustomers;
        this.numberOfVehicles = numberOfVehicles;
        this.distanceMatrix = distanceMatrix;
        this.allNodes = allNodes;
    }

    @Override
    public void run() {
        boolean terminationCondition = false;
        int localSearchIterator = 0;

        RelocationMove relocationMove = new RelocationMove(-1, -1, 0, Double.MAX_VALUE);

        while (terminationCondition == false) {
            findBestRelocationMove(relocationMove, solution, distanceMatrix, numberOfVehicles);

            if (relocationMove.getMoveCost() < 0) {
                applyRelocationMove(relocationMove, solution, distanceMatrix);
                localSearchIterator = localSearchIterator + 1;

            } else {
                terminationCondition = true;
            }

        }
    }

    @Override
    public Solution getSolution() {
        return solution;
    }

    @Override
    public void setSolution(Solution solution) {
        this.solution = solution;
    }

    private void applyRelocationMove(RelocationMove relocationMove, Solution currentSolution, double[][] distanceMatrix) {
        Node relocatedNode = currentSolution.getRoute().get(relocationMove.getRoute()).getNodes().get(relocationMove.getPositionOfRelocated());

        currentSolution.getRoute().get(relocationMove.getRoute()).getNodes().remove(relocationMove.getPositionOfRelocated());

        if (relocationMove.getPositionToBeInserted() < relocationMove.getPositionOfRelocated()) {
            currentSolution.getRoute().get(relocationMove.getRoute()).getNodes().add(relocationMove.getPositionToBeInserted() + 1, relocatedNode);
        } else {
            currentSolution.getRoute().get(relocationMove.getRoute()).getNodes().add(relocationMove.getPositionToBeInserted(), relocatedNode);
        }

        double newSolutionCost = 0;
        for (int i = 0; i < currentSolution.getRoute().get(relocationMove.getRoute()).getNodes().size() - 1; i++) {
            Node A = currentSolution.getRoute().get(relocationMove.getRoute()).getNodes().get(i);
            Node B = currentSolution.getRoute().get(relocationMove.getRoute()).getNodes().get(i + 1);
            newSolutionCost = newSolutionCost + distanceMatrix[A.getId()][B.getId()];
        }
        if (currentSolution.getRoute().get(relocationMove.getRoute()).getCost() + relocationMove.getMoveCost() != newSolutionCost) {
            System.out.println("Something went wrong with the cost calculations !!!!");
        }

        currentSolution.setCost(currentSolution.getCost() + relocationMove.getMoveCost());
        currentSolution.getRoute().get(relocationMove.getRoute()).setCost(currentSolution.getRoute().get(relocationMove.getRoute()).getCost() + relocationMove.getMoveCost());

        setSolution(currentSolution);
    }

    private void findBestRelocationMove(RelocationMove relocationMove, Solution currentSolution, double[][] distanceMatrix, int numberOfVehicles) {
        double bestMoveCost = Double.MAX_VALUE;

        for (int j = 0; j < numberOfVehicles; j++) {
            for (int relIndex = 1; relIndex < currentSolution.getRoute().get(j).getNodes().size() - 1; relIndex++) {
                Node A = currentSolution.getRoute().get(j).getNodes().get(relIndex - 1);
                Node B = currentSolution.getRoute().get(j).getNodes().get(relIndex);
                Node C = currentSolution.getRoute().get(j).getNodes().get(relIndex + 1);

                for (int afterInd = 0; afterInd < currentSolution.getRoute().get(j).getNodes().size() - 1; afterInd++) {
                    if (afterInd != relIndex && afterInd != relIndex - 1) {
                        Node F = currentSolution.getRoute().get(j).getNodes().get(afterInd);
                        Node G = currentSolution.getRoute().get(j).getNodes().get(afterInd + 1);

                        double costRemoved1 = distanceMatrix[A.getId()][B.getId()] + distanceMatrix[B.getId()][C.getId()];
                        double costRemoved2 = distanceMatrix[F.getId()][G.getId()];
                        double costRemoved = costRemoved1 + costRemoved2;

                        double costAdded1 = distanceMatrix[A.getId()][C.getId()];
                        double costAdded2 = distanceMatrix[F.getId()][B.getId()] + distanceMatrix[B.getId()][G.getId()];
                        double costAdded = costAdded1 + costAdded2;

                        double moveCost = costAdded - costRemoved;

                        if (moveCost < bestMoveCost) {
                            bestMoveCost = moveCost;
                            relocationMove.setPositionOfRelocated(relIndex);
                            relocationMove.setPositionToBeInserted(afterInd);
                            relocationMove.setMoveCost(moveCost);
                            relocationMove.setRoute(j);
                        }
                    }
                }
            }
        }
        setSolution(currentSolution);
    }

}
