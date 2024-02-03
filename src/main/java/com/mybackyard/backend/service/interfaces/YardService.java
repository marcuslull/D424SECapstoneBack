package com.mybackyard.backend.service.interfaces;

import com.mybackyard.backend.model.Yard;

import java.util.List;
import java.util.Optional;

public interface YardService {
    List<Yard> getAllYards();
    Optional<Yard> getYardById(long yardId);
    long saveYard(Yard yard);
    void deleteYardById(long id);
    Yard updateYard(String id, Yard yard);
    List<Yard> getAllYardsSearch(String query);
}
