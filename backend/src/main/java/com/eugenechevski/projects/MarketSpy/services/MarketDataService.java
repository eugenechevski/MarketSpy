package com.eugenechevski.projects.MarketSpy.services;

import com.eugenechevski.projects.MarketSpy.models.MarketData;
import com.eugenechevski.projects.MarketSpy.repositories.MarketDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MarketDataService {
    @Autowired
    private MarketDataRepository marketDataRepository;

    public MarketData getMarketData(Long id) {
        return marketDataRepository.findById(id).orElse(null);
    }
}
