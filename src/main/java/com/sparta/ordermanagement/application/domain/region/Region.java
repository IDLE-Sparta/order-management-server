package com.sparta.ordermanagement.application.domain.region;

public class Region {

    private Long id;

    private String regionName;

    public Region(Long id, String regionName) {
        this.id = id;
        this.regionName = regionName;
    }

    public Region(String regionName) {
        this.regionName = regionName;
    }

    public Long getId() {
        return id;
    }

    public String getRegionName() {
        return regionName;
    }
}
