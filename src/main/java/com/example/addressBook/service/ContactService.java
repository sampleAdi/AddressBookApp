package com.example.addressBook.service;

import com.example.addressBook.dto.ContactDTO;
import com.example.addressBook.mapper.ContactMapper;
import com.example.addressBook.model.Contact;
import com.example.addressBook.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private ContactMapper contactMapper;

    public List<ContactDTO> getAllContacts() {
        return contactRepository.findAll()
                .stream()
                .map(contactMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<ContactDTO> getContactById(Long id) {
        return contactRepository.findById(id).map(contactMapper::toDTO);
    }

    public ContactDTO addContact(ContactDTO contactDTO) {
        Contact contact = contactMapper.toEntity(contactDTO);
        return contactMapper.toDTO(contactRepository.save(contact));
    }

    public ContactDTO updateContact(Long id, ContactDTO contactDTO) {
        return contactRepository.findById(id)
                .map(existingContact -> {
                    existingContact.setName(contactDTO.getName());
                    existingContact.setEmail(contactDTO.getEmail());
                    return contactMapper.toDTO(contactRepository.save(existingContact));
                }).orElse(null);
    }

    public void deleteContact(Long id) {
        contactRepository.deleteById(id);
    }
}