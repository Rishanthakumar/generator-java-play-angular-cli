import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { URL_CONST } from '../config/url.constants';
import 'rxjs/Rx';

@Injectable()
export class SampleService {

  constructor(private http: Http) {}

  getHelloWorld() {
    return this.http.get(URL_CONST.URL_PREFIX + 'api/v1/hello-world')
      .map((response: Response) => response.text());
  }

}
