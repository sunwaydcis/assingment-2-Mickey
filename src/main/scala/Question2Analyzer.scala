// Question2Analyzer.scala
object Question2Analyzer {

  case class HotelEconomics(
                             hotelName: String,
                             avgPrice: Double,
                             avgDiscount: Double,
                             avgProfitMargin: Double
                           )

  def analyzeHotelEconomics(bookings: List[HotelBooking]): List[HotelEconomics] = {
    bookings.groupBy(_.hotelName)
      .view
      .mapValues { hotelBookings =>
        val avgPrice = hotelBookings.map(_.bookingPrice).sum / hotelBookings.size
        val avgDiscount = hotelBookings.map(_.discount).sum / hotelBookings.size
        val avgProfitMargin = hotelBookings.map(_.profitMargin).sum / hotelBookings.size
        HotelEconomics(hotelBookings.head.hotelName, avgPrice, avgDiscount, avgProfitMargin)
      }
      .values
      .toList
  }

  def analyze(bookings: List[HotelBooking]): Unit = {
    val hotelEconomics = analyzeHotelEconomics(bookings)

    println("=" * 60)
    println("QUESTION 2: Which hotel offers the most economical option?")
    println("=" * 60)

    // 2a. Most economical by booking price
    val cheapestHotel = hotelEconomics.minBy(_.avgPrice)
    println(s"2a. Most economical by booking price: ${cheapestHotel.hotelName}")
    println(s"   Average Price: $$${cheapestHotel.avgPrice.formatted("%.2f")}")

    // 2b. Most economical by discount
    val highestDiscountHotel = hotelEconomics.maxBy(_.avgDiscount)
    println(s"\n2b. Most economical by discount: ${highestDiscountHotel.hotelName}")
    println(s"   Average Discount: ${highestDiscountHotel.avgDiscount.formatted("%.1f")}%")

    // 2c. Most economical by profit margin
    val lowestMarginHotel = hotelEconomics.minBy(_.avgProfitMargin)
    println(s"\n2c. Most economical by profit margin: ${lowestMarginHotel.hotelName}")
    println(s"   Average Profit Margin: ${lowestMarginHotel.avgProfitMargin.formatted("%.1f")}%")

    println("\nDetailed hotel economics:")
    hotelEconomics.sortBy(_.avgPrice).foreach { economics =>
      println(s"  - ${economics.hotelName}: Price=$$${economics.avgPrice.formatted("%.2f")}, " +
        s"Discount=${economics.avgDiscount.formatted("%.1f")}%, " +
        s"Margin=${economics.avgProfitMargin.formatted("%.1f")}%")
    }
    println()
  }
}