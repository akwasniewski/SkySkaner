
CREATE EXTENSION IF NOT EXISTS cube;
CREATE EXTENSION IF NOT EXISTS earthdistance;


drop table if exists Airplane_Models cascade;
create table Airplane_Models(
	Id_Model numeric(6) constraint pk_models primary key,
	TheName varchar(250) not null,
	Capacity_Economy numeric(3),
	Capacity_Business numeric(3),
	Capacity_First numeric(3)
);

drop table if exists Airplanes cascade;
create table Airplanes(
	Id_Airplane numeric(6) constraint pk_airplanes primary key,
	Id_Model numeric(6) references Airplane_Models(Id_model) not null
);

drop table if exists Users cascade;
create table Users (
Id_User numeric(6) constraint pk_users primary key,
Username varchar(250) not null,
Password varchar(250) not null
);

drop table if exists Countries cascade;
create table Countries (
Id_Country numeric(6) constraint pk_countries primary key,
TheName varchar(250) not null
);

drop table if exists  Cities cascade;
create table Cities (
Id_City numeric(6) constraint pk_cities primary key,
TheName varchar(250) not null,
Id_Country numeric(6) references Countries(Id_Country) not null ,
Location point not null,
Timezone numeric(3) not null
);

drop table if exists Airports cascade;
create table Airports (
	Id_Airport numeric(6) constraint pk_airports primary key,
	Id_City numeric(6) references Cities(Id_City) not null,
	Code varchar(3) not null,
	TheName varchar(250) not null
);

drop table if exists Connections cascade;
create table Connections (
	Id_Connection numeric(6) constraint pk_connections primary key,
	Id_Airport_From numeric(6) references Airports(Id_Airport) not null,
	Id_Airport_To numeric(6) references Airports(Id_Airport) not null,
	Departure_Time time not null,
	Arrival_Time time not null
);

drop table if exists Airlines cascade;
create table Airlines(
	Id_Airline numeric(6) constraint pk_airlines primary key,
	Id_Airport numeric(6) references Airports(Id_Airport) not null,
	TheName varchar(250) not null
);

drop table if exists Airlines_Airplanes;
create table Airlines_Airplanes(
	Id_Airplane numeric(6) references Airplanes(Id_Airplane) not null,
	Id_Airline numeric(6) references Airlines(Id_Airline) not null
);

drop table if exists Terminals cascade;
create table Terminals (
Id_Terminal numeric(6) constraint pk_terminals primary key,
Id_Airport numeric(6) references Airports(Id_Airport) not null,
Terminal_Number numeric(6) not null
);


drop table if exists Flights cascade;
create table Flights (
	Id_Flight numeric(6) constraint pk_flights primary key,
	Id_Terminal numeric(6) references Terminals(Id_Terminal) not null,
	Id_Connection numeric(6) references Connections(Id_Connection) not null,
	Id_Airplane numeric(6) references Airplanes(Id_Airplane) not null,
	TheDate date
);

drop table if exists Tickets cascade;
create table Tickets (
	Id_Ticket numeric(6) constraint pk_tickets primary key,
	Id_Flight numeric(6) references Flights(Id_Flight) not null,
	Class numeric(1) not null,
	Price numeric(4) not null
);

drop table if exists User_Tickets;
create table User_Tickets (
	Id_Ticket numeric(6) references Tickets(Id_Ticket) not null,
	Id_User numeric(6) references Users(Id_User) not null,
	Is_Child boolean,
	LuggageType numeric(1)
);

insert into Tickets values
	(1, 1, 3, 300),
	(2, 1, 2, 1000),
	(3, 1, 1, 3000),
	(4, 2, 3, 200),
	(5, 3, 3, 400),
	(6, 3, 2, 1200),
	(7, 4, 3, 75),
	(8, 5, 3, 1000),
	(9, 5, 2, 2500),
	(10, 6, 3, 700),
	(11, 6, 2, 3000),
	(12, 6, 1, 9000),
	(13, 7, 3, 400),
	(14, 8, 3, 530),
	(15, 9, 3, 480),
	(16, 10, 3, 100),
	(17, 10, 2, 1300),
	(18, 11, 3, 400),
	(19, 11, 2, 2137),
	(20, 12, 3, 420),
	(21, 13, 3, 500),
	(22, 13, 2, 1500),
	(23, 13, 1, 3001),
	(24, 14, 3, 600),
	(25, 14, 2, 1677),
	(26, 15, 3, 200),
	(27, 15, 2, 1500),
	(28, 16, 3, 69),
	(29, 17, 3, 560),
	(30, 17, 2, 1100),
	(31, 17, 1, 9999),
	(32, 18, 3, 1000),
	(33, 18, 2, 3000),
	(34, 18, 1, 9000),
	(35, 19, 3, 600),
	(36, 19, 2, 2000),
	(37, 19, 1, 6000);

