package com.vrp.app.components;

import java.util.ArrayList;

public class Solution {
    private double cost;
    private ArrayList<Route> route;

    public Solution() {
        this.route = new ArrayList<Route>();
        this.cost = 0;
    }

    public ArrayList<Route> getRoute() {
        return route;
    }

    //TODO: fix mods
    private static Solution cloneSolution(Solution solution) {
        Solution out = new Solution();
        out.cost = solution.cost;
        out.route = (ArrayList<Route>) solution.route.clone();
        return out;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double costs) {
        this.cost = costs;
    }
}
