import {User} from './user';
import {ApplicationResponse} from './applicationResponse';
import {RegistrationApplicationResponse} from './registrationApplicationResponse';
import {Document} from './document';

export class RegistrationApplication {
  id: number;
  writer: User;
  createdDate: Date;
  finalResponse: ApplicationResponse;
  documents: Array<Document>;
  responses: Array<RegistrationApplicationResponse>;

  constructor() {}
}
