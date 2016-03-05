import org.scalatest.{BeforeAndAfter, FunSuite}

/**
  * Created by Shock on 3/5/2016.
  */
class CostLivingTest extends FunSuite with BeforeAndAfter {

  val taxPercentageOrigin = 40

  val sameCostLiving = {
    val baseOrigin = Income.fromNet(3100, taxPercentageOrigin)
    val baseDestination = Income.fromNet(3100, taxPercentageOrigin)
    CostLivingConfig(baseOrigin, baseDestination)
  }

  val sameCostLivingDifferentTaxes = {
    val baseOrigin = Income.fromNet(3100, taxPercentageOrigin)
    val baseDestination = Income.fromNet(3100, 80)
    CostLivingConfig(baseOrigin, baseDestination)
  }

  val costLiving150MoreExpensiveSameTaxes = {
    val baseOrigin = Income.fromNet(3000, taxPercentageOrigin)
    val baseDestination = Income.fromNet(4500, taxPercentageOrigin)
    CostLivingConfig(baseOrigin, baseDestination)
  }

  val costLiving150MoreExpensiveDifferentTaxes = {
    val baseOrigin = Income.fromNet(3000, taxPercentageOrigin)
    val baseDestination = Income.fromNet(4500, 30)
    CostLivingConfig(baseOrigin, baseDestination)
  }

  val currentIncome = Income.fromNet(1000, taxPercentageOrigin)

  test("Same cost of living return original salary") {
    val costLiving = new CostLiving(sameCostLiving)

    val neededIncome = costLiving.neededInDestination(currentIncome)
    assert(neededIncome == currentIncome)
  }

  test("Different taxes same cost of living") {
    val costLiving = new CostLiving(sameCostLivingDifferentTaxes)

    val neededIncome = costLiving.neededInDestination(currentIncome)
    val taxDifference = sameCostLiving.baseIncomeDestination.tax / (taxPercentageOrigin / 100D)
    assert(neededIncome.net * taxDifference == neededIncome.net)
  }

  test("Different taxes same cost of living return original salary (Income class takes care of taxes)") {
    val costLiving = new CostLiving(sameCostLivingDifferentTaxes)

    val neededIncome = costLiving.neededInDestination(currentIncome)
    assert(neededIncome == neededIncome)
  }

  test("Different cost of living returns needed income") {
    val costLiving = new CostLiving(costLiving150MoreExpensiveSameTaxes)

    val neededIncome = costLiving.neededInDestination(currentIncome)
    val expected = Income.fromNet(currentIncome.net * 1.5, taxPercentageOrigin)
    assert(expected == neededIncome)
  }

  test("Different cost of living and different taxes returns needed income") {
    val costLiving = new CostLiving(costLiving150MoreExpensiveDifferentTaxes)

    val neededIncome = costLiving.neededInDestination(currentIncome)
    val expected = Income.fromNet(currentIncome.net * 1.5, 30)
    assert(expected == neededIncome)
  }

}
