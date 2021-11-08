package exemples

import org.hibernate.cfg.Configuration
import java.util.logging.Level
import java.util.logging.LogManager

//Estamos eliminando una fila a una bbdd existente y comun, si ya no existe puede dar error

fun main(args: Array<String>) {
    LogManager.getLogManager().getLogger("").setLevel(Level.SEVERE)
    val sessio = Configuration().configure().buildSessionFactory().openSession()
    val t = sessio.beginTransaction()
    val com = sessio.load("classes.Comarca", "Columbretes")

    sessio.delete(com)

    t.commit()
    sessio.close()
}