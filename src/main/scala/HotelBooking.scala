// HotelBooking.scala
// Right-click → New → Scala Class → Kind: Case Class → Name: HotelBooking

case class HotelBooking(
                         hotelName: String,
                         country: String,
                         bookingPrice: Double,
                         discount: Double,
                         profitMargin: Double,
                         visitorCount: Int
                       ) extends Analyzable {

  override def displayInfo(): String = {
    s"Hotel: $hotelName, Country: $country, Price: $$$bookingPrice, " +
      s"Discount: $discount%, Profit Margin: $profitMargin%, Visitors: $visitorCount"
  }

  def finalPrice: Double = bookingPrice * (1 - discount/100)
  def totalProfit: Double = finalPrice * (profitMargin/100)
}