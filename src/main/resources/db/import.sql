INSERT INTO campuses (campus_name) VALUES
    ('Monterrico');
INSERT INTO campuses (campus_name) VALUES
    ('Villa');
INSERT INTO campuses (campus_name) VALUES
    ('San Miguel');
INSERT INTO campuses (campus_name) VALUES
    ('San Isidro');

-- carreras
INSERT INTO careers (career_name) VALUES
  ('Ingeniería'), ('Artes'), ('Negocios'), ('Medicina'), ('Derecho'),
  ('Ciencias'), ('Tecnología'), ('Diseño'), ('Educación'), ('Enfermería');

-- formatos
INSERT INTO formats (format_name) VALUES
  ('En línea'), ('Presencial'), ('Híbrido'), ('Autodirigido'), ('Con instructor'),
  ('Fin de semana'), ('Nocturno'), ('Matutino'), ('Intensivo'), ('Regular');

-- cursos
INSERT INTO courses (course_name) VALUES
  ('Matemáticas Básicas'), ('Pintura Creativa'), ('Contabilidad Práctica'), ('Anatomía Humana'), ('Introducción al Derecho'),
  ('Física General'), ('Programación Inicial'), ('Diseño Gráfico'), ('Pedagogía Moderna'), ('Cuidados de Enfermería');

-- course_formats (MuchosAMuchos)
INSERT INTO course_formats (course_id, format_id) VALUES
  (1, 1), (2, 2), (3, 3), (4, 4), (5, 5),
  (6, 6), (7, 7), (8, 8), (9, 9), (10, 10);

-- formalidades
INSERT INTO Formalities (title, description, start_date, end_date) VALUES
  ('Solicitud de carnet universitario', 'Trámite para obtener el carnet universitario oficial.', '2025-01-05', '2025-01-10'),
  ('Reserva de laboratorio', 'Solicitud para reservar laboratorios de ciencias o tecnología.', '2025-02-10', '2025-02-15'),
  ('Inscripción a actividades extracurriculares', 'Registro para participar en talleres, deportes o clubes estudiantiles.', '2025-03-01', '2025-03-07'),
  ('Solicitud de beca', 'Proceso para postular a becas académicas o de apoyo económico.', '2025-04-10', '2025-04-20'),
  ('Cambio de carrera', 'Trámite para solicitar el cambio de carrera universitaria.', '2025-05-05', '2025-05-15'),
  ('Solicitud de constancia de estudios', 'Petición para obtener constancia oficial de estudios realizados.', '2025-06-01', '2025-06-05'),
  ('Matrícula de ciclo', 'Proceso de matrícula para el ciclo académico correspondiente.', '2025-07-10', '2025-07-20'),
  ('Solicitud de traslado de campus', 'Trámite para cambiar de campus dentro de la universidad.', '2025-08-01', '2025-08-10'),
  ('Inscripción a examen de recuperación', 'Registro para rendir exámenes de recuperación de asignaturas.', '2025-09-05', '2025-09-10'),
  ('Solicitud de baja temporal', 'Trámite para solicitar la baja temporal de la universidad.', '2025-10-01', '2025-10-10');

-- reacciones
INSERT INTO reactions (reaction_name, icon_url) VALUES
  ('Me gusta', 'icon_megusta.png'), ('No me gusta', 'icon_nomegusta.png'), ('Me encanta', 'icon_meencanta.png'), ('Enojado', 'icon_enojado.png'), ('Triste', 'icon_triste.png'),
  ('Sorprendido', 'icon_sorprendido.png'), ('Divertido', 'icon_divertido.png'), ('Me importa', 'icon_meimporta.png'), ('Confundido', 'icon_confundido.png'), ('Emocionado', 'icon_emocionado.png');

-- etiquetas
INSERT INTO tags (tag_name) VALUES
  ('Ciencia'), ('Creatividad'), ('Negocios'), ('Salud'), ('Derecho'),
  ('Tecnología'), ('Diseño'), ('Educación'), ('Arte'), ('Matemáticas');

