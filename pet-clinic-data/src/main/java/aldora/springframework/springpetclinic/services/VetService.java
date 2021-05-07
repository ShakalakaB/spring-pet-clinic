package aldora.springframework.springpetclinic.services;

import aldora.springframework.springpetclinic.model.Pet;
import aldora.springframework.springpetclinic.model.Vet;

import java.util.Set;

public interface VetService {
    Vet findById(Long id);

    Vet save(Vet owner);

    Set<Vet> findAll();
}
