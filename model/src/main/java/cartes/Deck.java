package cartes;

import type.ICarte;
import type.IDeck;

import java.util.ArrayList;

public class Deck implements IDeck {

    private ArrayList<ICarte> deck;

    public Deck() {
        deck = new ArrayList<>();
    }

    @Override
    public void ajoutCarteDansDeck(ICarte carte)
    {
        if (deck.size() < 7) {
            deck.add(carte);
        }
    }
    @Override
    public void enleverCarteDuDeck(ICarte carte)
    {
        deck.remove(carte);
    }




    @Override
    public int getSizeDeck()
    {
        return deck.size();
    }
    @Override
    public ICarte getCarteDansDeck(int indice)
    {
        return deck.get(indice);
    }
    @Override
    public void clearDeck()
    {
        deck.clear();
    }

}
