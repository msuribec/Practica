
import java.util.PriorityQueue;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Class that implements the A* algorithm to reconstruct a short path that goes from a given vertex to the vertex 1
 * @author María Sofía Uribe
 * @author Isabel Graciano
 */
public class DirectedSearch{

    /**
     * Method implements the A* algorithm to find short path that goes from a given vertex to another vertex
     * @param src starting vertex
     * @param target end vertex
     * @param includeDirectPath true if the direct path has to be included
     */
    public static void Search(Node src, Node target, boolean  includeDirectPath){

        boolean reachEnd = false;
        Set<Node> visited = new HashSet<Node>();
        PriorityQueue<Node> queue = new PriorityQueue<Node>();
        int counter = 1;
        src.gval = 0;
        queue.add(src);

        while(!queue.isEmpty()&& !reachEnd){
            Node current = queue.poll();

            visited .add(current);
            if(current.idNode == target.idNode  ) reachEnd = true; //target found

            for(Edge e : current.EdgestoVisit){
                Node child = e.NodeDest;

                double cost = e.cost;
                double tempgval = current.gval + cost;// lo que me he gastado hasta el momento
                double tempfval = tempgval + child.hval;// predicción de lo que me gastaré en total

                if (counter == 1 && !includeDirectPath){// saltarse el camino directo hacia 1
                    counter++;
                    continue;
                }

                if( (visited .contains(child) && tempfval >= child.fval)) continue;

                if(!queue.contains(child) || tempfval < child.fval){
                    child.parent = current;
                    child.gval = tempgval;
                    child.fval = tempfval;

                    if(queue.contains(child)) queue.remove(child);
                    queue.add(child);
                }
            }
        }
    }


    /**
     * Method that recovers the path taken to get to a goal vertex
     * @param goal end node of the path
     * @return list of nodes that were taken to get to the goal vertex
     */
    public static List<Integer> recoverPath(Node goal){
        List<Integer> path = new ArrayList<Integer>();
        for(Node node = goal; node!=null; node = node.parent)path.add(node.idNode);
        Collections.reverse(path);
        return path;
    }


}

