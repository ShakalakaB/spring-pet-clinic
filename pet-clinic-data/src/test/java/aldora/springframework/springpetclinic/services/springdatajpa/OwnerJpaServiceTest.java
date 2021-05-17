package aldora.springframework.springpetclinic.services.springdatajpa;

import aldora.springframework.springpetclinic.model.Owner;
import aldora.springframework.springpetclinic.repositories.OwnerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OwnerJpaServiceTest {

    public static final String LAST_NAME = "Smith";
    public static final long ID = 1L;
    @Mock
    OwnerRepository ownerRepository;

    @InjectMocks
    OwnerJpaService ownerJpaService;

    Owner savedOwner;

    @BeforeEach
    void setUp() {
        savedOwner = Owner.builder().id(ID).lastName(LAST_NAME).build();
    }

    @Test
    void findAll() {
        Set<Owner> owners = new HashSet<>();
        owners.add(Owner.builder().id(1L).build());
        owners.add(Owner.builder().id(2L).build());

        when(ownerRepository.findAll()).thenReturn(owners);

        assertEquals(2, ownerJpaService.findAll().size());
        verify(ownerRepository).findAll();
    }

    @Test
    void findById() {
        when(ownerRepository.findById(anyLong())).thenReturn(Optional.of(savedOwner));
        Owner owner = ownerJpaService.findById(anyLong());

        assertNotNull(owner);
    }

    @Test
    void findByIdNotFound() {
        when(ownerRepository.findById(anyLong())).thenReturn(Optional.empty());
        Owner owner = ownerJpaService.findById(anyLong());

        assertNull(owner);
    }

    @Test
    void save() {
        when(ownerRepository.save(any())).thenReturn(savedOwner);
        Owner owner = ownerJpaService.save(any());

        assertEquals(ID, owner.getId());
        verify(ownerRepository).save(any());
    }

    @Test
    void delete() {
        ownerJpaService.delete(any());
        verify(ownerRepository).delete(any());
    }

    @Test
    void deleteById() {
        ownerJpaService.deleteById(anyLong());
        verify(ownerRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void findByLastName() {
        when(ownerRepository.findByLastName(anyString())).thenReturn(savedOwner);
        Owner owner = ownerJpaService.findByLastName(LAST_NAME);
        assertEquals(LAST_NAME, owner.getLastName());
    }
}