ALTER TABLE form ADD COLUMN status varchar(30) NOT NULL DEFAULT 'Draft';
ALTER TABLE form ADD COLUMN sharing_scope varchar(30) NOT NULL DEFAULT 'Public';
