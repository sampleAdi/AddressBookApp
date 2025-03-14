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

    // Remove private final, use constructor injection
    ContactRepository contactRepository;
    ContactMapper contactMapper;

    // Constructor for dependency injection
    public ContactService(ContactRepository contactRepository, ContactMapper contactMapper) {
        this.contactRepository = contactRepository;
        this.contactMapper = contactMapper;
    }

    @Override
    public List<ContactDTO> getAllContacts() {
        return contactRepository.findAll()
                .stream()
                .map(contactMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ContactDTO getContactById(Long id) {
        Optional<Contact> contact = contactRepository.findById(id);
        return contact.map(contactMapper::toDTO)
                .orElseThrow(() -> new AddressBookException("Contact with ID: " + id + " not found"));
    }

    @Override
    public ContactDTO addContact(ContactDTO contactDTO) {
        Contact contact = contactMapper.toEntity(contactDTO);
        return contactMapper.toDTO(contactRepository.save(contact));
    }

    @Override
    public ContactDTO updateContact(Long id, ContactDTO contactDTO) {
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
            contactRepository.deleteById(id);
            return true;
        } else {
            throw new AddressBookException("Contact with ID: " + id + " not found");
        }
    }
}
