import java.time.LocalDate
import scala.:+

case class Customer(name: String, loyaltyCard: Option[LoyaltyCard], dateOfBirth: LocalDate) {
  def over18: Boolean = {
    true
  }

}

trait LoyaltyCard
case object DrinksCard extends LoyaltyCard {
  val stamps:List[Int] = Nil
  def countStamps: Int = stamps.length
  def addStamp(stampCard: Option[LoyaltyCard]):LoyaltyCard = stampCard.getOrElse(stamps)
  def useStamps(stampCard: Option[LoyaltyCard]):LoyaltyCard = stampCard.getOrElse(stamps)
}
case object DiscountCard extends LoyaltyCard {

}