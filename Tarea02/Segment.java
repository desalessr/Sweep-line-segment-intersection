import java.util.*;
import java.lang.*;

public class Segment extends AVLNode<Segment> {
	
	Punto begin; //Punto que representara el Punto de entrada, es decir el menor de los 2 puntos
	Punto end; //Punto que representa el Punto de salida, el mayor de los 2 puntos

	public Segment(Punto b, Punto e) {
		this.begin = b;
		this.end = e;
	}

	public boolean equals(Segment s) {
		return this.begin.x == s.begin.x && 
				this.begin.y == s.begin.y &&
				this.end.x == s.end.x &&
				this.end.y == s.end.y;	
	}

	//Metodo necesario para mantener bien ordenado el AVLTree con los segmentos, decidir el orden
	//que quieren darle a sus segmentos, es decir leer de arriba hacia abajo o de derecha a izquierda.  
	@Override
	public int compareTo(Segment s) { /* El orden aquí será de  izquierda a derecha por el  barrido y*/
        if(this.equals(s)){
            return 0;
        }
        Punto x1 = this.begin;
        Punto x2 = s.begin;
        /*Consideremos los casos degenerados también, si tenemos que hay dos puntos iniciales con la misma coordenada x
        diremos que el segmento con la menor coordenada y -que es la mayor- es el segmento menor*/
        if(x1.getX() == x2.getX()) {
            return (x1.getY() >= x2.getY())? -1 : 1;
        }
        return (x1.getX() < x2.getX())? -1 : 1;
    } 

	//Metodo que ayudara a saber si existe una interseccion entre 2 segmentos.
    public boolean intersection(Segment s) { /*Esta comparación se realizará entre vecinos estrictamente, por lo que podemos atacar los 
                                                casos degenerados -que no son intersecciones propias*/ 

        Punto x1 = this.begin;
        Punto y1 = this.end;
        Punto x2 = s.begin;
        Punto y2 = s.end;

        /*Primero checamos el caso de la intersección propia -común- entre segmentos*/ 
        if(x1.turn(y1,x2)*x1.turn(y1,y2) < 0){
            return true;
        }
        /*CASOS DEGENERADOS */
        /*El caso cuando la intersección es propia entre segmentos, pero son colineales*/ 
        if (x1.turn(y1,x2) == 0 && x1.turn(y1,y2) == 0 && x2.turn(y2,x1) == 0 && x2.turn(y2,y1) == 0) {
            if(y1.compareTo(x2) >=0){ /*Si las rectas se traslapan colinealmente o se intersectan en un extremo*/
                return true;
            }
        }
        /*Caso cuando la intersección no es propia*/

        /*Caso cuando coinciden en una coordenada*/
    }	

	//Metodo que regresa el punto de interseccion entre 2 sementos.
	public Punto getIntersection(Segment s) { 		
        Punto intersection = new Punto();
        return intersection;
	}

	//Metodo que ayuda a la representacion visual del punto en terminal.
	//Modificarlo si quieren, esta sensillo pero me sirvio para saber como voy  
	public void imprime() {
		System.out.print("(" + this.begin.x + "," + this.begin.y + ")-(" + this.end.x + "," + this.end.y + ")");
	}

	//Metodo para imprimir el arbol en terminal de manera descente. 
	//Este igual modificarlo si quieren, Esta bastante bueno para saber como van con su barrido y su orden. 
	public static void print(AVLNode root) {
        if(root == null) {
            System.out.println("(VACIO)");
            return;
        }
        int height = Math.max(root.leftHeight, root.rightHeight) + 1,
            width = (int)Math.pow(2, height-1);
        List<AVLNode> current = new ArrayList<AVLNode>(1),
            next = new ArrayList<AVLNode>(2);
        current.add(root);
        final int maxHalfLength = 4;
        int elements = 1;
        StringBuilder sb = new StringBuilder(maxHalfLength*width);
        for(int i = 0; i < maxHalfLength*width; i++)
            sb.append(' ');
        String textBuffer;
        for(int i = 0; i < height; i++) {
            sb.setLength(maxHalfLength * ((int)Math.pow(2, height-1-i) - 1));
            textBuffer = sb.toString();
            for(AVLNode n : current) {
                System.out.print(textBuffer);
                if(n == null) {
                    System.out.print("       ");
                    next.add(null);
                    next.add(null);
                } else {
                	Segment s = (Segment) n;
                    s.imprime();
                    next.add(n.left);
                    next.add(n.right);
                }
                System.out.print(textBuffer);
            }
            System.out.println();
            if(i < height - 1) {
                for(AVLNode n : current) {
                    System.out.print(textBuffer);
                    if(n == null)
                        System.out.print("     ");
                    else
                        System.out.printf("%s          %s",
                                n.left == null ? " " : "/", n.right == null ? " " : "\\");
                    System.out.print(textBuffer);
                }
                System.out.println();
            }
            elements *= 2;
            current = next;
            next = new ArrayList<AVLNode>(elements);
        }
    }

}

