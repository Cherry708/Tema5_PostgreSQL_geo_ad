package exercicis


import java.awt.EventQueue
import java.awt.BorderLayout
import java.awt.FlowLayout
import java.awt.Color
import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.JLabel
import javax.swing.JTextField
import javax.swing.JScrollPane
import javax.swing.JComboBox
import javax.swing.DefaultListModel
import javax.swing.JList
import classes.Comarca
import classes.Poblacio
import org.hibernate.cfg.Configuration
import java.util.logging.Level
import java.util.logging.LogManager

class FinestraComboBox : JFrame() {
    val etiqueta = JLabel("Comarca:")
    val etIni = JLabel("Introdueix la comarca:")
    val cbCombo = JComboBox<String>()
    val listModel = DefaultListModel<String>()
    val area = JList(listModel)
    val peu = JTextField()

    /*No funciona suprimir advertencias aqui,
     la solucion seria realizar una conexion a cada funcion
     pero provoca que la ejecucion sea mucho mas lenta.
     Por otro lado, los warnings no suponen ningun problema,
     por lo tanto, se ignoran.
    */
    val sessio = Configuration().configure().buildSessionFactory().openSession()

    init {

        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        setTitle("HIBERNATE: Visualitzar Comarques i Pobles amb ComboBox")
        setBounds(100, 100, 550, 400)
        setLayout(BorderLayout())

        val panell1 = JPanel(FlowLayout())
        panell1.add(etIni)
        panell1.add(cbCombo)
        getContentPane().add(panell1, BorderLayout.NORTH)

        val panell2 = JPanel(BorderLayout())
        panell2.add(etiqueta, BorderLayout.NORTH)
        area.setForeground(Color.blue)
        val scroll = JScrollPane(area)
        panell2.add(scroll, BorderLayout.CENTER)
        getContentPane().add(panell2, BorderLayout.CENTER)
        getContentPane().add(peu, BorderLayout.SOUTH)

        agafarComarques()

        cbCombo.addActionListener() { visualitzaCom(cbCombo.getSelectedItem().toString()) }

        area.addListSelectionListener() {
            if (!area.isSelectionEmpty())
                visualitzaInstituts(area.getSelectedValue())
            else
                peu.setText("")
        }
    }

    fun agafarComarques() {
        val consultaComarcas = sessio.createQuery("from Comarca")
        val listaComarcas = consultaComarcas.list() as ArrayList<Comarca>
        for (comarca in listaComarcas.sortedBy { it.nomC }) {
            cbCombo.addItem(comarca.nomC)
        }
        // Instruccions per a posar en el ComboBox el nom de totes les comarques, millor si ??s per ordre alfab??tic

    }

    fun visualitzaCom(comarca: String) {
        LogManager.getLogManager().getLogger("").setLevel(Level.SEVERE)

        val comarcaSelec = sessio.get("classes.Comarca", comarca) as Comarca?
        listModel.clear()
        if (comarcaSelec != null) {
            val poblacions = comarcaSelec.poblacions
            for (poblacio in poblacions) {
                listModel.addElement(poblacio.nom)
            }
            area.model = listModel
        }

        // Instruccions per a llegir la comarca que arriba com a par??metre (s'ha de deixar en un objecte Comarca).
        // S'ha de cuidar que si no exiteix la comarca, en el JList es pose que no existeix.
        // La manera d'anar introduint informaci?? en el JList ??s a trav??s del DefaultListModel:
        // listModel.addElement("Linia que es vol introduir ")
        // Per a esborrar els element del JList: listModel.clear()
        // Es pot fer carregant un objecte, o per mig de consulta, per?? en aquest cas podem tenir problemes amb '
        // Una manera de solucionar el problema de la cometa simple ??s utilitzar comarca.replaceAll("'","''").
        // Una altra ??s utilitzar par??metres

    }

    fun visualitzaInstituts(poble: String) {

        val consultaPoblacio = sessio.createQuery("from Poblacio where nom = '${poble.replace("'", "''")}'")
        val poblacio = consultaPoblacio.uniqueResult() as Poblacio

        peu.text = "${poblacio.nom}: ${poblacio.instituts.size} instituts"


        // Instruccions per a mostrar el n??mero d'Instituts del poble seleccionat
        // La millor manera ??s per mig d'una consulta. Podem tenir problemes amb la cometa simple
        // Una manera de solucionar el problema de la cometa simple ??s utilitzar poble.replaceAll("'","''").
        // Una altra ??s utilitzar par??metres

    }

}

fun main() {
    EventQueue.invokeLater {
        FinestraComboBox().isVisible = true
    }
}

