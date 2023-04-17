package com.example.Assignment.Service;

import com.example.Assignment.Model.SparePart;
import com.example.Assignment.Repository.SparePartRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SparePartServiceImpl implements SparePartService {
    @Autowired
    private SparePartRepository sparePartRepository;

    @Override
    public List<SparePart> addSparePart(List<SparePart> spareParts) {

        for (SparePart sparePart : spareParts) {
            Optional<SparePart> optionalSparePart = sparePartRepository.findByName(sparePart.getName());
            if (optionalSparePart.isPresent()) {
                SparePart existingSparePart = optionalSparePart.get();
                existingSparePart.setQuantity(existingSparePart.getQuantity() + sparePart.getQuantity());
                sparePartRepository.save(existingSparePart);
            } else {
                sparePartRepository.save(sparePart);
            }
        }
        return spareParts;
    }

    @Override
    public List<SparePart> getSpareParts() {
        List<SparePart> list = new ArrayList<>();
        sparePartRepository.findAll().forEach(list::add);
        return list;
    }

    @Override
    public SparePart updateSparePart(Long id, SparePart updatedSparePart) {
        Optional<SparePart> optionalSparePart = sparePartRepository.findById(id);
        if (optionalSparePart.isPresent()) {
            SparePart sparePart = optionalSparePart.get();
            sparePart.setQuantity(updatedSparePart.getQuantity());
            return sparePartRepository.save(sparePart);
        }
        throw new EntityNotFoundException("Spare Part not found with id " + id);
    }

    @Override
    public void removeSparePart(Long id) {
        Optional<SparePart> optionalSparePart = sparePartRepository.findById(id);
        if (optionalSparePart.isPresent()) {
            sparePartRepository.deleteById(id);
            return;
        }
        throw new EntityNotFoundException("Spare Part not found with id " + id);
    }

    @Override
    public SparePart bookSparePart(Long id) {
        Optional<SparePart> optionalSparePart = sparePartRepository.findById(id);
        if (optionalSparePart.isPresent()) {
            SparePart sparePart = optionalSparePart.get();
            if (sparePart.getQuantity() > 0) {
                sparePart.setQuantity(sparePart.getQuantity() - 1);
                return sparePartRepository.save(sparePart);
            } else {
                throw new EntityNotFoundException("Spare Part is out of quantity with id: " + id);
            }
        }
        throw new EntityNotFoundException("Spare Part not found with id: " + id);
    }
}
