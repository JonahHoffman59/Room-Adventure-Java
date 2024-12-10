// Name: Jonah Hoffman
// Date: 5/19/2023
// Desription: room adventure... again *boooo* but in java this time *ooooh*

import java.util.Scanner;

public class RoomAdventure {
    

    

    // main function and loop
    public static void main(String[] args) {
        // create each room
        Room r1 = new Room("Room 1");
        Room r2 = new Room("Room 2");
        Room r3 = new Room("Room 3");
        Room r4 = new Room("Room 4");
        Room r5 = new Room("Forest");
        Room currentRoom = r1;
        // add exits to each room
        r1.addExit("east", r2);
        r1.addExit("south", r3);
    
        r2.addExit("west", r1);
        r2.addExit("south", r4);
    
        r3.addExit("east", r4);
        r3.addExit("north", r1);
    
        r4.addExit("west", r3);
        r4.addExit("north", r2);
    
        // add items to each room
        r1.addItem("chair", "this chair has two legs. seems like it skipped leg day.");
        r1.addItem("table", "seems like it skipped chair day.");
    
        r2.addItem("rug", "it needs to be vacuumed.");
        r2.addItem("fireplace", "looks like it was recently used.");
    
        r3.addItem("bookshelves", "looks like someone was reading their fortnite strategy guide.");
        r3.addItem("statue", "looks like it skipped leg day.");
    
        r4.addItem("oven", "wonder what's for dinner. the oven obviously didn't skip leg day");
        
        r5.addItem("trees", "yuuuup, them is some trees");

        // add grababbles to the rooms
        r1.addGrababble("key");

        r2.addGrababble("$19 dolla fortnte card");
        
        r3.addGrababble("fortnite strategy guide");
    
        r4.addGrababble("lip injections");
        String[] inventory = new String[10];
        Room placeholder = new Room("placeholder");

        while (true) {

            // kill game if player dies
            if (currentRoom == null) {
                placeholder.death();
                break;
            }
            
            String status = ("\n" + currentRoom.toString());


            // info about inventory
            if (inventory.length != 0) {
                status += "\nYou are carrying: ";
                for (String item : inventory) {
                    if (item != null) {
                    status += (item + " ");
                    }
                }
            } else {
                status += "\nYou have no items in your inventory";
            }

            // inform user of current status
            System.out.println(status);

            // set response to a default value
            String response = "Invalid input. Try the format [verb] [noun]. I only understand the verbs 'go', 'look', 'take', and 'eat'.\nType 'quit' to quit.";
            
            // ask for input from user
            System.out.println("What would you like to do?");
            Scanner s = new Scanner(System.in);

            String action = s.nextLine();
            
            String[] quit = {"quit", "q", "exit", "bye", "adios", "ciao", "meesa out"};

            for (String word : quit) {
                if (action.equals(word)) {
                    break;
                }
            }

            // split the input
            String[] words = action.split(" ", 3);
            String noun = words[1];
            String verb = words[0];
            if (words.length == 2) {
                verb = words[0];
                noun = words[1];
            }

            // did they say go
            if (verb.equals("go")) {
                response = "Invalid exit";
                if ((currentRoom.name.equals("Room 4")) && (noun.equals("south"))) {
                    for(String items : inventory) {
                        if (items != null) {
                            if (items.equals("key")) {
                                boolean WON = true;
                                response = "Congrats, you have escaped and beat the game";
                                currentRoom = r5;
                                    
                            }
                        }
                    }
                }
                
                for (String word : currentRoom.exitLocations) {
                    if (noun.equals(word)) {
                        for (int i=0; i < currentRoom.exitLocations.length; i++) {
                            if ((currentRoom == r4)) {
                                if(noun != "south") {
                                    if (currentRoom.exitLocations[i].equals(word) ) {
                                        currentRoom = currentRoom.exits[i];
                                        response = "Room changed";
                                        i = 100;
                                    }
                                }
                            } else if (currentRoom != r4) {
                                if (currentRoom.exitLocations[i].equals(word) ) {
                                    currentRoom = currentRoom.exits[i];
                                    response = "Room changed";
                                    i = 100;
                                }
                            }
                        }
                    }
                }
            // did they say look?
            } else if (verb.equals("look")) {
                response = "That item doesn't exist here.";
                for (String word : currentRoom.items) {
                    if (noun.equals(word)) {
                        for (int i=0; i < currentRoom.items.length; i++) {
                            if (currentRoom.items[i].equals(word)) {
                                response = currentRoom.itemDescriptions[i];
                            }
                        }
                    }
                }
            // did they say take
            } else if (verb.equals("take")) {
                response = "That's not something I can take.";
                System.out.println(currentRoom.grababbles + noun);
                if (currentRoom.grababbles != null) {
                    for(int i=0; i < inventory.length; i++) {
                        if (inventory[i] == null) {
                            inventory[i] = noun;
                            // adds secret escape exit if you find key
                            if (currentRoom == r1) {    
                                r4.addExit("south", r5);
                            }
                            noun = null;
                            response = ("Grabbed " + currentRoom.grababbles);
                            currentRoom.grababbles = null;
                        }
                    }
                }
                /*for (String word : currentRoom.grababbles) {
                    if (noun.equals(word)) {
                        for (int i=0; i < currentRoom.grababbles.length; i++) {
                            if (currentRoom.grababbles[i].equals(word)) {
                                for (int j=0; j < inventory.length; j++) {
                                    if (inventory[j] == null) {
                                        inventory[j] = noun;
                                        j = 10;
                                    }
                                }
                                response = ("Grabbed" + noun);
                            }
                        }
                    }
                }*/
            }

            // Did they say eat
            else if (verb.equals("eat")) {
                if (currentRoom.equals(r4) && noun.equals("bread")) {
                    response = "mmmmmmmmmmmmmmmmmmmmmmmmmmmm bread. I wonder how much I could eat";
                }
            }

            // print response
            System.out.println("\n" + response);
            
        }
    }
}

