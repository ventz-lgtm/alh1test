package generation;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.Random;

/**
 * Created by Daniel on 13/03/2016.
 */
public class BasicChunk {

    Random rand = new Random();

    public void create(Player player) {
        int startX = player.getLocation().getChunk().getBlock(0,0,0).getX();
        int startZ = player.getLocation().getChunk().getBlock(0,0,0).getZ();
        Location placeLocation = new Location(Bukkit.getServer().getWorld("world"),startX, 80, startZ);
        for(int y = 80; y > 0; y--){
            placeLocation.setY(y);

            if(y == 79) {
                placeLayer(Material.GRASS, placeLocation);
            }

            if(y < 79 && y > 72) {
                placeLayer(Material.DIRT, placeLocation);
            }

            if(y <= 72) {
                placeLayer(Material.STONE, placeLocation);
                placeIron(placeLocation);
                placeGold(placeLocation);
            }
        }
        placeLocation.setY(80);
        placeMobs(placeLocation);
        placeGrass(placeLocation);
        placeFlowers(placeLocation);
        placeTrees(placeLocation);
    }

    private void placeLayer(Material material, Location loc) {

        //generate layer
        Location initialLoc = new Location(Bukkit.getServer().getWorld("world"), loc.getX(), loc.getY(), loc.getZ());

        for(int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                initialLoc.setZ(initialLoc.getZ() + 1);
                initialLoc.getBlock().setType(material);
            }
            initialLoc.setX(initialLoc.getX() + 1);
            initialLoc.setZ(loc.getZ());
        }
    }

    private void placeIron(Location loc){
        //generate layer
        Location initialLoc = new Location(Bukkit.getServer().getWorld("world"), loc.getX(), loc.getY(), loc.getZ());

        for(int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                initialLoc.setZ(initialLoc.getZ() + 1);
                if(rand.nextInt(900) == 50) {
                    initialLoc.getBlock().setType(Material.IRON_ORE);
                }
            }
            initialLoc.setX(initialLoc.getX() + 1);
            initialLoc.setZ(loc.getZ());
        }
    }

    private void placeGold(Location loc){
        //generate layer
        Location initialLoc = new Location(Bukkit.getServer().getWorld("world"), loc.getX(), loc.getY(), loc.getZ());

        for(int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                initialLoc.setZ(initialLoc.getZ() + 1);
                if(rand.nextInt(2000) == 50) {
                    initialLoc.getBlock().setType(Material.GOLD_ORE);
                }
            }
            initialLoc.setX(initialLoc.getX() + 1);
            initialLoc.setZ(loc.getZ());
        }
    }

    private void placeGrass(Location loc){
        Location initialLoc = new Location(Bukkit.getServer().getWorld("world"), loc.getX(), loc.getY(), loc.getZ());

        for(int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                initialLoc.setZ(initialLoc.getZ() + 1);
                if(rand.nextInt(10) == 1) {
                    initialLoc.getBlock().setType(Material.LONG_GRASS);
                    initialLoc.getBlock().setData((byte)1);
                }
            }
            initialLoc.setX(initialLoc.getX() + 1);
            initialLoc.setZ(loc.getZ());
        }
    }

    private void placeFlowers(Location loc){
        Location initialLoc = new Location(Bukkit.getServer().getWorld("world"), loc.getX(), loc.getY(), loc.getZ());

        for(int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                initialLoc.setZ(initialLoc.getZ() + 1);
                if(rand.nextInt(35) == 1) {
                    switch(rand.nextInt(2)) {
                        case 0:
                            initialLoc.getBlock().setType(Material.RED_ROSE);
                            break;
                        case 1:
                            initialLoc.getBlock().setType(Material.YELLOW_FLOWER);
                            break;
                        default:
                            break;
                    }
                }
            }
            initialLoc.setX(initialLoc.getX() + 1);
            initialLoc.setZ(loc.getZ());
        }
    }

    private void placeTrees(Location loc){
        Location tempTreeLocation;
        int numOfTrees = rand.nextInt(2) + 4;
        for(int i = 0; i <= numOfTrees; i++){
            tempTreeLocation = loc.clone();
            tempTreeLocation.setX(tempTreeLocation.getX() + rand.nextInt(13) + 2);
            tempTreeLocation.setZ(tempTreeLocation.getZ() + rand.nextInt(13) + 2);
            Bukkit.getWorld("world").generateTree(tempTreeLocation, TreeType.TREE);
        }

    }

    private Location tempMobSpawnLocation;
    private int mobNumber;
    private int amountOfMobs;
    private void placeMobs(Location loc){
        amountOfMobs = rand.nextInt(2) + 6;
        for(int i = 0; i < amountOfMobs; i++) {
            int counter = 0;
            while(true){
                tempMobSpawnLocation = loc.clone();
                tempMobSpawnLocation.setX(tempMobSpawnLocation.getX() + rand.nextInt(11) + 3);
                tempMobSpawnLocation.setZ(tempMobSpawnLocation.getZ() + rand.nextInt(11) + 3);
                counter++;
                if(tempMobSpawnLocation.getBlock().getType() == Material.AIR){
                    break;
                }
                if(counter > 50){
                    break;
                }
            }

            mobNumber = rand.nextInt(100);
            if(mobNumber  <= 10){
                Bukkit.getWorld("world").spawnEntity(tempMobSpawnLocation, EntityType.PIG);
            }else if(mobNumber <=20){
                if(rand.nextInt(25) == 1){
                    Bukkit.getWorld("world").spawnEntity(tempMobSpawnLocation, EntityType.MUSHROOM_COW);
                }else {
                    Bukkit.getWorld("world").spawnEntity(tempMobSpawnLocation, EntityType.COW);
                }
            }else if(mobNumber <=30) {
                Bukkit.getWorld("world").spawnEntity(tempMobSpawnLocation, EntityType.SHEEP);
            }else{
                Bukkit.getWorld("world").spawnEntity(tempMobSpawnLocation, EntityType.CHICKEN);
            }
        }
    }

}
