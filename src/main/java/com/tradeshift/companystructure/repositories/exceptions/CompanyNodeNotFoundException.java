package com.tradeshift.companystructure.repositories.exceptions;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * Created by F.Noori on 1/22/2019.
 */
public class CompanyNodeNotFoundException extends Exception {

    public CompanyNodeNotFoundException(Class clazz, String... searchParamsMap) {
        super(CompanyNodeNotFoundException.generateMessage(clazz.getSimpleName(), toMap(String.class, String.class, searchParamsMap)));
    }

    private static String generateMessage(String entity, Map<String, String> searchParams) {
        return StringUtils.capitalize(entity) +
                " was not found for parameters " +
                searchParams;
    }

    private static <K, V> Map<K, V> toMap(
            Class<K> keyType, Class<V> valueType, Object... entries) {
        if (entries.length % 2 == 1)
            throw new IllegalArgumentException("Invalid entries");
        return IntStream.range(0, entries.length / 2).map(i -> i * 2)
                .collect(HashMap::new,
                        (m, i) -> m.put(keyType.cast(entries[i]), valueType.cast(entries[i + 1])),
                        Map::putAll);
    }
}
