case class Menu(drinks: List[Drink], coldFoods: List[ColdFood], hotFoods: List[HotFood], premiumFoods: List[PremiumFood]) {
  def addPremiumFood (item: PremiumFood) : Menu = {
    Menu(drinks, coldFoods, hotFoods, premiumFoods:+ item)
  }

  def removePremiumFood(itemToRemove: PremiumFood): Menu = {
    Menu(drinks, coldFoods, hotFoods, premiumFoods.filterNot(item => item == itemToRemove))
  }
}
