package com.ejemplo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ContactManagerTest {
    private ContactManager contactManager;

    @BeforeEach
    public void setup() {
        contactManager = new ContactManager();
    }

    @Test
    public void shouldAddContact() {
        contactManager.addContact("Carlos", "1234567890");
        assertFalse(contactManager.getAllContacts().isEmpty());
        assertEquals(1, contactManager.getAllContacts().size());
    }

    @Test
    public void shouldFindContact() {
        contactManager.addContact("Ana", "0987654321");
        assertNotNull(contactManager.findContact("Ana"));
    }

    @Test
    public void shouldDeleteContact() {
        contactManager.addContact("Ana", "0987654321");
        assertTrue(contactManager.deleteContact("Ana"));
    }
}
