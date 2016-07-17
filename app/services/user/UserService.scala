package services.user

import javax.inject.Inject

import anorm._
import models.user.UserModel.{Login, Register}
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
              // TODO perform password hashing
              'passhash -> registration.password)
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

  def doLogin(login: Login): String = {
    // use login here
    "api key here"
  }
}