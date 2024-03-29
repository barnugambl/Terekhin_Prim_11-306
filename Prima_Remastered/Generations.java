package Prima_Remastered;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Generations {

    public static void main(String[] args) {
        List<List<List<NodeCost>>> allGraphs = new ArrayList<>();
        Random random = new Random();

        // создание рандомных вершин и ребер и количество графов
        int randomCountGraphs = ThreadLocalRandom.current().nextInt(50, 101);
        System.out.println("Количество созданных графов = " + randomCountGraphs);
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("Data.bin")))) {
            for (int k = 0; k < randomCountGraphs; k++) {
                int randomNumberNodes = ThreadLocalRandom.current().nextInt(100, 10001);
                List<List<NodeCost>> graph = new ArrayList<>(randomNumberNodes);

                for (int i = 0; i < randomNumberNodes; i++) {
                    graph.add(new ArrayList<>());
                }

                for (int i = 0; i < randomNumberNodes; i++) {
                    for (int j = i + 1; j < randomNumberNodes; j++) {
                        int weight = random.nextInt(100) + 1;
                        graph.get(i).add(new NodeCost(j, weight));
                        graph.get(j).add(new NodeCost(i, weight));
                    }
                }

                allGraphs.add(graph);
                writeGraphsToFile1(outputStream, allGraphs);

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void writeGraphsToFile1(ObjectOutputStream outputStream, List<List<List<NodeCost>>> allGraphs) { //Запись в файлик
        try {
            for (List<List<NodeCost>> graph : allGraphs) {
                for (List<NodeCost> nodeList : graph) {
                    for (NodeCost node : nodeList) {
                        outputStream.writeInt(node.getNode());
                        outputStream.writeInt(node.getCost());
                    }
                }
            }
            outputStream.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            allGraphs.clear();
        }
    }
}
