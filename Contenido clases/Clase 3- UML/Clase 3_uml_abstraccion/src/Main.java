import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Persona persona= new Persona("Roberto Carlos","Ronaldo",18);
        System.out.println("Metodo : 1¿La persona es mayor: ? "+persona.esMayorDeEdad());
        System.out.println(" Metodo 2: ¿La persona es mayor: ? "+persona.esMayorDeEdad2());
        // Creamos varias personas
        Persona[] personasArray = {
                new Persona("Juan Pérez", "12345678", 25),
                new Persona("María López", "87654321", 30),
                new Persona("Carlos Gómez", "11223344", 22),
                new Persona("Ana Martínez", "55667788", 28)
        };

        ArrayList<Persona> listaPersonas = new ArrayList<>();
        for (Persona p : personasArray) {
            listaPersonas.add(p);
        }

        System.out.println("=== DEMOSTRACIÓN DE BUCLES EN JAVA ===\n");

        // 1. FOR tradicional
        System.out.println("1. Usando FOR tradicional:");
        for (int i = 0; i < personasArray.length; i++) {
            System.out.println(personasArray[i]);
        }

        System.out.println("\n2. Usando WHILE:");
        int i = 0;
        while (i < personasArray.length) {
            System.out.println(personasArray[i]);
            i++;
        }

        System.out.println("\n3. Usando DO-WHILE:");
        int j = 0;
        do {
            System.out.println(personasArray[j]);
            j++;
        } while (j < personasArray.length);

        System.out.println("\n4. Usando FOR-EACH (el más recomendado para recorrer colecciones):");
        for (Persona persona1 : personasArray) {
            System.out.println(persona);
        }

        // Ejemplo de equals()
        System.out.println("\n=== PRUEBA DE equals() ===");
        Persona p1 = new Persona("Juan Pérez", "12345678", 15);
        Persona p2 = new Persona("Juan Pérez", "12345678", 15);
        Persona p3 = new Persona("Juan Pérez", "99999999", 25);

        System.out.println("p1.equals(p2) → " + p1.equals(p2));  // true
        System.out.println("p1.equals(p3) → " + p1.equals(p3));  // false
    }
    }

