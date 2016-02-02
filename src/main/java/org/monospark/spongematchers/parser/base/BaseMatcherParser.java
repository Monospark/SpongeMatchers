package org.monospark.spongematchers.parser.base;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.parser.SpongeMatcherParseException;

public abstract class BaseMatcherParser<T> {

    public static final BaseMatcherParser<Boolean> BOOLEAN = new BooleanMatcherParser();

    public static final BaseMatcherParser<Long> INTEGER = new IntegerMatcherParser();

    public static final BaseMatcherParser<Double> FLOATING_POINT = new FloatingPointMatcherParser();

    public static final BaseMatcherParser<String> STRING = new StringMatcherParser();

    private String name;

    private Class<T> baseClass;

    private Pattern acceptancePattern;

    BaseMatcherParser(String name, Class<T> baseClass) {
        this.name = name;
        this.baseClass = baseClass;
        acceptancePattern = createAcceptancePattern();
    }

    protected abstract Pattern createAcceptancePattern();

    public final SpongeMatcher<T> parseMatcher(String string) throws SpongeMatcherParseException {
        Objects.requireNonNull(string, "Input string must be not null");

        Matcher matcher = acceptancePattern.matcher(string.trim());
        if (!matcher.matches()) {
            throw new SpongeMatcherParseException("Invalid " + name + " matcher: " + string);
        }

        SpongeMatcher<T> parsed = parse(matcher);
        return parsed;
    }

    protected abstract SpongeMatcher<T> parse(Matcher matcher) throws SpongeMatcherParseException;

    public final String getName() {
        return name;
    }

    public final Class<T> getBaseClass() {
        return baseClass;
    }

    public final Pattern getAcceptancePattern() {
        return acceptancePattern;
    }
}
