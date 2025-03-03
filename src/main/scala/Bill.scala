import scala.reflect.ClassManifestFactory.Null

case class Bill(orders: List[Order], serviceChoice: ServiceChoice = RegularServiceCharge, serviceAmount: Option[Double]) {
  val serviceChargeApplicable: Double = getServiceCharge


  def getServiceCharge: Double = {
    //val itemList = orders.flatMap(item => item.orderItems)
    val multiplierList = orders.flatMap(item => item.orderItems.map(item => item._1.serviceChargeMultiplier))
    val highestMultiplier = multiplierList.reduce { (a, b) => a max b }
    highestMultiplier
  }

  def getNetTotal: Double = {
    val itemList = orders.flatMap(item => item.orderItems.map(item => item))
    val priceList = itemList.map(item => item._1.price)
    priceList.sum

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
      case None if (serviceChoice == NoServiceCharge) => getNetTotal
      case None if (serviceChoice == ReplaceServiceCharge) => totalWithRegularService
    }

  }
}

sealed trait ServiceChoice

case object NoServiceCharge extends ServiceChoice

case object RegularServiceCharge extends ServiceChoice

case object AdditionalService extends ServiceChoice

case object ReplaceServiceCharge extends ServiceChoice

