package partie;

import cartes.Carte;
import cartes.Deck;
import cartes.GestionsEffetCarte;
import joueur.Joueur;
import merveilles.GestionsEffetsEtape;
import merveilles.Merveille;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class Partie {

    private int idPartie;
    private static int s = 0;

    private ArrayList<Joueur> listeDesJoueurs;
    private final int NB_JOUEURS = 4;
    private final int NB_CARTES = 88;
    private final int NB_MERVEILLES = 7;

    private int ageEnCours;
    private int tourEnCours;

    private List<Carte> cartes;
    private List<Merveille> merveilles;


    // Differentes listes pour différencier les cartes
    private ArrayList<Carte> cartesAgeI;
    private ArrayList<Carte> cartesAgeII;
    private ArrayList<Carte> cartesAgeIII;
    private ArrayList<Carte> carteDefausse;
    private GestionsEffetCarte gestionsEffetCarte;
    private GestionsEffetsEtape gestionsEffetsEtape;

    private boolean partieTerminee;

    public Partie(ArrayList<Joueur> listeDesJoueurs, List<Carte> cartes, List<Merveille> merveilles)
    {
        this.idPartie = s++;
        this.listeDesJoueurs = listeDesJoueurs;
        this.cartes = cartes;
        this.merveilles = merveilles;
        this.cartesAgeI = null;
        this.cartesAgeII = null;
        this.cartesAgeIII = null;
        this.carteDefausse = null;
        this.gestionsEffetsEtape = null;
        this.gestionsEffetCarte = null;
        this.ageEnCours = 1;
        this.tourEnCours = 1;
        this.gestionsEffetCarte = new GestionsEffetCarte();
        this.gestionsEffetsEtape = new GestionsEffetsEtape();
        this.partieTerminee = false;
    }



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

    public void miseEnPlacePartie()
    {
        this.constructionDesListes();
        this.listeDesJoueurs.forEach( j -> {
            this.cartesAgeI.forEach(c -> {
                j.getDeck().ajoutCarteDansDeck(c);
                this.cartesAgeI.remove(c);
            });

            this.merveilles.forEach(m -> {
                j.setMerveille(m);
                this.merveilles.remove(m);
            });
        });
    }

    public void jouerCarte(Joueur joueur, Carte carte) throws Exception {
        int indice = listeDesJoueurs.indexOf(joueur);
        boolean achatPossible = true;
        int pieceRedevableVoisinGauche = 0; // ces variables permettent d'appliquer l'achat de la carte que apres la verification de tous les conditions possible
        int pieceRedevableVoisinDroite = 0;

        AtomicBoolean carteGratuite = new AtomicBoolean(false);
        joueur.getCartesJouees().forEach(cj -> {
            if(carte.getChainage().containsValue(cj.getNom())){
                carteGratuite.set(true);
            }
        });
        if(!carteGratuite.get())
        {
          if(carte.getCout().containsKey("pieces"))
          {
              joueur.enleverPieces(carte.getCout().get("pieces"));
              joueur.getCartesJouees().add(carte);
              joueur.getDeck().enleverCarteDuDeck(carte);
              gestionsEffetCarte.appliquerEffetCarte(carte.getEffet(),joueur, listeDesJoueurs.get(voisinDeGauche(indice)), listeDesJoueurs.get(voisinDeDroite(indice)));
          }
          else
          {
              for (Map.Entry<String,Integer> entryCarte: carte.getCout().entrySet())
              {
                  String cle = entryCarte.getKey();
                  int cout = entryCarte.getValue();


                  // On va verifier la condition de si il a pas les ressources nécessaire, il va donc verifier chez le voisin
                  if(joueur.getRessources().get(cle) < cout)
                  {
                      // verification voisin de gauche
                      if(listeDesJoueurs.get(voisinDeGauche(indice)).getRessources().get(cle) + joueur.getRessources().get(cle) > cout)
                      {
                          // verification si il a des batiments commerciale qui lui donne un avantage pour l'achat
                          if(joueur.isCommerceMatieresPremieresGauche() && joueur.isCommerceProduitsManufactures()) //TODO a amilioré !
                          {
                              pieceRedevableVoisinGauche += cout - joueur.getRessources().get(cle);
                          }
                          else
                          {
                              pieceRedevableVoisinGauche +=(cout - joueur.getRessources().get(cle)) * 2;
                          }

                      }
                      else if (listeDesJoueurs.get(voisinDeDroite(indice)).getRessources().get(cle) + joueur.getRessources().get(cle) > cout)
                      {
                          // verification si il a des batiments commerciale qui lui donne un avantage pour l'achat
                          if(joueur.isCommerceMatieresPremieresDroite() && joueur.isCommerceProduitsManufactures()) //TODO a amilioré !
                          {
                              pieceRedevableVoisinDroite += cout - joueur.getRessources().get(cle);
                          }
                          else
                          {
                              pieceRedevableVoisinDroite +=(cout - joueur.getRessources().get(cle)) * 2;
                          }
                      }
                      else
                      {
                          achatPossible = false;
                      }
                  }
              }
              if(achatPossible)
              {
                  listeDesJoueurs.get(voisinDeGauche(indice)).addPieces(pieceRedevableVoisinGauche);
                  listeDesJoueurs.get(voisinDeDroite(indice)).addPieces(pieceRedevableVoisinDroite);
                  joueur.enleverPieces(pieceRedevableVoisinGauche + pieceRedevableVoisinDroite);
                  joueur.getCartesJouees().add(carte);
                  joueur.getDeck().enleverCarteDuDeck(carte);
                  gestionsEffetCarte.appliquerEffetCarte(carte.getEffet(),joueur, listeDesJoueurs.get(voisinDeGauche(indice)), listeDesJoueurs.get(voisinDeDroite(indice)));
              }
              else
              {
                  // Notif si il clique sur acheter ca doit lui afficher qu'il ne peut pas !
              }
          }
        }
        joueur.setAJoue(true);
        suitePartie();
    }

    public void deffausserCarteFinAge()
    {
        for (Joueur joueur: listeDesJoueurs) {
            for (int i =0; i< joueur.getDeck().getSizeDeck(); i++)
            {
                carteDefausse.add(joueur.getDeck().getCarteDansDeck(i));
                joueur.getDeck().clearDeck();
            }
        }

    }
    public void deffausserCarte(Joueur joueur, Carte carte) throws Exception {
        joueur.getDeck().enleverCarteDuDeck(carte);
        carteDefausse.add(carte);
        joueur.addPieces(3);
        suitePartie();
    }

    public void construireEtape(Joueur p) throws Exception {
        this.gestionsEffetsEtape.appliquerEffetMerveille(p);
        p.getMerveille().setEtape(p.getMerveille().getEtape()+1);  //on incrémente le num de l'étape de la merveille
        suitePartie();
    }

    public void passerAuTourSuivant()
    {
        tourEnCours +=1;
        Deck deck = new Deck();
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
                else {
                    listeDesJoueurs.get(j).setDeck(listeDesJoueurs.get(NB_JOUEURS-1).getDeck());
                }
            }
        }
        else
        {
            Deck deck1 = new Deck();
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

    public void passerAgeSuivant()
    {
        ageEnCours +=1;
        if (ageEnCours == 2)
        {
            this.listeDesJoueurs.forEach( j -> {
                this.cartesAgeII.forEach(c -> {
                    j.getDeck().ajoutCarteDansDeck(c);
                    this.cartesAgeII.remove(c);
                });
            });
        }
        else {
            this.listeDesJoueurs.forEach( j -> {
                this.cartesAgeIII.forEach(c -> {
                    j.getDeck().ajoutCarteDansDeck(c);
                    this.cartesAgeIII.remove(c);
                });
            });
        }
    }

    public int voisinDeDroite(int indice)
    {
        if (indice == NB_JOUEURS-1)
            return 0;
        return indice+1;
    }
    public int voisinDeGauche(int indice)
    {
        if (indice == 0)
            return NB_JOUEURS - 1;
        return indice-1;
    }

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

    public boolean finAge()
    {
        if(tourEnCours == 6 && toutLeMondeAJoue())
        {
            return true;
        }
        return false;
    }

    public boolean toutLeMondeAJoue()
    {
        for (Joueur joueur: listeDesJoueurs) {
            if(!joueur.getAJoue())
            {
                return false;
            }
        }
        return true;
    }
    public boolean finDernierTourDernierAge()
    {
        if (ageEnCours == 3 && tourEnCours == 6 && toutLeMondeAJoue())
        {
            return true;
        }
        return false;
    }
    public void partieTerminee() throws Exception {
        if (partieTerminee)
        {
            ajoutPointVictoireEnFinPartie();
            // ici on termine la partie
        }

    }

    public void suitePartie() throws Exception {

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
    public void comptagePointVictoirePourBatimentScientifique(Joueur joueur)
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
    public void ajoutPointVictoireEnFinPartie() throws Exception
    {
        if (finDernierTourDernierAge())
        {
            for (Joueur joueur: listeDesJoueurs) {
                comptagePointVictoirePourBatimentScientifique(joueur);
                ajoutPointVictoireDuTresor(joueur);
                ajoutPointVictoireConflitsMilitaire(joueur);
                int indice = listeDesJoueurs.indexOf(joueur);
                for (Carte carte: joueur.getCartesJouees()) {
                    if (carte.getType().equals("Guilde"))
                    {
                        gestionsEffetCarte.appliquerEffetGuildesFinDePartie(carte.getEffet(),joueur,listeDesJoueurs.get(voisinDeGauche(indice)), listeDesJoueurs.get(voisinDeDroite(indice)));
                    }
                }
            }
        }
    }
    public String affichageDesScores()
    {

        return "tata";
    }


    public int getIdPartie() {
        return idPartie;
    }

    public void ajoutPointVictoireDuTresor(Joueur joueur)
    {
        joueur.addPtsVictoire(joueur.getPieces()/3);
    }

    public void ajoutPointVictoireConflitsMilitaire(Joueur joueur)
    {
        joueur.addPtsVictoire(joueur.getPtsVictoireMilitaire() - joueur.getNbJetonsDefaite());
    }



    public ArrayList<Joueur> getListeDesJoueurs() {
        return listeDesJoueurs;
    }

    public void setListeDesJoueurs(ArrayList<Joueur> listeDesJoueurs) {
        this.listeDesJoueurs = listeDesJoueurs;
    }

    public int getNB_JOUEURS() {
        return NB_JOUEURS;
    }

    public int getNB_CARTES() {
        return NB_CARTES;
    }

    public int getNB_MERVEILLES() {
        return NB_MERVEILLES;
    }


    public ArrayList<Carte> getCarteDefausse() {
        return carteDefausse;
    }

    public void setCarteDefausse(ArrayList<Carte> carteDefausse) {
        this.carteDefausse = carteDefausse;
    }


    public ArrayList<Carte> getCartesAgeI() {
        return cartesAgeI;
    }

    public void setCartesAgeI(ArrayList<Carte> cartesAgeI) {
        this.cartesAgeI = cartesAgeI;
    }

    public ArrayList<Carte> getCartesAgeII() {
        return cartesAgeII;
    }

    public void setCartesAgeII(ArrayList<Carte> cartesAgeII) {
        this.cartesAgeII = cartesAgeII;
    }

    public ArrayList<Carte> getCartesAgeIII() {
        return cartesAgeIII;
    }

    public void setCartesAgeIII(ArrayList<Carte> cartesAgeIII) {
        this.cartesAgeIII = cartesAgeIII;
    }

    public List<Carte> getCartes() {
        return cartes;
    }

    public void setCartes(ArrayList<Carte> cartes) {
        this.cartes = cartes;
    }

    public List<Merveille> getMerveilles() {
        return merveilles;
    }
}
