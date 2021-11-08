package exemples

import classes.Comarca
import org.hibernate.cfg.Configuration
import java.util.logging.Level
import java.util.logging.LogManager

fun main(args: Array<String>) {
    LogManager.getLogManager().getLogger("").setLevel(Level.SEVERE)
    val sessio = Configuration().configure().buildSessionFactory().openSession()
    val q = sessio.createQuery ("from Comarca order by provincia, nomC")

    for (c in q.list()) {
        c as Comarca
        println(c.nomC + " --- " + c.provincia)
    }

    sessio.close()
}

