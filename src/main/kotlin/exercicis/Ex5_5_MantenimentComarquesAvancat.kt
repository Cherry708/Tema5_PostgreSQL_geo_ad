package exercicis



import java.awt.BorderLayout
import java.awt.FlowLayout
import java.awt.GridLayout
import java.awt.EventQueue

import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JTextField

import classes.Comarca
import org.hibernate.cfg.Configuration

import kotlin.system.exitProcess

class FinestraMantenimentComarquesAvancat : JFrame() {
    val etIni = JLabel("Manteniment de COMARQUES")
    val etNom = JLabel("Nom comarca")
    val etProv = JLabel("Nom província")

    val nomComarca = JTextField()
    val nomProvincia = JTextField()

    val primer = JButton("<<")
    val anterior = JButton("<")
    val seguent = JButton(">")
    val ultim = JButton(">>")

    val inserir = JButton("Inserir")
    val modificar = JButton("Modificar")
    val esborrar = JButton("Esborrar")

    val acceptar = JButton("Acceptar")
    val cancelar = JButton("Cancel·lar")

    val eixir = JButton("Eixir")

    val pDalt = JPanel(FlowLayout())
    val pCentre = JPanel(GridLayout(8, 0))
    val pDades = JPanel(GridLayout(2, 2))
    val pBotonsMov = JPanel(FlowLayout())
    val pBotonsAct = JPanel(FlowLayout())
    val pBotonsAccCanc = JPanel(FlowLayout())
    val pEixir = JPanel(FlowLayout())

    val s = Configuration().configure().buildSessionFactory().openSession()

    var llistaComarques = ArrayList<Comarca>()
    var indActual = 0

    var accio: String = ""

    init {
        defaultCloseOperation = JFrame.DO_NOTHING_ON_CLOSE
        setTitle("HIBERNATE: Manteniment Comarques")

        setBounds(100, 100, 350, 400)
        setLayout(BorderLayout())

        getContentPane().add(pCentre, BorderLayout.CENTER)
        getContentPane().add(JPanel(FlowLayout()), BorderLayout.WEST)
        getContentPane().add(JPanel(FlowLayout()), BorderLayout.EAST)
        getContentPane().add(pEixir, BorderLayout.SOUTH)

        pDalt.add(etIni)
        pCentre.add(pDalt)

        pDades.add(etNom)
        pDades.add(nomComarca)
        pDades.add(etProv)
        pDades.add(nomProvincia)
        pCentre.add(pDades)

        nomComarca.setEditable(false)
        nomProvincia.setEditable(false)

        pCentre.add(JPanel(FlowLayout()))

        pBotonsMov.add(primer)
        pBotonsMov.add(anterior)
        pBotonsMov.add(seguent)
        pBotonsMov.add(ultim)
        pCentre.add(pBotonsMov)

        pBotonsAct.add(inserir)
        pBotonsAct.add(modificar)
        pBotonsAct.add(esborrar)
        pBotonsAct.isVisible = true
        pCentre.add(pBotonsAct)

        pBotonsAccCanc.add(acceptar)
        pBotonsAccCanc.add(cancelar)
        pCentre.add(pBotonsAccCanc)
        pBotonsAccCanc.setVisible(false)

        pEixir.add(eixir)

        llistaComarques = agafarComarques()
        visComarca(indActual)

        primer.addActionListener() { primer() }
        anterior.addActionListener() { anterior() }
        seguent.addActionListener() { seguent() }
        ultim.addActionListener() { ultim() }

        inserir.addActionListener() { inserir() }
        modificar.addActionListener() { modificar() }
        esborrar.addActionListener() { esborrar() }

        acceptar.addActionListener() { acceptar() }
        cancelar.addActionListener() { cancelar() }

        eixir.addActionListener() { eixir() }
    }

    /*
    Como no se actualiza la lista con iformacion de la base de datos
    en tiempo de ejecucion, las modificaciones se han de realizar tanto
    en la lista como en la base de datos
     */
    fun agafarComarques(): ArrayList<Comarca> {
        var llista = ArrayList<Comarca>()
        // ací aniran les sentències per a omplir (i retornar) la llista de comarques
        val consultaComarcas = s.createQuery("from Comarca")
        llista = consultaComarcas.list() as ArrayList<Comarca>
        return llista
    }

