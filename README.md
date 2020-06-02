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

### Cadastrando vários endereços para o Cliente
> Exemplo do cadastro de vários endereços mediante envio de uma lista de ceps.
> Os ceps são consultados via serviço: https://viacep.com.br/.

##### POST http://localhost:8080/address/zipcode
request
```
{
	"idCustomer": 1,
	"zipCodes": [
		"76875845",
		"25267142",
		"44052054",
		"69312528"
		]
}
```
response
```
{
    "data": {
        "id": 1,
        "name": "Mariazinha",
        "email": "mariazinha@email.com",
        "address": [
            {
                "id": 1,
                "zipCode": "76875-845",
                "publicPlace": "Avenida das Esmeraldas",
                "complement": "até 5300/5301",
                "neighborhood": "Parque das Gemas",
                "locality": "Ariquemes",
                "uf": "RO"
            },
            {
                "id": 2,
                "zipCode": "25267-142",
                "publicPlace": "Rua Echeverria",
                "complement": "(Lot Jd Rotsen l)",
                "neighborhood": "Barro Branco",
                "locality": "Duque de Caxias",
                "uf": "RJ"
            },
            {
                "id": 3,
                "zipCode": "44052-054",
                "publicPlace": "1ª Travessa Juiz de Fora",
                "complement": "",
                "neighborhood": "CASEB",
                "locality": "Feira de Santana",
                "uf": "BA"
            },
            {
                "id": 4,
                "zipCode": "69312-528",
                "publicPlace": "Rua Valmir Sabino de Oliveira",
                "complement": "",
                "neighborhood": "Centenário",
                "locality": "Boa Vista",
                "uf": "RR"
            }
        ]
    },
    "errors": []
}
```
