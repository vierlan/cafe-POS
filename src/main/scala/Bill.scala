import scala.reflect.ClassManifestFactory.Null

case class Bill(orders: List[Order], serviceChoice: ServiceChoice = RegularServiceCharge, serviceAmount: Option[Double]) {
  val serviceChargeApplicable: Double = getServiceCharge


  def getServiceCharge: Double = {
    val multiplierList = orders.flatMap(item => item.orderItems.map(item => item._1.serviceChargeMultiplier))
    val highestMultiplier = multiplierList.reduce { (a, b) => a max b }
    highestMultiplier
  }

  def getItemTotal(price: Double, quantity: Int): Double = price * quantity

  def getNetTotal: Double = {
    val itemList = orders.flatMap(item => item.orderItems.map(item => item))
    val priceQuantityTuple: List[(Double, Int)] = itemList.map {
      case (food, quantity) => (food.price, quantity)
    }
    val total = priceQuantityTuple.map {
      case (price, quantity) => price * quantity
    }.sum
    total
  }

  def applyAdditionalService(amount: Double): Double = {
    totalWithRegularService + amount
  }


  def applyReplacementService(amount: Double): Double = {
    getNetTotal + amount
  }

  def totalWithRegularService: Double = {
    getNetTotal + (getNetTotal * serviceChargeApplicable)
  }

  def applyServiceCharge: Double = {
    val service = Some(serviceAmount)
    serviceAmount match {
      case Some(amount) if (serviceChoice == AdditionalService) => applyAdditionalService(amount)
      case Some(amount) if (serviceChoice == ReplaceServiceCharge) => applyReplacementService(amount)
      case Some(amount) if (serviceChoice == NoServiceCharge) => getNetTotal
      case None if (serviceChoice == NoServiceCharge) => getNetTotal
      case None if (serviceChoice == ReplaceServiceCharge) => totalWithRegularService
      case None if (serviceChoice == RegularServiceCharge) => totalWithRegularService
      case None if (serviceChoice == AdditionalService) => totalWithRegularService
    }
  }

  def getLoyaltyCard: Option[LoyaltyCard] = {
    orders.head.customer.loyaltyCard match {
      case Some(card) => Some(card)
      case None => None
    }
  }

      def stampCard: Any  = {
        val loyaltyCard = getLoyaltyCard
        loyaltyCard match {
          case Some(DrinksCard) if isDrinkInOrder => {
            if (DrinksCard.countStamps < 10) {
              println("this"+ loyaltyCard)
              DrinksCard.addStamp(loyaltyCard)
            } else {
              DrinksCard.useStamps(Some(loyaltyCard))
            }
          }
          case Some(DiscountCard) => 2
          case None => None
        }
      }
  def isDrinkInOrder: Boolean = {
    val foodList = orders.flatMap(item => item.orderItems.map(item => item._1))
    val checkForDrink = foodList.exists {
      case (food: Drink) => true
      case _ => false
    }
    checkForDrink
  }


}
  //object bill {
  //  val finalBill = Bill(orders = ???, serviceChoice = ???, serviceAmount = ???)
  //}

  sealed trait ServiceChoice

  case object NoServiceCharge extends ServiceChoice

  case object RegularServiceCharge extends ServiceChoice

  case object AdditionalService extends ServiceChoice

  case object ReplaceServiceCharge extends ServiceChoice

