package exemples

import classes.Comarca
import org.hibernate.cfg.Configuration
import java.util.logging.Level
import java.util.logging.LogManager

//Estamos añadiendo una fila a una bbdd existente y comun, si ya existe puede dar error

fun main(args: Array<String>) {
    LogManager.getLogManager().getLogger("").setLevel(Level.SEVERE)
    val sessio = Configuration().configure().buildSessionFactory().openSession()
    val t = sessio.beginTransaction ()
    val com = Comarca()

    com.nomC = "Columbretes"
    com.provincia = "Castelló"

    sessio.save(com)

    t.commit()
    sessio.close()
}