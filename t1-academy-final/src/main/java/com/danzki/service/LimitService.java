package com.danzki.service;

import com.danzki.model.Limit;
import com.danzki.repository.LimitRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LimitService {

    private final LimitRepository limitRepository;

    public Limit add(Long clientId, Double currentValue) {
        Limit limit = new Limit();
        limit.setClientId(clientId);
        limit.setCurrentValue(currentValue);

        return limitRepository.save(limit);
    }

    public Limit findById(Long id) {
        Optional<Limit> limitOptional = limitRepository.findById(id);
        return limitOptional.orElse(null);
    }

    public Limit findByClientId(Long clientId) {
        Optional<Limit> limitOptional = limitRepository.findByClientId(clientId);
        return limitOptional.orElse(null);
    }

    @Transactional
    public Limit update(Long clientId, Double currentValue) {
        Optional<Limit> limitOptional = limitRepository.findByClientId(clientId);
        if (limitOptional.isEmpty()) {
            return null;
        }
        Limit limit = limitOptional.get();
        limit.setCurrentValue(currentValue);
        return limitRepository.save(limit);
    }

    @Transactional
    public Integer deleteByClientId(Long clientId) {
        return limitRepository.deleteByClientId(clientId);
    }

    public List<Limit> getAll() {
        return limitRepository.findAll();
    }

    public Integer updateAllLimits(Double newAmount) {
        return limitRepository.updateAllCurrentAmount(newAmount);
    }

}
