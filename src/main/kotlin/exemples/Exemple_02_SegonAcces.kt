package exemples

import classes.Comarca
import org.hibernate.cfg.Configuration
import java.util.logging.Level
import java.util.logging.LogManager

fun main(args: Array<String>) {
    LogManager.getLogManager().getLogger("").setLevel(Level.SEVERE)
    val sessio = Configuration().configure().buildSessionFactory().openSession()

    val comarca = sessio.load("classes.Comarca", "Alt Maestrat") as Comarca
    print("Comarca " + comarca.nomC + ": ")
    print(comarca.provincia)
    println(" (" + comarca.poblacions.size + " pobles)")

    /*
    Mostramos una lista ordenada a nivel de consulta
    especificando order-by en res.Comarca.hbm.xml, linea 10
     */
    for (p in comarca.poblacions)
        println("\t" + p.nom)
    sessio.close()
}

