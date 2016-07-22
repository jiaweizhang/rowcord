import { Component, Input } from '@angular2/core';
import { Router } from '@angular/router';
@Component({
  selector: 'login',
  templateUrl: 'assets/app/login.component.html'
})

export class LoginComponent {
  username = 'empty user';
  password = 'empty password';

constructor(
  private router: Router
) {

}

  login(user: string, pass: string) {
    this.username = user;
    this.password = pass;
    this.router.navigate('/home')
  }
}
