
    create table carrello (
        id integer not null auto_increment,
        id_prodotto integer,
        prezzo float(53) not null,
        quantita integer not null,
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
        prezzo float(53) not null,
        quantita_disponibile integer not null,
        categoria varchar(100) not null,
        colore varchar(100) not null,
        descrizione varchar(100) not null,
        marca varchar(100) not null,
        nome varchar(100) not null,
        size varchar(100) not null,
        url_immagine varchar(100) not null,
        primary key (id)
    ) engine=InnoDB;

    create table recensioni (
        id integer not null auto_increment,
        id_prodotto integer,
        id_utente integer,
        valutazione integer not null,
        data_recensione datetime(6) not null,
        commento varchar(100) not null,
        primary key (id)
    ) engine=InnoDB;

    create table utenti (
        id integer not null auto_increment,
        id_carrello integer,
        ruolo tinyint check (ruolo between 0 and 2),
        data_creazione datetime(6) not null,
        cognome varchar(100) not null,
        email varchar(100) not null,
        hash_password varchar(100) not null,
        indirizzo_fatturazione varchar(100) not null,
        indirizzo_spedizione varchar(100) not null,
        nome varchar(100) not null,
        numero_telefono varchar(100) not null,
        primary key (id)
    ) engine=InnoDB;

    alter table utenti 
       add constraint UKg9ckeach3jbapa3vdntsowgkr unique (id_carrello);

    alter table carrello 
       add constraint FKn49bwe3p55s0lrbexr78rus5x 
       foreign key (id_prodotto) 
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

    alter table utenti 
       add constraint FKrvtkpy8x27183cu6wf19y8rje 
       foreign key (id_carrello) 
       references carrello (id);
