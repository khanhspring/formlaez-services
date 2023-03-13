ALTER TABLE form ADD COLUMN team_id bigint DEFAULT NULL;
ALTER TABLE form ADD COLUMN workspace_id bigint NOT NULL;

ALTER TABLE form ADD CONSTRAINT fk_form_team_id FOREIGN KEY (team_id) REFERENCES team (id);
ALTER TABLE form ADD CONSTRAINT fk_form_workspace_id FOREIGN KEY (workspace_id) REFERENCES workspace (id);
