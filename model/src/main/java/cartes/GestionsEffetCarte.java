package cartes;

import interfaces.type.IJoueur;
import joueur.Joueur;

import java.util.HashMap;
import java.util.Map;

public class GestionsEffetCarte {

    public void appliquerEffetCarte (Map<String,String> effet, IJoueur joueur, IJoueur voisinGauche , IJoueur voisinDroite) throws Exception
    {
        if (effet.get("nomEffet") == null)
        {
            throw new Exception(" Il y'a un soucis cette carte n'a pas d effet");
        }

        else

        {
            if(effet.get("nomEffet").equals("gain_pointsVictoire"))
            {
                joueur.addPtsVictoire(Integer.parseInt(effet.get("valeurEffet")));
            }
            if(effet.get("nomEffet").equals("gain_pointsBouclier"))
            {
                joueur.addPuissanceMilitaire(Integer.parseInt(effet.get("valeurEffet")));
            }
            if(effet.get("nomEffet").equals("gain_pieces"))
            {
                joueur.addPieces(Integer.parseInt(effet.get("valeurEffet")));
            }
            if (effet.get("nomEffet").equals("commerceRessourcesPrimaires") && effet.get("orientationEffet").equals("droite"))
            {
                joueur.setCommerceMatieresPremieresDroite(true);
            }
            if (effet.get("nomEffet").equals("commerceRessourcesPrimaires") && effet.get("orientationEffet").equals("gauche"))
            {
                joueur.setCommerceMatieresPremieresDroite(true);
            }
            if (effet.get("nomEffet").equals("commerceRessourcesSecondaires"))
            {
                joueur.setCommerceMatieresPremieresDroite(true);
            }
            if (effet.get("nomEffet").equals("gain_ressources"))
            {
                joueur.augmenterRessource(effet.get("ressourceEffet"),Integer.parseInt(effet.get("valeurEffet")));
            }

            if (effet.get("nomEffet").equals("gain_ressources_multiples"))
            {
                joueur.augmenterPlusieursRess(Integer.parseInt(effet.get("valeurEffet")), effet.get("ressourceEffet1"),effet.get("ressourceEffet2"));
            }
            if (effet.get("nomEffet").equals("gain_symboles") && effet.get("symboleEffet").equals("tablette"))
            {
                joueur.addTablettes(1);
            }
            if (effet.get("nomEffet").equals("gain_symboles") && effet.get("symboleEffet").equals("science"))
            {
                joueur.addCompas(1);
            }
            if (effet.get("nomEffet").equals("gain_symboles") && effet.get("symboleEffet").equals("ingenieur"))
            {
                joueur.addRouages(1);
            }

            if (effet.get("nomEffet").equals("bazar"))
            {
                joueur.addPieces(2*(joueur.getNbreCarteProduitManufacture() + voisinDroite.getNbreCarteProduitManufacture() + voisinGauche.getNbreCarteProduitManufacture()));
            }
            if (effet.get("nomEffet").equals("vignoble"))
            {
                joueur.addPieces(joueur.getNbreCarteMatierePremiere() + voisinDroite.getNbreCarteMatierePremiere()+voisinGauche.getNbreCarteMatierePremiere());
            }
            if (effet.get("nomEffet").equals("forum"))
            {
                // TODO (Probleme Ou a regle avant)
                joueur.addPtsVictoire(joueur.getNbreCarteMatierePremiere());
                joueur.addPieces(joueur.getNbreCarteMatierePremiere());
            }
            if (effet.get("nomEffet").equals("caravanserail"))
            {
                //TODO joueur.augmenterPlusieursRess(""); probelme OU a regle avant
                joueur.addPtsVictoire(joueur.getNbreCarteBatimentCommercial());
                joueur.addPieces(joueur.getNbreCarteBatimentCommercial());
            }
            if (effet.get("nomEffet").equals("port"))
            {
                joueur.addPtsVictoire(joueur.getNbreCarteMatierePremiere());
                joueur.addPieces(joueur.getNbreCarteMatierePremiere());
            }
            if (effet.get("nomEffet").equals("phare"))
            {
                joueur.addPtsVictoire(joueur.getNbreCarteBatimentCommercial());
                joueur.addPieces(joueur.getNbreCarteBatimentCommercial());
            }
            if (effet.get("nomEffet").equals("chambreCommerce"))
            {
                joueur.addPtsVictoire(joueur.getNbreCarteProduitManufacture() * 2);
                joueur.addPieces(joueur.getNbreCarteProduitManufacture() * 2);
            }
            if (effet.get("nomEffet").equals("arene"))
            {
                joueur.addPtsVictoire(joueur.getMerveille().getEtape());
                joueur.addPieces(joueur.getMerveille().getEtape() * 3);
            }
        }
    }
    public void appliquerEffetGuildesFinDePartie(Map<String,String> effet, IJoueur joueur,IJoueur voisinGauche , IJoueur voisinDroite) throws Exception {
        if (effet.get("nomEffet") == null)
        {
            throw new Exception(" Il y'a un soucis cette carte n'a pas d effet");
        }
        else
        {
            if (effet.get("nomEffet").equals("artisans"))
            {
                joueur.addPtsVictoire((voisinDroite.getNbreCarteProduitManufacture() + voisinGauche.getNbreCarteProduitManufacture()) * 2);
            }
            if (effet.get("nomEffet").equals("travailleurs"))
            {
                joueur.addPtsVictoire(voisinDroite.getNbreCarteMatierePremiere() + voisinGauche.getNbreCarteMatierePremiere());
            }
            if (effet.get("nomEffet").equals("magistrats"))
            {
                joueur.addPtsVictoire(voisinDroite.getNbreCarteBatimentCivil() + voisinGauche.getNbreCarteBatimentCivil());
            }
            if (effet.get("nomEffet").equals("commercants"))
            {
                joueur.addPtsVictoire(voisinDroite.getNbreCarteBatimentCivil() + voisinGauche.getNbreCarteBatimentCivil());
            }
            if (effet.get("nomEffet").equals("philosophes"))
            {
                joueur.addPtsVictoire(voisinDroite.getNbreCarteBatimentScientifique() + voisinGauche.getNbreCarteBatimentScientifique());
            }
            if (effet.get("nomEffet").equals("espions"))
            {
                joueur.addPtsVictoire(voisinDroite.getNbreCarteBatimentMilitaire() + voisinGauche.getNbreCarteBatimentMilitaire());
            }
            if (effet.get("nomEffet").equals("batisseurs"))
            {
                joueur.addPtsVictoire(voisinDroite.getMerveille().getEtape() + voisinGauche.getMerveille().getEtape() + joueur.getMerveille().getEtape());
            }
            if (effet.get("nomEffet").equals("armateurs"))
            {
                joueur.addPtsVictoire(joueur.getNbreCarteMatierePremiere() + joueur.getNbreCarteGuilde() + joueur.getNbreCarteProduitManufacture());
            }
            if (effet.get("nomEffet").equals("decorateurs"))
            {
                if (joueur.getMerveille().getEtape() == 3)
                {
                    joueur.addPtsVictoire(7);
                }
            }
            if (effet.get("nomEffet").equals("scientifiques"))
            {
                if (joueur.getNbCompas()> joueur.getNbRouages() && joueur.getNbCompas()>joueur.getNbTablettes())
                {
                    joueur.addCompas(1);
                }
                else if (joueur.getNbCompas() < joueur.getNbRouages() && joueur.getNbCompas()>joueur.getNbTablettes())
                {
                    joueur.addRouages(1);
                }
                else if (joueur.getNbCompas()> joueur.getNbRouages() && joueur.getNbCompas()<joueur.getNbTablettes())
                {
                    joueur.addTablettes(1);
                }
                else if (joueur.getNbCompas() == joueur.getNbRouages() && joueur.getNbCompas()>joueur.getNbTablettes())
                {
                    joueur.addRouages(1);
                }
                else if (joueur.getNbCompas() == joueur.getNbRouages() && joueur.getNbCompas() < joueur.getNbTablettes())
                {
                    joueur.addTablettes(1);
                }
                else if (joueur.getNbCompas() < joueur.getNbRouages() && joueur.getNbCompas()==joueur.getNbTablettes())
                {
                    joueur.addRouages(1);
                }
                else if (joueur.getNbCompas()> joueur.getNbRouages() && joueur.getNbCompas()==joueur.getNbTablettes())
                {
                    joueur.addTablettes(1);
                }

                else if (joueur.getNbCompas()> joueur.getNbRouages() && joueur.getNbRouages() == joueur.getNbTablettes())
                {
                    joueur.addCompas(1);
                }
            }
        }
    }
}
