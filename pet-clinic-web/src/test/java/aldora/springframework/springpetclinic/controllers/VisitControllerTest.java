package aldora.springframework.springpetclinic.controllers;

import aldora.springframework.springpetclinic.model.Pet;
import aldora.springframework.springpetclinic.services.PetService;
import aldora.springframework.springpetclinic.services.VisitService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.ModelAttribute;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

class VisitControllerTest {
    @Mock
    PetService petService;

    @Mock
    VisitService visitService;

    MockMvc mockMvc;

    VisitController visitController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        visitController = new VisitController(petService, visitService);
        mockMvc = MockMvcBuilders.standaloneSetup(visitController).build();
    }

    @Test
    void processCreateForm() throws Exception {
        Pet pet = Pet.builder().id(1L).build();
        when(petService.findById(anyLong())).thenReturn(pet);

        mockMvc.perform(post("/owners/1/pets/1/visits/new"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/1"))
                .andExpect(model().size(2));
    }
}