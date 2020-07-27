package com.vrp.app;

import com.vrp.app.components.Solution;

public interface Solver {
    void run();

    void setSolution(Solution solution);

    Solution getSolution();
}
