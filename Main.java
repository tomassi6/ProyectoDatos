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

      
   
    //Obtener bytes de long
    public static byte[] getBytes(long v) {
     byte[] writeBuffer = new byte[8];
     writeBuffer[7] = (byte) ((v >>> 56) & 0xFF);
     writeBuffer[6] = (byte) ((v >>> 48) & 0xFF);
     writeBuffer[5] = (byte) ((v >>> 40) & 0xFF);
     writeBuffer[4] = (byte) ((v >>> 32) & 0xFF);
     writeBuffer[3] = (byte) ((v >>> 24) & 0xFF);
     writeBuffer[2] = (byte) ((v >>> 16) & 0xFF);
     writeBuffer[1] = (byte) ((v >>> 8) & 0xFF);
     writeBuffer[0] = (byte) ((v >>> 0) & 0xFF);
     return writeBuffer;
   }

    //convertir a long
    public static long toLong(byte[] data, int offset) {
        return ((long)(data[offset + 7] & 0xFF) << 56) | ((long)(data[offset + 6] & 0xFF) << 48)
            | ((long)(data[offset + 5] & 0xFF) << 40)
            | ((long)(data[offset + 4] & 0xFF) << 32)  | ((long)(data[offset + 3] & 0xFF) << 24)  | ((long)(data[offset + 2] & 0xFF) << 16)  | ((long)(data[offset + 1] & 0xFF) << 8)  | ((long)(data[offset] & 0xFF));
    }

    //Radix Sort

    public static double[] RadixSort(double[] array){


    // array temporales para convertir de double a long
        long[] t = new long[array.length];
        long[] a = new long[array.length];
        for(int i = 0; i < array.length; i++){
                byte[] x = getBytes(Double.doubleToRawLongBits(array[i]));
                a[i] = toLong(x, 0);

        }

    int groupLength = 8;
    int bitLength = 64;

    // dimension de 2^r, numero de valores posibles de un número de r bits
    long[] count = new long[1 << groupLength];
    long[] pref = new long[1 << groupLength];
    int groups = bitLength / groupLength;
    int mask = (1 << groupLength) - 1;
    int negatives = 0, positives = 0;
    int shift = 0;
     for(int c = 0; c < groups; c++, shift += groupLength)
    {
        // reset array count
        for(int j = 0; j < count.length; j++){
            count[j] = 0;
        }

        for(int i = 0; i < a.length; i++)
        {
            count[(int)(a[i] >> shift) & mask]++;

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
            int index = (int) pref[(int)(a[i] >> shift) & mask]++;

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

    // Convierte long a la matriz double de nuevo
    double[] ret = new double[a.length];
    for(int i = 0; i < a.length; i++){
        long y = toLong(getBytes(a[i]), 0);
        ret[i] = Double.longBitsToDouble(y);
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
