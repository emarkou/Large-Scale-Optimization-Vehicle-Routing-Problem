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
package com.vrp.app;

import com.vrp.app.components.Node;
import com.vrp.app.components.Solution;
import com.vrp.app.solvers.LocalSearchIntraAndInterRelocation;
import com.vrp.app.solvers.NearestNeighbor;
import com.vrp.app.solvers.LocalSearchIntraRelocation;
import com.vrp.app.solvers.TabuSearch;
import com.vrp.app.utils.Printer;

import java.util.ArrayList;
import java.util.Random;

public class VRP {
    private static int numberOfCustomers = 30;
    private static int numberOfVehicles = 10;
    private static int algorithm = 1;
    private static final boolean PRINT = true;

    public static void main(String[] args) {
        Solution finalSolution;
        String solverName = "";

        setArgs(args);

        ArrayList<Node> allNodes = initCustomers(numberOfCustomers);
        Node depot = allNodes.get(0);

        double[][] distanceMatrix = initDistanceMatrix(allNodes);

        NearestNeighbor nearestNeighbor = new NearestNeighbor(numberOfCustomers, numberOfVehicles, depot, distanceMatrix, allNodes);
        nearestNeighbor.run();

        switch (algorithm) {
            case 1:
                finalSolution = nearestNeighbor.getSolution();
                solverName = "LocalSearch";
                break;
            case 2:
                LocalSearchIntraRelocation localSearchIntraRelocation = new LocalSearchIntraRelocation(numberOfCustomers, numberOfVehicles, depot, distanceMatrix, allNodes);
                localSearchIntraRelocation.setSolution(nearestNeighbor.getSolution());
                localSearchIntraRelocation.run();
                finalSolution = localSearchIntraRelocation.getSolution();
                solverName = "LocalSearch with Intra Relocation";
                break;
            case 3:
                LocalSearchIntraAndInterRelocation localSearchIntraAndInterRelocation = new LocalSearchIntraAndInterRelocation(numberOfVehicles, numberOfCustomers, allNodes, depot, distanceMatrix);
                localSearchIntraAndInterRelocation.setSolution(nearestNeighbor.getSolution());
                localSearchIntraAndInterRelocation.run();
                finalSolution = localSearchIntraAndInterRelocation.getSolution();
                solverName = "LocalSearch with Intra and Inner Relocation";
                break;
            case 4:
                TabuSearch tabuSearch = new TabuSearch(numberOfVehicles, numberOfCustomers, allNodes, depot, distanceMatrix);
                tabuSearch.setSolution(nearestNeighbor.getSolution());
                tabuSearch.run();
                finalSolution = tabuSearch.getSolution();
                solverName = "\t TABU Search ";
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + algorithm);
        }


        if (PRINT) {
            Printer.printResults(numberOfVehicles, finalSolution, solverName);
        }
    }

    private static void setArgs(String[] args) {
        if (args.length != 0) {
            algorithm = Integer.parseInt(args[0]);
            numberOfCustomers = Integer.parseInt(args[1]);
            numberOfVehicles = Integer.parseInt(args[2]);
        } else {
            System.out.println("Arguments should be 3: 1) Algorithm 2) Number of Customers and 3) Number of Vehicles");
            System.exit(1);
        }
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

    private static ArrayList<Node> initCustomers(int numberOfCustomers) {
        ArrayList<Node> allNodes = new ArrayList<>();

        Node depot = new Node(50, 50, 0);
        depot.setRouted(true);
        allNodes.add(depot);

        Random ran = new Random(150589);

        for (int i = 1; i <= numberOfCustomers; i++) {
            int x = ran.nextInt(100);
            int y = ran.nextInt(100);
            int demand = 4 + ran.nextInt(7);
            Node customer = new Node(x, y, i, demand);
            customer.setRouted(false);
            allNodes.add(customer);
        }

        return allNodes;
    }
}
