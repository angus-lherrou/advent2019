import scala.io.Source

object CounterUpper2 extends App {
  def fuelReq (x: Int): Int = {
    val init = x/3-2
    if (init <= 0) 0
    else init + fuelReq(init)
  }

  var total: Int = 0

  val modules = Source.fromURL("https://raw.githubusercontent.com/angus-lherrou/advent2019/master/data/day1.txt")

  for (line<-modules.getLines()) {
    total += fuelReq(line.toInt)
  }

  println(total)
}