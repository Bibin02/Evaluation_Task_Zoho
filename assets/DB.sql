-- create database
-- create database peoplemgmt;

-- In People management,
-- use peoplemgmt;

/* 
Above code is just demonstration, 
use PSQL Tool (Code in PSQL.txt) to do them.
For creation of tables and quries use this code to execute.
*/

-- Table Creations
create table keytable(apikey varchar(100) primary key, short_url varchar(10));
create table gptmodels (model_id varchar(20) primary key, model_name varchar(50), url varchar(200));
create table request_string_table(endpoint varchar(200) primary key, request_string varchar(500));

-- Sample Insertion Use your data for insertion.

-- insert into gptmodels values ('pkd', 'Discord (Pawan kd)', "https://api.pawan.krd/cosmosrp/v1/chat/completions"),


-- Sampl Insertions
/* INSERT INTO request_string_table VALUES ( 'https://api.pawan.krd/cosmosrp/v1/chat/completions', 
	'{
    "model": "gpt-4o",
    "messages": [
        {"role": "user", "content": "Contents or prompt we give .."}
    ],
    "temperature": 0.6 -- Recommended
}');
*/

-- Sample Insertions
-- INSERT INTO keytable values ('apikey1', 'pkd');

