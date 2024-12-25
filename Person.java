import java.util.List;

public class Person {
    private int favoriteDecade;
    private String favoriteGenre;
    private String name;
    Person(String Name, String favoriteGenre, int favoriteDecade){
        name = Name;
        this.favoriteGenre = favoriteGenre;
        this.favoriteDecade =  favoriteDecade;
    }
    public String getFavoriteGenre(){
        return favoriteGenre;
    }
    public int getFavoriteDecade(){
        return favoriteDecade;
    }
    public String getName(){
        return name;
    }
    public void setDecade(int decade){
        this.favoriteDecade = decade;
    }
    public void setGenre(String genre){
        this.favoriteGenre=genre;
    }
    public String toString(){
        return "Name: " + name + " Favorite Genre: " + favoriteGenre + " Favorite Decade: " + favoriteDecade;
    }
    @Override
    public boolean equals(Object compare){
        Person Personcompare = (Person) compare;
        if(this.name.equals(Personcompare.name) && this.favoriteGenre.equals(Personcompare.favoriteGenre) && this.favoriteDecade == Personcompare.favoriteDecade){
            return true;
        }else{
            return false;
        }
    }
}
