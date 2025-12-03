// Question1Analyzer.scala
object Question1Analyzer {

  def analyze(bookings: List[HotelBooking]): Unit = {
    println("\n" + "=" * 70)
    println("QUESTION 1: Which country has the highest number of bookings?")
    println("=" * 70)

    if (bookings.isEmpty) {
      println("❌ No data available")
      return
    }

    // Use groupBy to group bookings by origin country
    val bookingsByCountry = bookings.groupBy(_.originCountry)

    // Use mapValues to count bookings per country
    val countryCounts = bookingsByCountry.mapValues(_.size)

    // Use maxBy to find the country with maximum bookings
    val (topCountry, count) = countryCounts.maxBy(_._2)

    println(s"\n✅ ANSWER: $topCountry has the highest number of bookings")
    println(s"   Total bookings from $topCountry: $count")
    println(s"   Percentage of all bookings: ${(count.toDouble / bookings.size * 100).formatted("%.1f")}%")

    // Show top 5 countries using sortBy and take
    println("\n📊 TOP 5 COUNTRIES BY BOOKINGS:")
    val top5 = countryCounts.toList.sortBy(-_._2).take(5)
    top5.zipWithIndex.foreach { case ((country, count), index) =>
      val percentage = (count.toDouble / bookings.size * 100).formatted("%.1f")
      println(s"   ${index + 1}. $country: $count bookings ($percentage%)")
    }
  }
}