# CRUD de Produtos — Clean Architecture em Java

Sistema de linha de comando para gerenciamento de produtos (cadastro, listagem, busca, alteração, remoção e filtragem), desenvolvido como projeto de estudo de **Clean Architecture**, **TDD** e **SOLID** em Java.

## Funcionalidades

- **Cadastro**: nome, segmento, marca, valor e quantidade
- **Listagem**: exibe todos os produtos cadastrados
- **Busca por ID**: retorna um produto específico
- **Filtragem**: por marca e/ou segmento, de forma combinável
- **Alteração**: atualização parcial (só os campos informados são alterados)
- **Remoção**: remove um produto pelo ID

## Arquitetura

O projeto segue os princípios de Clean Architecture, com separação em camadas concêntricas onde as dependências sempre apontam para dentro (em direção ao domínio):

```bash
src/main/java/cortelucas/
├── domain/
│   ├── entities/
│   │   └── Product.java                 # Entidade com validação própria
│   └── exceptions/
│       └── ProductNotFoundException.java
├── application/
│   ├── dtos/                            # Contratos de Input/Output de cada use case
│   ├── repositories/
│   │   └── ProductsRepository.java      # Contrato (interface), sem detalhe de implementação
│   └── usecases/
│       ├── CreateProduct.java
│       ├── ListProducts.java
│       ├── FindProductById.java
│       ├── UpdateProduct.java
│       ├── DeleteProduct.java
│       └── FilterProducts.java
├── infrastructure/
│   └── repositories/
│       └── InMemoryProductsRepository.java  # Implementação concreta do repositório
├── presentation/
│   └── cli/
│       └── ProductMenu.java             # Interface de linha de comando
└── Main.java                            # Composition root
```

**Por que essa separação importa:** a camada de `domain` e `application` não conhece nada sobre `Scanner`, console ou como os dados são persistidos. Isso significa que o repositório em memória pode ser trocado por um banco de dados real (JDBC, JPA, etc.) — ou a interface de linha de comando por uma API REST — sem alterar uma única linha de regra de negócio.

### Fluxo de dependências

```bash
presentation → application → domain
infrastructure → application (implementa os contratos)
Main (composition root) → conhece todas as camadas, monta as dependências
```

## Tecnologias

- **Java 17**
- **Maven** (build e gerenciamento de dependências)
- **JUnit 5** (testes unitários)

## Como rodar

### Pré-requisitos

- JDK 17 ou superior
- Maven 3.8+

### Compilar e rodar os testes

```bash
mvn test
```

### Gerar o executável (.jar)

```bash
mvn clean package
```

### Rodar a aplicação

```bash
java -jar target/crud-projects-1.0-SNAPSHOT.jar
```

## Testes

O projeto foi desenvolvido seguindo **TDD** (red-green-refactor) do início ao fim: cada funcionalidade começou com um teste que falha, seguido da implementação mínima para fazê-lo passar.

- **31 testes** cobrindo entidade e todos os 6 casos de uso
- Testes de use case usam `InMemoryProductsRepository` como implementação real (sem mocks), garantindo que a integração entre camada de aplicação e persistência funciona de fato

```bash
mvn test
```

## Decisões de design

- **Entidade autovalidada**: `Product` nunca existe em estado inválido — todas as regras (nome mínimo de 3 caracteres, preço não negativo, etc.) são validadas no construtor e nos setters, não espalhadas pelo código que a utiliza.
- **Atualização parcial com `Optional`**: o caso de uso `UpdateProduct` recebe `Optional<T>` para cada campo alterável — um campo vazio (`Optional.empty()`) significa "não alterar", tornando explícita a intenção, ao contrário de usar `null` com múltiplos significados.
- **Filtro combinável**: `FilterProducts` aceita marca e segmento como critérios independentes e opcionais, que podem ser usados isoladamente ou em conjunto.
- **Exceção de domínio explícita**: em vez de retornar `null` quando um produto não é encontrado, os casos de uso lançam `ProductNotFoundException`, evitando `NullPointerException` na camada de apresentação.
- **Repositório fala a língua do domínio**: `ProductsRepository` trabalha diretamente com a entidade `Product`, não com DTOs de um caso de uso específico — mantendo o contrato de persistência desacoplado da aplicação.

## Possíveis evoluções

- Trocar `InMemoryProductsRepository` por uma implementação com banco real (JDBC ou JPA/Hibernate)
- Expor os casos de uso via API REST (ex: Spring Boot), reaproveitando toda a camada `domain`/`application` sem alterações
- Adicionar testes de integração para a camada `presentation`

## Autor

Lucas Corte
