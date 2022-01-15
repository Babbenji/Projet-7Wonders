package type;


import java.util.ArrayList;

public interface IDeck {
    void ajoutCarteDansDeck(ICarte carte);

    void enleverCarteDuDeck(ICarte carte);

    int getSizeDeck();

    ICarte getCarteDansDeck(int indice);

    void clearDeck();
    ArrayList<ICarte>getDeck();
}
