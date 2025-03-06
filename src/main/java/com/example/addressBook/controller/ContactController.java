package com.example.addressBook.controller;

import com.example.addressBook.dto.ContactDTO;
import com.example.addressBook.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/contacts")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @GetMapping
    public ResponseEntity<List<ContactDTO>> getAllContacts() {
        return ResponseEntity.ok(contactService.getAllContacts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContactDTO> getContactById(@PathVariable Long id) {
        Optional<ContactDTO> contactDTO = contactService.getContactById(id);
        return contactDTO.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ContactDTO> addContact(@RequestBody ContactDTO contactDTO) {
        return ResponseEntity.ok(contactService.addContact(contactDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContactDTO> updateContact(@PathVariable Long id, @RequestBody ContactDTO contactDTO) {
        ContactDTO updatedContact = contactService.updateContact(id, contactDTO);
        return updatedContact != null ? ResponseEntity.ok(updatedContact) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContact(@PathVariable Long id) {
        contactService.deleteContact(id);
        return ResponseEntity.noContent().build();
    }
}