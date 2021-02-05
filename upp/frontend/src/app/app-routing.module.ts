import {RouterModule, Routes} from '@angular/router';
import {NgModule} from '@angular/core';
import {LoginComponent} from './components/login/login.component';
import {RegisterComponent} from './components/register/register.component';
import {HomepageAdminComponent} from './components/homepage-admin/homepage-admin.component';
import {HomepageEditorComponent} from './components/homepage-editor/homepage-editor.component';
import {HomepageLecturerComponent} from './components/homepage-lecturer/homepage-lecturer.component';
import {HomepageReaderComponent} from './components/homepage-reader/homepage-reader.component';
import {HomepageWriterComponent} from './components/homepage-writer/homepage-writer.component';
import {WriterDialogComponent} from './components/writer-dialog/writer-dialog.component';
import {WriterReviewComponent} from './components/writer-review/writer-review.component';
import {WriterPaymentComponent} from './components/writer-payment/writer-payment.component';
import {PublishBookComponent} from './components/publish-book/publish-book.component';
import {InitialBookReviewComponent} from './components/initial-book-review/initial-book-review.component';
import {SubmitManuscriptComponent} from './components/submit-manuscript/submit-manuscript.component';
import {BookReviewComponent} from './components/book-review/book-review.component';
import {BetaReaderCommentComponent} from './components/beta-reader-comment/beta-reader-comment.component';
import {LecturerNotesTyposComponent} from './components/lecturer-notes-typos/lecturer-notes-typos.component';
import {WriterComplaintComponent} from './components/writer-complaint/writer-complaint.component';
import { ChooseEditorsComponent } from './components/choose-editors/choose-editors.component';
import {EditorPlagiarisedTableComponent} from './components/editor-plagiarised-table/editor-plagiarised-table.component';

const routes: Routes = [
  {
    path: '',
    component: LoginComponent,
  },
  {
    path: 'login',
    component: LoginComponent,
  },
  {
    path: 'register',
    component: RegisterComponent,
  },
  {
    path: 'adminHome',
    component: HomepageAdminComponent,
  },
  {
    path: 'editorHome',
    component: HomepageEditorComponent,
  },
  {
    path: 'lecturerHome',
    component: HomepageLecturerComponent,
  },
  {
    path: 'readerHome',
    component: HomepageReaderComponent,
  },
  {
    path: 'writerHome',
    component: HomepageWriterComponent,
  },
  {
    path: 'writerHomeFiles',
    component: WriterDialogComponent,
  },
  {
    path: 'writerReview',
    component: WriterReviewComponent,
  },
  {
    path: 'writerPayment',
    component: WriterPaymentComponent,
  },
  {
    path: 'publishBook',
    component: PublishBookComponent,
  },
  {
    path: 'initialBookReview',
    component: InitialBookReviewComponent,
  },
  {
    path: 'submitManuscript',
    component: SubmitManuscriptComponent,
  },
  {
    path: 'bookReview',
    component:  BookReviewComponent,
  },
  {
    path: 'betaReaderComment',
    component: BetaReaderCommentComponent,
  },
  {
    path: 'lecturerNotesTypos',
    component: LecturerNotesTyposComponent,
  }
  ,
  {
    path: 'writerComplain',
    component: WriterComplaintComponent,
  },
  {
    path: 'chooseEditors',
    component: ChooseEditorsComponent,
  },
  {
    path: 'editorPlagiarisedTable',
    component: EditorPlagiarisedTableComponent
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
