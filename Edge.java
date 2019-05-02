/**
 * Class that represents an edge or arc in the graph
 * @author María Sofía Uribe
 * @author Isabel Graciano
 */
//CAMBIA NORMBRE A CAMINO O PATH O SHORT PATH
public class Edge{


    /** cost of going from the starting node to the end node*/
    final double cost;

    /** destination node*/
    final Node NodeDest;

    /** Constructs the edge to the destination with a determined weight
    * @param nodeDest destination node
     *@param cost cost of the edge
     * */
    public Edge(Node nodeDest, double cost){
        this.NodeDest = nodeDest;
        this.cost = cost;
    }
}