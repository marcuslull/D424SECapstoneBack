package com.mybackyard.backend.validation.implementation;

import com.mybackyard.backend.validation.interfaces.SearchValidator;
import org.springframework.stereotype.Component;

@Component
public class SearchValidatorImpl implements SearchValidator {
    @Override
    public boolean isValidNameSearch(String query) {
        if (query == null) {
            return true;
        }
        return query.matches("^[a-zA-Z0-9 ]+( ?[a-zA-Z0-9 -./?!$%&*(),;:@#_]+)*$");
    }
}
