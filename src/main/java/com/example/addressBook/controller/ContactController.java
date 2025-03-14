package com.example.addressBook.controller;

import com.example.addressBook.dto.ContactDTO;
import com.example.addressBook.service.IContactService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@Validated
@RequestMapping("/api/contacts")
public class ContactController {

    // Remove private final, use constructor injection
    IContactService contactService;

    public ContactController(IContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping
    public ResponseEntity<List<ContactDTO>> getAllContacts() {
        log.info("Fetching all contacts");
        return ResponseEntity.ok(contactService.getAllContacts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContactDTO> getContactById(@PathVariable Long id) {
        log.info("Fetching contact with ID: {}", id);
        return ResponseEntity.ok(contactService.getContactById(id));
    }

    @PostMapping
    public ResponseEntity<ContactDTO> addContact(@RequestBody @Valid ContactDTO contactDTO) {
        log.info("Adding new contact: {}", contactDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(contactService.addContact(contactDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContactDTO> updateContact(@PathVariable Long id, @RequestBody @Valid ContactDTO contactDTO) {
        log.info("Updating contact with ID: {}", id);
        return ResponseEntity.ok(contactService.updateContact(id, contactDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContact(@PathVariable Long id) {
        log.info("Deleting contact with ID: {}", id);
        contactService.deleteContact(id);
        return ResponseEntity.noContent().build();
    }
}