insert into User_Tickets values
(1, 1, true, 2),
(1, 2, false, 1),
(2, 3, true, 3),
(2, 4, false, 2),
(3, 5, true, 1),
(3, 6, false, 3),
(4, 7, true, 2),
(4, 8, false, 1),
(5, 9, true, 3),
(5, 10, false, 2),
(6, 11, true, 1),
(6, 12, false, 3),
(7, 13, true, 2),
(7, 14, false, 1),
(8, 15, true, 3),
(8, 16, false, 2),
(9, 17, true, 1),
(9, 18, false, 3),
(10, 19, true, 2),
(10, 20, false, 1),
(11, 21, true, 3),
(11, 22, false, 2),
(12, 23, true, 1),
(12, 24, false, 3),
(13, 25, true, 2),
(13, 26, false, 1),
(14, 27, true, 3),
(14, 28, false, 2),
(15, 29, true, 1),
(15, 30, false, 3),
(16, 1, true, 2),
(16, 2, false, 1),
(17, 3, true, 3),
(17, 4, false, 2),
(18, 5, true, 1),
(18, 6, false, 3),
(19, 7, true, 2),
(19, 8, false, 1),
(1, 9, true, 3),
(1, 10, false, 2),
(2, 11, true, 1),
(2, 12, false, 3),
(3, 13, true, 2),
(3, 14, false, 1),
(4, 15, true, 3),
(4, 16, false, 2),
(5, 17, true, 1),
(5, 18, false, 3);

insert into Airports values
	(1, 1, 'LHR', 'London Heathrow Airport'),
	(2, 2, 'CDG', 'Charles de Gaulle Airport'),
	(3, 3, 'TXL', 'Berlin Tegel Airport'),
	(4, 4, 'MAD', 'Adolfo Suárez Madrid-Barajas Airport'),
	(5, 5, 'FCO', 'Leonardo da Vinci International Airport'),
	(6, 6, 'ATH', 'Eleftherios Venizelos International Airport'),
	(7, 7, 'DUB', 'Dublin Airport'),
	(8, 8, 'OSL', 'Oslo Airport, Gardermoen'),
	(9, 9, 'VIE', 'Vienna International Airport'),
	(10, 10, 'BRU', 'Brussels Airport'),
	(11, 11, 'WAW', 'Warsaw Chopin Airport'),
	(12, 12, 'LIS', 'Lisbon Portela Airport'),
	(13, 13, 'ARN', 'Stockholm Arlanda Airport'),
	(14, 14, 'BUD', 'Budapest Ferenc Liszt International Airport'),
	(15, 15, 'CPH', 'Copenhagen Airport'),
	(16, 16, 'PRG', 'Václav Havel Airport Prague'),
	(17, 17, 'HEL', 'Helsinki Airport'),
	(18, 18, 'OTP', 'Henri Coandă International Airport'),
	(19, 19, 'AMS', 'Amsterdam Airport Schiphol'),
	(20, 20, 'BRN', 'Bern Airport');

insert into Connections values
	(1, 3, 10, '14:25:00', '17:10:00', 56),
	(2, 5, 7, '19:45:00', '23:15:00', 78),
	(3, 8, 6, '07:30:00', '10:00:00', 146),
	(4, 2, 9, '14:10:00', '16:40:00', 46),
	(5, 4, 1, '09:15:00', '11:25:00', 96),
	(6, 1, 7, '15:30:00', '18:10:00', 43),
	(7, 10, 2, '08:40:00', '10:50:00', 45),
	(8, 3, 9, '13:55:00', '16:25:00', 92),
	(9, 6, 8, '11:30:00', '14:10:00', 73),
	(10, 5, 4, '16:20:00', '18:35:00', 163),
	(11, 7, 3, '07:20:00', '10:00:00', 157),
	(12, 2, 6, '12:45:00', '15:15:00', 84),
	(13, 1, 5, '08:50:00', '11:25:00', 136),
	(14, 4, 10, '15:10:00', '17:40:00', 24),
	(15, 8, 1, '10:30:00', '12:50:00', 64),
	(16, 9, 7, '17:00:00', '20:05:00', 56),
	(17, 10, 5, '08:15:00', '11:00:00', 73),
	(18, 6, 3, '13:40:00', '16:10:00', 37),
	(19, 2, 4, '10:20:00', '12:40:00', 63);

