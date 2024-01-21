package br.com.stoom.store.controller.parsers;


import br.com.stoom.store.exceptions.BadRequestException;

import java.util.Objects;

public abstract class Parser<Domain, Dto> {
    public abstract Domain parse(final Dto dto);

    public abstract Dto toDTO(final Domain domain);

    public String validate(final String txt, final String propertyName) {
        if (Objects.isNull(txt) || txt.trim().isEmpty()) {
            throw new BadRequestException("Informe o/a " + propertyName);
        }
        return txt.trim();
    }
}
