package com.optogo.utils;

public class StringFormatter {

    /**
     * Capitalizes every word, replaces all underscores with spaces.
     * Reverse of {@link StringFormatter#uderscoredLowerCase(String)}
     * @param str
     * @return
     */
    public static String capitalizeWord(String str) {
        str = str.toLowerCase();
        str = str.replace("_", " ");

        String words[] = str.split("\\s");
        String capitalizeWord = "";
        for (String w : words) {
            String first = w.substring(0, 1);
            String afterfirst = w.substring(1);
            capitalizeWord += first.toUpperCase() + afterfirst + " ";
        }
        return capitalizeWord.trim();
    }

    /**
     * Converts string to lower case, replaces all spaces with underscore.
     * Reverse of {@link StringFormatter#capitalizeWord(String)}
     * @param str
     * @return
     */
    public static String uderscoredLowerCase(String str) {
        str = str.toLowerCase();
        str = str.replace(" ", "_");
        return str;
    }

}