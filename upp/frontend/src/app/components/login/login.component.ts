import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {UserService} from '../../services/user.service';
import {User} from '../../model/user';
import {Role} from '../../model/role';
import {Router} from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  private loginForm: FormGroup;
  private submitted = false;
  user: User;
  userRole;
  constructor(private formBuilder: FormBuilder,
              private userService: UserService,
              private router: Router) { }

  ngOnInit() {
    this.loginForm = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  get f() {
    return this.loginForm.controls;
  }

  onSubmit() {
    if (this.loginForm.invalid) {
      return;
    }
    this.getUser();
  }

  async getUser() {
    const c = await this.userService.getUser(this.f.username.value);
    console.log(c);
    this.user = new User();
    this.user.username = c.username;
    this.user.password = c.password;
    this.userRole = c.role;
    console.log(this.user);
    this.attemptLogin();
  }

  attemptLogin() {

    if (this.userRole === 'ADMIN' && this.f.username.value === this.user.username && this.f.password.value === this.user.password) {
      this.router.navigate(['/adminHome']);
      this.user.role = Role.ADMIN;
      this.userService.setToken(this.user);
    } else if (this.userRole === 'READER' && this.f.username.value === this.user.username && this.f.password.value === this.user.password) {
      this.router.navigate(['/readerHome']);
      this.user.role = Role.READER;
      this.userService.setToken(this.user);
    } else if (this.userRole === 'WRITER' && this.f.username.value === this.user.username && this.f.password.value === this.user.password) {
      this.router.navigate(['/writerHome']);
      this.user.role = Role.WRITER;
      this.userService.setToken(this.user);
    } else if (this.userRole === 'BETA_READER' && this.f.username.value === this.user.username && this.f.password.value === this.user.password) {
      this.router.navigate(['/readerHome']);
      this.user.role = Role.BETA_READER;
      this.userService.setToken(this.user);
    } else if (this.userRole === 'LECTURER' && this.f.username.value === this.user.username && this.f.password.value === this.user.password) {
      this.router.navigate(['/lecturerHome']);
      this.user.role = Role.LECTURER;
      this.userService.setToken(this.user);
    } else if (this.userRole === 'EDITOR' && this.f.username.value === this.user.username && this.f.password.value === this.user.password) {
      this.router.navigate(['/editorHome'])
      this.user.role = Role.EDITOR;
      this.userService.setToken(this.user);
    }
  }
}
