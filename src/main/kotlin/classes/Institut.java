package classes;

public class Institut {
    private String codi;
    private String nom;
    private String adreca;
    private String numero;
    private Integer codpostal;
    private Poblacio poblacio;

    public String getCodi() {
        return codi;
    }

    public void setCodi(String codi) {
        this.codi = codi;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdreca() {
        return adreca;
    }

    public void setAdreca(String adreca) {
        this.adreca = adreca;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Integer getCodpostal() {
        return codpostal;
    }

    public void setCodpostal(Integer codpostal) {
        this.codpostal = codpostal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Institut institut = (Institut) o;

        if (codi != null ? !codi.equals(institut.codi) : institut.codi != null) return false;
        if (nom != null ? !nom.equals(institut.nom) : institut.nom != null) return false;
        if (adreca != null ? !adreca.equals(institut.adreca) : institut.adreca != null) return false;
        if (numero != null ? !numero.equals(institut.numero) : institut.numero != null) return false;
        if (codpostal != null ? !codpostal.equals(institut.codpostal) : institut.codpostal != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = codi != null ? codi.hashCode() : 0;
        result = 31 * result + (nom != null ? nom.hashCode() : 0);
        result = 31 * result + (adreca != null ? adreca.hashCode() : 0);
        result = 31 * result + (numero != null ? numero.hashCode() : 0);
        result = 31 * result + (codpostal != null ? codpostal.hashCode() : 0);
        return result;
    }

    public Poblacio getPoblacio() {
        return poblacio;
    }

    public void setPoblacio(Poblacio poblacio) {
        this.poblacio = poblacio;
    }
}
