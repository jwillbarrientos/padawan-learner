package amigosCode.dataStructures;

public class ClaseObjeto {
    public static void main(String[] args) {
//      Un cambio que quiero commitear a github
        Casa casa1 = null;
        System.out.println(casa1.colorDeLaClase);
        casa1.colorDeLaClase="Gris";
        System.out.println(casa1.colorDeLaClase);
        Casa casa2 = new Casa();
        System.out.println(casa2.colorDeLaClase);


//        Casa casaDeBarrio = new CasaDeBarrio();
//        casaDeBarrio.mostrarColor();

//        Casa casa = new Casa("SuperBlack");
//        System.out.println(casa.color);
//        System.out.println(new Casa().color);

//        amarillo.toString();
//        System.out.println(amarillo.color);
//        amarillo = new Casa();
//        amarillo.color = "amarillo";
//        System.out.println(amarillo.color);
//        metodo(amarillo);
//        System.out.println(amarillo.color);
    }


    static class Casa{
        public String color = "negro";
        static String colorDeLaClase = "rojo";
        void mostrarColor() {
            System.out.println(color);
        }

    }

    static class CasaDeBarrio extends Casa{

        void mostrarColor(){
            System.out.println("Cualquier cosa");
        }

    }





//
//        @Override
//        public boolean equals(Object o) {
//            if (this == o) return true;
//            if (o == null || getClass() != o.getClass()) return false;
//            Ball ball = (Ball) o;
//            return Objects.equals(color, ball.color);
//        }
//
//        @Override
//        public int hashCode() {
//            return Objects.hash(color);
//        }
//
//        @Override
//        public String toString() {
//            return "Ball{" +
//                    "color='" + color + '\'' +
//                    '}';
//        }
//    }
}