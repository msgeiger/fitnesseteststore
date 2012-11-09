/*
 * Fetch Fitnesse tests
 * Store tests in sqlite db file
 */
package com.mgeiger.fitnesseteststore;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Administrator
 */
public class FetchFitnesseTests {

    private static Pattern pattern;
    private static Matcher matcher;
    private static final String TEST_PATTERN = ".*Test*.*";
    private static List listTest = new ArrayList();
    private static String dir = null;
    private static String prePend = null;
    
    public static void setPrePend(String stringPrePend) {
        prePend = stringPrePend;
    }
    
    public static String getPrePend() {
        return prePend != null ? prePend : "C:<REMOVE CHARACTERS IN TEST PATH>";
    }
    
    public static void getFitnesseTests() {
        dir = ((dir != null) ? dir : "c:\\<FITNESSE DIR>\\");
        File currentDir = new File(dir);
        generateList(currentDir);
    }

    public static List generateList(File workDir) {
        pattern = Pattern.compile(TEST_PATTERN);
        File[] files = workDir.listFiles();
        int i = 1;
        String backslash = "\\\\";
        String testPath;
        //setPrePend("c:.github.Xebium-Sentry.FitNesseRoot.SentryDataSystems.");
        
        for (File file : files) {
            if (file.isDirectory()) {
                matcher = pattern.matcher(file.toString());
                if (matcher.matches()) {
                    testPath = file.toString().replaceAll(backslash, ".").replaceAll(getPrePend(), "");
                    //System.out.println(testPath);
                    listTest.add(testPath);
                }
                generateList(file);
            } else {
                //System.out.println(file.toString());
            }
        }

        return listTest;
    }
}
