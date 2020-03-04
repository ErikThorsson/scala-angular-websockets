package controllers

import javax.inject._

import play.api.mvc._

import akka.actor.ActorSystem
import akka.stream.Materializer
import com.google.inject.Singleton
import javax.inject.Inject
import play.api.libs.streams.ActorFlow
import play.api.mvc.{AbstractController, ControllerComponents, WebSocket}
import akka.stream.scaladsl._
import java.net.{URL, HttpURLConnection}
import play.api.libs.json.Json

import services.ChatServiceActor
import services.MyWebSocketActor


/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject() (cc: ControllerComponents)(implicit system: ActorSystem, mat: Materializer,
assetsFinder: AssetsFinder)
    extends AbstractController(cc) {
  
  def socket = WebSocket.accept[String, String] { request =>
      ActorFlow.actorRef { out =>
        MyWebSocketActor.props(out)
      }
  }
  
  /**
   * Create an Action to render an HTML page with a welcome message.
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def getTest = Action {
    println("hit get endpoint")
    // val url : String = "https://apidojo-yahoo-finance-v1.p.rapidapi.com/market/get-quotes?region=US&lang=en&symbols=AAPL"
    // Ok(Json.obj("content" -> get(url)))
    Ok(Json.obj("content" -> mockGet()))
  }

  @throws(classOf[java.io.IOException])
  @throws(classOf[java.net.SocketTimeoutException])
  def get(url: String,
        connectTimeout: Int = 5000,
        readTimeout: Int = 5000,
        requestMethod: String = "GET") =
  {
      val connection = (new URL(url)).openConnection.asInstanceOf[HttpURLConnection]
      connection.setConnectTimeout(connectTimeout)
      connection.setReadTimeout(readTimeout)
      connection.setRequestMethod(requestMethod)
      connection.setRequestProperty("x-rapidapi-host", "apidojo-yahoo-finance-v1.p.rapidapi.com")
      connection.setRequestProperty("x-rapidapi-key", "3dd172bf79msha520a5e5c44c454p1a848fjsn97cb404f2b7e")
      val inputStream = connection.getInputStream
      val content = scala.io.Source.fromInputStream(inputStream).mkString
      if (inputStream != null) inputStream.close
      content
  }

  def mockGet() : String = {
    return this.mockResponse
  }

  val mockResponse = """{
    "quoteResponse": {
      "result": [{
        "language": "en",
        "region": "US",
        "quoteType": "EQUITY",
        "triggerable": false,
        "quoteSourceName": "Nasdaq Real Time Price",
        "currency": "USD",
        "firstTradeDateMilliseconds": 345459600000,
        "priceHint": 2,
        "targetPriceMean": 332.91,
        "postMarketChangePercent": 0.33871853,
        "postMarketTime": 1583282203,
        "postMarketPrice": 290.3,
        "postMarketChange": 0.97998047,
        "regularMarketChange": -9.48999,
        "regularMarketChangePercent": -3.175928,
        "regularMarketTime": 1583269201,
        "regularMarketPrice": 289.32,
        "regularMarketDayHigh": 304.0,
        "regularMarketDayRange": "285.8 - 304.0",
        "regularMarketDayLow": 285.8,
        "regularMarketVolume": 78404318,
        "regularMarketPreviousClose": 298.81,
        "bid": 288.12,
        "ask": 287.0,
        "bidSize": 8,
        "askSize": 10,
        "fullExchangeName": "NasdaqGS",
        "regularMarketOpen": 303.67,
        "averageDailyVolume3Month": 35201518,
        "beta": 1.277385,
        "fiftyTwoWeekLowChange": 119.82001,
        "fiftyTwoWeekLowChangePercent": 0.7069027,
        "fiftyTwoWeekRange": "169.5 - 327.85",
        "fiftyTwoWeekHighChange": -38.53,
        "fiftyTwoWeekHighChangePercent": -0.11752325,
        "fiftyTwoWeekLow": 169.5,
        "fiftyTwoWeekHigh": 327.85,
        "earningsTimestamp": 1580247000,
        "earningsTimestampStart": 1588085940,
        "earningsTimestampEnd": 1588608000,
        "trailingPE": 22.97102,
        "dividendsPerShare": 3.04,
        "dividendRate": 3.08,
        "dividendYield": 1.03,
        "marketState": "POST",
        "epsTrailingTwelveMonths": 12.595,
        "sharesOutstanding": 4375479808,
        "marketCap": 1265913823232,
        "sourceInterval": 15,
        "exchangeDataDelayedBy": 0,
        "pageViews": {
          "midTermTrend": "UP",
          "longTermTrend": "UP",
          "shortTermTrend": "UP"
        },
        "tradeable": true,
        "exchange": "NMS",
        "shortName": "Apple Inc.",
        "longName": "Apple Inc.",
        "messageBoardId": "finmb_24937",
        "exchangeTimezoneName": "America/New_York",
        "exchangeTimezoneShortName": "EST",
        "gmtOffSetMilliseconds": -18000000,
        "market": "us_market",
        "esgPopulated": false,
        "components": ["^NQDMXJPLMAUD", "^NQDOG", "^NQUSBLM", "^NQSHYL", "^QIV", "^NQDXUSMLTCGN", "^NQUS500LCGN", "^NQUSB9572LMCADN", "^NQGMOIN", "^NQSXYN", "^NQGS", "^NQNALMEURN", "^NQUSB9000LMJPYN", "^NQUSB9572LMN", "^NQUSBLMCAD", "^NQUSB9000LMEURN", "^NQDMXKRJPYN", "^NQDMXKRLMAUDN", "^NQNA9000LMGBPN", "^NQUSB9570LMJPY", "^NQDMXJPLMAUDN", "^NQDMXKRLMAUD", "^NQUSBLMJPY", "^NDXCHF", "^NQNA9000LMEUR", "^AIX", "^NQNA9000LMCAD", "^NQGXGBLMN", "^NQDMXKRLCJPY", "^NQUSB9000LMEUR", "^NQDXUSLCEUR", "^NQUSB9572LMAUDN", "^NQDXUSLCG", "^CAPEXA", "^CPQ", "^CPQNTR", "^NQUSB9000LMCAD", "^NQDM9000LMJPY", "^NQG9000LMCADN", "^NQDMXKRAUD", "^NQDMXKRLMGBPN", "^NQGXGBLMGBPN", "^NQDM9000LMEURN", "^NQGXJPLMJPYN", "^NQNALMCADN", "^NQUSBLMEUR", "^NQG9000LMGBP", "^NQDM9000LMJPYN", "^NQNALMJPY", "^DWANQTL", "^MSH", "^NDX", "^NQGXGBLMJPY", "^QCRD", "^NQUSB9570LMGBPN", "^IXCO", "^NQGXJPLMJPY", "^NQDMXKR", "^NQDMXGBLMCADN", "^NQUSB9000LMJPY", "^NQDMXKRLCN", "^NQGXGBLMEURN", "^NQGXJPLMCAD", "^NQGXJPLMEUR", "^XNDXNNRCHF", "^DWARJFMU", "^NQUSB9572LMEUR", "^NQDMXJPLMGBPN", "^NQDMXJPLMN", "^NQUSB9572LMCAD", "^NQSXY", "^NQDM9000LMAUD", "^NQDMXKRLCJPYN", "^NQDMXKRLMCADN", "^NQDMXKRLCEUR", "^NQUSB9000LMN", "^NQDXUSLC", "^NQUSB9570LMAUDN", "^NQGXGBLMJPYN", "^NQUSB9572LMEURN", "^NQG9000LMJPYN", "^NQUSBLMEURN", "^NQDMXKRJPY", "^NQDMXKRLCCAD", "^NQUSB9570LM", "^NQDMXKRLCEURN", "^NQUSMLTCG", "^NQG9000LMAUD", "^NQGXJPLMGBPN", "^QMI", "^NQUSB9000LMCADN", "^IXIC", "^NQNALM", "^NQUS500LC", "^NQDMXGBLMJPY", "^NQNALMAUD", "^NQNA9000LMN", "^NQVMVUS", "^NQDXUSLCN", "^NQGXGBLMAUD", "^NQUSBLMN", "^NQDMXJPLMGBP", "^NQFGSL", "^NQUSB9570LMCADN", "^NQDMXKRLMEUR", "^MNX1", "^NQNALMAUDN", "^GMB", "^NQG9000LMEURN", "^CAPEXAN", "^NQDM9000LMCADN", "^NQDMXKRLMCAD", "^NQDMXGBLM", "^NQUSB9570LMAUD", "^NQSHYLN", "^NQNA9000LMGBP", "^NQDMXGBLMJPYN", "^NQDOGN", "^NQG9000LMN", "^NQDMXKRLMJPY", "^NQUSBLMJPYN", "^NQDM9000LMN", "^NQDMXGBLMCAD", "^NQUSB9572LMJPY", "^XNDXNNRGBP", "^NQUSLG", "^NQDMXGBLMEURN", "^NQDMXKRGBPN", "^NQBUY", "^NQDMXGBLMEUR", "^XNDXNNR", "^NQDMXKRAUDN", "^NQDMXKRLMJPYN", "^NQUSBLMCADN", "^NQDMXGBLMN", "^NQNALMGBPN", "^NQGXGBLMCADN", "^NQDMXKRLCCADN", "^NQGXJPLMCADN", "^NQUSB9572LM", "^NQGXJPLM", "^NQNA9000LMAUD", "^NQUSB9570LMGBP", "^NQUSBLMGBP", "^NQNALMN", "^NQUSB9000LMAUD", "^NQUSLGN", "^NQNA9000LM", "^NQUSB9570LMEURN", "^QFON", "^NQGXJPLMAUDN", "^NQCAPST", "^NQ96DIVUS", "^DJI", "^NQDMXKRLCGBP", "^NQDMXKRLM", "^NQDM9000LMGBP", "^NQUSMLTCGN", "^NQDMXKRLC", "^NQNALMGBP", "^NDXEUR", "^NDXHKD", "^NQDMXJPLMJPYN", "^NQG9000LMEUR", "^NQGXGBLM", "^NQG9000LMCAD", "^NQGMOI", "^NQDMXKRN", "^NQUSB9570LMN", "^XQC", "^NQCRDN", "^NQUSB9572LMJPYN", "^NQDMXJPLMEURN", "^NQG9000LMJPY", "^NDXCAD", "^NQDXUSMLTCG", "^NQNA9000LMJPYN", "^NQFFUSMN", "^NQDMXJPLM", "^NQDMXKREUR", "^NQGXJPLMGBP", "^NQDM9000LMAUDN", "^NQDMXKRLMEURN", "^NQUSBLMAUD", "^NQGXGBLMGBP", "^NQDMXKRCAD", "^NQNA9000LMEURN", "^XQO", "^DJA", "^NQUSB9000LMAUDN", "^NQUSB9000LMGBP", "^NQDXUSLCEURN", "^GMBNTR", "^NQNA9000LMAUDN", "^NQG9000LMAUDN", "^NQDMXKRLCAUD", "^XCQ", "^NQDXUSMEGAN", "^NQNALMCAD", "^AVSPY", "^XCI", "^NQGXJPLMEURN", "^NQDM9000LM", "^NQDXUSLCGBP", "^NQGXJPLMN", "^NQNALMEUR", "^NDXGBP", "^NQGXGBLMEUR", "^NQDMXKRGBP", "^NQDM9000LMEUR", "^XNDXNNRHKD", "^NQFFUSM", "^NQUSB9572LMGBPN", "^NQDM9000LMCAD", "^NQDMXGBLMGBPN", "^XNDXNNREUR", "^NQCRD", "^NQDMXKRCADN", "^NQGXGBLMCAD", "^NQDMXGBLMGBP", "^NQDMXKRLCAUDN", "^XNDXNNRCAD", "^NQDM9000LMGBPN", "^NQDXUSLCGBPN", "^NQUSB9570LMJPYN", "^NQG9000LM", "^NQUSB9572LMAUD", "^NQUSBLMGBPN", "^NQDMXKRLMN", "^NQFGSLN", "^NQGXJPLMAUD", "^NQDMXGBLMAUDN", "^NDXT", "^NQUSB9000LM", "^NQDMXJPLMJPY", "^NQDMXKRLCGBPN", "^NQDXUSLCGN", "^NQVMVUSN", "^NQUS500LCG", "^NDXE", "^NQDMXKREURN", "^NQNALMJPYN", "^NQUSB9000LMGBPN", "^NQG9000LMGBPN", "^NQDMXJPLMCADN", "^NQDVRIS", "^NQUSB9570LMCAD", "^NQUSB9570LMEUR", "^NQDMXGBLMAUD", "^NQUSBLMAUDN", "^NQNA9000LMJPY", "^DWUSSR", "^NQDMXJPLMCAD", "^NQDMXJPLMEUR", "^NQDXUSMEGA", "^NQDMXKRLMGBP", "^NQGXGBLMAUDN", "^NQUS500LCN", "^NQUSB9572LMGBP", "^NQNA9000LMCADN"],
        "symbol": "AAPL"
      }],
      "error": null
    }
  }"""

}
