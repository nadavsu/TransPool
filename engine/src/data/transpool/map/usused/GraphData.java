package data.transpool.map.usused;


import data.transpool.util.WeightedGraph;

public abstract class GraphData<V, E, D> implements WeightedGraph<V, E, D> {
/*    private List<List<Pair<V, D>>> graph;
    private List<V> allVertices;
    private List<E> allEdges;
    private List<D> allData;

    public GraphData(List<E> allEdges, List<V> allVertices, List<D> allData) {
        this.graph = new ArrayList<>();
        this.allVertices = allVertices;
        this.allEdges = allEdges;
        this.allData = allData;

        for (int i = 0; i < allEdges.size(); i++) {
            graph.add(new ArrayList<>());
        }
    }

    @Override
    public List<List<Pair<V, D>>> getAllPossibleRoutes(V source, V destination) throws Exception {
        boolean[] beingVisited = new boolean[allEdges.size()];
        ArrayList<E> currentPath = new ArrayList<>();
        ArrayList<List<E>> successfulPaths = new ArrayList<>();

        currentPath.add(source);
        depthFirstTraversal(source, destination, beingVisited, currentPath, successfulPaths);

        return successfulPaths;
    }

    private void depthFirstTraversal(E currentStop, E destination, boolean[] beingVisited, List<E> currentRoute, List<List<E>> successfulRoutes) {
        beingVisited[currentStop.getID()] = true;

        if (currentStop.equals(destination)) {
            successfulRoutes.add(new ArrayList<>(currentRoute));
            beingVisited[currentStop.getID()] = false;
            return;
        }

        for (Stop i : graph.get(currentStop.getID())) {
            if (i != null && !beingVisited[i.getID()]) {
                currentRoute.add(i);
                depthFirstTraversal(i, destination, beingVisited, currentRoute, successfulRoutes);
                currentRoute.remove(i);
            }
        }

        beingVisited[currentStop.getID()] = false;
    }

    @Override
    public List<List<E>> getGraph() {
        return null;
    }*/
}
