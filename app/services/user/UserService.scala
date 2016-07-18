package services.user

import javax.inject.Inject

import anorm._
import models.user.UserModel.{Login, Register}
import org.mindrot.jbcrypt.BCrypt
import play.api.db._
import play.api.libs.json._

/**
  * Created by jiaweizhang on 7/17/2016.
  */

class UserService @Inject()(db: Database) {

  def doRegister(registration: Register): (Boolean, JsValue) = {
    db.withConnection { implicit conn =>
      val emailCount: Int =
        SQL("SELECT COUNT(*) AS count " +
          "FROM users u " +
          "WHERE u.email = {email}")
          .on(
            'email -> registration.email)
          .as(SqlParser.int("count").single)
      if (emailCount == 0) {
        val userId: Option[Long] =
          SQL("INSERT INTO users(email, passhash)" +
            "VALUES({email}, {passhash})")
            .on(
              'email -> registration.email,
              'passhash -> BCrypt.hashpw(registration.password, BCrypt.gensalt()))
            .executeInsert()
        (true, Json.obj(
          "status" -> "Ok",
          "message" -> "Successfully registered user",
          "data" -> Json.obj(
            "userId" -> userId
          ))
          )
      } else {
        (false, Json.obj(
          "status" -> "Bad",
          "message" -> "Email already exists"
        ))
      }
    }
  }

  def doLogin(login: Login): (Boolean, JsValue) = {
    db.withConnection { implicit conn =>
      // TODO do these two operations in a single query
      val emailCount: Int =
        SQL("SELECT COUNT(*) AS count " +
          "FROM users u " +
          "WHERE u.email = {email}")
          .on(
            'email -> login.email)
          .as(SqlParser.int("count").single)
      if (emailCount == 0) {
        // email doesn't exist
        return (false, Json.obj(
          "status" -> "Bad",
          "message" -> "Invalid email"
        ))
      }
      val passhash: String =
        SQL("SELECT u.passhash " +
          "FROM users u " +
          "WHERE u.email = {email}")
          .on(
            'email -> login.email)
          .as(SqlParser.str("passhash").single)
      if (BCrypt.checkpw(login.password, passhash)) {
        (true, Json.obj(
          "status" -> "Ok",
          "message" -> "Successfully logged in",
          "data" -> Json.obj(
            // TODO add authentication token
            "token" -> "some token here"
          )
        ))
      } else {
        (false, Json.obj(
          "status" -> "Bad",
          "message" -> "Login attempt failed"
        ))
      }
    }
  }
}