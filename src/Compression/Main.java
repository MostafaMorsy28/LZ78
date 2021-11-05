package Compression;

public class Main {

    public static void main(String[] args) {
        LZ78 lz = new LZ78();
        String input = "input.txt";
        String tagsOutput = "TagsOutput.txt";
        String bytesOutput = "BytesOutput.txt";
        String output = "output.txt";

        lz.compress(input, tagsOutput, bytesOutput);

        lz.decompress(bytesOutput, output);
    }
}