-- docentes
INSERT INTO teachers (first_name, last_name, general_description, profile_picture_url, average_rating) VALUES
  ('Carlos', 'Ramírez', 'Docente con amplia experiencia en ingeniería y proyectos tecnológicos.', 'carlos.png', 4.8),
  ('María', 'González', 'Especialista en arte y creatividad, apasionada por la enseñanza.', 'maria.png', 4.6),
  ('José', 'Fernández', 'Contador público con enfoque en educación financiera.', 'jose.png', 4.4),
  ('Ana', 'Martínez', 'Médica dedicada a la formación de futuros profesionales de la salud.', 'ana.png', 4.7),
  ('Luis', 'Torres', 'Abogado con experiencia en derecho civil y docente universitario.', 'luis.png', 4.5),
  ('Sofía', 'López', 'Investigadora en ciencias y promotora de la educación científica.', 'sofia.png', 4.3),
  ('Miguel', 'Vargas', 'Experto en tecnología y programación, motivador de jóvenes talentos.', 'miguel.png', 4.6),
  ('Valeria', 'Castro', 'Diseñadora gráfica con enfoque en innovación y creatividad.', 'valeria.png', 4.2),
  ('Javier', 'Mendoza', 'Educador con vocación y experiencia en pedagogía moderna.', 'javier.png', 4.7),
  ('Paula', 'Ríos', 'Enfermera con pasión por la docencia y el cuidado integral.', 'paula.png', 4.5);

-- teacher_careers (MuchosAMuchos)
INSERT INTO teacher_careers (teacher_id, career_id) VALUES
  (1, 1), (2, 2), (3, 3), (4, 4), (5, 5),
  (6, 6), (7, 7), (8, 8), (9, 9), (10, 10);

-- teacher_campuses (MuchosAMuchos)
INSERT INTO teacher_campuses (teacher_id, campus_id) VALUES
  (1, 1), (2, 2), (3, 3), (4, 4), (5, 1),
  (6, 2), (7, 3), (8, 4), (9, 1), (10, 2);

-- teacher_courses (MuchosAMuchos)
INSERT INTO teacher_courses (teacher_id, course_id) VALUES
  (1, 1), (2, 2), (3, 3), (4, 4), (5, 5),
  (6, 6), (7, 7), (8, 8), (9, 9), (10, 10);

-- usuarios con contraseñas encriptadas
INSERT INTO users (username, email, password, state, creation_date, profile_picture_url) VALUES
('alicia', 'alicia@email.com', '$2b$12$N/0uUCFsyP6GHgs77Ky2Wu.3azPocyLeQEVrtOCuwVwyRs/a2ToT6', true, '2025-01-01', 'alicia.png'),
('roberto', 'roberto@email.com', '$2b$12$eSxBS7aKpPvaKTKzV2g1kuTnM97Cmjew8x1m63hRwZxwpKXVLnbeq', true, '2025-01-02', 'roberto.png'),
('carlos', 'carlos@email.com', '$2b$12$BvlNL4N1rraGdur4Y2KY9.qU3tjsUrRDbngq5P6JKzueIDeV0mdwa', true, '2025-01-03', 'carlos.png'),
('diana', 'diana@email.com', '$2b$12$e0bPeB4WRhqJ14bCwkTohOZGcB8ww0hzcoupvpgcDhtb62YJdOvou', true, '2025-01-04', 'diana.png'),
('eva', 'eva@email.com', '$2b$12$pr7Wrwbb00kYTajP1YKWU.uY9STNGIClvqr3qW2mzo8lYJ9rZj07.', true, '2025-01-05', 'eva.png'),
('francisco', 'francisco@email.com', '$2b$12$KgeysY9v.c6Ga/NX/QB4i.xwDbkujefABTB5s0677n.X1I2cwT1b6', true, '2025-01-06', 'francisco.png'),
('graciela', 'graciela@email.com', '$2b$12$5Qph/Kjl6mDydEYVDHfjs.lSVKEJHJIztRvYfVGZCTMeF7YYwUhvq', true, '2025-01-07', 'graciela.png'),
('hector', 'hector@email.com', '$2b$12$zypJrCoMgGw.L/3f1ajjiOCX0ISU8gzCLZPJ8YU1VhkOtaz.8dJ8u', true, '2025-01-08', 'hector.png'),
('ines', 'ines@email.com', '$2b$12$cmgN6ZqXFoQD3zV8y9F.yOcDZiDD5rPAQK5LTJLSFjaeNoQoe3gxK', true, '2025-01-09', 'ines.png'),
('juan', 'juan@email.com', '$2b$12$D6TsNrNe4OpcvqmMH6qiU.s951L1X2xW6EWbTiLcf148FFmXHhgdm', true, '2025-01-10', 'juan.png');

