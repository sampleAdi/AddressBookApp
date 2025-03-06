package com.example.addressBook.mapper;

import com.example.addressBook.dto.ContactDTO;
import com.example.addressBook.model.Contact;
import org.springframework.stereotype.Component;

@Component
public class ContactMapper {

    public ContactDTO toDTO(Contact contact) {
        return new ContactDTO(contact.getId(), contact.getName(), contact.getEmail(), contact.getPhone());
    }

    public Contact toEntity(ContactDTO contactDTO) {
        Contact contact = new Contact();
        contact.setId(contactDTO.getId());
        contact.setName(contactDTO.getName());
        contact.setEmail(contactDTO.getEmail());
        contact.setPhone(contactDTO.getPhone());
        return contact;
    }
}