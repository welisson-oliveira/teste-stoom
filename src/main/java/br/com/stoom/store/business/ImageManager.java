package br.com.stoom.store.business;

import br.com.stoom.store.exceptions.FileException;
import br.com.stoom.store.model.Image;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Component
public class ImageManager {

    @Value("${file.allowed-file-extensions}")
    private List<String> allowedExtensions;

    public String upload(final String directory, final MultipartFile file) throws IOException {

        if (Objects.isNull(file) || Objects.isNull(file.getOriginalFilename())) {
            throw new FileException("Adicione um arquivo");
        }

        final String[] split = file.getOriginalFilename().split("\\.");
        final String ext = split[split.length - 1];

        if (!this.allowedExtensions.contains(ext)) {
            throw new FileException("Tipo de arquivo invalido");
        }
        final byte[] bytes = file.getBytes();

        final String imageName = UUID.randomUUID() + file.getOriginalFilename();
        final Path pathDir = Paths.get(directory + File.separator + imageName);
        Files.write(pathDir, bytes);
        return imageName;
    }

    public byte[] read(final Image image) throws IOException {
        final File imageFile = new File(image.getAbsolutePath());
        return Files.readAllBytes(imageFile.toPath());
    }

    public void delete(final Image image) {
        try {
            final Path pathDir = Paths.get(image.getAbsolutePath());
            Files.delete(pathDir);
        } catch (final IOException e) {
            throw new FileException(e.getMessage());
        }
    }

}
