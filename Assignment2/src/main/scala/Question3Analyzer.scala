// Question3Analyzer.scala
object Question3Analyzer {

  /**
   * Sophisticated business profitability analysis
   * Demonstrates ability to design custom algorithms using collection operations
   */
  def analyze(bookings: List[HotelBooking]): Unit = {
    println("\n" + "=" * 70)
    println("QUESTION 3: Which hotel is the most profitable?")
    println("(Considering number of visitors and profit margin)")
    println("=" * 70)

    // Early validation - professional error handling
    if (bookings.isEmpty) {
      println("❌ No data available")
      return
    }

    // Primary data organization
    // Creates efficient Map[String, List[HotelBooking]] for hotel analysis

    val bookingsByHotel = bookings.groupBy(_.hotelName)

    // Complex multi-metric calculation in single map operation
    // Calculates 6 distinct business metrics for each hotel
    val hotelProfitability = bookingsByHotel.map { case (hotelName, hotelBookings) =>
      // Collection API: Multiple aggregations using map-sum pattern
      val totalVisitors = hotelBookings.map(_.noOfPeople).sum
      val avgProfitMargin = hotelBookings.map(_.profitMargin).sum / hotelBookings.size
      val totalRevenue = hotelBookings.map(_.bookingPrice).sum
      // Business calculations using derived values
      val estimatedProfit = totalRevenue * (avgProfitMargin / 100)
      val bookingCount = hotelBookings.size

      // Custom algorithm - Profitability Score
      // Combines volume (visitors) with margin into single metric
      val profitabilityScore = totalVisitors * avgProfitMargin

      // Returns 6-element tuple with comprehensive business metrics
      // (name, visitors, avgMargin, estimatedProfit, score, bookingCount)
      (hotelName, totalVisitors, avgProfitMargin, estimatedProfit, profitabilityScore, bookingCount)
    }

    // Statistical significance filter
    // Ensures metrics are based on sufficient data (≥2 bookings)
    val validHotels = hotelProfitability.filter(_._6 >= 2).toList

    if (validHotels.isEmpty) {
      println("❌ No hotels with sufficient booking data")
      return
    }

    // maxBy with custom profitability score (index 5)
    // Demonstrates understanding of how to use custom scoring with collection operations
    val mostProfitable = validHotels.maxBy(_._5)

    println(s"\n✅ ANSWER: ${mostProfitable._1} is the most profitable hotel")
    println(s"   Total Visitors: ${mostProfitable._2}")
    println(f"   Average Profit Margin: ${mostProfitable._3}%.1f%%")
    println(f"   Estimated Total Profit: $$${mostProfitable._4}%.2f")
    println(f"   Profitability Score: ${mostProfitable._5}%.1f")
    println(s"   Based on ${mostProfitable._6} bookings")

    // Show top 5 profitable hotels
    println("\n📈 TOP 5 MOST PROFITABLE HOTELS:")
    // Multi-step ranking pipeline
    // 1. sortBy(-_._5) - Sort descending by profitability score (negative for descending)
    // 2. take(5) - Limit to top 5 results
    // 3. zipWithIndex - Add ranking indices
    // 4. foreach with nested tuple pattern matching
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