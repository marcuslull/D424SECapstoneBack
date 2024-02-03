package com.mybackyard.backend.repository;

import com.mybackyard.backend.model.Yard;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface YardRepository  extends CrudRepository<Yard, Long> {
    List<Yard> findYardsByYUserId(long principal);
    Optional<Yard> findYardByYardIdAndYUserId(long yardId, long principal);
    void deleteYardByYardIdAndYUserId(long yardId, long principal);
    List<Yard> findYardsByNameContainingAndYUserId(String query, long principal);
}
