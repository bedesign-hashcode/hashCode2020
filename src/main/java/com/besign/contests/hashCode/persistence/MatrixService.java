package com.besign.contests.hashCode.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.stream.Collectors;

@Service
public class MatrixService implements Matrix {

    @Autowired MatrixElementRepository matrixElementRepository;
    private HashMap<MatrixId, Long> matrixValueCache = new HashMap<>();

    @Override
    public synchronized void insert(Long row, Long column, Long value) {
        if (matrixValueCache.size() >= 100000) {
            matrixElementRepository.saveAll(matrixValueCache.keySet().stream().map(k -> new MatrixElement(k.r, k.c, matrixValueCache.get(k))).collect(Collectors.toList()));
            matrixValueCache.clear();
        }
        matrixElementRepository.save(new MatrixElement(row, column, value));
    }

    @Override
    public synchronized Long get(Long row, Long column) {
        return matrixElementRepository.findById(new MatrixId(row, column)).map(e -> e.v)
                .orElse(matrixValueCache.get(new MatrixId(row, column)));
    }

    @Override
    public void clear() {
        matrixValueCache.clear();
        matrixElementRepository.deleteAll();
    }
}
