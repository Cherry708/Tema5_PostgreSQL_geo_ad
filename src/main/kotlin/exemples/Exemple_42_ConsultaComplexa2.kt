package exemples

import org.hibernate.cfg.Configuration
import java.util.logging.Level
import java.util.logging.LogManager

fun main(args: Array<String>) {
    LogManager.getLogManager().getLogger("").setLevel(Level.SEVERE)
    val sessio = Configuration().configure().buildSessionFactory().openSession()

    val q = sessio.createQuery("select avg(altura) from Poblacio")

    val mitjana = q.uniqueResult ()

    println("Altura mitjana: " + mitjana)

    sessio.close()
}