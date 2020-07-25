package com.vrp.app;

public class Node {
    int x;
    int y;
    int id;
    int demand;

    // true/false flag indicating if a customer has been inserted in the solution
    boolean routable;

    public Node(int x, int y, int id, int demand) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.demand = demand;
    }

    public Node(int x, int y, int id) {
        this.x = x;
        this.y = y;
        this.id = id;
    }

    public boolean getRouted() {
        return routable;
    }

    public void setRouted(boolean rt) {
        this.routable = rt;
    }

    public int getX() {
        return x;

    }

    public int getY() {
        return y;

    }

    public int getId() {
        return id;

    }
}
