package org.monospark.spongematchers.parser.element;

import java.util.Set;

import org.monospark.spongematchers.parser.SpongeMatcherParseException;

import com.google.common.base.Strings;
import com.google.common.collect.Sets;

public final class StringElementContext {

    private String original;

    private String string;

    private Set<StringElement> elements;

    StringElementContext(String string) {
        original = string;
        this.string = string;
        this.elements = Sets.newHashSet();
    }

    public StringElement getFinalElement() throws SpongeMatcherParseException {
        if (elements.size() != 1) {
            throw new SpongeMatcherParseException("Invalid input string: " + original);
        }
        StringElement element = elements.iterator().next();
        if (element.getStart() != 0 || element.getEnd() != original.length()) {
            throw new SpongeMatcherParseException("Invalid input string: " + original);
        }

        return element;
    }

    public void addElement(StringElement e) {
        int size = e.getEnd() - e.getStart();
        String replace = Strings.repeat("#", size);
        String newString = string.substring(0, e.getStart()) + replace + string.substring(e.getEnd(), string.length());
        string = newString;
        elements.add(e);
    }

    public void removeElement(StringElement e) {
        elements.remove(e);
    }

    public StringElement getElementAt(int start, int end) throws SpongeMatcherParseException {
        for (StringElement e : elements) {
            if (e.getStart() == start && e.getEnd() == end) {
                return e;
            }
        }
        throw new SpongeMatcherParseException("No element found at: " + start + " - " + end);
    }

    public String getString() {
        return string;
    }

    public String getOriginalStringAt(int start, int end) {
        return original.substring(start, end);
    }
}
