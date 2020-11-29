# startSpring
Projeto spring sem dependências inicialmente. Prentendo adicionar dependência conforme a necessidade
## Ambiente e ferramentas
Desenvolvimento utilizando o Ubuntu 20.04.1 LTS e Intelij 2020.2.4 Community Edition
#### Requisitos e passos para instalar
Para gerenciar a versão do java utilizei o sdkman https://sdkman.io. Passos para instalar o Sdkman e o Java 11. 
```
curl -s "https://get.sdkman.io" | bash
source "$HOME/.sdkman/bin/sdkman-init.sh"
sdk version
sdk install java 11.0.9-zulu
``` 
Instalar maven
```
sudo apt install maven
mvn --version
``` 
#### Passos para executar o projeto
Instalar dependências: Na pasta do projeto execute o comando para baixar as dependências do projeto
```
mvn install
``` 
Executar o projeto: Na pasta do projeto execute o comando subir o projeto

```
mvn spring-boot:run
``` 
Executar os testes do projeto: Na pasta do projeto execute o comando executar os testes do projeto

```
mvn spring-boot:run test
``` 
#### Acesso ao banco de dados
O projeto utiliza banco de dados H2 Database Engine
##### Detalhes conexão
**Url :** http://localhost:8080/h2-console

**JDBC Url :** jdbc:h2:mem:banco

**User Name :** desenvolvimento

**Password :** acessolivre