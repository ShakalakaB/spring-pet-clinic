package aldora.springframework.springpetclinic.controllers;

import aldora.springframework.springpetclinic.model.Owner;
import aldora.springframework.springpetclinic.model.Pet;
import aldora.springframework.springpetclinic.model.PetType;
import aldora.springframework.springpetclinic.services.OwnerService;
import aldora.springframework.springpetclinic.services.PetTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Set;

@RequestMapping("/owners/{ownerId}")
@Controller
public class PetController {
    private final OwnerService ownerService;
    private final PetTypeService petTypeService;

    public PetController(OwnerService ownerService, PetTypeService petTypeService) {
        this.ownerService = ownerService;
        this.petTypeService = petTypeService;
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
        if (bindingResult.hasErrors()) {
            model.addAttribute("pet", pet);
            return "pets/createOrUpdatePetForm";
        }

        pet.setOwner(owner);
        owner.getPets().add(pet);
        Owner savedOwner = ownerService.save(owner);

        model.addAttribute("pet", Pet.builder().owner(owner).build());

        return "redirect:/owners/" + savedOwner.getId();
    }
}
