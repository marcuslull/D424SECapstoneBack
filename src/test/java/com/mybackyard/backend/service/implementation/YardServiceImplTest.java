package com.mybackyard.backend.service.implementation;

import com.mybackyard.backend.model.Yard;
import com.mybackyard.backend.repository.YardRepository;
import com.mybackyard.backend.service.interfaces.ApiKeyService;
import com.mybackyard.backend.service.interfaces.CompareAndUpdate;
import com.mybackyard.backend.service.interfaces.YardService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class YardServiceImplTest {

    private YardRepository yardRepository;
    private CompareAndUpdate compareAndUpdate;
    private static ApiKeyService apikeyService;
    private YardService yardService;
    private MockedStatic<SecurityContextHolder> securityContextHolder;
    private SecurityContext securityContext;
    private Authentication authentication;
    private Principal principal;

    @BeforeEach
    void setUp() {
        yardRepository = mock(YardRepository.class);
        compareAndUpdate = mock(CompareAndUpdate.class);
        apikeyService = mock(ApiKeyService.class);
        yardService = new YardServiceImpl(yardRepository, compareAndUpdate, apikeyService);

        // below 9 lines are to mock the principalId
        securityContextHolder = mockStatic(SecurityContextHolder.class);
        securityContext = mock(SecurityContext.class);
        authentication = mock(Authentication.class);
        principal = mock(Principal.class);
        securityContextHolder.when(() -> SecurityContextHolder.getContext()).thenReturn(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(principal);
        when(principal.toString()).thenReturn("1");
        when(apikeyService.matchKeyToUserId(anyString())).thenReturn(1L);
    }

    // need this in order to create a new security context for each test
    @AfterEach
    void tearDown() {
        securityContextHolder.close();
    }

    @Test
    void getAllYards() {
        Yard yard = new Yard();
        List<Yard> listOfYards = new ArrayList<>();
        listOfYards.add(yard);
        when(yardRepository.findYardsByYUserId(1L)).thenReturn(listOfYards);

        assertEquals(listOfYards, yardService.getAllYards());
    }

    @Test
    void getYardById() {
        long yardId = 1L;
        long userId = 1L;
        Yard yard = new Yard();
        yard.setYUserId(userId);
        Optional<Yard> optionalYard = Optional.of(yard);
        when(yardRepository.findYardByYardIdAndYUserId(yardId, userId)).thenReturn(optionalYard);

        assertEquals(optionalYard, yardService.getYardById(yardId));
    }

    @Test
    void saveYard() {
        Yard yard = new Yard();
        long yardId = 1L;
        yard.setYardId(yardId);
        when(yardRepository.save(yard)).thenReturn(yard);

        assertEquals(yardId, yardService.saveYard(yard));
    }

    @Test
    void deleteYardById() {
    }

    @Test
    void updateYard() throws IllegalAccessException {
        long yardId = 1L;
        long userId = 1L;
        Yard yard = new Yard();
        yard.setYUserId(userId);
        yard.setName("name");
        Optional<Yard> baseYard = Optional.of(yard);
        Yard patch = new Yard();
        patch.setName("New Name");
        Yard updatedYard = new Yard();
        updatedYard.setYUserId(userId);
        updatedYard.setName("New Name");
        when(yardRepository.findYardByYardIdAndYUserId(yardId, userId)).thenReturn(baseYard);
        when(compareAndUpdate.updateYard(baseYard.get(), patch)).thenReturn(updatedYard);
        when(yardRepository.save(updatedYard)).thenReturn(updatedYard);

        assertEquals(updatedYard, yardService.updateYard(String.valueOf(yardId), patch));
    }

    @Test
    void getAllYardsSearch() {
        String search = "Ro";
        long yardId = 1L;
        long userId = 1L;
        Yard yard = new Yard();
        yard.setYUserId(userId);
        yard.setName("Rover");
        List<Yard> searchedList = List.of(yard);
        when(yardRepository.findYardsByNameContainingAndYUserId(search, userId)).thenReturn(searchedList);

        assertEquals(searchedList, yardService.getAllYardsSearch(search));
    }
}