trait BankAccount {

  def closeAccount(): Unit

  def getBalance: Option[Int]

  def incrementBalance(increment: Int): Option[Int]
}

object Bank {
  def openAccount(): BankAccount = new Client()
}

class Client extends BankAccount {

  private var balance = Option(0)

  override def closeAccount(): Unit = {
    balance = None
  }

  override def getBalance: Option[Int] = balance

  override def incrementBalance(increment: Int): Option[Int] = synchronized {
    balance = balance.map(_ + increment)
    balance
  }
}