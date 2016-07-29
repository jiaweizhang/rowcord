/* sp_register */
DROP FUNCTION sp_register( CHARACTER VARYING, CHARACTER VARYING );
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
  INSERT INTO users (email, passhash) VALUES (p_email, p_passhash) RETURNING userId INTO p_userId;
  --p_userId := lastval();
  p_success := TRUE;
  p_message := 'Successfully registered';
  RETURN;
END; $$

LANGUAGE plpgsql;