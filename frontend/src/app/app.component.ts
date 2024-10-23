import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { NewsFeedComponent } from '../components/news-feed/news-feed.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, NewsFeedComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'frontend';
}
