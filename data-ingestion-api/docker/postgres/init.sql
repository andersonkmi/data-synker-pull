--- Create all sequences ---
CREATE SEQUENCE invoiceseq;
CREATE SEQUENCE invoicetrackingseq;

--- Create all tables ---
CREATE TABLE invoice (
    id bigint not null primary key DEFAULT NEXTVAL('invoiceseq'),
    invoiceid varchar(50) not null unique,
    invoicename varchar(50) not null,
    companyname varchar (120) not null,
    billtoname varchar(50) not null,
    amount numeric (10, 2) not null,
    status varchar(20) not null,
    creationdate timestamp with time zone not null,
    lastmodificationdate timestamp with time zone not null,
    version bigint not null
);

create table invoicetracking (
    id bigint not null primary key DEFAULT NEXTVAL('invoicetrackingseq'),
    invoiceid varchar(50) not null,
    creationdate  timestamp with time zone not null,
    lastmodificationdate timestamp with time zone not null,
    status varchar(10) not null check ((status = ANY ('{created, submitted, confirmed}'::text[])))
);