package com.example.addressBook.service;

import com.example.addressBook.dto.ContactDTO;
import com.example.addressBook.exceptions.AddressBookException;
import com.example.addressBook.model.Contact;
import com.example.addressBook.repository.ContactRepository;
import com.example.addressBook.mapper.ContactMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ContactService implements IContactService {

    private final ContactRepository contactRepository;
    private final ContactMapper contactMapper;

    public ContactService(ContactRepository contactRepository, ContactMapper contactMapper) {
        this.contactRepository = contactRepository;
        this.contactMapper = contactMapper;
    }

    @Override
    public List<ContactDTO> getAllContacts() {
        log.info("Fetching all contacts from database");
        return contactRepository.findAll()
                .stream()
                .map(contactMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ContactDTO getContactById(Long id) {
        log.info("Fetching contact with ID: {}", id);
        return contactRepository.findById(id)
                .map(contactMapper::toDTO)
                .orElseThrow(() -> new AddressBookException("Contact with ID: " + id + " not found"));
    }

    @Override
    public ContactDTO addContact(ContactDTO contactDTO) {
        log.info("Adding new contact: {}", contactDTO);
        Contact contact = contactMapper.toEntity(contactDTO);
        return contactMapper.toDTO(contactRepository.save(contact));
    }

    @Override
    public ContactDTO updateContact(Long id, ContactDTO contactDTO) {
        log.info("Updating contact with ID: {}", id);
        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new AddressBookException("Contact with ID: " + id + " not found"));
        contact.setName(contactDTO.getName());
        contact.setEmail(contactDTO.getEmail());
        contact.setPhone(contactDTO.getPhone());
        contact.setCity(contactDTO.getCity());
        return contactMapper.toDTO(contactRepository.save(contact));
    }

    @Override
    public boolean deleteContact(Long id) {
        if (contactRepository.existsById(id)) {
            log.info("Deleting contact with ID: {}", id);
            contactRepository.deleteById(id);
            return true;
        } else {
            log.warn("Attempted to delete non-existing contact with ID: {}", id);
            throw new AddressBookException("Contact with ID: " + id + " not found");
        }
    }
}
