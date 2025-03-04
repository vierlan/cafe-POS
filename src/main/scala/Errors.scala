abstract class Errors {
    def Message: String
  }

  case class NoStockError(item: String) extends Errors {
    override def Message: String = s"There is not enough $item"
  }

  case class UnderAge(age: String) extends Errors {
    override def Message: String = s"This Customer is only $age years old"
  }

  case class NoCardError(card: String) extends Errors {
    override def Message: String = s"$card is an invalid card choice"
  }

  case class CardExists(card: String) extends Errors {
    override def Message: String = s"Customer already has $card card"
  }




