package com.eugenechevski.projects.MarketSpy.resolvers;

import com.eugenechevski.projects.MarketSpy.models.MarketData;
import com.eugenechevski.projects.MarketSpy.services.MarketDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class MarketDataResolver {
    @Autowired
    private MarketDataService marketDataService;

    @QueryMapping
    public MarketData getMarketData(@Argument Long id) {
        return marketDataService.getMarketData(id);
    }
}
