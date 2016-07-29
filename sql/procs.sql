/* sp_register */
DROP FUNCTION IF EXISTS sp_register( CHARACTER VARYING, CHARACTER VARYING );
CREATE OR REPLACE FUNCTION sp_register(
      p_email    VARCHAR,
      p_passhash VARCHAR,
  OUT p_userId   BIGINT,
  OUT p_success  BOOLEAN,
  OUT p_message  VARCHAR
) AS $$
BEGIN
  IF (SELECT COUNT(*)
      FROM users u
      WHERE u.email = p_email) > 0
  THEN
    p_userId := 0;
    p_success := FALSE;
    p_message := 'Email already exists';
    RETURN;
  END IF;
  INSERT INTO users (email, passhash) VALUES (p_email, p_passhash)
  RETURNING userId
    INTO p_userId;
  -- this also works but not sure about which one is more efficient
  -- p_userId := lastval();
  p_success := TRUE;
  p_message := 'Successfully registered';
  RETURN;
END; $$

LANGUAGE plpgsql;

/* sp_login */
DROP FUNCTION IF EXISTS sp_login( CHARACTER VARYING );
CREATE OR REPLACE FUNCTION sp_login(
      p_email    VARCHAR,
  OUT p_passhash VARCHAR,
  OUT p_userId   BIGINT,
  OUT p_success  BOOLEAN,
  OUT p_message  VARCHAR
) AS $$
BEGIN
  IF (SELECT COUNT(*)
      FROM users u
      WHERE u.email = p_email) != 1
  THEN
    p_userId := 0;
    p_success := FALSE;
    p_message := 'Email does not exist';
    RETURN;
  END IF;
  SELECT
    u.userId,
    u.passhash,
    TRUE,
    'Successfully retrieved userId and passhash'
  FROM users u
  WHERE u.email = p_email
  INTO
    p_userId, p_passhash, p_success, p_message;
  RETURN;
END; $$

LANGUAGE plpgsql;