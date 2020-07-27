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
package com.vrp.app.components;

import java.util.ArrayList;

public class Solution {
    private double cost;
    private ArrayList<Route> route;

    public Solution() {
        this.route = new ArrayList<>();
        this.cost = 0;
    }

    @SuppressWarnings("unchecked")
    public static Solution cloneSolution(Solution solution) {
        Solution out = new Solution();
        out.setCost(solution.getCost());
        out.setRoute((ArrayList<Route>) solution.getRoute().clone());
        return out;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double costs) {
        this.cost = costs;
    }

    public void setRoute(ArrayList<Route> route) {
        this.route = route;
    }

    public ArrayList<Route> getRoute() {
        return route;
    }

}