insert into Airlines values
	(1, 19, 'KLM'),
	(2, 3, 'Lufthansa'),
	(3, 20, 'SwissAir'),
	(4, 9, 'Austrian Airlines'),
	(5, 11, 'LOT'),
	(6, 1, 'British Airways'),
	(7, 14, 'Wizzair'),
	(8, 1, 'EasyJet'),
	(9, 7, 'Ryanair'),
	(10, 2, 'Air France'),
	(11, 4, 'Iberia'),
	(12, 3, 'Eurowings'),
	(13, 11, 'Buzz'),
	(14, 17, 'Finnair'),
	(15, 13, 'SAS'),
	(16, 10, 'Brussels Airlines'),
	(17, 8, 'Norwegian Airlines'),
	(18, 5, 'ITA Airways'),
	(19, 6, 'Aegean Airlines'),
	(20, 19, 'Transavia');

insert into Airplane_Models values
	(1, 'Boeing 737-700', 126, null, null),
	(2, 'Boeing 737-900', 178, null, null),
	(3, 'Airbus A380', 400, 60, 12),
	(4, 'Airbus A320', 180, null, null),
	(5, 'Boeing 787-8 Dreamliner', 240, 18, null),
	(6, 'Boeing 747', 400, 50, 20),
	(7, 'Airbus A319', 80, 30, 10),
	(8, 'Boeing 737-MAX', 200, 30, null),
	(9, 'Embraer E195LR', 132, null, null),
	(10, 'Embraer 170', 70, null, null);

insert into Airplanes values
	(1, 1),
	(2, 1),
	(3, 2),
	(4, 8),
	(5, 8),
	(6, 8),
	(7, 3),
	(8, 3),
	(9, 9),
	(10, 8),
	(11, 8),
	(12, 8),
	(13, 5),
	(14, 3),
	(15, 7),
	(16, 10),
	(17, 6),
	(18, 6),
	(19, 2),
	(20, 2);
	
insert into Airlines_Airplanes values
	(1, 9),
	(1, 13),
	(2, 16),
	(2, 10),
	(3, 9),
	(4, 7),
	(5, 7),
	(6, 6),
	(7, 2),
	(7, 12),
	(8, 2),
	(8, 3),
	(9, 3),
	(10, 15),
	(10, 17),
	(10, 14),
	(11, 15),
	(11, 17),
	(11, 14),
	(12, 18),
	(12, 19),
	(13, 5),
	(14, 1),
	(14, 20),
	(15, 10),
	(16, 6),
	(17, 6),
	(17, 8),
	(18, 11),
	(19, 4),
	(20, 5);

insert into Flights values
	(1, 1, 4, 17, '2023-06-02'),
	(2, 1, 15, 3, '2023-06-10'),
	(3, 1, 5, 12, '2023-06-22'),
	(4, 2, 8, 19, '2023-06-08'),
	(5, 2, 11, 6, '2023-06-14'),
	(6, 1, 18, 8, '2023-06-23'),
	(7, 2, 7, 2, '2023-06-17'),
	(8, 1, 13, 20, '2023-06-15'),
	(9, 2, 2, 1, '2023-06-01'),
	(10, 2, 16, 10, '2023-06-18'),
	(11, 1, 1, 5, '2023-06-11'),
	(12, 2, 9, 9, '2023-06-13'),
	(13, 1, 6, 7, '2023-06-19'),
	(14, 2, 12, 4, '2023-06-03'),
	(15, 1, 17, 11, '2023-06-26'),
	(16, 2, 14, 16, '2023-06-05'),
	(17, 1, 3, 18, '2023-06-07'),
	(18, 1, 10, 14, '2023-06-16'),
	(19, 2, 5, 15, '2023-06-24');

insert into Countries values
		(1, 'United Kingdom'),
		(2, 'France'),
		(3, 'Germany'),
		(4, 'Spain'),
		(5, 'Italy'),
		(6, 'Greece'),
		(7, 'Ireland'),
		(8, 'Norway'),
		(9, 'Austria'),
		(10, 'Belgium'),
		(11, 'Poland'),
		(12, 'Portugal'),
		(13, 'Sweden'),
		(14, 'Hungary'),
		(15, 'Denmark'),
		(16, 'Czechia'),
		(17, 'Finland'),
		(18, 'Romania'),
		(19, 'Netherlands'),
		(20, 'Switzeland');

