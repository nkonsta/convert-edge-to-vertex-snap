import java.io.*;

/**
 * Created by nkonsta on 29/07/2017.
 */
public class EdgetoVertexConversion {

    private String path;
    private String[] StringArray;
    private String outfile;

    public EdgetoVertexConversion(Integer size,String FilePath,String OutputFilePath) {
        StringArray = new String[size];
        path = FilePath;
        outfile = OutputFilePath;

    }

    public void convert() throws IOException {
        System.out.println("Converting");
        buildArray();
        finalizeArray();
        System.out.println("Done converting");
    }

    private void buildArray() throws IOException {

        System.out.println("Building Array");
        BufferedReader reader = new BufferedReader(new FileReader(path));
        String line;
        while((line = reader.readLine())!=null) {

            //exclude comments
            if (!line.contains("#")) {
                String[] parts = line.split("\t");
                int vertex = Integer.parseInt(parts[0]);

                if (StringArray[vertex] == null)
                    StringArray[vertex] = "[[" + parts[1] + ",0]";
                else
                    StringArray[vertex] = StringArray[vertex] + ",[" + parts[1] + ",0]";
            }
        }


        System.out.println("Array ready");

        reader.close();
    }


    private void finalizeArray(){
        System.out.println("Finalizing array");
        for (int i=0; i<StringArray.length; i++){
            if (StringArray[i]!=null)
                StringArray[i] = "[" + String.valueOf(i) + ",0," + StringArray[i] + "]]";
        }

        System.out.println("Finalizing finished");
    }

    public void save() throws FileNotFoundException {

        //save only nodes that have outgoing edges
        try (PrintWriter out = new PrintWriter(outfile)) {
            for (int i = 0;i < StringArray.length; i++){
                if (StringArray[i]!=null){
                    out.print(StringArray[i] + "\n");

                }
            }

            out.close();
        }
    }

    public static void main(String [] args) throws IOException {
        if (args.length!=3) {
            System.out.println("Correct usage: java <class name> <graph order> <filepath> <outputfilepath>");
            System.exit(0);
        }
        else {
            EdgetoVertexConversion instance = new EdgetoVertexConversion(Integer.parseInt(args[0]),args[1],args[2]);
            instance.convert();
            instance.save();
        }
    }

}
