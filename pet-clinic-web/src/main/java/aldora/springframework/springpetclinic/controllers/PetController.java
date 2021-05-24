package aldora.springframework.springpetclinic.controllers;

import aldora.springframework.springpetclinic.model.Owner;
import aldora.springframework.springpetclinic.model.Pet;
import aldora.springframework.springpetclinic.model.PetType;
import aldora.springframework.springpetclinic.services.OwnerService;
import aldora.springframework.springpetclinic.services.PetService;
import aldora.springframework.springpetclinic.services.PetTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collection;

@RequestMapping("/owners/{ownerId}")
@Controller
public class PetController {
    private final OwnerService ownerService;
    private final PetTypeService petTypeService;
    private final PetService petService;

    public PetController(OwnerService ownerService, PetTypeService petTypeService, PetService petService) {
        this.ownerService = ownerService;
        this.petTypeService = petTypeService;
        this.petService = petService;
    }

    @ModelAttribute("owner")
    public Owner findOwner(@PathVariable Long ownerId) {
        return ownerService.findById(ownerId);
    }

    @ModelAttribute("petTypes")
    public Collection<PetType> findAllPetType() {
        return petTypeService.findAll();
    }

    @InitBinder("owner")
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @GetMapping("/pets/new")
    public String initCreateForm(@ModelAttribute("owner") Owner owner, Model model) {
        model.addAttribute("pet", Pet.builder().owner(owner).build());

        return "pets/createOrUpdatePetForm";
    }

    @PostMapping("/pets/new")
    public String processCreateForm(@ModelAttribute("owner") Owner owner, Pet pet, Model model, BindingResult bindingResult) {
        if (StringUtils.hasLength(pet.getName()) && pet.isNew() && owner.getPet(pet.getName(), true) != null) {
            bindingResult.rejectValue("name", "duplicate", "already exists");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("pet", pet);
            return "pets/createOrUpdatePetForm";
        }

        pet.setOwner(owner);
        owner.getPets().add(pet);
        Pet savedPet = petService.save(pet);

        model.addAttribute("pet", savedPet);

        return "redirect:/owners/" + owner.getId();
    }

    @GetMapping("/pets/{petId}/edit")
    public String initUpdateForm(@ModelAttribute("owner") Owner owner, @PathVariable Long petId, Model model) {
        Pet pet = petService.findById(petId);

        if (pet == null) {
            throw new RuntimeException("Pet Not Found. id: " + petId);
        }

        model.addAttribute("pet", pet);

        return "pets/createOrUpdatePetForm";
    }

    @PostMapping("/pets/{petId}/edit")
    public String processUpdateForm(@ModelAttribute Owner owner, Pet pet, Model model, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            pet.setOwner(owner);
            model.addAttribute("pet", pet);
            return "pets/createOrUpdatePetForm";
        } else {
//            owner.getPets().add(pet);
            pet.setOwner(owner);
            petService.save(pet);
            return "redirect:/owners/" + owner.getId();
        }
    }
}
