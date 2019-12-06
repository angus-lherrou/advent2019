import scala.io.Source

object CounterUpper extends App {
  val fuelReq = (x: Int) => x/3-2

  var total: Int = 0

  val modules = Source.fromURL("https://raw.githubusercontent.com/angus-lherrou/advent2019/master/data/day1.txt")

  for (line<-modules.getLines()) {
    total += fuelReq(line.toInt)
  }

  println(total)
}
