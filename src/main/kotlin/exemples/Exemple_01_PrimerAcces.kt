package exemples

import org.hibernate.cfg.Configuration
import classes.Comarca

fun main(args: Array<String>) {
    val sf = Configuration().configure().buildSessionFactory()
    val sessio = sf.openSession()
    val com = sessio.load("classes.Comarca", "Alt Maestrat") as Comarca
    print("Comarca " + com.nomC + ": ")
    print(com.provincia)
    println(" (" + com.poblacions.size + " pobles)")
    sessio.close()
}

