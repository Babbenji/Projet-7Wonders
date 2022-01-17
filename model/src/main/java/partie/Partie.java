package partie;

import cartes.*;
import merveilles.exceptions.MaximumEtapeAtteintException;
import partie.exceptions.ChoixDejaFaitException;
import partie.exceptions.PasAssezDeRessourcesException;
import type.ICarte;
import type.IDeck;
import type.IJoueur;
import type.IMerveille;
import merveilles.GestionsEffetsEtape;
import type.IPartie;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class Partie implements IPartie, Serializable {

    private int idPartie;
    private static int s = 0;

    private ArrayList<IJoueur> listeDesJoueurs;
    private final int NB_JOUEURS = 4;
    private final int NB_CARTES = 88;
    private final int NB_MERVEILLES = 7;

    private int ageEnCours;
    private int tourEnCours;

    private List<ICarte> cartes;
    private List<IMerveille> merveilles;


    // Differentes listes pour différencier les cartes
    private ArrayList<ICarte> cartesAgeI;
    private ArrayList<ICarte> cartesAgeII;
    private ArrayList<ICarte> cartesAgeIII;
    private ArrayList<ICarte> carteDefausse;
    private GestionsEffetCarte gestionsEffetCarte;
    private GestionsEffetsEtape gestionsEffetsEtape;

    private boolean partieTerminee;

    public Partie(ArrayList<IJoueur> listeDesJoueurs, List<ICarte> cartes, List<IMerveille> merveilles)
    {
        this.idPartie = s++;
        this.listeDesJoueurs = listeDesJoueurs;
        this.cartes = cartes;
        this.merveilles = merveilles;
        this.cartesAgeI = new ArrayList<>();
        this.cartesAgeII = new ArrayList<>();
        this.cartesAgeIII = new ArrayList<>();
        this.carteDefausse = new ArrayList<>();
        this.gestionsEffetsEtape = null;
        this.gestionsEffetCarte = null;
        this.ageEnCours = 1;
        this.tourEnCours = 1;
        this.gestionsEffetCarte = new GestionsEffetCarte();
        this.gestionsEffetsEtape = new GestionsEffetsEtape();
        this.partieTerminee = false;
    }


    @Override
    public void constructionDesListes()
    {
        Collections.shuffle(this.merveilles);

        this.cartes.forEach(c -> {
            if (c.getAge() == 1){
                this.cartesAgeI.add(c);
            }else if (c.getAge() == 2){
                this.cartesAgeII.add(c);
            }else{
                this.cartesAgeIII.add(c);
            }});

        Collections.shuffle(this.cartesAgeI);
        Collections.shuffle(this.cartesAgeII);
        Collections.shuffle(this.cartesAgeIII);

    }


    @Override
    public void miseEnPlacePartie()
    {
        this.constructionDesListes();
        Iterator<ICarte> iter = this.cartesAgeI.iterator();
        Iterator<IMerveille> itera = this.merveilles.iterator();
        this.listeDesJoueurs.forEach( j -> {
            j.setMerveille(itera.next());
        });
        this.listeDesJoueurs.forEach( j -> {
            while (j.getDeck().getSizeDeck() < 7)
            {
                j.getDeck().ajoutCarteDansDeck(iter.next());
                iter.remove();
            }
        });
    }

    @Override
    public void jouerCarte(IJoueur joueur, ICarte carte) throws ChoixDejaFaitException, PasAssezDeRessourcesException
    {
        if(joueur.getAJoue()){
            throw new ChoixDejaFaitException();
        }
        else {

            int indice = listeDesJoueurs.indexOf(joueur);
            boolean achatPossible = true;
            int pieceRedevableVoisinGauche = 0; // ces variables permettent d'appliquer l'achat de la carte que apres la verification de tous les conditions possible
            int pieceRedevableVoisinDroite = 0;

            AtomicBoolean carteGratuite = new AtomicBoolean(false);
            joueur.getCartesJouees().forEach(cj -> {

                if (carte.getChainage() == null) {

                } else if (carte.getChainage().containsKey(cj.getNom())) {
                    carteGratuite.set(true);
                }
            });

            if (carte.getCout().isEmpty()) {
                carteGratuite.set(true);
            }

            if (!carteGratuite.get()) {
                if (carte.getCout().containsKey("pieces")) {
                    joueur.enleverPieces(carte.getCout().get("pieces"));
                    joueur.getCartesJouees().add(carte);
                    joueur.getDeck().enleverCarteDuDeck(carte);
                    gestionsEffetCarte.appliquerEffetCarte(carte.getEffet(), joueur, listeDesJoueurs.get(voisinDeGauche(indice)), listeDesJoueurs.get(voisinDeDroite(indice)));
                } else {
                    for (Map.Entry<String, Integer> entryCarte : carte.getCout().entrySet()) {
                        String cle = entryCarte.getKey();
                        int cout = entryCarte.getValue();


                        // On va verifier la condition de si il a pas les ressources nécessaire, il va donc verifier chez le voisin
                        if (joueur.getRessources().get(cle) < cout) {
                            // verification voisin de gauche
                            if (listeDesJoueurs.get(voisinDeGauche(indice)).getRessources().get(cle) + joueur.getRessources().get(cle) > cout) {
                                // verification si il a des batiments commerciale qui lui donne un avantage pour l'achat
                                if (joueur.isCommerceMatieresPremieresGauche() && joueur.isCommerceProduitsManufactures()) //TODO a amilioré !
                                {
                                    pieceRedevableVoisinGauche += cout - joueur.getRessources().get(cle);
                                } else {
                                    pieceRedevableVoisinGauche += (cout - joueur.getRessources().get(cle)) * 2;
                                }

                            } else if (listeDesJoueurs.get(voisinDeDroite(indice)).getRessources().get(cle) + joueur.getRessources().get(cle) > cout) {
                                // verification si il a des batiments commerciale qui lui donne un avantage pour l'achat
                                if (joueur.isCommerceMatieresPremieresDroite() && joueur.isCommerceProduitsManufactures()) //TODO a amilioré !
                                {
                                    pieceRedevableVoisinDroite += cout - joueur.getRessources().get(cle);
                                } else {
                                    pieceRedevableVoisinDroite += (cout - joueur.getRessources().get(cle)) * 2;
                                }
                            } else {
                                achatPossible = false;
                            }
                        }
                    }
                }
            }
            if (achatPossible) {
                listeDesJoueurs.get(voisinDeGauche(indice)).addPieces(pieceRedevableVoisinGauche);
                listeDesJoueurs.get(voisinDeDroite(indice)).addPieces(pieceRedevableVoisinDroite);
                joueur.enleverPieces(pieceRedevableVoisinGauche + pieceRedevableVoisinDroite);
                joueur.getCartesJouees().add(carte);
                joueur.getDeck().enleverCarteDuDeck(carte);
                gestionsEffetCarte.appliquerEffetCarte(carte.getEffet(), joueur, listeDesJoueurs.get(voisinDeGauche(indice)), listeDesJoueurs.get(voisinDeDroite(indice)));
            } else {
                throw new PasAssezDeRessourcesException();
            }
            joueur.setAJoue(true);
        }
    }

    @Override
    public void deffausserCarteFinAge()
    {
        for (IJoueur joueur: listeDesJoueurs) {
            for (int i =0; i< joueur.getDeck().getSizeDeck(); i++)
            {
                carteDefausse.add(joueur.getDeck().getCarteDansDeck(i));
            }
            joueur.getDeck().clearDeck();
        }
    }
    @Override
    public void deffausserCarte(IJoueur joueur, ICarte carte) throws ChoixDejaFaitException {
        if(joueur.getAJoue()){
            throw new ChoixDejaFaitException();
        }
        else {
            joueur.getDeck().enleverCarteDuDeck(carte);
            carteDefausse.add(carte);
            joueur.addPieces(3);
            joueur.setAJoue(true);
        }
    }

    @Override
    public void construireEtape(IJoueur joueur, ICarte carte) throws ChoixDejaFaitException, PasAssezDeRessourcesException, MaximumEtapeAtteintException
    {
        if(joueur.getAJoue()){
            throw new ChoixDejaFaitException();
        }
        else
        {
            int indice = listeDesJoueurs.indexOf(joueur);
            boolean achatPossible = true;
            int pieceRedevableVoisinGauche = 0; // ces variables permettent d'appliquer l'achat de la carte que apres la verification de tous les conditions possible
            int pieceRedevableVoisinDroite = 0;
            Map<String, Integer> ressourceEtapeSuivante = joueur.getMerveille().getRessourceEtapeSuivante();
            if (joueur.getMerveille().getEtape() == 3) {
                throw new MaximumEtapeAtteintException();
            }
            for (HashMap.Entry<String, Integer> entryRessource : ressourceEtapeSuivante.entrySet()) {
                String cle = entryRessource.getKey();
                int cout = entryRessource.getValue();

                // On va verifier la condition de si il a pas les ressources nécessaire, il va donc verifier chez le voisin
                if (joueur.getRessources().get(cle) < cout) {
                    // verification voisin de gauche
                    if (listeDesJoueurs.get(voisinDeGauche(indice)).getRessources().get(cle) + joueur.getRessources().get(cle) > cout) {
                        // verification si il a des batiments commerciale qui lui donne un avantage pour l'achat
                        if (joueur.isCommerceMatieresPremieresGauche() && joueur.isCommerceProduitsManufactures()) //TODO a amilioré !
                        {
                            pieceRedevableVoisinGauche += cout - joueur.getRessources().get(cle);
                        } else {
                            pieceRedevableVoisinGauche += (cout - joueur.getRessources().get(cle)) * 2;
                        }

                    } else if (listeDesJoueurs.get(voisinDeDroite(indice)).getRessources().get(cle) + joueur.getRessources().get(cle) > cout) {
                        // verification si il a des batiments commerciale qui lui donne un avantage pour l'achat
                        if (joueur.isCommerceMatieresPremieresDroite() && joueur.isCommerceProduitsManufactures()) //TODO a amilioré !
                        {
                            pieceRedevableVoisinDroite += cout - joueur.getRessources().get(cle);
                        } else {
                            pieceRedevableVoisinDroite += (cout - joueur.getRessources().get(cle)) * 2;
                        }
                    } else {
                        achatPossible = false;
                    }
                }
                if (achatPossible) {
                    listeDesJoueurs.get(voisinDeGauche(indice)).addPieces(pieceRedevableVoisinGauche);
                    listeDesJoueurs.get(voisinDeDroite(indice)).addPieces(pieceRedevableVoisinDroite);
                    joueur.enleverPieces(pieceRedevableVoisinGauche + pieceRedevableVoisinDroite);
                    joueur.getCartesJouees().add(carte);
                    joueur.getDeck().enleverCarteDuDeck(carte);
                    gestionsEffetCarte.appliquerEffetCarte(carte.getEffet(), joueur, listeDesJoueurs.get(voisinDeGauche(indice)), listeDesJoueurs.get(voisinDeDroite(indice)));
                } else {
                    throw new PasAssezDeRessourcesException();
                }
                joueur.setAJoue(true);
            }

            this.gestionsEffetsEtape.appliquerEffetMerveille(joueur);
            joueur.getMerveille().setEtape(joueur.getMerveille().getEtape() + 1);  //on incrémente le num de l'étape de la merveille
            carteDefausse.add(carte);
            joueur.getDeck().enleverCarteDuDeck(carte);
        }
    }


    @Override
    public void passerAuTourSuivant()
    {
        tourEnCours +=1;
        IDeck deck = new Deck();
        if(ageEnCours == 1 || ageEnCours == 3)
        {
            for (int j = NB_JOUEURS-1; j>= 0; j--)
            {
                if (j == NB_JOUEURS-1){
                    deck = listeDesJoueurs.get(j).getDeck();
                    listeDesJoueurs.get(j).setDeck(listeDesJoueurs.get(j-1).getDeck());
                }
                else if (j == 0)
                {
                    listeDesJoueurs.get(j).setDeck(deck);
                }
                else
                {
                    listeDesJoueurs.get(j).setDeck(listeDesJoueurs.get(j-1).getDeck());
                }
            }
        }
        else
        {
            IDeck deck1 = new Deck();
            for (int i = 0; i<NB_JOUEURS; i++)
            {
                if (i < NB_JOUEURS-1){
                    if (i==0)
                    {
                        deck1 = listeDesJoueurs.get(i).getDeck();
                        listeDesJoueurs.get(i).setDeck(listeDesJoueurs.get(i+1).getDeck());
                    }
                    listeDesJoueurs.get(i).setDeck(listeDesJoueurs.get(i+1).getDeck());
                }
                else {
                    listeDesJoueurs.get(i).setDeck(deck1);
                }
            }
        }
    }

    @Override
    public void passerAgeSuivant() {
        deffausserCarteFinAge();
        ageEnCours +=1;
        Iterator<ICarte> iter2 = this.cartesAgeII.iterator();
        Iterator<ICarte> iter3 = this.cartesAgeIII.iterator();
        if (ageEnCours == 2)
        {
            this.listeDesJoueurs.forEach( j -> {

                j.getDeck().ajoutCarteDansDeck(iter2.next());
                iter2.remove();

            });
        }
        else {
            this.listeDesJoueurs.forEach( j -> {
                j.getDeck().ajoutCarteDansDeck(iter3.next());
                iter3.remove();
            });
        }
    }

    @Override
    public int voisinDeDroite(int indice)
    {
        if (indice == NB_JOUEURS-1)
            return 0;
        return indice+1;
    }
    @Override
    public int voisinDeGauche(int indice)
    {
        if (indice == 0)
            return NB_JOUEURS - 1;
        return indice-1;
    }

    @Override
    public void conflitsMilitaire()
    {
        for (int i =0 ; i< NB_JOUEURS; i++)
        {
            if (ageEnCours == 1)
            {
                // Bataille militaire avec le voisin de gauche et le voisin de droite pour l'age 1
                if(listeDesJoueurs.get(voisinDeGauche(i)).getPuissanceMilitaire() > listeDesJoueurs.get(i).getPuissanceMilitaire())
                {
                    listeDesJoueurs.get(voisinDeGauche(i)).addPointsVictoireMilitaire(1);
                    listeDesJoueurs.get(i).addJetonsDefaiteMilitaire(1);

                }
                else if (listeDesJoueurs.get(voisinDeGauche(i)).getPuissanceMilitaire() < listeDesJoueurs.get(i).getPuissanceMilitaire())
                {
                    listeDesJoueurs.get(i).addPointsVictoireMilitaire(1);
                    listeDesJoueurs.get(voisinDeGauche(i)).addJetonsDefaiteMilitaire(1);
                }
                if(listeDesJoueurs.get(voisinDeDroite(i)).getPuissanceMilitaire() > listeDesJoueurs.get(i).getPuissanceMilitaire())
                {
                    listeDesJoueurs.get(voisinDeDroite(i)).addPointsVictoireMilitaire(1);
                    listeDesJoueurs.get(i).addJetonsDefaiteMilitaire(1);
                }
                else if (listeDesJoueurs.get(voisinDeDroite(i)).getPuissanceMilitaire() < listeDesJoueurs.get(i).getPuissanceMilitaire())
                {
                    listeDesJoueurs.get(i).addPointsVictoireMilitaire(1);
                    listeDesJoueurs.get(voisinDeDroite(i)).addJetonsDefaiteMilitaire(1);
                }
            }
            else if(ageEnCours == 2)
            {
                if(listeDesJoueurs.get(voisinDeGauche(i)).getPuissanceMilitaire() > listeDesJoueurs.get(i).getPuissanceMilitaire())
                {
                    listeDesJoueurs.get(voisinDeGauche(i)).addPointsVictoireMilitaire(3);
                    listeDesJoueurs.get(i).addJetonsDefaiteMilitaire(3);

                }
                else if (listeDesJoueurs.get(voisinDeGauche(i)).getPuissanceMilitaire() < listeDesJoueurs.get(i).getPuissanceMilitaire())
                {
                    listeDesJoueurs.get(i).addPointsVictoireMilitaire(3);
                    listeDesJoueurs.get(voisinDeGauche(i)).addJetonsDefaiteMilitaire(3);
                }
                if(listeDesJoueurs.get(voisinDeDroite(i)).getPuissanceMilitaire() > listeDesJoueurs.get(i).getPuissanceMilitaire())
                {
                    listeDesJoueurs.get(voisinDeDroite(i)).addPointsVictoireMilitaire(3);
                    listeDesJoueurs.get(i).addJetonsDefaiteMilitaire(3);
                }
                else if (listeDesJoueurs.get(voisinDeDroite(i)).getPuissanceMilitaire() < listeDesJoueurs.get(i).getPuissanceMilitaire())
                {
                    listeDesJoueurs.get(i).addPointsVictoireMilitaire(3);
                    listeDesJoueurs.get(voisinDeDroite(i)).addJetonsDefaiteMilitaire(3);
                }
            }
            else
            {
                if(listeDesJoueurs.get(voisinDeGauche(i)).getPuissanceMilitaire() > listeDesJoueurs.get(i).getPuissanceMilitaire())
                {
                    listeDesJoueurs.get(voisinDeGauche(i)).addPointsVictoireMilitaire(5);
                    listeDesJoueurs.get(i).addJetonsDefaiteMilitaire(5);

                }
                else if (listeDesJoueurs.get(voisinDeGauche(i)).getPuissanceMilitaire() < listeDesJoueurs.get(i).getPuissanceMilitaire())
                {
                    listeDesJoueurs.get(i).addPointsVictoireMilitaire(5);
                    listeDesJoueurs.get(voisinDeGauche(i)).addJetonsDefaiteMilitaire(5);
                }
                if(listeDesJoueurs.get(voisinDeDroite(i)).getPuissanceMilitaire() > listeDesJoueurs.get(i).getPuissanceMilitaire())
                {
                    listeDesJoueurs.get(voisinDeDroite(i)).addPointsVictoireMilitaire(5);
                    listeDesJoueurs.get(i).addJetonsDefaiteMilitaire(5);
                }
                else if (listeDesJoueurs.get(voisinDeDroite(i)).getPuissanceMilitaire() < listeDesJoueurs.get(i).getPuissanceMilitaire())
                {
                    listeDesJoueurs.get(i).addPointsVictoireMilitaire(5);
                    listeDesJoueurs.get(voisinDeDroite(i)).addJetonsDefaiteMilitaire(5);
                }
            }
        }
    }

    @Override
    public boolean finAge()
    {
        if(tourEnCours == 6 && toutLeMondeAJoue())
        {
            return true;
        }
        return false;
    }

    @Override
    public boolean toutLeMondeAJoue()
    {
        for (IJoueur joueur: listeDesJoueurs) {
            if(!joueur.getAJoue())
            {
                return false;
            }
        }
        return true;
    }
    @Override
    public boolean finDernierTourDernierAge()
    {
        if (ageEnCours == 3 && tourEnCours == 6 && toutLeMondeAJoue())
        {
            return true;
        }
        return false;
    }
    @Override
    public void partieTerminee() throws Exception {
        if (partieTerminee)
        {
            ajoutPointVictoireEnFinPartie();
            // ici on termine la partie
        }

    }
    @Override
    public void suitePartie() {

        if(!finDernierTourDernierAge())
        {
            if (finAge())
            {
                conflitsMilitaire();
                deffausserCarteFinAge();
                passerAgeSuivant();
            }

            if (toutLeMondeAJoue())
            {
                passerAuTourSuivant();
            }
        }
        else {
            conflitsMilitaire();
            deffausserCarteFinAge();
            partieTerminee = true;
        }

        // on arrete la partie ici
    }


    @Override
    public void comptagePointVictoirePourBatimentScientifique(IJoueur joueur)
    {
        joueur.addPtsVictoire(joueur.getNbRouages() * joueur.getNbRouages());
        joueur.addPtsVictoire(joueur.getNbCompas() * joueur.getNbCompas());
        joueur.addPtsVictoire(joueur.getNbTablettes() * joueur.getNbTablettes());

        int nbRouage = joueur.getNbRouages();
        int nbCompas = joueur.getNbCompas();
        int nbTablette = joueur.getNbTablettes();
        int lotSymbole = 0;

        while (nbRouage == 0 || nbCompas == 0 || nbTablette == 0 )
        {
            lotSymbole += 1;
            nbRouage -=1;
            nbCompas -=1;
            nbTablette -=1;
        }
        joueur.addPtsVictoire(lotSymbole*7);
    }
    @Override
    public void ajoutPointVictoireEnFinPartie() throws Exception
    {
        if (finDernierTourDernierAge())
        {
            for (IJoueur joueur: listeDesJoueurs) {
                comptagePointVictoirePourBatimentScientifique(joueur);
                ajoutPointVictoireDuTresor(joueur);
                ajoutPointVictoireConflitsMilitaire(joueur);
                int indice = listeDesJoueurs.indexOf(joueur);
                for (ICarte carte: joueur.getCartesJouees()) {
                    if (carte.getType().equals("Guilde"))
                    {
                        gestionsEffetCarte.appliquerEffetGuildesFinDePartie(carte.getEffet(),joueur,listeDesJoueurs.get(voisinDeGauche(indice)), listeDesJoueurs.get(voisinDeDroite(indice)));
                    }
                }
            }
        }
    }
    @Override
    public String affichageDesScores()
    {

        return "tata";
    }

    public IMerveille getMerveille(IJoueur joueur)
    {
        int indice = listeDesJoueurs.indexOf(joueur);
        return listeDesJoueurs.get(indice).getMerveille();
    }


    @Override
    public int getIdPartie() {
        return idPartie;
    }

    @Override
    public void ajoutPointVictoireDuTresor(IJoueur joueur)
    {
        joueur.addPtsVictoire(joueur.getPieces()/3);
    }

    @Override
    public void ajoutPointVictoireConflitsMilitaire(IJoueur joueur)
    {
        joueur.addPtsVictoire(joueur.getPtsVictoireMilitaire() - joueur.getNbJetonsDefaite());
    }


    public IMerveille iMerveille(IJoueur joueur)
    {
        int indice = listeDesJoueurs.indexOf(joueur);
        return listeDesJoueurs.get(indice).getMerveille();
    }
    public IDeck getDeck(IJoueur joueur)
    {
        int indice = listeDesJoueurs.indexOf(joueur);
        return listeDesJoueurs.get(indice).getDeck();
    }

    @Override
    public ArrayList<IJoueur> getListeDesJoueurs() {
        return listeDesJoueurs;
    }

    @Override
    public void setListeDesJoueurs(ArrayList<IJoueur> listeDesJoueurs) {
        this.listeDesJoueurs = listeDesJoueurs;
    }

    @Override
    public void addJoueur(IJoueur joueur){
        this.listeDesJoueurs.add(joueur);
    }

    @Override
    public int getNB_JOUEURS() {
        return NB_JOUEURS;
    }

    @Override
    public int getNB_CARTES() {
        return NB_CARTES;
    }

    @Override
    public int getNB_MERVEILLES() {
        return NB_MERVEILLES;
    }


    @Override
    public ArrayList<ICarte> getCarteDefausse() {
        return carteDefausse;
    }

    @Override
    public void setCarteDefausse(ArrayList<ICarte> carteDefausse) {
        this.carteDefausse = carteDefausse;
    }


    @Override
    public ArrayList<ICarte> getCartesAgeI() {
        return cartesAgeI;
    }

    @Override
    public void setCartesAgeI(ArrayList<ICarte> cartesAgeI) {
        this.cartesAgeI = cartesAgeI;
    }

    @Override
    public ArrayList<ICarte> getCartesAgeII() {
        return cartesAgeII;
    }

    @Override
    public void setCartesAgeII(ArrayList<ICarte> cartesAgeII) {
        this.cartesAgeII = cartesAgeII;
    }

    @Override
    public ArrayList<ICarte> getCartesAgeIII() {
        return cartesAgeIII;
    }

    @Override
    public void setCartesAgeIII(ArrayList<ICarte> cartesAgeIII) {
        this.cartesAgeIII = cartesAgeIII;
    }

    @Override
    public List<ICarte> getCartes() {
        return cartes;
    }

    @Override
    public void setCartes(ArrayList<ICarte> cartes) {
        this.cartes = cartes;
    }

    @Override
    public List<IMerveille> getMerveilles() {
        return merveilles;
    }

    @Override
    public int getAgeEnCours(){ return ageEnCours;}
}
