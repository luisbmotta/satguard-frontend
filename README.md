# 🛰️ SatGuard — Monitoramento de Desastres e Ameaças Espaciais em Tempo Real

> Plataforma web fullstack que centraliza dados reais de terremotos (USGS) e asteroides próximos da Terra (NASA NeoWs) em um mapa interativo, desenvolvida como solução para a Global Solution 2026 — Space Connect da FIAP.

---

## 📋 Índice

- [Sobre o Projeto](#sobre-o-projeto)
- [Funcionalidades](#funcionalidades)
- [Tecnologias Utilizadas](#tecnologias-utilizadas)
- [Arquitetura](#arquitetura)
- [Pré-requisitos](#pré-requisitos)
- [Como Executar](#como-executar)
  - [Backend](#backend-spring-boot)
  - [Frontend](#frontend-angular)
- [Endpoints da API](#endpoints-da-api)
- [APIs Externas](#apis-externas)
- [Estrutura do Projeto](#estrutura-do-projeto)
- [Integrantes](#integrantes)

---

## 📖 Sobre o Projeto

O **SatGuard** é uma aplicação de monitoramento em tempo real que consome dados espaciais públicos da NASA e USGS para exibir eventos críticos — terremotos e asteroides próximos da Terra — em um dashboard interativo com mapa geográfico.

O problema que resolve: informações sobre desastres naturais e ameaças espaciais estão fragmentadas em múltiplos sistemas internacionais, em inglês e de difícil acesso para o público geral. O SatGuard centraliza esses dados em uma interface simples e intuitiva.

---

## ✅ Funcionalidades

- 🌍 **Terremotos em tempo real** — dados reais da USGS Earthquake API com magnitude, localização e data
- ☄️ **Asteroides próximos da Terra** — dados reais da NASA NeoWs API com nome, diâmetro e nível de perigo
- 🗺️ **Mapa interativo** — visualização geográfica dos eventos com marcadores clicáveis (Leaflet.js)
- 🔍 **Filtros por tipo** — filtrar eventos por Terremoto ou Asteroide em tempo real
- ⭐ **Sistema de favoritos** — marcar e desmarcar eventos para acompanhamento
- 🔄 **Sincronização sob demanda** — atualizar os dados das APIs externas a qualquer momento
- 🗄️ **Persistência em banco** — todos os eventos são salvos no banco H2 in-memory

---

## 🛠️ Tecnologias Utilizadas

### Backend
| Tecnologia | Versão | Uso |
|---|---|---|
| Java | 17 | Linguagem principal |
| Spring Boot | 4.0.6 | Framework backend |
| Spring Data JPA | — | Persistência de dados |
| Spring Web | — | API REST |
| H2 Database | — | Banco de dados in-memory |
| Lombok | — | Redução de boilerplate |
| Maven | — | Gerenciamento de dependências |

### Frontend
| Tecnologia | Versão | Uso |
|---|---|---|
| Angular | 17+ | Framework frontend |
| TypeScript | — | Linguagem principal |
| Leaflet.js | — | Mapa interativo |
| HTML/CSS | — | Interface |

### APIs Externas
| API | Provedor | Dados |
|---|---|---|
| Earthquake API | USGS | Terremotos em tempo real |
| NeoWs API | NASA | Asteroides próximos da Terra |

---

## 🏗️ Arquitetura

```
┌─────────────────┐     HTTP/REST      ┌──────────────────────┐
│   Angular 17    │ ◄────────────────► │   Spring Boot 4      │
│   (porta 4200)  │                    │   (porta 8080)       │
└─────────────────┘                    └──────────┬───────────┘
                                                  │
                                    ┌─────────────┼─────────────┐
                                    ▼             ▼             ▼
                               ┌─────────┐  ┌─────────┐  ┌──────────┐
                               │H2 Bank  │  │  USGS   │  │  NASA    │
                               │(memory) │  │Earthquake│  │  NeoWs  │
                               └─────────┘  └─────────┘  └──────────┘
```

### Camadas do Backend

```
br.com.satguard.api
├── controller    → EventoController   (endpoints REST)
├── service       → EventoService      (regras de negócio)
├── client        → EventoApiClient    (consumo APIs externas)
├── model         → Evento             (entidade JPA)
├── repository    → EventoRepository   (acesso ao banco)
└── dto           → EventoDTO          (transferência de dados)
```

---

## ⚙️ Pré-requisitos

Certifique-se de ter instalado:

- **Java 17** — [Download](https://adoptium.net/)
  ```bash
  java -version
  # deve mostrar: openjdk version "17.x.x"
  ```

- **Node.js 18+** — [Download](https://nodejs.org/)
  ```bash
  node -v
  # deve mostrar: v18.x.x ou superior
  ```

- **Angular CLI** — instalar via npm
  ```bash
  npm install -g @angular/cli
  ng version
  ```

- **Git** — [Download](https://git-scm.com/)
  ```bash
  git --version
  ```

> ℹ️ **Não é necessário** instalar MySQL, PostgreSQL ou qualquer outro banco de dados. O projeto usa H2, que roda em memória automaticamente com o Spring Boot.

---

## 🚀 Como Executar

### Clonando o Repositório

```bash
git clone https://github.com/SEU_USUARIO/satguard.git
cd satguard
```

---

### Backend (Spring Boot)

**1. Navegue até a pasta do backend:**
```bash
cd satguard-api
```

**2. Execute o projeto com o Maven Wrapper:**

Windows (PowerShell ou CMD):
```bash
./mvnw spring-boot:run
```

Linux/macOS:
```bash
./mvnw spring-boot:run
```

> ⚠️ Na primeira execução, o Maven vai baixar as dependências automaticamente. Isso pode levar alguns minutos dependendo da sua conexão.

**3. Aguarde a mensagem de sucesso:**
```
Started SatguardApiApplication in X.XXX seconds
```

**4. Confirme que está rodando:**

Abra no navegador: `http://localhost:8080/api/eventos`

Deve retornar `[]` (lista vazia antes de sincronizar).

**5. Popule o banco com dados reais:**

Windows (PowerShell):
```powershell
Invoke-RestMethod -Method POST -Uri http://localhost:8080/api/eventos/sincronizar
```

Linux/macOS:
```bash
curl -X POST http://localhost:8080/api/eventos/sincronizar
```

Deve retornar: `Sincronização concluída!`

**6. Verifique os dados:**

Abra: `http://localhost:8080/api/eventos`

Agora deve retornar uma lista com terremotos e asteroides.

---

### Console H2 (banco de dados)

Para visualizar os dados diretamente no banco:

1. Acesse: `http://localhost:8080/h2-console`
2. Preencha os campos:
   - **JDBC URL:** `jdbc:h2:mem:satguarddb`
   - **User Name:** `sa`
   - **Password:** *(deixe vazio)*
3. Clique em **Connect**
4. Execute: `SELECT * FROM EVENTOS`

---

### Frontend (Angular)

**1. Abra um novo terminal** (mantenha o backend rodando no terminal anterior)

**2. Navegue até a pasta do frontend:**
```bash
cd satguard-frontend
```

**3. Instale as dependências:**
```bash
npm install
```

> ⚠️ Execute `npm install` apenas na primeira vez ou quando houver atualização das dependências.

**4. Instale a biblioteca do mapa (se ainda não instalada):**
```bash
npm install leaflet
npm install @types/leaflet --save-dev
```

**5. Execute o frontend:**
```bash
ng serve
```

**6. Aguarde a mensagem:**
```
Application bundle generation complete.
Local: http://localhost:4200/
```

**7. Acesse no navegador:** `http://localhost:4200`

---

## 🔌 Endpoints da API

Base URL: `http://localhost:8080`

| Método | Endpoint | Descrição |
|---|---|---|
| `GET` | `/api/eventos` | Lista todos os eventos |
| `GET` | `/api/eventos?tipo=TERREMOTO` | Filtra por tipo (TERREMOTO ou ASTEROIDE) |
| `GET` | `/api/eventos?pais=BR` | Filtra por país |
| `GET` | `/api/eventos?tipo=TERREMOTO&pais=BR` | Filtra por tipo e país |
| `GET` | `/api/eventos/favoritos` | Lista apenas os eventos favoritados |
| `PATCH` | `/api/eventos/{id}/favorito` | Alterna favorito de um evento |
| `POST` | `/api/eventos/sincronizar` | Busca dados atualizados das APIs externas |

### Exemplos de uso

```bash
# Listar todos os eventos
curl http://localhost:8080/api/eventos

# Listar só terremotos
curl http://localhost:8080/api/eventos?tipo=TERREMOTO

# Listar só asteroides
curl http://localhost:8080/api/eventos?tipo=ASTEROIDE

# Favoritar evento de id 1
curl -X PATCH http://localhost:8080/api/eventos/1/favorito

# Sincronizar dados
curl -X POST http://localhost:8080/api/eventos/sincronizar
```

---

## 🌐 APIs Externas

### USGS Earthquake API
- **Provedor:** United States Geological Survey
- **Documentação:** https://earthquake.usgs.gov/fdsnws/event/1/
- **Autenticação:** Não requer chave de API
- **URL utilizada:**
  ```
  https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson
    &minlatitude=-33&maxlatitude=5
    &minlongitude=-73&maxlongitude=-34
    &minmagnitude=2.0&limit=20&orderby=time
  ```
- **Dados retornados:** Magnitude, localização, coordenadas, data/hora

### NASA NeoWs API
- **Provedor:** NASA — Near Earth Object Web Service
- **Documentação:** https://api.nasa.gov/
- **Autenticação:** Usa `DEMO_KEY` (gratuita, limite de 30 req/hora)
- **URL utilizada:**
  ```
  https://api.nasa.gov/neo/rest/v1/feed/today?detailed=false&api_key=DEMO_KEY
  ```
- **Dados retornados:** Nome, diâmetro estimado, velocidade, se é potencialmente perigoso

> ℹ️ Para aumentar o limite de requisições da NASA, obtenha uma chave gratuita em [api.nasa.gov](https://api.nasa.gov/) e substitua `DEMO_KEY` no arquivo `EventoApiClient.java`.

---

## 📁 Estrutura do Projeto

```
satguard/
│
├── satguard-api/                          # Backend Spring Boot
│   ├── src/
│   │   └── main/
│   │       ├── java/br/com/satguard/api/
│   │       │   ├── controller/
│   │       │   │   └── EventoController.java
│   │       │   ├── service/
│   │       │   │   └── EventoService.java
│   │       │   ├── client/
│   │       │   │   └── EventoApiClient.java
│   │       │   ├── model/
│   │       │   │   └── Evento.java
│   │       │   ├── repository/
│   │       │   │   └── EventoRepository.java
│   │       │   └── dto/
│   │       │       └── EventoDTO.java
│   │       └── resources/
│   │           └── application.properties
│   ├── pom.xml
│   └── mvnw
│
└── satguard-frontend/                     # Frontend Angular
    ├── src/
    │   └── app/
    │       ├── components/
    │       │   ├── mapa/
    │       │   │   ├── mapa.ts
    │       │   │   ├── mapa.html
    │       │   │   └── mapa.css
    │       │   └── lista-eventos/
    │       │       ├── lista-eventos.ts
    │       │       ├── lista-eventos.html
    │       │       └── lista-eventos.css
    │       ├── services/
    │       │   └── evento.ts
    │       ├── app.ts
    │       ├── app.html
    │       └── app.css
    ├── angular.json
    └── package.json
```

---

## ❗ Solução de Problemas

**Backend não inicia:**
- Verifique se o Java 17 está instalado: `java -version`
- Verifique se a porta 8080 está livre
- Use `./mvnw` e não `mvn` diretamente

**Frontend não conecta ao backend:**
- Confirme que o backend está rodando em `localhost:8080`
- Verifique se o CORS está configurado (anotação `@CrossOrigin` no controller)
- Ambos devem estar rodando ao mesmo tempo em terminais separados

**Mapa não aparece:**
- Confirme que `leaflet.css` está no `angular.json` em `"styles"`
- Rode `npm install` novamente

**Dados não aparecem após sincronizar:**
- Verifique sua conexão com a internet (as APIs são externas)
- A NASA DEMO_KEY tem limite de 30 req/hora — aguarde antes de sincronizar novamente
- Acesse `http://localhost:8080/api/eventos` para confirmar se os dados estão no banco

---

## 👥 Integrantes

| Nome | RM | Turma |
|---|---|---|
| Luis | — | 4SIOA |
| [Nome do colega] | — | 4SIOA |

---

## 📄 Licença

Projeto desenvolvido para fins acadêmicos — FIAP Global Solution 2026.
