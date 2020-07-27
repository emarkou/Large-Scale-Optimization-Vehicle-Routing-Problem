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

public class RelocationMove {
    private int positionOfRelocated;
    private int positionToBeInserted;
    private int route;
    private double moveCost;
    private int fromRoute;
    private int toRoute;
    private double moveCostTo;
    private double moveCostFrom;
    private int newLoadFrom;
    private int newLoadTo;

    public RelocationMove(int positionOfRelocated, int positionToBeInserted, int route, double moveCost) {
        this.positionOfRelocated = positionOfRelocated;
        this.positionToBeInserted = positionToBeInserted;
        this.route = route;
        this.moveCost = moveCost;
    }

    public RelocationMove(int positionOfRelocated, int positionToBeInserted, int fromRoute, int toRoute, double moveCostFrom, double moveCostTo) {
        this.positionOfRelocated = positionOfRelocated;
        this.positionToBeInserted = positionToBeInserted;
        this.fromRoute = fromRoute;
        this.toRoute = toRoute;
        this.moveCostFrom = moveCostFrom;
        this.moveCostTo = moveCostTo;
    }

    public int getPositionOfRelocated() {
        return positionOfRelocated;
    }

    public int getPositionToBeInserted() {
        return positionToBeInserted;
    }

    public int getRoute() {
        return route;
    }

    public double getMoveCost() {
        return moveCost;
    }

    public void setPositionOfRelocated(int positionOfRelocated) {
        this.positionOfRelocated = positionOfRelocated;
    }

    public void setPositionToBeInserted(int positionToBeInserted) {
        this.positionToBeInserted = positionToBeInserted;
    }

    public void setRoute(int route) {
        this.route = route;
    }

    public void setMoveCost(double moveCost) {
        this.moveCost = moveCost;
    }

    public int getFromRoute() {
        return fromRoute;
    }

    public void setFromRoute(int fromRoute) {
        this.fromRoute = fromRoute;
    }

    public int getToRoute() {
        return toRoute;
    }

    public void setToRoute(int toRoute) {
        this.toRoute = toRoute;
    }

    public double getMoveCostTo() {
        return moveCostTo;
    }

    public void setMoveCostTo(double moveCostTo) {
        this.moveCostTo = moveCostTo;
    }

    public double getMoveCostFrom() {
        return moveCostFrom;
    }

    public void setMoveCostFrom(double moveCostFrom) {
        this.moveCostFrom = moveCostFrom;
    }

    public int getNewLoadFrom() {
        return newLoadFrom;
    }

    public void setNewLoadFrom(int newLoadFrom) {
        this.newLoadFrom = newLoadFrom;
    }

    public int getNewLoadTo() {
        return newLoadTo;
    }

    public void setNewLoadTo(int newLoadTo) {
        this.newLoadTo = newLoadTo;
    }


}
