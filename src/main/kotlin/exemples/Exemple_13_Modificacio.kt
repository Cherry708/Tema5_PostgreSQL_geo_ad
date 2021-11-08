package exemples

import classes.Comarca
import org.hibernate.cfg.Configuration
import java.util.logging.Level
import java.util.logging.LogManager

fun main(args: Array<String>) {
    LogManager.getLogManager().getLogger("").setLevel(Level.SEVERE)
    val sessio = Configuration().configure().buildSessionFactory().openSession()
    val t = sessio.beginTransaction()
    val com = sessio.load("classes.Comarca", "Camp de Morvedre") as Comarca
    com.provincia = "Castelló"

    sessio.update(com)

    t.rollback()

    sessio.close()
}