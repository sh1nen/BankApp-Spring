import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { HttpModule } from '@angular/http';
import { NoopAnimationsModule, BrowserAnimationsModule } from '@angular/platform-browser/animations'
import 'hammerjs';

import {
  MatButtonModule,
  MatToolbarModule,
  MatGridListModule,
  MatInputModule,
  MatOptionModule,
  MatSelectModule,
  MatSlideToggleModule,
  MatCheckboxModule,
  MatTableModule
} from "@angular/material";

import { MatIconModule } from '@angular/material/icon';

import { routing } from './app.routing';

import { AppComponent } from './app.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { LoginComponent } from './components/login/login.component';
import { UserAccountComponent } from './components/user-account/user-account.component';
import { AppointmentComponent } from './components/appointment/appointment.component';
import { PrimaryTransactionComponent } from './components/primary-transaction/primary-transaction.component';
import { SavingsTransactionComponent } from './components/savings-transaction/savings-transaction.component';

import { LoginService } from './services/login.service';
import { UserService } from './services/user.service';
import { AppointmentService } from './services/appointment.service'; 

const MAT_MODULES  = [
  MatButtonModule,
  MatToolbarModule,
  MatGridListModule,
  MatInputModule,
  MatOptionModule,
  MatSelectModule,
  MatCheckboxModule,  
  MatSlideToggleModule,
  MatIconModule,
  BrowserAnimationsModule,
  MatTableModule
];

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    LoginComponent,
    UserAccountComponent,
    AppointmentComponent,
    PrimaryTransactionComponent,
    SavingsTransactionComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    NoopAnimationsModule,
    BrowserAnimationsModule,
    routing,
    MAT_MODULES
  ],
  providers: [
    LoginService,
    UserService,
    AppointmentService
  ],
  bootstrap: [AppComponent]  
})
export class AppModule { }
