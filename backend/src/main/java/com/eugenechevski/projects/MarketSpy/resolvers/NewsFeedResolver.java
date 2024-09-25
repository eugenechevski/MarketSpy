package com.eugenechevski.projects.MarketSpy.resolvers;

import com.eugenechevski.projects.MarketSpy.models.NewsFeed;
import com.eugenechevski.projects.MarketSpy.repositories.NewsFeedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class NewsFeedResolver {
    @Autowired
    private NewsFeedRepository newsFeedRepository;
    
    @QueryMapping
    public List<NewsFeed> getNewsFeed() {
        return newsFeedRepository.findAll();
    }    
}

