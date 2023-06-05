CREATE USER admin WITH PASSWORD 'tanieczyszczeniesprzatanie';
ALTER USER admin createdb;
CONNECT postgres admin;
CREATE database skyskaner;