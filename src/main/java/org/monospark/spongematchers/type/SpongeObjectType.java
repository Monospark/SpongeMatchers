package org.monospark.spongematchers.type;

import java.util.Map;
import java.util.function.Function;

import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.parser.SpongeMatcherParseException;
import org.monospark.spongematchers.parser.element.StringElement;

public abstract class SpongeObjectType<T> extends MatcherType<T> {

    private String name;

    private Class<?> typeClass;

    private Function<SpongeMatcher<Map<String, Object>>, SpongeMatcher<T>> creationFunction;

    private MatcherType<Map<String, Object>> type;

    SpongeObjectType(String name, Class<?> typeClass,
            Function<SpongeMatcher<Map<String, Object>>, SpongeMatcher<T>> creationFunction) {
        this.name = name;
        this.typeClass = typeClass;
        this.creationFunction = creationFunction;
    }

    private MatcherType<Map<String, Object>> getType() {
        if (type == null) {
            type = createType();
        }
        return type;
    }

    protected abstract MatcherType<Map<String, Object>> createType();

    @Override
    public final boolean canMatch(Object o) {
        return typeClass.isInstance(o);
    }

    @Override
    protected final boolean canParse(StringElement element) {
        return getType().canParse(element);
    }

    @Override
    protected final SpongeMatcher<T> parse(StringElement element) throws SpongeMatcherParseException {
        try {
            return creationFunction.apply(getType().parse(element));
        } catch (SpongeMatcherParseException e) {
            throw new SpongeMatcherParseException("Couldn't parse " + name + " matcher: " + element.getString(), e);
        }
    }
}
