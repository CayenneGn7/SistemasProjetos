# Sistema de Gerenciamento de Projetos

Aplicação desenvolvida em Java para cadastrar, consultar, alterar e excluir projetos. O sistema utiliza programação orientada a objetos, separação de responsabilidades, persistência em arquivos CSV, interface gráfica e uma API REST.

## Funcionalidades

- Cadastro de projetos.
- Listagem de projetos cadastrados.
- Busca de projetos por ID.
- Alteração e exclusão de projetos.
- Validação de dados de entrada.
- Persistência de dados em arquivo CSV.
- Interface gráfica desenvolvida com Java Swing.
- API REST para operações com projetos.
- Comunicação HTTP com a API por meio de um cliente Java.
- Respostas JSON para consultas e operações da API.

## Tecnologias utilizadas

- Java 21
- Maven
- Java Swing
- Javalin
- Jackson
- HTTP Client
- Arquivos CSV
- Git e GitHub

## Estrutura do projeto

```text
SistemaProjetos/
├── dados/
│   └── projetos.csv
├── src/
│   └── main/
│       └── java/
│           ├── Main.java
│           ├── TelaPrincipal.java
│           ├── api/
│           │   ├── Api.java
│           │   └── ErrorResponse.java
│           ├── client/
│           │   └── ApiClient.java
│           ├── dao/
│           │   └── ProjetoCSV.java
│           ├── model/
│           │   └── Projeto.java
│           └── service/
│               └── ProjetoService.java
├── .gitignore
├── pom.xml
└── README.md
```

### Organização das classes

- **Main:** executa a versão do sistema pelo terminal.
- **TelaPrincipal:** implementa a interface gráfica com Java Swing.
- **model:** contém a classe que representa os projetos.
- **service:** concentra as operações e regras de negócio.
- **dao:** responsável pelo acesso aos dados armazenados em CSV.
- **api:** disponibiliza os endpoints REST e a estrutura das respostas de erro.
- **client:** realiza requisições HTTP para a API.
- **dados:** armazena os dados persistidos em CSV.

## Pré-requisitos

- JDK 21 ou compatível com a versão configurada no projeto.
- Apache Maven instalado e disponível no terminal.
- Git, caso deseje clonar o repositório.

## Como executar

Clone o repositório e entre na pasta do projeto:

```bash
git clone https://github.com/CayenneGn7/SistemasProjetos.git
cd SistemasProjetos
```

Compile o projeto com o Maven:

```bash
mvn clean compile
```

### Executar pelo terminal

```bash
mvn exec:java "-Dexec.mainClass=Main"
```

### Executar a interface gráfica

```bash
mvn exec:java "-Dexec.mainClass=TelaPrincipal"
```

### Executar a API REST

```bash
mvn exec:java "-Dexec.mainClass=api.Api"
```

A API utiliza a porta `7071`.

### Endpoints disponíveis

| Método HTTP | Endpoint | Operação |
|---|---|---|
| GET | `/` | Verificar a mensagem inicial da API |
| GET | `/api/projetos` | Listar projetos |
| GET | `/api/projetos/{id}` | Consultar um projeto |
| POST | `/api/projetos` | Cadastrar um projeto |
| PUT | `/api/projetos/{id}` | Atualizar um projeto |
| DELETE | `/api/projetos/{id}` | Excluir um projeto |

## Status do projeto

Em desenvolvimento.
