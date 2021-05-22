package aldora.springframework.springpetclinic.bootstrap;

import aldora.springframework.springpetclinic.model.*;
import aldora.springframework.springpetclinic.services.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {
    private final OwnerService ownerService;
    private final VetService vetService;
    private final PetTypeService petTypeService;
    private final SpecialityService specialityService;
    private final VisitService visitService;

    public DataLoader(OwnerService ownerService, VetService vetService, PetTypeService petTypeService,
                      SpecialityService specialityService,
                      VisitService visitService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
        this.petTypeService = petTypeService;
        this.specialityService = specialityService;
        this.visitService = visitService;
    }

    @Override
    public void run(String... args) throws Exception {
        int count = petTypeService.findAll().size();

        if (count == 0) {
            loadData();
        }
    }

    private void loadData() {
        Speciality radiology = new Speciality();
        radiology.setDescription("radiology");
        Speciality savedRadiology = specialityService.save(radiology);

        Speciality dentist = new Speciality();
        dentist.setDescription("dentist");
        Speciality savedDentist = specialityService.save(radiology);

        PetType dogPetType = new PetType();
        dogPetType.setName("Dog");
        PetType savedDogPetType = petTypeService.save(dogPetType);

        PetType catPetType = new PetType();
        catPetType.setName("Cat");
        PetType savedCatPetType = petTypeService.save(catPetType);

        Owner owner1 = new Owner();
        owner1.setFirstName("Santa");
        owner1.setLastName("Uno");
        owner1.setAddress("Avenue1");
        owner1.setCity("HK");
        owner1.setTelephone("123123");

        Pet petMik = new Pet();
        petMik.setName("Mika");
        petMik.setPetType(savedDogPetType);
        petMik.setBirthDate(LocalDate.now());
        petMik.setOwner(owner1);

        owner1.getPets().add(petMik);
        ownerService.save(owner1);

        Visit visit = new Visit();
        visit.setDate(LocalDate.now());
        visit.setDescription("Kitty");
        visit.setPet(petMik);
        visitService.save(visit);

        Owner owner2 = new Owner();
        owner2.setFirstName("Jack");
        owner2.setLastName("Unoma");
        owner2.setAddress("avenue2");
        owner2.setCity("Macau");
        owner2.setTelephone("312121");

        Pet petkiya = new Pet();
        petkiya.setName("Kika");
        petkiya.setPetType(savedCatPetType);
        petkiya.setBirthDate(LocalDate.now());
        petkiya.setOwner(owner2);

        owner2.getPets().add(petkiya);
        ownerService.save(owner2);

        System.out.println("owners created!");

        Vet vet1 = new Vet();
        vet1.setFirstName("vet1");
        vet1.setLastName("com1");
        vet1.getSpecialities().add(savedRadiology);
        vetService.save(vet1);

        Vet vet2 = new Vet();
        vet2.setFirstName("vet2");
        vet2.setLastName("com2");
        vet2.getSpecialities().add(savedDentist);
        vetService.save(vet2);

        System.out.println("vets created!");
    }
}
