import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import {HttpClientModule} from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ViajesVerificarComponent } from './viajes-verificar/viajes-verificar.component';
import { ViajesService } from './viajes.service';

@NgModule({
  declarations: [
    AppComponent,
    ViajesVerificarComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule
  ],
  providers: [ViajesService],
  bootstrap: [AppComponent]
})
export class AppModule { }
