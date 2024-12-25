public class Song {
    private int decade;
    private String name;
    private String genre;   
    Song(String Name, String genre, int decade){
        name = Name;
        this.genre = genre;
        this.decade =  decade;
    }
    Song(){
        name = "";
        genre = "";
        decade = 0;
    }
    public String getGenre(){
        return genre;
    }
    public int getDecade(){
        return decade;
    }
    public String getName(){
        return name;
    }
    public void setName(String newName){
        name = newName;
    }
    public void setGenre(String newGenre){
        genre = newGenre;
    }
    public void setDecade(int newDecade) {
        decade = newDecade;
    }
    public String toString(){
        return "Name: " + name + " genre: " + genre + " decade: " + decade;
    }
    @Override
    public boolean equals(Object compare){
        Song Songcompare = (Song) compare;
        if(this.name.equals(Songcompare.name) && this.genre.equals(Songcompare.genre) && this.decade == Songcompare.decade){
            return true;
        }else{
            return false;
        }
    }
}