insert into Cities values
		(1 , 'London', 1, POINT(51.48378922368449, -0.12488499963308404), 0),
		(2 , 'Paris', 2, POINT(48.85342731198252, 2.3488110469414902), 1),
		(3 , 'Germany',3 , POINT(52.515187773888506, 13.408574729919318), 1),
		(4 , 'Madrid', 4, POINT(40.40408658771536, -3.702026637591249), 1),
		(5 , 'Rome',5 , POINT(41.8971584527088, 12.49324415524131), 1),
		(6 , 'Athens', 6, POINT(37.97569130450103, 23.725180112250857), 1),
		(7 , 'Dublin',7 , POINT(53.33653716891955, -6.260160720672973), 0),
		( 8, 'Oslo',8 , POINT(59.91124441091061, 10.76417030400713), 1),
		( 9, 'Vienna', 9, POINT(48.19153265141656, 16.374151654112442), 1),
		(10 , 'Brussels',10 , POINT(50.84350622097226, 4.355416343819361), 1),
		(11 , 'Warsaw', 11, POINT(52.22234745371804, 21.01349683533828), 1),
		( 12, 'Lisbon',12 , POINT(38.714296082436896, -9.14328783406434), 0),
		(13 , 'Stockholm',13 , POINT(59.31508440443203, 18.067546763971368), 1),
		(14 , 'Budapest',14 , POINT(47.48312009274352, 19.05741445797183), 1),
		( 15, 'Copenhagen', 15, POINT(55.67089888835524, 12.552147918821767), 1),
		(16 , 'Prague',16 , POINT(50.06605889932048, 14.439079988114072), 1),
		( 17, 'Helsinki', 17, POINT(60.17293149833255, 24.936036364274166), 2),
		( 18, 'Bukarest', 18, POINT(44.416217854021156, 26.10028556612783), 2),
		( 19, 'Amsterdam',19 , POINT(52.3624887185638, 4.905408933369399), 1),
		(20 , 'Bern', 20, POINT(46.945232196748634, 7.44849514042303), 1);

insert into Terminals values
		(1,1,1),
		( 2,1 , 2),
		( 3, 2, 1),
		( 4, 2, 2),
		( 5, 3, 1),
		( 6, 3, 2),
		( 7, 4, 1),
		( 8, 4, 2),
		( 9, 5, 1),
		( 10, 5, 2),
		( 11, 6,1 ),
		( 12, 6, 2),
		(13,7 , 1),
		( 14, 7, 2),
		(15,8 , 1),
		( 16,8 , 2),
		( 17,9 , 1),
		(18 , 9, 2),
		( 19, 10, 1),
		( 20,10 , 2),
		( 21,11 ,1 ),
		( 22, 11, 2),
		( 23,12 , 1),
		(24, 12, 2),
		( 25, 13, 1),
		( 26, 13, 2),
		(27 , 14, 1),
		( 28,14 , 2),
		( 29, 15, 1),
		(30 ,15 , 2),
		( 31, 16, 1),
		( 32,16 , 2),
		( 33, 17, 1),
		( 34, 17, 2),
		(35 , 18, 1),
		( 36, 18, 2),
		( 37, 19, 1),
		(38 , 19,2 ),
		( 39, 20, 1),
		( 40, 20, 2);



insert into Users values
(1,'john123',	'pa$$word1'),
(2,'jessica92',	'1234abcd'),
(3,'bob_smith',	'b0b_pass'),
(4,'sarah_carter',	's@r@h123'),
(5,'max_power',	'p0w3r_m@x'),
(6,'emma_watson',	'harryp0tter'),
(7,'kevin_jones',	'kevin_jones_123'),
(8,'amy_lee',	'evanesc3nce'),
(9,'david_bowie',	'ziggystardust'),
(10,'carla_garcia',	'1q2w3e4r'),
(11,'michael_jordan',	'airjordan23'),
(12,'taylor_swift',	'love$tor'),
(13,'bruce_wayne',	'darkknight'),
(14,'lucy_lawless',	'xena123'),
(15,'justin_bieber',	'belieber4ever'),
(16,'jennifer_lopez',	'jlo12#'),
(17,'chris_evans',	'captainamerica'),
(18,'kristen_stewart',	'edwardcullen'),
(19,'ryan_gosling',	'heygirl!'),
(20,'scarlett_johansson',	'blackwidow'),
(21,'leonardo_dicaprio',	'titanic1997'),
(22,'anne_hathaway',	'princessdiaries'),
(23,'zac_efron',	'highschoolmusical'),
(24,'emily_blunt',	'marypoppins'),
(25,'johnny_depp',	'jacksparrow'),
(26,'sandra_bullock',	'misscongeniality'),
(27,'hugh_jackman',	'wolverine'),
(28,'margot_robbie',	'harleyquinn'),
(29,'tom_holland',	'spiderman'),
(30,'angelina_jolie',	'tombraider');
