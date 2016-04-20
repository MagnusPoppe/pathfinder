/**
 * Created by Magnu on 19.04.2016.
 */

class Path implements Comparable<Path> {

    Vertex dest;
    double cost;

    Path( Vertex d, double c ) {
        dest = d;
        cost = c;
    }

    @Override
    public int compareTo( Path rhs ) {
        double othercost = rhs.cost;
        return cost < othercost ? -1 : cost > othercost ? 1 : 0;
    }
}