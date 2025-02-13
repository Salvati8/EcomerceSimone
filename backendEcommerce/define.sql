
    create table carrello (
        id integer not null auto_increment,
        id_utente integer,
        prezzo float(53),
        quantita integer,
        primary key (id)
    ) engine=InnoDB;

    create table carrello_prodotto (
        carrello_id integer,
        id integer not null auto_increment,
        prodotto_id integer,
        quantita integer,
        primary key (id)
    ) engine=InnoDB;

    create table messaggi_sistema (
        code varchar(255) not null,
        lang varchar(255) not null,
        messaggio varchar(255),
        primary key (code, lang)
    ) engine=InnoDB;

    create table ordini (
        cap integer not null,
        id integer not null auto_increment,
        id_prodotto integer,
        id_utente integer,
        prezzo_totale float(53) not null,
        quantità integer not null,
        data_ordine datetime(6) not null,
        indirizzo_spedizione varchar(100) not null,
        stato varchar(100) not null,
        primary key (id)
    ) engine=InnoDB;

    create table pagamenti (
        id integer not null auto_increment,
        id_ordine integer,
        importo float(53) not null,
        data_pagamento datetime(6) not null,
        metodo_pagamento varchar(100) not null,
        stato_pagamento varchar(100) not null,
        primary key (id)
    ) engine=InnoDB;

    create table prodotti (
        id integer not null auto_increment,
        prezzo float(53),
        quantita_disponibile integer,
        categoria varchar(255),
        colore varchar(255),
        descrizione varchar(255),
        marca varchar(255),
        nome varchar(255),
        size varchar(255),
        url_img varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table recensioni (
        id integer not null auto_increment,
        id_prodotto integer,
        id_utente integer,
        valutazione integer not null,
        data_recensione TIMESTAMP DEFAULT CURRENT_TIMESTAMP null,
        commento varchar(100) not null,
        primary key (id)
    ) engine=InnoDB;

    create table utenti (
        id integer not null auto_increment,
        ruolo tinyint check (ruolo between 0 and 2),
        cognome varchar(100) not null,
        email varchar(100) not null,
        hash_password varchar(100) not null,
        indirizzo_fatturazione varchar(100) not null,
        indirizzo_spedizione varchar(100) not null,
        nome varchar(100) not null,
        numero_telefono varchar(100) not null,
        primary key (id)
    ) engine=InnoDB;

    alter table carrello 
       add constraint UKl287dga2nb4ahi1j34on39ruk unique (id_utente);

    alter table carrello 
       add constraint FK776wjw2xhhhnvng0dcvarv4tg 
       foreign key (id_utente) 
       references utenti (id);

    alter table carrello_prodotto 
       add constraint FKf22k9i16cc0y30kre0qf8lx16 
       foreign key (carrello_id) 
       references carrello (id);

    alter table carrello_prodotto 
       add constraint FK73wyc9yy2sf2epe9nkaexvbav 
       foreign key (prodotto_id) 
       references prodotti (id);

    alter table ordini 
       add constraint FKstu4ffvdir0e3p3xg8se2a4un 
       foreign key (id_prodotto) 
       references prodotti (id);

    alter table ordini 
       add constraint FKqj5n4mdh5tc9qkj7hx54mvgi4 
       foreign key (id_utente) 
       references utenti (id);

    alter table pagamenti 
       add constraint FKb19bn6ntprd4pcu61w9fpyqin 
       foreign key (id_ordine) 
       references ordini (id);

    alter table recensioni 
       add constraint FK5wtirys8opmifrpgxxe3lla6q 
       foreign key (id_prodotto) 
       references prodotti (id);

    alter table recensioni 
       add constraint FK3qtralkbontl617niblqxy543 
       foreign key (id_utente) 
       references utenti (id);
