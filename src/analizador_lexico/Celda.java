package analizador_lexico;

public class Celda {
    private int token;
    private String lexema;
    private String tipo;

    public Celda(int token, String lexema, String tipo) {
        this.token = token;
        this.lexema = lexema;
        this.tipo = tipo;
    }

    public int getToken() {
        return token;
    }

    public String getLexema() {
        return lexema;
    }

    public String getTipo() {
        return tipo;
    }

}
