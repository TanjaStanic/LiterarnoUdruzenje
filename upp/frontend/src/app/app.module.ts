import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {
  MatButtonModule,
  MatCardModule,
  MatFormFieldModule,
  MatIconModule,
  MatListModule, MatSelectModule,
  MatSidenavModule,
  MatToolbarModule,
  MatDialogModule, MatRadioModule, MatTableModule
} from '@angular/material';
import {RouterModule} from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import {AppRoutingModule} from './app-routing.module';
import {ReactiveFormsModule} from '@angular/forms';
import { ReaderDialogComponent } from './components/reader-dialog/reader-dialog.component';
import { WriterDialogComponent } from './components/writer-dialog/writer-dialog.component';
import { FormsModule } from '@angular/forms';
import { HomepageReaderComponent } from './components/homepage-reader/homepage-reader.component';
import { HomepageWriterComponent } from './components/homepage-writer/homepage-writer.component';
import { HomepageAdminComponent } from './components/homepage-admin/homepage-admin.component';
import { HomepageEditorComponent } from './components/homepage-editor/homepage-editor.component';
import { HomepageLecturerComponent } from './components/homepage-lecturer/homepage-lecturer.component';
import { WriterReviewComponent } from './components/writer-review/writer-review.component';
import { WriterReviewDialogComponent } from './components/writer-review-dialog/writer-review-dialog.component';
import { WriterPaymentComponent } from './components/writer-payment/writer-payment.component';
import { GenericFormComponent } from './components/generic-form/generic-form.component';
import { GenericInputRowComponent } from './components/generic-input-row/generic-input-row.component';
import { PublishBookComponent } from './components/publish-book/publish-book.component';
import { InitialBookReviewComponent } from './components/initial-book-review/initial-book-review.component';
import { InitialBookReviewDialogComponent } from './components/initial-book-review-dialog/initial-book-review-dialog.component';
import { SubmitManuscriptComponent } from './components/submit-manuscript/submit-manuscript.component';
import { SubmitManuscriptDialogComponent } from './components/submit-manuscript-dialog/submit-manuscript-dialog.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    ReaderDialogComponent,
    WriterDialogComponent,
    HomepageReaderComponent,
    HomepageWriterComponent,
    HomepageAdminComponent,
    HomepageEditorComponent,
    HomepageLecturerComponent,
    WriterReviewComponent,
    WriterReviewDialogComponent,
    WriterPaymentComponent,
    GenericFormComponent,
    GenericInputRowComponent,
    PublishBookComponent,
    InitialBookReviewComponent,
    InitialBookReviewDialogComponent,
    SubmitManuscriptComponent,
    SubmitManuscriptDialogComponent,
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    MatSidenavModule,
    MatToolbarModule,
    MatListModule,
    MatIconModule,
    RouterModule,
    AppRoutingModule,
    ReactiveFormsModule,
    MatCardModule,
    MatButtonModule,
    MatFormFieldModule,
    MatSelectModule,
    MatDialogModule,
    MatRadioModule,
    HttpClientModule,
    FormsModule,
    MatTableModule
  ],
  providers: [],
  bootstrap: [AppComponent],
  entryComponents: [ReaderDialogComponent, WriterReviewDialogComponent, InitialBookReviewDialogComponent, SubmitManuscriptDialogComponent]
})
export class AppModule { }
