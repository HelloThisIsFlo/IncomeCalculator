class CostLiving(config: CostLivingConfig) {

  def neededInDestination(currentIncome: Income):Income = {
    val currentNet = currentIncome.net
    val neededNet = currentNet * config.rateBetweenOriginAndDestination
    Income.fromNet(neededNet, config.baseIncomeDestination.taxPercentage)
  }

}


case class CostLivingConfig(baseIncomeOrigin: Income, baseIncomeDestination: Income) {

  def rateBetweenOriginAndDestination = baseIncomeDestination.net / baseIncomeOrigin.net
}