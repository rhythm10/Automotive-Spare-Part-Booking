package com.example.Assignment.Service;

import com.example.Assignment.Model.SparePart;

import java.util.List;

public interface SparePartService {
    List<SparePart> addSparePart(List<SparePart> sparePart);

    List<SparePart> getSpareParts();

    SparePart updateSparePart(Long id, SparePart updateSparePart);

    void removeSparePart(Long id);

    SparePart bookSparePart(Long id);
}
