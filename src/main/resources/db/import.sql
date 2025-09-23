INSERT INTO campuses (campus_name) VALUES
    ('Main Campus');
INSERT INTO campuses (campus_name) VALUES
    ('North Campus');
INSERT INTO campuses (campus_name) VALUES
    ('East Campus');
INSERT INTO campuses (campus_name) VALUES
    ('West Campus');
INSERT INTO campuses (campus_name) VALUES
    ('South Campus');
INSERT INTO campuses (campus_name) VALUES
    ('Central Campus');
INSERT INTO campuses (campus_name) VALUES
    ('Tech Campus');
INSERT INTO campuses (campus_name) VALUES
    ('Arts Campus');
INSERT INTO campuses (campus_name) VALUES
    ('Business Campus');
INSERT INTO campuses (campus_name) VALUES
    ('Medical Campus');

-- careers
INSERT INTO careers (career_name) VALUES
  ('Engineering'), ('Arts'), ('Business'), ('Medicine'), ('Law'),
  ('Science'), ('Technology'), ('Design'), ('Education'), ('Nursing');

-- formats
INSERT INTO formats (format_name) VALUES
  ('Online'), ('Offline'), ('Hybrid'), ('Self-paced'), ('Instructor-led'),
  ('Weekend'), ('Evening'), ('Morning'), ('Intensive'), ('Regular');

-- courses
INSERT INTO courses (course_name) VALUES
  ('Math 101'), ('Painting 101'), ('Accounting 101'), ('Anatomy 101'), ('Law 101'),
  ('Physics 101'), ('Programming 101'), ('Graphic Design 101'), ('Teaching 101'), ('Nursing 101');

-- course_formats (ManyToMany)
INSERT INTO course_formats (course_id, format_id) VALUES
  (1, 1), (2, 2), (3, 3), (4, 4), (5, 5),
  (6, 6), (7, 7), (8, 8), (9, 9), (10, 10);

-- Formalities
INSERT INTO Formalities (title, description, start_date, end_date) VALUES
  ('Formality 1', 'Description 1', '2025-01-01', '2025-01-10'),
  ('Formality 2', 'Description 2', '2025-02-01', '2025-02-10'),
  ('Formality 3', 'Description 3', '2025-03-01', '2025-03-10'),
  ('Formality 4', 'Description 4', '2025-04-01', '2025-04-10'),
  ('Formality 5', 'Description 5', '2025-05-01', '2025-05-10'),
  ('Formality 6', 'Description 6', '2025-06-01', '2025-06-10'),
  ('Formality 7', 'Description 7', '2025-07-01', '2025-07-10'),
  ('Formality 8', 'Description 8', '2025-08-01', '2025-08-10'),
  ('Formality 9', 'Description 9', '2025-09-01', '2025-09-10'),
  ('Formality 10', 'Description 10', '2025-10-01', '2025-10-10');

-- reaction
INSERT INTO reactions (reaction_name, icon_url) VALUES
  ('Like', 'icon_like.png'), ('Dislike', 'icon_dislike.png'), ('Love', 'icon_love.png'), ('Angry', 'icon_angry.png'), ('Sad', 'icon_sad.png'),
  ('Wow', 'icon_wow.png'), ('Haha', 'icon_haha.png'), ('Care', 'icon_care.png'), ('Confused', 'icon_confused.png'), ('Excited', 'icon_excited.png');

-- tag
INSERT INTO tags (tag_name) VALUES
  ('Science'), ('Creativity'), ('Business'), ('Health'), ('Law'),
  ('Technology'), ('Design'), ('Education'), ('Art'), ('Math');

-- teachers
INSERT INTO teachers (first_name, last_name, general_description, profile_picture_url, average_rating) VALUES
  ('John', 'Smith', 'Desc 1', 'pic1.png', 4.5), ('Jane', 'Johnson', 'Desc 2', 'pic2.png', 4.2),
  ('Mike', 'Brown', 'Desc 3', 'pic3.png', 4.0), ('Anna', 'Lee', 'Desc 4', 'pic4.png', 3.8),
  ('Sara', 'White', 'Desc 5', 'pic5.png', 4.7), ('Tom', 'Black', 'Desc 6', 'pic6.png', 4.1),
  ('Lucy', 'Green', 'Desc 7', 'pic7.png', 3.9), ('Paul', 'Blue', 'Desc 8', 'pic8.png', 4.3),
  ('Emma', 'Gray', 'Desc 9', 'pic9.png', 4.6), ('Alex', 'Red', 'Desc 10', 'pic10.png', 4.4);