// create room class and instantiate some things
class Room extends RoomAdventure{
    String name;

    String[] exitLocations = new String[10];
    Room[] exits = new Room[10];

    String[] items = new String[10];
    String[] itemDescriptions = new String[10];

    String grababbles;

    // constructors
    Room() {
        name = "UNNAMED";
    }

    Room(String name) {
        this.name = name;
    }

    // accessors and mutators
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }


    public Room[] getExits() {
        return exits;
    }

    public void setExits(Room[] exits) {
        this.exits = exits;
    }


    public String[] getExitLocations() {
        return exitLocations;
    }

    public void setExitLocations(String[] exitLocations) {
        this.exitLocations = exitLocations;
    }


    public String[] getItemDescriptions() {
        return itemDescriptions;
    }

    public void setItemDescriptions(String[] itemDescriptions) {
        this.itemDescriptions = itemDescriptions;
    }


    public String[] getItems() {
        return items;
    }

    public void setItems(String[] items) {
        this.items = items;
    }


    public String getGrababbles() {
        return grababbles;
    }

    public void setGrababbles(String grababbles) {
        this.grababbles = grababbles;
    }



    // additional methods
    public void addExit(String exitLocation, Room exit) {
        for (int i = 0; i < exitLocations.length; i++) {
            if (exitLocations[i] == null) {
                exitLocations[i] = exitLocation;
                exits[i] = exit;
                exitLocation = null;
                exit = null;
            }
        }
    }

    public void addItem(String itemName, String itemDesciption) {
        for (int i = 0; i < items.length; i++) {
            if (items[i] == null) {
                items[i] = itemName;
                itemDescriptions[i] = itemDesciption;
                itemDesciption = null;
                itemName = null;
            }
        }
    }

    public void addGrababble(String grababble) {
        /*for (int i = 0; i < grababbles.length-1; i++) {
            if (grababbles[i] == null) {
                grababbles[i] = grababble;
                grababble = null;
            }
        }*/
        grababbles = grababble;

    }

    public void deleteGrababble(String grababble) {
        /*for (int i=0; i < grababbles.length; i++) {
            if (grababbles[i].equals(grababble)) {
                grababbles[i] = null;
            }*/
        grababbles = null;
    }
    

    public String toString() {
        String result = "";
        result += ("Location: " + name);
        if (items.length != 0) {
            result += "\nYou see:";
            for (String item : items) {
                if (item != null) {
                    result += (" " + item);
                }
            }
            result += "\n";
        }
        if (exitLocations.length != 0) {
            result += "Exits:";
            for (int i=0; i<10; i++) {
                if (exitLocations[i] != null) {
                    result += (" " + exitLocations[i]);
                }
            }
        }
        
        return result;
    }


    void death() {
        System.out.println("You died.");
    }
}