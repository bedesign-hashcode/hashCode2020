package com.besign.contests.hashCode;


import com.besign.contests.hashCode.dynamicImplementations.knapsack.Item;
import com.besign.contests.hashCode.dynamicImplementations.knapsack.Knapsack;
import com.besign.contests.hashCode.dynamicImplementations.knapsack.Solution;
import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static com.besign.contests.hashCode.Utils.exercisePath;
import static com.besign.contests.hashCode.Utils.fileInProcess;
import static com.besign.contests.hashCode.Utils.filesInProcess;
import static com.besign.contests.hashCode.Utils.getContentOfFile;
import static com.besign.contests.hashCode.Utils.println;
import static com.besign.contests.hashCode.Utils.toInt;
import static com.besign.contests.hashCode.Utils.writeSolutionFor;

public class GoogleHashPractice2020 {
    private Integer toStartMapSack;

    public static void main(String[] args) throws IOException {
        new GoogleHashPractice2020().solve();
    }

    public void solve() throws IOException {
        exercisePath = "practice2020";
        filesInProcess = Arrays.asList("a_example", "b_small", "c_medium", "d_quite_big", "e_also_big");
        for (String fileName: filesInProcess) {
            println(fileName);
            fileInProcess = fileName;

            List<String> contentOfFile = getContentOfFile(fileInProcess);
            List<String> solution = solution(contentOfFile);
            AtomicInteger index = new AtomicInteger();
            List<Item> items = Arrays.stream(contentOfFile.get(1).split(" ")).map(w -> new Item((index.getAndIncrement()), toInt(w), toInt(w))).collect(Collectors.toList());
            List collect = Arrays.stream(solution.get(1).split(" ")).map(idx -> items.get(Integer.parseInt(idx))).collect(Collectors.toList());
            println("Points for " + fileName + ": " + Arrays.stream(solution.get(1).split(" ")).mapToInt(idx -> items.get(Integer.parseInt(idx)).value).sum());
            writeSolutionFor(fileInProcess, solution);
            println("\n");
        }
    }

    private List<String> solution(List<String> content) {
        toStartMapSack = 1000000;
        Integer max = Integer.valueOf(content.get(0).split(" ")[0]);
        AtomicInteger index = new AtomicInteger();
        List<Item> items = Arrays.stream(content.get(1).split(" ")).map(w -> new Item((index.getAndIncrement()), toInt(w), toInt(w))).collect(Collectors.toList());
        Pair<Integer, List<Item>> integerListPair = null;
        if (max > toStartMapSack) {
            integerListPair = reduceTillKnapsackDoable(max, items);
        }
        if (integerListPair != null) {
            items.removeAll(integerListPair.getRight());
            Solution solution = Knapsack.resolveWithKnapsack(integerListPair.getLeft(), items);
            List<Integer> results = integerListPair.getRight().stream().map(i -> i.name).collect(Collectors.toList());
            results.addAll(Arrays.stream(solution.itemsIds).boxed().collect(Collectors.toList()));
            return Arrays.asList("" + results.size(), results.stream().sorted().map(d -> "" + d).collect(Collectors.joining(" ")));
        } else {
            Solution solution = Knapsack.resolveWithKnapsack(max, items);
            return Arrays.asList("" + solution.itemsIds.length, Arrays.stream(solution.itemsIds).sorted().mapToObj(d -> "" + d).collect(Collectors.joining(" ")));
        }
    }

    private Pair<Integer, List<Item>> reduceTillKnapsackDoable(Integer max, List<Item> items) {
        List<Item> firstItems = new LinkedList<>();
        Integer currentMax = max;
        for (int i = items.size() - 1; i > 0; i--) {
            currentMax = currentMax - items.get(i).value;
            firstItems.add(items.get(i));
            if (canBeKnapsacked(currentMax, i)) break;
        }
        return Pair.of(currentMax, firstItems);
    }

    private boolean canBeKnapsacked(Integer currentMax, int itemsSize) {
        return Long.valueOf(currentMax) * itemsSize * 4 < 13032385536L;
    }
}
