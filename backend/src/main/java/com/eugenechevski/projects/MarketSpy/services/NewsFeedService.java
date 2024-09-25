package com.eugenechevski.projects.MarketSpy.services;

import com.eugenechevski.projects.MarketSpy.models.NewsFeed;
import com.eugenechevski.projects.MarketSpy.repositories.NewsFeedRepository;
import io.github.cdimascio.dotenv.Dotenv;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class NewsFeedService {

    @Autowired
    private NewsFeedRepository newsFeedRepository;

    @Autowired
    private Dotenv dotenv;

    private final String baseUrl = "https://www.alphavantage.co/query?function=NEWS_SENTIMENT&apikey=";

    public void fetchAndSaveNewsSentimentData() {
        String apiKey = dotenv.get("ALPHA_VANTAGE_API_KEY");
        String url = baseUrl + apiKey;

        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);

        JSONObject jsonData = new JSONObject(response);

        // Extract the "feed" array
        JSONArray feedArray = jsonData.getJSONArray("feed");

        for (int i = 0; i < feedArray.length(); i++) {
            JSONObject feedItem = feedArray.getJSONObject(i);

            NewsFeed newsFeed = new NewsFeed();
            newsFeed.setTitle(feedItem.getString("title"));
            newsFeed.setUrl(feedItem.getString("url"));
            newsFeed.setTimePublished(LocalDateTime.parse(feedItem.getString("time_published"),
                    DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss")));
            newsFeed.setSummary(feedItem.getString("summary"));
            newsFeed.setSource(feedItem.getString("source"));
            newsFeed.setCategory(feedItem.optString("category_within_source", "n/a"));
            newsFeed.setBannerImage(feedItem.optString("banner_image", null));
            newsFeed.setSentimentScore(feedItem.getDouble("overall_sentiment_score"));
            newsFeed.setSentimentLabel(feedItem.getString("overall_sentiment_label"));

            // Save to PostgreSQL
            newsFeedRepository.save(newsFeed);
        }
    }
}
