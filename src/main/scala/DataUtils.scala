// DataUtils.scala

import scala.io.Source
import scala.util.{Try, Using}

object DataUtils {

  def loadHotelData(filePath: String): List[HotelBooking] = {
    Try {
      Using(Source.fromFile(filePath)) { source =>
        source.getLines().drop(1) // Skip header
          .map(_.split(",").map(_.trim))
          .filter(_.length >= 6)
          .map { fields =>
            HotelBooking(
              hotelName = fields(0),
              country = fields(1),
              bookingPrice = fields(2).toDouble,
              discount = fields(3).toDouble,
              profitMargin = fields(4).toDouble,
              visitorCount = fields(5).toInt
            )
          }.toList
      }.get
    }.getOrElse {
      println("Error loading dataset. Using sample data instead.")
      getSampleData()
    }
  }

  private def getSampleData(): List[HotelBooking] = {
    List(
      HotelBooking("Grand Plaza", "USA", 250.0, 15.0, 20.0, 150),
      HotelBooking("Grand Plaza", "UK", 200.0, 10.0, 18.0, 100),
      HotelBooking("Seaside Resort", "USA", 300.0, 25.0, 25.0, 200),
      HotelBooking("Seaside Resort", "France", 280.0, 20.0, 22.0, 120),
      HotelBooking("Mountain Lodge", "Germany", 180.0, 5.0, 15.0, 80),
      HotelBooking("Mountain Lodge", "USA", 220.0, 12.0, 17.0, 90),
      HotelBooking("City Central", "UK", 190.0, 8.0, 16.0, 110),
      HotelBooking("City Central", "France", 210.0, 10.0, 18.0, 95)
    )
  }
}