# Vehicle Routing Problem (VRP) Solvers

Java Constraint Solvers for Vehicle Routing Problem (VRP).

# Quick Start
```
make && make run-all
```

# Features
Currently the following algorithms are supported:
- Nearest Neighbors
- Local search with intra- relocation moves
- Local search with intra- and inter- relocation moves
- Tabu search


#### Nearest Neigbors
A constructive heuristic is developed based on Nearest Neighbor to produce an initial solution for the VRP.
It is based on the nearest neighbor heuristic for TSP.
However, in this case the capacity constraints of the routes must be taken into consideration while inserting a new customer.
If no neighbor that respects the limitations exist, then the current route is finalized and the method continues by building the second route and so on. Obviously the method terminates when all customers have been inserted into the solution.

#### Local Search with intra- relocations
The nearest neighbors algorithm is initially applied. Afterwwards, the selected routes get refined using the local search method.
This method considers all possible customer relocations within their routes.
The relocation yielding the biggest cost reduction is selected to be applied to the candidate solution.
The method terminates if no improving intra-route relocation can be identified.

#### Local Search with intra- & inter- relocations
This local search method is an extension of the one described above.
This method considers all possible customer relocations (both intra- and inter-route).
This means that at each iteration, the method explores all potential relocations of
customers to any point of the existing solution.

####  Tabu Search
The nearest neighbors algorithm is initially applied. Afterwards, the selected routes get refined using a tabu-search method.
This method considers all possible customer relocations (both intra- and inter-route) with respect to the selected tabu policy.
The method terminates after 200 iterations.



<!-- - Number of customers: 30
- Total Number of nodes: 31 (customers + depot)
- Number of vehicles: 10 
- Capacity of each vehicle: 50 product units
- Depot geo-location: (x,y)=(50,50)
- Geo-location and demand of each is generated randomly -->

