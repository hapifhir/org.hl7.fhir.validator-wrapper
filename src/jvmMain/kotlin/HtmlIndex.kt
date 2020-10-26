import kotlinx.html.*

/**
 * We use kotlinx to create this HTML index file which contains the reference to `/static/output.js`, the location
 * of our generated JS code from KotlinJS. This is also where we load in our external fonts and set the main 'root' div.
 */
fun HTML.index() {
    head {
        meta {
            charset = "UTF-8"
        }
        title("Validator GUI")
        link(
            href = "https://fonts.googleapis.com/css2?family=Montserrat:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap",
            rel = "stylesheet"
        )
        link(
            href = "https://fonts.googleapis.com/css2?family=Source+Code+Pro:ital,wght@0,200;0,300;0,400;0,500;0,600;0,700;0,900;1,200;1,300;1,400;1,500;1,600;1,700;1,900&display=swap",
            rel = "stylesheet"
        )
    }
    body {
        div {
            id = "root"
        }
        script(src = "/static/output.js") {}
    }
}