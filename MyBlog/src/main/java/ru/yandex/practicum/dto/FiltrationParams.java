package ru.yandex.practicum.dto;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Predicate;

public record FiltrationParams(String tags) {

    private static final String WHERE_CONDITION = " WHERE";

    public String getWhereClauseForTags() {
        String condition = Optional.ofNullable(tags)
                .filter(Predicate.not(String::isBlank))
                .map(t -> t.split(";"))
                .map(Arrays::asList)
                .stream()
                .flatMap(Collection::stream)
                .reduce(WHERE_CONDITION, "%s ARRAY_CONTAINS(TAGS, '%s') OR"::formatted);
        return condition.substring(0, condition.length() - 3);
    }
}
