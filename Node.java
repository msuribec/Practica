import java.util.ArrayList;
/**
 * Class that represents a vertex or node in the graph
 * @author María Sofía Uribe
 * @author Isabel Graciano
 */
public class Node implements Comparable<Node>{

    /** identifier of the node*/
    int idNode;

    /** X Coordinate of the node*/
    private double coordx;
    /** Y Coordinate of the node*/
    private double coordy;
    /** Name of the location represented by the node*/
    private final String name;

    /** parent node of the current node in a path*/
    Node parent;//Nodo auxiliar para ir guardando posiles padres en el camino

    /** listo of edges reachable from the current node*/
    ArrayList<Edge> EdgestoVisit;
    /** True if the current node shares a ride with another node*/
    boolean isRideSharing;

    /** Heuristic estimated cost from the node to node 1*/
    double hval;

    /** Exact cost of the path from the current node to any vertex*/
    double gval;//
    /** Exact cost of the path from a child node to the end node.Will be computed by f= g+h in the algorithm*/
    double fval;//


    /**
     * Constructs a vertex or node in the graph given an ID , x coordinate, y coordinate and a name of the location
     * @param iden identifier of the node
     * @param x  x Coordinate of the node
     * @param y  y Coordinate of the node
     * @param name Name of the location represented by the node
     */
    public Node(int iden, double x,  double y,String name){
        this.idNode = iden;
        this.name = name;
        this.coordx =x;
        this.coordy=y;
        this.isRideSharing = false;
        this.fval =0;
        this.EdgestoVisit=new ArrayList<>();
    }

    /**
     * Constructs a vertex or node in the graph given an ID , x coordinate, y coordinate and a name of the location
     * @param iden identifier of the node
     * @param x  x Coordinate of the node
     * @param y  y Coordinate of the node
     * @param name Name of the location represented by the node
     * @param h  Heuristic estimated cost from the node to node 1
     */
    public Node(int iden, double x, double y,String name,double h){
        this.idNode = iden;
        this.name = name;
        this.coordx =x;
        this.coordy=y;
        this.fval=0;
        this.hval=h;
        this.isRideSharing = false;
        this.EdgestoVisit=new ArrayList<>();
    }

    /**
     * resets the parent, f value and g value of the node to null and 0 respectively
     */
    public  void reset(){
        this.parent= null;
        this.fval =0;
        this.gval=0;
    }


    /**
     * Compares the f value of the current node with the f value of a given node
     * @param j a value of  0 if the f value of the argument is equal to this Node's f value';
     *          a value less than 0 if this Node's f value' is numerically less than the f value of the argument;
     *          and a value greater than 0 if this Node's f value is numerically greater than the f value of the argument
     *          (signed comparison).
     *
     */
    @Override
    public int compareTo(Node j) {
        return Double.compare(this.fval,j.fval);
    }

    /**
     * Returns the Name of the location represented by the node
     * @return the Name of the location represented by the node
     */
    public String toString(){
        return name;
    }





}