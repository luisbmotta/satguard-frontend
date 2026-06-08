import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EventoService, Evento } from '../../services/evento';
import * as L from 'leaflet';

@Component({
  selector: 'app-mapa',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './mapa.html',
  styleUrls: ['./mapa.css']
})
export class MapaComponent implements OnInit {

  private map: any;
  eventos: Evento[] = [];

  constructor(private eventoService: EventoService) {}

  ngOnInit(): void {
    this.initMap();
    this.carregarEventos();
  }

  private initMap(): void {
    this.map = L.map('map').setView([-15.0, -50.0], 4);
    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      attribution: '© OpenStreetMap contributors'
    }).addTo(this.map);
  }

  carregarEventos(tipo?: string): void {
    this.eventoService.listar(tipo).subscribe(eventos => {
      this.eventos = eventos;
      this.adicionarMarcadores(eventos);
    });
  }

  private adicionarMarcadores(eventos: Evento[]): void {
    this.map.eachLayer((layer: any) => {
      if (layer instanceof L.Marker) this.map.removeLayer(layer);
    });

    eventos.forEach(evento => {
      let icone = '🌍';
      if (evento.tipo === 'ASTEROIDE') icone = '☄️';
      if (evento.tipo === 'TERREMOTO') icone = '🌍';

      const marker = L.marker([evento.latitude, evento.longitude]);
      marker.bindPopup(`
        <b>${icone} ${evento.tipo}</b><br>
        ${evento.descricao}<br>
        <small>Intensidade: ${evento.intensidade.toFixed(2)}</small><br>
        <small>${new Date(evento.dataEvento).toLocaleString('pt-BR')}</small>
      `);
      marker.addTo(this.map);
    });
}
}