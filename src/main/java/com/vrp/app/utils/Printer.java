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
package com.vrp.app.utils;

import com.vrp.app.components.Solution;

public class Printer {
    public Printer(){
    }

    public static void printResults(int numberOfVehicles, Solution solution, String solverName) {
        StringBuilder resultOutput = new StringBuilder();
        resultOutput.append("* * * * * * * * * * * * * * * * * * * * * * * * * *\n");
        resultOutput.append("\t VRP Starts ! \n");
        resultOutput.append(" Solver Used : " + solverName + " \n");
        resultOutput.append("* * * * * * * * * * * * * * * * * * * * * * * * * *\n");

        for (int j = 0; j < numberOfVehicles; j++) {
            int vehicle = j + 1;
            resultOutput.append("Assignment to Vehicle " + vehicle + ": ");
            for (int k = 0; k < solution.getRoute().get(j).getNodes().size(); k++) {
                resultOutput.append(solution.getRoute().get(j).getNodes().get(k).getId() + "  ");
            }
            resultOutput.append("\n");
            resultOutput.append("Route Cost: " + solution.getRoute().get(j).getCost() + " - Route Load: " + solution.getRoute().get(j).getLoad() + "\n");
            resultOutput.append("\n");
        }
        resultOutput.append("* * * * * * * * * * * * * * * * * * * * * * * * * *\n");
        resultOutput.append("Total Cost: " + solution.getCost() + "\n");
        System.out.println(resultOutput);
    }
}
