import { Component } from '@angular/core';

import { SampleService } from './shared/services/sample.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  hello = '';

  constructor(private sampleService: SampleService) {}

  public getSampleData(): void {
    this.sampleService.getHelloWorld()
      .subscribe((data: any) => {
        this.hello = data;
      }, (err) => {
        console.log(err);
      });
  }
}
