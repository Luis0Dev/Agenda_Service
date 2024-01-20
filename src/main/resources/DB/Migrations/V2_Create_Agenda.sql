CREATE TABLE agenda(

    id serial PRIMARY KEY,
    descricao VARCHAR(200),
    data_hora TIMESTAMP,
    paciente_id INTEGER,
    CONSTRAINT  fk_agenda_paciente FOREIGN KEY (paciente_id) REFERENCES paciente(id)
);