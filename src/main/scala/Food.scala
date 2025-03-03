abstract class Food(val name: String,val price: Double, val inventory: Int) {
  val serviceChargeMultiplier: Double

}

case class Drink(override val name: String, override val price: Double, override val inventory: Int) extends Food(name, price, inventory) {
  override val serviceChargeMultiplier: Double = 0
  def addStock(unitsAdded: Int): Int = inventory + unitsAdded
}

case class ColdFood(override val name: String, override val price: Double, override val inventory: Int) extends Food(name, price, inventory) {
  override val serviceChargeMultiplier: Double = 0.1
}

case class HotFood(override val name: String, override val price: Double, override val inventory: Int) extends Food(name, price, inventory) {
  override val serviceChargeMultiplier: Double = 0.2
}

case class PremiumFood(override val name: String, override val price: Double, override val inventory: Int) extends Food(name, price, inventory) {
  override val serviceChargeMultiplier: Double = 0.25
}
