import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class NewsFeedService {

  private graphqlUrl = 'http://localhost:8080/graphql';

  constructor(private http: HttpClient) { }

  getNewsFeed(): Observable<any> {
    const query = {
      query: `
        query {
          getNewsFeed {
            id
            title
            url
            timePublished
            summary
            source
            sentimentScore
            sentimentLabel
          }
        }
      `
    };

    return this.http.post(this.graphqlUrl, query);
  }
}
