package com.cencoe.cencoe.models.repository;

import com.cencoe.cencoe.models.entity.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICampaignRepository extends JpaRepository<Campaign, Long> {
}
