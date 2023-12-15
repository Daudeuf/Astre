package fr.elpine.astre.metier.objet;

public class Module
{
    private String nom;
    private String code;
    private String abreviation;
    private String typeModule;
    private boolean validation;
    private Semestre semestre;

    public Module(String nom, String code, String abreviation, String typeModule, boolean validation,Semestre semestre)
    {
        this.nom            = nom;
        this.code           = code;
        this.abreviation    = abreviation;
        this.typeModule     = typeModule;
        this.validation     = validation;
        this.semestre       = semestre;
    }

    /*   GETTER    */

    public String  getNom         () { return nom         ;}
    public String  getCode        () { return code        ;}
    public String  getAbreviation () { return abreviation ;}
    public String  getTypeModule  () { return typeModule  ;}
    public boolean estValide      () { return validation  ;}
    public Semestre getSemestre   () { return semestre    ;}

    /*   SETTER   */

    public void setNom            ( String nom        ) { this.nom         = nom        ;}
    public void setCode           ( String code       ) { this.code        = code       ;}

    public void setAbreviation ( String  abreviation ) { this.abreviation = abreviation ;}
    public void setTypeModule  ( String  typeModule )  { this.typeModule  = typeModule  ;}
    public void setValidation  ( boolean validation )  { this.validation  = validation  ;}
    public void setSemestre    ( Semestre semestre  )  { this.semestre    = semestre    ;}
}
