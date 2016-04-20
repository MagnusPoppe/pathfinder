import java.util.*;

/**
 * Created by Magnus Poppe Wang on 18.04.2016.
 * @author Magnus Poppe Wang
 *
 *
 *
 */
public class Graph
{
    /**
     * The default cost of an edge.
     */
    public static final double INFINITY = Double.MAX_VALUE;

    /**
     * The dictionary contains a list of all registered
     * members of the map that we are analyzing.
     */
    private Map<String, Vertex> dictionary = new HashMap<>();

    /**
     * Adds an edge to the structure.
     *
     * @param name of source vertex
     * @param destName of destination vertex
     * @param cost of using the path.
     */
    public void addEdge( String name, String destName, double cost)
    {
        Vertex source       = dictionary.get( name );
        Vertex destination  = dictionary.get( destName );

        source.adj.add( new Edge( destination, cost ) );
    }

    public void print( Vertex destination )
    {
        if (destination.previous != null)
        {
            printPath( destination.previous.name );
            System.out.println( " to " );
        }
        System.out.println( destination.name );
    }

    private void printPath( String destinationName )
    {
        Vertex w = dictionary.get( destinationName );

        if ( w == null )
        {
            throw new NoSuchElementException( "" );
        }
        else if (w.distance == INFINITY)
        {
            System.out.println( w.name + " is unreachable.");
        }
        else
        {
            System.out.println( "( Cost is: " + w.distance + " ) " );
            print(w);
            System.out.println( );
        }

    }

    private void clear()
    {
        for (Vertex node : dictionary.values())
        {
            node.reset();
        }
    }
    /** UVEKTET:
     * Kode til søkealgoritmen for uvektet søkemetode.
     * Uvektet søking er når man bruker node -> node sortering
     * slik at avstanden mellom to noder er alltid 1.
     *
     * DETTE ER NIVÅORDEN TRAVERSERING AV ET SLAGS B-TRE. DETTE
     * KALLES BREDDE FØRST.
     */
    public void unweighted( String startName ) throws Exception
    {
        // CLEARS ALL NODES OF EARLIER DATA
        clear( );

        // GETS STARTING POSITION:
        Vertex start = dictionary.get(startName);
        if (start == null)
        {
            throw new Exception( "Not a valid start-destination name." );
        }

        // INITIATES STARTER VALUES FOR THE QUEUE
        Queue<Vertex> queue = new LinkedList<>();
        queue.add( start );

        start.distance = 0; // ZERO DISTANCE TO IT SELF.

        // STARTS THE PATHFINDING:
        while ( !queue.isEmpty( ) )
        {
            // GETTING NEXT ELEMENT IN QUEUE:
            Vertex current = queue.remove( );

            // CHECKING ALL NEIGHBOURS FOR SHORT PATHS:
            for (Edge e : current.adj)
            {

                // GETTING DESTINATION NODE:
                Vertex other = e.destination;

                // IF INFINITY, PROCESS. ELSE, IGNORE.
                if( other.distance == INFINITY)
                {
                    // INCREASING DISTANCE AND QUEUING:
                    other.distance = current.distance +1;
                    other.previous = current;
                    queue.add( other );
                }
            }
        }
    }

    /** DIJKSTRA:
     * Dijkstra er en kjent CS dude, som fant opp denne algoritmen.
     * Bruker størrelsesbasert prioritetskø til å alltid prosessere
     * node med lavest "sti verdi". Man eliminerer ved  å se at det
     * nåværende node er samme som en vi har sett tidligere og har
     * prosessert/eliminert/"scratched". Dermed vet vi at den allerede,
     * utenom å kjøre ikke kan finne bedre vei til enden.
     *
     * Bruker nivåorden traversering for å fylle prioritetskøen.
     */
    public void dijkstra( String startName ) throws Exception
    {
        PriorityQueue<Path> pq = new PriorityQueue<>();
        Vertex start = dictionary.get(startName);

        if (start == null) {
            throw new Exception();
        }

        clear();
        pq.add( new Path(start, 0));
        start.distance = 0;

        int nodesSeen = 0;
        while (!pq.isEmpty() && nodesSeen < dictionary.size( ) )
        {
            Path vrec = pq.remove();
            Vertex v = vrec.dest;

            // HVIS NODEN ER SETT:
            if ( v.scratch ) continue;

            v.scratch = true;
            nodesSeen++;

            for( Edge e : v.adj )
            {
                Vertex w = e.destination;
                double cvw = e.cost; //KOSTNAD MELLOM V OG W

                if ( cvw < 0 ) throw new Exception( );

                if ( w.distance > v.distance + cvw ) {
                    w.distance = v.distance + cvw;
                    w.previous = v;
                    pq.add( new Path( w, w.distance ) );
                }
            }
        }
    }

    // UBRUKT OG IKKE PENSUM:
    // public void negative()
    // {}

    // UBRUKT OG IKKE PENSUM:
    // public void acyclic()
    // {}

    /**
     * Gets a vertex if it exsists in the dictionary. Else
     * it creates the vertex.
     *
     * @param name of Vertex, new or old.
     * @return the vertex object.
     */
    private Vertex getVertex( String name )
    {
        Vertex vertex = dictionary.get( name );

        if ( vertex == null )
        {
            vertex = new Vertex( name );
            dictionary.put(name, vertex);
        }

        return vertex;
    }

}

