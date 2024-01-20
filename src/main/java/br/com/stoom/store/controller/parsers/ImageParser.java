package br.com.stoom.store.controller.parsers;

import br.com.stoom.store.business.ImageManager;
import br.com.stoom.store.controller.dto.ImageDTO;
import br.com.stoom.store.exceptions.FileException;
import br.com.stoom.store.model.Image;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ImageParser {

    private final ImageManager imageManager;

    public ImageDTO toDTO(final Image image) throws IOException {
        return new ImageDTO(image.getId(), image.getName(), image.getPath(), image.getProduct(), this.imageManager.read(image));
    }

    public List<ImageDTO> toDTOs(final List<Image> images) {
        return images.stream().map(image -> {
            try {
                return this.toDTO(image);
            } catch (final IOException e) {
                throw new FileException(e.getMessage());
            }
        }).collect(Collectors.toList());
    }
}
