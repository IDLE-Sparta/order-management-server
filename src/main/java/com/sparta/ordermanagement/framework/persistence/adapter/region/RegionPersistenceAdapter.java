package com.sparta.ordermanagement.framework.persistence.adapter.region;

import com.sparta.ordermanagement.application.domain.region.Region;
import com.sparta.ordermanagement.application.output.region.RegionOutputPort;
import com.sparta.ordermanagement.framework.persistence.entity.region.RegionEntity;
import com.sparta.ordermanagement.framework.persistence.repository.region.RegionRepository;
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
