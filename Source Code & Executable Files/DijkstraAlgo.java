public class DijkstraAlgo
{

	public static Object dijObj(int[][] adjMatrx, int src , int dest ,int userInput) 
	{
		
		int nxtNode;
		int m;
		int n;
		int x;
		int j;
		int min;
		int tot;
		int[] dist = new int[adjMatrx.length];	    //this array will maintain shortest distance between 2 nodes
		int[] visitdNode = new int[adjMatrx.length];	//this array will keep track of visited nodes	
				
		nxtNode = src;					  	    //an index to next node having source node
			for(m=0;m<adjMatrx[0].length;m++)	//populate dist[] array with subsequent weights
			{
				dist[m] = adjMatrx[src][m];	
			}
			
		
		int[] pred = new int[adjMatrx[0].length];  //initialize predecessor [pred] that will help further to traverse path
			
		for (n = 0; n < pred.length; n++) 					
		{pred[n] = src;}
		
		visitdNode[nxtNode] = 1;			// initially source is considered as visited; set to true
		
		for(x=0;x<adjMatrx[0].length; x++) // sort dist[] array on min path weights and determines shortest path between nodes
		{
			min = Integer.MAX_VALUE;

			for (j = 0; j < dist.length; j++) {
				
				if (visitdNode[j]!=1 && j != src && dist[j] != -1 && src != -1) // if a node is neither visited and nor its sorce node or a down node then calculate
					{
					if (dist[j] < min) { //search for min distance and save that node in nextHop
						min = dist[j];
						nxtNode = j;
					}
				}
			}

			if (nxtNode == dest) 	// if nextHop equals to destination thats the shortest path									
			{ break; }

			visitdNode[nxtNode] = 1;						
			for (int i = 0; i < dist.length; i++) {

					if(visitdNode[i] != 1 && dist[i] == -1 && adjMatrx[nxtNode][i] != -1)	//handles down nodes with -1 weight which means nodes having no link/connectivity
					{
						dist[i] =  adjMatrx[nxtNode][i] + dist[nxtNode];
						pred[i] = nxtNode;
					}
					
					else if(adjMatrx[nxtNode][i] != -1 && dist[nxtNode] > min+adjMatrx[nxtNode][i])
					{
						dist[i] =  adjMatrx[nxtNode][i] + dist[nxtNode];
						pred[i] = nxtNode;	
					}
				
			}
		}
		
		
		/*when when user input is 2 for creating shortest path option 3. User provided source and 
		 * destination values are passed further to output minimum path*/
		
		if(userInput ==2 ) 
		{
		
		minValue(pred, src, dest, dist.length,1);
			System.out.println();
			tot = dist[dest] - dist[src]; // total Cost
			if(tot <= 0)	// when user request for the router that is down or removed from the topology
			{ System.out.println("Router is down. Please choose different router"); }
			else
			{ System.out.println("Total cost =  "+ tot); }
	    }
		
		/*To determine connection table for the router user has requested*/
		
		if(userInput==1)
		{
			minValue(pred, src, dest, dist.length, 2);
		}
		else return null;
		
		return null;
    }
	
	
	/*This function output's shortest path between two nodes and populates connection router table */
	


	public static void minValue(int[] pred, int src, int dest, int length, int userInput)
	{
		int i = dest;
		int c = 1; // stores current value
		int[] routePath = new int[length];
		routePath[0] = i;
		boolean fl=false;
		int k,x;
		while (pred[i] != src) // aligns predecessor routers to edgePath[] array in order to output the path
		{
			i = pred[i];
			routePath[c] = i;
			c++;
		}
		
		routePath[c] = src;

		if(userInput==1) 	// used to output the shortest path between source and destination
		{
			System.out.print("Shortest path from "+(src + 1)+" to "+(dest + 1));
			System.out.println();
		
			for (k = c; k > 0; k--)
				{
				System.out.print("R" + (routePath[k] + 1) + " to ");		
			}
			System.out.print("R" + (routePath[0] + 1)) ;
		}
		
		if(userInput==2) 	// used to get connection table for specified router
		{	
			if(c>0)
			{
				for (x = c-1; x > 0; x--)
				{
					System.out.print("\tR" + (routePath[x] + 1) + "\n");
					fl=true;
				}
			}

			if(fl==false)
			{
				System.out.print("\tR" + (routePath[0] + 1)+"\n") ;
			}

		}
	}
}
