package com.besign.contests.hashCode;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.Stream;

import static com.besign.contests.hashCode.Utils.exercisePath;
import static com.besign.contests.hashCode.Utils.fileInProcess;
import static com.besign.contests.hashCode.Utils.filesInProcess;
import static com.besign.contests.hashCode.Utils.getContentOfFile;
import static com.besign.contests.hashCode.Utils.println;
import static com.besign.contests.hashCode.Utils.writeSolutionFor;

public class GoogleHash2020 {

    public static void main(String[] args) throws IOException {
        new GoogleHash2020().solve();
    }

    public void solve() throws IOException {
        exercisePath = "hashCode2020";
        filesInProcess = Arrays.asList("a_example.txt", "b_read_on.txt", "c_incunabula.txt", "d_tough_choices.txt", "e_so_many_books.txt", "f_libraries_of_the_world.txt");
        for (String fileName : filesInProcess) {
            println(fileName);
            fileInProcess = fileName;

            List<String> contentOfFile = getContentOfFile(fileInProcess);
            List<String> solution = solution(contentOfFile);
            println("Points for " + fileName + ": " + evaluatePointsForSolution(solution));
            writeSolutionFor(fileInProcess, solution);
            println("\n");
        }
    }

    private String evaluatePointsForSolution(List<String> solution) {
        return "10000";
    }

    private List<String> solution(List<String> content) {
        Request request = evaluateRequest(content);
        request = evaluateScoreOnLibraries(request);
        return evaluateResponse(request);
    }

    private List<String> evaluateResponse(Request request) {
        Arrays.sort(request.libArray, Comparator.comparingDouble(o -> o.score));
        ArrayList<String> strings = new ArrayList<>();
        int libraryCount = request.libArray.length;
        strings.add("" + libraryCount);
        for (int i = 0; i < libraryCount; i++) {
            Library library = request.libArray[i];
            strings.add("" + library.idx + " " + library.bookCount());
            strings.add(withSpaces(orderByValue(request, library.booksArray)));
        }
        return strings;
    }

    private int[] orderByValue(Request request, int[] booksArray) {
        Integer[] booksIndex = IntStream.range(0, booksArray.length).boxed().toArray(Integer[]::new);
        Arrays.sort(booksIndex, (i, j) -> request.bookIdToValues[booksIndex[i]] - request.bookIdToValues[booksIndex[j]]);
        int[] ordered = new int[booksArray.length];
        for (int i = 0; i > booksArray.length; i++) {
            ordered[i] = booksArray[booksIndex[i]];
        }
        return ordered;
    }

    private String withSpaces(int[] booksArray) {
        String s = "";
        for (int i : booksArray) {
            s += i + " ";
        }
        return s;
    }

    private Request evaluateScoreOnLibraries(Request request) {
        for (Library l : request.libArray) {
            l.score = Math.min(request.days - l.signup, l.bookCount() / l.parallel) * values(request, l) / (l.bookCount() / l.parallel);
        }
        return request;
    }

    private double values(Request request, Library l) {
        return IntStream.of(l.booksArray).mapToDouble(i -> request.bookIdToValues[i]).sum();
    }


    private Request evaluateScoreOnLibrariesWithDifferentBookValue(Request request) {
        return request;
    }

    private Request evaluateRequest(List<String> content) {
        String[] firstLine = content.get(0).split(" ");
        int books = Integer.parseInt(firstLine[0]);
        int libraries = Integer.parseInt(firstLine[1]);
        int days = Integer.parseInt(firstLine[2]);
        Request request = new Request(books, libraries, days);
        request.bookScore = Stream.of(content.get(1).split(" ")).mapToInt(Integer::parseInt).toArray();

        int libIdx = 0;
        for (int i = 2; i < content.size(); i += 2) {
            String[] libLine1 = content.get(i).split(" ");
            int signup = Integer.parseInt(libLine1[1]);
            int parallel = Integer.parseInt(libLine1[2]);
            int[] bookIdx = Stream.of(content.get(i + 1).split(" ")).mapToInt(Integer::parseInt).toArray();

            Library l = new Library();
            l.idx = libIdx;
            l.parallel = parallel;
            l.signup = signup;
            l.booksArray = bookIdx;
            request.libArray[libIdx] = l;

            libIdx++;
        }

        return request;
    }

    static class Request {
        public int bookCount;
        public int libCount;
        public int days;
        public int[] bookIdToValues;
        public int[] bookScore;
        public Library[] libArray;

        public Request(int bookCount, int libCount, int days) {
            this.bookCount = bookCount;
            this.libCount = libCount;
            this.days = days;
            this.bookIdToValues = new int[bookCount];
            this.libArray = new Library[libCount];
        }
    }


    static class Library {
        public int idx;
        public int signup;
        public int parallel;
        public int[] booksArray;
        public double score;

        public double bookCount() {
            return booksArray.length;
        }
    }
}

