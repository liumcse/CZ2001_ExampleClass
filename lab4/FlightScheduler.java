
import java.util.*;
import java.text.DecimalFormat;

/**
 * @author Ivan Yang
 *
 */

public class FlightScheduler {
    static ArrayList<String> cities = new ArrayList<String> (Arrays.asList("Singapore","Kuala Lumpur","Jakarta","Bali","Bangkok",
            "Hanoi","Manila","Cebu","Perth","Melbourne","Sydney","Auckland","Port Moresby","Taipei","Tokyo","Osaka","Sapporo",
            "Hong Kong","Shanghai", "Beijing","Seoul","New Delhi", "Mumbai","Doha","Dubai","Tel Aviv","Istanbul","Cairo","Johannesburg",
            "Cape Town","Casa Blanca","Kiev","Vienna", "Athens","London","Geneva","Stockholm","Madrid","Lisbon","Frankfurt","Copenhagen",
            "Amsterdam", "Paris","Berlin","Rome","Moscow","Havana","Port-au-Prince","New York", "Los Angeles","Chicago","San Francisco",
            "Mexico City","Ottawa","Brasilia","Rio de Janeiro","Santiago", "Lima","La Paz","Buenos Aires"));//60 cities


    public static void main(String[] args){
        int i=1;
        int start;
        int end;
        long startTime;
        long duration;
        Random rand= new Random();
        for (String s : cities) {
            System.out.printf("%2d. ",i);
            System.out.println(s);
            i++;
        }
        System.out.println("");//new line
        //System.out.println("debug 1");
        for(int j=2;j<13;j++){//for 10,15,20,25,30,35,40,45,50,55,60 cities
            LinkedList[] adj;
            //System.out.println("debug 2."+j);
            System.out.println("");//new line
            System.out.println("----------------------------------------------------------------------------------------------------");
            System.out.println("Total number of cities: "+(j*5));//number of cities in multiples of 5
            adj = AdjacencyList(j * 5);//build graph of connections between j*5 cities
            System.out.println("");//new line
            for(int k=0;k<3;k++) {//choose 3 start and end points for each graph
                start=end=0;
                while (start == end) {//randomize start and end cities, ensure not the same
                    start = (rand.nextInt(j * 5));
                    end = (rand.nextInt(j * 5));
                }
                System.out.println("Start = " + cities.get(start) + ",   End = " + cities.get(end));
                startTime=System.nanoTime();//start clock
                breadthFS(adj, start, end, (j * 5));//find and print shortest path
                duration=System.nanoTime()-startTime;//stop clock
                double milliseconds = ((double)duration / 1000000);
                System.out.println("Elapsed time = "+ new DecimalFormat("#.######").format(milliseconds)+" ms");
                System.out.println("");//new line
            }
        }
        //System.out.println("debug 3");
    }

    private static void breadthFS(LinkedList[] newAdj, int start, int end,int size){
        int city;
        LinkedList[] adj= newAdj.clone();//copy the adjacency list
        LinkedList<Integer>queue= new LinkedList<Integer>();//create queue for Depth First Search
        ArrayList<Integer> visited = new ArrayList<Integer>();//create ArrayList for marking visited cities
        ArrayList<Integer> parent = new ArrayList<Integer>();//create ArrayList for marking previous cities
        for(int i=0;i<size;i++){
            visited.add(0);//initialise array of 0s, one for each unvisited city
            parent.add(-1);//initialise all cities to have parent -1
        }
        //System.out.println("debug entered BFS, visited and parent initialised");
        visited.set(start,1);//set start city to 1 to mark visited
        queue.add(start);//add start city to queue
        while(queue.peek()!=null){//while queue not empty
            //System.out.println("debug taking next queue item "+cities.get(queue.peek()));
            city=queue.poll();//set city to be the next destination to be checked
            visited.set(city,1);//mark city as visited
            if (city==end){//found end
                //System.out.println("debug found destination");
                ArrayList<Integer> shortestPath = new ArrayList<Integer>();//create new arraylist to work backwards
                Integer node = city;
                while(node != -1) {
                    shortestPath.add(node);//add city to list
                    node = parent.get(node);//iterate node to be the parent on the current node
                }
                //System.out.println("debug finished creating shortestPath list");
                Collections.reverse(shortestPath);//reverse arraylist to obtain path from start to end
                for (int j=0;j<shortestPath.size();j++) {
                    System.out.print(cities.get(shortestPath.get(j)));//print path
                    if (j!=(shortestPath.size()-1))
                        System.out.print(" -> ");
                }
                System.out.println("\nFewest number of flights = "+(shortestPath.size()-1));
                return;//exit breadthFS as shortest path has been found
            }
            else{//city is not final destination
                for (int j=0;j<adj[city].size();j++){//push all of city's neighbours into queue
                    if (queue.contains((Integer)adj[city].get(j))||(visited.get((Integer)adj[city].get(j))==1))
                        continue; //dont push cities already in queue or visited
                    else {//city.get(j) not in queue and not visited
                        queue.add((Integer) adj[city].get(j));
                        parent.set((Integer) adj[city].get(j),city);//set city to be previous city from its neighbours
                    }
                }
            }
        }
        //if queue is empty and destination not reached...
        System.out.println("No path from "+cities.get(start)+" to "+cities.get(end));

    }

    private static LinkedList[] AdjacencyList(int num) {//returns completed adjacency list
            LinkedList<Integer>[] adj;
            int count=0;//count total number of flights
            adj = (LinkedList<Integer>[])new LinkedList[num];//adj[x] is the xth city
            //System.out.println("debug 2."+(num/5)+"."+1);
            for (int i = 0; i < num; i++){
                adj[i] = new LinkedList<Integer>();//each linked list contains the adjacent cities connected to city x by a direct flight
            }
            //System.out.println("debug 2."+(num/5)+"."+2);
            for(int j=0;j<num;j++){//input random flights connecting the cities
                Random rand = new Random();
                for(int k=0;k<2;k++) {//do this twice for each city, so every city has at least two destinations
                    int one = j;
                    int two = j;

                    while (one == two) {//no reflexivity, so avoid flight to and from same city
                        two = rand.nextInt(num);
                        if (adj[one].contains(two))
                            two = j;// avoid repeat by setting one==two so while loop loops again
                    }

                    adj[one].add(two);//add two as destination from one
                    adj[two].add(one);//add one as destination from two
                    count+=2;//increase count of number of flights by 2
                }
                //no weights as we are counting number of flights not distance or time.
            }
            //System.out.println("debug 2."+(num/5)+"."+3);
            for(int k=0;k<num;k++){//iterate through adj
                System.out.printf("%2d. %-14s has flights to ",(k+1),cities.get(k));
                for (int m=0;m<adj[k].size();m++){//iterate through adj[x]
                    System.out.print(cities.get(adj[k].get(m)));
                    if (m!=adj[k].size()-1) System.out.print(", ");//while not last object in list
                }
                System.out.println("");//new line
            }
            System.out.println("Total number of flights = "+count);
            //System.out.println("debug 2."+(num/5)+"."+4);
            return adj;
    }
}
