import {RegistrationApplication} from './registrationApplication';
import {User} from './user';
import {ApplicationResponse} from './applicationResponse';

export class RegistrationApplicationResponse {
  id: number;
  registrationApplication: RegistrationApplication;
  lecturer: User;
  response: ApplicationResponse;
  comment: string;

  constructor() {}
}
