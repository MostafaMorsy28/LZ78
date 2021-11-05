package Compression;

public class Tag {
    private int index;
    private char next;

    void setIndex(int p) {
        index = p;
    }


    void setNext(char c) {
        next = c;
    }

    int getIndex() {
        return index;
    }


    char getNext() {
        return next;
    }
}
