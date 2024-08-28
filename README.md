# Sistema de Gerenciamento de Estoque e Vendas

Este projeto é um sistema de gerenciamento de estoque e vendas desenvolvido em JavaFX, utilizando o padrão de arquitetura MVC (Model-View-Controller). O sistema permite a gestão de clientes, fornecedores, funcionários, produtos, e vendas de uma empresa de informática, oferecendo funcionalidades como autenticação de usuários, controle de permissões, relatórios em gráficos de vendas, e muito mais.

## Funcionalidades Principais

### 1. Autenticação de Usuários
- Tela de login segura, que permite a autenticação de usuários por meio de senha criptografada utilizando **bcrypt**.
- Funcionalidade de logout para garantir a segurança das informações.

### 2. Controle de Permissões
- Permissões de acesso para diferentes tipos de usuários (administradores, funcionários).
- Somente usuários autorizados podem realizar operações de exclusão e alteração de dados sensíveis.

### 3. Gerenciamento de Clientes, Fornecedores, Funcionários, Produtos e Vendas
- **CRUD Completo**: Criar, ler, atualizar e deletar informações de clientes, fornecedores, funcionários, e produtos.
- Validação de dados na entrada de informações para garantir a integridade dos registros.

### 4. Relatórios Gráficos
- Gráfico de barras que exibe a quantidade de vendas por dia, permitindo uma análise visual das vendas ao longo do tempo.
- Uso de **JavaFX Charts** para a geração de gráficos interativos.

### 5. Gerenciamento de Telas
- Navegação entre diferentes telas (Clientes, Fornecedores, Funcionários, Produtos, Vendas, etc.) de forma eficiente.

## Estrutura do Projeto

O projeto segue o padrão **MVC (Model-View-Controller)**, dividindo claramente as responsabilidades:

- **Model**: Contém as classes de entidade e mapeamento do banco de dados utilizando **JPA/Hibernate**.
- **View**: Arquivos FXML que definem a interface do usuário, bem como o uso de CSS para estilização.
- **Controller**: Classes Java que manipulam eventos da interface do usuário e coordenam a lógica entre a interface e o modelo de dados.
- **DAO**: Classes de acesso aos dados armazenados no banco de dados, contendo operações simples e específicas para cada entidade.

## Tecnologias utilizadas

- **Eclipse IDE**
- **Java 17**
- **JavaFX 22**
- **MySQL 8**
- **Maven**
- **Scene Builder**
- **BCrypt**
