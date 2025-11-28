// Question1Analyzer.scala
object Question1Analyzer {

  def findCountryWithHighestBookings(bookings: List[HotelBooking]): (String, Int) = {
    val countryBookings = bookings.groupBy(_.country)
      .view
      .mapValues(_.size)
      .toMap

    countryBookings.maxBy(_._2)
  }

  def analyze(bookings: List[HotelBooking]): Unit = {
    val (country, count) = findCountryWithHighestBookings(bookings)

    println("=" * 60)
    println("QUESTION 1: Which country has the highest number of bookings?")
    println("=" * 60)
    println(s"Answer: $country has the highest number of bookings with $count bookings")

    // Display top 5 countries
    val topCountries = bookings.groupBy(_.country)
      .view
      .mapValues(_.size)
      .toList
      .sortBy(-_._2)
      .take(5)

    println("\nTop 5 countries by number of bookings:")
    topCountries.foreach { case (country, count) =>
      println(s"  - $country: $count bookings")
    }
    println()
  }
}