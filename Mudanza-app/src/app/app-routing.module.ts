import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ViajesVerificarComponent } from './viajes-verificar/viajes-verificar.component';

const routes: Routes = [
  {
    path: 'viajes/verificar',
    component: ViajesVerificarComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
