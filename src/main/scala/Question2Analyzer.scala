object Question2Analyzer {
  /**
   * Sophisticated multi-dimensional economical analysis
   * Demonstrates advanced collection operations with complex scoring algorithms
   */
  def analyze(bookings: List[HotelBooking]): Unit = {
    println("\n" + "=" * 70)
    println("QUESTION 2: Which hotel offers the most economical option?")
    println("=" * 70)
    // Early validation - professional error handling
    if (bookings.isEmpty) {
      println("❌ No data available")
      return
    }
    // Multi-level grouping with tuple key
    // Groups by (hotelName, city, country) for precise location-based analysis
    // Group by hotel + city + country
    val hotelStats = bookings
      .groupBy(b => (b.hotelName, b.destinationCity, b.destinationCountry))
      .map { case ((hotelName, city, country), hotelBookings) =>
        // Multiple aggregations in single transformation
        val bookingCount = hotelBookings.size
        val avgPrice = hotelBookings.map(_.bookingPrice).sum / bookingCount
        val avgDiscount = hotelBookings.map(_.discountValue).sum / bookingCount
        val avgMargin = hotelBookings.map(_.profitMargin).sum / bookingCount

        // Returns 7-element tuple with comprehensive hotel statistics
        (hotelName, city, country, avgPrice, avgDiscount, avgMargin, bookingCount)
      }
      .filter(_._7 >= 1)  // Statistical significance filter
      .toList

    if (hotelStats.isEmpty) {
      println("❌ No hotels with sufficient booking data")
      return
    }

    // Extract specific metrics for normalization
    val prices = hotelStats.map(_._4)
    val discounts = hotelStats.map(_._5)
    val margins = hotelStats.map(_._6)

    // Find min and max for normalization ranges
    val minPrice = prices.min
    val maxPrice = prices.max
    val minDiscount = discounts.min
    val maxDiscount = discounts.max
    val minMargin = margins.min
    val maxMargin = margins.max

    // Calculate normalized scores for each hotel
    val hotelScores = hotelStats.map {
      case (hotel, city, country, avgPrice, avgDiscount, avgMargin, count) =>

        // Normalize price: lower price = higher score
        val priceScore = if (maxPrice - minPrice > 0)
          (1 - ((avgPrice - minPrice) / (maxPrice - minPrice))) * 100
        else 50.0

        // Normalize discount: higher discount = higher score
        val discountScore = if (maxDiscount - minDiscount > 0)
          ((avgDiscount - minDiscount) / (maxDiscount - minDiscount)) * 100
        else 50.0

        // Normalize profit margin: lower margin = higher score
        val marginScore = if (maxMargin - minMargin > 0)
          (1 - ((avgMargin - minMargin) / (maxMargin - minMargin))) * 100
        else 50.0

        // Composite score: average of three normalized scores
        val compositeScore = (priceScore + discountScore + marginScore) / 3.0
        // 8-element tuple with all calculated metrics
        (hotel, city, country, avgPrice, avgDiscount, avgMargin, count, compositeScore)
    }

    // Find hotel location with the highest composite score
    val (bestHotel, bestCity, bestCountry, bestAvgPrice, bestAvgDiscount, bestAvgMargin, bestCount, bestCompositeScore) =
      hotelScores.maxBy(_._8)

    // Primary answer with detailed metrics
    println("\n 2. MOST ECONOMICAL HOTEL")
    println(s"   ► Hotel: $bestHotel")
    println(s"   ► City: $bestCity")
    println(s"   ► Country: $bestCountry")
    println(f"   ► Final Score: $bestCompositeScore%.2f")
    println(f"   ► Average Price: $$$bestAvgPrice%.2f")
    println(f"   ► Average Discount: $bestAvgDiscount%.1f%%")
    println(f"   ► Average Profit Margin: ${bestAvgMargin * 100}%.1f%%")
    println(s"   ► Transactions: $bestCount")

    // Show top 3 hotels in horizontal format
    println("\n" + "-" * 90)
    println("TOP 3 MOST ECONOMICAL HOTELS:")
    println("-" * 90)

    val top3Hotels = hotelScores.sortBy(-_._8).take(3)
    // Multi-column formatted output using printf
    // Creates professional table-like comparison

    // Headers with ranking
    println()
    println()
    printf("%-30s %-30s %-30s\n",
      s"1. ${top3Hotels(0)._1}",
      s"2. ${top3Hotels(1)._1}",
      s"3. ${top3Hotels(2)._1}")

    // Print city and country
    printf("%-30s %-30s %-30s\n",
      s"   ${top3Hotels(0)._2}, ${top3Hotels(0)._3}",
      s"   ${top3Hotels(1)._2}, ${top3Hotels(1)._3}",
      s"   ${top3Hotels(2)._2}, ${top3Hotels(2)._3}")

    // Location information
    printf("%-30s %-30s %-30s\n",
      f"   Score: ${top3Hotels(0)._8}%.2f",
      f"   Score: ${top3Hotels(1)._8}%.2f",
      f"   Score: ${top3Hotels(2)._8}%.2f")

    // Print Average Price
    printf("%-30s %-30s %-30s\n",
      f"   Price: $$${top3Hotels(0)._4}%.2f",
      f"   Price: $$${top3Hotels(1)._4}%.2f",
      f"   Price: $$${top3Hotels(2)._4}%.2f")

    // Print Average Discount
    printf("%-30s %-30s %-30s\n",
      f"   Discount: ${top3Hotels(0)._5}%.1f%%",
      f"   Discount: ${top3Hotels(1)._5}%.1f%%",
      f"   Discount: ${top3Hotels(2)._5}%.1f%%")

    // Print Average Profit Margin
    printf("%-30s %-30s %-30s\n",
      f"   Margin: ${top3Hotels(0)._6 * 100}%.1f%%",
      f"   Margin: ${top3Hotels(1)._6 * 100}%.1f%%",
      f"   Margin: ${top3Hotels(2)._6 * 100}%.1f%%")

    // Print Effective Price (price after discount)
    printf("%-30s %-30s %-30s\n",
      f"   Eff. Price: $$${top3Hotels(0)._4 * (1 - top3Hotels(0)._5/100)}%.2f",
      f"   Eff. Price: $$${top3Hotels(1)._4 * (1 - top3Hotels(1)._5/100)}%.2f",
      f"   Eff. Price: $$${top3Hotels(2)._4 * (1 - top3Hotels(2)._5/100)}%.2f")

    // Print Transactions
    printf("%-30s %-30s %-30s\n",
      s"   Transactions: ${top3Hotels(0)._7}",
      s"   Transactions: ${top3Hotels(1)._7}",
      s"   Transactions: ${top3Hotels(2)._7}")

    println("=" * 90)
  }
}