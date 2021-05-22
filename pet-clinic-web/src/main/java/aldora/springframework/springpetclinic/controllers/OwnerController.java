package aldora.springframework.springpetclinic.controllers;

import aldora.springframework.springpetclinic.model.Owner;
import aldora.springframework.springpetclinic.services.OwnerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/owners")
@Controller
public class OwnerController {
    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @GetMapping("/{ownerId}")
    public ModelAndView showOwner(@PathVariable String ownerId) {
        ModelAndView modelAndView = new ModelAndView("owners/ownerDetails");
        modelAndView.addObject(ownerService.findById(Long.valueOf(ownerId)));

        return modelAndView;
    }

    @GetMapping("/find")
    public String findOwners(Model model) {
        Owner owner = Owner.builder().build();
        model.addAttribute("owner", owner);

        return "owners/findOwners";
    }

    @GetMapping("")
    public String processFindForm(Owner owner, Model model, BindingResult bindResult) {
        if (owner.getLastName() == null) {
            owner.setLastName("");
        }

        List<Owner> ownerList = ownerService.findAllByLastNameLike("%" + owner.getLastName() + "%");

        if (ownerList.isEmpty()) {
            bindResult.rejectValue("lastName", "notFound", "not found");
            return "owners/findOwners";
        } else if (ownerList.size() == 1) {
            Owner resultOwner = ownerList.get(0);

            return "redirect:/owners/" + resultOwner.getId();
        } else {
            model.addAttribute("owners", ownerList);

            return "owners/ownersList";
        }
    }

    @GetMapping("/new")
    public String initCreateForm(Model model) {
        model.addAttribute("owner", Owner.builder().build());

        return "owners/createOrUpdateOwnerForm";
    }

    @PostMapping("/new")
    public String processCreateForm(@Valid Owner owner, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "owners/createOrUpdateOwnerForm";
        }

        Owner savedOwner = ownerService.save(owner);

        return "redirect:/owners/" + savedOwner.getId();
    }

    @GetMapping("/{ownerId}/edit")
    public String initUpdateForm(@PathVariable String ownerId, Model model) {
        model.addAttribute("owner", ownerService.findById(Long.valueOf(ownerId)));

        return "owners/createOrUpdateOwnerForm";
    }

    @PostMapping("/{ownerId}/edit")
    public String processUpdateForm(@PathVariable String ownerId, @Valid Owner owner, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "owners/createOrUpdateOwnerForm";
        }

        owner.setId(Long.valueOf(ownerId));
        Owner savedOwner = ownerService.save(owner);

        return "redirect:/owners/" + savedOwner.getId();
    }
}
