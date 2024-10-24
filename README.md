# BarbeariaCarlos 
[![NPM](https://img.shields.io/npm/l/react)](https://github.com/devsuperior/sds1-wmazoni/blob/master/LICENSE) 

# Sobre o projeto

BarbeariaMendes  é uma aplicação full stack desktop construída durante o 4ª semestre do Curso analise e desenvolvimento de sistemas organizado pela Faculdade Impacta.

A aplicação consiste em um software desktop feito para uma barbearia que serve para cadastrar os cliente, serviços, barbeiros e realizar agendamentos por data e hora.


## Requisitos do projeto
- Java 17
- JavaFX
- MySql
- Eclipse

## Layout web

<p align="center">
    <img width="800" height="600" src="https://github.com/SanFisherBRK/Dashboard-feita-em-React-para-a-Api-vendinha/blob/main/src/ImagensGitHub/Layout2.gif"
</p>


## Diagrama de classes

<p align="center">
    <img width="800" height="600" src="https://github.com/SanFisherBRK/Dashboard-feita-em-React-para-a-Api-vendinha/blob/main/src/ImagensGitHub/Layout2.gif"
</p>

# Tecnologias utilizadas

## Front end
- JavaFX

## Implantação em produção
- Back end: Java Desktop
- Front end JavaFx Desktop
- Banco de dados: Mysql


## Front end
Pré-requisitos: Java 17

#Crie as tabelas do banco de dados.

create table usuario(
	idusuario INT auto_increment primary Key unique not null,
    nome varchar(255) not null,
	email varchar(255) not null,
	login varchar(45) not null,
	senha varchar(255) not null
);

create table cliente(
	idcliente INT auto_increment primary Key unique not null,
    nome varchar(255) not null,
	email varchar(255) not null,
	telefone varchar(45) not null,
	endereco varchar(255) not null,
	cpf varchar(11) not null unique,
	usuario_id int,
	foreign key (usuario_id) references usuario(idusuario)
);

create table barbeiro(
	idbarbeiro INT auto_increment primary Key unique not null,
    nome varchar(255) not null,
	email varchar(255) not null,
	telefone varchar(45) not null,
	endereco varchar(255) not null,
	cpf varchar(11) not null unique,
	usuario_id int,
	foreign key (usuario_id) references usuario(idusuario)
);

create table servico(
	idservico INT auto_increment primary Key unique not null,
    nome varchar(255) not null,
	descricao varchar(255) not null,
	preco DECIMAL(10,2) NOT NULL,
	usuario_id int,
	foreign key (usuario_id) references usuario(idusuario)
);

create table agendamento(
	idagendamento INT auto_increment primary Key unique not null,
    datahora TIMESTAMP,
	cliente_id int,
	servico_id int,
	barbeiro_id int,
	foreign key (cliente_id) references cliente(idcliente),
    foreign key (servico_id) references servico(idservico),
    foreign key (barbeiro_id) references barbeiro(idbarbeiro)
);

```bash
# clonar repositório
git clone git@github.com:SanFisherBRK/Barbearia-Mendes.git

# Importar o projeto no eclipse

# criar uma pasta java-libs em c:
Baixar o conector mysql-connector-j-9.0.0.jar e colocar dentro da pasta java-libs.
Baixar o javafx-sdk-22.0.2 e colocar dentro da pasta java-libs.
Configurar o javafx-sdk-22.0.2 e mysql-connector-j-9.0.0.jar no seu projeto dentro do eclipse.

# Como executar o projeto
Clicar em Run Main para executar o projetos.
```

# Autor

Francinaldo e Silva Souza.

https://www.linkedin.com/in/francinaldo-e-silva-sousa

Carlos Mendes.

https://github.com/carlosmendesjmc

Felipe Aparecido Dias.

https://github.com/felipeaparecidodias

Elisa Sobrinho.

https://github.com/EcosElisa

