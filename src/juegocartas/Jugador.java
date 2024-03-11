package juegocartas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.swing.JPanel;

public class Jugador {

    private int TOTAL_CARTAS = 10;
    private int MARGEN = 20;
    private int DISTANCIA = 50;
    private int[] contadorEscalera = new int[NombreCarta.values().length];

    private Carta[] cartas = new Carta[TOTAL_CARTAS];

    private Random r = new Random();

    public void repartir() {
        for (int i = 0; i < TOTAL_CARTAS; i++) {
            cartas[i] = new Carta(r);
        }
    }

    public void mostrar(JPanel pnl) {
        pnl.removeAll();
        int x = MARGEN;
        //recorrido objetual por una lista de objetos
        for (Carta c : cartas) {
            c.mostrar(pnl, x, MARGEN);
            x += DISTANCIA;
        }

        pnl.repaint();
    }

    public String getGrupos() {
        String mensaje = "No se encontraron grupos";
        int[] contadores = new int[NombreCarta.values().length];
        for (Carta c : cartas) {
            contadores[c.getNombre().ordinal()]++;
        }

        int totalGrupos = 0;
        for (int c : contadores) {
            if (c >= 2) {
                totalGrupos++;
            }
        }

        if (totalGrupos > 0) {
            mensaje = "Los grupos encontrados fueron:\n";
            for (int i = 0; i < contadores.length; i++) {
                if (contadores[i] >= 2) {
                    mensaje += Grupo.values()[contadores[i]] + " de " + NombreCarta.values()[i] + "\n";
                }
            }
        }
        return mensaje;
    }

    public String getPuntaje() {
        String mensaje = "El total del puntaje es:\n";
        int totalPuntaje = 0;
        PuntajeCarta puntajes = new PuntajeCarta();

        int[] contadores = new int[NombreCarta.values().length];
        int[] contadorTotal = new int[NombreCarta.values().length];

        for (Carta c : cartas) {
            contadores[c.getNombre().ordinal()]++;
        }
        
        // Este puntaje contiene un error, que al repetir, le resta a una unidad un dato que no deberia
        // y eso hace que se sume una carta, que no deberia sumar.
        for (int i = 0; i < contadores.length; i++){
            contadorTotal[i] = contadores[i] - contadorEscalera[i];
            System.out.println(contadorEscalera[i]);
        }

        for (int i = 0; i < contadores.length; i++) {
            if (contadorTotal[i] == 1) {
                totalPuntaje += puntajes.getPuntajeCarta()[i];
            }
        }
        if (totalPuntaje >= 0) {
            mensaje += totalPuntaje;
        }

        return mensaje;
    }

    public Map<Pinta, List<NombreCarta>> agruparPorPinta() {
        int[] indices = new int[cartas.length];
        Carta[] cartasOrdenadas = new Carta[TOTAL_CARTAS];
        for (int i = 0; i < cartas.length; i++) {
            indices[i] = cartas[i].getIndice();
        }
        Arrays.sort(indices);

        for (int i = 0; i < cartas.length; i++) {
            for (int j = 0; j < cartas.length; j++) {
                if (cartas[j].getIndice() == indices[i]) {
                    cartasOrdenadas[i] = cartas[j];
                }
            }
        }
        Map<Pinta, List<NombreCarta>> agruparPinta = new HashMap<>();

        for (Carta carta : cartasOrdenadas) {
            Pinta pinta = carta.getPinta();
            NombreCarta nombre = carta.getNombre();
            List<NombreCarta> conjuntoCartas = agruparPinta.getOrDefault(pinta, new ArrayList<>());
            conjuntoCartas.add(nombre);
            agruparPinta.put(pinta, conjuntoCartas);
        }

        return agruparPinta;
    }

    public String Escalera() {
        //La escalera funciona correctamente, solo que repite la misma escalera por que encuentra una distinta
        //escalera pero la guarda en el misma lista de la pinta.
        
        String mensajeEscalera = "";
//    int[] contadorEscalera = new int[NombreCarta.values().length];
        Map<Pinta, List<NombreCarta>> agruparPinta = agruparPorPinta();
        //indiceContadorEscalera=new ArrayList<>();
        
        for (Map.Entry<Pinta, List<NombreCarta>> entry : agruparPinta.entrySet()) {
            Pinta pinta = entry.getKey();
            List<NombreCarta> agrupacionPinta = entry.getValue();
            //Integer indicePinta=pintaCarta.getSumarIndice();
            int sumaTemporal = 1;
            List<Integer> guardarEscalera = new ArrayList<>();
            List<Integer> guardarEscaleraFinal = new ArrayList<>();
            Integer primerNumero;
            Integer segundoNumero;
            for (int i = 0; i < agrupacionPinta.size() - 1; i++) {
                primerNumero = agrupacionPinta.get(i).getOrden();
                segundoNumero = agrupacionPinta.get(i + 1).getOrden();

                if (segundoNumero - primerNumero != 1) {
                    sumaTemporal = 1;
                    guardarEscalera.clear();

                } else {
                    sumaTemporal++;
                    contadorEscalera[agrupacionPinta.get(i).ordinal()]++;
                    contadorEscalera[agrupacionPinta.get(i + 1).ordinal()]++;
                    if (!guardarEscalera.contains(primerNumero)) {
                        guardarEscalera.add(primerNumero);
                    }
                    if (!guardarEscalera.contains(segundoNumero)) {
                        guardarEscalera.add(segundoNumero);
                    }
                }
                if (!guardarEscaleraFinal.containsAll(guardarEscalera)) {
                    guardarEscaleraFinal.addAll(guardarEscalera);
                }
            
                if (sumaTemporal >= 2) {
                
                if (!mensajeEscalera.contains("ESCALERA de " + pinta.name() + " de " + guardarEscaleraFinal + "\n")) {
                    mensajeEscalera += "ESCALERA de " + pinta.name() + " de " + guardarEscaleraFinal +"\n";
                }
               
            }
                
            }
        }
        getPuntaje();
        return mensajeEscalera;
    }         
}

