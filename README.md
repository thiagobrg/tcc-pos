
# Projeto de Controle Financeiro Pessoal

## Descrição
Este projeto tem como objetivo ajudar os usuários a organizar suas finanças pessoais, permitindo o registro de receitas, despesas e a visualização dos dados através de dashboards. Desenvolvido durante a Pós Graduação do curso de Engenharia de Software da PUC Minas.

## Tecnologias Utilizadas
- **Java**: Backend da aplicação.
- **Spring Boot**: Framework utilizado no backend.
- **React**: Frontend da aplicação.
- **PostgreSQL**: Banco de dados relacional.

## Requisitos
Antes de iniciar, certifique-se de ter as seguintes ferramentas instaladas em seu ambiente:
- Java JDK (versão 11 ou superior)
- Node.js (versão 14 ou superior)
- npm (Node Package Manager)
- PostgreSQL

## Passo a Passo para Executar o Projeto

### 1. Clonar o Repositório
```bash
git clone https://github.com/thiagobrg/tcc-pos.git
cd tcc-pos
```

### 2. Configurar o Banco de Dados PostgreSQL
- Crie um banco de dados no PostgreSQL:
```sql
CREATE DATABASE controle_financeiro;
```
- Configure as credenciais do banco de dados no arquivo `src/main/resources/application.properties` do backend:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/controle_financeiro
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.jpa.hibernate.ddl-auto=update
```

### 3. Executar o Backend
1. Navegue até o diretório do backend:
```bash
cd backend
```
2. Compile e execute a aplicação Spring Boot:
```bash
./mvnw spring-boot:run
```

### 4. Executar o Frontend
1. Navegue até o diretório do frontend:
```bash
cd frontend
```
2. Instale as dependências do projeto:
```bash
npm install
```
3. Execute a aplicação React:
```bash
npm start
```

### 5. Acessar a Aplicação
- Após iniciar ambos os serviços, você poderá acessar a aplicação no navegador:
```url
http://localhost:3000
```

## Contato
Para mais informações ou suporte, entre em contato com o autor através do e-mail: [thiagobrg98@gmail.com](mailto:hiagobrg98@gmail.com).
