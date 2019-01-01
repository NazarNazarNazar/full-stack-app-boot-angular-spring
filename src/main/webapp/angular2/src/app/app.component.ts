import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup} from '@angular/forms';
import {Http, RequestOptions, Response} from "@angular/http";
import {map} from "rxjs/operators";
import {Observable} from "rxjs/internal/Observable";
import {Headers} from "@angular/http";
import {HttpHeaders} from "@angular/common/http";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  constructor(private http: Http) {
  }

  private request: ReserveRoomRequest;
  private baseUrl: string = 'http://localhost:8080';
  private getUrl: string = this.baseUrl + '/room/reservation/v1/';
  private postUrl: string = this.baseUrl + '/room/reservation/v1';
  public submitted: boolean;
  roomsearch: FormGroup;
  rooms: Room[];
  currentCheckInVal: string;
  currentCheckOutVal: string;

  ngOnInit() {
    this.roomsearch = new FormGroup({
      checkin: new FormControl(''),
      checkout: new FormControl('')
    });

    const roomsearchValueChanges$ = this.roomsearch.valueChanges;

    roomsearchValueChanges$.subscribe(valChange => {
      this.currentCheckInVal = valChange.checkin;
      this.currentCheckOutVal = valChange.checkout;
    });

  }

  onSubmit({value, valid}: { value: Roomsearch, valid: boolean }) {

    this.getAll()
      .subscribe(
        rooms => this.rooms = rooms,
        err => {
          // Log errors if any
          console.log(err);
        });
  }

  reserveRoom(value: string) {
    this.request = new ReserveRoomRequest(value, this.currentCheckInVal, this.currentCheckOutVal);
    this.createReservation(this.request);
  }


  createReservation(body: ReserveRoomRequest) {
    let bodyString = JSON.stringify(body);
    let headers = new Headers({'Content-Type': 'application/json'});
    let option = new RequestOptions({headers: headers});

    this.http.post(this.postUrl, bodyString, option)
      .subscribe(res => console.log(res));
  }


  getAll(): Observable<Room[]> {


    return this.http
      .get(this.getUrl + '?checkin='
        + this.currentCheckInVal + '&checkout=' + this.currentCheckOutVal)
      .pipe(map((res: Response) => res.json() as Room[]));
  }
}

export interface Roomsearch {
  checkin: string;
  checkout: string;
}

export interface Room {
  id: string;
  roomNumber: string;
  price: string;
  links: string;
}

export class ReserveRoomRequest {
  roomId: string;
  checkin: string;
  checkout: string;

  constructor(roomId: string, checkin: string, checkout: string) {
    this.roomId = roomId;
    this.checkin = checkin;
    this.checkout = checkout;
  }
}

