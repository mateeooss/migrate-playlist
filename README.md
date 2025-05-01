

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

    Configure suas credenciais da API:

        Crie apps no Google Cloud Console e no Spotify Developer Dashboard

        Insira suas credenciais OAuth nos arquivos de configuração do projeto:

    # application.properties
    youtube.api.clientId=YOUR_YOUTUBE_CLIENT_ID
    youtube.api.clientSecret=YOUR_YOUTUBE_CLIENT_SECRET
    spotify.api.clientId=YOUR_SPOTIFY_CLIENT_ID
    spotify.api.clientSecret=YOUR_SPOTIFY_CLIENT_SECRET

Compile o projeto com Maven:

mvn clean install

Execute a aplicação:

    mvn spring-boot:run

🛠 Endpoints (Exemplos)

POST /api/playlist/migrate/youtube-to-spotify
POST /api/playlist/migrate/spotify-to-youtube

Exemplo de corpo da requisição:

{
"playlistUrl": "https://www.youtube.com/playlist?list=...",
"userId": "spotify_user_id",
"accessToken": "oauth_access_token"
}

🔐 Autenticação

O projeto utiliza OAuth 2.0 para autenticar os usuários nas APIs do YouTube e Spotify. Implemente o fluxo de autorização (Authorization Code Flow) para obter o access_token necessário para acessar as playlists dos usuários.
📄 Licença

Este projeto está licenciado sob a MIT License.

Desenvolvido com 💻 por Matheus Lima de Almeida


---

Se quiser, posso substituir `Matheus Lima de Almeida`, `seu-usuario`, ou adicionar instruções específicas para um frontend ou ferramenta externa. Deseja personalizar algum detalhe?

