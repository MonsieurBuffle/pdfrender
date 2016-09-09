package controllers

import javax.inject._
import play.api._
import play.api.mvc._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject() extends Controller {

  /**
   * Create an Action to render an HTML page with a welcome message.
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index = Action { implicit request =>

    val input = request.body

    val outputStream = new ByteArrayOutputStream
    val pdf = Pdf(new PdfConfig {
      orientation := Portrait
      pageSize := "Letter"
      marginTop := "0.5cm"
      marginBottom := "0.5cm"
      marginLeft := "0.5cm"
      marginRight := "0.5cm"
      printMediaType := Some(true)
    })

    Logger.info("build input stream")
    val content = new ByteArrayInputStream(input.getBytes("UTF-8"))
    Logger.info("build input stream done")

    Logger.info("running")
    pdf.run(content, outputStream)

    Ok(outputStream).as("application/pdf")
  }

}
