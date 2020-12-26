import scala.Function.const
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

    solutions.find(const(true))
  }

  private def colorRules(colors: Seq[Color]): Boolean = Try(colors(colors.indexOf(Green) - 1) == Ivory).getOrElse(false)

  private def nationRules(colors: Seq[Color], nations: Seq[Resident]): Boolean =
    nations.head == Norwegian && colors(nations.indexOf(Englishman)) == Red &&
      (Try(colors(nations.indexOf(Norwegian) - 1) == Blue).getOrElse(false) ||
        Try(colors(nations.indexOf(Norwegian) + 1) == Blue).getOrElse(false))

  private def drinkRules(colors: Seq[Color], nations: Seq[Resident], drinks: Seq[Drink]): Boolean =
    drinks(nations.indexOf(Ukrainian)) == Tea &&
      drinks(colors.indexOf(Green)) == Coffee &&
      drinks(2) == Milk

  private def petRules(nations: Seq[Resident], pets: Seq[Pet]): Boolean = pets(nations.indexOf(Spaniard)) == Dog

  private def smokeRules(colors: Seq[Color], nations: Seq[Resident], drinks: Seq[Drink], pets: Seq[Pet], smokes: Seq[Smoke]): Boolean =
    pets(smokes.indexOf(OldGold)) == Snails &&
      smokes(colors.indexOf(Yellow)) == Kools &&
      (Try(pets(smokes.indexOf(Chesterfields) - 1) == Fox).getOrElse(false) ||
        Try(pets(smokes.indexOf(Chesterfields) + 1) == Fox).getOrElse(false)) &&
      drinks(smokes.indexOf(LuckyStrike)) == OrangeJuice &&
      (Try(smokes(pets.indexOf(Horse) - 1) == Kools).getOrElse(false) ||
        Try(smokes(pets.indexOf(Horse) + 1) == Kools).getOrElse(false)) &&
      smokes(nations.indexOf(Japanese)) == Parliaments


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
