package org.sopt.diary.api;

public enum OrderBy {
    ASC,
    DESC;

    public static OrderBy getValue(String orderBy) {
        try {
            return OrderBy.valueOf(orderBy.toUpperCase());
        } catch (IllegalArgumentException e) {
            return OrderBy.ASC;
        }
    }
}
