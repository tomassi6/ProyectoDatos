import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;


public class Main {

    /*
      Este metodo es para convertir el arrayList de float[] a un arreglo de DroneBee[]
     */

    public static DroneBee[] convertToArray(ArrayList<double[]> arrayList){

        DroneBee[] listDrone = new DroneBee[arrayList.size()];

        for (int i = 0; i < arrayList.size(); i ++){
            double[] temp = arrayList.get(i);
            DroneBee bee = new DroneBee(temp[0],temp[1],temp[2]);
            listDrone[i] = bee;
        }

        return listDrone;

    }

    /*
     Aca usamos el algoritmo RadixSort para organizar la matriz por la
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

    public static long toLong(byte[] data, int offset) {
        return ((long)(data[offset + 7] & 0xFF) << 56) | ((long)(data[offset + 6] & 0xFF) << 48)
                | ((long)(data[offset + 5] & 0xFF) << 40)
                | ((long)(data[offset + 4] & 0xFF) << 32)  | ((long)(data[offset + 3] & 0xFF) << 24)  | ((long)(data[offset + 2] & 0xFF) << 16)  | ((long)(data[offset + 1] & 0xFF) << 8)  | ((long)(data[offset] & 0xFF));
    }

    //Radix Sort

    public static void RadixSort(DroneBee[] array){


        // array temporales para convertir de float a int
        DroneBee[] temp = new DroneBee[array.length];

        for(int i = 0; i < array.length; i++){
            byte[] x = getBytes(Double.doubleToRawLongBits(array[i].getPos_fx()));
            array[i].setPos_x(toLong(x,0));

        }

        int groupLength = 8;
        int bitLength = 64;

        // dimension de 2^r, numero de valores posibles de un número de r bits
        int[] count = new int[1 << groupLength];
        int[] pref = new int[1 << groupLength];
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

            for(int i = 0; i < array.length; i++)
            {
                count[(int)(array[i].getPos_x() >> shift) & mask]++;

                // Cuenta todos los valores negativos en primera ronda
                if(c == 0 && array[i].getPos_x() < 0)
                    negatives++;
            }
            if(c == 0){
                positives = array.length - negatives;
            }
            // calcula los prefijos
            pref[0] = 0;
            for(int i = 1; i < count.length; i++){
                pref[i] = pref[i - 1] + count[i - 1];
            }
            // ordena elementos de a[] a t[]
            for(int i = 0; i < array.length; i++){
                // Obtiene índice para ordenar el número
                int index = pref[(int)(array[i].getPos_x() >> shift) & mask]++;

                if(c == groups - 1)
                {
                    // Último grupo (de los más significativos), si el número es negativo se ordena inversamente al frente de la matriz, dejando los positivos al fondo
                    if(array[i].getPos_x() < 0){
                        index = positives - (index - negatives) - 1;
                    }else{
                        index += negatives;
                    }
                }
                temp[index] = array[i];
            }

            // a[] = t[], comienza de nuevo hasta el último grupo
            array = temp.clone();
        }


        /*
            //Este Codigo es para saber si el algoritmo los esta ordenando.

        for(int i = 0; i < array.length; i++){
            System.out.println("Posición ["+i+"] = "+ array[i].getPos_fx());
        }

        */

    }


    /*
      Aca iria el metodo donde comparamos las coordenadas entre si para determinar cuales estan a menos
      de 100 metros o no.

     */


    public static boolean calculateDistanceLong(double xA, double xB){
        return Math.abs(xB - xA) * 111111 <= 100;
    }


    public static boolean calculateDistanceNormal(double xA, double xB, double yA, double yB, double zA, double zB){

        return Math.sqrt(  Math.pow((xA - xB)*111111,2) +
                           Math.pow((yA - yB)*111111,2) +
                           Math.pow(zA - zB,2) ) <= 100;

    }

    public static void compararPos(DroneBee[] array){

        //Este metodo G.O.A.T...

        for (int i = 0; i < array.length - 1; i++ ){

            int j = i + 1;

            double t = array[i].getPos_fx();
            double u = array[j].getPos_fx();

            double v = array[i].getPos_fy();

            double x = array[i].getPos_fz();

            while(calculateDistanceLong(t, u) && j < array.length){

                u = array[j].getPos_fx();
                double w = array[j].getPos_fy();
                double y = array[j].getPos_fz();

                if(calculateDistanceNormal(t, u, v, w, x, y)){
                    System.out.println("Abejas en riezgo de colision");
                    System.out.println("Abeja 1: " + "," + t + "," + v + "," + x);
                    System.out.println("Abeja 2: " + "," + u + "," + w + "," + y);
                    System.out.println("------------------------------------------");
                }
                j++;
            }

        }

    }



    public static void main(String[] args){

        Reader readFile = new Reader();
        Scanner console = new Scanner(System.in);

        System.out.println("Ingrese el nombre del archivo......... no ponga el .txt");
        String fileName = console.nextLine();

        try {
            readFile.leerArchivo(fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        ArrayList<double[]> temp = readFile.getList();

        DroneBee[] array = convertToArray(temp);
        RadixSort(array);

        compararPos(array);

    }

}
