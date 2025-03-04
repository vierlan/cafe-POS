import scala.reflect.ClassManifestFactory.Null

case class Bill(orders: List[Order], serviceChoice: ServiceChoice = RegularServiceCharge, serviceAmount: Option[Double]) {
  val serviceChargeApplicable: Double = getServiceCharge


  def getServiceCharge: Double = {
    val multiplierList = orders.flatMap(item => item.orderItems.map(item => item._1.serviceChargeMultiplier))
    println("multiplierList: " + multiplierList)
    val highestMultiplier = multiplierList.max
    println("highestMultiplier: " + highestMultiplier)
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

  def stampCard: Any = {
    val loyaltyCard = getLoyaltyCard
    println("stampcard" + loyaltyCard)
    loyaltyCard match {
      case Some(DrinksCard(_)) if isDrinkInOrder(orders) => {
        if (loyaltyCard.map(_.getPoints.length).getOrElse(0) > 10) {
          orders.head.customer.useStamps
        } else {
          orders.head.customer.addStamp
        }
      }
      case Some(DiscountCard(_)) if getNetTotal > 20 && loyaltyCard.exists(_.points.length < 8) => {
        orders.head.customer.addStamp
      }
      case None => None
    }
  }
    def isDrinkInOrder(orders: List[Order]): Boolean = {
      val foodList = orders.flatMap(item => item.orderItems.map(item => item._1))
      val checkForDrink = foodList.exists {
        case (food: Drink) => true
        case _ => false
      }
      checkForDrink
    }
  }


sealed trait ServiceChoice

case object NoServiceCharge extends ServiceChoice

case object RegularServiceCharge extends ServiceChoice

case object AdditionalService extends ServiceChoice

case object ReplaceServiceCharge extends ServiceChoice

