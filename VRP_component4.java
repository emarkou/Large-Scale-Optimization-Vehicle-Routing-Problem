import java.util.ArrayList;
import java.util.Random;



public class VRP_component4 {


	public static void main(String[] args) {
		Random ran = new Random(191093);
		//Set up Input for VRP
		int numberOfCustomers  = 30;
		int numberOfVehicles = 10;

		//initialize depot
		Node depot = new Node();
		depot.x = 50;
		depot.y = 50;
		depot.ID = 0;
		depot.isRouted = true;

		//Create the list with allNodes and add depot into it as well
		ArrayList <Node> allNodes = new ArrayList<Node>();
		allNodes.add(depot);

		for(int i=1 ; i<=numberOfCustomers; i++)
		{
			Node cust = new Node();
			cust.x = ran.nextInt(100);
			cust.y = ran.nextInt(100);
			cust.demand = 4 + ran.nextInt(7);
			cust.ID = i;
			cust.isRouted = false;
			allNodes.add(cust);
		}
		System.out.println("* * * * * * * * * * * * *");
		System.out.println("VRP Starts !");
		System.out.println("* * * * * * * * * * * * *");
		System.out.println(" ");
		// Distance Matrix
		double [][] distanceMatrix = new double [allNodes.size()][allNodes.size()];
		for (int i = 0 ; i < allNodes.size(); i++)
		{
			Node from = allNodes.get(i);

			for (int j = 0 ; j < allNodes.size(); j++)
			{
				Node to = allNodes.get(j);

				double Delta_x = (from.x - to.x);
				double Delta_y = (from.y - to.y);
				double distance = Math.sqrt((Delta_x * Delta_x) + (Delta_y * Delta_y));

				distance = Math.round(distance);

				distanceMatrix[i][j] = distance;

			}
		}

		// VRP heuristic
		//initialize solution
		Solution s = new Solution();

		// array list of ALL routes
		ArrayList<Route> routes = s.rt;

		// loop for vehicles
		for (int i=1; i<=numberOfVehicles; i++)
		{
			Route route_nodes = new Route();
			route_nodes.ID = i;
			routes.add(route_nodes);
		}
		int toRoute = 30;
		for (int j = 1; j<=numberOfVehicles; j++)
		{
			ArrayList <Node> nodeSequence = routes.get(j-1).nodes;
			int remaining = routes.get(j-1).capacity;
			int load = routes.get(j-1).load;
			nodeSequence.add(depot);
			boolean finalized = false;
			
			if (toRoute == 0) {
				finalized = true;
				nodeSequence.add(depot);
				
			}
			
			//boolean finalized = false;
			
			while (finalized == false )			{
				//this will be the position of the nearest neighbor customer -- initialization to -1
				int positionOfTheNextOne = -1;
				// This will hold the minimal cost for moving to the next customer - initialized to something very large
				double bestCostForTheNextOne = Double.MAX_VALUE;
				//This is the last customer of the route (or the depot if the route is empty)
				Node lastInTheRoute = nodeSequence.get(nodeSequence.size() - 1);
				//identify nearest non-routed customer
				for (int k = 1 ; k < allNodes.size(); k++)
				{
					// The examined node is called candidate
					Node candidate = allNodes.get(k);
					// if this candidate has not been pushed in the solution
					if (candidate.isRouted == false)					{
						//This is the cost for moving from the last to the candidate one
						double trialCost = distanceMatrix[lastInTheRoute.ID][candidate.ID];
						//If this is the minimal cost found so far -> store this cost and the position of this best candidate
						if (trialCost < bestCostForTheNextOne && candidate.demand<= remaining)
						{
							positionOfTheNextOne = k;
							bestCostForTheNextOne = trialCost;
						}
					}
				}//end of for
				

				if (positionOfTheNextOne != -1 )
				{
					Node insertedNode = allNodes.get(positionOfTheNextOne);
					//Push him
					nodeSequence.add(insertedNode);
					s.cost = s.cost + bestCostForTheNextOne;
					routes.get(j-1).cost = routes.get(j-1).cost + bestCostForTheNextOne;
					//routes.get(j-1).load = load ;
					insertedNode.isRouted = true;
					remaining = remaining - insertedNode.demand;
					load = load + insertedNode.demand;
					routes.get(j-1).load = load;
					toRoute = toRoute - 1;
					//System.out.println(j + " / " + insertedNode.demand + " / " + load);
				} else { //this adds the depot at the end of every route when it is full and cannot take up more customers
					nodeSequence.add(depot);
					s.cost = s.cost + distanceMatrix[lastInTheRoute.ID][0];
					routes.get(j-1).cost = routes.get(j-1).cost + distanceMatrix[lastInTheRoute.ID][0];
					finalized = true;

				}//end of if
				//this adds the depot as the end point in case when although the vehicle is not completely full yet, there are no more customers to serve

			}//end of while loop


		}//end of for loop
		for (int j = 0; j<numberOfVehicles; j++)
		{
			int vehicle = j+1;
			System.out.print("Assignment to Vehicle " + vehicle + ": ");
			for (int k=0; k<s.rt.get(j).nodes.size(); k++) 
			{
				System.out.print(s.rt.get(j).nodes.get(k).ID + "  ");
			}
			System.out.println("");
			System.out.println("Route Cost: " + s.rt.get(j).cost + " - Route Load: " + s.rt.get(j).load);
			System.out.println("");

		}		
		System.out.println("Total Cost: " + s.cost);

		//START OF LOCAL SEARCH CODE///
		//
		//The NN Solution has been generated
		//
		//Local Search
		System.out.println(" ");
		System.out.println("* * * * * * * * * * * * * * *");
		System.out.println("Local Search Starts (intra) !");
		System.out.println("* * * * * * * * * * * * * * *");
		System.out.println(" ");

		//this is a boolean flag (true/false) for terminating the local search procedure
		boolean terminationCondition = false;

		//this is a counter for holding the local search iterator
		int localSearchIterator = 0;

		//Here we apply the best relocation move local search scheme
		//This is an object for holding the best relocation move that can be applied to the candidate solution
		RelocationMove rm = new RelocationMove(); // in order to apply one relocation  move for all routes - dont want to lose previous if i change vehicle

		//Initialize the relocation move rm
		rm.positionOfRelocated = -1;
		rm.positionToBeInserted = -1;
		rm.route = 0;
		rm.moveCost = Double.MAX_VALUE;			
		// Until the termination condition is set to true repeat the following block of code
		while (terminationCondition == false)
		{
			//With this function we look for the best relocation move
			//the characteristics of this move will be stored in the object rm
			findBestRelocationMove(rm, s, distanceMatrix, numberOfVehicles);

			// If rm (the identified best relocation move) is a cost improving move, or in other words
			// if the current solution is not a local optimum
			if (rm.moveCost < 0)
			{
				//This is a function applying the relocation move rm to the candidate solution
				applyRelocationMove(rm, s, distanceMatrix);
				localSearchIterator = localSearchIterator + 1;
				
			}
			else
			{
				//if no cost improving relocation move was found,
				//or in other words if the current solution is a local optimum
				//terminate the local search algorithm
				terminationCondition = true;
			}

		}
		for (int j = 0; j<numberOfVehicles; j++)
		{
			int vehicle = j+1;
			System.out.print("New Assignment to Vehicle " + vehicle + ": ");
			for (int k=0; k<s.rt.get(j).nodes.size(); k++) 
			{
				System.out.print(s.rt.get(j).nodes.get(k).ID + "  ");
			}
			System.out.println("");
			System.out.println("Route Cost: " + s.rt.get(j).cost+ " - Route Load: " + s.rt.get(j).load);
			System.out.println(" ");
			
			

		}		
		System.out.println("Total Cost: " + s.cost);	
		System.out.println("Total Iterations: " + localSearchIterator);

	}//end of main

