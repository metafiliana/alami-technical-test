CREATE TABLE members (
                         id serial PRIMARY KEY,
                         name VARCHAR (50) NOT NULL,
                         dob DATE NOT NULL,
                         address VARCHAR (50) NOT NULL,
                         nik VARCHAR (50) UNIQUE NOT NULL,
                         created_at TIMESTAMP default CURRENT_TIMESTAMP,
                         updated_at TIMESTAMP
);

CREATE TABLE member_funds (
                              id serial PRIMARY KEY,
                              member_id INT NOT NULL,
                              total_saving INT default 0,
                              total_debt INT default 0,
                              lending_eligibility BOOLEAN default TRUE,
                              created_at TIMESTAMP default CURRENT_TIMESTAMP,
                              updated_at TIMESTAMP,
                              FOREIGN KEY (member_id)
                                  REFERENCES members (id)
);

CREATE TABLE fund_transactions (
                                   id serial PRIMARY KEY,
                                   member_id INT NOT NULL,
                                   state smallint not null,
                                   value int NOT NULL,
                                   created_at TIMESTAMP,
                                   updated_at TIMESTAMP,
                                   FOREIGN KEY (member_id)
                                       REFERENCES members (id)
);