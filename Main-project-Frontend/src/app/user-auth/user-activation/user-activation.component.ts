import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { APIbaseURL } from 'src/app/settings';
import { map } from 'rxjs/operators';
import { Router } from '@angular/router';
import { UserService } from 'src/app/services/user.service';
import { AuthenticationService } from 'src/app/services/authentication.service';

@Component({
  selector: 'app-user-activation',
  templateUrl: './user-activation.component.html',
  styleUrls: ['./user-activation.component.scss']
})
export class UserActivationComponent implements OnInit {
  token!: string;

  constructor(private http: HttpClient, private router: Router, private userService: UserService,private authService: AuthenticationService) { }

  foo(token: String): Observable<any> {
    return this.http.get<any>(`${APIbaseURL}/auth/register/registrationConfirm?token=${this.token}`).pipe(
      map(data => {
        console.log(data)
        return data;
      })
    );
  }

  sleep(ms: number) {
    return new Promise(r => setTimeout(r, ms));
  }

  ngOnInit(): void {
    (async () => {
      let url = String(this.router.url)

      console.log(url)

      url = url.replace('/auth/register/registrationConfirm?token=', '')

      this.token = url

      console.log(this.token)

      this.foo(this.token).subscribe(
        (data) => {
          console.log(data)
        }
      )
      
      await this.sleep(3000)

      this.router.navigate(['/login'])
    })();
  }

}
