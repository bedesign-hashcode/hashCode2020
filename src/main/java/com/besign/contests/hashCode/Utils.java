package com.besign.contests.hashCode;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

public class Utils {
    public static String exercisePath;
    public static List<String> filesInProcess;
    public static String fileInProcess;

    public static void println(String s) {
        System.out.println(s);
    }

    public static List<String> getContentOfFile(String fileName) throws IOException {
        String classPath = Utils.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        String filePath = classPath.substring(0, classPath.indexOf("/target")) + "/" + exercisePath + "/" + fileName;
        return FileUtils.readLines(new File(filePath), StandardCharsets.UTF_8).stream().filter(StringUtils::isNotBlank).collect(Collectors.toList());
    }

    public static void writeSolutionFor(String fileName, List<String> content) throws IOException {
        String classPath = Utils.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        String filePath = classPath.substring(0, classPath.indexOf("/target")) + "/" + exercisePath + "/solution/" + fileName + ".out";
        FileUtils.writeLines(new File(filePath), content);
    }

    public static int toInt(String s) {
        return Integer.parseInt(s);
    }
}
