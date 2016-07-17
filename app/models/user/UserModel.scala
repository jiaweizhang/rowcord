package models.user

import play.api.libs.json._

/**
  * Created by jiaweizhang on 7/17/2016.
  */

object UserModel {

  case class Register(email: String, password: String)

  case class Login(email: String, password: String)

  implicit val registerReads = Json.reads[Register]

  implicit val loginReads = Json.reads[Login]

}