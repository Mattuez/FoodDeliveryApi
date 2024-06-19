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
- Upload de imagens, armazenando-as em uma pasta local e disponibilizando-as para consulta posterior, facilitando a gestão de fotos de produtos.

# Tecnologias Utilizadas
- Backend: Spring Boot, Spring Data JPA, Spring Mail
- Segurança: Spring Security, OAuth2, JWT
- Banco de Dados: MySQL
- Versionamento do Banco de Dados: Flyway
- Containerização: Docker
- Balanceamento de Carga: Nginx

# Como usar
- Git clone https://github.com/Mattuez/FoodDeliveryApi.git
- ./mvnw clean package
- docker-compose up --scale food-delivery-api=2
- Pronto a aplicação está rodando. Consulte a documentação para ver os endpoints.
- Infelizmente a feature de envio de emails transacionais precisa de uma chave privada que não pode ser disponibilizada aqui. Caso tenha interesse entre em contato comigo

# Obseravações
- O projeto ainda está em desenvolvimento. Pretendo implantar o upload de imagens para Amazon s3 e coloca-lo em produção na nuvem.
