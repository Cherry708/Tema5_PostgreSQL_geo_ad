package exemples

import classes.Comarca
import org.hibernate.cfg.Configuration
import java.util.logging.Level
import java.util.logging.LogManager

fun main(args: Array<String>) {
    LogManager.getLogManager().getLogger("").setLevel(Level.SEVERE)
    val sessio = Configuration().configure().buildSessionFactory().openSession()
    val com = sessio.load("classes.Comarca", "Alcalatén") as Comarca
    print("La comarca " + com.nomC)
    print(" (província de " + com.provincia + ") ")
    println("té " + com.poblacions.size + " pobles")
    println()
    println("Llista de pobles")
    println("-----------------")

    val it = com.poblacions.iterator()!!

    while (it.hasNext()) {
        val p = it.next()
        System.out.println(p.nom + " (" + p.poblacio + " habitants)")
    }

    sessio.close()
}