	private static void findBestRelocationMove(RelocationMove rm, Solution s, double [][] distanceMatrix, int numberOfVehicles) 
	{
		//This is a variable that will hold the cost of the best relocation move
		double bestMoveCost = Double.MAX_VALUE;

		//We will iterate through all available nodes to be relocated
		for (int j = 0; j<numberOfVehicles; j++)
		{

			for (int relIndex = 1; relIndex < s.rt.get(j).nodes.size() - 1; relIndex++)
			{
				//Node A is the predecessor of B
				Node A = s.rt.get(j).nodes.get(relIndex - 1);
				
				//Node B is the relocated node
				Node B = s.rt.get(j).nodes.get(relIndex);
				
				//Node C is the successor of B
				Node C = s.rt.get(j).nodes.get(relIndex + 1);

				//We will iterate through all possible re-insertion positions for B
				for (int afterInd = 0; afterInd < s.rt.get(j).nodes.size() -1; afterInd ++)
				{
					// Why do we have to write this line?
					// This line has to do with the nature of the 1-0 relocation
					// If afterInd == relIndex -> this would mean the solution remains unaffected
					// If afterInd == relIndex - 1 -> this would mean the solution remains unaffected
					if (afterInd != relIndex && afterInd != relIndex - 1)
					{
						//Node F the node after which B is going to be reinserted
						Node F = s.rt.get(j).nodes.get(afterInd);
						
						//Node G the successor of F
						Node G = s.rt.get(j).nodes.get(afterInd + 1);

						//The arcs A-B, B-C, and F-G break
						double costRemoved1 = distanceMatrix[A.ID][B.ID] + distanceMatrix[B.ID][C.ID];
						double costRemoved2 = distanceMatrix[F.ID][G.ID];
						double costRemoved = costRemoved1 + costRemoved2;

						//The arcs A-C, F-B and B-G are created
						double costAdded1 = distanceMatrix[A.ID][C.ID];
						double costAdded2 = distanceMatrix[F.ID][B.ID] + distanceMatrix[B.ID][G.ID];
						double costAdded = costAdded1 + costAdded2;

						//This is the cost of the move, or in other words
						//the change that this move will cause if applied to the current solution
						double moveCost = costAdded - costRemoved;

						//If this move is the best found so far
						if (moveCost < bestMoveCost)
						{
							//set the best cost equal to the cost of this solution
							bestMoveCost = moveCost;

							//store its characteristics
							rm.positionOfRelocated = relIndex;
							rm.positionToBeInserted = afterInd;
							rm.moveCost = moveCost;
							rm.route = j;
						}
					}
				}
			}
		}
	}


