package doc.num.projet;


public enum AttributRole  {
    
    MESSAGE,
    DONATION,
    REQUEST,
    AUTH,
    ACCEPT,
    DENY,
    BARTER,
    CAT,
    CATREQUEST,
    NOCAT,
    ERRORMSG,
    AUTHREQUEST,
    ;

    public String get() {
        return "ROLE_"+this.name();
    }
}