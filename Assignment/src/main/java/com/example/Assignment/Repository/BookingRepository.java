package com.example.Assignment.Repository;

import com.example.Assignment.Model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByUsername(String username);

    Optional<Booking> findByName(String Name);
}
