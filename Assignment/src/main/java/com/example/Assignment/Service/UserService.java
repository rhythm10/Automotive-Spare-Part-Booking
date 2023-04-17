package com.example.Assignment.Service;

import com.example.Assignment.Model.Booking;
import com.example.Assignment.Model.SparePart;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface UserService {
    ResponseEntity<?> bookSparePartsInBulk(List<SparePart> spareParts, Authentication authentication);

    ResponseEntity<List<Booking>> viewUserBooking();

    ResponseEntity<?> bookSparePart(String name, Authentication authentication);
}
