// Question3Analyzer.scala
object Question3Analyzer {

  def analyze(bookings: List[HotelBooking]): Unit = {
    println("\n" + "=" * 70)
    println("QUESTION 3: Which hotel is the most profitable?")
    println("(Considering number of visitors and profit margin)")
    println("=" * 70)

    if (bookings.isEmpty) {
      println("❌ No data available")
      return
    }

    // Group bookings by hotel name
    val bookingsByHotel = bookings.groupBy(_.hotelName)

    // Calculate profitability metrics for each hotel
    val hotelProfitability = bookingsByHotel.map { case (hotelName, hotelBookings) =>
      val totalVisitors = hotelBookings.map(_.noOfPeople).sum
      val avgProfitMargin = hotelBookings.map(_.profitMargin).sum / hotelBookings.size
      val totalRevenue = hotelBookings.map(_.bookingPrice).sum
      val estimatedProfit = totalRevenue * (avgProfitMargin / 100)
      val bookingCount = hotelBookings.size

      // Profitability score = total visitors × profit margin
      val profitabilityScore = totalVisitors * avgProfitMargin

      (hotelName, totalVisitors, avgProfitMargin, estimatedProfit, profitabilityScore, bookingCount)
    }

    // Filter hotels with at least 2 bookings
    val validHotels = hotelProfitability.filter(_._6 >= 2).toList

    if (validHotels.isEmpty) {
      println("❌ No hotels with sufficient booking data")
      return
    }

    // Find the most profitable hotel by profitability score
    val mostProfitable = validHotels.maxBy(_._5)

    println(s"\n✅ ANSWER: ${mostProfitable._1} is the most profitable hotel")
    println(s"   Total Visitors: ${mostProfitable._2}")
    println(f"   Average Profit Margin: ${mostProfitable._3}%.1f%%")
    println(f"   Estimated Total Profit: $$${mostProfitable._4}%.2f")
    println(f"   Profitability Score: ${mostProfitable._5}%.1f")
    println(s"   Based on ${mostProfitable._6} bookings")

    // Show top 5 profitable hotels
    println("\n📈 TOP 5 MOST PROFITABLE HOTELS:")
    validHotels.sortBy(-_._5).take(5).zipWithIndex.foreach {
      case ((name, visitors, margin, profit, score, count), index) =>
        println(s"\n   ${index + 1}. $name")
        println(s"      Total Visitors: $visitors")
        println(f"      Profit Margin: $margin%.1f%%")
        println(f"      Estimated Profit: $$$profit%.2f")
        println(f"      Profitability Score: $score%.1f")
    }
  }
}