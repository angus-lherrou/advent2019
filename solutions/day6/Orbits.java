import java.io.*;
import java.util.*;

public class Orbits {
    public static void main(String[] args) throws FileNotFoundException {
        Diagram d = new Diagram(new Scanner(new File("/Users/stygg/Documents/advent2019/data/day6.txt")));
        System.out.println(d.countOrbits());
    }
}

/**
 * Diagram class.
 */
class Diagram {
    SpaceObject com;
    List<SpaceObject> objects;

    public Diagram(Scanner file) {
        objects = new ArrayList<>();
        com = new SpaceObject("COM");
        objects.add(com);
        while (file.hasNextLine()) {
            String[] relationship = file.nextLine().split("\\)");
            SpaceObject newObj = getObject(relationship[1]);
            if (newObj == null) {
                newObj = new SpaceObject(relationship[1]);
                objects.add(newObj);
            }
            SpaceObject orbitCenter = getObject(relationship[0]);
            if (orbitCenter == null) {
                orbitCenter = new SpaceObject(relationship[0]);
                objects.add(orbitCenter);
            }
            orbitCenter.addOrbiter(newObj);
            newObj.orbit(orbitCenter);
        }
    }

    private SpaceObject getObject(String name) {
        for (SpaceObject obj : objects) {
            if (obj.getName().equals(name)) {
                return obj;
            }
        }
        return null;
    }

    public String toString() {
        return objects.toString();
    }

    int countOrbits() {
        return countOrbits(com);
    }

    private int countOrbits(SpaceObject obj) {
        if (obj.hasOrbiters()) {
            int total = 0;
            for (SpaceObject o : obj.getOrbiters()) {
                total += o.countComDistance() + countOrbits(o);
            }
            return total;
        } else {
            return 0;
        }
    }

    public int countSomeOrbits() {
        return objects.get(objects.size()-1).countComDistance();
    }
}

/**
 * SpaceObject class.
 */
class SpaceObject {
    String name;
    List<SpaceObject> orbiters;
    SpaceObject orbiting;

    public SpaceObject(String name) {
        this.name = name;
        this.orbiting = null;
        this.orbiters = new ArrayList<>();
    }

    void orbit(SpaceObject obj) {
        orbiting = obj;
    }

    void addOrbiter(SpaceObject obj) {
        orbiters.add(obj);
    }

    String getName() {
        return name;
    }

    List<SpaceObject> getOrbiters() {
        return orbiters;
    }

    boolean hasOrbiters() {
        return orbiters.size() > 0;
    }

    int countComDistance() {
        SpaceObject current = orbiting;
        int count = 0;
        while (current != null) {
            count++;
            current = current.getOrbiting();
        }
        return count;
    }

    SpaceObject getOrbiting() {
        return orbiting;
    }

    public String toString() {
        return name;
    }
}
