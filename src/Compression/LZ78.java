package Compression;

import jdk.jfr.Unsigned;

import java.io.*;
import java.util.ArrayList;

public class LZ78 {

    public void compress(String input, String tagsOutput, String bytesOutput) {
        try {
            File file = new File(input);
            byte[] data = new byte[(int) file.length()];
            FileInputStream fileInputStream = new FileInputStream(file);
            fileInputStream.read(data);
            fileInputStream.close();

            ArrayList<String> dictionary = new ArrayList<String>();

            StringBuilder inputString = new StringBuilder();

            for (int i = 0; i < data.length; ++i) {
                inputString.append((char) data[i]);
            }
            dictionary.add("");

            String T = "";
            int pointer = 0;

            FileWriter fileWriter = new FileWriter(tagsOutput); // write in tagsOutput file
            fileWriter.write("<Index In Dictionary, Next Character>\n");

            FileOutputStream fileOutputStream = new FileOutputStream(bytesOutput);

            for (int i = 0; i < inputString.length(); ++i) {

                T += inputString.charAt(i);
                boolean check = false;
                for (int j = 0; j < dictionary.size(); ++j) {
                    if (T.equals(dictionary.get(j))) {
                        pointer = j;
                        check = true;
                        break;
                    }
                }
                if (!check) {
                    fileWriter.write("<" + pointer + ", " + inputString.charAt(i) + ">\n");
                    fileOutputStream.write((byte) pointer);
                    fileOutputStream.write((byte) inputString.charAt(i));
                    dictionary.add(T);
                    if (dictionary.size() == (1 << 8)) {
                        dictionary.clear();
                        pointer = 0;
                        T = "";
                    }
                    T = "";
                    pointer = 0;
                } else if (i + 1 == inputString.length()) {
                    fileWriter.write("<" + pointer + ", " + "null" + ">\n");
                    fileOutputStream.write((byte) pointer);
                    fileOutputStream.write((byte) 0);
                }
            }
            fileWriter.close();
            fileOutputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void decompress(String bytesInput, String output) {
        try {
            FileInputStream fileInputStream = new FileInputStream(bytesInput);

            ArrayList<String> dictionary = new ArrayList<String>();
            dictionary.add("");

            byte temp;

            String outputString = "";

            while (true) {
                temp = (byte) fileInputStream.read();
                if (temp == -1) {
                    break;
                }
                int pointer = (int) temp;
                char next = (char) fileInputStream.read();
                String T = dictionary.get(pointer);
                if ((byte) next != 0) {
                    T += next;
                }
                dictionary.add(T);
                outputString += T;
                if (dictionary.size() == (1 << 8)) {
                    dictionary.clear();
                    pointer = 0;
                    T = "";
                }
            }
            FileWriter fileWriter = new FileWriter(output);

            fileWriter.write(outputString);

            fileWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
