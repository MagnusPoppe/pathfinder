/**
 * Created by Magnus Poppe Wang on 18.04.2016.
 * @author Magnus Poppe Wang
 *
 * An edge consists of two nodes, and contains
 * the value of the path between them, represented
 * as a cost.
 *
 * @see Vertex
 */
class Edge implements Comparable<Edge>
{
    protected Vertex destination;   // Other destination in edge
    protected double cost;          // Price of the path

    Edge( Vertex v, double c)
    {
        destination = v;
        cost = c;
    }

    public int compareTo( Edge rhs ) {
        double othercost = rhs.cost;
        return cost < othercost ? -1 : cost > othercost ? 1 : 0;
    }
}