-- teacher_careers (ManyToMany)
INSERT INTO teacher_careers (teacher_id, career_id) VALUES
  (1, 1), (2, 2), (3, 3), (4, 4), (5, 5),
  (6, 6), (7, 7), (8, 8), (9, 9), (10, 10);

-- teacher_campuses (ManyToMany)
INSERT INTO teacher_campuses (teacher_id, campus_id) VALUES
  (1, 1), (2, 2), (3, 3), (4, 4), (5, 5),
  (6, 6), (7, 7), (8, 8), (9, 9), (10, 10);

-- users
INSERT INTO users (username, email, password, state, creation_date, profile_picture_url) VALUES
  ('alice', 'alice@email.com', 'pass1', true, '2025-01-01', 'alice.png'),
  ('bob', 'bob@email.com', 'pass2', true, '2025-01-02', 'bob.png'),
  ('charlie', 'charlie@email.com', 'pass3', true, '2025-01-03', 'charlie.png'),
  ('diana', 'diana@email.com', 'pass4', true, '2025-01-04', 'diana.png'),
  ('eve', 'eve@email.com', 'pass5', true, '2025-01-05', 'eve.png'),
  ('frank', 'frank@email.com', 'pass6', true, '2025-01-06', 'frank.png'),
  ('grace', 'grace@email.com', 'pass7', true, '2025-01-07', 'grace.png'),
  ('hank', 'hank@email.com', 'pass8', true, '2025-01-08', 'hank.png'),
  ('ivy', 'ivy@email.com', 'pass9', true, '2025-01-09', 'ivy.png'),
  ('jack', 'jack@email.com', 'pass10', true, '2025-01-10', 'jack.png');

-- students
INSERT INTO students (user_id, current_semester) VALUES
  (1, 1), (2, 2), ( 3, 3), (4, 4), (5, 5),
  (6, 6), ( 7, 7), ( 8, 8), (9, 9), (10, 10);

-- student_careers (ManyToMany)
INSERT INTO student_careers (student_id, career_id) VALUES
  (1, 1), (2, 2), (3, 3), (4, 4), (5, 5),
  (6, 6), (7, 7), (8, 8), (9, 9), (10, 10);

-- review
INSERT INTO review (student_id, teacher_id, description, rating, created_at) VALUES
  (1, 1, 'Excellent teacher and course!', 4.8, '2025-09-01T09:00:00'),
  (2, 2, 'Very helpful and clear.', 4.5, '2025-09-02T10:00:00'),
  (3, 3, 'Good explanations.', 4.2, '2025-09-03T11:00:00'),
  (4, 4, 'Engaging lessons.', 4.7, '2025-09-04T12:00:00'),
  (5, 5, 'Could improve materials.', 3.9, '2025-09-05T13:00:00'),
  (6, 6, 'Great support.', 4.6, '2025-09-06T14:00:00'),
  (7, 7, 'Fun and interactive.', 4.3, '2025-09-07T15:00:00'),
  (8, 8, 'Too fast paced.', 3.8, '2025-09-08T16:00:00'),
  (9, 9, 'Highly recommended.', 4.9, '2025-09-09T17:00:00'),
  (10, 10, 'Not my style.', 3.5, '2025-09-10T18:00:00');

-- review_tags (ManyToMany)
INSERT INTO review_tags (review_id, tag_id) VALUES
  (1, 1), (2, 2), (3, 3), (4, 4), (5, 5),
  (6, 6), (7, 7), (8, 8), (9, 9), (10, 10);

-- review_reactions
INSERT INTO review_reactions (reaction_id, author_id, review_id, created_at) VALUES
  (1, 1, 1, '2025-09-22T10:00:00'), (2, 2, 2, '2025-09-22T10:01:00'), (3, 3, 3, '2025-09-22T10:02:00'),
  (4, 4, 4, '2025-09-22T10:03:00'), (5, 5, 5, '2025-09-22T10:04:00'), (6, 6, 6, '2025-09-22T10:05:00'),
  (7, 7, 7, '2025-09-22T10:06:00'), (8, 8, 8, '2025-09-22T10:07:00'), (9, 9, 9, '2025-09-22T10:08:00'), (10, 10, 10, '2025-09-22T10:09:00');

-- role
INSERT INTO role (role_name) VALUES
  ('ADMIN'), ('USER'), ('TEACHER'), ('STUDENT'), ('MODERATOR'),
  ('GUEST'), ('EDITOR'), ('REVIEWER'), ('MANAGER'), ('SUPPORT');

-- user_roles (ManyToMany)
INSERT INTO user_roles (user_id, role_id) VALUES
  (1, 1), (2, 2), (3, 3), (4, 4), (5, 5),
  (6, 6), (7, 7), (8, 8), (9, 9), (10, 10);
