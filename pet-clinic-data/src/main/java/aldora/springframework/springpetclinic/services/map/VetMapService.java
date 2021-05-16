package aldora.springframework.springpetclinic.services.map;

import aldora.springframework.springpetclinic.model.Speciality;
import aldora.springframework.springpetclinic.model.Vet;
import aldora.springframework.springpetclinic.services.SpecialityService;
import aldora.springframework.springpetclinic.services.VetService;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class VetMapService extends AbstractMapService<Vet, Long> implements VetService {
    private final SpecialityService specialityService;

    public VetMapService(SpecialityService specialityService) {
        this.specialityService = specialityService;
    }
    @Override
    public Set<Vet> findAll() {
        return super.findAll();
    }

    @Override
    public Vet findById(Long id) {
        return super.findById(id);
    }

    @Override
    public Vet save(Vet object) {
        if (object == null) {
            throw new RuntimeException("Object cannot be null");
        }

        if (object.getSpecialities() == null) {
            return super.save(object);
        }

        object.getSpecialities().forEach(speciality -> {
            if (speciality == null) {
                throw new RuntimeException("Pet type is required");
            }

            if (speciality.getId() == null) {
                Speciality savedSpeciality = specialityService.save(speciality);
                speciality.setId(savedSpeciality.getId());
            }
        });

        return super.save(object);
    }

    @Override
    public void delete(Vet object) {
        super.delete(object);
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }
}
