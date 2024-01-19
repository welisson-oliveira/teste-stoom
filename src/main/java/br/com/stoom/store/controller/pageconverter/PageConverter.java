package br.com.stoom.store.controller.pageconverter;

import br.com.stoom.store.controller.parsers.Parser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class PageConverter<Domain, DTO> implements Function<Domain, DTO> {

    private final Map<Class, Parser<Domain, DTO>> parsers;

    @Override
    public DTO apply(final Domain domain) {
        return this.parsers.get(domain.getClass()).toDTO(domain);
    }
}
