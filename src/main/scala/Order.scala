import scala.collection.IterableOnce.iterableOnceExtensionMethods
import scala.collection.immutable.Nil.forall

case class Order(orderItems: List[(Food, Int)], table: Int) {
  val takeOut: Boolean = if (table == 0) true else false
}

object Order {

  def processOrder(orderItems: List[(Food, Int)]): Either[Errors, List[Food]] = {
    val enoughStock = checkEnoughStock(orderItems, Nil)
    if (enoughStock.contains(false)) {
      Left(NoStockError("stock to fulfill the order(processOrder Error"))
  } else {
      Right(orderItems.map(item => depleteStock(item)))
    }
  }

  def depleteStock(orderItem: (Food, Int)):Food = {
    orderItem._1 match {
      case ColdFood(name, price, inventory) => ColdFood(orderItem._1.name, orderItem._1.price, (orderItem._1.inventory)-(orderItem._2))
      case Drink(name, price, inventory) => Drink(orderItem._1.name, orderItem._1.price, (orderItem._1.inventory)-(orderItem._2))
      case HotFood(name, price, inventory) => HotFood(orderItem._1.name, orderItem._1.price, (orderItem._1.inventory)-(orderItem._2))
      case PremiumFood(name, price, inventory) => PremiumFood(orderItem._1.name, orderItem._1.price, (orderItem._1.inventory)-(orderItem._2))
    }
  }

  def checkEnoughStock(orderItems: List[(Food, Int)],acc: List[Boolean]): Either[Errors, List[Boolean]] = {
    if (orderItems.isEmpty) Right(acc)
    else {
       val item = orderItems.head
       val tail = orderItems.tail
        if (item._1.inventory > item._2) {
          checkEnoughStock(tail, true :: acc)
        } else {
          Left(NoStockError(s"$item"))
        }
    }
  }
}