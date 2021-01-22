import { Component, OnInit } from '@angular/core';
import {UserService} from '../../services/user.service';
import {RegistrationApplicationResponseService} from '../../services/registration-application-response.service';

@Component({
  selector: 'app-writer-review',
  templateUrl: './writer-review.component.html',
  styleUrls: ['./writer-review.component.css']
})
export class WriterReviewComponent implements OnInit {

  private formFields = [];
  private enumValues = []
  constructor(private userService: UserService,
              private rapService: RegistrationApplicationResponseService) { }

  ngOnInit() {

    const x = this.userService.getReviewFields();

    x.subscribe(
      res => {
        console.log(res);
        this.formFields = res.formFields;
        this.formFields.forEach( (field) => {
          if ( field.id === 'review_id') {
            this.enumValues = Object.keys(field.type.values);
          }
        });
      },
      err => {
        console.log(err);
      }
    );
    this.getByUsername();
  }

  async getByUsername() {
    const c = await this.rapService.getListForReviews(this.userService.getLoggedUser().username);
    console.log(c);
  }

}
