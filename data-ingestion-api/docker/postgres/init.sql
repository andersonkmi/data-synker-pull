--- Create all sequences ---
CREATE SEQUENCE invoiceseq;

--- Create all tables ---
CREATE TABLE invoice (
    idnumber bigint not null primary key DEFAULT NEXTVAL('invoiceseq'),
    invoiceid varchar(50) not null unique,
    invoicename varchar(50) not null,
    companyname varchar (120) not null,
    billtoname varchar(50) not null,
    amount numeric (10, 2) not null,
    creationdate timestamp with time zone not null,
    lastmodificationdate timestamp with time zone not null
);