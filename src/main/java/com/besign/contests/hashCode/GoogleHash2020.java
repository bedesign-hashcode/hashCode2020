package com.besign.contests.hashCode;


import javax.swing.plaf.IconUIResource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;
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

    private String fileName = "";

    public void solve() throws IOException {
        exercisePath = "hashCode2020";
        filesInProcess = Arrays.asList("a_example.txt", "b_read_on.txt", "c_incunabula.txt", "e_so_many_books.txt", "f_libraries_of_the_world.txt", "d_tough_choices.txt");

//        filesInProcess = Arrays.asList("d_tough_choices.txt");

        for (String fileName : filesInProcess) {
            this. fileName = fileName;
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
        int idx = 0;
        int idxCounter = 0;
        int fullLenght = request.libArray.length;
        while (request.libArray.length > 0 && request.days > 0) {
            request = evaluateScoreOnLibraries(request);
            Request finalRequest1 = request;
            Library first = Stream.of(request.libArray).parallel().filter(current -> finalRequest1.days - current.signup > 0).findFirst().orElse(null);
            if (first == null) {
                break;
            };
            Library finalFirst = first;
            if (first == null) {
                break;
            }
            first = Stream.of(request.libArray).parallel()
                    .filter(current -> finalRequest1.days - current.signup > 0)
                    .filter(current -> {
                if (finalFirst.score < current.score) {
                    return true;
                } else if (finalFirst.score.equals(current.score) && finalFirst.signup > current.signup) {
                    return true;
                } else if (finalFirst.score.equals(current.score) && finalFirst.signup == current.signup && values(finalRequest1, finalFirst) < values(finalRequest1, current)) {
                    return true;
                }
                return false;
            }).findFirst().orElse(first);

            if (idxCounter % 1000 == 0)
                println("Processed " + idxCounter +" of " + fullLenght + " remaining " +(request.libArray.length -1)  + " days " +  request.days);

            first.booksArray = orderByValue(request, first.booksArray);

            first.booksArray = Arrays.copyOfRange(first.booksArray, 0, (int) Math.min((request.days - first.signup), first.bookCount(request)));

            Request finalRequest = request;
            IntStream.of(first.booksArray).forEach(bIdx -> finalRequest.bookScore[bIdx] = 0);
            request.days -= first.signup;
            request.libOrderedArray[idx ++] = first;
            List<Library> libraries = new ArrayList<>();
            libraries.addAll(Arrays.asList(request.libArray));
            libraries.remove(first);
            request.libArray = libraries.toArray(new Library[0]);
        }

        return evaluateResponse(request);
    }

    private List<String> evaluateResponse(Request request) {
        ArrayList<String> strings = new ArrayList<>();
        int libraryCount =  (int) Stream.of(request.libOrderedArray).filter(Objects::nonNull).count();
        strings.add("" + libraryCount);
        for (int i = 0; i < libraryCount; i++) {
            Library library = request.libOrderedArray[i];
            if (library == null) {
                break;
            }
            strings.add("" + library.idx + " " + library.booksArray.length);
            strings.add(withSpaces(library.booksArray));
        }
        return strings;
    }

    private int[] orderByValue(Request request, int[] booksArray) {
        Integer[] objects = IntStream.of(booksArray).boxed().toArray(Integer[]::new);
        Arrays.sort(objects, (i, j) -> request.bookScore[j] - request.bookScore[i]);
        return Stream.of(objects).mapToInt(i -> i).toArray();
    }

    private String withSpaces(int[] booksArray) {
        String s = "";
        for (int i : booksArray) {
            s += i + " ";
        }
        return s;
    }

    private Request evaluateScoreOnLibraries(Request request) {
        if ("d_tough_choices.txt".equals(fileName)) {
            Stream.of(request.libArray).parallel().forEach(l -> l
                    .score = Math.min((request.days - l.signup ) * l.parallel, l.bookCount(request)) *
                    values(request, l));
        } else {
            Stream.of(request.libArray).parallel().forEach(l ->
                    l.score = Math.min((request.days - l.signup) * l.parallel, l.bookCount(request) ) * values(request, l)
                            / (l.bookCount(request) / l.parallel));
        }

        return request;
    }

    private double values(Request request, Library l) {
        return IntStream.of(l.booksArray).mapToDouble(i -> request.bookScore[i]).sum();
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
            int signup = 0 ;
            signup = Integer.parseInt(libLine1[1]);

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
        public int[] bookScore;
        public Library[] libArray;
        public Library[] libOrderedArray;

        public Request(int bookCount, int libCount, int days) {
            this.bookCount = bookCount;
            this.libCount = libCount;
            this.days = days;
            this.libArray = new Library[libCount];
            this.libOrderedArray = new Library[libCount];
        }
    }


    static class Library {
        public int idx;
        public int signup;
        public int parallel;
        public int[] booksArray;
        public Double score;

        public double bookCount(Request request) {
            return IntStream.of(booksArray).parallel().filter(i -> request.bookScore[i] != 0).count();
        }
    }
}

