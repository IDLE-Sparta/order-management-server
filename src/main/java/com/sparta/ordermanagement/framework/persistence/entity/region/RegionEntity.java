package com.sparta.ordermanagement.framework.persistence.entity.region;

import com.sparta.ordermanagement.application.domain.region.Region;
import com.sparta.ordermanagement.framework.persistence.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "p_region")
public class RegionEntity extends BaseEntity {

    @Id
    @Column(name = "region_id")
    @GeneratedValue(strategy= GenerationType.UUID)
    private String id;

    @Column(nullable = false, columnDefinition = "varchar(255)")
    private String regionName;

    public RegionEntity(String regionName) {
        this.regionName = regionName;
    }

    public static RegionEntity from(String regionName) {
        return new RegionEntity(regionName);
    }

    public Region toDomain() {
        return new Region(
            regionName
        );
    }
}
