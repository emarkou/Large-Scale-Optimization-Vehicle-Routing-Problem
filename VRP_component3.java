import java.util.ArrayList;
import java.util.Random;



public class VRP_component3 {
	

	public static void main(String[] args) {
		// Set seed
		Random ran = new Random(150589);
		// Define number of available customers and vehicles
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
		
		//Create customers and add them to the list as well
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
		
		// Create the distance Matrix
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
		
		System.out.println("* * * * * * * * * * * * *");
		System.out.println("VRP Starts !");
		System.out.println("* * * * * * * * * * * * *");
		System.out.println(" ");
		
		// VRP heuristic
		// initialize solution
		Solution s = new Solution();
		// array list of ALL routes
		ArrayList<Route> routes = s.rt;
		// routes initialization - one for every vehicle
		for (int i=1; i<=numberOfVehicles; i++)
		{
			Route route_nodes = new Route();
			route_nodes.ID = i;
			routes.add(route_nodes);
		}
		
		// toRoute indicate how many customers are have been routed yet
		int toRoute = 30;
		// customer assignment to vehicles
		for (int j = 1; j<=numberOfVehicles; j++)
		{
			ArrayList <Node> nodeSequence = routes.get(j-1).nodes;
			int remaining = routes.get(j-1).capacity;
			int load = routes.get(j-1).load;
			nodeSequence.add(depot);
			boolean finalized = false;
			// If all customers are routed then add depot as the end point to the vehicles that did not travel at all
			if (toRoute == 0) {
				finalized = true;
				nodeSequence.add(depot);
				
			}
									
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
		
		// Print the results of the VRP
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

		
		

	}//end of main
	
}//end of VRP comp 3


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