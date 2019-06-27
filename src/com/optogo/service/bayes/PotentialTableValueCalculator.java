package com.optogo.service.bayes;

import java.util.*;

public class PotentialTableValueCalculator {
    public float[] calculate(final Map<String, Float> symptoms) {
        List<ProbabilityPair> probabilities = new ArrayList<>(symptoms.size());
        int size = (int) Math.pow(2, symptoms.size());
        for (int i = 0; i < size; i++) {
            probabilities.add(new ProbabilityPair());
        }

        List<String> keys = new ArrayList<>(symptoms.keySet());
        for (int i = 1; i < size; i++) {
            if (isPowerOfTwo(i)) {
                int symptom = log2(i);
                probabilities.get(i).setYes(symptoms.get(keys.get(symptom)));
            } else {
                double no = 1;
                for (int onesPos : getOnesPositions(i)) {
                    no *= probabilities.get((int) Math.pow(2, onesPos)).getNo();
                }
                probabilities.get(i).setNo(no);
            }
        }

//        print(symptoms, probabilities);

        float[] values = new float[size * 2];
        int i = 0;
        for (int pi = probabilities.size() - 1; pi >= 0; pi--) {
            values[i++] = (float) probabilities.get(pi).getYes();
            values[i++] = (float) probabilities.get(pi).getNo();
        }

        return values;
    }

    private Set<Integer> getOnesPositions(int n) {
        Set<Integer> result = new HashSet<>();

        String bin = Integer.toBinaryString(n);
        int len = bin.length();

        int i;
        while ((i = bin.indexOf("1")) != -1) {
            bin = bin.replaceFirst("1", "0");
            result.add(len - i - 1);
        }

        return result;
    }

    private int log2(int i) {
        return (int) (Math.log(i) / Math.log(2));
    }

    public void print(Map<String, Float> symptoms, List<ProbabilityPair> probabilityPairs) {
        List<String> keyList = new ArrayList<>(symptoms.keySet());

        List<String> lines = new ArrayList<>();

        for (String symptom : keyList) {
            StringBuilder stringBuilder = new StringBuilder();

            int numOfDigits = (int) Math.pow(2, keyList.indexOf(symptom)) - 1;
            int count = 0;
            boolean val = false;

            stringBuilder.append(String.format("%25s\t|  ", symptom));

            for (int i = 0; i < Math.pow(2, symptoms.size()); i++) {
                stringBuilder.append(val ? "1" : "0").append("  |  ");
                if (count++ == numOfDigits) {
                    count = 0;
                    val = !val;
                }
            }
            lines.add(stringBuilder.toString());
        }

        for (int i = lines.size() - 1; i >= 0; i--) {
            System.out.println(lines.get(i));
        }


        System.out.printf("%25s\t|", "Yes");
        for (ProbabilityPair probabilityPair : probabilityPairs) {
            System.out.printf("%1.3f|", probabilityPair.getYes());
        }
        System.out.println();

        System.out.printf("%25s\t|", "No");
        for (ProbabilityPair probabilityPair : probabilityPairs) {
            System.out.printf("%1.3f|", probabilityPair.getNo());
        }

    }

    private boolean isPowerOfTwo(int n) {
        return (n & (n - 1)) == 0;
    }

}
