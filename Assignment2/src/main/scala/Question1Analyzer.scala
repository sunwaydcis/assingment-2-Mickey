// Question1Analyzer.scala
object Question1Analyzer {

  /**
   * EXCELLENT: Demonstrates sophisticated collection processing pipeline.
   * Each step shows mastery of different collection operations.
   */
  def analyze(bookings: List[HotelBooking]): Unit = {
    println("\n" + "=" * 70)
    println("QUESTION 1: Which country has the highest number of bookings?")
    println("=" * 70)

    // Safety check - prevents runtime errors
    if (bookings.isEmpty) {
      println("❌ No data available")
      return
    }

    // EXCELLENT: groupBy creates Map[String, List[HotelBooking]]
    // Key insight: Groups all bookings by destination country
    val bookingsByCountry = bookings.groupBy(_.originCountry)

    // EXCELLENT: mapValues efficiently transforms without creating intermediate keys
    // Converts List[HotelBooking] to count (Int) for each country
    val countryCounts = bookingsByCountry.mapValues(_.size)

    // EXCELLENT: maxBy with tuple pattern matching
    // Finds entry with maximum count, destructures into (topCountry, count)
    val (topCountry, count) = countryCounts.maxBy(_._2)

    // Present primary result with context
    println(s"\n✅ ANSWER: $topCountry has the highest number of bookings")
    println(s"   Total bookings from $topCountry: $count")
    // EXCELLENT: Percentage calculation with formatting
    // Shows understanding of data normalization
    println(s"   Percentage of all bookings: ${(count.toDouble / bookings.size * 100).formatted("%.1f")}%")

    // EXTENDED ANALYSIS: Top 5 countries
    println("\n📊 TOP 5 COUNTRIES BY BOOKINGS:")
    // EXCELLENT: Complex collection transformation chain:
    // 1. toList - Convert Map to List of tuples
    // 2. sortBy(-_._2) - Sort descending by count (negative for descending)
    // 3. take(5) - Get top 5 elements
    // 4. zipWithIndex - Add ranking indices
    // 5. foreach - Iterate with pattern matching
    val top5 = countryCounts.toList.sortBy(-_._2).take(5)
    top5.zipWithIndex.foreach { case ((country, count), index) =>
      // EXCELLENT: Recalculation of percentage for each item
      val percentage = (count.toDouble / bookings.size * 100).formatted("%.1f")
      println(s"   ${index + 1}. $country: $count bookings ($percentage%)")
    }
  }
}