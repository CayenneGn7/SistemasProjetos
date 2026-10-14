# Sistema de Gerenciamento de Projetos

Projeto desenvolvido em Java com o objetivo de cadastrar, listar, buscar, alterar e remover projetos, aplicando conceitos de programação orientada a objetos, coleções e persistência de dados em arquivos CSV.

## Funcionalidades

- Cadastro de projetos.
- Listagem de projetos cadastrados.
- Busca por ID.
- Busca por categoria.
- Busca por status.
- Alteração de projetos por ID.
- Remoção de projetos por ID.
- Validação de nome vazio e ID duplicado.
- Salvamento dos projetos em arquivo CSV.
- Carregamento dos projetos salvos ao iniciar o sistema.

## Tecnologias utilizadas

- Java
- Git
- GitHub
- Arquivos CSV

## Estrutura do projeto

```text
SistemaProjetos/
├── dados/
│   └── projetos.csv
└── src/
    ├── Main.java
    ├── ExemploLista.java
    ├── model/
    │   └── Projeto.java
    ├── service/
    │   └── ProjetoService.java
    └── dao/
        └── ProjetoCSV.java
```

## Execução

A partir da pasta raiz do projeto, compile os arquivos Java:

```bash
javac -d out src/Main.java src/model/Projeto.java src/service/ProjetoService.java src/dao/ProjetoCSV.java
```

Execute o programa:

```bash
java -cp out Main
```

## Status do projeto

Em desenvolvimento.