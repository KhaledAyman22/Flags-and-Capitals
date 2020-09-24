package QuestionHandling;

public class Country {
    private String countryName,capitalName,imageName;
    private int id;

    Country(int id, String countryName, String capitalName, String imageName){
        this.id=id;
        this.countryName=countryName;
        this.capitalName=capitalName;
        this.imageName=imageName;
    }

    public int getId() {
        return id;
    }

    public String getCountryName() {
        return countryName;
    }

    public String getCapitalName() {
        return capitalName;
    }

    public String getImageName() {
        return imageName;
    }
}
