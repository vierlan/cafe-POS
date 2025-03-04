import CafeLogic.{coke, lan, lanNone, lemonade, pie, roast, sandwich, steak}
import org.scalatest.wordspec.AnyWordSpec

class BillSpec extends AnyWordSpec {
  val bill = Bill
  val drinksOnlyOrder: Order = Order(List((coke, 1), (lemonade, 2)), lanNone) // 3
  val coldFoodOrder: Order = Order(List((sandwich, 1), (lemonade, 2)), lanNone) // 4
  val hotFoodOrder: Order = Order(List((coke, 1), (lemonade, 2), (pie, 2)), lanNone) // 9
  val premiumFoodOrder: Order = Order(List((coke, 1), (lemonade, 2), (pie, 2), (steak, 2)), lanNone) // 20
  "getServiceCharge" should {
    "return a zero service charge" when {
      "only drinks are ordered and serviceChoice is RegularServiceCharge" in {
        val result: Double = Bill(List(drinksOnlyOrder), RegularServiceCharge, None).getServiceCharge
        assert(result == 0)
      }
    }
    "return a 10% service charge" when {
      "only drinks and coldFoods are ordered and serviceChoice is RegularServiceCharge" in {
        val result: Double = Bill(List(coldFoodOrder), RegularServiceCharge, None).getServiceCharge
        assert(result == 0.1)
      }
    }
    "return a 20% service charge" when {
      "hot Foods are included in order and serviceChoice is RegularServiceCharge" in {
        val result: Double = Bill(List(hotFoodOrder), RegularServiceCharge, None).getServiceCharge
        assert(result == 0.2)
      }
    }
    "return a 25% service charge" when {
      "Premium Foods are included in order and serviceChoice is RegularServiceCharge" in {
        val result: Double = Bill(List(premiumFoodOrder), RegularServiceCharge, None).getServiceCharge
        assert(result == 0.25)
      }
    }
    "hot food is ordered and the serviceChoice is ReplacementService with 0 ServiceAmount" in {
      val result: Double = Bill(List(hotFoodOrder), ReplaceServiceCharge, Some(0)).getServiceCharge
    }
    "food is ordered and the serviceChoice is NoServiceCharge" in {
      val result: Double = Bill(List(hotFoodOrder), NoServiceCharge, Some(0)).getServiceCharge
    }
    "food is ordered and the serviceChoice is NoServiceCharge with a non-zero amount in ServiceAmount" in {
      val result: Double = Bill(List(hotFoodOrder), NoServiceCharge, Some(100)).getServiceCharge
    }
  }
  "applyServiceShould" should {
    "return the net total without service charge" when {
      "only drinks are ordered and serviceChoice is RegularServiceCharge" in {
        val result: Double = Bill(List(drinksOnlyOrder), RegularServiceCharge, None).applyServiceCharge
        assert(result == 3.0)
      }
      "cold food is ordered and the serviceChoice is ReplacementService with 0 ServiceAmount" in {
        val result: Double = Bill(List(coldFoodOrder), ReplaceServiceCharge, Some(0)).applyServiceCharge
        assert(result == 4)
      }
      "hot food is ordered and the serviceChoice is NoServiceCharge" in {
        val result: Double = Bill(List(hotFoodOrder), NoServiceCharge, Some(0)).applyServiceCharge
        assert(result == 9)
      }
      "Premium food is ordered and the serviceChoice is NoServiceCharge with a non-zero amount in ServiceAmount" in {
        val result: Double = Bill(List(premiumFoodOrder), NoServiceCharge, Some(100)).applyServiceCharge
        assert(result == 19)
      }
    }
  }

}
