import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { MapaComponent } from './components/mapa/mapa';
import { ListaEventosComponent } from './components/lista-eventos/lista-eventos';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, HttpClientModule, MapaComponent, ListaEventosComponent],
  templateUrl: './app.html',
  styleUrls: ['./app.css']
})
export class App {
  title = 'SatGuard';
}