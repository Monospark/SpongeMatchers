package org.monospark.spongematchers.parser.element;

import java.util.Set;
import java.util.regex.Matcher;

public final class ConnectedElement extends StringElement {

    private Set<StringElement> elements;;

    private Operator operator;

    ConnectedElement(Matcher matcher, Set<StringElement> elements, Operator operator) {
        super(matcher);
        this.elements = elements;
        this.operator = operator;
    }

    public Set<StringElement> getElements() {
        return elements;
    }

    public Operator getOperator() {
        return operator;
    }

    public enum Operator {

        AND, OR;
    }
}
