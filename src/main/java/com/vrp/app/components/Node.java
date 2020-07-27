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
