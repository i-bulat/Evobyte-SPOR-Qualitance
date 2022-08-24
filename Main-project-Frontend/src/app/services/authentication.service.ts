import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { BehaviorSubject, Observable } from 'rxjs';
import { User } from '../models/user.model';
import { UserInfo, UserLoginInfo } from '../models/userInfo.model';
import { map } from 'rxjs/operators';
import { APIbaseURL, APIbaseURLcart } from '../settings';
import { UserService } from './user.service';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  loggedInUser: User = {}

  constructor(private httpClient: HttpClient, private router: Router) {
    if(this.isAuthenticated())
      this.loggedInUser = JSON.parse(localStorage.getItem('currentUser')!)
  }

  isAuthenticated(): boolean {
    return localStorage.getItem('token') !== null;
  }

  register(data: UserInfo): Observable<UserInfo> {
    return this.httpClient.post<UserInfo>(`${APIbaseURL}/auth/register`, data);      //////de modificat endpoint-ul in functie de backend
  }

  login(username: string, password: string): Observable<any> {
    var formData: any = new FormData();
    formData.append("username", username);
    formData.append("password", password);
    return this.httpClient.post<any>(`${APIbaseURL}/auth/login`, formData).pipe(
      map(data => {
        localStorage.setItem('token', data.token)
        this.router.navigate(['/']);
        return data;
      })
    );   //////de modificat endpoint-ul in functie de backend
  }
  
  getToken(): any {
    return localStorage.getItem('token');
  }

  logout(): void {
    localStorage.removeItem('token');
    localStorage.removeItem('currentUser');
  }

  redirectToLogin(): void {
    this.router.navigate(['auth/login']);
  }
}
