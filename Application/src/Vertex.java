import java.util.LinkedList;
import java.util.List;

/**
 * Created by Magnus Poppe Wang on 18.04.2016.
 * @author Magnus Poppe Wang
 *
 * A vertex is a point in a graph. It has a list
 * of its adjacent neighbours, which  consists of
 * edges.
 *
 * @see Edge
 */
class Vertex implements Comparable<Vertex>
{
    protected String      name;
    protected List<Edge>  adj;
    protected double      distance;
    protected Vertex      previous;
    protected boolean     scratch;

    Vertex( String name )
    {
        this.name = name;
        this.adj  = new LinkedList<>( );
        reset( );
    }

    void reset( )
    {
        distance = Graph.INFINITY;
        previous = null;
        // pos = null;
        scratch = false;
    }

    @Override
    public int compareTo(Vertex o) {
        if (distance < o.distance) return -1;
        else if (distance > o.distance) return 1;
        else return 0;
    }
}
