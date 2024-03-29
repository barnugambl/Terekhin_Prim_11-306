package Prima_Remastered;
import java.io.*;
import java.util.*;

public class Test {
    public static void main(String[] args) {
        Prims prims = new Prims();
        List<List<List<NodeCost>>> allGraphs = new ArrayList<>();
        // считывание с файла
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("Data.bin"))) {
            while (true) {
                List<List<NodeCost>> graph = (List<List<NodeCost>>) inputStream.readObject();
                allGraphs.add(graph);
            }
        } catch (EOFException e) {
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        // обработка данных
        for (List<List<NodeCost>> graph : allGraphs) {
            long startTime = System.currentTimeMillis();
            int mstCost = prims.Find_MST(0, graph);
            long endTime = System.currentTimeMillis();
            long executionTime = endTime - startTime;
            System.out.println("Время: " + executionTime + " миллисекундах");
            System.out.println("Количество вершин графов: " + graph.size());
            System.out.println("Количество итерраций = "+ prims.iteration_cnt);
            System.out.println("\n");

        }

    }
}
class NodeCost implements Serializable {
    int node;
    int cost;
    NodeCost(int node, int cost) {
        this.node = node;
        this.cost = cost;
    }
    public int getNode(){
        return node;
    }
    public int getCost(){
        return cost;
    }


}

class Prims {

    int iteration_cnt = 0;

    int Find_MST(int source_node, List<List<NodeCost>> graph) {
        Comparator<NodeCost> NodeCostComparator = (obj1, obj2) -> {
            return obj1.cost - obj2.cost;
        };

        PriorityQueue<NodeCost> pq = new PriorityQueue<>(NodeCostComparator);

        pq.add(new NodeCost(source_node, 0));

        boolean added[] = new boolean[graph.size()];
        Arrays.fill(added, false);

        int mst_cost = 0;

        while (!pq.isEmpty()) {

            NodeCost item = pq.peek();
            pq.remove();

            int node = item.node;
            int cost = item.cost;

            if (!added[node]) {
                mst_cost += cost;
                added[node] = true;

                for (NodeCost pair_node_cost : graph.get(node)) {
                    int adj_node = pair_node_cost.node;
                    if (added[adj_node] == false) {
                        pq.add(pair_node_cost);
                        iteration_cnt++;


                    }
                }
            }

        }
        return mst_cost;
    }

}




