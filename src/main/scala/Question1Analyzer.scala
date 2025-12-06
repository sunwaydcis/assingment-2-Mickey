object Question1Analyzer {
  /**
   * Demonstrates sophisticated collection processing pipeline.
   * Each transformation step showcases mastery of different collection operations.
   */

  def analyze(bookings: List[HotelBooking]): Unit = {
    println("\n" + "─" * 70)
    println("QUESTION 1: Which country has the highest number of bookings?")
    println("─" * 70)

    // Early validation prevents runtime errors on empty collections
    if (bookings.isEmpty) {
      println("❌ No data available")
      return // Early return pattern
    }

    // groupBy creates efficient Map[String, List[HotelBooking]]
    // Partitions all bookings by destinationCountry field
    val bookingsByCountry = bookings.groupBy(_.destinationCountry)

    // mapValues transforms grouped collections efficiently
    // Converts List[HotelBooking] to count (Int) for each country
    val countryCounts = bookingsByCountry.mapValues(_.size)

    // maxBy finds entry with maximum value, destructures tuple
    // Uses pattern: maxBy(_._2) where _._2 accesses the count value
    val (topCountry, count) = countryCounts.maxBy(_._2)
    // Present primary result with contextual information
    println(s"$topCountry has the highest number of bookings")
    println(s"Total bookings to $topCountry: $count")
    println(s"Percentage of all bookings: ${(count.toDouble / bookings.size * 100).formatted("%.1f")}%")

    // Show top 5 countries using sortBy and take
    println("\nTOP 5 COUNTRIES BY BOOKINGS:")
    val top5 = countryCounts.toList.sortBy(-_._2).take(5)
    top5.zipWithIndex.foreach { case ((country, count), index) =>
      val percentage = (count.toDouble / bookings.size * 100).formatted("%.1f")
      println(s"   ${index + 1}. $country: $count bookings ($percentage%)")
    }
  }
}