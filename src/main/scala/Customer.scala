import java.time.{LocalDate, Period}


class Customer(val name: String, val loyaltyCard: Option[LoyaltyCard], val dateOfBirth: LocalDate) {
  def age: Int = Period.between(dateOfBirth, LocalDate.now()).getYears

  def over18: Boolean = {
    age >= 18
  }

  def addCard(desiredCard: LoyaltyCard): Either[Errors, Customer] = {
    if (!over18) {
      Left(UnderAge(age.toString))
    } else if (loyaltyCard.isDefined) {
      Left(CardExists(loyaltyCard.toString))
    } else {
      Right(new Customer(name, Some(desiredCard), dateOfBirth))
    }
  }

  def getPoints: Int = loyaltyCard.map(_.getPoints).getOrElse(Nil).length

  def addStamp: Either[Errors, Customer] = loyaltyCard match {
    case Some(drinksCard: DrinksCard) =>
      Right(new Customer(name, Some(drinksCard.addStamp), dateOfBirth))
    case Some(discountCard: DiscountCard) =>
      Right(new Customer(name, Some(discountCard.addStar), dateOfBirth))
    case None =>
      Left(CardExists("No loyalty card found."))
  }

  def useStamps: Either[Errors, Customer] = loyaltyCard match {
    case Some(drinksCard: DrinksCard) =>
      Right(new Customer(name, Some(drinksCard.useStamps), dateOfBirth))
    case Some(_: DiscountCard) =>
      Left(CardExists("DiscountCard does not support stamps."))
    case None =>
      Left(CardExists("No loyalty card found."))
  }
}

object Customer {
  def apply(name: String, loyaltyCard: Option[LoyaltyCard], dateOfBirth: LocalDate) = new Customer(name, loyaltyCard, dateOfBirth)
}



