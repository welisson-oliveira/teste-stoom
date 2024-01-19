package br.com.stoom.store.business;

import br.com.stoom.store.model.Image;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Component
public class ImageManager {

    public String upload(final String directory, final MultipartFile file) {
        final byte[] bytes;
        try {
            bytes = file.getBytes();

            final String imageName = UUID.randomUUID() + file.getOriginalFilename();
            final Path pathDir = Paths.get(directory + "/" + imageName);
            Files.write(pathDir, bytes);
            return imageName;

        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
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
            throw new RuntimeException(e);
        }
    }

}
