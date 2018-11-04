import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;


public class Main {

    /*
      Este metodo es para convertir el arrayList de float[] a un arreglo de float[], osea una matriz
      de tipo float.
     */

    public static float[][] convertToArray(ArrayList<float[]> arrayList){

        float[][] finArr = new float[arrayList.size()][3];

        for (int i = 0; i < arrayList.size(); i ++){
            float[] temp = arrayList.get(i);
            finArr[i][0] = temp[0];
            finArr[i][1] = temp[1];
            finArr[i][2] = temp[2];
        }

        return finArr;

    }

    /*
     Aca usamos el algoritmo quickSort o cualquiera que sea mas rapido.. para organizar la matriz por la
     posicion x de lar coordenadas
     */

    public static void quickSort(float[][] array){



    }


    /*
      Aca iria el metodo donde comparamos las coordenadas entre si para determinar cuales estan a menos
      de 100 metros o no.

     */

    public static void compararPos(){


    }

    public static void main(String[] args){

        Reader readFile = new Reader();
        Scanner console = new Scanner(System.in);
        String fileName = console.nextLine();

        try {
            readFile.leerArchivo(fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        ArrayList<float[]> temp = readFile.getList();

        float[][] array = convertToArray(temp);

    }

}
