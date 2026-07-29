package com.contactos;

import java.util.ArrayList;
import java.util.List;

public class GestorContactos {

    private final List<Contacto> contactos;

    public GestorContactos() {
        contactos = new ArrayList<>();
    }

    public void agregarContacto(Contacto contacto) {

        if (contacto == null) {
            throw new IllegalArgumentException(
                    "El contacto no puede ser nulo"
            );
        }

        contactos.add(contacto);
    }

    public Contacto buscarContactoPorId(int id) {

        for (Contacto contacto : contactos) {

            if (contacto.getId() == id) {
                return contacto;
            }
        }

        return null;
    }

    public boolean eliminarContacto(int id) {

        Contacto contacto =
                buscarContactoPorId(id);

        if (contacto == null) {
            return false;
        }

        contactos.remove(contacto);

        return true;
    }

    public int obtenerNumeroContactos() {
        return contactos.size();
    }
}