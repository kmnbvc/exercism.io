import java.util.*;

public class Allergies {

    private int score;

    public Allergies(int score) {
        this.score = score % 256;
    }

    public boolean isAllergicTo(Allergen allergen) {
        return getList().contains(allergen);
    }

    public List<Allergen> getList() {
        return getList(score, Collections.max(EnumSet.allOf(Allergen.class)).ordinal());
    }

    private List<Allergen> getList(int score, int index) {
        if (index < 0 || score <= 0) return new ArrayList<>();

        Allergen allergen = Allergen.values()[index];
        List<Allergen> allergens = getList(score % allergen.getScore(), --index);

        if (allergen.getScore() <= score) {
            allergens.add(allergen);
        }

        return allergens;
    }

}
