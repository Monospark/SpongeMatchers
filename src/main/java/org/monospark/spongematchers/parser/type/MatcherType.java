package org.monospark.spongematchers.parser.type;

import java.util.List;
import java.util.Optional;

import org.monospark.spongematchers.matcher.MatcherLogic;
import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.parser.SpongeMatcherParseException;
import org.monospark.spongematchers.parser.base.BaseMatcherParser;
import org.monospark.spongematchers.parser.element.ConnectedElement;
import org.monospark.spongematchers.parser.element.ConnectedElement.Operator;
import org.monospark.spongematchers.parser.element.PatternElement;
import org.monospark.spongematchers.parser.element.PatternElementParser.Type;
import org.monospark.spongematchers.parser.element.StringElement;
import org.monospark.spongematchers.parser.type.MapType.Builder;

public abstract class MatcherType<T> {

    public static final MatcherType<Boolean> BOOLEAN = new BaseType<>(BaseMatcherParser.BOOLEAN);
    
    public static final MatcherType<Long> INTEGER = new BaseType<>(BaseMatcherParser.INTEGER);
    
    public static final MatcherType<Double> FLOATING_POINT = new BaseType<>(BaseMatcherParser.FLOATING_POINT);
    
    public static final MatcherType<String> STRING = new BaseType<>(BaseMatcherParser.STRING);
    
    public static <T> MatcherType<List<T>> list(MatcherType<T> type) {
        return new ListType<T>(type);
    }
    
    public static Builder map() {
        return MapType.builder();
    }
    
    public static <T> MatcherType<Optional<T>> optional(MatcherType<T> type) {
        return new OptionalType<T>(type);
    }
    
    public final SpongeMatcher<T> parseMatcher(StringElement element) throws SpongeMatcherParseException {
        if (element instanceof ConnectedElement) {
            ConnectedElement con = (ConnectedElement) element;
            SpongeMatcher<T> matcher1 = parseMatcher(con.getFirstElement());
            SpongeMatcher<T> matcher2 = parseMatcher(con.getSecondElement());
            return con.getOperator() == Operator.AND ? MatcherLogic.and(matcher1, matcher2)
                    : MatcherLogic.or(matcher1, matcher2);
        } else if (element instanceof PatternElement) {
            PatternElement p = (PatternElement) element;
            if (p.getType() == Type.NOT) {
                return MatcherLogic.not(parseMatcher(p.getElement()));
            } else if(p.getType() == Type.PARANTHESES) {
                return parseMatcher(p.getElement());
            }
        } 
        return parse(element);
    }
    
    abstract SpongeMatcher<T> parse(StringElement element) throws SpongeMatcherParseException;
}