-- estudiantes
INSERT INTO students (user_id, current_semester) VALUES
    (1, 2), (2, 3), (3, 1), (4, 4), (5, 2);

-- student_careers (MuchosAMuchos)
INSERT INTO student_careers (student_id, career_id) VALUES
  (1, 1), (2, 2), (3, 3), (4, 4), (5, 5),
  (1, 6), (2, 7), (3, 8), (4, 9), (5, 10);

-- reseñas
INSERT INTO review (student_id, teacher_id, description, rating, created_at) VALUES
  (1, 1, 'Excelente docente, explica con claridad y paciencia.', 4.8, '2025-09-01T09:00:00'),
  (2, 2, 'Muy creativa y motivadora en sus clases.', 4.7, '2025-09-02T10:00:00'),
  (3, 3, 'Explica bien los conceptos contables.', 4.5, '2025-09-03T11:00:00'),
  (4, 4, 'Gran profesional, transmite confianza.', 4.6, '2025-09-04T12:00:00'),
  (5, 5, 'Abogado con ejemplos prácticos y útiles.', 4.4, '2025-09-05T13:00:00'),
  (1, 6, 'Fomenta el pensamiento crítico en ciencias.', 4.3, '2025-09-06T14:00:00'),
  (2, 7, 'Muy buen enfoque en tecnología y programación.', 4.7, '2025-09-07T15:00:00'),
  (3, 8, 'Creativa y actualizada en diseño gráfico.', 4.2, '2025-09-08T16:00:00'),
  (4, 9, 'Pedagoga dedicada y con mucha vocación.', 4.6, '2025-09-09T17:00:00'),
  (5, 10, 'Enfermera comprometida con la docencia.', 4.5, '2025-09-10T18:00:00');

-- review_tags (MuchosAMuchos)
INSERT INTO review_tags (review_id , tag_id) VALUES
    (1, 1), (1, 6),
    (2, 2), (2, 8),
    (3, 3), (3, 1),
    (4, 4), (4, 6),
    (5, 5), (5, 3),
    (6, 6), (6, 1),
    (7, 7), (7, 6),
    (8, 8), (8, 2),
    (9, 9), (9, 8),
    (10, 10), (10, 4);
-- review_reactions
INSERT INTO review_reactions (reaction_id, author_id, review_id, created_at) VALUES
    (1, 2, 1, '2025-09-11T10:00:00'),
    (3, 3, 1, '2025-09-11T11:00:00'),
    (1, 4, 2, '2025-09-12T09:30:00'),
    (5, 5, 2, '2025-09-12T10:15:00'),
    (2, 1, 3, '2025-09-13T14:00:00'),
    (4, 2, 3, '2025-09-13T15:30:00'),
    (1, 3, 4, '2025-09-14T08:45:00'),
    (3, 4, 4, '2025-09-14T09:20:00'),
    (5, 5, 5, '2025-09-15T13:00:00'),
    (2, 1, 5, '2025-09-15T14:30:00');
-- roles
INSERT INTO role (role_name) VALUES ('ADMIN'), ('STUDENT'), ('MODERATOR') , ('WRITER');



-- user_roles (MuchosAMuchos)
INSERT INTO user_roles (user_id, role_id) VALUES
(1, 2), (2, 2), (3, 2), (4, 2), (5, 2), (6, 1), (7, 3), (8, 1), (9, 3), (10, 3);
