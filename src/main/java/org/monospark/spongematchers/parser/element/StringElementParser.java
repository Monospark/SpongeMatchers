package org.monospark.spongematchers.parser.element;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.monospark.spongematchers.parser.SpongeMatcherParseException;
import org.monospark.spongematchers.parser.base.BaseMatcherParser;

import com.google.common.collect.Sets;

public abstract class StringElementParser {

    public static final Pattern REPLACE_PATTERN = Pattern.compile("#+");

    private static final Set<StringElementParser> ONE_TIME_ELEMENT_PARSERS = createOneTimeElementParsers();

    private static final Set<StringElementParser> MANY_TIMES_ELEMENT_PARSERS = createManyTimesElementParsers();

    private static Set<StringElementParser> createOneTimeElementParsers() {
        Set<StringElementParser> oneTimeParsers = Sets.newLinkedHashSet();
        oneTimeParsers.add(new MapKeyElementParser());
        oneTimeParsers.add(new BaseElementParser<>(BaseMatcherParser.STRING));
        oneTimeParsers.add(new BaseElementParser<>(BaseMatcherParser.BOOLEAN));
        oneTimeParsers.add(new BaseElementParser<>(BaseMatcherParser.FLOATING_POINT));
        oneTimeParsers.add(new BaseElementParser<>(BaseMatcherParser.INTEGER));
        oneTimeParsers.add(new LiteralElementParser(LiteralElement.Type.ABSENT));
        oneTimeParsers.add(new LiteralElementParser(LiteralElement.Type.EXISTENT));
        oneTimeParsers.add(new LiteralElementParser(LiteralElement.Type.NONE));
        oneTimeParsers.add(new LiteralElementParser(LiteralElement.Type.WILDCARD));
        return oneTimeParsers;
    }

    private static Set<StringElementParser> createManyTimesElementParsers() {
        Set<StringElementParser> manyTimesParsers = Sets.newHashSet();
        manyTimesParsers.add(new PatternElementParser(PatternElement.Type.PARANTHESES));
        manyTimesParsers.add(new PatternElementParser(PatternElement.Type.NOT));
        manyTimesParsers.add(new ConnectedElementParser());
        manyTimesParsers.add(new ListElementParser());
        manyTimesParsers.add(new PatternElementParser(PatternElement.Type.LIST_MATCH_ANY));
        manyTimesParsers.add(new PatternElementParser(PatternElement.Type.LIST_MATCH_ALL));
        manyTimesParsers.add(new MapElementParser());
        return manyTimesParsers;
    }

    public static StringElement parseStringElement(String string) throws SpongeMatcherParseException {
        StringElementContext context = new StringElementContext(string.trim());
        for (StringElementParser parser : ONE_TIME_ELEMENT_PARSERS) {
            parser.parseElements(context);
        }
        String lastContextString;
        do {
            lastContextString = context.getString();
            for (StringElementParser parser : MANY_TIMES_ELEMENT_PARSERS) {
                parser.parseElements(context);
            }
        } while (!lastContextString.equals(context.getString()));

        return context.getFinalElement();
    }

    private Pattern pattern;

    StringElementParser() {}

    abstract Pattern createPattern();

    final void parseElements(StringElementContext context) throws SpongeMatcherParseException {
        if (pattern == null) {
            pattern = createPattern();
        }

        Matcher matcher = pattern.matcher(context.getString());
        while (matcher.find()) {
            parse(matcher, context);
        }
    }

    abstract void parse(Matcher matcher, StringElementContext context) throws SpongeMatcherParseException;
}
