package aldora.springframework.springpetclinic.services;

import aldora.springframework.springpetclinic.model.Owner;
import java.util.Set;

public interface OwnerService extends CrudService<Owner, Long> {
    Owner findByLastName(String lastName);
}
