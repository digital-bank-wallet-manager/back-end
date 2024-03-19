Create table if not exists foreign_transfer (
                                                id varchar (6) primary key,
                                                account_ref_foreign varchar (50) not null unique
    );



alter table transfer
add column id_foreign_transfer varchar(6) references foreign_transfer (id) ;

alter table transfer
drop column record_date;