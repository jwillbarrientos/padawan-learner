package book.serializableExample;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ObjectStreamSample {
    public static List<Animal> getAnimals(File dataFile) throws IOException, ClassNotFoundException {
        List<Animal> animals = new ArrayList<Animal>();
        try(ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(dataFile)))) {
            while(true) {
                Object object = in.readObject(); //Animal.class.newInstance();
                if(object instanceof Animal)
                    animals.add((Animal) object);
            }
        } catch (EOFException e) {
            //File end reached
        }
        return animals;
    }

    public static void createAnimalsFile(List<Animal> animals, File dataFile) throws IOException {
        try(ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(dataFile)))) {
            for(Animal animal : animals)
                out.writeObject(animal);
        }
    }

    public static void main(String[] args) throws Exception {
//        List<Animal> animals = new ArrayList<Animal>();
//        animals.add(new Animal("Tommy Tiger", 5, 'T'));
//        animals.add(new Animal("Peter Penguin", 8, 'P'));
//        System.out.println(animals);
//
//        File dataFile = new File("animal.data");
//        createAnimalsFile(animals, dataFile);
//        System.out.println(getAnimals(dataFile));

//        System.out.println(Paths.get("C:\\Users\\barri\\.gitconfig"));
        System.out.println(Files.readAllLines(Paths.get("C:\\Users\\barri\\.gitconfig")).size());
        System.out.println(Files.readAllLines(Paths.get("C:/Users/barri/.gitconfig")).size());
        System.out.println(Files.readAllLines(Paths.get("/Users/barri/.gitconfig")).size());
        System.out.println(Paths.get("/Users/barri/.gitconfig").isAbsolute());

        Files.createDirectory(Paths.get("/bison/field/pasture"));

//        System.out.println(new File(".").getAbsolutePath());
//
//        Path file = Paths.get("ocp/pom.xml");
//        System.out.println(Files.readAllLines(file).size());
//        Path uriRel = Paths.get(URI.create("ocp/pom.xml").getPath());
//        System.out.println(Files.readAllLines(uriRel).size());
//        Path uri = Paths.get(new URI("file:///C:/Users/barri/IdeaProjects/padawan-learner/ocp/pom.xml"));
//        System.out.println(Files.readAllLines(uri).size());
//
//        FileSystem fileSystem = FileSystems.getFileSystem(
//                new URI("http://www.selikoff.net"));
//        Path path = fileSystem.getPath("duck.txt");
//        System.out.println(Files.readAllLines(path).size());
    }
}
