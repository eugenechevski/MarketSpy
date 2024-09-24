package com.eugenechevski.projects.MarketSpy.repositories;

import com.eugenechevski.projects.MarketSpy.models.MarketData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarketDataRepository extends JpaRepository<MarketData, Long> {
    
}
