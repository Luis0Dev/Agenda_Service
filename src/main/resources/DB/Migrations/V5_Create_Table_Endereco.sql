CREATE TABLE endereco(

    id serial PRIMARY KEY,
    rua VARCHAR(255),
    numero INTEGER,
    complemento VARCHAR(255),
    bairro VARCHAR(255),
    paciente_id INTEGER,

    CONSTRAINT  fk_endereco_paciente FOREIGN KEY (paciente_id) REFERENCES paciente(id)
);