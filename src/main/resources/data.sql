INSERT INTO users (username, password_hash)
SELECT 'alice', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'alice');

INSERT INTO users (username, password_hash)
SELECT 'bob', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'bob');

INSERT INTO posts (user_id, title, body)
SELECT (SELECT id FROM users WHERE username = 'alice'),
       'Welcome to BitterJava',
       'A small place to share ideas and ask questions.'
WHERE NOT EXISTS (SELECT 1 FROM posts WHERE title = 'Welcome to BitterJava');

INSERT INTO posts (user_id, title, body)
SELECT (SELECT id FROM users WHERE username = 'bob'),
       'What are you learning?',
       'Tell the community what you are working on this week.'
WHERE NOT EXISTS (SELECT 1 FROM posts WHERE title = 'What are you learning?');
