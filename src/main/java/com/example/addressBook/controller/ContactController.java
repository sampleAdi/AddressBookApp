package com.example.addressBook.controller;

import com.example.addressBook.dto.ContactDTO;
import com.example.addressBook.service.IContactService;
import com.example.addressBook.service.MessageProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    IContactService contactService;

    @Autowired
    MessageProducer messageProducer;  // Inject RabbitMQ Producer


    @Autowired
    public ContactController(IContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping
    public ResponseEntity<List<ContactDTO>> getAllContacts() {
        log.info("Fetching all contacts");
        List<ContactDTO> contacts = contactService.getAllContacts();
        return ResponseEntity.ok(contacts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContactDTO> getContactById(@PathVariable Long id) {
        log.info("Fetching contact with ID: {}", id);
        ContactDTO contactDTO = contactService.getContactById(id);
        return ResponseEntity.ok(contactDTO);
    }

    @PostMapping
    public ResponseEntity<ContactDTO> addContact(@RequestBody @Valid ContactDTO contactDTO) {
        log.info("Adding new contact: {}", contactDTO);
        ContactDTO savedContact = contactService.addContact(contactDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedContact);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContactDTO> updateContact(@PathVariable Long id, @RequestBody @Valid ContactDTO contactDTO) {
        log.info("Updating contact with ID: {}", id);
        ContactDTO updatedContact = contactService.updateContact(id, contactDTO);
        return ResponseEntity.ok(updatedContact);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContact(@PathVariable Long id) {
        log.info("Deleting contact with ID: {}", id);
        contactService.deleteContact(id);
        return ResponseEntity.noContent().build();
    }

    // Send Contact Details to RabbitMQ
    @PostMapping("/sendToQueue")
    public ResponseEntity<String> sendToQueue(@RequestBody ContactDTO dto) {
        messageProducer.sendMessage("Contact Info: " + dto.toString());
        return ResponseEntity.ok("Contact sent to RabbitMQ successfully");
    }
}