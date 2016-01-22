package org.monospark.spongematchers.parser.element;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.monospark.spongematchers.parser.SpongeMatcherParseException;
import org.monospark.spongematchers.parser.base.BaseMatcherParser;
import org.monospark.spongematchers.parser.element.PatternElementParser.Type;

import com.google.common.collect.Sets;

public abstract class StringElementParser {

    public static final Pattern REPLACE_PATTERN = Pattern.compile("#+");
    
    private static final Set<StringElementParser> BASE_ELEMENT_PARSERS = createBaseElementParsers();
    
    private static final Set<StringElementParser> OTHER_ELEMENT_PARSERS = createOtherElementParsers();
    
    private static Set<StringElementParser> createBaseElementParsers() {
        Set<StringElementParser> baseParsers = Sets.newLinkedHashSet();
        baseParsers.add(new BaseElementParser<>(BaseMatcherParser.STRING));
        baseParsers.add(new BaseElementParser<>(BaseMatcherParser.BOOLEAN));
        baseParsers.add(new BaseElementParser<>(BaseMatcherParser.FLOATING_POINT));
        baseParsers.add(new BaseElementParser<>(BaseMatcherParser.INTEGER));
        baseParsers.add(new EmptyElementParser());
        return baseParsers;
    }
    
    private static Set<StringElementParser> createOtherElementParsers() {
        Set<StringElementParser> otherParsers = Sets.newHashSet();
        otherParsers.add(new PatternElementParser(Type.PARANTHESES));
        otherParsers.add(new PatternElementParser(Type.NOT));
        otherParsers.add(new ConnectedElementParser());
        otherParsers.add(new ListElementParser());
        otherParsers.add(new PatternElementParser(Type.LIST_MATCH_ANY));
        otherParsers.add(new PatternElementParser(Type.LIST_MATCH_ALL));
        otherParsers.add(new MapElementParser());
        return otherParsers;
    }
    
    public static StringElement parseStringElement(String string) throws SpongeMatcherParseException {
        StringElementContext context = new StringElementContext(string.trim());
        for (StringElementParser parser : BASE_ELEMENT_PARSERS) {
            parser.parseElements(context);
        }
        String lastContextString;
        do {
            lastContextString = context.getString();
            for (StringElementParser parser : OTHER_ELEMENT_PARSERS) {
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
