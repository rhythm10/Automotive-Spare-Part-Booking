package com.example.Assignment.Repository;

import com.example.Assignment.Model.SparePart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SparePartRepository extends JpaRepository<SparePart, Long> {
    Optional<SparePart> findByName(String name);


}
