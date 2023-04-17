package com.example.Assignment.Service;

import com.example.Assignment.Model.Booking;
import com.example.Assignment.Model.SparePart;
import com.example.Assignment.Repository.BookingRepository;
import com.example.Assignment.Repository.SparePartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private SparePartRepository sparePartRepository;
    @Autowired
    private BookingRepository bookingRepository;

    @Override
    public ResponseEntity<?> bookSparePart(String name, Authentication authentication) {
        String username = null;
        if (authentication != null && authentication.isAuthenticated()) {
            username = authentication.getName();
        }

        if (username == null) {
            return ResponseEntity.badRequest().body("Username not found in authentication token.");
        }

        Optional<SparePart> optionalSparePart = sparePartRepository.findByName(name);
        if (optionalSparePart.isPresent()) {
            SparePart sparePart = optionalSparePart.get();
            if (sparePart.getQuantity() > 0) {
                sparePart.setQuantity(sparePart.getQuantity() - 1);
                sparePartRepository.save(sparePart);

                Optional<Booking> optionalBooking = bookingRepository.findByName(sparePart.getName());
                if (optionalBooking.isPresent()) {
                    Booking availableBooking = optionalBooking.get();
                    availableBooking.setQuantity(availableBooking.getQuantity() + 1);
                    bookingRepository.save(availableBooking);
                } else {
                    Booking temp = new Booking(username, sparePart.getName(), 1);
                    bookingRepository.save(temp);
                }
                return ResponseEntity.status(HttpStatus.OK).body("Spare Parts Booked for Current User");
            } else {
                return ResponseEntity.badRequest().body("Spare part out of stock");
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<?> bookSparePartsInBulk(@RequestBody List<SparePart> spareParts, Authentication authentication) {
        authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = null;

        if (authentication != null && authentication.isAuthenticated()) {
            username = authentication.getName();
        }

        if (username == null) {
            return ResponseEntity.badRequest().body("Username not found in authentication token.");
        }

        for (SparePart sparePart : spareParts) {
            Optional<SparePart> optionalSparePart = sparePartRepository.findByName(sparePart.getName());
            System.out.println(optionalSparePart + "   " + optionalSparePart.isPresent());

            if (optionalSparePart.isPresent()) {
                SparePart availableSparePart = optionalSparePart.get();
                if ((availableSparePart.getQuantity() - sparePart.getQuantity()) >= 0) {
                    availableSparePart.setQuantity(availableSparePart.getQuantity() - sparePart.getQuantity());
                    sparePartRepository.save(availableSparePart);
                    Optional<Booking> optionalBooking = bookingRepository.findByName(sparePart.getName());
                    if (optionalBooking.isPresent()) {
                        Booking availableBooking = optionalBooking.get();
                        availableBooking.setQuantity(availableBooking.getQuantity() + sparePart.getQuantity());
                        bookingRepository.save(availableBooking);
                    } else {
                        Booking temp = new Booking(username, sparePart.getName(), sparePart.getQuantity());
                        bookingRepository.save(temp);
                    }
                } else {
                    return ResponseEntity.badRequest().body("Spare part is out of stock: " + availableSparePart.getName());
                }
            } else {
                return ResponseEntity.badRequest().body("Spare part not found with ID: " + sparePart.getId());
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body("Spare Parts Booked for Current User");
    }

    @Override
    public ResponseEntity<List<Booking>> viewUserBooking() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        List<Booking> bookings = bookingRepository.findByUsername(username);
        return ResponseEntity.ok(bookings);
    }

}
