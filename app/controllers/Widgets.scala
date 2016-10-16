package controllers

import javax.inject.Inject

import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller, BodyParsers}
import play.modules.reactivemongo.{MongoController, ReactiveMongoApi, ReactiveMongoComponents}
import reactivemongo.bson.{BSONDocument, BSONObjectID}
import repos.WidgetRepoImpl

import scala.util.Try

object WidgetFields {
  val Id = "_id"
  val Name ="name"
  val Value = "value"
}

class Widgets @Inject()(val reactiveMongoApi: ReactiveMongoApi) extends Controller
        with MongoController with ReactiveMongoComponents {

  import controllers.WidgetFields._

  def widgetRepo = new WidgetRepoImpl(reactiveMongoApi)


  def index = Action.async { implicit request =>
    widgetRepo.find().map(widgets => Ok(Json.toJson(widgets)))
  }

  def create = Action.async(BodyParsers.parse.json) { implicit request => 
    val name = (request.body \ Name).as[String]
    //val value = (request.body \ Value).as[Int]
    widgetRepo.save(BSONDocument(
      Name -> name//,
      //Value -> value 
      )).map(result => Created)
  }
  def read(id: String) = Action.async { implicit request =>
    widgetRepo.select(BSONDocument("_id" -> BSONObjectID(id))).map(widget => Ok(Json.toJson(widget)))
  }
  def update(id: String) = Action.async(BodyParsers.parse.json) { implicit request =>
    val name = (request.body \ Name).as[String]
    //val value = (request.body \ Value).as[Int]
    widgetRepo.update(BSONDocument(Id -> BSONObjectID(id)),
      BSONDocument(
        "$set" -> BSONDocument(Name -> name/*, Value -> value */) 
      )).map(result => Accepted)
  }
  def delete(id: String) = Action.async { implicit request =>
    widgetRepo.remove(BSONDocument(Id -> BSONObjectID(id))).map(request => Accepted)
  }

}
