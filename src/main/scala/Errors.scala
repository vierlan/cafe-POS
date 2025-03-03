abstract class Errors {
    def Message: String
  }

  case class NoStockError(item: String) extends Errors {
    override def Message: String = s"There is not enough $item"
  }

  case class InvalidPostcodeError(message: String) extends Errors {
    override def Message: String = "The Postcode is Invalid"
  }


