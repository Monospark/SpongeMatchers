package org.monospark.spongematchers.parser.element;

import java.util.regex.Matcher;

public final class ConnectedElement extends StringElement {

    private StringElement firstElement;

    private StringElement secondElement;

    private Operator operator;

    ConnectedElement(Matcher matcher, StringElement firstElement, StringElement secondElement, Operator operator) {
        super(matcher);
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

    public enum Operator {

        AND, OR;
    }
}
