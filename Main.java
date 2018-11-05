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

       //Obtener bytes de entero
    public static byte[] getBytes(int v) {
     byte[] writeBuffer = new byte[4];
     writeBuffer[3] = (byte) ((v >>> 24) & 0xFF);
     writeBuffer[2] = (byte) ((v >>> 16) & 0xFF);
     writeBuffer[1] = (byte) ((v >>> 8) & 0xFF);
     writeBuffer[0] = (byte) ((v >>> 0) & 0xFF);
     return writeBuffer;
   }

    //convertir a entero
    public static int toInt32(byte[] data, int offset) {
    return (data[offset] & 0xFF) | ((data[offset + 1] & 0xFF) << 8)
    | ((data[offset + 2] & 0xFF) << 16)
    | ((data[offset + 3] & 0xFF) << 24);
    }

    //Radix Sort

    public static float[] RadixSort(float[] array){


    // array temporales para convertir de float a int
        int[] t = new int[array.length];
        int[] a = new int[array.length];
        for(int i = 0; i < array.length; i++){
                byte[] x = getBytes(Float.floatToRawIntBits(array[i]));
                a[i] = toInt32(x, 0);

        }

    int groupLength = 4;
    int bitLength = 32;

    // dimension de 2^r, numero de valores posibles de un número de r bits
    int[] count = new int[1 << groupLength];
    int[] pref = new int[1 << groupLength];
    int groups = bitLength / groupLength;
    int mask = (1 << groupLength) - 1;
    int negatives = 0, positives = 0;

    for(int c = 0, shift = 0; c < groups; c++, shift += groupLength)
    {
        // reset array count
        for(int j = 0; j < count.length; j++){
            count[j] = 0;
        }

        for(int i = 0; i < a.length; i++)
        {
            count[(a[i] >> shift) & mask]++;

            // Cuenta todos los valores negativos en primera ronda
            if(c == 0 && a[i] < 0)
                negatives++;
        }
        if(c == 0){
            positives = a.length - negatives;
        }
        // calcula los prefijos
        pref[0] = 0;
        for(int i = 1; i < count.length; i++){
                 pref[i] = pref[i - 1] + count[i - 1];
        }
        // ordena elementos de a[] a t[]
        for(int i = 0; i < a.length; i++){
            // Obtiene índice para ordenar el número
            int index = pref[(a[i] >> shift) & mask]++;

            if(c == groups - 1)
            {
                // Último grupo (de los más significativos), si el número es negativo se ordena inversamente al frente de la matriz, dejando los positivos al fondo
                if(a[i] < 0){
                    index = positives - (index - negatives) - 1;
                }else{
                    index += negatives;
                }
            }
            t[index] = a[i];
        }

        // a[] = t[], comienza de nuevo hasta el último grupo
        a = t.clone();
    }

    // Convierte los ints a la matriz float de nuevo
    float[] ret = new float[a.length];
    for(int i = 0; i < a.length; i++){
        int y = toInt32(getBytes(a[i]), 0);
        ret[i] = Float.intBitsToFloat(y);
        System.out.println("Posición ["+i+"] = "+ ret[i]);
    }
    return ret;
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
