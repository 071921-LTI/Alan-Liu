drop table if exists Items,Sales,Customers,Employee;

CREATE TABLE if not exists Items (
	iid serial NOT NULL,
	iname varchar(20) NOT NULL,
	price numeric(5, 2) NOT NULL,
	quantity int4 NOT NULL,
	CONSTRAINT items_iname_key UNIQUE (iname),
	CONSTRAINT items_pkey PRIMARY KEY (iid)
);


CREATE table if not exists Customers (
	cid serial NOT NULL,
	cname varchar(10) NOT NULL,
	cpassword varchar(20) NOT NULL,
	CONSTRAINT customers_cname_key UNIQUE (cname),
	CONSTRAINT customers_pkey PRIMARY KEY (cid)
);

CREATE TABLE if not exists Employee (
	eid serial NOT NULL,
	ename varchar(10) NOT NULL,
	epassword varchar(20) NOT NULL,
	CONSTRAINT employee_ename_key UNIQUE (ename),
	CONSTRAINT employee_pkey PRIMARY KEY (eid)
);

CREATE TABLE if not exists Sales (
	sid serial NOT NULL,
	iid int4 NOT NULL,
	cid int4 NOT NULL,
	sdate date NULL DEFAULT now(),
	snum int4 NOT NULL,
	CONSTRAINT sales_pkey PRIMARY KEY (sid),
	CONSTRAINT sales_cid_fkey FOREIGN KEY (cid) REFERENCES public.customers(cid),
	CONSTRAINT sales_iid_fkey FOREIGN KEY (iid) REFERENCES public.items(iid)
);
