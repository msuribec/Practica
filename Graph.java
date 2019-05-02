import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * Class that creates the graph , determines which nodes will share a ride and prints the answer.
 * @author María Sofía Uribe
 * @author Isabel Graciano
 */
public class Graph {

    /** number of nodes of the graph*/
    int V;
    /** Hashmap that has an integer as a key and Nodes as a value*/

    private HashMap<Integer,Node> Map = new HashMap<>();

    /** Constructs a graph
     * @param v number of nodes in the graph*/

    public Graph(int v) {
        V = v;
    }

    /** adds the destination node as successor to the source node
     * @param source id of the source node
     * @param destination id of the destination node
     * @param weight of the edge that connects the source node and the destination node*/

    public void addSuccesor (int source, int destination, double weight){
        Node dest = Map.get(destination);
        Edge e = new Edge(dest,weight);
        Node src = Map.get(source);
        src.EdgestoVisit.add(e);
    }

    /** changes the h value of a given vertex
     * @param idnode id of the node
     * @param h h value of the node*/
    public void addHval(int idnode, double h){
        Map.get(idnode).hval = h;
    }

    /** Adds a node to the graph
     * @param iden identifier of the node
     * @param x  x Coordinate of the node
     * @param y  y Coordinate of the node
     * @param name Name of the location represented by the node*/
    public void addNode(int iden, double x,  double y,String name){
        Map.put(iden,new Node(iden,x,y,name));
    }

    /** Adds a node to the graph
     * @param iden identifier of the node
     * @param x  x Coordinate of the node
     * @param y  y Coordinate of the node
     * @param name Name of the location represented by the node
     * @param h h value of the node*/
    public void addNode(int iden, double x,  double y,String name,double h){
        Map.put(iden,new Node(iden,x,y,name,h));
    }


    /** Gets the weight of the edge that connects two nodes
     * @param source id of the source node
     * @param destination id of the destination node
     * @return weight of the edge that connects two nodes*/
    public double getMinCost(int source, int destination){
        Node src = Map.get(source);
        for (Edge e : src.EdgestoVisit){
            if (e.NodeDest.idNode == destination){
                return e.cost;
            }
        }
        return -1;
    }



    /**
     * resets the parent, f value and g value of every node in a map
     */
    public void reset() {
        Iterator it = Map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry element = (Map.Entry) it.next();
            Map.get(element .getKey()).reset();
        }

    }

    public double recovervalue(List<Integer> path){
        if (path.size() == 0) return 0;
        if (path.size() == 1) return Map.get(path.get(0)).hval;
        double sum=0;
        for (int i = 0; i < path.size()- 1; i++){
            int src = path.get(i);
            int dest = path.get(i+1);
            if (dest !=1){
                sum += getMinCost(src,dest);
            }

        }
        return sum;
    }

    /**
     * Determines which nodes will share a ride and prints the answer.
     * @param condition condition to compute the maximum value an owner can excede their cost to reach vertex 1
     */
    public void solve(double condition){



        List<List<Integer>> resp= new LinkedList<>();

        for (int i = 1; i <= V; i++)
            resp.add(new LinkedList<>());// inicializar todas las listas

        for (int i = V; i >= 2; i--){

            Node ni = Map.get(i);
            Node n1 = Map.get(1);

            if (!ni.isRideSharing){
                DirectedSearch.Search(ni,n1,false);// calcular un
                // camino corto desde el nodo ni hasta el nodo 1 pasando por otros nodos(ignorar el directo)
                List<Integer> path = DirectedSearch.recoverPath(n1);// Devolverse usando el nodo parent para reconstruir el camino

                resp.add(new ArrayList<>());//
                for (Integer a: path) {
                    Node na = Map.get(a);
                    if (!na.isRideSharing){
                        resp.get(i).add(a);
                        na.isRideSharing = true;
                    }
                }
            }
            reset();
        }


        for (int i = 0; i < resp.size(); i++){
            List<Integer> route1 = resp.get(i);
            System.out.println("r1 : " + route1);
            if (!route1.isEmpty() && route1.size()<5){
                double totalcost = recovervalue(route1);
                Integer mainCar = route1.get(0);
                for (int j = i+1; j < resp.size() -1 ; j++){//next non empty route
                    List<Integer>  nextr = resp.get(j);
                    if (!nextr.isEmpty() && nextr.size()< 5) {
                        System.out.println("r1 : " + route1);
                        System.out.println("next r: " +nextr);
                        for (int k = 0; k < nextr.size() && route1.size() < 5; k++ ){
                            Integer nextCar = nextr.get(k);
                            System.out.println("main car: " + mainCar);
                            System.out.println("next car : " + nextCar);

                            double oldCost1 = Map.get(mainCar).hval;

                            System.out.println("oldcost " + oldCost1);
                            System.out.println("sum until now "+totalcost);

                            double sum1 = totalcost + getMinCost(route1.get(route1.size()-1),nextCar)+ Map.get(nextCar).hval;
                            System.out.println("total "+ totalcost);

                            if (totalcost <= (condition)*(oldCost1)){
                                route1.add(nextCar);
                                nextr.remove(nextCar);
                                System.out.println("new r1 : "+ route1);
                                System.out.println("new nextr : "+ nextr);
                                totalcost = sum1;
                            }
                        }

                    }
                }

            }


        }



        final String nombreDelArchivo = "respuesta-ejemplo-U="+V+"-p="+condition+".txt";
        try {
            PrintWriter escritor = new PrintWriter(nombreDelArchivo, "UTF-8");
            for (List<Integer> L: resp) {
                if (L.size() > 0){
                    for (Integer a: L )
                        if (a!=1) escritor.print(a+" ");
                    escritor.println();
                }
            }
            escritor.close();
        }
        catch(IOException ioe) {
            System.out.println("Error escribiendo el archivo de salida " + ioe.getMessage() );
        }


    }
}