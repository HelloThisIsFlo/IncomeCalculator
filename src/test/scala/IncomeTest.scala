import org.scalatest.{BeforeAndAfter, FunSuite}

class IncomeTest extends FunSuite with BeforeAndAfter {

  val tax = 40

  test("Zero income is zero net") {
    assert(0 == Income.fromNet(0, tax).net)
  }

  test("100 net income is 100 net") {
    assert(100 == Income.fromNet(100, tax).net)
  }

  test("Zero net income is zero brut") {
    assert(0 == Income.fromNet(0, tax).brut)
  }

  test("100 net income is 100/60% brut if tax is 40%") {
    assert(100 / (60D / 100) == Income.fromNet(100, tax).brut)
  }

  test("250 brut income is 250 brut") {
    assert(250 == Income.fromBrut(250, tax).brut)
  }

  test("250 brut income is 250 * 60% net if tax is 40%") {
    assert(250 * (60D / 100) == Income.fromBrut(250, tax).net)
  }

  test("2 incomes equal if same brut and net values") {
    val net = Income.fromNet(180, tax) //tax == 40
    val brut = Income.fromBrut(300, tax)

    assert(net == brut)
  }

  test("1000 monthly equals 12000 yearly") {
    assert(12000 == Income.fromNet(1000, 0).netYearly)
  }

  test("1000 monthly brut is 24000 yearly if tax is 50%") {
    assert(24000 == Income.fromNet(1000, 50).brutYearly)
  }
}
