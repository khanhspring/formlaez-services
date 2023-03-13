ALTER TABLE attachment DROP COLUMN path;
ALTER TABLE attachment ADD COLUMN original_name varchar(200) NOT NULL;
ALTER TABLE attachment ADD COLUMN category varchar(50) NOT NULL;
ALTER TABLE attachment ALTER COLUMN type TYPE varchar(250);
