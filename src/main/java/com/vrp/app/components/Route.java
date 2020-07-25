package com.vrp.app.components;

import java.util.ArrayList;

public class Route {
    private ArrayList<Node> nodes;
    private double cost;
    private int ID;
    private int load;
    private int capacity;

    public Route() {
        this.cost = 0;
        this.ID = -1;
        this.capacity = 50;
        this.load = 0;
        this.nodes = new ArrayList<Node>();
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }

    public double getCost() {
        return cost;
    }

    public int getLoad() {
        return load;
    }

    public int getID() {
        return ID;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public void setLoad(int load) {
        this.load = load;
    }

    public void setID(int idx) {
        this.ID = idx;
    }
}
