package exemples

import classes.Poblacio
import classes.Comarca
import org.hibernate.cfg.Configuration
import java.util.logging.Level
import java.util.logging.LogManager

fun main(args: Array<String>) {
    LogManager.getLogManager().getLogger("").setLevel(Level.SEVERE)
    val sessio = Configuration().configure().buildSessionFactory().openSession()

    val q = sessio.createQuery("from Poblacio p, Comarca c where p.comarca.nomC=c.nomC order by p.nom")

    for(tot in q.list()) {
        tot as Array<Object>
        val p = tot[0] as Poblacio
        val c = tot[1] as Comarca
        println(p.nom + " (" + c.nomC + ". " + c.provincia + ")")
    }

    sessio.close()
}