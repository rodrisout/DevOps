package com.ejemplo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ContactManager {
    private List<Contact> contactList = new ArrayList<>();

    public void addContact(String name, String phoneNumber) {
        contactList.add(new Contact(name, phoneNumber));
    }

    public Contact findContact(String name) {
        return contactList.stream()
            .filter(contact -> contact.getName().equals(name))
            .findFirst()
            .orElse(null);
    }

    public boolean deleteContact(String name) {
        return contactList.removeIf(contact -> contact.getName().equals(name));
    }

    public List<Contact> getAllContacts() {
        return contactList;
    }
}
