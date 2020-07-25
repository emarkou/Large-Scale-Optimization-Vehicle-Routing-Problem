package com.vrp.app;

import java.util.ArrayList;

public class Route {
    ArrayList<Node> nodes;
    double cost;
    int ID;
    int load;
    int capacity;

    //This is the Route constructor. It is executed every time a new Route object is created (new Route)
    Route() {
        cost = 0;
        ID = -1;
        capacity = 50;
        load = 0;

        // A new arraylist of nodes is created
        nodes = new ArrayList<Node>();
    }
}
