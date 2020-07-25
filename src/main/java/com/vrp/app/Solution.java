package com.vrp.app;

import java.util.ArrayList;

public class Solution {
    double cost;
    ArrayList<Route> route;

    //This is the Solution constructor. It is executed every time a new Solution object is created (new Solution)
    Solution() {
        // A new route object is created addressed by route. The constructor of route is called
        route = new ArrayList<Route>();
        cost = 0;
    }
}
