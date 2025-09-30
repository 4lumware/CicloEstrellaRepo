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

-- usuarios
INSERT INTO users (username, email, password, state, creation_date, profile_picture_url) VALUES
  ('alicia', 'alicia@email.com', 'clave1', true, '2025-01-01', 'alicia.png'),
  ('roberto', 'roberto@email.com', 'clave2', true, '2025-01-02', 'roberto.png'),
  ('carlos', 'carlos@email.com', 'clave3', true, '2025-01-03', 'carlos.png'),
  ('diana', 'diana@email.com', 'clave4', true, '2025-01-04', 'diana.png'),
  ('eva', 'eva@email.com', 'clave5', true, '2025-01-05', 'eva.png'),
  ('francisco', 'francisco@email.com', 'clave6', true, '2025-01-06', 'francisco.png'),
  ('graciela', 'graciela@email.com', 'clave7', true, '2025-01-07', 'graciela.png'),
  ('hector', 'hector@email.com', 'clave8', true, '2025-01-08', 'hector.png'),
  ('ines', 'ines@email.com', 'clave9', true, '2025-01-09', 'ines.png'),
  ('juan', 'juan@email.com', 'clave10', true, '2025-01-10', 'juan.png');

-- estudiantes
INSERT INTO students (user_id, current_semester) VALUES
  (1, 1), (2, 2), (3, 3), (4, 4), (5, 5),
  (6, 6), (7, 7), (8, 8), (9, 9), (10, 10);

-- student_careers (MuchosAMuchos)
INSERT INTO student_careers (student_id, career_id) VALUES
  (1, 1), (2, 2), (3, 3), (4, 4), (5, 5),
  (6, 6), (7, 7), (8, 8), (9, 9), (10, 10);

-- reseñas
INSERT INTO review (student_id, teacher_id, description, rating, created_at) VALUES
  (1, 1, 'Excelente docente, explica con claridad y paciencia.', 4.8, '2025-09-01T09:00:00'),
  (2, 2, 'Muy creativa y motivadora en sus clases.', 4.7, '2025-09-02T10:00:00'),
  (3, 3, 'Explica bien los conceptos contables.', 4.5, '2025-09-03T11:00:00'),
  (4, 4, 'Gran profesional, transmite confianza.', 4.6, '2025-09-04T12:00:00'),
  (5, 5, 'Abogado con ejemplos prácticos y útiles.', 4.4, '2025-09-05T13:00:00'),
  (6, 6, 'Fomenta el pensamiento científico y crítico.', 4.3, '2025-09-06T14:00:00'),
  (7, 7, 'Motiva a aprender tecnología de forma sencilla.', 4.6, '2025-09-07T15:00:00'),
  (8, 8, 'Creativa y dinámica en sus clases de diseño.', 4.2, '2025-09-08T16:00:00'),
  (9, 9, 'Excelente metodología pedagógica.', 4.7, '2025-09-09T17:00:00'),
  (10, 10, 'Muy dedicada al cuidado y enseñanza.', 4.5, '2025-09-10T18:00:00');

-- review_tags (MuchosAMuchos)
INSERT INTO review_tags (review_id, tag_id) VALUES
  (1, 1), (2, 2), (3, 3), (4, 4), (5, 5),
  (6, 6), (7, 7), (8, 8), (9, 9), (10, 10);

-- review_reactions
INSERT INTO review_reactions (reaction_id, author_id, review_id, created_at) VALUES
  (1, 1, 1, '2025-09-22T10:00:00'), (2, 2, 2, '2025-09-22T10:01:00'), (3, 3, 3, '2025-09-22T10:02:00'),
  (4, 4, 4, '2025-09-22T10:03:00'), (5, 5, 5, '2025-09-22T10:04:00'), (6, 6, 6, '2025-09-22T10:05:00'),
  (7, 7, 7, '2025-09-22T10:06:00'), (8, 8, 8, '2025-09-22T10:07:00'), (9, 9, 9, '2025-09-22T10:08:00'), (10, 10, 10, '2025-09-22T10:09:00');

-- roles
INSERT INTO role (role_name) VALUES ('ADMIN'), ('STUDENT'), ('MODERATOR');



-- user_roles (MuchosAMuchos)
INSERT INTO user_roles (user_id, role_id) VALUES
(1, 2), (2, 2), (3, 2), (4, 2), (5, 2), (6, 2), (7, 2), (8, 2), (9, 2), (10, 2);
