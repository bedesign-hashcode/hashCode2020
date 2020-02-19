package com.besign.contests.hashCode.persistence;

public interface Matrix {
    default void insert(int row, int column, int value) {
        insert(Long.valueOf(row), Long.valueOf(column), Long.valueOf(value));
    }
    default int get(int row, int column) {
        return get(Long.valueOf(row), Long.valueOf(column)).intValue();
    }

    void insert(Long row, Long column, Long value);
    Long get(Long row, Long column);

    void clear();
}
