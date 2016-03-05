abstract class Income(taxPercentage: Double) {
  def net: Double

  def brut: Double

  def netRate = 1 - taxPercentage / 100
}

/**
  * Created by Shock on 3/5/2016.
  */
class Calculator(destinationTaxPercentage: Double, baseSalaryOriginalCountry: Double = 3100, equivalentSalaryDestination: Double = 3100) {
  def neededDestination(originalSalary: Income): Income = {
    val netNeeded = netNeededDestination(originalSalary.net)
    NetIncome(netNeeded, destinationTaxPercentage)
  }


  def brutNeededDestination(originalNetSalary: Double) = {
    NetIncome(netNeededDestination(originalNetSalary), destinationTaxPercentage).brut
  }

  def netNeededDestination(originalNetSalary: Double) = rateBetweenCities * originalNetSalary

  def rateBetweenCities = equivalentSalaryDestination / baseSalaryOriginalCountry

  def toBrut(netIncome: Double) = NetIncome(netIncome, destinationTaxPercentage).brut

  def toNet(brutIncome: Double) = BrutIncome(brutIncome, destinationTaxPercentage).net
}

case class NetIncome(netIncome: Double, taxPercentage: Double) extends Income(taxPercentage) {
  override def net = netIncome

  override def brut = netIncome / netRate
}

case class BrutIncome(brutIncome: Double, taxPercentage: Double) extends Income(taxPercentage) {
  override def net = brutIncome * netRate

  override def brut = brutIncome
}
