import java.io.*;
import java.util.*;

class Orbits {
    public static void main(String[] args) throws FileNotFoundException {
        Diagram d = new Diagram(new Scanner(new File("/Users/stygg/Documents/advent2019/data/day6.txt")));
        System.out.println(d.findSantaDistance());
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

    public int findSantaDistance() {
        return findDistance("YOU", "SAN");
    }

    private int findDistance(String name1, String name2) {
        int count = 0;
        SpaceObject start = getObject(name1);
        SpaceObject end = getObject(name2);
        if (start == null || end == null) {
            return -1;
        }
        List<String> orbitChain = new ArrayList<>();
        SpaceObject current = start.getOrbiting();
        while (current != null) {
            orbitChain.add(current.getName());
            current = current.getOrbiting();
        }

        List<String> orbitChain2 = new ArrayList<>();
        current = end.getOrbiting();
        while (current != null) {
            orbitChain2.add(current.getName());
            current = current.getOrbiting();
        }

        for (String source1 : orbitChain) {
            for (String source2 : orbitChain2) {
                if (source1.equals(source2)) {
                    count = orbitChain.indexOf(source1) + orbitChain2.indexOf(source2);
                    return count;
                }
            }
        }

        return -1;
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