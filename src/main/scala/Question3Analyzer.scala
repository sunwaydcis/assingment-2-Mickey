// Question3Analyzer.scala
object Question3Analyzer {

  case class HotelProfitability(
                                 hotelName: String,
                                 totalVisitors: Int,
                                 avgProfitMargin: Double,
                                 profitabilityScore: Double
                               )

  def analyzeHotelProfitability(bookings: List[HotelBooking]): List[HotelProfitability] = {
    bookings.groupBy(_.hotelName)
      .view
      .mapValues { hotelBookings =>
        val totalVisitors = hotelBookings.map(_.visitorCount).sum
        val avgProfitMargin = hotelBookings.map(_.profitMargin).sum / hotelBookings.size
        val profitabilityScore = totalVisitors * avgProfitMargin
        HotelProfitability(hotelBookings.head.hotelName, totalVisitors, avgProfitMargin, profitabilityScore)
      }
      .values
      .toList
  }

  def analyze(bookings: List[HotelBooking]): Unit = {
    val hotelProfitability = analyzeHotelProfitability(bookings)
    val mostProfitable = hotelProfitability.maxBy(_.profitabilityScore)

    println("=" * 60)
    println("QUESTION 3: Which hotel is the most profitable considering visitors and profit margin?")
    println("=" * 60)
    println(s"Answer: ${mostProfitable.hotelName} is the most profitable hotel")
    println(s"   - Total Visitors: ${mostProfitable.totalVisitors}")
    println(s"   - Average Profit Margin: ${mostProfitable.avgProfitMargin.formatted("%.1f")}%")
    println(s"   - Profitability Score: ${mostProfitable.profitabilityScore.formatted("%.1f")}")

    println("\nAll hotels by profitability:")
    hotelProfitability.sortBy(-_.profitabilityScore).foreach { hotel =>
      println(s"  - ${hotel.hotelName}: Score=${hotel.profitabilityScore.formatted("%.1f")}, " +
        s"Visitors=${hotel.totalVisitors}, Margin=${hotel.avgProfitMargin.formatted("%.1f")}%")
    }
    println()
  }
}