    fun visComarca(indActual: Int) {
        // Mètode per a visualitzar la comarca marcada per l'índex que ve com a paràmetre
        nomComarca.text = llistaComarques.get(indActual).nomC
        nomProvincia.text = llistaComarques.get(indActual).provincia
        controlBotons()
    }

    fun primer() {
        indActual = 0
        visComarca(indActual)
        controlBotons()
    }

    fun anterior() {
        indActual--
        visComarca(indActual)
    }

    fun seguent() {
        indActual++
        visComarca(indActual)
    }

    fun ultim() {
        indActual = llistaComarques.lastIndex
        visComarca(indActual)
        controlBotons()
    }

    fun controlBotons() {
        // Mètode per a habilitar/deshabilitar els botons anterior i següent, si s'està en la primera o última comarca
        // No us oblideu d'habilitar-los quan toque
        anterior.isEnabled = indActual != 0
        seguent.isEnabled = indActual != llistaComarques.lastIndex
    }

    fun inserir() {
        //accions per a preparar per a inserir una nova comarca
        nomComarca.text = ""
        nomComarca.isEditable = true

        val t = s.beginTransaction ()
        val comarca = Comarca()

        comarca.nomC = nomComarca.text
        comarca.provincia = nomProvincia.text

        s.save(comarca)

        t.commit()

        pBotonsAccCanc.isVisible = true
        activarBotons(false)
        //Modificar la base de datos y la lista
    }

    fun modificar() {
        //accions per a preparar per a modificar la comarca actual
        //Modificar la base de datos y la lista
        nomProvincia.isEditable = true

        val t = s.beginTransaction()
        val comarca = s.load("classes.Comarca", nomComarca.text) as Comarca
        comarca.provincia = nomProvincia.text
        s.update(comarca)
        t.rollback()

        llistaComarques.get(llistaComarques.indexOf(comarca)).provincia = nomProvincia.text

        pBotonsAccCanc.isVisible = true
        activarBotons(false)
    }

    fun esborrar() {
        //accions per a preparar per a esborrar la comarca actual
        //Modificar la base de datos y la lista
        pBotonsAccCanc.isVisible = true

        val t = s.beginTransaction()
        val comarca = s.load("classes.Comarca", nomComarca.text)

        s.delete(comarca)

        t.commit()
        activarBotons(false)
    }

    fun acceptar() {
        //accions per a fer l'acció d'inserir, modificar i esborrar

        //Insertar (Se ha de modificar la base de datos y lista)
        if (nomComarca.isEditable && !nomProvincia.isEditable){
            //Llamar a la funcion
            inserir()
        }

        //Modificar (Se ha de modificarla bade de datos y lista)
        if (nomComarca.isEditable && nomProvincia.isEditable){
            //Llamar a la funcion
            modificar()
        }

        //Borrar (Se ha de modificarla bade de datos y lista)
        if (!nomComarca.isEditable && !nomProvincia.isEditable){
            //Llamar a la funcion
            esborrar()
            /*
            ESTO SOLO NO, TAMBIEN ELIMINAR EN LA BASE DE DATOS
            llistaComarques.remove(llistaComarques.get(indActual))
            */
        }
        pBotonsAccCanc.isVisible = false
        activarBotons(true)
    }

    fun cancelar() {
        //accions per a cancel·lar la inserció, modificació o esborrat
        nomComarca.text = llistaComarques.get(indActual).nomC
        nomComarca.isEditable = false
        pBotonsAccCanc.isVisible = false

        activarBotons(true)
    }

    fun buscaCom(text: String): Int {
        // Busca la comarca passada com a paràmetre en llistaComarques, tornant el seu índex. Per a situar-se després d'una inserció.
        var ind = 0
        // Ací haurien d'anar les sentències

        return ind
    }

    fun activarBotons(b: Boolean) {
        // Mètode per activar o desactivar (segons el paràmetre) els botons de moviment i els d'actualització
        // Farem invisible o visible el panell dels botons acceptar i cancel·lar (pBotonsAccCanc
        pBotonsMov.isOpaque = b
        pBotonsAct.isEnabled = b
    }

    fun eixir() {
        //accions per a tancar i per a eixir
        s.close()
        exitProcess(0)
    }
}
fun main() {
    EventQueue.invokeLater {
        FinestraMantenimentComarquesAvancat().isVisible = true
    }
}

