object Question3Analyzer {
  /**
   * Sophisticated profitability analysis with dual-factor normalization
   * Demonstrates advanced statistical techniques using collection operations
   */
  def analyze(bookings: List[HotelBooking]): Unit = {
    println("\n" + "=" * 70)
    println("QUESTION 3: Which hotel is the most profitable?")
    println("(Considering number of visitors and profit margin)")
    println("=" * 70)
    // Early validation - prevents processing errors
    if (bookings.isEmpty) {
      println("❌ No data available")
      return
    }

    // Group by hotel location tuple for precise analysis
    val hotelMetrics = bookings
      .groupBy(b => (b.hotelName, b.destinationCity, b.destinationCountry))
      .map { case ((hotelName, city, country), hotelBookings) =>
        // Multiple aggregations in single transformation
        val bookingCount = hotelBookings.size
        val totalVisitors = hotelBookings.map(_.noOfPeople).sum
        val avgProfitMargin = hotelBookings.map(_.profitMargin).sum / bookingCount

        // 6-element tuple with location and aggregated metrics
        (hotelName, city, country, totalVisitors, avgProfitMargin, bookingCount)
      }
      .filter(_._6 >= 1)  // Minimum data threshold
      .toList

    if (hotelMetrics.isEmpty) {
      println("❌ No hotels with sufficient booking data")
      return
    }

    // Extract metrics for normalization ranges
    val allVisitors = hotelMetrics.map(_._4)
    val allMargins = hotelMetrics.map(_._5)
    // Calculate normalization boundaries
    val minVisitors = allVisitors.min
    val maxVisitors = allVisitors.max
    val minMargin = allMargins.min
    val maxMargin = allMargins.max

    // Calculate normalized scores with edge case handling
    val hotelsWithScores = hotelMetrics.map {
      case (hotel, city, country, visitors, margin, bookings) =>

        // Normalize visitors: linear scaling 0-1 / (max - min)
        val visitorScore = if (maxVisitors > minVisitors)
          (visitors - minVisitors).toDouble / (maxVisitors - minVisitors)
        else 0.5 // Default when all values are identical

        // Normalize profit margin: linear scaling 0-1
        val marginScore = if (maxMargin > minMargin)
          (margin - minMargin) / (maxMargin - minMargin)
        else 0.5 // Default when all values are identical

        // Weighted composite scoring algorithm
        // Equal weighting: 50% volume (visitors) + 50% profitability (margin)
        val combinedScore = (visitorScore + marginScore) / 2
        // 9-element tuple with comprehensive profitability metrics
        // (hotel, city, country, combinedScore, visitorScore, marginScore, visitors, margin, bookings)
        (hotel, city, country, combinedScore, visitorScore, marginScore, visitors, margin, bookings)
    }

    // maxBy finds hotel with highest combined score
    val mostProfitable = hotelsWithScores.maxBy(_._4)

    // Professional formatted output for primary answer
    println("\n3. MOST PROFITABLE HOTEL")
    println(s"   ► Hotel: ${mostProfitable._1}")
    println(s"   ► City: ${mostProfitable._2}")
    println(s"   ► Country: ${mostProfitable._3}")
    println(f"   ► Combined Score: ${mostProfitable._4 * 100}%.2f")
    println(s"   ► Total Visitors: ${mostProfitable._7}")
    println(f"   ► Average Profit Margin: ${mostProfitable._8 * 100}%.1f%%")
    println(s"   ► Total Bookings: ${mostProfitable._9}")

    // Show top 3 profitable hotels in horizontal format
    println("\n" + "-" * 90)
    println("TOP 3 MOST PROFITABLE HOTELS:")
    println("-" * 90)
    // Sort descending and take top 3
    val top3Profitable = hotelsWithScores.sortBy(-_._4).take(3)
    // Multi-column formatted output using printf
    // Creates professional table-like comparison across 3 hotels

    // Headers with ranking numbers
    println()
    printf("%-30s %-30s %-30s\n",
      s"1. ${top3Profitable(0)._1}",
      s"2. ${top3Profitable(1)._1}",
      s"3. ${top3Profitable(2)._1}")

    // Location information (city, country)
    printf("%-30s %-30s %-30s\n",
      s"   ${top3Profitable(0)._2}, ${top3Profitable(0)._3}",
      s"   ${top3Profitable(1)._2}, ${top3Profitable(1)._3}",
      s"   ${top3Profitable(2)._2}, ${top3Profitable(2)._3}")

    // Combined profitability scores
    printf("%-30s %-30s %-30s\n",
      f"   Score: ${top3Profitable(0)._4 * 100}%.2f",
      f"   Score: ${top3Profitable(1)._4 * 100}%.2f",
      f"   Score: ${top3Profitable(2)._4 * 100}%.2f")

    // Visitor component scores (normalized)
    printf("%-30s %-30s %-30s\n",
      f"   Visitor Score: ${top3Profitable(0)._5 * 100}%.1f",
      f"   Visitor Score: ${top3Profitable(1)._5 * 100}%.1f",
      f"   Visitor Score: ${top3Profitable(2)._5 * 100}%.1f")

    // Margin component scores (normalized)
    printf("%-30s %-30s %-30s\n",
      f"   Margin Score: ${top3Profitable(0)._6 * 100}%.1f",
      f"   Margin Score: ${top3Profitable(1)._6 * 100}%.1f",
      f"   Margin Score: ${top3Profitable(2)._6 * 100}%.1f")

    // Total visitor counts (raw metric)
    printf("%-30s %-30s %-30s\n",
      s"   Visitors: ${top3Profitable(0)._7}",
      s"   Visitors: ${top3Profitable(1)._7}",
      s"   Visitors: ${top3Profitable(2)._7}")

    // Average profit margins (raw metric)
    printf("%-30s %-30s %-30s\n",
      f"   Profit Margin: ${top3Profitable(0)._8 * 100}%.1f%%",
      f"   Profit Margin: ${top3Profitable(1)._8 * 100}%.1f%%",
      f"   Profit Margin: ${top3Profitable(2)._8 * 100}%.1f%%")

    // Booking transaction counts
    printf("%-30s %-30s %-30s\n",
      s"   Bookings: ${top3Profitable(0)._9}",
      s"   Bookings: ${top3Profitable(1)._9}",
      s"   Bookings: ${top3Profitable(2)._9}")

    println("=" * 90)
  }
}