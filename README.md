# FoodDeliveryApi - MVP
Este é um Produto Mínimo Viável (MVP) de uma API RESTful para gerenciamento de pedidos de delivery de comida, desenvolvido com foco em segurança, escalabilidade e facilidade de uso.

# Descrição
A API Delivery oferece endpoints para gerenciar usuários, restaurantes, produtos, pedidos, meios de pagamentos e etc. A segurança é garantida com Spring Security, OAuth2 e tokens JWT, enquanto o envio de emails transacionais é feito com Spring Mail. A aplicação é dockerizada e utiliza Nginx para balanceamento de carga.

# Features
- Autenticação e Autorização: Spring Security, OAuth2 e JWT para garantir acesso seguro aos recursos da API.
- Envio de Emails Transacionais: Notificações de status do pedido.
- Dockerização: Facilita a implantação e escalabilidade da aplicação.
- Balanceamento de Carga: Nginx para implantação de Poor's Man Load Balance.
- Versionamento do Banco de Dados: Flyway para gerenciar as migrações do banco de dados MySQL.
- Endpoint de geração de Jasper Reports
- Upload de imagens, armazenando-as em uma pasta local e disponibilizando-as para consulta posterior, facilitando a gestão de fotos de produtos.

# Tecnologias Utilizadas
- Backend: Spring Boot, Spring Data JPA, Spring Mail
- Segurança: Spring Security, OAuth2, JWT
- Banco de Dados: MySQL
- Versionamento do Banco de Dados: Flyway
- Containerização: Docker
- Balanceamento de Carga: Nginx

# Como usar
- Clonar o Repositório: git clone https://github.com/Mattuez/FoodDeliveryApi.git
- Build: ./mvnw clean package
- docker-compose up --scale food-delivery-api=2
- Executar: docker-compose up --scale food-delivery-api=2
- A aplicação estará rodando. Consulte a documentação para ver os endpoints disponíveis.

# Obseravações
- Envio de Emails: A funcionalidade de envio de emails transacionais requer uma chave privada que não pode ser disponibilizada publicamente. Entre em contato para mais informações.
- Em Desenvolvimento: O projeto ainda está em desenvolvimento e futuras melhorias incluem a migração do armazenamento de imagens para Amazon S3 e a implantação em produção na nuvem.
