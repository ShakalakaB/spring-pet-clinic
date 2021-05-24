package aldora.springframework.springpetclinic.controllers;

import aldora.springframework.springpetclinic.model.Pet;
import aldora.springframework.springpetclinic.model.Visit;
import aldora.springframework.springpetclinic.services.PetService;
import aldora.springframework.springpetclinic.services.VisitService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/owners/{orderId}/pets/{petId}")
public class VisitController {
    private final PetService petService;
    private final VisitService visitService;

    public VisitController(PetService petService, VisitService visitService) {
        this.petService = petService;
        this.visitService = visitService;
    }

    @ModelAttribute("pet")
    public Pet findPet(@PathVariable Long petId) {
        Pet pet = petService.findById(petId);

        if (pet == null) {
            throw new RuntimeException("Pet Not Found. id: " + petId);
        }

        return pet;
    }

    @GetMapping("/visits/new")
    public String initCreateForm(Model model) {
        model.addAttribute("visit", Visit.builder().build());

        return "pets/createOrUpdateVisitForm";
    }

    @PostMapping("/visits/new")
    public String ProcessCreateForm(@PathVariable Long orderId, @ModelAttribute Pet pet, Visit visit, Model model, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("visit", visit);
            return "pets/createOrUpdateVisitForm";
        }
        visit.setPet(pet);
        visitService.save(visit);

        return "redirect:/owners/" + orderId;
    }
}
