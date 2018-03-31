import { Injectable } from '@angular/core';
import { URL_CONST } from '../config/url.constants';
import 'rxjs/Rx';
import {HttpClient, HttpResponse} from "@angular/common/http";

@Injectable()
export class SampleService {

  constructor(private http: HttpClient) {}

  getHelloWorld() {
    return this.http.get(URL_CONST.URL_PREFIX + 'api/v1/hello-world', {responseType: 'text'})
  }

}
