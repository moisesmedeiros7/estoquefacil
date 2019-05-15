CREATE DATABASE estoquefacil2;
USE estoquefacil2;

CREATE TABLE Usuario(
id_usuario INT NOT NULL AUTO_INCREMENT,
nome VARCHAR (50) NOT NULL,
matricula VARCHAR (50),
email VARCHAR (50) NOT NULL,
senha VARCHAR (50) NOT NULL,

PRIMARY KEY (id_usuario)
);

CREATE TABLE Estoque(
id_estoque INT NOT NULL AUTO_INCREMENT,
nome VARCHAR(50) NOT NULL,
loc VARCHAR(50),
descricao VARCHAR(50),
endereco VARCHAR(50),
telefone VARCHAR(50),

PRIMARY KEY(id_estoque)
);

CREATE TABLE Produto(
id_produto INT NOT NULL AUTO_INCREMENT,
nome VARCHAR(50) NOT NULL,
unidade VARCHAR(50),
descricao VARCHAR(50),
status VARCHAR(50),
licitavel VARCHAR(50),
data_vencLic DATE,

est INT,

PRIMARY KEY(id_produto),
FOREIGN KEY(est) REFERENCES Estoque (id_estoque)  
);

CREATE TABLE Lote(
id_lote INT NOT NULL AUTO_INCREMENT,
numero_lote VARCHAR(50),
data_validade DATE,
data_entrada DATE NOT NULL,
quantidade int,
entradaInicial int,
prod INT,
data_modificacao DATE,
usr_modificacao INT,
justificativa_edit VARCHAR(50),

PRIMARY KEY(id_lote),
FOREIGN KEY(prod) REFERENCES Produto (id_produto)
);

CREATE TABLE Cliente(
id_cliente INT NOT NULL AUTO_INCREMENT,
nome VARCHAR(50),
cpf_cnpj VARCHAR(50),
endereco VARCHAR(50),
cidade VARCHAR(50),
email VARCHAR(50),
senha VARCHAR(50),

PRIMARY KEY(id_cliente)
);

CREATE TABLE Pedido(
numero INT NOT NULL AUTO_INCREMENT,
qtde INT,
data_pedido DATE,
hora_pedido DATETIME,
lot INT,
us INT,
cli int,

PRIMARY KEY(numero),
FOREIGN KEY(lot) REFERENCES Lote (id_lote),
FOREIGN KEY(us) REFERENCES Usuario (id_usuario),
FOREIGN KEY(cli) REFERENCES Cliente (id_cliente)
);

