package aldora.springframework.springpetclinic.repositories;

import aldora.springframework.springpetclinic.model.PetType;
import org.springframework.data.repository.CrudRepository;

public interface PetTypeRepository extends CrudRepository<PetType, Long> {
}
