# Large Scale Optimization - Vehicle Routing Problem
A java application developed for dealing with the well-known vehicle routing problem by implementing Local Search with intra- and inter- relocation moves as well as Tabu Search. 

## Prerequisuites
Before implementing any of the aforementioned algorithms a VRP instance with the following characteristics was created:
- Number of customers: 30
- Total Number of nodes: 31 (customers + depot)
- Number of vehicles: 10 
- Capacity of each vehicle: 50 product units
- Depot geo-location: (x,y)=(50,50)
- Geo-location and demand of each is generated randomly

## Component 3: Nearest Neighbor
A constructive heuristic is developed based on Nearest Neighbor to produce an initial solution for the VRP. It is based on the nearest neighbor heuristic for TSP. However, in this case the capacity constraints of the routes must be taken into consideration while inserting a new customer. If no neighbor that respects the limitations exist, then the current route is finalized and the method contiinues by building the second route and so on. Obviously the method terminates when all customers have been inserted into the solution. 

## Component 4: Local search with intra-route relocations 
A local search is then designed for improving the initial solution generated at Component 3. This local search method considers all possible customer relocations within their routes. The relocation yielding the biggest cost reduction is selected to be applied to the candidate solution. The method terminates if no improving intra-route relocation can be identified.

## Component 5: Local Search with intra- and inter-route relocation moves
A local search is then designed for improving even further the initial solution generated at Component 3.  This local search method considers all possible customer relocations (both intra- and inter-route). This means that at each iteration, the method explores all potential relocations of customers to any point of the existing solution. 

## Component 6: Tabu Search
A tabu-search method is finally implemented for improving the initial solution generated in Component 3. This tabu search method considers all possible customer relocations (both intra- and inter-route) with respect to the selected tabu policy. The method terminates after 200 iterations.