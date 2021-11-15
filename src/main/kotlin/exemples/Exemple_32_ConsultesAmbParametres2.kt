package exemples

import classes.Poblacio
import org.hibernate.cfg.Configuration
import java.util.logging.Level
import java.util.logging.LogManager

fun main(args: Array<String>) {
    LogManager.getLogManager().getLogger("").setLevel(Level.SEVERE)
    val sessio = Configuration().configure().buildSessionFactory().openSession()

    val q = sessio.createQuery("from Poblacio where altura>=:alt and comarca.nomC=:com")
    q.setParameter("alt",500)
    q.setParameter("com","Alcalatén")
    for (p in q.list()) {
        p as Poblacio
        println(p.nom + " - " + p.altura)
    }

    sessio.close()
}