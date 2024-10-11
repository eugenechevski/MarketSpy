package com.eugenechevski.projects.MarketSpy.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.eugenechevski.projects.MarketSpy.models.NewsFeed;
import com.eugenechevski.projects.MarketSpy.repositories.NewsFeedRepository;

import io.github.cdimascio.dotenv.Dotenv;

@Service
public class NewsFeedService {

    private static final Logger LOGGER = Logger.getLogger(NewsFeedService.class.getName());

    private final NewsFeedRepository newsFeedRepository;
    private final Dotenv dotenv;
    private final String baseUrl = "https://www.alphavantage.co/query?function=NEWS_SENTIMENT&apikey=";

    public NewsFeedService(NewsFeedRepository newsFeedRepository, Dotenv dotenv) {
        this.newsFeedRepository = newsFeedRepository;
        this.dotenv = dotenv;
    }

    public void fetchAndSaveNewsSentimentData() {
        String apiKey = dotenv.get("ALPHA_VANTAGE_API_KEY");
        if (apiKey == null || apiKey.isEmpty()) {
            LOGGER.severe("API Key for Alpha Vantage is not set. Please check your environment configuration.");
            return;
        }

        String url = baseUrl + apiKey;
        RestTemplate restTemplate = new RestTemplate();

        try {
            LOGGER.info("Fetching news sentiment data from Alpha Vantage API");
            String response = restTemplate.getForObject(url, String.class);
            JSONObject jsonData = new JSONObject(response);
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

                newsFeedRepository.save(newsFeed);
                LOGGER.info("Saved news feed item: " + newsFeed.getTitle());
            }

        } catch (Exception e) {
            LOGGER.severe("An error occurred while fetching or saving news sentiment data: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
