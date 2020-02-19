package com.besign.contests.hashCode.dynamicImplementations.knapsack;

import com.besign.contests.hashCode.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Knapsack {

    // items of our problem
    private Item[] items;
    // capacity of the bag
    private int capacity;

    public Knapsack(Item[] items, int capacity) {
        this.items = items;
        this.capacity = capacity;
    }

    public void display() {
        if (items != null  &&  items.length > 0) {
            System.out.println("Knapsack problem");
            System.out.println("Capacity: " + capacity);
            System.out.println("Items: " + items.length);
        }
    }

    // we write the solve algorithm
    public Solution solve() {
        int NB_ITEMS = items.length;
        // we use a matrix to store the max value at each n-th item
        int[][] matrix = new int[NB_ITEMS + 1][capacity + 1];

        // first line is initialized to 0
        for (int i = 0; i <= capacity; i++)
            matrix[0][i] = 0;

        // we iterate on items
        for (int i = 1; i <= NB_ITEMS; i++) {
            // we iterate on each capacity
            for (int j = 0; j <= capacity; j++) {
                if (items[i - 1].weight > j)
                    matrix[i][j] = matrix[i-1][j];
                else
                    // we maximize value at this rank in the matrix
                    matrix[i][j] = Math.max(matrix[i-1][j], matrix[i-1][j - items[i-1].weight]
                            + items[i-1].value);
            }
        }

        int res = matrix[NB_ITEMS][capacity];
        int w = capacity;
        List<Item> itemsSolution = new ArrayList<>();

        for (int i = NB_ITEMS; i > 0  &&  res > 0; i--) {
            if (res != matrix[i-1][w]) {
                itemsSolution.add(items[i-1]);
                // we remove items value and weight
                res -= items[i-1].value;
                w -= items[i-1].weight;
            }
        }

        return new Solution(itemsSolution.stream().mapToInt(i -> i.name).toArray(), matrix[NB_ITEMS][capacity]);
    }

    public Solution solveWithoutHeapProblem() {
        int[] oddItems = new int[2000];
        int oddIndex = 0;
        int[] evenItems = new int[2000];
        int evenIndex = 0;
        int n = items.length;
        int W = capacity;
        // matrix to store final result
        int mat[][] = new int[2][W + 1];

        // iterate through all items
        int i = 0;
        while (i < n) // one by one traverse each element
        {
            int j = 0; // traverse all weights j <= W

            // if i is odd that mean till now we have odd
            // number of elements so we store result in 1th
            // indexed row
            if (i % 2 != 0)
            {
                while (++j <= W) // check for each value
                {
                    if (items[i].weight <= j) // include element
                    {
                        oddItems[oddIndex] = items[i].name;
                        mat[1][j] = Math.max(items[i].value + mat[0][j - items[i].weight], mat[0][j]);
                    } else // exclude element
                    {
                        mat[1][j] = mat[0][j];
                    }
                    if (j % 10000 == 0) {
                        Utils.println("Evaluated the first " + j + " weight for item " + i);
                    }
                }

            }

            // if i is even that means till now
            // we have even number of elements
            // so we store result in 0th indexed row
            else
            {
                while (++j <= W)
                {
                    if (items[i].weight <= j)
                    {
                        evenItems[evenIndex] = items[i].name;
                        mat[0][j] = Math.max(items[i].value + mat[1][j - items[i].weight],
                                mat[1][j]);
                    } else
                    {
                        mat[0][j] = mat[1][j];
                    }
                    if (j % 10000 == 0) {
                        Utils.println("Evaluated the first " + j + " weight for item " + i);
                    }
                }
            }
            i++;
            if (i % 100 == 0) {
                Utils.println("Evaluated the first " + i + " items ");
            }
        }

        // Return mat[0][W] if n is odd, else mat[1][W]
        int v = (n % 2 != 0) ? mat[0][W] : mat[1][W];
        int[] result = (n % 2 != 0) ? oddItems : evenItems;
        return new Solution(result, v);
    }

    public static Solution resolveWithKnapsack (int maxValue, List<Item> items) {
        Knapsack knapsack = new Knapsack(items.toArray(new Item[0]), maxValue);
        knapsack.display();
//        return maxValue > 1000000 ? knapsack.solveWithoutHeapProblem() :
        return knapsack.solve();
    }
}