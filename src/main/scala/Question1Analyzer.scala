object Question1Analyzer {

  def analyze(bookings: List[HotelBooking]): Unit = {
    println("\n" + "─" * 70)
    println("QUESTION 1: Which country has the highest number of bookings?")
    println("─" * 70)

    if (bookings.isEmpty) {
      println("❌ No data available")
      return
    }
    
    val bookingsByCountry = bookings.groupBy(_.destinationCountry)
    
    val countryCounts = bookingsByCountry.mapValues(_.size)
    
    val (topCountry, count) = countryCounts.maxBy(_._2)

    println(s"$topCountry has the highest number of bookings")
    println(s"Total bookings to $topCountry: $count")
    println(s"Percentage of all bookings: ${(count.toDouble / bookings.size * 100).formatted("%.1f")}%")
    
    println("\nTOP 5 COUNTRIES BY BOOKINGS:")
    val top5 = countryCounts.toList.sortBy(-_._2).take(5)
    top5.zipWithIndex.foreach { case ((country, count), index) =>
      val percentage = (count.toDouble / bookings.size * 100).formatted("%.1f")
      println(s"   ${index + 1}. $country: $count bookings ($percentage%)")
    }
  }
}