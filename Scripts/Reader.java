import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

public class Reader {

    private ArrayList<double[]> mat = new ArrayList<>();

    public void leerArchivo(String file) throws FileNotFoundException{
        Scanner in = new Scanner(new File(./txt_files/file+".txt"));

        String visaje_linea = in.nextLine(); // La primera line no sirve pa nada


        int cont = 0;
        while(in.hasNextLine()){
            double[] pos = new double[3];

            String line = in.nextLine();
            String[] tokens = line.split(",");

            pos[0] = Double.parseDouble(tokens[0]); //pos_x
            pos[1] = Double.parseDouble(tokens[1]); //pos_y
            pos[2] = Double.parseDouble(tokens[2]); //pos_z
            mat.add(pos);

        }
    }

    public ArrayList<double[]> getList(){
        return mat;
    }

}
