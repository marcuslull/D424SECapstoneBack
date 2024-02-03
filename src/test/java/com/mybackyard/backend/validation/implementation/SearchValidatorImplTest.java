package com.mybackyard.backend.validation.implementation;

import com.mybackyard.backend.validation.interfaces.SearchValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SearchValidatorImplTest {
    SearchValidator searchValidator;

    @BeforeEach
    void setUp() {
        searchValidator = new SearchValidatorImpl();
    }

    @Test
    void isValidNameSearch() {
        String query1 = null;
        String query2 = "asdf";
        String query3 = "'";
        String query4 = "";

        assertTrue(searchValidator.isValidNameSearch(query1));
        assertTrue(searchValidator.isValidNameSearch(query2));
        assertFalse(searchValidator.isValidNameSearch(query3));
        assertFalse(searchValidator.isValidNameSearch(query4));
    }
}