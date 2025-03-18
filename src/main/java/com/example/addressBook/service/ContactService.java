package com.example.addressBook.service;

import com.example.addressBook.dto.ContactDTO;
import com.example.addressBook.exceptions.AddressBookException;
import com.example.addressBook.model.Contact;
import com.example.addressBook.repository.ContactRepository;
import com.example.addressBook.mapper.ContactMapper;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ContactService implements IContactService {

    private ContactRepository contactRepository;
    private ContactMapper contactMapper;

    public ContactService(ContactRepository contactRepository, ContactMapper contactMapper) {
        this.contactRepository = contactRepository;
        this.contactMapper = contactMapper;
    }

    @Override
    public List<ContactDTO> getAllContacts() {
        try {
            return contactRepository.findAll()
                    .stream()
                    .map(contactMapper::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new AddressBookException("Error fetching contacts: " + e.getMessage());
        }
    }

    @Override
    public ContactDTO getContactById(Long id) {
        try {
            Optional<Contact> contact = contactRepository.findById(id);
            return contact.map(contactMapper::toDTO)
                    .orElseThrow(() -> new AddressBookException("Contact not found with ID: " + id));
        } catch (Exception e) {
            throw new AddressBookException("Error fetching contact with ID " + id + ": " + e.getMessage());
        }
    }

    @Override
    public ContactDTO addContact(ContactDTO contactDTO) {
        try {
            Contact contact = contactMapper.toEntity(contactDTO);
            return contactMapper.toDTO(contactRepository.save(contact));
        } catch (Exception e) {
            throw new AddressBookException("Error adding contact: " + e.getMessage());
        }
    }

    @Override
    public ContactDTO updateContact(Long id, ContactDTO contactDTO) {
        try {
            Contact contact = contactRepository.findById(id)
                    .orElseThrow(() -> new AddressBookException("Contact not found for update with ID: " + id));

            contact.setName(contactDTO.getName());
            contact.setEmail(contactDTO.getEmail());
            contact.setPhone(contactDTO.getPhone());
            contact.setCity(contactDTO.getCity());

            return contactMapper.toDTO(contactRepository.save(contact));
        } catch (Exception e) {
            throw new AddressBookException("Error updating contact with ID " + id + ": " + e.getMessage());
        }
    }

    @Override
    public boolean deleteContact(Long id) {
        try {
            if (contactRepository.existsById(id)) {
                contactRepository.deleteById(id);
                return true;
            } else {
                throw new AddressBookException("Contact not found for deletion with ID: " + id);
            }
        } catch (Exception e) {
            throw new AddressBookException("Error deleting contact with ID " + id + ": " + e.getMessage());
        }
    }
}