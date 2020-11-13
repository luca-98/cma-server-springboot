package com.github.cmateam.cmaserver.service;

import java.util.UUID;

import com.github.cmateam.cmaserver.repository.OrdinalNumberRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrdinalNumberServiceImpl {

    private OrdinalNumberRepository ordinalNumberRepository;

    @Autowired
    public OrdinalNumberServiceImpl(OrdinalNumberRepository ordinalNumberRepository) {
        this.ordinalNumberRepository = ordinalNumberRepository;
    }

    public Short getOrdinalByRoom(UUID roomId) {
        Short maxOrdinal = ordinalNumberRepository.getMaxOrdinalNumberOfRoom(roomId);
        if (maxOrdinal != null) {
            return ++maxOrdinal;
        }
        return 1;
    }
}
