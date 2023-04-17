package com.example.Assignment.Model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class SparePart {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;
    String name;
    int quantity;

    public SparePart() {
    }

    public SparePart(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
