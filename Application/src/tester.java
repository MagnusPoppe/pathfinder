/**
 * Created by Magnus Poppe Wang on 21.04.2016.
 * Class created to test the class "Graph".
 *
 * @see Graph
 */
public class tester {


    final static String FILENAME = "C:\\Users\\Magnu\\OneDrive\\Informatikk - Høyskolen i Telemark\\S4 V16   5610 Algoritmer og Datastrukturer\\Forelesning 3.5\\pathfinder\\Application\\src\\graph.txt";

    final static String start   = "A";
    final static String end     = "D";

    public static void main(String[] args)
    {
        Graph g = createWithStats( FILENAME );
        //g.unweighted(start, end);
        g.weighted(start, end);
        System.out.println(g.printPath(end));
    }

    private static Graph createWithStats( String file )
    {
        Graph g = new Graph( file );

        // PRINTING STATS:
        System.out.println("GRAPH CREATION STATISTICS");
        System.out.println(" -  Number of nodes:        "+g.nodes);
        System.out.println(" -  Dictionary looks like:  "+g.map.toString());
        System.out.println(" -  Random node's adj list: "+g.map.get("A").adj.toString());
        System.out.println();
        return g;
    }
}
