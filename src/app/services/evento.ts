import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Evento {
  id: number;
  tipo: string;
  descricao: string;
  latitude: number;
  longitude: number;
  intensidade: number;
  pais: string;
  dataEvento: string;
  favorito: boolean;
}

@Injectable({
  providedIn: 'root'
})
export class EventoService {

  private apiUrl = 'http://localhost:8080/api/eventos';

  constructor(private http: HttpClient) {}

  listar(tipo?: string, pais?: string): Observable<Evento[]> {
    let params: any = {};
    if (tipo) params['tipo'] = tipo;
    if (pais) params['pais'] = pais;
    return this.http.get<Evento[]>(this.apiUrl, { params });
  }

  listarFavoritos(): Observable<Evento[]> {
    return this.http.get<Evento[]>(`${this.apiUrl}/favoritos`);
  }

  toggleFavorito(id: number): Observable<Evento> {
    return this.http.patch<Evento>(`${this.apiUrl}/${id}/favorito`, {});
  }

  sincronizar(): Observable<string> {
    return this.http.post(`${this.apiUrl}/sincronizar`, {}, { responseType: 'text' });
  }
}