package com.sparta.ordermanagement.application.output;

import com.sparta.ordermanagement.application.domain.region.Region;

public interface RegionOutputPort {

    Region saveRegion(String regionName);
}
