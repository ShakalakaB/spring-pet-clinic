package aldora.springframework.springpetclinic.bootstrap;

import aldora.springframework.springpetclinic.model.Owner;
import aldora.springframework.springpetclinic.model.Vet;
import aldora.springframework.springpetclinic.services.OwnerService;
import aldora.springframework.springpetclinic.services.VetService;
import aldora.springframework.springpetclinic.services.map.OwnerServiceMap;
import aldora.springframework.springpetclinic.services.map.VetServiceMap;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {
    private final OwnerService ownerService;

    private final VetService vetService;

    public DataLoader(OwnerService ownerService, VetService vetService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
    }

    @Override
    public void run(String... args) throws Exception {
        Owner owner1 = new Owner();
        owner1.setId(1L);
        owner1.setFirstName("Santa");
        owner1.setLastName("Uno");
        ownerService.save(owner1);

        Owner owner2 = new Owner();
        owner2.setId(2L);
        owner2.setFirstName("Jack");
        owner2.setLastName("Ma");
        ownerService.save(owner2);
        System.out.println("owners created!");

        Vet vet1 = new Vet();
        vet1.setId(1L);
        vet1.setFirstName("vet1");
        vet1.setLastName("com1");
        vetService.save(vet1);

        Vet vet2 = new Vet();
        vet2.setId(1L);
        vet2.setFirstName("vet2");
        vet2.setLastName("com2");
        vetService.save(vet2);

        System.out.println("vets created!");
    }
}
