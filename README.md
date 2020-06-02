# Custumer Service

### Requisitos

1. JDK 8
1. Maven 3

### Rodando

1. Clone o projeto: `https://github.com/Michael-Silva-de-Sousa/prova-java-tokio-backend.git`
1. Entre na pasta `tokio-test` e execute: `mvn spring-boot:run`

### Requisitos pedidos na prova
- [x] Criar um novo cliente, editar um cliente e excluir um cliente.
- [x] Valide os dados antes de cadastrar ou editar.
- [x] Pagine a listagem de clientes.
- [x] Possibilite o cadastro de múltiplos endereços para um cliente.
- [x] No cadastro de endereço permita inserir apenas o CEP carregando os dados via consumo do serviço: https://viacep.com.br/
- [x] Tratamento de exceções (RestControllerAdvice).
- [x] Testes.
- [x] Validações.
- [x] Autenticação.
- [x] Documentação. http://localhost:8080/swagger-ui.html
- [ ] Frontend. (Em desenvolvimento, será em Angular2)

### Tecnologias utilizadas
- Java8
- SpringBoot
- SpringSecurity
- JWT
- JUnit
- Mockito
- Swagger
- Maven

### Padrões utilizados
- Rest
- DTO
- MVC


### Obtendo o Token
> Para usar os recursos do backend é necessário estar autenticado e pedir o token para o backend.
> Segue uma maneira de obter um token via PostMan. Pelo Swagger também é possível.

##### POST http://localhost:8080/users/auth
request
```
{
	"login":"admin",
	"password":"1234"
}
```
response
```
{
    "login": "admin",
    "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTU5MTExMzkwMn0.g_hnYMhR6n2pAE9oO2dEr9aTDI2ZjhQBzH4L-6GYC3Tr5n6aIFqMZYbW6fqwIUwoZumflNUbHjFsJZHsRZ8VQg"
}
```

**Obs ao configurar o Authorization no PostMan não esquecer de colocar o value dessa forma**
```
Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTU5MTExMzkwMn0.g_hnYMhR6n2pAE9oO2dEr9aTDI2ZjhQBzH4L-6GYC3Tr5n6aIFqMZYbW6fqwIUwoZumflNUbHjFsJZHsRZ8VQg
```

