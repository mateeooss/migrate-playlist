# 🎵 Migrate Playlist - YouTube ↔ Spotify

Este projeto é uma aplicação Java com Spring Boot que permite **migrar playlists entre YouTube e Spotify** de forma automatizada e segura, utilizando as APIs oficiais de ambas as plataformas.

## 🚀 Funcionalidades

- ✅ Migração de playlists do YouTube para o Spotify
- ✅ Migração de playlists do Spotify para o YouTube
- 🔐 Autenticação OAuth 2.0 com YouTube e Spotify
- 🔎 Correspondência inteligente de faixas por nome e artista
- 📦 Interface RESTful pronta para integrar com front-end ou consumir via Postman

## 🧰 Tecnologias Utilizadas

- Java 21
- Spring Boot 3.4.4
- API YouTube Data v3 (`google-api-services-youtube`)
- Spotify Web API Java SDK
- Lombok
- Apache Commons Lang3

## 📦 Instalação

1. **Clone o repositório**:
   ```bash
   git clone https://github.com/seu-usuario/migrate-playlist.git
   cd migrate-playlist

## 🛠 Endpoints (Exemplos)

**POST** /api/playlist/migrate/youtube-to-spotify

**POST** /api/playlist/migrate/spotify-to-youtube

## 🔐 Autenticação

O projeto utiliza OAuth 2.0 para autenticar os usuários nas APIs do YouTube e Spotify. Implemente o fluxo de autorização (Authorization Code Flow) para obter o access_token necessário para acessar as playlists dos usuários.


---
