insert into category (id)
values ('java');
insert into category (id)
values ('spring');
insert into category (id)
values ('How To Find An Internship');
insert into category (id)
values ('Innovative Software Methods');
insert into message (author, content, created_at, category_id, title, dtype, id)
values ('admin', 'Do you feel like learning lots of stuff', CURRENT_TIMESTAMP, null, 'How are you feeling today?',
        'Question',  0);
insert into message (author, content, created_at, category_id, title, dtype, id)
values ('George', 'Hi can someone tell me how to find an internship please :)', CURRENT_TIMESTAMP, 'How To Find An Internship', 'What are the steps to finding an internship?',
        'Question', -3);
insert into message (author, content, created_at, category_id, title, dtype, id)
values ('Adelina', 'I am trying to make a sentient AI in pure java any tips ?', CURRENT_TIMESTAMP, 'Innovative Software Methods', 'Do Andrioids dream of electric sheep?',
        'Question', -2);
insert into message (author, content, created_at, category_id, title, dtype, id)
values ('Laksumi', 'Does anyone know where I can find a cheap 1080ti 10 euros max ?', CURRENT_TIMESTAMP, 'Innovative Software Methods', 'Need a new Graphics card ',
        'Question', -1);
insert into user_details (username, password, birthday, roles, score, team, email)
values ('admin', '$2a$12$NpCabQjKivWO3UU3XceliOXjHt3SJWxyz8vuVZuQt4fXIK5yOIYAa', '2000-01-01', 'USER,API', 0, 'GRYFFINDOR', 'admin@email.com');
-- Insert sample user details into the user_details table
insert into user_details (username, password, birthday, roles, score, team, email)
values ('Adelina', '$2a$12$NpCabQjKivWO3UU3XceliOXjHt3SJWxyz8vuVZuQt4fXIK5yOIYAa', '1999-10-15', 'ROLE_USER', 0, 'HUFFLEPUFF', 'adelina@email.com');
insert into user_details (username, password, birthday, roles, score, team, email)
values ('George', '$2a$12$NpCabQjKivWO3UU3XceliOXjHt3SJWxyz8vuVZuQt4fXIK5yOIYAa', '1999-10-15', 'ROLE_USER', 0, 'RAVENCLAW', 'george@email.com');
insert into user_details (username, password, birthday, roles, score, team, email)
values ('Laksumi', '$2a$12$NpCabQjKivWO3UU3XceliOXjHt3SJWxyz8vuVZuQt4fXIK5yOIYAa', '1999-10-15', 'ROLE_USER', 0, 'SLYTHERIN', 'laksumi@email.com');