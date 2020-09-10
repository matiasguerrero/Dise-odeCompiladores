package analizador_lexico;

import java.util.Hashtable;

public class TablaDeSimbolos {
    private Hashtable<Integer, Celda> tablaSimb;

    public TablaDeSimbolos(){
        tablaSimb=new Hashtable<>();
    }

    /**
     * Agrega una celda (token,lexema,tipo). En caso de existir previamente, retorna el objeto. Sino, null.
     * @param celda
     * @return
     */
    public Celda agregar(Celda celda){
        if (tablaSimb.containsKey(celda.getToken()))
            return tablaSimb.get(celda.getToken());
        else {
            tablaSimb.put(celda.getToken(), celda);
            return null;
        }
    }

    /**
     * Elimina una celda dado el token
     * @param token
     * @return
     */
    public boolean eliminar(int token){
        if (tablaSimb.containsKey(token)){
            tablaSimb.remove(token);
            return true;
        }
        return false;
    }
}
