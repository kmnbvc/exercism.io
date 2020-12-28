import scala.util.Try

object ZebraPuzzle {

  case class Solution(waterDrinker: Resident, zebraOwner: Resident)

  lazy val solve: Solution = findSolution().orNull

  private def findSolution(): Option[Solution] = {
    val solutions = for {
      colors <- Seq(Red, Blue, Yellow, Ivory, Green).permutations if colorRules(colors)
      nations <- Seq(Englishman, Spaniard, Ukrainian, Norwegian, Japanese).permutations if nationRules(colors, nations)
      drinks <- Seq(Tea, Coffee, Milk, OrangeJuice, Water).permutations if drinkRules(colors, nations, drinks)
      pets <- Seq(Dog, Fox, Snails, Horse, Zebra).permutations if petRules(nations, pets)
      smokes <- Seq(Kools, LuckyStrike, OldGold, Chesterfields, Parliaments).permutations if smokeRules(colors, nations, drinks, pets, smokes)
    } yield Solution(nations(drinks.indexOf(Water)), nations(pets.indexOf(Zebra)))

    solutions.toStream.headOption
  }

  private def colorRules(colors: Seq[Color]): Boolean = colors.containsSlice(Seq(Ivory, Green))

  private def nationRules(colors: Seq[Color], nations: Seq[Resident]): Boolean =
    nations.head == Norwegian &&
      belongsTo(Red, Englishman, colors, nations) &&
      nextTo(Blue, Norwegian, colors, nations)

  private def drinkRules(colors: Seq[Color], nations: Seq[Resident], drinks: Seq[Drink]): Boolean =
    belongsTo(Tea, Ukrainian, drinks, nations) &&
      belongsTo(Coffee, Green, drinks, colors) &&
      drinks(2) == Milk

  private def petRules(nations: Seq[Resident], pets: Seq[Pet]): Boolean = belongsTo(Dog, Spaniard, pets, nations)

  private def smokeRules(colors: Seq[Color], nations: Seq[Resident], drinks: Seq[Drink], pets: Seq[Pet], smokes: Seq[Smoke]): Boolean =
    belongsTo(Snails, OldGold, pets, smokes) &&
      belongsTo(Kools, Yellow, smokes, colors) &&
      nextTo(Fox, Chesterfields, pets, smokes) &&
      belongsTo(OrangeJuice, LuckyStrike, drinks, smokes) &&
      nextTo(Kools, Horse, smokes, pets) &&
      belongsTo(Parliaments, Japanese, smokes, nations)


  private def belongsTo[A, T](value: A, target: T, values: Seq[A], targets: Seq[T]): Boolean = {
    values(targets.indexOf(target)) == value
  }

  private def nextTo[A, T](value: A, target: T, values: Seq[A], targets: Seq[T]): Boolean = {
    Try(values(targets.indexOf(target) - 1) == value).getOrElse(false) ||
      Try(values(targets.indexOf(target) + 1) == value).getOrElse(false)
  }

  sealed trait Resident
  case object Englishman extends Resident
  case object Spaniard extends Resident
  case object Ukrainian extends Resident
  case object Norwegian extends Resident
  case object Japanese extends Resident

  sealed trait Color
  case object Red extends Color
  case object Blue extends Color
  case object Yellow extends Color
  case object Ivory extends Color
  case object Green extends Color

  sealed trait Drink
  case object Tea extends Drink
  case object Coffee extends Drink
  case object Milk extends Drink
  case object OrangeJuice extends Drink
  case object Water extends Drink

  sealed trait Pet
  case object Dog extends Pet
  case object Fox extends Pet
  case object Snails extends Pet
  case object Horse extends Pet
  case object Zebra extends Pet

  sealed trait Smoke
  case object Kools extends Smoke
  case object LuckyStrike extends Smoke
  case object OldGold extends Smoke
  case object Chesterfields extends Smoke
  case object Parliaments extends Smoke

}
