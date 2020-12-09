create table  usuario(
	id bigint not null auto_increment,
    nome varchar(100) not null,
    email varchar(100) not null,
    senha varchar(255) not null,
    data_cadastro datetime not null,
    constraint usuario_pk primary key (id)
)engine=InnoDB default charset=utf8;

create table  grupo(
	id bigint not null auto_increment,
    nome varchar(100) not null,
    constraint grupo_pk primary key (id)
)engine=InnoDB default charset=utf8;

create table  forma_pagamento(
	id bigint not null auto_increment,
    descricao varchar(100) not null,
    constraint forma_pagamento_pk primary key (id)
)engine=InnoDB default charset=utf8;

create table  permissao(
	id bigint not null auto_increment,
    nome varchar(100) not null,
    descricao varchar(100) not null,
    constraint permissao_pk primary key (id)
)engine=InnoDB default charset=utf8;

create table  estado(
	id bigint not null auto_increment,
    nome varchar(100) not null,
    constraint estado_pk primary key (id)
)engine=InnoDB default charset=utf8;

create table  cidade(
	id bigint not null auto_increment,
    nome varchar(100) not null,
    id_estado bigint not null,
    constraint cidade_pk primary key (id),
    constraint cidade_fk_estado foreign key (id_estado) references  estado(id)
)engine=InnoDB default charset=utf8;

create table  cozinha(
	id bigint not null auto_increment,
    nome varchar(100) not null,
    constraint cozinha_pk primary key (id)
)engine=InnoDB default charset=utf8;

create table  restaurante(
	id bigint not null auto_increment,
    nome varchar(100) not null,
    taxa_frete numeric(5,2),
    data_cadastro datetime not null,
    data_atualizacao datetime not null,
    endereco_cep varchar(10),
    endereco_logradouro varchar(255),
    endereco_numero varchar(20),
    endereco_complemento varchar(100),
    endereco_bairro varchar(100),
    endereco_cidade_id bigint,
    id_cozinha bigint,
    constraint restaurante_pk primary key (id),
    constraint restaurante_fk_cidade foreign key (endereco_cidade_id) references  cidade(id),
    constraint restaurante_fk_cozinha foreign key (id_cozinha) references  cozinha(id)
)engine=InnoDB default charset=utf8;

create table  produto(
	id bigint not null auto_increment,
    nome varchar(100) not null,
    descricao varchar(255) not null,
    preco numeric(14,2),
    ativo boolean not null,
    id_restaurante bigint,
    constraint produto_pk primary key (id),
    constraint produto_fk_restaurante foreign key (id_restaurante) references  restaurante(id)
)engine=InnoDB default charset=utf8;

create table  grupo_permissao(
	grupo_id bigint not null,
    permissao_id bigint not null,
    constraint grupo_permissao_pk primary key (grupo_id, permissao_id),
    constraint grupo_permissao_fk_grupo foreign key (grupo_id) references  grupo(id),
    constraint grupo_permissao_fk_permissao foreign key (permissao_id) references  permissao(id)
)engine=InnoDB default charset=utf8;

create table  restaurante_forma_pagamento(
	restaurante_id bigint not null,
    forma_pagamento_id bigint not null,
    constraint restaurante_forma_pagamento_pk primary key (restaurante_id, forma_pagamento_id),
    constraint restaurante_forma_pagamento_fk_rt foreign key (restaurante_id) references  restaurante(id),
    constraint restaurante_forma_pagamento_fk_fp foreign key (forma_pagamento_id) references  forma_pagamento(id)
)engine=InnoDB default charset=utf8;
