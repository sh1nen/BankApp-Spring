import { ModuleWithProviders } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { UserAccountComponent } from './components/user-account/user-account.component';
import { PrimaryTransactionComponent } from './components/primary-transaction/primary-transaction.component';
import { SavingsTransactionComponent } from './components/savings-transaction/savings-transaction.component';
import { AppointmentComponent } from './components/appointment/appointment.component';

const appRoutes : Routes = [
    {
        path: '',
        redirectTo: '/login',
        pathMatch: 'full'
    },
    {
        path: 'login',
        component: LoginComponent
    },
    {
        path: 'userAccount',
        component: UserAccountComponent
    },
    {
        path: 'primaryTransaction/:username',
        component: PrimaryTransactionComponent
      },
      {
        path: 'savingsTransaction/:username',
        component: SavingsTransactionComponent
      },
      {
        path: 'appointment',
        component: AppointmentComponent
      }
];

export const routing: ModuleWithProviders = RouterModule.forRoot(appRoutes);