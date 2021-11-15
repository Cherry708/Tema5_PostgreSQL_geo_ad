package exemples

import org.hibernate.cfg.Configuration
import java.util.logging.Level
import java.util.logging.LogManager

fun main(args: Array<String>) {
    LogManager.getLogManager().getLogger("").setLevel(Level.SEVERE)
    val sessio = Configuration().configure().buildSessionFactory().openSession()

    val q = sessio.createQuery("select c.nomC,count(p.codM),avg(p.altura) "
            + "from Comarca c , Poblacio p "
            + "where c.nomC=p.comarca.nomC "
            + "group by c.nomC "
            + "order by c.nomC");
    for (tot in q.list()){
        tot as Array<Object>
        println("Comarca: " + tot[0] + ". Núm. pobles: " + tot[1] + ". Altura mitjana: " + tot[2]);
    }
    sessio.close()
}