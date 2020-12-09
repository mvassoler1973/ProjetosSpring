create table pedido (
    id bigint not null auto_increment,
    subtotal decimal(10,2) not null,
    taxa_frete decimal(10,2) not null,
    valor_total decimal(14,2) not null,

    restaurante_id bigint not null,
    usuario_cliente_id bigint not null,
    forma_pagamento_id bigint not null,
    
    endereco_cidade_id bigint not null,
    endereco_cep varchar(10) not null,
    endereco_logradouro varchar(255) not null,
    endereco_numero varchar(20) not null,
    endereco_complemento varchar(100) null,
    endereco_bairro varchar(100) not null,
    
    status varchar(10) not null,
    data_criacao datetime not null,
    data_confirmacao datetime null,
    data_cancelamento datetime null,
    data_entrega datetime null,

    constraint pedido_pk primary key (id),

    constraint pedido_Fk_endereco_cidade foreign key (endereco_cidade_id) references cidade (id),
    constraint pedido_Fk_restaurante foreign key (restaurante_id) references restaurante (id),
    constraint pedido_Fk_usuario_cliente foreign key (usuario_cliente_id) references usuario (id),
    constraint pedido_Fk_forma_pagamento foreign key (forma_pagamento_id) references forma_pagamento (id)
) engine=InnoDB default charset=utf8;

create table item_pedido (
    id bigint not null auto_increment,
    quantidade smallint(6) not null,
    preco_unitario decimal(14,2) not null,
    preco_total decimal(14,2) not null,
    observacao varchar(255) null,
    pedido_id bigint not null,
    produto_id bigint not null,
    
    constraint item_pedido_pk primary key (id),
    unique key item_pedido_uk_produto (pedido_id, produto_id),

    constraint item_pedido_fk_pedido foreign key (pedido_id) references pedido (id),
    constraint item_pedido_fk_produto foreign key (produto_id) references produto (id)
) engine=InnoDB default charset=utf8;