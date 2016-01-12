package org.monospark.spongematchers.parser;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import com.google.common.collect.Lists;

public final class ParserHelper {

    private ParserHelper() {}
    
    public static <T> Optional<List<T>> tokenize(String string, char tokenizer,
            ParserFunction<T> function) throws SpongeMatcherParseException {
        List<T> parts = Lists.newArrayList();
        String[] split = string.split(Pattern.quote(String.valueOf(tokenizer)));
        if (split.length == 1) {
            Optional<? extends T> applied = function.parse(string);
            return applied.isPresent() ? Optional.of(Collections.singletonList(applied.get())) : Optional.empty();
        }
        
        int start = 0;
        for (int i = 0; i < split.length; i++) {
            String current = concatStringParts(split, start, i, tokenizer);
            Optional<? extends T> part = function.parse(current);
            if (part.isPresent()) {
                parts.add(part.get());
                start = i + 1;
            }
        }
        
        return start != split.length ? Optional.empty() : Optional.of(parts);
    }
    
    private static String concatStringParts(String[] split, int start, int end, char tokenizer) {
        StringBuilder builder = new StringBuilder();
        builder.append(split[start]);
        for (int i = start + 1; i <= end; i++) {
            builder.append(tokenizer);
            builder.append(split[i]);
        }
        return builder.toString();
    }
    
    @FunctionalInterface
    public static interface ParserFunction<T> {
        
        Optional<? extends T> parse(String input) throws SpongeMatcherParseException;
    }
}
