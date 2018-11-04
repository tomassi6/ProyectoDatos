

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

public class Reader {

    private ArrayList<float[]> mat = new ArrayList<>();

    public void leerArchivo(String file) throws FileNotFoundException{
        Scanner in = new Scanner(new File(file+".txt"));

        String visaje_linea = in.nextLine(); // La primera line no sirve pa nada

        int cont = 0;

        while(in.hasNextLine()){
            float pos_x, pos_y, pos_z;
            float[] pos = new float[3];

            String line = in.nextLine();
            String[] tokens = line.split(",");

            pos[0] = Float.parseFloat(tokens[0]);
            pos[1] = Float.parseFloat(tokens[1]);
            pos[2] = Float.parseFloat(tokens[2]);

            mat.add(pos);

        }
    }

    public ArrayList<float[]> getList(){
        return mat;
    }

}
