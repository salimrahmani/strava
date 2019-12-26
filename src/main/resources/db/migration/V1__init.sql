CREATE TABLE run(
   run_id serial PRIMARY KEY,
   start_date TIMESTAMP NOT NULL,
   end_date TIMESTAMP NOT NULL,
   kms NUMERIC NOT NULL,
   cals NUMERIC NOT NULL
);