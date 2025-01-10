# Login Mod para Minecraft Forge 1.18.2

<div align="center">

![Minecraft Version](https://img.shields.io/badge/Minecraft-1.18.2-brightgreen)
![Forge Version](https://img.shields.io/badge/Forge-40.2.0-blue)
![License](https://img.shields.io/badge/License-MIT-yellow)

</div>

## 📋 Sobre

Um mod simples e eficiente de login/registro para servidores Minecraft Forge. Desenvolvido para preencher a lacuna de mods de autenticação confiáveis e leves para servidores modificados.

### ✨ Características

- Sistema de registro e login seguro
- Senhas criptografadas com BCrypt
- Compatível com servidores online e offline/pirata
- Leve e otimizado
- Não causa conflitos com outros mods
- Funciona em qualquer modpack Forge 1.18.2

## 🔒 Segurança

- Jogadores não podem se mover até fazer login
- Inventário protegido
- Sem acesso a comandos até autenticar
- Sem interação com o mundo até autenticar
- Senhas salvas com hash criptográfico

## 📌 Comandos

| Comando | Descrição |
|---------|-----------|
| `/register <senha> <confirmar senha>` | Registra uma nova conta |
| `/login <senha>` | Faz login em uma conta existente |
| `/removepass <jogador>` | Remove a senha de um jogador (apenas admin) |

## 💻 Instalação

1. Baixe o arquivo `.jar` mais recente
2. Coloque o arquivo na pasta `mods` do seu servidor
3. Reinicie o servidor
4. Pronto! O mod já está funcionando

## ⚙️ Configuração

O mod não requer configuração adicional. As senhas são salvas automaticamente em: ./player_passwords.dat


## 🤝 Contribuindo

Sinta-se à vontade para:
- Reportar bugs
- Sugerir novas funcionalidades
- Enviar pull requests

## 📝 Por que este mod?

A maioria dos mods de login disponíveis são:
- Complexos demais
- Pesados
- Causam conflitos
- Desatualizados
- Inseguros

Este mod foi criado para ser uma solução simples, segura e eficiente para servidores Forge.

## ⚠️ Requisitos

- Minecraft 1.18.2
- Forge 40.2.0+
- Java 17

## 📜 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

---

<div align="center">

Feito com ❤️ por [@yleo](https://github.com/yleosamp) para a comunidade Minecraft

</div>