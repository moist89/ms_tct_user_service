-- Insert de usuario
INSERT INTO USERS (id, name, email, password, token, created, modified, last_login, active)
VALUES (
    'e83d1590-01d9-4d92-b33a-0d3a431f9f83',
    'Juan Rodriguez',
    'juan@rodriguez.org',
    'hunter2',
    'eyJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoidXNlcjEiLCJlbWFpbCI6InVzZXJtYWlsQHBydWViYS5vcmciLCJpYXQiOjE3NTE2MTE0NTMsImV4cCI6MTc1MTYxMTgxM30.8CvY4hEwhTMOlEiU_seEx_o_Mp_hjf88Qo4fY-IRboY',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    TRUE
);
-- Insert de teléfono asociado
INSERT INTO PHONES (number, city_code, country_code, user_id)
VALUES ('1234567', '1', '57', 'e83d1590-01d9-4d92-b33a-0d3a431f9f83');