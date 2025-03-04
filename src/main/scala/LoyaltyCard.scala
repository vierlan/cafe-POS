abstract class LoyaltyCard(val points: List[Int]) {
  def getPoints: List[Int] = points

}

case class DrinksCard(override val points: List[Int]) extends LoyaltyCard(points) {

  def countStamps: Int = points.length
  def addStamp: DrinksCard = this.copy(points = 1 :: points)
  def useStamps: DrinksCard = this.copy(points = points.drop(10))
}
case class DiscountCard(override val points: List[Int]) extends LoyaltyCard(points) {
  def addStar: DiscountCard = this.copy(points = 1 :: points)
}

