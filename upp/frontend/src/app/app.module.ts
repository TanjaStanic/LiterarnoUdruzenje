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
import { BookReviewComponent } from './components/book-review/book-review.component';
import { BookReviewDialogComponent } from './components/book-review-dialog/book-review-dialog.component';
import { BookReviewDialogBetaReadersComponent } from './components/book-review-dialog-beta-readers/book-review-dialog-beta-readers.component';
import { BetaReaderCommentComponent } from './components/beta-reader-comment/beta-reader-comment.component';
import { BetaReaderCommentDialogComponent } from './components/beta-reader-comment-dialog/beta-reader-comment-dialog.component';
import { ImproveManuscriptDialogComponent } from './components/improve-manuscript-dialog/improve-manuscript-dialog.component';
import { NeedMoreWorkDialogComponent } from './components/need-more-work-dialog/need-more-work-dialog.component';
import { LecturerNotesTyposComponent } from './components/lecturer-notes-typos/lecturer-notes-typos.component';
import { LecturerNotesTyposDialogComponent } from './components/lecturer-notes-typos-dialog/lecturer-notes-typos-dialog.component';
import { EditorHaveSuggestionDialogComponent } from './components/editor-have-suggestion-dialog/editor-have-suggestion-dialog.component';
import { WriterComplaintComponent } from './components/writer-complaint/writer-complaint.component';
import { ChooseEditorsComponent } from './components/choose-editors/choose-editors.component';
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
    BookReviewComponent,
    BookReviewDialogComponent,
    BookReviewDialogBetaReadersComponent,
    BetaReaderCommentComponent,
    BetaReaderCommentDialogComponent,
    ImproveManuscriptDialogComponent,
    NeedMoreWorkDialogComponent,
    LecturerNotesTyposComponent,
    LecturerNotesTyposDialogComponent,
    EditorHaveSuggestionDialogComponent,
    WriterComplaintComponent,
    ChooseEditorsComponent
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
  entryComponents: [ReaderDialogComponent, WriterReviewDialogComponent, InitialBookReviewDialogComponent, SubmitManuscriptDialogComponent,
  BookReviewDialogComponent, BookReviewDialogBetaReadersComponent, BetaReaderCommentDialogComponent, ImproveManuscriptDialogComponent,
  NeedMoreWorkDialogComponent, LecturerNotesTyposDialogComponent, EditorHaveSuggestionDialogComponent]
})
export class AppModule { }
