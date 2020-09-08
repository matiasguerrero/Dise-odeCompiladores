package analizador_lexico;

public class MaquinaEstados {
    public final int estadoFinal = 11;

    /**
     * Posibles inputs.
     */
    public final int descartable = 0; // \t, “ “.
    public final int saltoLinea = 1; // \n.
    public final int lMinuscula = 2; // [a-z].
    public final int lMayuscula = 3; // [A-Z].
    public final int guionBajo = 4; // “_“.
    public final int porcentaje = 5; // “%“.
    public final int operadorAritm = 6; // “+”, “-” , “*”, “/”.
    public final int comparador = 7; // “<“, “>“, “!“.
    public final int igual = 8; // “=“.
    public final int tokenUnitario = 9; // “{”, “}”, “(”, “)”, “,”, ”;”.
    public final int digito = 10; // [0-9]
    public final int comilla = 11; // “"“.
    public final int signoPesos = 12; // “$“. //TODO Hacer estados.

    /**
     * Estados.
     */
    private final int estadoInicial = 0;
    private final int estadoDeteccionId = 1;
    private final int estadoDeteccionPR = 2;
    private final int estadoInicioComent = 3;
    private final int estadoComent = 4;
    private final int estadoComp = 5;
    private final int estadoSignoIgual = 6;
    private final int estadoCadena = 10;

    private final TransicionEstado[][] maquinaEstados = new TransicionEstado[estadoFinal][signoPesos + 1]; //[filas][columnas].

    private int estadoActual = 0;

    public MaquinaEstados(){
        /* Estado 0 (Inicial) */
        inicializarEstado(0, estadoFinal,"Nada");
        maquinaEstados[estadoInicial][descartable] = new TransicionEstado(estadoInicial,"Nada"); // \t, ESP.
        maquinaEstados[estadoInicial][saltoLinea] = new TransicionEstado(estadoInicial,"AS12"); // \n.
        maquinaEstados[estadoInicial][lMinuscula] = new TransicionEstado(estadoDeteccionId,"AS0"); // ids.
        maquinaEstados[estadoInicial][lMayuscula] = new TransicionEstado(estadoDeteccionPR,"Nada"); // p. reservadas.
        maquinaEstados[estadoInicial][porcentaje] = new TransicionEstado(estadoInicioComent,"Nada"); // inicio comentario.
        maquinaEstados[estadoInicial][comparador] = new TransicionEstado(estadoComp,"Nada"); //comparacion.
        maquinaEstados[estadoInicial][igual] = new TransicionEstado(estadoSignoIgual,"Nada"); //asignacion e igualacion.
        //TODO deteccion ctes numericas.
        maquinaEstados[estadoInicial][comilla] = new TransicionEstado(estadoCadena,"Nada"); //Inicio cadena multilinea.

        /* Estado 1 (identificadores) */
        inicializarEstado(estadoDeteccionId, estadoFinal,"AS2,AS3");
        maquinaEstados[estadoDeteccionId][lMinuscula] = new TransicionEstado(estadoDeteccionId,"AS1");
        maquinaEstados[estadoDeteccionId][guionBajo] = new TransicionEstado(estadoDeteccionId,"AS1");
        maquinaEstados[estadoDeteccionId][digito] = new TransicionEstado(estadoDeteccionId,"AS1");

        /* Estado 2 (palabras reservadas) */
        inicializarEstado(2, estadoFinal,"AS4");
        maquinaEstados[2][lMayuscula] = new TransicionEstado(estadoDeteccionPR,"Nada");
        maquinaEstados[2][guionBajo] = new TransicionEstado(estadoDeteccionPR,"Nada");

        /* Estado 3 (inicio comentario) */
        inicializarEstado(estadoInicioComent, estadoFinal,"Nada");
        maquinaEstados[estadoInicioComent][porcentaje] = new TransicionEstado(estadoComent,"Nada");

        /* Estado 4 (comentario) */
        inicializarEstado(estadoComent, estadoComent,"Nada");
        maquinaEstados[estadoComent][saltoLinea] = new TransicionEstado(estadoFinal,"AS12");

        /* Estado 5 (inicio comparacion) */
        inicializarEstado(estadoComp, estadoFinal,"AS3");
        maquinaEstados[estadoComp][igual] = new TransicionEstado(estadoFinal,"AS5");

        /* Estado 6 (igualdad / asignacion) */
        inicializarEstado(estadoSignoIgual, estadoFinal,"AS3");
        maquinaEstados[estadoSignoIgual][igual] = new TransicionEstado(estadoFinal,"AS5");

        //TODO Hacer estados para constantes numericas.

        /* Estado 10 (inicio cadena multilinea) */
        inicializarEstado(estadoCadena, estadoCadena,"Nada");
        maquinaEstados[estadoCadena][saltoLinea] = new TransicionEstado(estadoCadena,"AS12");
        maquinaEstados[estadoCadena][comilla] = new TransicionEstado(estadoFinal,"Nada");
    }

    /**
     * Inicializa un estado por defecto. Para cada input posible, establece como siguiente estado el estadoDestino, y le
     * asigna a la transicion una accion semantica.
     *
     * @param estadoAInicializar Estado a inicializar.
     * @param estadoDestino Estado al que se debe transicionar.
     * @param accionSemantica Accion que debe ejecutarse al llegar al estadoAInicializar.
     */
    private void inicializarEstado(int estadoAInicializar, int estadoDestino, Object accionSemantica){
        for (int i = 0; i < signoPesos + 1; i++)
            maquinaEstados[estadoAInicializar][i] = new TransicionEstado(estadoDestino,accionSemantica);
    }

    /**
     * Ejecuta la accion semantica del estado y avanza al siguiente.
     *
     * @param input codigo ASCII del caracter leido.
     */
    public void transicionar(int input){
        TransicionEstado transicionEstado = maquinaEstados[estadoActual][input];

        transicionEstado.ejecutarAccionSemantica();
        estadoActual = transicionEstado.siguienteEstado();
    }

    /**
     * Temporal para testeos.
     *
     * @return el estado actual de la maquina.
     */
    public int getEstadoActual(){
        return estadoActual;
    }

    /**
     * Temporal para testeos.
     */
    public void reset(){
        this.estadoActual = 0;
    }
}
