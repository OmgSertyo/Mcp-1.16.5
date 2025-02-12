package sertyo.events.util;


import net.minecraft.util.FastRandom;

public class NameGeneratorHelper {
    private static  final String[] negr1 = {
            "The", "Super", "Mega", "Ultra", "Power", "Master", "Great",
            "Hyper", "Quantum", "Atomic", "Cosmic", "Turbo", "Mighty",
            "Fantastic", "Legendary", "Epic", "Glorious", "Incredible",
            "Marvelous", "Supreme", "Stellar", "Dynamic", "Heroic",
            "Valiant", "Brave", "Noble", "Radiant", "Brilliant", "Bold",
            "Fearless", "Fierce", "Savage", "Wild", "Storm", "Thunder",
            "Lightning", "Solar", "Lunar", "Galactic", "Nebula", "Phoenix",
            "Titan", "Colossal", "Majestic", "Regal", "Royal", "Sovereign",
            "MetaFaze"
    };

    private static  final String[] negr2 = {
            "Gamer", "Player", "Ninja", "Warrior", "Champion", "Legend", "Hero",
            "Master", "Conqueror", "Slayer", "Guardian", "Knight", "Paladin",
            "Crusader", "Ranger", "Assassin", "Mage", "Sorcerer", "Wizard",
            "Enchanter", "Necromancer", "Berserker", "Gladiator", "Samurai",
            "Viking", "Pirate", "Outlaw", "Mercenary", "Hunter", "Scout",
            "Rogue", "Thief", "Sentinel", "Protector", "Savior", "Defender",
            "Avenger", "Warlord", "Commander", "Captain", "General", "Marshal",
            "Overlord", "Monarch", "Emperor", "King", "Queen", "Prince",
            "Princess", "Duke", "Duchess", "Baron", "Baroness", "Lord", "Lady",
            "Warden", "Sentinel", "Crusader", "Champion", "Virtuoso", "Adept",
            "Prodigy", "Savant", "Genius", "Maven", "Whiz", "Ace",
            "Virtuoso", "Expert", "Specialist", "Technician", "Strategist",
            "Tactician", "Operative", "Agent", "Spy", "Infiltrator", "Saboteur",
            "Shadow", "Phantom", "Specter", "Shade", "Mystic", "Seer",
            "Oracle", "Prophet", "Visionary", "Dreamer", "Illusionist",
            "Conjurer", "Invoker", "Diviner", "Alchemist", "Shaman", "Druid",
            "Elementalist", "Geomancer", "Pyromancer", "Hydromancer", "Aeromancer",
            "SinShluxi"
    };

    private static  final String[] negr3 = {
            "Swift", "Fierce", "Sneaky", "Brave", "Savage", "Fearless", "Stealthy",
            "Valiant", "Bold", "Cunning", "Mighty", "Noble", "Resolute", "Vigilant",
            "Relentless", "Intrepid", "Daring", "Gallant", "Tenacious", "Ferocious",
            "Unyielding", "Audacious", "Courageous", "Indomitable", "Dauntless",
            "Unstoppable", "Determined", "Invincible", "Unbreakable", "Epic",
            "Legendary", "Mythic", "Heroic", "Glorious", "Triumphant", "Fearsome",
            "Imposing", "Stalwart", "Stout", "Steadfast", "Grim", "Resolute",
            "Fateful", "Loyal", "Trusty", "Staunch", "Hardy", "Doughty",
            "Unflinching", "Unfaltering", "Brisk", "Keen", "Alert", "Quick",
            "Agile", "Nimble", "Lithe", "Spry", "Energetic", "Vibrant",
            "Dynamic", "Lively", "Sprightly", "Active", "Forceful", "Vigorous",
            "Spirited", "Animated", "Robust", "Brawny", "Muscular", "Husky",
            "Strong", "Tough", "Solid", "Sturdy", "Hefty", "Powerful",
            "Mighty", "Colossal", "Gigantic", "Mammoth", "Titanic", "Towering",
            "Massive", "Monumental", "Heroic", "Bravehearted", "Gutsy", "Doughty",
            "Unyielding", "Unwavering", "Iron-willed", "Strong-willed", "Unshakeable",
            "CrashDami"
    };

    private static  final String[] negr4 = {
            "Wolf", "Tiger", "Lion", "Eagle", "Panther", "Dragon", "Phoenix",
            "Bear", "Leopard", "Hawk", "Falcon", "Cheetah", "Jaguar", "Griffin",
            "Raven", "Fox", "Shark", "Viper", "Cobra", "Falcon", "Crocodile",
            "Raptor", "Condor", "Lynx", "Ocelot", "Cougar", "Puma", "Hound",
            "Bison", "Mammoth", "Rhino", "Buffalo", "Stallion", "Mustang",
            "Pegasus", "Wyvern", "Cerberus", "Minotaur", "Chimera", "Hydra",
            "Kraken", "Basilisk", "Manticore", "Unicorn", "Sphinx", "Grizzly",
            "Kodiak", "Polar Bear", "Sabertooth", "Direwolf", "Orca",
            "Narwhal", "Walrus", "Beluga", "Elephant", "Hippo", "Gorilla",
            "Orangutan", "Chimpanzee", "Baboon", "Mongoose", "Ferret",
            "Weasel", "Otter", "Badger", "Wolverine", "Honey Badger", "Lizard",
            "Iguana", "Gecko", "Komodo Dragon", "Monitor Lizard", "Tortoise",
            "Turtle", "Alligator", "Caiman", "Anaconda", "Python", "Boa",
            "Eel", "Swordfish", "Marlin", "Barracuda", "Piranha", "Penguin",
            "Albatross", "Seagull", "Pelican", "Stork", "Heron", "Flamingo",
            "Top"
    };

    private static  final FastRandom random = new FastRandom();

    public static String getMEGANIGGA() {
        String prefix = random.nextBoolean() ? negr1[random.nextInt(negr2.length)] : "";
        String middle = random.nextBoolean() ? negr3[random.nextInt(negr3.length)] : negr4[random.nextInt(negr4.length)];
        String suffix = negr2[random.nextInt(negr2.length)];
        int number = random.nextInt(32);

        return String.format("%s%s%s%d", prefix, middle, suffix, number).substring(0, Math.min(16, String.format("%s%s%s%d", prefix, middle, suffix, number).length()));
    }
}
