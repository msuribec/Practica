import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Implementation of an algorithm to asign shared vehicles
 * Data structured used is a hashmap with nodes, each node has a list of reachable nodes
 * Implementacion de un algoritmo para asignar vehiculos compartidos

 * @author Mauricio Toro
 * @author Isabel graciano
 * @author María Sofía Uribe
 * @version 2
 */
public class ReadData
{
    /**
     * Method to read the file with the owners of the cars and the end location they must reach
     * @param  num  number of nodes is 1 from the end location and n-1 from the owners' locations
     * @param  p condition to compute the maximum value an owner can excede their cost to reach vertex 1
     */
    public static void leerArchivo(int num, double p){
        int mb = 1024 * 1024;
        long beforeUsedMem=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
        long startTime = 0;
        long endTime=0;
        final String nombreDelArchivo = "dataset-ejemplo-U="+num+"-p="+p+".txt";
        Graph graph = new Graph(num);
        try {
            BufferedReader br = new BufferedReader(new FileReader(nombreDelArchivo));
            String lineaActual = br.readLine();
            for (int i = 1; i <= 3; i++)lineaActual = br.readLine();
            for (int i = 0; i < num ; i++){
                lineaActual = br.readLine();
                String name= "";
                String [] cadenaParticionada = lineaActual.split(" ");
                if ((cadenaParticionada.length >= 4)) {
                    for (int j=3; j<cadenaParticionada.length;j++) name += " "+cadenaParticionada[j];
                }
                int v =Integer.parseInt(cadenaParticionada[0]);
                double x = Double.parseDouble(cadenaParticionada[1]);
                double y = Double.parseDouble(cadenaParticionada[2]);
                if (v==1) graph.addNode(v,x,y,name,0);
                else graph.addNode(v,x,y,name);
            }

            for (int i = 1; i <= 3; i++)
                lineaActual = br.readLine();
            startTime=System.currentTimeMillis();
            lineaActual = br.readLine();
            while (lineaActual != null){
                String [] cadenaParticionada = lineaActual.split(" ");
                int src = Integer.parseInt(cadenaParticionada[0]);
                int dest = Integer.parseInt(cadenaParticionada[1]);
                double weight = Double.parseDouble(cadenaParticionada[2]);
                if (src==1) graph.addHval(dest,weight);
                graph.addSuccesor(src,dest,weight);
                lineaActual = br.readLine();
            }
            endTime = System.currentTimeMillis();
        }
        catch(IOException ioe) {
            System.out.println("Error leyendo el archivo de entrada: " + ioe.getMessage());
        }




        graph.solve(p);
//        long endTime = System.currentTimeMillis();

        long timeElapsed = endTime - startTime;

        System.out.println("Execution time in milliseconds: " + timeElapsed);
        graph.reset();

        long afterUsedMem=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
        long actualMemUsed=afterUsedMem-beforeUsedMem;
        //System.out.print(actualMemUsed);
    }


    public static void main(String[] args){
        leerArchivo(205,1.2);
    }
}