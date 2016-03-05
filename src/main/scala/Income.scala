abstract class Income(taxPercentage: Double) {
  def tax = taxPercentage / 100

  def net:Double
  def brut:Double
  def netYearly:Double = net * 12
  def brutYearly:Double = brut * 12

  override def equals(obj: scala.Any): Boolean = obj match {
    case income: Income => income.net == this.net && income.brut == this.brut
    case _ => false
  }
}

object Income {
  def fromBrut(income: Double, taxPercentage: Double):Income = BrutIncome(income, taxPercentage)
  def fromNet(income: Double, taxPercentage: Double):Income = NetIncome(income, taxPercentage)
}

private case class NetIncome(income: Double, taxPercentage: Double) extends Income(taxPercentage) {
  def net = income

  def brut = income / (1 - tax)
}

private case class BrutIncome(income: Double, taxPercentage: Double) extends Income(taxPercentage)  {
  def brut = income

  def net = income * (1 - tax)
}
