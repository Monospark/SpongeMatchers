package org.monospark.spongematchers.parser;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import com.google.common.collect.Lists;

public final class ParserHelper {

    private ParserHelper() {}
    
    public static <T> Optional<List<T>> tokenize(String string, String tokenizer,
            Function<String, Optional<? extends T>> function) {
        List<T> parts = Lists.newArrayList();
        String[] split = string.split(tokenizer);
        int start = 0;
        for (int i = 0; i < split.length; i++) {
            String current = concatStringParts(split, start, i);
            Optional<? extends T> part = function.apply(current);
            if (part.isPresent()) {
                parts.add(part.get());
                start = i + 1;
            }
        }
        
        return start == split.length ? Optional.empty() : Optional.of(parts);
    }
    
    private static String concatStringParts(String[] split, int start, int end) {
        StringBuilder builder = new StringBuilder();
        builder.append(split[start]);
        for (int i = start + 1; i <= end; i++) {
            builder.append(",");
            builder.append(split[i]);
        }
        return builder.toString();
    }
}