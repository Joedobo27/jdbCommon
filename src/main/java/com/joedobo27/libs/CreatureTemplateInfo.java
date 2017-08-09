package com.joedobo27.libs;

import java.util.Arrays;
import java.util.Objects;

@SuppressWarnings("unused")
public enum CreatureTemplateInfo {
    ANACONDA(38, "Anaconda", "An over 3 meters long muscle, this grey-green snake is formidable."),
    AVENGER_OF_THE_LIGHT(68, "Avenger of the Light", "Some kind of giant lumbers here, hunting humans."),
    BARTENDER(41, "Bartender", "A fat and jolly bartender, eager to help people settling in."),
    BISON(82, "Bison", "The bison are impressive creatures when moving in hordes."),
    BLACK_BEAR(42, "Black bear", "The black bear looks pretty kind, but has strong, highly curved claws ready to render you to pieces."),
    BLACK_DRAGON_HATCHLING(18, "Black dragon hatchling", "The black dragon hatchling is not as large as a full-grown dragon and unable to fly."),
    BLACK_DRAGON(89, "Black dragon", "The menacing huge dragon, with scales as dark as the night."),
    BLACK_WOLF(10, "Black wolf", "This dark shadow of the forests glares hungrily at you."),
    BLUE_DRAGON_HATCHLING(104, "Blue dragon hatchling", "The blue dragon hatchling is not as large as a full-grown dragon and unable to fly."),
    BLUE_DRAGON(91, "Blue dragon", "The menacing huge dragon, with dark blue scales."),
    BLUE_WHALE(97, "Blue whale", "These gigantic creatures travel huge distances looking for food, while singing their mysterious songs."),
    BROWN_BEAR(12, "Brown bear", "The brown bear has a distinctive hump on the shoulders, and long deadly claws on the front paws."),
    BROWN_COW(3, "Brown cow", "A brown docile cow."),
    BULL(49, "Bull", "This bull looks pretty menacing."),
    CALF(50, "Calf", "This calf looks happy and free."),
    CAVE_BUG(43, "Cave bug", "Some kind of unnaturally large and deformed insect lunges at you from the dark. It has a grey carapace, with small patches of lichen growing here and there."),
    CHICKEN(48, "Chicken", "A cute chicken struts around here."),
    CHILD(66, "Child", "A small child is here, exploring the world."),
    COBRA_KING(63, "Cobra King", "A huge menacing king cobra is guarding here, head swaying back and forth."),
    CRAB(95, "Crab", "Crabs are known to hide well and walk sideways."),
    CROCODILE(58, "Crocodile", "This meat-eating reptile swims very well but may also perform quick rushes on land in order to catch you."),
    DEATHCRAWLER_MINION(73, "Deathcrawler minion", "The Deathcrawler minions usually spawn in large numbers. They have deadly poisonous bites."),
    DEER(54, "Deer", "A fallow deer is here, watching for enemies."),
    DOG(51, "Dog", "Occasionally this dog will bark and scratch itself behind the ears."),
    DOLPHIN(99, "Dolphin", "A playful dolphin. They have been known to defend sailors in distress from their natural enemy, the shark."),
    DRAKESPIRIT(76, "Drakespirit", "Drakespirits are usually found in their gardens on Valrei. They are hungry and aggressive."),
    EAGLESPIRIT(77, "Eaglespirit", "The Eaglespirits live on a glacier on Valrei. They will attack if hungry or threatened."),
    EASTER_BUNNY(53, "Easter bunny", "Wow, the mystical easter bunny skips around here joyfully!"),
    EPIPHANY_OF_VYNORA(78, "Epiphany of Vynora", "This female creature is almost see-through, and you wonder if she is made of water or thoughts alone."),
    EVIL_SANTA(47, "Evil Santa", "Some sort of Santa Claus is standing here, with a fat belly, yellow eyes, and a bad breath."),
    FOAL(65, "Foal", "A foal skips around here merrily."),
    FOG_SPIDER(105, "Fog Spider", "Usually only encountered under foggy conditions, this creature is often considered an Omen."),
    FOREST_GIANT(20, "Forest giant", "With an almost sad look upon its face, this filthy giant might be mistaken for a harmless huge baby."),
    GOBLIN_LEADER(26, "Goblin leader", "Always on the brink of cackling wildly, this creature is possibly insane."),
    GOBLIN(23, "Goblin", "This small, dirty creature looks at you greedily, and would go into a frenzy if you show pain."),
    GREEN_DRAGON_HATCHLING(17, "Green dragon hatchling", "The green dragon hatchling is not as large as a full-grown dragon and unable to fly."),
    GREEN_DRAGON(90, "Green dragon", "The menacing huge dragon, with emerald green scales."),
    GUARDBRUTAL(8, "guardBrutal", "Not many people would like to cross this warrior."),
    GUARDTOUGH(7, "guardTough", "This warrior would pose problems for any intruder."),
    GUIDE(61, "Guide", "A rather stressed out person is here giving instructions on how to survive to everyone who just arrived."),
    HELL_HORSE(83, "Hell Horse", "This fiery creature is rumoured to be the mounts of the demons of Sol."),
    HELL_HOUND(84, "Hell Hound", "The hell hound is said to be spies and assassins for the demons of Sol."),
    HELL_SCORPIOUS(85, "Hell Scorpious", "The pets of the demons of Sol are very playful."),
    HEN(45, "Hen", "A fine hen proudly prods around here."),
    HORDE_OF_THE_SUMMONED_TOWER_GUARD(35, "Horde of the Summoned tower guard", "This person seems to be able to put up some resistance. These guards will help defend you if you say help."),
    HORSE(64, "Horse", "Horses like this one have many uses."),
    HUGE_SHARK(71, "Huge shark", "These huge sharks were apparently not just a rumour. How horrendous!"),
    HUGE_SPIDER(25, "Huge spider", "Monstrously huge and fast, these spiders love to be played with."),
    HUMAN(1, "Human", "Another explorer."),
    INCARNATION_OF_LIBILA(81, "Incarnation of Libila", "This terrifying female apparition has something disturbing over it. As if it's just one facet of Libila."),
    ISLES_TOWER_GUARD(67, "Isles tower guard", "This person seems to be able to put up some resistance. These guards will help defend you if you say help."),
    JENN_KELLON_TOWER_GUARD(34, "Jenn-Kellon tower guard", "This person seems to be able to put up some resistance. These guards will help defend you if you say help."),
    JOE_THE_STUPE(2, "Joe the Stupe", "A hollow-eyed person is standing here, potentially dangerous but stupid as ever."),
    JUGGERNAUT_OF_MAGRANON(79, "Juggernaut of Magranon", "A ferocious beast indeed, the juggernaut can crush mountains with its horned forehead."),
    KYKLOPS(22, "Kyklops", "This large drooling one-eyed giant is obviously too stupid to feel any mercy."),
    LADY_OF_THE_LAKE(62, "Lady of the lake", "The hazy shape of a female spirit lingers below the waves."),
    LAMB(101, "Lamb", "A small cuddly ball of fluff."),
    LARGE_RAT(13, "Large rat", "This is an unnaturally large version of a standard black rat."),
    LAVA_FIEND(57, "Lava fiend", "These lava creatures enter the surface through lava pools, probably in order to hunt. Or burn."),
    LAVA_SPIDER(56, "Lava spider", "Lava spiders usually lurk in their lava pools, catching curious prey."),
    MANIFESTATION_OF_FO(80, "Manifestation of Fo", "Something seems to have gone wrong as Fo tried to create his manifestation. The thorns are not loving at all and it seems very aggressive."),
    MOL_REHAN_TOWER_GUARD(36, "Mol-Rehan tower guard", "This person seems to be able to put up some resistance. These guards will help defend you if you say help."),
    MOUNTAIN_GORILLA(39, "Mountain gorilla", "This normally calm mountain gorilla may suddenly become a very fierce and dangerous foe if annoyed."),
    MOUNTAIN_LION(14, "Mountain lion", "Looking like a huge cat, it is tawny-coloured, with a small head and small, rounded, black-tipped ears."),
    NPC_HUMAN(113, "NPC Human", "A relatively normal person stands here waiting for something to happen."),
    OCTOPUS(100, "Octopus", "Larger specimen have been known to pull whole ships down into the abyss. Luckily this one is small."),
    PHEASANT(55, "Pheasant", "The pheasant slowly paces here, vigilant as always."),
    PIG(44, "Pig", "A pig is here, wallowing in the mud."),
    RABID_HYENA(40, "Rabid hyena", "Normally this doglike creature would act very cowardly, but some sickness seems to have driven it mad and overly aggressive."),
    RAM(102, "Ram", "A mythical beast of legends, it stares back at you with blood filled eyes and froth around the mouth."),
    RED_DRAGON_HATCHLING(103, "Red dragon hatchling", "The red dragon hatchling is not as large as a full-grown dragon and unable to fly."),
    RED_DRAGON(16, "Red dragon", "The menacing huge dragon, with scales in every possible red color."),
    RIFT_BEAST(106, "Rift Beast", "These vile creatures emerge from the rift in great numbers."),
    RIFT_CASTER(110, "Rift Caster", "Proficient spell casters, but they seem to avoid direct contact."),
    RIFT_JACKAL(107, "Rift Jackal", "The Jackals accompany the Beasts as they spew out of the rift."),
    RIFT_OGRE_MAGE(111, "Rift Ogre Mage", "Ogre Mages have mysterious powers."),
    RIFT_OGRE(108, "Rift Ogre", "The Rift Ogres seem to bully Beasts and Jackals into following orders."),
    RIFT_SUMMONER(112, "Rift Summoner", "Summoners seem to be able to call for aid from the Rift."),
    RIFT_WARMASTER(109, "Rift Warmaster", "These plan and lead attacks from the rift."),
    ROOSTER(52, "Rooster", "A proud rooster struts around here."),
    SALESMAN(9, "Salesman", "An envoy from the king, buying and selling items."),
    SANTA_CLAUS(46, "Santa Claus", "Santa Claus is standing here, with a jolly face behind his huge white beard."),
    SCORPION(59, "Scorpion", "The monstruously large type of scorpion found in woods and caves here is fairly aggressive."),
    SEA_SERPENT(70, "Sea Serpent", "Sea Serpents are said to sleep in the dark caves of the abyss for years, then head to the surface to hunt once they get hungry."),
    SEAL_CUB(98, "Seal cub", "A young seal, waiting to be fed luscious fish."),
    SEAL(93, "Seal", "These creatures love to bathe in the sun and go for a swim hunting fish."),
    SHEEP(96, "Sheep", "A mythical beast of legends, it stares back at you with blood filled eyes and froth around the mouth."),
    SKELETON(87, "Skeleton", "This abomination has been animated by powerful magic."),
    SOL_DEMON(72, "Sol Demon", "This demon has been released from Sol."),
    SON_OF_NOGUMP(75, "Son of Nogump", "Nogump the dirty has given birth to this foul two-headed giant wielding a huge twohanded sword."),
    SPAWN_OF_UTTACHA(74, "Spawn of Uttacha", "Uttacha is a vengeful demigod who lives in the depths of an ocean on Valrei. These huge larvae are hungry and confused abominations here."),
    SPIRIT_AVENGER(30, "Spirit avenger", "This restless spirit vaguely resembles a human being, that for some reason has chosen to guard this place."),
    SPIRIT_BRUTE(31, "Spirit brute", "This fierce spirit seems restless and upset but for some reason has chosen to guard this place."),
    SPIRIT_GUARD(28, "Spirit guard", "This fierce spirit vaguely resembles a human warrior, and for some reason guards here."),
    SPIRIT_SENTRY(29, "Spirit sentry", "This spirit vaguely resembles a human being, and for some reason guards here."),
    SPIRIT_SHADOW(33, "Spirit shadow", "A dark humanoid shadow looms about, its intentions unclear."),
    SPIRIT_TEMPLAR(32, "Spirit templar", "The spirit of a proud knight has decided to protect this place."),
    TORMENTOR(60, "Tormentor", "A particularly grim person stands here, trying to sort things out."),
    TORTOISE(94, "Tortoise", "The tortoise is pretty harmless but can pinch you quite bad with its bite."),
    TROLL_KING(27, "Troll king", "This troll has a scary clever look in his eyes. He surely knows what he is doing."),
    TROLL(11, "Troll", "A dark green stinking troll. Always hungry. Always deadly."),
    UNICORN(21, "Unicorn", "A bright white unicorn with a slender twisted horn."),
    WHITE_DRAGON_HATCHLING(19, "White dragon hatchling", "The white dragon hatchling is not as large as a full-grown dragon and unable to fly."),
    WHITE_DRAGON(92, "White dragon", "The menacing huge dragon, with snow white scales."),
    WILD_BOAR(37, "Wild boar", "A large and strong boar is grunting here."),
    WILD_CAT(15, "Wild cat", "A small wild cat, fierce and aggressive."),
    WORG(86, "Worg", "This wolf-like creature is unnaturally big and clumsy. The Worg seems finicky and nervous, which makes it unpredictable and dangerous to deal with."),
    WRAITH(88, "Wraith", "The wraith is born of darkness and shuns the daylight."),
    ZOMBIE(69, "Zombie", "A very bleak humanlike creature stands here, looking abscent-minded.");

    private Integer templateId;
    private String gameName;
    private String description;

    CreatureTemplateInfo(Integer templateId, String gameName, String description) {
        this.templateId = templateId;
        this.gameName = gameName;
        this.description = description;
    }

    public CreatureTemplateInfo[] getValues(){
        return values();
    }

    public static Integer getCreatureIdFromGameName(String gameName) {
        if (Objects.equals("none", gameName.toLowerCase()))
            return -1;
        CreatureTemplateInfo cti = Arrays.stream(values())
                .filter(creatureTemplateInfo -> Objects.equals(creatureTemplateInfo.gameName.toLowerCase(), gameName.toLowerCase()))
                .findFirst()
                .orElse(null);
        if (cti == null)
            return null;
        return cti.templateId;
    }

    public String getGameName() {
        return gameName;
    }

    public Integer getTemplateId() {
        return templateId;
    }

    public String getDescription() {
        return description;
    }
}
