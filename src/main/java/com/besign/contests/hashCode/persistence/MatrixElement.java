//package com.besign.contests.hashCode.persistence;
//
//import javax.persistence.Column;
//import javax.persistence.EmbeddedId;
//import javax.persistence.Entity;
//import java.io.Serializable;
//
//@Entity
//public class MatrixElement implements Serializable {
//
//    @EmbeddedId
//    public MatrixId matrixId;
//
//    @Column
//    public Long v;
//
//    public MatrixElement() {}
//
//    public MatrixElement(Long row, Long column, Long value) {
//        this.matrixId = new MatrixId(row, column);
//        this.v = value;
//    }
//}
