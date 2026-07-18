INSERT INTO users (username, password, role)
SELECT 'admin', '$2b$12$Hwnx.qu9pqGT.OZrT/C0n.6ETDGr6Etv11.NpdfQuQqBjjaEFhU1G', 'admin'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'admin');
