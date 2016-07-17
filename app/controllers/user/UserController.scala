package controllers.user

import javax.inject.Inject

import models.user.UserModel.{Login, Register}
import play.api.libs.json.{JsError, Json}
import play.api.mvc.{Action, BodyParsers, Controller}
import services.user.UserService

/**
  * Created by jiaweizhang on 7/17/2016.
  */
class UserController @Inject()(us: UserService) extends Controller {

  def register = Action(BodyParsers.parse.json) { request =>
    val registerResult = request.body.validate[Register]

    registerResult.fold(
      errors => {
        BadRequest(Json.obj("status" -> "Bad", "message" -> JsError.toJson(errors)))
      },
      register => {
        val (success, response) = us.doRegister(register)
        if (success) Ok(response) else BadRequest(response)
      }
    )
  }

  def login = Action(BodyParsers.parse.json) { request =>
    val loginResult = request.body.validate[Login]

    loginResult.fold(
      errors => {
        BadRequest(Json.obj("status" -> "Bad", "message" -> JsError.toJson(errors)))
      },
      login => {
        us.doLogin(login)
        Ok(Json.obj("status" -> "Ok", "message" -> "Successfully logged in"))
      }
    )
  }
}
