package com.vrp.app.components;

public class Node {
    private int x;
    private int y;
    private int id;
    private int demand;

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

    public void setId(int index) {
        this.id = index;
    }

    public int getDemand() {
        return demand;
    }
}
