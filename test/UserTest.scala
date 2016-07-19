package test

import models.user.UserModel._
import org.scalatest.mock.MockitoSugar
import org.scalatest.{BeforeAndAfterAll, FunSuite}
import play.api.db.evolutions.Evolutions
import play.api.db.{Database, Databases}
import play.api.inject.bind
import play.api.inject.guice.GuiceInjectorBuilder
import services.user.UserService

/**
  * Created by Jiawei on 7/19/2016.
  */
class UserTest extends FunSuite with BeforeAndAfterAll with MockitoSugar {

  var userService: UserService = _
  val testDb = Databases(
    driver = "org.postgresql.Driver",
    url = "jdbc:postgresql://localhost/test",
    config = Map(
      "username" -> "postgres",
      "password" -> "password"
    )
  )

  override def beforeAll() = {
    Evolutions.applyEvolutions(testDb)
    val guice = new GuiceInjectorBuilder()
      .overrides(bind[Database].toInstance(testDb))
      .build()
    userService = guice.instanceOf[UserService]
  }

  override def afterAll() = {
    Evolutions.cleanupEvolutions(testDb)
    testDb.shutdown()
  }

  test("creating duplicate users") {
    val user = Register("jiawei@ddd.com" + java.util.UUID.randomUUID.toString, "password")

    val result1 = userService.doRegister(user)
    assert(result1._1)

    val result2 = userService.doRegister(user)
    assert(!result2._1)
  }

  test("logging in should fail with wrong password") {
    val email = "jiawei@ddd.com" + java.util.UUID.randomUUID.toString

    val result1 = userService.doRegister(Register(email, "rightpassword"))
    assert(result1._1)

    val result2 = userService.doLogin(Login(email, "rightpassword"))
    assert(result2._1)

    val result3 = userService.doLogin(Login(email, "wrongpassword"))
    assert(!result3._1)
  }
}
