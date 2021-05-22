package aldora.springframework.springpetclinic.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "owners")
public class Owner extends Person{
    private String address;
    private String city;
    private String telephone;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private Set<Pet> pets = new HashSet<>();

    @Builder
    public Owner(Long id, String firstName, String lastName, String address, String city, String telephone, Set<Pet> pets) {
        super(id, firstName, lastName);
        this.address = address;
        this.city = city;
        this.telephone = telephone;
        this.pets = pets;
    }

    public Pet getPet(String petName, boolean ignoreNew) {
        petName = petName.toLowerCase();

        for (Pet pet : pets) {
            if (!ignoreNew || !pet.isNew()) {
                String compareName = pet.getName();
                compareName = compareName.toLowerCase();
                if (compareName.equals(petName)) {
                    return pet;
                }
            }
        }

        return null;
    }
}
