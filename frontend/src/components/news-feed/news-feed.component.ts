import { Component } from '@angular/core';

import { Component, OnInit } from '@angular/core';
import { NewsFeedService } from '../../services/news-feed.service';

@Component({
  selector: 'app-news-feed',
  standalone: true,
  imports: [],
  templateUrl: './news-feed.component.html',
  styleUrl: './news-feed.component.scss'
})

export class NewsFeedComponent implements OnInit {

  newsFeed: any[] = [];

  constructor(private newsFeedService: NewsFeedService) { }

  ngOnInit(): void {
    this.newsFeedService.getNewsFeed().subscribe((response: { data: { getNewsFeed: any[]; }; }) => {
      this.newsFeed = response.data.getNewsFeed;
    });
  }
}
