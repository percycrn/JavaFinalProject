create table client(
	clientName nchar(20) primary key not null,
	password nchar(20) not null
)
create table friend(
	clientName nchar(20) references client(clientName) ,
	friendName nchar(20) primary key(clientName,friendName)
)