import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { User } from '../models/user.model';
import { APIbaseURL } from '../settings';


@Injectable({
  providedIn: 'root'
})
export class UserService {

  public userList: User[] = [];
  user!: User;

  refreshList() {
    this.getAll().subscribe(
      (list) => {
        this.userList = list
      }
    )
  }

  constructor(private http: HttpClient) { 
    this.refreshList()
  }

  ngOnInit() {
    this.refreshList()
  }

  private baseURL = APIbaseURL;

  getUserByID(id: number) {
    return this.http.get<User>(`${this.baseURL}/users/${id}`);
  }

  getUserByUsername(username: string): Observable<User> {
    return this.http.get<User>(`${this.baseURL}/users/findByUsername/${username}`);
  }

  getAll(): Observable<User[]> {
    return this.http.get<User[]>(`${this.baseURL}/users`);
  }

  create(data: User): Observable<User> {
    return this.http.post<User>(`${this.baseURL}/users`, data);
  }

  update(id: number, data: User): Observable<User> {
    data.id = id;
    return this.http.put<User>(`${this.baseURL}/users/${id}`, data);
  }

  getUserAddress() {
    return this.http.get<User>(`${this.baseURL}/users/address`);
  }

  getUserById2(id: number | undefined): Observable<User> {  //creat de IonB
    return this.http.get<User>(`${this.baseURL}/users/${id}`);
  }

  delete(id?: number): Observable<void> {
    if (!id) {
      return of();
    }

    console.log(id)
    return this.http.delete<void>(`${this.baseURL}/users/${id}`);
  }
}
