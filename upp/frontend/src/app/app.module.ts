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
  MatDialogModule, MatRadioModule
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
    HomepageLecturerComponent
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
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent],
  entryComponents: [ReaderDialogComponent, WriterDialogComponent]
})
export class AppModule { }
