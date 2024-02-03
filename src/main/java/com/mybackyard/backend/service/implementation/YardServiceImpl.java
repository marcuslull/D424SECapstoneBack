package com.mybackyard.backend.service.implementation;

import com.mybackyard.backend.model.Yard;
import com.mybackyard.backend.repository.YardRepository;
import com.mybackyard.backend.service.interfaces.ApiKeyService;
import com.mybackyard.backend.service.interfaces.CompareAndUpdate;
import com.mybackyard.backend.service.interfaces.YardService;
import jakarta.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class YardServiceImpl implements YardService {

    private final YardRepository yardRepository;
    private final CompareAndUpdate compareAndUpdate;
    private final ApiKeyService apiKeyService;


    public YardServiceImpl(YardRepository yardRepository, CompareAndUpdate compareAndUpdate, ApiKeyService apiKeyService) {
        this.yardRepository = yardRepository;
        this.compareAndUpdate = compareAndUpdate;
        this.apiKeyService = apiKeyService;
    }

    @Override
    public List<Yard> getAllYards() {
        long principalId = apiKeyService.matchKeyToUserId(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        return yardRepository.findYardsByYUserId(principalId);
    }

    @Override
    public Optional<Yard> getYardById(long yardId) {
        long principalId = apiKeyService.matchKeyToUserId(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        return yardRepository.findYardByYardIdAndYUserId(yardId, principalId);
    }

    @Override
    public long saveYard(Yard yard) {
        long principalId = apiKeyService.matchKeyToUserId(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        yard.setYUserId(principalId);
        return yardRepository.save(yard).getYardId();
    }

    @Override
    @Transactional
    public void deleteYardById(long id) {
        long principalId = apiKeyService.matchKeyToUserId(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        yardRepository.deleteYardByYardIdAndYUserId(id, principalId);
    }

    @Override
    @Transactional
    public Yard updateYard(String id, Yard yard) {
        long principalId = apiKeyService.matchKeyToUserId(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        yard.setYardId(Long.parseLong(id));
        Yard baseYard = yardRepository.findYardByYardIdAndYUserId(Long.valueOf(id), principalId)
                .orElseThrow(() -> new NoSuchElementException("Yard not found with id: " + id));
        try {
            return yardRepository.save(compareAndUpdate.updateYard(baseYard, yard));
        }
        catch (Exception e) {
            // TODO: figure out what to do with these exceptions - v.N
        }
        return baseYard;
    }

    @Override
    public List<Yard> getAllYardsSearch(String query) {
        long principalId = apiKeyService.matchKeyToUserId(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        return yardRepository.findYardsByNameContainingAndYUserId(query, principalId);
    }
}
