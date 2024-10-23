import { Component, OnInit } from '@angular/core';
import { Apollo, gql } from 'apollo-angular';

@Component({
  selector: 'app-news-feed',
  standalone: true,
  imports: [],
  templateUrl: './news-feed.component.html',
  styleUrl: './news-feed.component.scss',
})
export class NewsFeedComponent implements OnInit {
  newsFeed: any[] = [];

  constructor(private readonly apollo: Apollo) {}

  ngOnInit(): void {
    this.apollo
      .watchQuery({
        query: gql`
          {
            newsFeed {
              id
              title
              url
              timePublished
              summary
              source
              category
              bannerImage
              sentimentScore
              sentimentLabel
            }
          }
        `,
      })
      .valueChanges.subscribe((result: any) => {
        this.newsFeed = result.data.newsFeed;
      });
  }
}
