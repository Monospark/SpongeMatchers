package org.monospark.spongematchers.parser.element;

public final class ConnectedElement extends StringElement {

    private StringElement firstElement;
    
    private StringElement secondElement;
    
    private Operator operator;
    
    ConnectedElement(int start, int end, StringElement firstElement, StringElement secondElement, Operator operator) {
        super(start, end);
        this.firstElement = firstElement;
        this.secondElement = secondElement;
        this.operator = operator;
    }

    public StringElement getFirstElement() {
        return firstElement;
    }

    public StringElement getSecondElement() {
        return secondElement;
    }
    
    public Operator getOperator() {
        return operator;
    }


    public static enum Operator {
        
        AND,
        OR;
    }
}
