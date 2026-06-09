# 🗺️ SatGuard — Frontend

> Interface web desenvolvida com Angular 17 e Leaflet.js para visualização de terremotos e asteroides em mapa interativo, com filtros e sistema de favoritos. FIAP Global Solution 2026 — Space Connect.

---

## 📋 Índice

- [Sobre](#sobre)
- [Tecnologias](#tecnologias)
- [Pré-requisitos](#pré-requisitos)
- [Como Executar](#como-executar)
- [Funcionalidades](#funcionalidades)
- [Estrutura](#estrutura)
- [Integrantes](#integrantes)

---

## 📖 Sobre

Frontend do SatGuard — consome a API REST do backend (Spring Boot) e exibe os eventos em um dashboard com mapa interativo, cards informativos e filtros por tipo de evento.

> ⚠️ O backend precisa estar rodando em `http://localhost:8080` antes de iniciar o frontend. Repositório do backend: [satguard-api](https://github.com/luisbmotta/satguard-api)

---

## 🛠️ Tecnologias

| Tecnologia | Versão |
|---|---|
| Angular | 17+ |
| TypeScript | — |
| Leaflet.js | — |
| HTML/CSS | — |
| Node.js | 18+ |

---

## ⚙️ Pré-requisitos

- **Node.js 18+** instalado
  ```bash
  node -v
  ```

- **Angular CLI** instalado
  ```bash
  npm install -g @angular/cli
  ```

- **Backend rodando** em `http://localhost:8080`

---

## 🚀 Como Executar

**1. Clone o repositório:**
```bash
git clone https://github.com/luisbmotta/satguard-frontend.git
cd satguard-frontend
```

**2. Instale as dependências:**
```bash
npm install
```

**3. Instale a biblioteca do mapa:**
```bash
npm install leaflet
npm install @types/leaflet --save-dev
```

**4. Execute o frontend:**
```bash
ng serve
```

**5. Acesse no navegador:**
```
http://localhost:4200
```

---

## ✅ Funcionalidades

- 🗺️ Mapa interativo com marcadores clicáveis por evento
- 🔍 Filtros por tipo: Todos / Terremotos / Asteroides
- 📋 Cards informativos com descrição, intensidade e data
- ⭐ Sistema de favoritos — marcar e desmarcar eventos
- 🔄 Dados em tempo real sincronizados com o backend

---

## 📁 Estrutura

```
satguard-frontend/
└── src/app/
    ├── components/
    │   ├── mapa/
    │   │   ├── mapa.ts
    │   │   ├── mapa.html
    │   │   └── mapa.css
    │   └── lista-eventos/
    │       ├── lista-eventos.ts
    │       ├── lista-eventos.html
    │       └── lista-eventos.css
    ├── services/
    │   └── evento.ts
    ├── app.ts
    ├── app.html
    └── app.css
```

---

## ❗ Solução de Problemas

**Frontend não conecta ao backend:**
- Confirme que o backend está rodando em `localhost:8080`
- Os dois devem estar rodando em terminais separados ao mesmo tempo

**Mapa não aparece:**
- Confirme que `leaflet.css` está no `angular.json` em `"styles"`
- Rode `npm install` novamente

**Erro ao compilar:**
- Verifique se Node.js 18+ está instalado: `node -v`
- Rode `npm install` novamente

---

## 👥 Integrantes

| Nome | RM | Turma |
|---|---|---|
| Luis Fernando de Barros Motta | 95664 | 4SIOA |
| Eduardo Lucca Dias da Costa | 95415 | 4SIOA |

---

*FIAP Global Solution 2026 — Space Connect*
