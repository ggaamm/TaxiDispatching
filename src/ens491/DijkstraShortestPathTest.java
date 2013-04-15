package ens491;

import java.util.Arrays;
import java.util.List;

import org.jgrapht.*;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;


/**
 * .
 *
 * @author John V. Sichi
 */
public class DijkstraShortestPathTest
    extends ShortestPathTestCase
{
    //~ Methods ----------------------------------------------------------------

    /**
     * .
     */
    public void testConstructor()
    {
        DijkstraShortestPath<String, DefaultWeightedEdge> path;
        Graph<String, DefaultWeightedEdge> g = create();

        path =
            new DijkstraShortestPath<String, DefaultWeightedEdge>(
                g,
                V3,
                V4,
                Double.POSITIVE_INFINITY);
        assertEquals(
            Arrays.asList(
                new DefaultEdge[] {
                    e13,
                    e12,
                    e24
                }),
            path.getPathEdgeList());
        assertEquals(10.0, path.getPathLength(), 0);

        path =
            new DijkstraShortestPath<String, DefaultWeightedEdge>(
                g,
                V3,
                V4,
                7);
        assertNull(path.getPathEdgeList());
        assertEquals(Double.POSITIVE_INFINITY, path.getPathLength(), 0);
        
        List<DefaultWeightedEdge> list = findPathBetween(g,"v1", "v4");
        for(int i=0;i<list.size();i++)
        	System.out.println(list.get(i));
        
    }

    protected List findPathBetween(
        Graph<String, DefaultWeightedEdge> g,
        String src,
        String dest)
    {
        return DijkstraShortestPath.findPathBetween(g, src, dest);
    }
}

// End DijkstraShortestPathTest.java
