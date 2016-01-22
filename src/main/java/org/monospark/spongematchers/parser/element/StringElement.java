package org.monospark.spongematchers.parser.element;


public abstract class StringElement {
    
    private int start;
    
    private int end;

    StringElement(int start, int end) {
        this.start = start;
        this.end = end;
    } 

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }
}
