package exercicis

import classes.Comarca
import org.hibernate.ObjectNotFoundException
import org.hibernate.cfg.Configuration
import java.awt.EventQueue
import java.awt.BorderLayout
import java.awt.FlowLayout
import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.JButton
import javax.swing.JTextArea
import javax.swing.JLabel
import javax.swing.JTextField
import java.awt.Color
import java.util.logging.Level
import java.util.logging.LogManager
import javax.swing.JScrollPane

class Finestra : JFrame() {
    val etiqueta = JLabel("Comarca:")
    val etIni = JLabel("Introdueix la comarca:")
    val com = JTextField(15)
    val consultar = JButton("Consultar")
    val area = JTextArea()

    init {
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        setTitle("HIBERNATE: Visualitzar Comarques i Pobles")
        setBounds(100, 100, 450, 300)
        setLayout(BorderLayout())

        val panell1 = JPanel(FlowLayout())
        panell1.add(etIni)
        panell1.add(com)
        panell1.add(consultar)
        getContentPane().add(panell1, BorderLayout.NORTH)

        val panell2 = JPanel(BorderLayout())
        panell2.add(etiqueta, BorderLayout.NORTH)
        area.setForeground(Color.blue)
        val scroll = JScrollPane(area)
        panell2.add(scroll, BorderLayout.CENTER)
        getContentPane().add(panell2, BorderLayout.CENTER)

        consultar.addActionListener() {
            etiqueta.setText("Comarca: " + com.getText())
            visualitzaCom(com.getText())
        }
    }

    fun visualitzaCom(comarca: String) {
        area.text = ""

        LogManager.getLogManager().getLogger("").setLevel(Level.SEVERE)
        /*Como solo queremos cargar un objeto usamos load o get,
        si dicho objeto no da resultado en el mapeado de la base de datos
        load lanza una excepcion. Load conlleva la caputara de excepciones,
        con get solo deberemos comprobar casos.*/
        val sessio = Configuration().configure().buildSessionFactory().openSession()
        /*
        try {
            val comarcaSelec = sessio.load("classes.Comarca", comarca) as Comarca

            val poblacions = comarcaSelec.poblacions
            for (poble in poblacions.sortedBy { it.nom })
                area.text += "${poble.nom}\n"

        } catch (e: ObjectNotFoundException) {
            area.text = "La comarca no existe."
        }
        */

        //Debemos usar '?' para especificar que puede ser null
        val comarcaSelec = sessio.get("classes.Comarca", comarca) as Comarca?

        if (comarcaSelec == null){
            area.text = "La comarca '$comarca' no existe."
        } else {
            val poblacions = comarcaSelec.poblacions
            for (poble in poblacions.sortedBy { it.nom })
                area.append("${poble.nom}\n")
        }


        sessio.close()
        // Instruccions per a llegir la comarca que arriba com a paràmetre (s'ha de deixar en un objecte Comarca).
        // S'ha de cuidar que si no exiteix la comarca, en el JTextArea es pose que no existeix.
        // La manera d'anar introduint informació en el JTextArea és area.append("Linia que es vol introduir ")
    }
}

fun main() {
    EventQueue.invokeLater {
        Finestra().isVisible = true
    }
}

