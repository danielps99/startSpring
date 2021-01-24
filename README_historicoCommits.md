#### commit 44700db969efcf7fd19b6024cb477846602c65f2

    Autenticação via JWT e autorização.
      -> Configurado dois usuários com perfis de acesso (ADMIN, EDITOR) em memória.
      -> Autorização no ProdutoControler de acordo com o perfil de acesso do usuário.
      -> TokenAuthenticationService deixou de ser estático para ser um service @Service.

#### commit 7c6ee65957f60df7a629e0513fdd52ee042bbe7c

    Autenticação jwt com usuários em memória

#### commit 41e74eb3dd41aa563aee03001193facd977af502

    Adicionar Autorização com usuários em memória

#### commit 9c7eabe36efffd8c2826549bc08410909bb8ad79

    Retornando e recebendo DTO. Utilização do ModelMapper

#### commit d44ea26a7870f7c2358c2cb2dc231fa19883cc7f

    Alteração: Metodo salvar retorna somente id ao invés de retornar a entidade.

#### commit ad08e6dd6ab7608fddc9eeb77e5ef7c80306c1d1

    CRUD Rest full

#### commit dc437713ce6c481a747a7358b9a15d8977cdce6b

    Adicionar facilidades Lombok
    -> @Data
    -> @Builder
    -> @NoArgsConstructor
    -> @AllArgsConstructor

#### commit 0e56718455bb44738fbf0f484289debb3fc6b500

    Definir profile dev e test
    Os profiles funcionan ao executar com 
            mvn srping-boot:run
        e 
            mvn spring-boot:test 
        para setar o profile, ao executar StartSpringApplication no Intelij, passe o parmetro: 
            --spring.profiles.active=dev 
    Apesar de funcionar, no commit 'Corrigindo file banco h2', troquei a linha:
        spring.datasource.url=jdbc:h2:~/file:banco
    por:
        spring.datasource.url=jdbc:h2:file:~/banco

#### commit 2277157d6d8d623ed33befb924a50cf7d3cc8931

    Depois de acicionar auditoria, os testes requer essas dependências. Pelo que li o erro que dava acontece no java 11. No java 10 não acontece

#### commit eb78e7602d83d7742b5bc45bd420313ce1cc0c57

    Audited Table. AuditTable. Auditoria tabela produto

#### commit 073a51291a36851fb67b1e3db9bd1acb16c744f9

    Testes CRUD produtoService

#### commit 57d42a6c77b62c594ae2d322787a448aed3cc20d

    Gerar id entity. @GeneratedValue(strategy = GenerationType.IDENTITY)

#### commit b3edd67e518c9b76bfc941745df60f67d12f3a35

    Salvar Produto com teste

#### commit 063395db556d6f2d51f96567bc8cbdf19ccbef69

    Adicionar liquibase e criar tabela produto

#### commit 7002934026bff4d57b267a29e41e4b2696e91ada

    Configuração banco h2
    spring-boot-starter-web -> Dependência responsável por tornar o projeto acessível via navegador
    spring-boot-starter-data-jpa-> Dependência responsável por comunicar banco e aplicação
    h2 -> Dependência referênte ao banco de dados em memória.