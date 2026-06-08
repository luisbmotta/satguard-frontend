import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EventoService, Evento } from '../../services/evento';

@Component({
  selector: 'app-lista-eventos',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './lista-eventos.html',
  styleUrls: ['./lista-eventos.css']
})
export class ListaEventosComponent implements OnInit {

  eventos: Evento[] = [];

  constructor(private eventoService: EventoService) {}

  ngOnInit(): void {
    this.carregarEventos();
  }

  carregarEventos(): void {
    this.eventoService.listar().subscribe(eventos => {
      this.eventos = eventos;
    });
  }

  toggleFavorito(evento: Evento): void {
    this.eventoService.toggleFavorito(evento.id).subscribe(atualizado => {
      evento.favorito = atualizado.favorito;
    });
  }
}