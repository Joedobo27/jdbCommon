package com.joedobo27.libs;

@SuppressWarnings("unused")
public enum RarityTypes {
    RARITY_NONE(0),
    RARITY_RARE(1),
    RARITY_SUPREME(2),
    RARITY_FANTASTIC(3);

    /*
    Player getRarity()
    supreme 1 in 33.334 chance ... 3/100=33.334 for Paying. 1 in 100 chance for F2P
    fantastic 1 in 9708.737 chance ... 1.03f/10,000; 103 in 1,000,000 for Paying. 1 in 10,000 chance for F2P.

    improvement has a 1 in 5 chance to go rare if: the power/success of action is > 0, and the action's rarity
    is greater then the rarity of the item.
    */
    private final int id;

    RarityTypes(int id){
        this.id = id;
    }

    public byte getId() {
        return (byte)id;
    }
}
