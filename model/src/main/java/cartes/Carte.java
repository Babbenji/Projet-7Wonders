package cartes;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import type.ICarte;

import java.io.File;
import java.io.Serializable;
import java.util.Map;

public class Carte implements ICarte, Serializable {

    private String nom;
    private String type;
    private Map<String, Integer> cout;
    private String image;

    private int age;
    Map<String,String> effet;
    Map<String,String> chainage;

    @Override
    public Map<String, String> getChainage() {
        return chainage;
    }

    @Override
    public void setChainage(Map<String, String> chainage) {
        this.chainage = chainage;
    }

    public Carte() {
        super();
    }

    public Carte(String nom, String type, Map<String, Integer> cout, int configurationNumber, int age, Map<String, String> effet, Map<String,String> chainage,String image) {
        this.nom = nom;
        this.type = type;
        this.cout = cout;
        this.age = age;
        this.effet = effet;
        this.chainage = chainage;
        this.image = image;

    }

    public ImageView creationGraphique()
    {
        File file = new File("clientJfx/src/main/resources/images/");

        Image image1 = new Image(file.toURI().toString()+this.image);
        ImageView imageView = new ImageView();
        imageView.setImage(image1);
        return imageView;
    }


    // Getters & setters

    @Override
    public String getNom() {
        return nom;
    }

    @Override
    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public Map<String, Integer> getCout() {
        return cout;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public void setCout(Map<String, Integer> cout) {
        this.cout = cout;
    }

    @Override
    public int getAge() {
        return age;
    }

    @Override
    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public Map<String, String> getEffet() {
        return effet;
    }

    @Override
    public void setEffet(Map<String, String> effet) {
        this.effet = effet;
    }

    @Override
    public String toString() {
        return "Carte{" +
                "nom='" + nom + '\'' +
                ", type='" + type + '\'' +
                ", cout=" + cout +
                ", image='" + image + '\'' +
                ", age=" + age +
                ", effet=" + effet +
                ", chainage=" + chainage +
                '}';
    }
}
