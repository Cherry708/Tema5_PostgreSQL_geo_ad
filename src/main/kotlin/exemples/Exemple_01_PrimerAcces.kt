package exemples

import org.hibernate.cfg.Configuration
import classes.Comarca

fun main(args: Array<String>) {
    val sf = Configuration().configure().buildSessionFactory()
    val sessio = sf.openSession()
    val comarca = sessio.load("classes.Comarca", "Alt Maestrat") as Comarca
    print("Comarca " + comarca.nomC + ": ")
    print(comarca.provincia)
    println(" (" + comarca.poblacions.size + " pobles)")
    sessio.close()
}

