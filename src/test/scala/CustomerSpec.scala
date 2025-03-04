import org.scalatest.wordspec.AnyWordSpec

import java.time.LocalDate

class CustomerSpec extends AnyWordSpec {
  val lananh = Customer("lananh", Some(DrinksCard(Nil)), LocalDate.of(1978,3,10))
  val lanWithPoints = Customer("lananh", Some(DrinksCard(List(1,1,1,1,1))), LocalDate.of(1978,3,10))
  val lanNoCard = Customer("lananh", None, LocalDate.of(1978,3,10))
  val lanTooYoung = Customer("lananh", None, LocalDate.of(2008,2,10))
  "getPoints" should {
    "return a List[Int] or an empty list" when {
      "customer has a DrinkCard with no stamps" in {
        val result:Int = lananh.getPoints
        assert(result == 0)
      }
    }
    "return a List[Int] or an empty list" when {
      "customer does not have a loyaltyCard " in {
        val result:Int = lanNoCard.getPoints
        assert(result == 0)
      }
    }
    "return 5 " when {
      "the customer has a card with 5 points" in {
        val result: Int = lanWithPoints.getPoints
        assert(result == 5)
      }
    }
  }

  "addCard" should {
    "return a UnderageError" when {
      "customer is under 18" in {
        val result = lanTooYoung.addCard(DrinksCard(Nil))
        assert(result == Left(UnderAge("17")))
      }
    }
  }

  "addCard" should {
    "return a CardExistsError" when {
      "customer already has a card" in {
        val result = lananh.addCard(DrinksCard(Nil))
        assert(result == Left(CardExists("Some(DrinksCard(List()))")))
      }
    }
  }

  "addCard" should {
    "add a DrinksCard to the customer" when {
      "criteria is met(over18 && no current card)" in {
        val result = lanNoCard.addCard(DrinksCard(Nil)).map(_.loyaltyCard)
        assert(result == Right(Some(DrinksCard(List()))))
      }
    }
  }
  "addCard" should {
    "add a DiscountCard to the customer" when {
      "criteria is met(over18 && no current card)" in {
        val result = lanNoCard.addCard(DiscountCard(Nil)).map(_.loyaltyCard)
        assert(result == Right(Some(DiscountCard(List()))))
      }
    }
  }



}
