package com.example.Assignment.Controller;

import com.example.Assignment.Model.SparePart;
import com.example.Assignment.Service.SparePartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AdminController {
    @Autowired
    SparePartService sparePartService;

    @PostMapping("/spareparts")
    @Secured("ROLE_ADMIN")
    public List<SparePart> addSparePart(@RequestBody List<SparePart> sparePart) {
        return sparePartService.addSparePart(sparePart);
    }

    @GetMapping("/spareparts")
    @Secured("ROLE_ADMIN")
    public List<SparePart> getSpareParts() {
        return sparePartService.getSpareParts();
    }

    @PutMapping("/spareparts/{id}")
    @Secured("ROLE_ADMIN")
    public SparePart updateSpareParts(@PathVariable Long id, @RequestBody SparePart updatedSparePart) {
        return sparePartService.updateSparePart(id, updatedSparePart);
    }

    @DeleteMapping("/spareparts/{id}")
    @Secured("ROLE_ADMIN")
    public void removeSparePart(@PathVariable Long id) {
        sparePartService.removeSparePart(id);
    }

}
