package com.contactos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GestorContactosTest {

    private GestorContactos gestor;

    @BeforeEach
    void inicializar() {
        gestor = new GestorContactos();
    }

    @Test
    void deberiaAgregarUnContacto() {

        Contacto contacto =
                new Contacto(1, "Rodrigo", "rodrigo@email.com", 24);

        gestor.agregarContacto(contacto);

        assertEquals(1, gestor.obtenerNumeroContactos());
    }

    @Test
    void deberiaBuscarUnContactoPorId() {

        Contacto contacto =
                new Contacto(1, "Rodrigo", "rodrigo@email.com", 24);

        gestor.agregarContacto(contacto);

        Contacto contactoEncontrado =
                gestor.buscarContactoPorId(1);

        assertNotNull(contactoEncontrado);

        assertEquals("Rodrigo",
                contactoEncontrado.getNombre());

        assertEquals("rodrigo@email.com",
                contactoEncontrado.getEmail());

        assertEquals(24,
                contactoEncontrado.getEdad());
    }

    @Test
    void deberiaDevolverNullCuandoElContactoNoExiste() {

        Contacto contactoEncontrado =
                gestor.buscarContactoPorId(100);

        assertNull(contactoEncontrado);
    }

    @Test
    void deberiaEliminarUnContactoExistente() {

        Contacto contacto =
                new Contacto(1, "Rodrigo", "rodrigo@email.com", 24);

        gestor.agregarContacto(contacto);

        boolean eliminado =
                gestor.eliminarContacto(1);

        assertTrue(eliminado);

        assertEquals(0,
                gestor.obtenerNumeroContactos());

        assertNull(
                gestor.buscarContactoPorId(1)
        );
    }

    @Test
    void noDeberiaEliminarUnContactoInexistente() {

        boolean eliminado =
                gestor.eliminarContacto(100);

        assertFalse(eliminado);
    }

}