package com.example.Assignment.Controller;

import com.example.Assignment.Model.Booking;
import com.example.Assignment.Model.SparePart;
import com.example.Assignment.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    // Endpoint to book a single spare part
    @PostMapping("/spareparts/book/{name}")
    @Secured("ROLE_USER")
    public ResponseEntity<?> bookSparePart(@PathVariable String name, Authentication authentication) {
        return userService.bookSparePart(name, authentication);
    }

    //Endpoint to book bulk of Spare Parts
    @PostMapping("/spareparts/book")
    @Secured("ROLE_USER")
    public ResponseEntity<?> bookSparePartsInBulk(@RequestBody List<SparePart> spareParts, Authentication authentication) {
        return userService.bookSparePartsInBulk(spareParts, authentication);
    }

    @GetMapping("/bookings")
    @Secured("ROLE_USER")
    public ResponseEntity<List<Booking>> viewUserBookings() {
        return userService.viewUserBooking();
    }

}
