package br.com.stoom.store.controller.parsers;


public abstract class Parser<Domain, Dto> {
    public abstract Domain parse(final Dto dto);

    public abstract Dto toDTO(final Domain domain);
}
