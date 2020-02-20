package com.besign.contests.hashCode;


import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
        for (String fileName: filesInProcess) {
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
        return null;
    }

    private Request evaluateScoreOnLibraries(Request request) {
        return request;
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

        for (int i = 1; i<content.size(); i++) {
            content.get(i);
        }

        return request;
    }

    static class Request {
        int bookCount;
        int libCount;
        int days;
        int[] bookIdToValues;
        Library[] libArray;

        public Request(int bookCount, int libCount, int days) {
            this.bookCount = bookCount;
            this.libCount = libCount;
            this.days = days;
            this.bookIdToValues = new int[bookCount];
            this.libArray = new Library[libCount];
        }
    }


    static class Library {
        int position;
        int bookCount;
        int signup;
        int parallel;
        int[] booksArray;
        int score;
    }
}

