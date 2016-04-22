import com.sun.org.apache.xpath.internal.SourceTree;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by Magnu on 21.04.2016.
 */
 class Graph {

    // KONSTANTER:
    final static double INFINITY = Double.MAX_VALUE;

    // OTHER:
    Map<String, Vertex> map = new HashMap<>();
    int nodes;

    // KONSTRUKTØR:
    Graph(String filename)
    {
        Scanner reader;
        try {
            File file = new File(filename);
            FileReader fileReader = new FileReader(file);
            reader = new Scanner(fileReader);
        }
        catch (IOException error) {
            System.out.println("Something went wrong with file: "+error);
            return;
        }

        // GETTING NUMBER OF NODES FROM FILE.
        String line = "";
        if (reader.hasNextLine( )) line = reader.nextLine();

        try {
            this.nodes = Integer.parseInt(line);
        }
        catch (ArithmeticException error) {
            System.out.println("File format error, first line is not number of nodes.");
            return;
        }

        // SETTING UP THE MAP:
        while (reader.hasNextLine())
        {
            // GETTING NODES FROM FILE:
            String[] path = reader.nextLine().split(" ");

            // ADAPTING COST VARIABLE:
            double cost = 1;
            if (path.length == 3) {
                try {
                    cost = Double.parseDouble(path[2]);
                }
                catch (ArithmeticException error) {
                    System.out.println(
                        "File format error, third character is not number in: " +
                        path[0] + "->" + path[1]
                    );
                    return;
                }
            }

            Vertex from = map.get(path[0]);
            Vertex to   = map.get(path[1]);

            // IF "FROM" NODE IS NOT SEEN BEFORE:
            if (from == null) {
                from = new Vertex(path[0]);
                map.put(path[0], from);
            }
            // IF "TO" NODE IS NOT SEEN BEFORE:
            if (to == null) {
                to = new Vertex(path[1]);
                map.put(path[1], to);
            }

            from.adj.add(new Edge(to, cost));
        }
    }


    private Vertex safeGet( String name )
    {
        Vertex vertex = map.get(name);
        if ( vertex == null )
        {
            throw new NoSuchElementException("Cannot find element: " + name);
        }
        return vertex;
    }

    public void unweighted( String from, String to )
    {
        // TESTING EXTREME-POINTS FOR THE ANALYSIS:
        Vertex start = safeGet(from);
        start.distance = 0;
        Vertex end   = safeGet(to);

        LinkedList<Vertex> queue = new LinkedList<Vertex>();
        queue.add(start);

        // SEARCHING FOR FASTEST ROUTE
        while( ! queue.isEmpty() )
        {
            Vertex v = queue.remove(0);
            if (v != end)
            {
                for (Edge edge : v.adj)
                {
                    if (edge.destination.distance <= v.distance)
                    {
                        // NODEN HAR BLITT SETT FØR. INGEN BEDRING.
                        continue;
                    }
                    edge.destination.previous = v;
                    edge.destination.distance = v.distance + 1;
                    queue.add(edge.destination);
                }
            }
        }

        if (end.previous == null) System.out.println(" END NOT FOUND :( ");
        else System.out.println(" GREAT SUCCESS! FINISHED IN " + end.distance + "  STEPS.");

    }

    public void weighted( String from, String to )
    {
        Vertex start = safeGet(from);
        Vertex end   = safeGet(to);

        PriorityQueue<Vertex> queue = new PriorityQueue<>();
        queue.add(start);
        start.distance = 0;

        while (! queue.isEmpty() ) {
            Vertex v = queue.remove();

            if (v.scratch) continue;
            v.scratch = true;

            for (Edge e : v.adj)
            {
                if (e.destination.scratch) continue;

                if (v.distance + e.cost > e.destination.distance )
                {
                    // NODEN ER SETT OG HAR INGEN BEDRING.
                    continue;
                }
                e.destination.distance = e.cost + v.distance;
                e.destination.previous = v;
                queue.add(e.destination);
            }
        }

        if (end.previous != null) System.out.println( "YEEEY, GREAT SUCCESS! COST: " + end.distance);
        else System.out.println( "ÅNEEEJ, VI TAPTA!");
    }

    public String printPath(String last) {
        StringBuilder output = new StringBuilder("Path: ");

        Vertex end = safeGet(last);
        LinkedList<Vertex> stack = new LinkedList<>();
        Vertex node = end;
        stack.push(end);
        while (node.previous != null)
        {
            stack.push(node.previous);
            node = node.previous;
        }

        if (!stack.isEmpty()) output.append(stack.pop().name.toString());
        while (!stack.isEmpty())
        {
            output.append(" -> ");
            Vertex v = stack.pop();
            output.append(v.name.toString());

        }
        return output.toString();
    }
}
