import {Role} from './role';

export class User {
  id: number;
  username: string;
  firstName: string;
  lastName: string;
  password: string;
  email: string;
  city: string;
  state: string;
  role: Role;

  constructor(username?: string, password?: string, role?: Role) {
    this.username = username;
    this.password = password;
    this.role = role;
  }
}
