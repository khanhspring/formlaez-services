ALTER TABLE form ADD COLUMN accept_responses boolean NOT NULL DEFAULT true;
ALTER TABLE form ADD COLUMN allow_printing boolean NOT NULL DEFAULT false;
ALTER TABLE form ADD COLUMN allow_response_editing boolean NOT NULL DEFAULT true;
