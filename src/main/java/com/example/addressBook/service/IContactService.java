package com.example.addressBook.service;

import com.example.addressBook.dto.ContactDTO;
import java.util.List;

public interface IContactService {
    List<ContactDTO> getAllContacts();
    ContactDTO getContactById(Long id);
    ContactDTO addContact(ContactDTO contactDTO);
    ContactDTO updateContact(Long id, ContactDTO contactDTO);
    boolean deleteContact(Long id);
}
