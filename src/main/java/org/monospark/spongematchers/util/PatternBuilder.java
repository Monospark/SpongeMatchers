package org.monospark.spongematchers.util;

import java.util.regex.Pattern;

public final class PatternBuilder {

    private String pattern;
    
    public PatternBuilder() {
        pattern = "";
    }
    
    public PatternBuilder openAnonymousParantheses() {
        pattern += "(?:";
        return this;
    }
    
    public PatternBuilder openNamedParantheses(String name) {
        pattern += "(?<" + name + ">";
        return this;
    }
    
    public PatternBuilder closeParantheses() {
        pattern += ")";
        return this;
    }
    
    public PatternBuilder optional() {
        pattern += "?";
        return this;
    }
    
    public PatternBuilder or() {
        pattern += "|";
        return this;
    }
    
    public PatternBuilder zeroOrMore() {
        pattern += "*";
        return this;
    }
    
    public PatternBuilder oneOrMore() {
        pattern += "+";
        return this;
    }
    
    public PatternBuilder appendCapturingPart(String toAppend, String name) {
        return appendCapturingPart(toAppend, name, false);
    }
    
    public PatternBuilder appendCapturingPart(String toAppend, String name, boolean optional) {
        openNamedParantheses(name);
        appendNonCapturingPart(toAppend);
        closeParantheses();
        optional();
        return this;
    }
    
    public PatternBuilder appendCapturingPart(Pattern toAppend, String name) {
        return appendCapturingPart(toAppend, name, false);
    }
    
    public PatternBuilder appendCapturingPart(Pattern toAppend, String name, boolean optional) {
        return appendCapturingPart(toAppend.toString(), name, optional);
    }

    public PatternBuilder appendNonCapturingPart(String toAppend) {
        pattern += Pattern.quote(toAppend);
        return this;
    }

    public Pattern build() {
        return Pattern.compile(pattern);
    }
}
