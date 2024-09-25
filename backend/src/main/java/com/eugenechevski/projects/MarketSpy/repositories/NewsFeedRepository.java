package com.eugenechevski.projects.MarketSpy.repositories;

import com.eugenechevski.projects.MarketSpy.models.NewsFeed;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsFeedRepository extends JpaRepository<NewsFeed, Long> {
    
}