	//This function applies the relocation move rm to solution s
	private static void applyRelocationMove(RelocationMove rm, Solution s, double[][] distanceMatrix) 
	{
		//This is the node to be relocated
		Node relocatedNode = s.rt.get(rm.route).nodes.get(rm.positionOfRelocated);

		//Take out the relocated node
		s.rt.get(rm.route).nodes.remove(rm.positionOfRelocated);

		//Reinsert the relocated node into the appropriate position
		//Where??? -> after the node that WAS (!!!!) located in the rm.positionToBeInserted of the route

		//Watch out!!! 
		//If the relocated customer is reinserted backwards we have to re-insert it in (rm.positionToBeInserted + 1)
		if (rm.positionToBeInserted < rm.positionOfRelocated)
		{
			s.rt.get(rm.route).nodes.add(rm.positionToBeInserted + 1, relocatedNode);
		}
		////else (if it is reinserted forward) we have to re-insert it in (rm.positionToBeInserted)
		else
		{
			s.rt.get(rm.route).nodes.add(rm.positionToBeInserted, relocatedNode);
		}

		// The rest of the code is just for testing purposes
		// to check if everything is OK
		double newSolutionCost = 0;
		for (int i = 0 ; i < s.rt.get(rm.route).nodes.size() - 1; i++)
		{
			Node A = s.rt.get(rm.route).nodes.get(i);
			Node B = s.rt.get(rm.route).nodes.get(i + 1);
			newSolutionCost = newSolutionCost + distanceMatrix[A.ID][B.ID];
		}
		if (s.rt.get(rm.route).cost + rm.moveCost != newSolutionCost)
		{
			System.out.println("Something Went wrong with the cost calculations !!!!");
		}

		//update the cost of the solution and the corresponding cost of the route object in the solution
		s.cost = s.cost + rm.moveCost;
		s.rt.get(rm.route).cost = s.rt.get(rm.route).cost + rm.moveCost;
		//System.out.println("Check Results");
	}


}//end of VRP comp 4


// Representation for both customers and depot
class Node 
{
	int x;
	int y;
	int ID;
	int demand;

	// true/false flag indicating if a customer has been inserted in the solution
	boolean isRouted; 

	Node() 
	{
	}
}

class Solution 
{
	double cost;
	ArrayList<Route> rt;

	//This is the Solution constructor. It is executed every time a new Solution object is created (new Solution)
	Solution ()
	{
		// A new route object is created addressed by rt
		// The constructor of route is called
		rt = new ArrayList<Route>();
		cost = 0;
	}
}

class Route 
{
	ArrayList <Node> nodes;
	double cost;
	int ID;
	int load;
	int capacity;

	//This is the Route constructor. It is executed every time a new Route object is created (new Route)
	Route() 
	{
		cost = 0;
		ID = -1;
		capacity = 50;
		load = 0;

		// A new arraylist of nodes is created
		nodes = new ArrayList<Node>();
	}
}

class RelocationMove 
{
	int positionOfRelocated;
	int positionToBeInserted;
	int route;
	double moveCost;

	RelocationMove() 
	{
	}
}