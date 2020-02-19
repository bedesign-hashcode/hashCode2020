package com.besign.contests.hashCode.persistence;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class MatrixId  implements Serializable {
    public Long r;

    public Long c;

    // default constructor

    public MatrixId() {}

    public MatrixId(Long row, Long column) {
        this.r = row;
        this.c = column;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MatrixId matrixId = (MatrixId) o;
        return r == matrixId.r &&
                c == matrixId.c;
    }

    @Override
    public int hashCode() {
        return Objects.hash(r, c);
    }
}

