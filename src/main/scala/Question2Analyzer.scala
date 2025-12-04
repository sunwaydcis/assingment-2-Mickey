object Question2Analyzer {

  def analyze(bookings: List[HotelBooking]): Unit = {
    println("\n" + "─" * 90)
    println("QUESTION 2: Which hotel offers the most economical option?")
    println("─" * 90)

    if (bookings.isEmpty) {
      println("❌ No data available")
      return
    }

    val hotelStats = bookings
      .groupBy(_.hotelName)
      .map { case (hotelName, hotelBookings) =>
        val bookingCount = hotelBookings.size
        val avgPrice = hotelBookings.map(_.bookingPrice).sum / bookingCount
        val avgDiscount = hotelBookings.map(_.discountValue).sum / bookingCount
        val avgMargin = hotelBookings.map(_.profitMargin).sum / bookingCount

        val effectivePrice = avgPrice * (1 - avgDiscount/100)

        val economyScore = effectivePrice * (1 + avgMargin/100)

        (hotelName, avgPrice, avgDiscount, avgMargin, effectivePrice, economyScore, bookingCount)
      }
      .filter(_._7 >= 2)
      .toList

    if (hotelStats.isEmpty) {
      println("❌ No hotels with sufficient booking data")
      return
    }


    val mostEconomical = hotelStats.minBy(_._6)

    println(s"${mostEconomical._1} is the most economical hotel")
    println(f"Economy Score: ${mostEconomical._6}%.2f (lower is better)")
    println(f"Average Price: $$${mostEconomical._2}%.2f")
    println(f"Average Discount: ${mostEconomical._3}%.1f%%")
    println(f"Average Profit Margin: ${mostEconomical._4}%.1f%%")
    println(f"Effective Price: $$${mostEconomical._5}%.2f")
    println(s"Based on ${mostEconomical._7} bookings")

    println("\n" + "-" * 90)
    println("TOP 3 MOST ECONOMICAL HOTELS:")
    println("-" * 90)

    val top3Economical = hotelStats.sortBy(_._6).take(3)

    printf("%-30s %-30s %-30s\n",
      s"1. ${top3Economical(0)._1}",
      s"2. ${top3Economical(1)._1}",
      s"3. ${top3Economical(2)._1}")


    printf("%-30s %-30s %-30s\n",
      f"   Score: ${top3Economical(0)._6}%.2f",
      f"   Score: ${top3Economical(1)._6}%.2f",
      f"   Score: ${top3Economical(2)._6}%.2f")

    printf("%-30s %-30s %-30s\n",
      f"   Price: $$${top3Economical(0)._2}%.2f",
      f"   Price: $$${top3Economical(1)._2}%.2f",
      f"   Price: $$${top3Economical(2)._2}%.2f")

    printf("%-30s %-30s %-30s\n",
      f"   Discount: ${top3Economical(0)._3}%.1f%%",
      f"   Discount: ${top3Economical(1)._3}%.1f%%",
      f"   Discount: ${top3Economical(2)._3}%.1f%%")

    printf("%-30s %-30s %-30s\n",
      f"   Margin: ${top3Economical(0)._4}%.1f%%",
      f"   Margin: ${top3Economical(1)._4}%.1f%%",
      f"   Margin: ${top3Economical(2)._4}%.1f%%")

    printf("%-30s %-30s %-30s\n",
      f"   Eff. Price: $$${top3Economical(0)._5}%.2f",
      f"   Eff. Price: $$${top3Economical(1)._5}%.2f",
      f"   Eff. Price: $$${top3Economical(2)._5}%.2f")

    printf("%-30s %-30s %-30s\n",
      s"   Bookings: ${top3Economical(0)._7}",
      s"   Bookings: ${top3Economical(1)._7}",
      s"   Bookings: ${top3Economical(2)._7}")

    println("=" * 90)
  }
}