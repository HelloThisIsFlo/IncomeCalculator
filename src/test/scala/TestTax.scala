import org.scalatest.{FunSpec, BeforeAndAfter, FunSuite}

class TestTax extends FunSuite with BeforeAndAfter{

  /**
    * Object oriented way. Will do functional later
    */

  val netIncome = 1000
  val brutIncome = 90000
  val baseSalaryOriginCountry = 3100 // euros
  val equivalentSalaryDestinationCountry = 4861 //euros

  test("Tax is 50% -> get half of salary") {
    val calculator = new Calculator(50)
    assert(45000 == calculator.toNet(brutIncome))
  }

  test("Tax is 40% -> get 60% of salary") {
    val calculator = new Calculator(40)
    assert(90000 * 0.6 == calculator.toNet(brutIncome))
  }

  test("Base salary") {
    val calculator = new Calculator(37, baseSalaryOriginCountry, equivalentSalaryDestinationCountry)
    val originalNetSalary = 2000
    assert(rateBetweenCities * originalNetSalary == calculator.netNeededDestination(originalNetSalary))
  }

  test("Tax is 40% -> brut is net / 0.60") {
    val calculator = new Calculator(40)
    assert(netIncome / 0.6 == calculator.toBrut(netIncome))
  }

  test("Income in origin city -> brut in destination") {
    val calculator = new Calculator(40, 2000, 6000)
    val originalNetSalary = 1000
    assert(originalNetSalary* (6000/2000) / 0.6 == calculator.brutNeededDestination(originalNetSalary))
  }

  test("previous test with income return tax") {
    val calculator = new Calculator(37, baseSalaryOriginCountry, equivalentSalaryDestinationCountry)
    val originalSalary = NetIncome(2000, 40)

    val result = calculator.neededDestination(originalSalary)

    assert(rateBetweenCities * originalSalary.net == calculator.neededDestination(originalSalary).net)
  }

  //todo get tax from salary, test with different rates

  def rateBetweenCities = equivalentSalaryDestinationCountry / baseSalaryOriginCountry.toDouble
}
