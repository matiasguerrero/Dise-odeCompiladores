package analizador_lexico.maquina_estados;

import analizador_lexico.MaquinaEstados;

public class TestTransicionesEstadoInicial {
    public static void main(String[] args) {
        MaquinaEstados maquinaEstados = new MaquinaEstados();

        //Imprime true si el estado esperado es el alcanzado, e imprime la AS.

        System.out.println(check(maquinaEstados, maquinaEstados.descartable, 0));
        System.out.println(check(maquinaEstados, maquinaEstados.saltoLinea, 0));
        System.out.println(check(maquinaEstados, maquinaEstados.lMinuscula, 1));
        System.out.println(check(maquinaEstados, maquinaEstados.lMayuscula, 2));
        System.out.println(check(maquinaEstados, maquinaEstados.guionBajo, maquinaEstados.estadoFinal));
        System.out.println(check(maquinaEstados, maquinaEstados.porcentaje, 3));
        System.out.println(check(maquinaEstados, maquinaEstados.operadorAritm, maquinaEstados.estadoFinal));
        System.out.println(check(maquinaEstados, maquinaEstados.comparador, 5));
        System.out.println(check(maquinaEstados, maquinaEstados.igual, 6));
        System.out.println(check(maquinaEstados, maquinaEstados.tokenUnitario, maquinaEstados.estadoFinal));
//        System.out.println(check(maquinaEstados, maquinaEstados.digito, maquinaEstados.estadoFinal)); TODO Hacer.
        System.out.println(check(maquinaEstados, maquinaEstados.comilla, 10));

    }

    private static boolean check(MaquinaEstados maquinaEstados, int input, int estadoEsperado){
        maquinaEstados.transicionar(input);

        boolean resultado = maquinaEstados.getEstadoActual() == estadoEsperado;

        maquinaEstados.reset();

        return resultado;
    }
}
