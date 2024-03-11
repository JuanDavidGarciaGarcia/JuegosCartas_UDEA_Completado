package juegocartas;

public enum NombreCarta {
    AS(1),
    DOS(2),
    TRES(3),
    CUATRO(4),
    CINCO(5),
    SEIS(6),
    SIETE(7),
    OCHO(8),
    NUEVE(9),
    DIEZ(10),
    JACK(11),
    QUEEN(12),
    KING(13);
    
    private int Orden;

    private NombreCarta(int Orden) {
        this.Orden = Orden;
    }

    public int getOrden() {
        return Orden;
    }

}
