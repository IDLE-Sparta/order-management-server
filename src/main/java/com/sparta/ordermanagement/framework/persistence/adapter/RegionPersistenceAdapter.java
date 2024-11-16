package com.sparta.ordermanagement.framework.persistence.adapter;

import com.sparta.ordermanagement.application.domain.region.Region;
import com.sparta.ordermanagement.application.output.RegionOutputPort;
import com.sparta.ordermanagement.framework.persistence.entity.region.RegionEntity;
import com.sparta.ordermanagement.framework.persistence.repository.RegionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RegionPersistenceAdapter implements RegionOutputPort {

    private final RegionRepository regionRepository;

    @Override
    public Region saveRegion(String regionName) {
        return regionRepository.save(RegionEntity.from(regionName)).toDomain();
    }
}
