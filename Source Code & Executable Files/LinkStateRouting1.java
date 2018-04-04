
/*
################################## CN-542 INDIVIDUAL ASSIGNMENT ####################################### 

          Name Saba Soleha Ghauri      -    CWID A20336256     -     Seat No. 017  

LinkStateRouting1.java is used to display the main menu with 6 different options to show the 
working and implementation of Link State Routing Protocol, which is based on Dijkstras Algorithm
Dijkstras Algorithm is implemented in a separate java file called DijkstrasAlgo.java 

*/ 

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class LinkStateRouting1 {
	
	static DijkstraAlgo dijObj = new DijkstraAlgo();			//dijObj-an object of class DijkstraAlgo
	public static void main(String[] args) throws Exception 
	{

		int adjMatrx[][]=null;  //2D matrix to store routing table fromn topology.txt
		//prints the menu with 6 different operations
	    System.out.println("\t$ MENU $");
		System.out.println("1. Create a Network Topology\n2. Build a Forward Table For A Router \n3. Shortest Path to Destination Router \n4. Modify A Topology \n5. Best Broadcast Router \n6. Exit");
		System.out.println("ENTER COMMAND:");	
		Scanner scan1 = new Scanner(System.in);//reads users input
		int userInput = scan1.nextInt();//stores it in a variable
		while(userInput!=6)
		{
			switch (userInput)
			{
			case 1:
				   System.out.println("Input original network topology matrix data file:");
    			   String fname = scan1.next();
    			   adjMatrx = readInput(fname);
    			   displayRT(adjMatrx);
    			   break;
				
			case 2:
				   routingTable(adjMatrx);
				   break;
				   
			case 3:
				   bestRoute(adjMatrx);
				   break;
				   
			case 4:
				   modifyTopology(adjMatrx);
				   break;

			case 5:
				   bestBroadcast(adjMatrx);
				   break;	   
				  
			default:
				    System.out.println("Invalid. Please enter a valid option from menu");
			}
 					System.out.println("\n\t$ MENU $");
					System.out.println("1. Create a Network Topology\n2. Build a Forward Table For A Router \n3. Shortest Path to Destination Router \n4. Modify A Topology \n5. Best Broadcast Router \n6. Exit");
					System.out.println("ENTER COMMAND:");
			userInput=scan1.nextInt();
			
		}
		
		System.out.println("Exit CS542-04 2017 Fall project. Good Bye!");
		scan1.close();
		
	}
	
	/* displayRT displays the Routing tables as 2D matrix and it also returns total number of routers in topology */
	 public static void displayRT(int adjMatrx[][])
    {
    	for(int i=0;i<adjMatrx.length;i++){
    		for(int j=0;j<adjMatrx.length;j++)
    		{System.out.print(adjMatrx[i][j] + " ");}
    		 System.out.println();
    	}
    
    }
	
	/* readInput is used to read the input matrix from the source location. Option 1 from the menu
	prompts user to enter a input file , which is then read and stores in  a 2D matrix
	*/
	public static int[][] readInput(String fname) throws Exception {
		
		int rCount = 0; // rCount stores Row Counts
		int cCount = 0; // cCount stores Column Counts
		int temp=0;
		String rowText;
		String row[]; 
		
		BufferedReader buff1 = new BufferedReader(new FileReader(fname));    								 
		while((rowText = buff1.readLine())!=null) //read data until file is not empty
		{
			rCount++;
			row = rowText.split(" "); // data is separated by space
			cCount = row.length;
			
		}
		buff1.close();

		// to store data in 2D integer matrix
		int Adjmatrix[][] = new int[rCount][cCount];
		BufferedReader buff2 = new BufferedReader(new FileReader(fname)); 
		while ((rowText = buff2.readLine()) != null)
		{
			row = rowText.split(" ");
			for(int i=0;i<cCount;i++)
			{
				Adjmatrix[temp][i] = Integer.parseInt(row[i]);
			}
			temp++;
		}
		
		buff2.close();
		System.out.println("Network Topology Matrix:");
		return Adjmatrix;
	}
   
    /* routingTable() is called when user selects option 2. This function computes connection table 
     for respective router asked by the user and displays it, it calls DijkstraAlgo's object to 
     calculate the next hop for destination from a source node*/
    
	public static void routingTable(int Adjmatrix[][])
	{	
	
		int router;
		int i;
		Scanner sc2 =new Scanner(System.in);
		System.out.println("Enter the router");
		router = sc2.nextInt();
		System.out.println("Destination\tInterface");
		System.out.print("\tR" + router + "\t-" + "\n");

		for(i=0;i<Adjmatrix.length;i++)
		{			
			if(i!=router-1)
				{
				System.out.print("\tR"+(i+1));
				DijkstraAlgo.dijObj(Adjmatrix, router-1, i, 1);
				}					
		}	
		
	}
	
	/*bestRoute() is called for option 3 of the selection menu. It takes input source and destination routers
	 from user input in order to compute shortest path between them. it calls Dijkstra's Algorithm to
	 calculate shortest path between source and destination in the topology.txt*/
	
	public static void bestRoute(int adjmatrx[][]) throws IOException
	{
		int src;
		int dest;
		System.out.println("Enter the source router"); 
		Scanner sc3 =new Scanner(System.in);        // reads source router
		src = Integer.parseInt(sc3.nextLine()) - 1; //stores in src
		System.out.println("Enter the destination router");
		dest = Integer.parseInt(sc3.nextLine()) - 1;  // read and store destination router
		DijkstraAlgo.dijObj(adjmatrx,src,dest,2); // calls Dijkstras Algorithm to calculate shortest path 
	}
	
	/*modifyTopology() is triggered when user selects option 4 to delete a router from the network topology.
	 It asks user to input router to be deleted and updates the network topology matrix's subsequent 
	 router entry to -1 {rows & columns}. -1 here indicates that their is no connection path to that router
	 (either removed or down)
	 */
	
	public static void modifyTopology(int[][] adjMatrx) throws IOException{
		Scanner sc4 = new Scanner(System.in);
		int router;
		int i;
		int j;
		System.out.println("Enter router to be deleted:");
		router = sc4.nextInt(); // reads and stores router to be deleted into "router"
		
		for(i=0;i<adjMatrx.length; i++){
			if(i==router-1){// matches router within topology
				for(j=0;j<adjMatrx.length;j++)
					{
					adjMatrx[i][j] = -1;// down the router
					adjMatrx[j][i] = -1;// down the router
					}
			}
		}
		displayRT(adjMatrx); // displays the Routing Table with deleted Router

		//calculate best route including the deleted router in order to confirm modifications
		System.out.println("\nTo calculate shortest path\n");
		bestRoute(adjMatrx); //calls bestRoute function using modified matrix
	}	
    
     public static void bestBroadcast(int adjMatrx[][]) throws Exception{
		int brdcst[] = new int[adjMatrx.length];
		int c=0;
		int minV;
		minV=c;
		int i,j;
		for(i=0;i<adjMatrx.length;i++){			
			for(j=0; j<adjMatrx.length;j++){
				c=c+newTotal(adjMatrx,i,j,2);	// calculates shortest path from every node and select the router with min 
				if(j==adjMatrx.length-1)       //shortest path distance as the best router
					{
					brdcst[i]=c;
					c=0;	//restarts the counter 
					}				
				}
			}
		minV=FindSmallest(brdcst);// minv contains the best broadcast router
		System.out.println("Best Broadcast Router is R"+(minV+1));
	}	
				
				
	/* FindSmallest() selects the smallest value, shortest path from a given node to all other 
	routers within network topology. This method returns index of that router*/			
	public static int FindSmallest (int [] arr1){//start method

       int index=0;
       int min = arr1[index];
	   int i;
       for (i=0; i<arr1.length; i++){
			if (arr1[i] < min && arr1[i]>0 ){
               min = arr1[i];
               index = i;
           }
       }
       return index ;
   }

   
	public static int newTotal(int[][] adjMatrx, int src , int dest ,int userInput) {
		
		int[] dist = new int[adjMatrx.length];  // distance[] array will maintain shortest distance between 2 routers
		int[] visitd = new int[adjMatrx.length];//visitd[] array will keep track of visited nodes 
		int i, j;																
		int nextHop;
		nextHop	= src;	// An index to predecessor array 
		int min;
		
			for(j=0;j<adjMatrx[0].length;j++)	//Copy router subsequent weights into an distance[] array
				{
				dist[j] = adjMatrx[src][j];
				}

		int[] pred = new int[adjMatrx[0].length];//initialize predecessor that will help further to traverse path
		for (i = 0; i < pred.length; i++) 					
		{
			pred[i] = src;
		}
		visitd[nextHop] = 1;					// initially visited of source make it as true
		for(int x=0;x<adjMatrx[0].length; x++)	//sort distance array based on minimum path weight, to determine shortest path between routers
		{
			min = Integer.MAX_VALUE;
			for (j = 0; j < dist.length; j++) {
				if (visitd[j]!=1 && j != src && dist[j] != -1) {
					if (dist[j] < min) {
						min = dist[j];
						nextHop = j;
					}
				}
			}
			
			if (nextHop == dest) 										
			{break;}
			
			visitd[nextHop] = 1;						
			for (i = 0; i < dist.length; i++) {	
				if(visitd[i] != 1 && dist[i] == -1 && adjMatrx[nextHop][i] != -1)	//handle routers with -1 weight means routers having no link/connectivity
					{
					dist[i] =  adjMatrx[nextHop][i] + dist[nextHop];
					pred[i] = nextHop;
					}
					
				else if(adjMatrx[nextHop][i] != -1 && dist[nextHop] > min+adjMatrx[nextHop][i])
					{
					dist[i] = adjMatrx[nextHop][i] + dist[nextHop];
					pred[i] = nextHop;	
					}
			}
		}
		
		if(userInput ==2) 
		{
			minWeight(pred, src, dest, dist.length,1);
			int total;
			total = dist[dest] - dist[src];
			if(total == -1)	// when user request for the router that is down or removed from the topology
			{
				//System.out.println("Router is down. Please chose different router");
			}
			else
			{
			//System.out.println(total);	
			return total;
			}
	    }
		
		/*To determine connection table for the router user has requested*/
		
		if(userInput==1)
		{
			minWeight(pred, src, dest, dist.length, 2);
		}
		return 0;
		
    }
	
	
	public static void minWeight(int[] pred, int src, int dest, int length, int userInput)	
	{		
		int curr = 1;  //current variable
		int[] nodePath = new int[length];
		int i = dest;
		nodePath[0] = i;
		
		boolean flag=false;
		
		while (pred[i] != src) 			// aligns predecessor routers to nodePath[] array in order to output the path
		{
			i = pred[i];
			nodePath[curr] = i;
			curr++;
		}
		nodePath[curr] = src;

		if(userInput==1) 				// selects when user wants to output the shortest path between source and destination
		{
			for (int k = curr; k > 0; k--)
			{
				//System.out.print("R" + (nodePath[k] + 1) + " to ");
				
			}
			//System.out.print("R" + (nodePath[0] + 1)) ;
		}
		
		
		if(userInput==2) 					// selects when user wants to get connection table for specified router
		{	
			if(curr>0)
			{
				for (int x = curr-1; x > 0; x--)
				{
					System.out.print("\tR" + (nodePath[x] + 1) + "\n");
					flag=true;
				}
			}
			if(flag==false)
			{
				System.out.print("\tR" + (nodePath[0] + 1)+"\n") ;
			}
		}
	}

}
