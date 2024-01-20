package br.com.stoom.store.business;

import br.com.stoom.store.business.interfaces.IBrandBO;
import br.com.stoom.store.exceptions.ResourceNotFoundException;
import br.com.stoom.store.model.Brand;
import br.com.stoom.store.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BrandBO implements IBrandBO {

    private final BrandRepository brandRepository;

    @Override
    public Page<Brand> findAll(final Pageable pageable) {
        return this.brandRepository.findAllByActiveTrue(pageable);
    }

    @Override
    public Brand create(final Brand brand) {
        return this.brandRepository.save(brand);
    }

    @Override
    public Brand update(final Long id, final Brand brand) {
        final Brand brandDB = this.getBrand(id);
        brandDB.update(brand);
        return this.brandRepository.save(brandDB);
    }

    @Override
    public Brand activated(final Long id, final Boolean flag) {
        final Brand brandDB = this.getBrand(id);
        brandDB.activated(flag);
        return this.brandRepository.save(brandDB);
    }

    @Override
    public Brand getByName(final String brand) {
        return this.brandRepository.getByName(brand).orElseThrow(() -> new ResourceNotFoundException("Marca não encontrada!"));
    }

    @Override
    public Page<Brand> findAllInactivated(final Pageable pageable) {
        return this.brandRepository.findAllByActiveFalse(pageable);
    }

    private Brand getBrand(final Long id) {
        return this.brandRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Marca não encontrada!"));
    }
}
